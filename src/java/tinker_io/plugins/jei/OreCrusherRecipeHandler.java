package tinker_io.plugins.jei;

import javax.annotation.Nonnull;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class OreCrusherRecipeHandler implements IRecipeHandler<OreCrusherRecipeWrapper>{

	@Nonnull
	@Override
	public Class<OreCrusherRecipeWrapper> getRecipeClass() {
		return OreCrusherRecipeWrapper.class;
	}

	@Nonnull
	public String getRecipeCategoryUid() {
		return OreCrusherRecipeCategory.CATEGORY;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull OreCrusherRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull OreCrusherRecipeWrapper recipe) {
		return (recipe.outputList != null && !recipe.outputList.isEmpty() && recipe.outputList.get(0) != null);
	}

	@Override
	public String getRecipeCategoryUid(OreCrusherRecipeWrapper recipe) {
		return OreCrusherRecipeCategory.CATEGORY;
	}

}
