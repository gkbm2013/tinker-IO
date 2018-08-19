package tinker_io.inventory.slots;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotSolidFuel extends SlotItemHandler {
    public SlotSolidFuel(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        if(stack.isEmpty())
            return false;
        return TileEntityFurnace.isItemFuel(stack);
    }
}
