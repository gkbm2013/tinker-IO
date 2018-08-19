package tinker_io.inventory.slots;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import tinker_io.handler.EnumHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class SlotUpgrades extends SlotItemHandler {

    List<EnumHandler.ItemUpgradeTypes> accept;

    public SlotUpgrades(IItemHandler itemHandler, int index, int xPosition, int yPosition, List<EnumHandler.ItemUpgradeTypes> accept) {
        super(itemHandler, index, xPosition, yPosition);
        this.accept = accept;
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        if(stack.isEmpty())
            return false;
        for(EnumHandler.ItemUpgradeTypes type : accept) {
            if(stack.getMetadata() == type.getID()) {
                return true;
            }
        }
        return false;
    }
}
