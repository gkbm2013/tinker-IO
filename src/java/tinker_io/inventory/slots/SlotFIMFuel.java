package tinker_io.inventory.slots;

import tinker_io.registry.ItemRegistry;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.ItemStack;

public class SlotFIMFuel extends Slot
{
    
    private SlotFurnaceFuel slotFurnaceFuel;
    
	public SlotFIMFuel(
			IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
		
		this.slotFurnaceFuel = new SlotFurnaceFuel(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(
			ItemStack stack)
	{
		return slotFurnaceFuel.isItemValid(stack) || stack.isItemEqual(new ItemStack(ItemRegistry.SolidFuel));
	}
	
	@Override
    public int getItemStackLimit(ItemStack stack)
    {
        return slotFurnaceFuel.getItemStackLimit(stack);
    }
}
