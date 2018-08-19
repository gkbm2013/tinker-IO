package tinker_io.plugins.jei.oreCrusher;

import com.google.common.collect.ImmutableList;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import tinker_io.helper.OreCrusherRecipe;
import tinker_io.helper.OreDictionaryHelper;

import java.util.List;

public class OreCrusherRecipeWrapper implements IRecipeWrapper {

    protected final List<ItemStack> inputs;
    protected final List<ItemStack> outputs;

    public OreCrusherRecipeWrapper(OreCrusherRecipe recipe) {
        if(recipe.getItemStackInput().isEmpty()) {
            NonNullList<ItemStack> list = OreDictionaryHelper.getItemStacksFromOreDict(recipe.getOreDictInput());
            if(list.size() > 0) {
                inputs = ImmutableList.of(list.get(0));
            }else{
                inputs = ImmutableList.of(ItemStack.EMPTY);
            }
        }else{
            inputs = ImmutableList.of(recipe.getItemStackOutput());
        }
        outputs = ImmutableList.of(recipe.getItemStackOutput());
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        iIngredients.setInputLists(ItemStack.class, ImmutableList.of(inputs));
        iIngredients.setOutputs(ItemStack.class, outputs);
    }
}
