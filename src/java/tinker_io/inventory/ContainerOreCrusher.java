package tinker_io.inventory;

import tinker_io.TileEntity.OreCrusherTileEntity;
import tinker_io.items.SpeedUPG;
import tinker_io.registry.ItemRegistry;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
//import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerOreCrusher extends Container{

	OreCrusherTileEntity tileOC;
	
	public static final int SPEED_UPG = 0, ORE = 1, product = 2, slotsFortuneUPG1 = 3, slotsFortuneUPG2 = 4, slotsFortuneUPG3 = 5;  
	public ContainerOreCrusher(InventoryPlayer player, OreCrusherTileEntity tileEntityOC){
		List<Integer> banList = Lists.newLinkedList();
		banList.add(0);
		banList.add(1);
		banList.add(2);
		banList.add(3);
		banList.add(4);
		banList.add(5);
		
		this.tileOC = tileEntityOC;
		this.addSlotToContainer(new Slot(tileEntityOC, SPEED_UPG, 38, 34)); // Speed UPG.
		this.addSlotToContainer(new Slot(tileEntityOC, ORE, 62, 34)); // ore
		this.addSlotToContainer(new SlotProduct(tileEntityOC, product, 113, 34)); // product.
		
		this.addSlotToContainer(new SlotFortuneEnchantedBook(tileEntityOC, slotsFortuneUPG1, 44, 62)); // enchanted book
		this.addSlotToContainer(new SlotFortuneEnchantedBook(tileEntityOC, slotsFortuneUPG2, 62, 62)); // enchanted book
		this.addSlotToContainer(new SlotUPG(tileEntityOC, slotsFortuneUPG3, 81, 62, banList, 1)); // Infinity UPG.
		
		//this.addSlotToContainer(new SlotFurnace(player.player, tileEntityASC, 0, 25, 34));
		
		//player's inventory
		int i;
		for(i = 0; i < 3; ++i){
			for(int j = 0; j < 9; ++j){
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		//action bar
		for(i = 0; i < 9; ++i){
			this.addSlotToContainer(new Slot(player, i , 8 + i * 18 , 142));
		}
	}
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileOC.isUseableByPlayer(player);
	}

	/**
	 * Called when a player shift-clicks on a slot.
	 */
	@Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		final int fimINV_SIZE = tileOC.getSizeInventory();
		ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);

        //null checks and checks if the item can be stacked (maxStackSize > 1)
        if (slotObject != null && slotObject.getHasStack()) {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            //merges the item into player inventory since its in the tileEntity
            if (slot < fimINV_SIZE) {
                    if (!this.mergeItemStack(stackInSlot, fimINV_SIZE, 36+fimINV_SIZE, false)) {
                            return null; //do nothing if it can't
                    }
            }
            //itemstack is in player
            else {
            	//fuel is in player
            	if(tileOC.isOreInOreDic(stackInSlot)){
            		if (!this.mergeItemStack(stackInSlot, ORE, ORE+1, false)){
            			return null;
            		}
            	}
            	//spUPG is in player
            	else if (stackInSlot.getItem() instanceof SpeedUPG){
            		if (!this.mergeItemStack(stackInSlot, SPEED_UPG, SPEED_UPG+1, false)){
            			return null;
            		}
            	}
            	//enchanted book is in player
            	else if (tileOC.isFortuenEnchantedBook(stackInSlot)){
            		if (!this.mergeItemStack(stackInSlot, slotsFortuneUPG1, slotsFortuneUPG1+2, false)){
            			return null;
            		}
            	}
            	//slot UPG. infinity is in player
            	else if (stackInSlot.isItemEqual(new ItemStack(ItemRegistry.Upgrade, 1, 6))){
            		if (!this.mergeItemStack(stackInSlot, slotsFortuneUPG3, slotsFortuneUPG3+1, false)){
            			return null;
            		}
            	}
            	// place in action bar
    			else if (slot < fimINV_SIZE+27) {
    				if (!this.mergeItemStack(stackInSlot, fimINV_SIZE+27, fimINV_SIZE+36, false)){
    					return null;
    				}
    			}
    			// item in action bar - place in player inventory
    			else if (slot >= fimINV_SIZE+27 && slot < fimINV_SIZE+36 ){
    				if (!this.mergeItemStack(stackInSlot, fimINV_SIZE, fimINV_SIZE+27, false)){
    					return null;
    				}
    			}
            }
            //places it into the tileEntity is possible since its in the player inventory
//            else if (!this.mergeItemStack(stackInSlot, 0, tileFIM.getSizeInventory(), false)) {
//                    return null;
//            }

                if (stackInSlot.stackSize == 0) {
                        slotObject.putStack(null);
                } else {
                        slotObject.onSlotChanged();
                }

                if (stackInSlot.stackSize == stack.stackSize) {
                        return null;
                }
                slotObject.onPickupFromSlot(player, stackInSlot);
        }
        return stack;
    }
	
	@Override
	public void detectAndSendChanges(){
		super.detectAndSendChanges();
		/*for(int i = 0; i < this.crafters.size(); ++i){
			ICrafting craft = (ICrafting) this.crafters.get(i);
			
			//if(this.lastInputTime != this.tileFIM.inputTime){
				//craft.sendProgressBarUpdate(this, 0, this.tileFIM.inputTime);
			//}
		}*/
		
		//this.lastInputTime = this.tileFIM.inputTime;
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2){
		if(par1 == 0){
			//this.tileFIM.inputTime = par2;
		}
	}
}
