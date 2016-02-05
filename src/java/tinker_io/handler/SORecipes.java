package tinker_io.handler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
	
	public ItemStack getCastingRecipes(FluidStack fluid, ItemStack itemStack){	
		ItemStack basin = new ItemStack(TinkerSmeltery.searedBlock ,1 ,2);
//		if(fluid != null && fluid.isFluidEqual(new FluidStack(TinkerSmeltery.moltenGlassFluid, FluidContainerRegistry.BUCKET_VOLUME)) && itemStack != null && itemStack.isItemEqual(basin)){
//			return new ItemStack(TinkerSmeltery.clearGlass ,1);
//		}
		
//		LiquidCasting tableCasting = TConstructRegistry.instance.getTableCasting();
//		CastingRecipe tableRecipe = tableCasting.getCastingRecipe(fluid, itemStack);
		CastingRecipe tableRecipe = TinkerRegistry.getTableCasting(basin, null);
		
		if(tableRecipe != null){
			return tableRecipe.getResult();
		}else{
			return null;
		}
	}
	
	public FluidStack getCastingFluidCost(FluidStack fluid, ItemStack itemStack){
		ItemStack basin = new ItemStack(TinkerSmeltery.searedBlock ,1 ,2);
//		if(fluid != null && fluid.isFluidEqual(new FluidStack(TinkerSmeltery.moltenGlassFluid, FluidContainerRegistry.BUCKET_VOLUME)) && itemStack != null && itemStack.isItemEqual(basin)){
//			return new FluidStack(TinkerSmeltery.moltenGlassFluid, FluidContainerRegistry.BUCKET_VOLUME);
//		}
		
		
//		LiquidCasting tableCasting = TinkerRegistry.getTableCasting();		
//		CastingRecipe tableRecipe = tableCasting.getCastingRecipe(fluid, itemStack);
		CastingRecipe tableRecipe = TinkerRegistry.getTableCasting(basin, null);
	
		if(tableRecipe != null){
			FluidStack tableFluid = tableRecipe.getFluid();
			
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
		if(Main.iguanas_support == false){
			return false;
		}
//		
//		ItemStack pattern = new ItemStack(TinkerSmeltery.metalPattern, 1, 0);
//		String patternString = pattern.toString();
//		String outputStirng = output.toString();
//		String model = patternString.split("@")[0];
//		
//		//System.out.println(patternString.split("@")[0]);
//		
//		String itemPatternString = patternString.split("@")[0];
//		String itemOutputStirngString = outputStirng.split("@")[0];
//		//System.out.println(itemPatternString);
//		//System.out.println(itemOutputStirngString);
//		
//		if(itemPatternString.equals(itemOutputStirngString)){
//			return true;
//		}
//		return false;
		Collection<Item> items = TinkerRegistry.getPatternItems();
		Item item = output.getItem();
		for (Item i : items) {
			if ( item.equals(i)) {
				return true;
			}
		}
		return false;
	}
	
	public FluidStack getBasinFluid(FluidStack fluid, ItemStack itemStack){
		
//		LiquidCasting basinCasting = TinkerRegistry.getBasinCasting();
//		CastingRecipe basinRecipe = basinCasting.getCastingRecipe(fluid, itemStack);
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
	
}
