package tinker_io.plugins.jei.oreCrusher;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import tinker_io.helper.OreCrusherRecipe;

public class OreCrusherRecipeHandler implements IRecipeWrapperFactory<OreCrusherRecipe> {
    @Override
    public IRecipeWrapper getRecipeWrapper(OreCrusherRecipe recipe) {
        return new OreCrusherRecipeWrapper(recipe);
    }
}
