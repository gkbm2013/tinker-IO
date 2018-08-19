package tinker_io.inventory.slots;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import tinker_io.registry.ItemRegistry;

import javax.annotation.Nonnull;

public class SlotSpeedUpgrade extends SlotItemHandler {
    public SlotSpeedUpgrade(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        if (stack.isEmpty())
            return false;
        return stack.isItemEqual(new ItemStack(ItemRegistry.speedUpgrade));
    }
}
