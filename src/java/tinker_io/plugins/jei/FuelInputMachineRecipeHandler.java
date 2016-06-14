package tinker_io.plugins.jei;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class FuelInputMachineRecipeHandler implements IRecipeHandler<FuelInputMachineRecipeWrapper>{

	@Override
	public Class<FuelInputMachineRecipeWrapper> getRecipeClass() {
		return FuelInputMachineRecipeWrapper.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		return FuelInputMachineRecipeCategory.CATEGORY;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(FuelInputMachineRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(FuelInputMachineRecipeWrapper recipe) {
		return true;
	}

	@Override
	public String getRecipeCategoryUid(FuelInputMachineRecipeWrapper recipe) {
		return FuelInputMachineRecipeCategory.CATEGORY;
	}

}
