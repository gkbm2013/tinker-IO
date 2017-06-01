package tinker_io.handler;

import java.util.Collection;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.smeltery.Cast;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.library.smeltery.ICastingRecipe;
import slimeknights.tconstruct.library.utils.ListUtil;
import slimeknights.tconstruct.smeltery.item.CastCustom;
//import slimeknights.tconstruct.library.crafting.LiquidCasting;

public class SORecipe{
	
	private static List<CastingRecipe> tableCastRegistryWithFluidStack = Lists.newLinkedList();
	
	public ItemStack getCastingRecipes(FluidStack fluid, ItemStack itemStack){	
		/*ItemStack basin = new ItemStack(TinkerSmeltery.searedBlock ,1 ,2);
		if(fluid != null && fluid.isFluidEqual(new FluidStack(TinkerSmeltery.moltenGlassFluid, FluidContainerRegistry.BUCKET_VOLUME)) && itemStack != null && itemStack.isItemEqual(basin)){
			return new ItemStack(TinkerSmeltery.clearGlass ,1);
		}*/
		
		ICastingRecipe itableRecipe = TinkerRegistry.getTableCasting(itemStack, fluid.getFluid());
		CastingRecipe tableRecipeWithFluidStack = getTableCastingWithFluidStack(itemStack, fluid);
		
		if(itableRecipe != null && itableRecipe instanceof CastingRecipe){
			CastingRecipe tableRecipe = (CastingRecipe)itableRecipe;
			if(tableRecipe.getResult() != null){
				return tableRecipe.getResult();
			}
		}else if(tableRecipeWithFluidStack != null){
			return tableRecipeWithFluidStack.getResult();
		}
		return null;
	}
	
	public FluidStack getCastingFluidCost(FluidStack fluid, ItemStack itemStack){
		//ItemStack basin = new ItemStack(TinkerSmeltery.searedBlock ,1 ,2);
//		if(fluid != null && fluid.isFluidEqual(new FluidStack(TinkerSmeltery.moltenGlassFluid, FluidContainerRegistry.BUCKET_VOLUME)) && itemStack != null && itemStack.isItemEqual(basin)){
//			return new FluidStack(TinkerSmeltery.moltenGlassFluid, FluidContainerRegistry.BUCKET_VOLUME);
//		}
		
		
		//Fluid
		
		ICastingRecipe itableRecipe = TinkerRegistry.getTableCasting(itemStack, fluid.getFluid());
		CastingRecipe tableRecipeWithFluidStack = getTableCastingWithFluidStack(itemStack, fluid);
		
		CastingRecipe useAbleTableRecipe = null;
		if(itableRecipe != null && itableRecipe instanceof CastingRecipe){
			CastingRecipe tableRecipe = (CastingRecipe) itableRecipe;
			if(tableRecipe.getResult() != null){
				useAbleTableRecipe = tableRecipe;
			}
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
	
	public boolean isConsumeCast(FluidStack fluid, ItemStack itemStack){
		ICastingRecipe itableRecipe = TinkerRegistry.getTableCasting(itemStack, fluid.getFluid());
		CastingRecipe tableRecipeWithFluidStack = getTableCastingWithFluidStack(itemStack, fluid);
		
		if(itableRecipe != null && itableRecipe instanceof CastingRecipe){
			CastingRecipe tableRecipe = (CastingRecipe) itableRecipe;
			if(tableRecipe.getResult() != null){
				return tableRecipe.consumesCast();
			}
		}else if(tableRecipeWithFluidStack != null){
			return tableRecipeWithFluidStack.consumesCast();
		}
		return false;
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
		if(output.getItem() instanceof CastCustom || output.getItem() instanceof Cast){
			return true;
		}
		return false;
	}
	
	/*public boolean isPatternWithIguana(ItemStack itemStack){
		if(Main.iguanas_support == false){
			return false;
		}
		return isPattern(itemStack);
	}*/
	
	public FluidStack getBasinFluidCost(FluidStack fluid, ItemStack itemStack){
		ICastingRecipe ibasinRecipe = TinkerRegistry.getBasinCasting(itemStack, fluid.getFluid());

		if(ibasinRecipe != null && ibasinRecipe instanceof CastingRecipe){
			CastingRecipe basinRecipe = (CastingRecipe)ibasinRecipe;
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
	
	public ItemStack getBasinResult(FluidStack fluid, ItemStack itemStack){
		ICastingRecipe ibasinRecipe = TinkerRegistry.getBasinCasting(itemStack, fluid.getFluid());

		if(ibasinRecipe != null && ibasinRecipe instanceof CastingRecipe){
			CastingRecipe basinRecipe = (CastingRecipe)ibasinRecipe;
			ItemStack basinResult = basinRecipe.getResult();
			return basinResult;
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
	    if((cast == null && recipe.cast == null) || (recipe.cast != null && recipe.cast.matches(ListUtil.getListFrom(cast)) != null)) {
	      return recipe.getFluid().isFluidEqual(fluidStack);
	    }
	    return false;
	}
	
	public static List<CastingRecipe> getCastingRecipeWithNBT(){
		return tableCastRegistryWithFluidStack;
	}
	
}
