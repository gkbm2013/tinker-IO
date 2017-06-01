package tinker_io.plugins.jei;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class SmartOutputRecipeWrapperFactory implements IRecipeWrapperFactory<SmartOutputRecipeWrapper>{

	@Override
	public IRecipeWrapper getRecipeWrapper(SmartOutputRecipeWrapper recipe) {
		return recipe;
	}

}
