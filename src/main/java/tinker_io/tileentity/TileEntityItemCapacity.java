package tinker_io.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class TileEntityItemCapacity extends TileEntity {

    private final int slotsSize;
    protected ItemStackHandler inventory;
    private ItemStackHandler inventoryIO;

    protected TileEntityItemCapacity(int slot){
        slotsSize = slot;
        inventory = new ItemStackHandler(slotsSize){
            @Override
            protected void onContentsChanged(int slot) {
                // We need to tell the tile entity that something has changed so
                // that the chest contents is persisted
                TileEntityItemCapacity.this.markDirty();
                TileEntityItemCapacity.this.onSlotChange(slot);
            }
        };

        inventoryIO = new ItemStackHandler(slotsSize){
            @Override
            protected void onContentsChanged(int slot) {
                TileEntityItemCapacity.this.markDirty();
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
                return TileEntityItemCapacity.this.insertItem(slot, stack, simulate);
            }

            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                return TileEntityItemCapacity.this.extractItem(slot, amount, simulate);
            }
        };
    }

    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate){
        return stack;
    }

    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return ItemStack.EMPTY;
    }

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
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if(facing == null){
                return (T) inventory;
            }else{
                return (T) inventoryIO;
            }
        }
        return super.getCapability(capability, facing);
    }

    protected void onSlotChange(int slot) {

    }
}
