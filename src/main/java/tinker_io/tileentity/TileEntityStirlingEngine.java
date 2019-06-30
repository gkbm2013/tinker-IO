package tinker_io.tileentity;

import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import cofh.redstoneflux.impl.EnergyStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import slimeknights.tconstruct.smeltery.tileentity.TileTank;
import tinker_io.helper.BlockFinder;

import java.util.List;
import java.util.stream.Collectors;

public class TileEntityStirlingEngine extends TileEntity implements ITickable, IEnergyProvider {

    public static String TAG_ANGLE = "angle";
    public static String TAG_RF_PER_TICK = "rfPerTick";

    private double angle = 0;
    private int rfPerTick = 0;
    private int energyExportCount = 0;
    private int syncCount = 0;

    protected EnergyStorage storage = new EnergyStorage(500000, 0, 2000);


    /* RF Energy */
    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
        return storage.extractEnergy(Math.min(storage.getMaxExtract(), maxExtract), simulate);
    }

    @Override
    public int getEnergyStored(EnumFacing from) {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {
        return storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        System.out.println("GO");
        return true;
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

            if (rfPerTick != 0 && getEnergyStored(null) < getMaxEnergyStored(null)) {
                storage.setEnergyStored(getEnergyStored(null) + rfPerTick);
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

        BlockFinder blockFinder = new BlockFinder(pos, world);
        List<BlockPos> blocPosList = blockFinder.getSurroundingTileEntityPos(pos);
        blocPosList = blocPosList.stream().filter(pos -> world.getTileEntity(pos) instanceof IEnergyReceiver).collect(Collectors.toList());

        if (blocPosList.size() == 0) {
            return false;
        }
        if (energyExportCount >= blocPosList.size()) {
            energyExportCount = 0;
        }
        BlockPos blockPos = blocPosList.get(energyExportCount);

        if (blockPos == null) {
            energyExportCount = 0;
            return false;
        }

        int averageExtraEnergy = (int) Math.floor((float) exportPerTick / (float) blocPosList.size());

        IEnergyReceiver rfStorage = (IEnergyReceiver) world.getTileEntity(blockPos);
        if (rfStorage != null) {
            if (this.storage.getEnergyStored() > 0 && rfStorage.getEnergyStored(EnumFacing.DOWN) < rfStorage.getMaxEnergyStored(EnumFacing.DOWN)) {
                storage.setEnergyStored(storage.getEnergyStored() - averageExtraEnergy);
                rfStorage.receiveEnergy(EnumFacing.DOWN, averageExtraEnergy, false);
            }
        }

        energyExportCount++;
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
