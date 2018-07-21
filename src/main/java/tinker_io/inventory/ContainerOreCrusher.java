package tinker_io.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import tinker_io.tileentity.TileEntityOreCrusher;

public class ContainerOreCrusher extends ContainerBase {

    private TileEntityOreCrusher te;
    public static final int SPEED_UPG = 0, ORE = 1, PRODUCT = 2, FORTUNE_UPG_1 = 3, FORTUNE_UPG_2 = 4, FORTUNE_UPG_3 = 5;

    public ContainerOreCrusher(InventoryPlayer playerInv, final TileEntityOreCrusher tileEntity) {
        this.te = tileEntity;
        addOwnSlots();
        addPlayerSlots(playerInv);
    }

    private void addOwnSlots(){
        IItemHandler inventory;
        inventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        addSlotToContainer(new SlotItemHandler(inventory, SPEED_UPG, 38, 34));
        addSlotToContainer(new SlotItemHandler(inventory, ORE, 62, 34)); // ore
        addSlotToContainer(new SlotItemHandler(inventory, PRODUCT, 113, 34)); // product.
        addSlotToContainer(new SlotItemHandler(inventory, FORTUNE_UPG_1, 44, 62)); // enchanted book
        addSlotToContainer(new SlotItemHandler(inventory, FORTUNE_UPG_2, 62, 62)); // enchanted book
        addSlotToContainer(new SlotItemHandler(inventory, FORTUNE_UPG_3, 81, 62)); // Infinity UPG.
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
