package tinker_io.plugins.jei;

import mezz.jei.api.*;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import tinker_io.gui.GuiOreCrusher;
import tinker_io.helper.OreCrusherRecipe;
import tinker_io.plugins.jei.oreCrusher.OreCrusherRecipeCategory;
import tinker_io.plugins.jei.oreCrusher.OreCrusherRecipeHandler;
import tinker_io.registry.OreCrusherRecipeRegister;

import javax.annotation.Nonnull;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {

    public static IJeiHelpers jeiHelpers;
    public static ICraftingGridHelper craftingGridHelper;
    public static IRecipeRegistry recipeRegistry;

    public static OreCrusherRecipeCategory oreCrusherRecipeCategory;

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        final IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        final IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        //Ore Crusher
        oreCrusherRecipeCategory = new OreCrusherRecipeCategory(guiHelper);

        registry.addRecipeCategories(oreCrusherRecipeCategory);
    }

    @Override
    public void register(@Nonnull IModRegistry registry) {
        jeiHelpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        //Ore Crusher
        registry.handleRecipes(OreCrusherRecipe.class, new OreCrusherRecipeHandler(), OreCrusherRecipeCategory.CATEGORY);
        registry.addRecipes(OreCrusherRecipeRegister.oreCrusherRecipes, OreCrusherRecipeCategory.CATEGORY);

        //Click area
        registry.addRecipeClickArea(GuiOreCrusher.class, 82, 35, 24, 15, OreCrusherRecipeCategory.CATEGORY);

    }

    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {
        recipeRegistry = jeiRuntime.getRecipeRegistry();
    }

}
