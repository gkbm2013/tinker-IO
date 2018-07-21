package tinker_io.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import tinker_io.tileentity.TileEntitySmartOutput;

public class ContainerSmartOutput extends ContainerBase {

    private TileEntitySmartOutput te;
    public static final int PATTERN = 0, PRODUCT = 1, UPG_UP = 2, UPG_DOWN = 3;

    public ContainerSmartOutput(InventoryPlayer playerInv, final TileEntitySmartOutput tileEntity) {
        this.te = tileEntity;
        addOwnSlots();
        addPlayerSlots(playerInv);
    }

    private void addOwnSlots(){
        IItemHandler inventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        addSlotToContainer(new SlotItemHandler(inventory, PATTERN, 68, 33));
        addSlotToContainer(new SlotItemHandler(inventory, PRODUCT, 128, 34));
        addSlotToContainer(new SlotItemHandler(inventory, UPG_UP, 153, 25));
        addSlotToContainer(new SlotItemHandler(inventory, UPG_DOWN, 153, 43));
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
