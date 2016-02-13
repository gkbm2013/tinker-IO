package tinker_io.TileEntity;

import net.minecraft.item.ItemStack;
import tinker_io.registry.ItemRegistry;

public class GKBTools {
	
	static public int getInputSize(ItemStack slot){
		int size = 1;
		boolean infinity = false;
		
		ItemStack slotUPG1 = new ItemStack(ItemRegistry.Upgrade, 1, 1);
		ItemStack slotUPG2 = new ItemStack(ItemRegistry.Upgrade, 1, 2);
		ItemStack slotUPG3 = new ItemStack(ItemRegistry.Upgrade, 1, 3);
		ItemStack slotUPG4 = new ItemStack(ItemRegistry.Upgrade, 1, 4);
		ItemStack slotUPGinfinity = new ItemStack(ItemRegistry.Upgrade, 1, 6);
		
		if(slot != null)
		{
			if(slot.isItemEqual(slotUPG1))
			{
				size = size+(slot.stackSize)*1;
			}
			else if(slot.isItemEqual(slotUPG2))
			{
				size = size+(slot.stackSize)*2;
			}
			else if(slot.isItemEqual(slotUPG3))
			{
				size = size+(slot.stackSize)*3;
			}
			else if(slot.isItemEqual(slotUPG4))
			{
				size = size+(slot.stackSize)*4;
			}
			else if(slot.isItemEqual(slotUPGinfinity))
			{
				infinity = true;
			}
		}
		
		if(infinity == true)
		{
			return 2048;
		}
		
		return size * 30;
	}
	
}
