package tinker_io.handler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.CastingRecipe;
import tconstruct.library.crafting.LiquidCasting;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.tools.TinkerTools;
import tinker_io.main.Main;
import tinker_io.mainRegistry.ItemRegistry;

public class SORecipes{
	
	public ItemStack getCastingRecipes(FluidStack fluid, ItemStack itemStack){	
		ItemStack basin = new ItemStack(TinkerSmeltery.searedBlock ,1 ,2);
		if(fluid != null && fluid.isFluidEqual(new FluidStack(TinkerSmeltery.moltenGlassFluid, FluidContainerRegistry.BUCKET_VOLUME)) && itemStack != null && itemStack.isItemEqual(basin)){
			return new ItemStack(TinkerSmeltery.clearGlass ,1);
		}
		
		LiquidCasting tableCasting = TConstructRegistry.instance.getTableCasting();
		CastingRecipe tableRecipe = tableCasting.getCastingRecipe(fluid, itemStack);
		
		if(tableRecipe != null){
			return tableRecipe.getResult();
		}else{
			return null;
		}
	}
	
	public FluidStack getCastingFluidCost(FluidStack fluid, ItemStack itemStack){
		ItemStack basin = new ItemStack(TinkerSmeltery.searedBlock ,1 ,2);
		if(fluid != null && fluid.isFluidEqual(new FluidStack(TinkerSmeltery.moltenGlassFluid, FluidContainerRegistry.BUCKET_VOLUME)) && itemStack != null && itemStack.isItemEqual(basin)){
			return new FluidStack(TinkerSmeltery.moltenGlassFluid, FluidContainerRegistry.BUCKET_VOLUME);
		}
		
		
		LiquidCasting tableCasting = TConstructRegistry.instance.getTableCasting();		
		CastingRecipe tableRecipe = tableCasting.getCastingRecipe(fluid, itemStack);
		
		if(tableRecipe != null){
			FluidStack tableFluid = tableRecipe.castingMetal;
			
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
		
		ItemStack pattern = new ItemStack(TinkerSmeltery.metalPattern, 1, 0);
		String patternString = pattern.toString();
		String outputStirng = output.toString();
		String model = "1xitem.tconstruct.MetalPattern";
		
		String itemPatternString = patternString.substring(0, model.length());
		String itemOutputStirngString = outputStirng.substring(0, model.length());
		
		
		if(itemPatternString.equals(itemOutputStirngString)){
			return true;
		}
		return false;
	}
	
	/*public FluidStack getBasinFluid(FluidStack fluid, ItemStack itemStack){
		
		LiquidCasting basinCasting = TConstructRegistry.getBasinCasting();
		CastingRecipe basinRecipe = basinCasting.getCastingRecipe(fluid, itemStack);
		
		if(basinRecipe != null){
			FluidStack basinFluid = basinRecipe.castingMetal;
			if(basinFluid != null){
				return basinFluid;
			}else{
				return null;
			}
		}else{
			return null;
		}
		
	}*/
	
}
