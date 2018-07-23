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

public class TileEntityFuelInputMachine extends TileEntitySmelteryItemCapacity implements ITickable {

    private static final int SLOTS_SIZE = 2;

    public TileEntityFuelInputMachine() {
        super(SLOTS_SIZE);
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

    public void resetTemp(){

    }

    @Override
    public void update() {

    }
}
