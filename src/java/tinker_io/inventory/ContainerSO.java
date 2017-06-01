package tinker_io.inventory;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
//import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
//import net.minecraftforge.common.util.ForgeDirection;1.7
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import tinker_io.TileEntity.SOTileEntity;
import tinker_io.handler.SORecipe;
import tinker_io.inventory.slots.SlotProduct;
import tinker_io.inventory.slots.SlotUPG;
import tinker_io.items.Upgrade;

public class ContainerSO extends ContainerTemplate{
	private SOTileEntity tileSO;
	
	private int lastFrozenTime;
	private int lastItemFrozenTime;
	
	public static final int PATTERN_SLOT = 0, PRODUCT_SLOT = 1;
	public static final int  UPG_UP_SLOT = 2, UPG_DOWN_SLOT = 3;
	public ContainerSO(InventoryPlayer player, SOTileEntity tileEntitySO){
		List<Integer> banList = Lists.newLinkedList();
		banList.add(0);
		banList.add(6);
		
		this.tileSO = tileEntitySO;
		this.addSlotToContainer(new Slot(tileEntitySO, PATTERN_SLOT, 68, 33)); //Pattern
		this.addSlotToContainer(new SlotProduct(tileEntitySO, PRODUCT_SLOT, 128, 34)); //Product
		this.addSlotToContainer(new SlotUPG(tileEntitySO, UPG_UP_SLOT, 153, 25, banList)); //UPG. up
		this.addSlotToContainer(new SlotUPG(tileEntitySO, UPG_DOWN_SLOT, 153, 43, banList)); //UPG. down
		
		this.addPlayerInventorySlotToContainer(player);
	}
	
	@Override
	public boolean enchantItem(EntityPlayer player, int action){
		return true;
	}
	

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileSO.isUsableByPlayer(player);
	}
	
	public static final int INV_START = 4; 
	public static final int HOTBAR_START = INV_START +27;
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slotObject = (Slot) inventorySlots.get(slot);
		SORecipe recipie = new SORecipe();

		// null checks and checks if the item can be stacked (maxStackSize > 1)
		if (slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			// merges the item into player inventory since its in the tileEntity
			if (slot < tileSO.getSizeInventory()) {
				if (!this.mergeItemStack(stackInSlot,
						tileSO.getSizeInventory(),
						36 + tileSO.getSizeInventory(), true)) {
					return ItemStack.EMPTY;
				}
			} else { // in inventory and bar
				if (stackInSlot.getItem() instanceof Upgrade) {
					if (!mergeItemStack(stackInSlot, UPG_UP_SLOT, UPG_DOWN_SLOT+1, false)) {
						return ItemStack.EMPTY;
					}
				} else if(recipie.isPattern(stackInSlot)){
					if (!mergeItemStack(stackInSlot, PATTERN_SLOT, PATTERN_SLOT+1, false)) {
						return ItemStack.EMPTY;
					}
				} else if(slot >= INV_START && slot < HOTBAR_START){ // inv -> bar
					if(!mergeItemStack(stackInSlot, HOTBAR_START, HOTBAR_START + 9, false)) {
						return ItemStack.EMPTY;
					}
				} else if (slot >= HOTBAR_START && slot < HOTBAR_START + 9) {
					if(!mergeItemStack(stackInSlot, INV_START, HOTBAR_START, false)) {
						return ItemStack.EMPTY;
					}
				}
			}
			recipie.isPattern(stackInSlot);
			// places it into the tileEntity is possible since its in the player
			// inventory
//			else if (!this.mergeItemStack(stackInSlot, 0,
//					tileSO.getSizeInventory(), false)) {
//				return null;
//			}

			if (stackInSlot.getCount() == 0) {
				slotObject.putStack(ItemStack.EMPTY);
			} else {
				slotObject.onSlotChanged();
			}

			if (stackInSlot.getCount() == stack.getCount()) {
				return ItemStack.EMPTY;
			}
			//slotObject.onPickupFromSlot(player, stackInSlot);
			slotObject.onSlotChanged();
		}
		return stack;
	}

}

