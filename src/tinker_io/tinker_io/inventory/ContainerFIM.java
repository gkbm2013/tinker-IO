package tinker_io.inventory;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tinker_io.TileEntity.FIMTileEntity;
import tinker_io.items.SolidFuel;
import tinker_io.items.SpeedUPG;
import tinker_io.items.Upgrade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
//import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class ContainerFIM extends Container {
	
	private FIMTileEntity tileFIM;
	private int lastInputTime;
	
	public static final int SPEED_UPG = 0, FUEL = 1, INV1_UPG = 2, INV2_UPG = 3;  
	public ContainerFIM(InventoryPlayer player, FIMTileEntity tileEntityASC){
		this.tileFIM = tileEntityASC;
		this.addSlotToContainer(new Slot(tileEntityASC, SPEED_UPG, 25, 20)); // Speed UPG.
		this.addSlotToContainer(new Slot(tileEntityASC, FUEL, 79, 34)); // catalyst
		this.addSlotToContainer(new Slot(tileEntityASC, INV1_UPG, 25, 34)); // Speed UPG.
		this.addSlotToContainer(new Slot(tileEntityASC, INV2_UPG, 25, 48)); // Speed UPG.
		
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
		return this.tileFIM.isUseableByPlayer(player);
	}
	
	/**
	 * Called when a player shift-clicks on a slot.
	 */
	@Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		final int fimINV_SIZE = tileFIM.getSizeInventory();
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
            	if(stackInSlot.getItem() instanceof SolidFuel){
            		if (!this.mergeItemStack(stackInSlot, FUEL, FUEL+1, false)){
            			return null;
            		}
            	}
            	//spUPG is in player
            	else if (stackInSlot.getItem() instanceof SpeedUPG){
            		if (!this.mergeItemStack(stackInSlot, SPEED_UPG, SPEED_UPG+1, false)){
            			return null;
            		}
            	}
            	//upg is in player
            	else if (stackInSlot.getItem() instanceof Upgrade){
            		if (!this.mergeItemStack(stackInSlot, INV1_UPG, INV2_UPG+1, false)){
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
	
	public void detectAndSendChanges(){
		super.detectAndSendChanges();
		for(int i = 0; i < this.crafters.size(); ++i){
			ICrafting craft = (ICrafting) this.crafters.get(i);
			
			if(this.lastInputTime != this.tileFIM.inputTime){
				craft.sendProgressBarUpdate(this, 0, this.tileFIM.inputTime);
			}
		}
		
		this.lastInputTime = this.tileFIM.inputTime;
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2){
		if(par1 == 0){
			this.tileFIM.inputTime = par2;
		}
	}

}
