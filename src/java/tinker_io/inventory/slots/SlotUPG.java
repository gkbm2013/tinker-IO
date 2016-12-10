package tinker_io.inventory.slots;

import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import tinker_io.items.Upgrade;

public class SlotUPG  extends Slot{

	private List<Integer> metaBanList; 
	
	private int slotStackLimit;
	
	public SlotUPG(IInventory inventoryIn, int index, int xPosition, int yPosition, List<Integer> metaBanList) {
		super(inventoryIn, index, xPosition, yPosition);
		this.metaBanList = metaBanList;
	}
	
	public SlotUPG(IInventory inventoryIn, int index, int xPosition, int yPosition, List<Integer> metaBanList, int slotStackLimit) {
		super(inventoryIn, index, xPosition, yPosition);
		this.metaBanList = metaBanList;
		this.slotStackLimit = slotStackLimit;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack){
		if(stack.getItem() instanceof Upgrade){
			if(metaBanList != null && metaBanList.size() > 0){
				if(!metaBanList.contains(stack.getItem().getDamage(stack))){
					return true;
				}
			}else{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int getSlotStackLimit(){
		if(slotStackLimit > 0){
			return slotStackLimit;
		}else{
			return this.inventory.getInventoryStackLimit();
		}
    }

}
