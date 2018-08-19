package tinker_io.registry;

import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.smeltery.MeltingRecipe;
import tinker_io.helper.CrushedOreHelper;

import java.util.List;

public class MeltingRecipeRegister {

    public static final List<MeltingRecipe> customMeltingRecipes = Lists.newLinkedList();

    public static void register() {
        registerPureMetal();
    }

    private static void registerPureMetal() {
        OreCrusherRecipeRegister.oreCrusherRecipes.forEach(recipe -> {
            if(recipe.getItemStackOutput().isItemEqual(new ItemStack(ItemRegistry.crushedOre)) && recipe.getOreDictInput() != null) {
                FluidStack fluidStack = new FluidStack(FluidRegister.pureMetal, Material.VALUE_Ingot, new NBTTagCompound());
                fluidStack.tag.setString(CrushedOreHelper.TAG_ORE_DICT, recipe.getOreDictInput());
                registerMeltingWithNBT(recipe.getItemStackOutput(), fluidStack, 300);
            }
        });
    }

    public static void registerMeltingWithNBT(ItemStack inputItem, FluidStack outputFluidStack, int meltingTime){
        MeltingRecipe recipe = new MeltingRecipe(RecipeMatch.ItemCombination.ofNBT(inputItem, outputFluidStack.amount), outputFluidStack, meltingTime);
        TinkerRegistry.registerMelting(recipe);
        customMeltingRecipes.add(recipe);
    }

    public static void registerMelting(Item item, Fluid fluid, int outputAmount, int meltingTime){
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(item, outputAmount), fluid, meltingTime));
    }

}
