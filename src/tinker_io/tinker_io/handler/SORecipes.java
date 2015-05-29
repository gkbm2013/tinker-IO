package tinker_io.handler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.CastingRecipe;
import tconstruct.library.crafting.LiquidCasting;

public class SORecipes{
	public ItemStack getCastingRecipes(FluidStack fluid, ItemStack itemStack, boolean hasPowered){
		if(!hasPowered){
			LiquidCasting tableCasting = TConstructRegistry.instance.getTableCasting();
			CastingRecipe tableRecipe = tableCasting.getCastingRecipe(fluid, itemStack);
			
			if(tableRecipe != null){
				return tableRecipe.getResult();
			}else{
				return null;
			}
		}else{
			return null;
		}
		
	}
	
	public ItemStack getCastingRecipes(FluidStack fluid, ItemStack itemStack){
		LiquidCasting tableCasting = TConstructRegistry.instance.getTableCasting();
		CastingRecipe tableRecipe = tableCasting.getCastingRecipe(fluid, itemStack);
		
		if(tableRecipe != null){
			return tableRecipe.getResult();
		}else{
			return null;
		}
	}
	
	public FluidStack getCastingFluid(FluidStack fluid, ItemStack itemStack){
		
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
	
	
	
	public ItemStack getBasinRecipes(FluidStack fluid, ItemStack itemStack, boolean hasPowered){
		if(hasPowered){
			LiquidCasting basinCasting = TConstructRegistry.getBasinCasting();
			CastingRecipe basinRecipe = basinCasting.getCastingRecipe(fluid, itemStack);
			if(basinRecipe != null){
				return basinRecipe.getResult();
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	public FluidStack getBasinFluid(FluidStack fluid, ItemStack itemStack){
		
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
		
	}
	
}
