package tinker_io.inventory.slots;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import tinker_io.registry.ItemRegistry;
import tinker_io.tileentity.TileEntityOreCrusher;

import javax.annotation.Nonnull;

public class SlotFortuneUpgrade extends SlotItemHandler {

    public static final int TYPE_ENCHANTED_BOOK = 0;
    public static final int TYPE_FORTUNE_UPGRADE = 1;

    private int type;

    public SlotFortuneUpgrade(IItemHandler itemHandler, int index, int xPosition, int yPosition, int type) {
        super(itemHandler, index, xPosition, yPosition);
        this.type = type;
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        if(stack.isEmpty())
            return false;

        boolean isValid = false;

        switch (type) {
            case TYPE_ENCHANTED_BOOK:
                isValid = TileEntityOreCrusher.isFortuneEnchantedBook(stack);
                break;
            case TYPE_FORTUNE_UPGRADE:
                isValid = stack.isItemEqual(new ItemStack(ItemRegistry.upgrade, 1, 6));
                break;
        }
        return isValid;
    }

}
