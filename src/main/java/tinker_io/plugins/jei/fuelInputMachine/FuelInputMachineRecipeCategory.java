package tinker_io.plugins.jei.fuelInputMachine;

import com.google.common.collect.Lists;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import tinker_io.TinkerIO;
import tinker_io.registry.BlockRegistry;
import tinker_io.registry.ItemRegistry;

import java.util.List;

public class FuelInputMachineRecipeCategory implements IRecipeCategory {

    public static String CATEGORY = TinkerIO.MOD_ID + ":" + "fuel_input_machine";
    private static ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(TinkerIO.MOD_ID, "textures/gui/jei/fuel_input_machine_jei_recipe.png");

    protected final IDrawable background;
    protected final IDrawableAnimated arrow;

    public FuelInputMachineRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(BACKGROUND_TEXTURE, 0, 0, 140, 60);

        IDrawableStatic arrowDrawable = guiHelper.createDrawable(BACKGROUND_TEXTURE, 142, 23, 14, 14);
        this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 100, IDrawableAnimated.StartDirection.TOP, true);
    }

    @Override
    public String getUid() {
        return CATEGORY;
    }

    @Override
    public String getTitle() {
        return I18n.format(BlockRegistry.fuelInputMachine.getUnlocalizedName() + ".name");
    }

    @Override
    public String getModName() {
        return TinkerIO.NAME;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        arrow.draw(minecraft, 81, 24);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup items = recipeLayout.getItemStacks();

        items.init(0, true, 57, 22);
        items.set(0, ingredients.getInputs(ItemStack.class).get(0));
        //items.setFromRecipe(0, recipe.getInputs());

        List<ItemStack> speedUpg = Lists.newLinkedList();
        speedUpg.add(new ItemStack(ItemRegistry.speedUpgrade));

        items.init(2, false, 3, 22);
        items.set(2, speedUpg);
        //items.setFromRecipe(2, speedUpg);
    }
}
