package tinker_io.inventory.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SlotFortuneEnchantedBook extends Slot{

	public SlotFortuneEnchantedBook(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack){
		return isFortuenEnchantedBook(stack);
	}
	
	private boolean isFortuenEnchantedBook(ItemStack itemstack){
		if(itemstack != null){
			if(getEnchantID(itemstack) == 35){
				return true;
			}
		}
		return false;
	}
	
	private int getEnchantID(ItemStack itemstack){
		if(itemstack != null && itemstack.getItem() instanceof ItemEnchantedBook){
			ItemEnchantedBook book = (ItemEnchantedBook) itemstack.getItem();
			NBTTagCompound tag = (NBTTagCompound) book.getEnchantments(itemstack).get(0);
			if(tag != null){
				return tag.getInteger("id");
			}
		}
		return 0;
	}

}
