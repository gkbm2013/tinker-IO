package tinker_io.plugins.jei;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class FuelInputMachineRecipeWrapperFactory implements IRecipeWrapperFactory<FuelInputMachineRecipeWrapper>{

	@Override
	public IRecipeWrapper getRecipeWrapper(FuelInputMachineRecipeWrapper recipe) {
		return recipe;
	}

}
