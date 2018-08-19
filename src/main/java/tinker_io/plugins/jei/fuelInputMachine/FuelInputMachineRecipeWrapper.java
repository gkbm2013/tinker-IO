package tinker_io.plugins.jei.fuelInputMachine;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.List;

public class FuelInputMachineRecipeWrapper implements IRecipeWrapper {

    protected final ItemStack input;

    public FuelInputMachineRecipeWrapper(ItemStack itemStack) {
        input = itemStack;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(ItemStack.class, input);
    }
}
