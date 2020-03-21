package tinker_io.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import slimeknights.tconstruct.smeltery.tileentity.TileTank;
import tinker_io.helper.BlockFinder;
import tinker_io.tileentity.energy.EnergyCapability;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public class TileEntityStirlingEngine extends TileEntity implements ITickable {

    public static String TAG_ANGLE = "angle";
    public static String TAG_RF_PER_TICK = "rfPerTick";

    private double angle = 0;
    private int rfPerTick = 0;
    private int energyExportCount = 0;
    private int syncCount = 0;

    protected EnergyCapability storage = new EnergyCapability(500000, 0, 2000);


    /* RF Energy */

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityEnergy.ENERGY) {
            return (T) this.storage.getStorage();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityEnergy.ENERGY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    /* NBT */

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        angle = tagCompound.getDouble(TAG_ANGLE);
        rfPerTick = tagCompound.getInteger(TAG_RF_PER_TICK);
        this.storage.readFromNBT(tagCompound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setDouble(TAG_ANGLE, (short) angle);
        tagCompound.setInteger(TAG_RF_PER_TICK, rfPerTick);
        this.storage.writeToNBT(tagCompound);
        return tagCompound;
    }

    /* Packet */
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(this.pos, 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        readFromNBT(packet.getNbtCompound());
    }

    /* Logic */

    @Override
    public void update() {
        rfPerTick = 0;

        if(syncCount <= 20) {
            notifyBlockUpdate();
            markDirty();
            syncCount++;
        }

        FluidTank tank = getTankBelow();
        if (tank != null) {
            calculateRfPerTick(tank);

            if (rfPerTick != 0 && this.storage.getStorage().getEnergyStored() < this.storage.getStorage().getMaxEnergyStored()) {
                storage.setEnergyStored(getEnergyStored() + rfPerTick);
                angle = (angle + 1) % 61;
                drainTank(tank);
                notifyBlockUpdate();
                markDirty();
            }
        }


        if (extraEnergyToSurroundingMachine()) {
            notifyBlockUpdate();
            markDirty();
        }
    }

    private void calculateRfPerTick(FluidTank tank) {
        FluidStack fluidStack = tank.getFluid();
        if (fluidStack != null) {
            Fluid fluid = fluidStack.getFluid();
            if (fluid != null) {
                rfPerTick = (fluid.getTemperature() - 300) * 15 / 100;
            }
        }
    }

    private void drainTank(FluidTank tank) {
        FluidStack canDrain = tank.drain(1, false);
        if (canDrain != null) {
            tank.drain(2, true);
        }
    }

    public FluidTank getTankBelow() {
        TileEntity te = this.world.getTileEntity(pos.down());
        if (te instanceof TileTank) {
            return ((TileTank) te).getInternalTank();
        } else {
            return null;
        }
    }

    private boolean extraEnergyToSurroundingMachine() {
        int exportPerTick = Math.min(this.storage.getEnergyStored(), 1000);

        EnumFacing facing = null;
        EnumFacing facingOpp = null;
        switch (energyExportCount) {
            case 0:
                facing = EnumFacing.EAST;
                facingOpp = EnumFacing.WEST;
                break;
            case 1:
                facing = EnumFacing.WEST;
                facingOpp = EnumFacing.EAST;
                break;
            case 2:
                facing = EnumFacing.SOUTH;
                facingOpp = EnumFacing.NORTH;
                break;
            case 3:
                facing = EnumFacing.NORTH;
                facingOpp = EnumFacing.SOUTH;
                break;
            case 4:
                facing = EnumFacing.UP;
                facingOpp = EnumFacing.DOWN;
                break;
            case 5:
                facing = EnumFacing.DOWN;
                facingOpp = EnumFacing.UP;
                break;
            default:
                break;
        }

        if(facing != null) {
            TileEntity te = this.getWorld().getTileEntity(getPos().offset(facing));
            if(te != null) {
                IEnergyStorage iEnergyStorage = te.getCapability(CapabilityEnergy.ENERGY, facingOpp);
                if(iEnergyStorage != null) {
                    int estConsume = iEnergyStorage.receiveEnergy(exportPerTick, true);
                    iEnergyStorage.receiveEnergy(estConsume, false);
                    this.storage.getStorage().extractEnergy(estConsume, false);
                    return true;
                }
            }
        }

        if(energyExportCount + 1 >= 6 || energyExportCount < 0) {
            energyExportCount = 0;
        } else {
            energyExportCount++;
        }
        return true;
    }

    public int getRfPerTick() {
        return rfPerTick;
    }

    public void setEnergyAmount(int amount) {
        storage.setEnergyStored(amount);
    }

    public int getEnergyStored() {
        return storage.getEnergyStored();
    }

    public double getAngle() {
        return angle;
    }

    public FluidStack getFluid() {
        FluidTank tank = getTankBelow();
        if(tank != null) {
            if(tank.getFluid() != null) {
                return tank.getFluid();
            }
        }
        return null;
    }

    public int getFluidAmount() {
        FluidStack fluidStack = getFluid();
        if(fluidStack != null) {
            return fluidStack.amount;
        }
        return 0;
    }

    public int getTemperature() {
        FluidStack fluidStack = getFluid();
        if(fluidStack != null && fluidStack.getFluid() != null) {
            return fluidStack.getFluid().getTemperature();
        }
        return 0;
    }

    private void notifyBlockUpdate(){
        if(world!=null && pos != null){
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
        }
    }
}
