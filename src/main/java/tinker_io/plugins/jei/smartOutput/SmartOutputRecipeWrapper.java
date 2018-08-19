package tinker_io.plugins.jei.smartOutput;

import com.google.common.collect.ImmutableList;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Modified from {@link slimeknights.tconstruct.plugin.jei.casting.CastingRecipeWrapper}
 * by GKB - 2018/8/19
 * */
public class SmartOutputRecipeWrapper implements IRecipeWrapper {

    protected final List<ItemStack> cast;
    protected final List<FluidStack> inputFluid;
    protected List<ItemStack> output;

    public final IDrawable castingBlock;

    private final CastingRecipe recipe;

    public SmartOutputRecipeWrapper(List<ItemStack> casts, CastingRecipe recipe, IDrawable castingBlock) {
        this.cast = casts;
        this.recipe = recipe;
        this.inputFluid = ImmutableList.of(recipe.getFluid());
        this.output = ImmutableList.of(recipe.getResult());
        this.castingBlock = castingBlock;
    }

    public SmartOutputRecipeWrapper(CastingRecipe recipe, IDrawable castingBlock) {
        // cast is not required
        if(recipe.cast != null) {
            cast = recipe.cast.getInputs();
        }
        else {
            cast = ImmutableList.of();
        }
        this.inputFluid = ImmutableList.of(recipe.getFluid());
        this.recipe = recipe;
        this.output = ImmutableList.of(recipe.getResult());

        this.castingBlock = castingBlock;
    }

    public boolean hasCast() {
        return recipe.cast != null;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(ItemStack.class, ImmutableList.of(cast));
        ingredients.setInputs(FluidStack.class, inputFluid);
        ingredients.setOutputs(ItemStack.class, lazyInitOutput());
    }

    public List<ItemStack> lazyInitOutput() {
        if(output == null) {
            if(recipe.getResult() == null) {
                return ImmutableList.of();
            }
            // we lazily evaluate the output in case the oredict wasn't there before
            output = ImmutableList.of(recipe.getResult());
        }
        return output;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        if(recipe.consumesCast()) {
            String s = I18n.format("gui.jei.casting.consume");
            int x = 55;
            x -= minecraft.fontRenderer.getStringWidth(s)/2;
            minecraft.fontRenderer.drawString(s, x, 40, 0xaa0000);
        }
    }

    public boolean isValid(boolean checkCast) {
        return !this.inputFluid.isEmpty()
                && this.inputFluid.get(0) != null
                && (!checkCast || !this.hasCast()
                || (!this.cast.isEmpty()
                && !this.cast.get(0).isEmpty()))
                && !this.output.isEmpty()
                && !this.output.get(0).isEmpty();
    }
}
