package tinker_io.handler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.CastingRecipe;
import tconstruct.library.crafting.LiquidCasting;
import tconstruct.smeltery.TinkerSmeltery;
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
	
	public FluidStack getCastingFluid(FluidStack fluid, ItemStack itemStack){
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
