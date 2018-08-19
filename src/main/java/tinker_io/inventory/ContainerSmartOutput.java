package tinker_io.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import tinker_io.handler.EnumHandler;
import tinker_io.inventory.slots.SlotProduct;
import tinker_io.inventory.slots.SlotUpgrades;
import tinker_io.tileentity.TileEntitySmartOutput;

import java.util.ArrayList;

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

        ArrayList<EnumHandler.ItemUpgradeTypes> acceptList = new ArrayList<EnumHandler.ItemUpgradeTypes>() {{
            add(EnumHandler.ItemUpgradeTypes.BASIN_UPGRADE);
            add(EnumHandler.ItemUpgradeTypes.REDETONE_UPG);
            add(EnumHandler.ItemUpgradeTypes.SLOT_UPG_1);
            add(EnumHandler.ItemUpgradeTypes.SLOT_UPG_2);
            add(EnumHandler.ItemUpgradeTypes.SLOT_UPG_3);
            add(EnumHandler.ItemUpgradeTypes.SLOT_UPG_4);
        }};

        addSlotToContainer(new SlotItemHandler(inventory, PATTERN, 68, 33));
        addSlotToContainer(new SlotProduct(inventory, PRODUCT, 128, 34));
        addSlotToContainer(new SlotUpgrades(inventory, UPG_UP, 153, 25, acceptList));
        addSlotToContainer(new SlotUpgrades(inventory, UPG_DOWN, 153, 43, acceptList));
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
