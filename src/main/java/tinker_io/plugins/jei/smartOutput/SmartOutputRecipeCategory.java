package tinker_io.plugins.jei.smartOutput;

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
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.materials.Material;
import tinker_io.TinkerIO;
import tinker_io.registry.BlockRegistry;
import tinker_io.registry.ItemRegistry;

import java.util.List;

public class SmartOutputRecipeCategory implements IRecipeCategory {

    public static String CATEGORY = TinkerIO.MOD_ID + ":" + "smart_output";
    private static ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(TinkerIO.MOD_ID, "textures/gui/jei/smart_output_jei_recipe.png");

    private final IDrawable background;
    protected final IDrawableAnimated arrow;

    public SmartOutputRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(BACKGROUND_TEXTURE, 0, 0, 149, 63);

        IDrawableStatic arrowDrawable = guiHelper.createDrawable(BACKGROUND_TEXTURE, 150, 0, 24, 17);
        this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 30, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public String getUid() {
        return CATEGORY;
    }

    @Override
    public String getTitle() {
        return I18n.format(BlockRegistry.smartOutput.getUnlocalizedName() + ".name");
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
        arrow.draw(minecraft, 71, 21);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup items = recipeLayout.getItemStacks();
        IGuiFluidStackGroup fluids = recipeLayout.getFluidStacks();

        List<FluidStack> input = ingredients.getInputs(FluidStack.class).get(0);

        int cap = input.get(0).amount;

        items.init(0, true, 58, 25);
        items.init(1, false, 113, 24);
        items.set(ingredients);

        fluids.init(0, true, 3, 2, 12, 52, Material.VALUE_Block, false, null);
        fluids.set(ingredients);

        fluids.init(1, true, 3, 2, 12, 52, cap, false, null);
        // otherwise it tries to get the second input fluidstack
        fluids.set(1, input);

        items.init(0, true, 44, 19);
        items.init(1, false, 104, 20);
        items.set(ingredients);

        List<ItemStack> upg = Lists.newLinkedList();
        upg.add(new ItemStack(ItemRegistry.upgrade, 1, 1));
        upg.add(new ItemStack(ItemRegistry.upgrade, 1, 2));
        upg.add(new ItemStack(ItemRegistry.upgrade, 1, 3));
        upg.add(new ItemStack(ItemRegistry.upgrade, 1, 4));
        upg.add(new ItemStack(ItemRegistry.upgrade, 1, 5));

        items.init(2, false, 129, 11);
        items.set(2, upg);

        items.init(3, false, 129, 29);
        if(recipeWrapper instanceof SmartOutputRecipeWrapper && ((SmartOutputRecipeWrapper)recipeWrapper).hasCast()){
            items.set(3, upg);
        }else{
            items.set(3, new ItemStack(ItemRegistry.upgrade, 1, 7));
        }


        fluids.init(0, true, 3, 2, 12, 52, Material.VALUE_Block, false, null);
    }
}
