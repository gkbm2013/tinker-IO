package tinker_io.handler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
//import slimeknights.tconstruct.library.crafting.LiquidCasting;
import slimeknights.tconstruct.tools.TinkerTools;
import tinker_io.main.Main;
import tinker_io.registry.ItemRegistry;

public class SORecipes{
	
	private static List<CastingRecipe> tableCastRegistryWithFluidStack = Lists.newLinkedList();
	
	public ItemStack getCastingRecipes(FluidStack fluid, ItemStack itemStack){	
		/*ItemStack basin = new ItemStack(TinkerSmeltery.searedBlock ,1 ,2);
		if(fluid != null && fluid.isFluidEqual(new FluidStack(TinkerSmeltery.moltenGlassFluid, FluidContainerRegistry.BUCKET_VOLUME)) && itemStack != null && itemStack.isItemEqual(basin)){
			return new ItemStack(TinkerSmeltery.clearGlass ,1);
		}*/
		
		CastingRecipe tableRecipe = TinkerRegistry.getTableCasting(itemStack, fluid.getFluid());
		CastingRecipe tableRecipeWithFluidStack = getTableCastingWithFluidStack(itemStack, fluid);
		
		if(tableRecipe != null){
			return tableRecipe.getResult();
		}else if(tableRecipeWithFluidStack != null){
			return tableRecipeWithFluidStack.getResult();
		}else{
			return null;
		}
	}
	
	public FluidStack getCastingFluidCost(FluidStack fluid, ItemStack itemStack){
		//ItemStack basin = new ItemStack(TinkerSmeltery.searedBlock ,1 ,2);
//		if(fluid != null && fluid.isFluidEqual(new FluidStack(TinkerSmeltery.moltenGlassFluid, FluidContainerRegistry.BUCKET_VOLUME)) && itemStack != null && itemStack.isItemEqual(basin)){
//			return new FluidStack(TinkerSmeltery.moltenGlassFluid, FluidContainerRegistry.BUCKET_VOLUME);
//		}
		
		
		//Fluid
		
		CastingRecipe tableRecipe = TinkerRegistry.getTableCasting(itemStack, fluid.getFluid());
		CastingRecipe tableRecipeWithFluidStack = getTableCastingWithFluidStack(itemStack, fluid);
		
		CastingRecipe useAbleTableRecipe = null;
		
		if(tableRecipe != null){
			useAbleTableRecipe = tableRecipe;
		}else if(tableRecipeWithFluidStack != null){
			useAbleTableRecipe = tableRecipeWithFluidStack;
		}
		
		if(useAbleTableRecipe != null){
			FluidStack tableFluid = useAbleTableRecipe.getFluid();
			
			
			if(tableFluid != null){
				return tableFluid;
			}else{
				return null;
			}
		}else{
			return null;
		}
		
	}
	
	/**
	 * 
	 * @param output The item output form Smart Output.
	 * @return This method will return ture when the ItemStack "output" is a pattern. *Note : Only if Main.iguanas_support == true, this method will active.
	 */
	public boolean isPattern(ItemStack output){
		Collection<Item> items = TinkerRegistry.getPatternItems();
		Item item = output.getItem();
		for (Item i : items) {
			if ( item.equals(i)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isPatternWithIguana(ItemStack itemStack){
		if(Main.iguanas_support == false){
			return false;
		}
		return isPattern(itemStack);
	}
	
	public FluidStack getBasinFluid(FluidStack fluid, ItemStack itemStack){
		CastingRecipe basinRecipe = TinkerRegistry.getBasinCasting(itemStack, fluid.getFluid());

		
		if(basinRecipe != null){
			FluidStack basinFluid = basinRecipe.getFluid();
			if(basinFluid != null){
				return basinFluid;
			}else{
				return null;
			}
		}else{
			return null;
		}
		
	}
	
	/*
	 * ============================================================
	 * Because TConStuct's casting table didn't support FluidStack,
	 *  I build these method to process the output of pure metal.
	 *  -GKB 2016/4/5 14:31
	 * ============================================================
	 * */
	
	public static void registerTableCastingWithFluidStack(CastingRecipe recipe) {
		tableCastRegistryWithFluidStack.add(recipe);
	}
	
	public static CastingRecipe getTableCastingWithFluidStack(ItemStack cast, FluidStack fluidstack) {
	    for(CastingRecipe recipe : tableCastRegistryWithFluidStack) {
	      if(matches(cast, fluidstack, recipe)) {
	        return recipe;
	      }
	    }
	    return null;
	}
	
	public static boolean matches(ItemStack cast, FluidStack fluidStack, CastingRecipe recipe) {
	    if((cast == null && recipe.cast == null) || (recipe.cast != null && recipe.cast.matches(new ItemStack[]{cast}) != null)) {
	      return recipe.getFluid().isFluidEqual(fluidStack);
	    }
	    return false;
	}
	
}
