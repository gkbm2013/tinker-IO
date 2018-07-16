package tinker_io.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import tinker_io.inventory.ContainerFuelInputMachine;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityFuelInputMachine extends TileEntity implements ITickable {

    private static final int SLOTS_SIZE = 2;

    private ItemStackHandler inventory = new ItemStackHandler(SLOTS_SIZE){
        @Override
        protected void onContentsChanged(int slot) {
            // We need to tell the tile entity that something has changed so
            // that the chest contents is persisted
            TileEntityFuelInputMachine.this.markDirty();
        }
    };

    private ItemStackHandler inventoryIO = new ItemStackHandler(SLOTS_SIZE){
        @Override
        protected void onContentsChanged(int slot) {
            TileEntityFuelInputMachine.this.markDirty();
        }

        @Override
        public void setStackInSlot(int slot, @Nonnull ItemStack stack)
        {
            inventory.setStackInSlot(slot, stack);
        }

        @Override
        public int getSlots()
        {
            return inventory.getSlots();
        }

        @Override
        @Nonnull
        public ItemStack getStackInSlot(int slot) {
            return inventory.getStackInSlot(slot);
        }

        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (slot == ContainerFuelInputMachine.FUEL) {
                return inventory.insertItem(slot, stack, simulate);
            }
            return stack;
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
//            if (slot == ContainerFuelInputMachine.PRODUCT) {
//                return inventory.extractItem(slot, amount, simulate);
//            }
            return ItemStack.EMPTY;
        }
    };

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        super.readFromNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
//        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T)inventory : super.getCapability(capability, facing);
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if(facing == null){
                return (T) inventory;
            }else{
                return (T) inventoryIO;
            }
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void update() {

    }
}
