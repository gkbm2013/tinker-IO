package tinker_io.plugins.jei.fuelInputMachine;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class FuelInputMachineRecipeHandler implements IRecipeWrapperFactory<FuelInputMachineRecipeWrapper> {
    @Override
    public IRecipeWrapper getRecipeWrapper(FuelInputMachineRecipeWrapper recipe) {
        return recipe;
    }
}
