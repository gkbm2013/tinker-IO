package tinker_io.plugins.jei;

import javax.annotation.Nonnull;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

@Deprecated
public class SmartOutputRecipeHandler  implements IRecipeHandler<SmartOutputRecipeWrapper>{

	@Nonnull
	@Override
	public Class<SmartOutputRecipeWrapper> getRecipeClass() {
		return SmartOutputRecipeWrapper.class;
	}

	/*@Nonnull
	@Override
	@Deprecated
	public String getRecipeCategoryUid() {
		return SmartOutputRecipeCategory.CATEGORY;
	}*/
	
	@Override
	public String getRecipeCategoryUid(SmartOutputRecipeWrapper recipe) {
		return SmartOutputRecipeCategory.CATEGORY;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(SmartOutputRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(SmartOutputRecipeWrapper recipe) {
		return !recipe.inputFluid.isEmpty() &&
		           recipe.inputFluid.get(0) != null &&
		           (!recipe.hasCast() || (!recipe.cast.isEmpty() && recipe.cast.get(0) != null)) &&
		           (recipe.output != null && !recipe.output.isEmpty() && recipe.output.get(0) != null);
	}

}
