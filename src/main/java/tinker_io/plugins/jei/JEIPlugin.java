package tinker_io.plugins.jei;

import mezz.jei.api.*;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import tinker_io.gui.GuiFuelInputMachine;
import tinker_io.gui.GuiOreCrusher;
import tinker_io.gui.GuiSmartOutput;
import tinker_io.helper.OreCrusherRecipe;
import tinker_io.plugins.jei.fuelInputMachine.FuelInputMachineRecipeCategory;
import tinker_io.plugins.jei.fuelInputMachine.FuelInputMachineRecipeChecker;
import tinker_io.plugins.jei.fuelInputMachine.FuelInputMachineRecipeHandler;
import tinker_io.plugins.jei.fuelInputMachine.FuelInputMachineRecipeWrapper;
import tinker_io.plugins.jei.oreCrusher.OreCrusherRecipeCategory;
import tinker_io.plugins.jei.oreCrusher.OreCrusherRecipeHandler;
import tinker_io.plugins.jei.smartOutput.SmartOutputRecipeCategory;
import tinker_io.plugins.jei.smartOutput.SmartOutputRecipeChecker;
import tinker_io.plugins.jei.smartOutput.SmartOutputRecipeHandler;
import tinker_io.plugins.jei.smartOutput.SmartOutputRecipeWrapper;
import tinker_io.registry.BlockRegistry;
import tinker_io.registry.OreCrusherRecipeRegister;

import javax.annotation.Nonnull;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {

    public static IJeiHelpers jeiHelpers;
    public static ICraftingGridHelper craftingGridHelper;
    public static IRecipeRegistry recipeRegistry;

    public static OreCrusherRecipeCategory oreCrusherRecipeCategory;
    public static SmartOutputRecipeCategory smartOutputRecipeCategory;
    public static FuelInputMachineRecipeCategory fuelInputMachineRecipeCategory;

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        final IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        final IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        //Ore Crusher
        oreCrusherRecipeCategory = new OreCrusherRecipeCategory(guiHelper);
        smartOutputRecipeCategory = new SmartOutputRecipeCategory(guiHelper);
        fuelInputMachineRecipeCategory = new FuelInputMachineRecipeCategory(guiHelper);


        registry.addRecipeCategories(
                oreCrusherRecipeCategory,
                smartOutputRecipeCategory,
                fuelInputMachineRecipeCategory);
    }

    @Override
    public void register(@Nonnull IModRegistry registry) {
        jeiHelpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        //Ore Crusher
        registry.handleRecipes(OreCrusherRecipe.class, new OreCrusherRecipeHandler(), OreCrusherRecipeCategory.CATEGORY);
        registry.addRecipes(OreCrusherRecipeRegister.oreCrusherRecipes, OreCrusherRecipeCategory.CATEGORY);

        //Smart Output
        registry.handleRecipes(SmartOutputRecipeWrapper.class, new SmartOutputRecipeHandler(), SmartOutputRecipeCategory.CATEGORY);
        registry.addRecipes(SmartOutputRecipeChecker.getCastingRecipes(), SmartOutputRecipeCategory.CATEGORY);

        //Fuel Input Machine
        registry.handleRecipes(FuelInputMachineRecipeWrapper.class, new FuelInputMachineRecipeHandler(), FuelInputMachineRecipeCategory.CATEGORY);
        registry.addRecipes(FuelInputMachineRecipeChecker.getFuel(), FuelInputMachineRecipeCategory.CATEGORY);

        //Click area
        registry.addRecipeClickArea(GuiOreCrusher.class, 82, 35, 24, 15, OreCrusherRecipeCategory.CATEGORY);
        registry.addRecipeClickArea(GuiSmartOutput.class, 94, 34, 24, 15, SmartOutputRecipeCategory.CATEGORY);
        registry.addRecipeClickArea(GuiFuelInputMachine.class, 102, 35, 18, 18, FuelInputMachineRecipeCategory.CATEGORY);

        //Recipe Catalyst
        registry.addRecipeCatalyst(new ItemStack(BlockRegistry.fuelInputMachine), FuelInputMachineRecipeCategory.CATEGORY);
        registry.addRecipeCatalyst(new ItemStack(BlockRegistry.smartOutput), SmartOutputRecipeCategory.CATEGORY);
        registry.addRecipeCatalyst(new ItemStack(BlockRegistry.oreCrusher), OreCrusherRecipeCategory.CATEGORY);
    }

    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {
        recipeRegistry = jeiRuntime.getRecipeRegistry();
    }

}
