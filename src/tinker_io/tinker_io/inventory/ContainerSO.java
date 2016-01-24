package tinker_io.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import tinker_io.TileEntity.SOTileEntity;
import tinker_io.handler.SORecipes;

public class ContainerSO extends Container{
	private SOTileEntity tileSO;
	
	private int lastFrozenTime;
	private int lastItemFrozenTime;
	
	public static final int PATTERN_SLOT = 0, PRODUCT_SLOT = 1;
	public static final int  UPG_UP_SLOT = 2, UPG_DOWN_SLOT = 3;
	public ContainerSO(InventoryPlayer player, SOTileEntity tileEntitySO){
		this.tileSO = tileEntitySO;
		this.addSlotToContainer(new Slot(tileEntitySO, PATTERN_SLOT, 68, 33)); //Pattern
		this.addSlotToContainer(new Slot(tileEntitySO, PRODUCT_SLOT, 128, 34)); //Product
		this.addSlotToContainer(new Slot(tileEntitySO, UPG_UP_SLOT, 153, 25)); //UPG. up
		this.addSlotToContainer(new Slot(tileEntitySO, UPG_DOWN_SLOT, 153, 43)); //UPG. down
		//this.addSlotToContainer(new SlotFurnace(player.player, tileEntitySO, 1, 128, 34)); //Product
		
		//this.addSlotToContainer(new Slot(tileEntitySO, 1, 79, 34));
		
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
	public boolean enchantItem(EntityPlayer player, int action){
		return true;
	}
	

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileSO.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);

		// null checks and checks if the item can be stacked (maxStackSize > 1)
		if (slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			// merges the item into player inventory since its in the tileEntity
			if (slot < tileSO.getSizeInventory()) {
				if (!this.mergeItemStack(stackInSlot,
						tileSO.getSizeInventory(),
						36 + tileSO.getSizeInventory(), true)) {
					return null;
				}
			}
			// places it into the tileEntity is possible since its in the player
			// inventory
			else if (!this.mergeItemStack(stackInSlot, 0,
					tileSO.getSizeInventory(), false)) {
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

}

