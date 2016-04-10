package tinker_io.inventory;

import tinker_io.registry.ItemRegistry;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFIMFuel extends Slot{

	public SlotFIMFuel(
			IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(
			ItemStack stack)
	{
		return stack.isItemEqual(new ItemStack(ItemRegistry.SolidFuel));
	}
}
