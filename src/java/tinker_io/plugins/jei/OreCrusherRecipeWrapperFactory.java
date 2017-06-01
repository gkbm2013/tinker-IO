package tinker_io.plugins.jei;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class OreCrusherRecipeWrapperFactory implements IRecipeWrapperFactory<OreCrusherRecipeWrapper>{

	@Override
	public IRecipeWrapper getRecipeWrapper(OreCrusherRecipeWrapper recipe) {
		return recipe;
	}

}
