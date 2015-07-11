package tinker_io.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tinker_io.TileEntity.FIMTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class ContainerFIM extends Container {
	
	private FIMTileEntity tileFIM;
	private int lastInputTime;

	public ContainerFIM(InventoryPlayer player, FIMTileEntity tileEntityASC){
		this.tileFIM = tileEntityASC;
		this.addSlotToContainer(new Slot(tileEntityASC, 0, 25, 20)); // Speed UPG.
		this.addSlotToContainer(new Slot(tileEntityASC, 1, 79, 34)); // catalyst
		
		this.addSlotToContainer(new Slot(tileEntityASC, 2, 25, 34)); // Speed UPG.
		this.addSlotToContainer(new Slot(tileEntityASC, 3, 25, 48)); // Speed UPG.

		//this.addSlotToContainer(new SlotFurnace(player.player, tileEntityASC, 0, 25, 34));
		int i;
		
		for(i = 0; i < 3; ++i){
			for(int j = 0; j < 9; ++j){
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for(i = 0; i < 9; ++i){
			this.addSlotToContainer(new Slot(player, i , 8 + i * 18 , 142));
		}
	}
	
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileFIM.isUseableByPlayer(player);
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);

        //null checks and checks if the item can be stacked (maxStackSize > 1)
        if (slotObject != null && slotObject.getHasStack()) {
                ItemStack stackInSlot = slotObject.getStack();
                stack = stackInSlot.copy();

                //merges the item into player inventory since its in the tileEntity
                if (slot < tileFIM.getSizeInventory()) {
                        if (!this.mergeItemStack(stackInSlot, tileFIM.getSizeInventory(), 36+tileFIM.getSizeInventory(), true)) {
                                return null;
                        }
                }
                //places it into the tileEntity is possible since its in the player inventory
                else if (!this.mergeItemStack(stackInSlot, 0, tileFIM.getSizeInventory(), false)) {
                        return null;
                }

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
