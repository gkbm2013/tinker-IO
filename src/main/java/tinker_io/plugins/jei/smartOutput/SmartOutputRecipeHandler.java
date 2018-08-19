package tinker_io.plugins.jei.smartOutput;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class SmartOutputRecipeHandler implements IRecipeWrapperFactory<SmartOutputRecipeWrapper> {
    @Override
    public IRecipeWrapper getRecipeWrapper(SmartOutputRecipeWrapper recipe) {
        return recipe;
    }
}
