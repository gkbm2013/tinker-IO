package tinker_io.handler;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.library.smeltery.ICastingRecipe;
import slimeknights.tconstruct.library.utils.ListUtil;

import java.util.List;

public class SmartOutputRecipeHandler {
    public static List<CastingRecipe> fluidStackTableCastingRecipe  = Lists.newLinkedList();

    public static ICastingRecipe findTableCastingRecipe(ItemStack cast, FluidStack fluidStack) {
        ICastingRecipe recipe = null;
        if(fluidStack != null){
            recipe = TinkerRegistry.getTableCasting(cast, fluidStack.getFluid());
            if(recipe == null) {
                recipe = getFluidStackTableCasting(cast, fluidStack);
            }
        }
        return recipe;
    }

    public static ICastingRecipe findBasinCastingRecipe(ItemStack cast, FluidStack fluidStack) {
        if(fluidStack == null) return null;
        return TinkerRegistry.getBasinCasting(cast, fluidStack.getFluid());
    }

    public static void registerSmartOutputCasting(ItemStack output, ItemStack cast, FluidStack fluid){
        if(!cast.isEmpty() && !output.isEmpty()){
            registerFluidStackCastingRecipe(new CastingRecipe(output, RecipeMatch.of(cast), fluid, false, true));
        }
    }

    /*
     * ============================================================
     * Because TConstuct's casting table didn't support FluidStack,
     *  I build these method to process the output of pure metal.
     *  -GKB 2016/4/5 14:31
     * ============================================================
     * */

    public static void registerFluidStackCastingRecipe(CastingRecipe recipe) {
        fluidStackTableCastingRecipe.add(recipe);
    }

    public static CastingRecipe getFluidStackTableCasting(ItemStack cast, FluidStack fluidstack) {
        for(CastingRecipe recipe : fluidStackTableCastingRecipe) {
            if(matches(cast, fluidstack, recipe)) {
                return recipe;
            }
        }
        return null;
    }

    public static boolean matches(ItemStack cast, FluidStack fluidStack, CastingRecipe recipe) {
        if((cast == null && recipe.cast == null) || (recipe.cast != null && recipe.cast.matches(ListUtil.getListFrom(cast)) != null)) {
            return recipe.getFluid().isFluidEqual(fluidStack);
        }
        return false;
    }

    public static List<CastingRecipe> getFluidStackTableCastingRecipe(){
        return fluidStackTableCastingRecipe;
    }

}
