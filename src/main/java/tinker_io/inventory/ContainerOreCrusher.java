package tinker_io.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import tinker_io.inventory.slots.SlotFortuneUpgrade;
import tinker_io.inventory.slots.SlotOreCrusherInput;
import tinker_io.inventory.slots.SlotProduct;
import tinker_io.inventory.slots.SlotSpeedUpgrade;
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

        addSlotToContainer(new SlotSpeedUpgrade(inventory, SPEED_UPG, 38, 34));
        addSlotToContainer(new SlotOreCrusherInput(inventory, ORE, 62, 34)); // ore
        addSlotToContainer(new SlotProduct(inventory, PRODUCT, 113, 34)); // product.
        addSlotToContainer(new SlotFortuneUpgrade(inventory, FORTUNE_UPG_1, 44, 62, SlotFortuneUpgrade.TYPE_ENCHANTED_BOOK)); // enchanted book
        addSlotToContainer(new SlotFortuneUpgrade(inventory, FORTUNE_UPG_2, 62, 62, SlotFortuneUpgrade.TYPE_ENCHANTED_BOOK)); // enchanted book
        addSlotToContainer(new SlotFortuneUpgrade(inventory, FORTUNE_UPG_3, 81, 62, SlotFortuneUpgrade.TYPE_FORTUNE_UPGRADE)); // Infinity UPG.
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
