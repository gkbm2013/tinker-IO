package tinker_io.inventory;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tinker_io.TileEntity.fim.FIMTileEntity;
import tinker_io.api.Observable;
import tinker_io.api.Observer;
import tinker_io.items.SolidFuel;
import tinker_io.items.SpeedUPG;
import tinker_io.items.Upgrade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerFIM extends ContainerTemplate implements Observer{
	
	private FIMTileEntity tile;
	
	public static final int
		SPEED_UPG = 0,
		FUEL = 1;
		/*INV1_UPG = 2,
		INV2_UPG = 3;*/  
	public ContainerFIM(InventoryPlayer player, FIMTileEntity tile){
		this.tile = tile;
		
		this.addSlotToContainer(new SlotFIMSpeedUPG(tile, SPEED_UPG, 25, 34)); // Speed UPG.
		this.addSlotToContainer(new SlotFIMFuel(tile, FUEL, 79, 34)); // catalyst
		/*this.addSlotToContainer(new Slot(tile, INV1_UPG, 25, 34)); // Speed UPG.
		this.addSlotToContainer(new Slot(tile, INV2_UPG, 25, 48)); // Speed UPG.*/
		
		this.addPlayerInventorySlotToContainer(player);
	}
	
	/**
	 *  player move item with mouse;
	 */
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tile.isUseableByPlayer(player);
	}
	
	/**
	 * Called it when a player shift-clicks on a slot.
	 */
	@Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		final int fimINV_SIZE = tile.getSizeInventory();
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
            	/*if(stackInSlot.getItem() instanceof SolidFuel){
            		if (!this.mergeItemStack(stackInSlot, FUEL, FUEL+1, false)){
            			return null;
            		}
            	}*/
            	
            	if(TileEntityFurnace.getItemBurnTime(stackInSlot) > 0){
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
            	/*else if (stackInSlot.getItem() instanceof Upgrade){
            		if (!this.mergeItemStack(stackInSlot, INV1_UPG, INV2_UPG+1, false)){
            			return null;
            		}
            	}*/
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
		for(int i = 0; i < this.crafters.size(); ++i){
			ICrafting craft = (ICrafting) this.crafters.get(i);
			
			if(hasDifferentInputTime())
			{
				craft.sendProgressBarUpdate(this, 0, inputTime);
			}
			
			if(this.lastfueltemp != this.fueltemp)
			{
			    craft.sendProgressBarUpdate(this, 1, fueltemp);
			}
		}
		
		this.lastInputTime = inputTime;
		this.lastfueltemp = fueltemp;
	}
	
	private boolean hasDifferentInputTime()
	{
		return this.lastInputTime != this.inputTime;
	}
	
	@Override
	public void onCraftGuiOpened(ICrafting listener)
	{
		super.onCraftGuiOpened(listener);
		listener.sendProgressBarUpdate(this, 0, inputTime);
		
		tile.addObserver(this);
	}
	
    /**
     * Called when the container is closed.
     */
	@Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
	    super.onContainerClosed(playerIn);
	    
	    tile.removeObserver(this);
    }
	

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int ID, int data)
    {
        switch (ID)
        {
            case 0:
                this.tile.inputTime = data;
                return;
            case 1:
                this.tile.keepInputTime = data;
                return;
        }
    }

    private int inputTime = 0;
    private int lastInputTime = 0;

    private int fueltemp = 0;
    private int lastfueltemp = 0;


    @Override
    public void receivedTopic()
    {
        this.inputTime = tile.getInputTime();
        this.fueltemp = tile.keepInputTime;
    }
	
}
