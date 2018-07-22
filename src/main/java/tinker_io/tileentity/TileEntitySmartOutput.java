package tinker_io.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntitySmartOutput extends TileEntityItemCapacity implements ITickable, IFluidHandler {

    private static final int SLOTS_SIZE = 4;

    private FluidTank tank;

    public TileEntitySmartOutput() {
        super(SLOTS_SIZE);
        tank = new FluidTank(Fluid.BUCKET_VOLUME * 10);
    }

    @Override
    public void update() {

    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        tank.readFromNBT(tag);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tank.writeToNBT(tag);
        return super.writeToNBT(tag);
    }

    public FluidTank getTank() {
        return tank;
    }

    /* Capability */
    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return (T) tank;
        return super.getCapability(capability, facing);
    }

    /* IFluidHandler */

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        int amount = tank.fill(resource, doFill);
        //TODO check mark dirty??
        markDirty();
        return amount;
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if (tank.getFluidAmount() == 0)
            return null;
        if (tank.getFluid().getFluid() != resource.getFluid())
            return null;
        //TODO Check mark dirty
        return this.drain(resource.amount, doDrain);
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        FluidStack amount = tank.drain(maxDrain, doDrain);
        if (amount != null && doDrain) {
            //TODO check mark dirty??
            markDirty();
        }
        return amount;
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return getTank().getTankProperties();
    }
}
