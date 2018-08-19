package tinker_io.inventory.slots;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import tinker_io.helper.OreCrusherRecipe;
import tinker_io.registry.OreCrusherRecipeRegister;

import javax.annotation.Nonnull;

public class SlotOreCrusherInput extends SlotItemHandler {
    public SlotOreCrusherInput(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        if (stack.isEmpty())
            return false;
        //TODO: performance check
        for(OreCrusherRecipe recipe : OreCrusherRecipeRegister.oreCrusherRecipes) {
            if(recipe.match(stack)) {
                return true;
            }
        }
        return false;
    }
}
