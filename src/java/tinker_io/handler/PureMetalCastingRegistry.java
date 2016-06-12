package tinker_io.handler;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import tinker_io.registry.FluidRegister;
import tinker_io.registry.SmelteryRecipeRegistry;

public class PureMetalCastingRegistry {
	public static void registerPureMetalCasting(){
		String[] oreDicts = OreDictionary.getOreNames();
		
		long startTime = System.currentTimeMillis();
		
		for(int i = 0; i < oreDicts.length; i++){
			if(OreCrusherRecipe.isOreDicAccepted(oreDicts[i])){
				FluidStack fluidStack = new FluidStack(FluidRegister.pureMetal, Material.VALUE_Ingot, new NBTTagCompound());
				fluidStack.tag.setString("oreDic", oreDicts[i]);
				
				if(getOutput(fluidStack) != null){
					SmelteryRecipeRegistry.registerSmaetOutputCasting(getOutput(fluidStack), TinkerSmeltery.castIngot, fluidStack);
					//SmelteryRecipeRegistry.registerTableCasting(getOutput(fluidStack), casting, fluidStack);
				}
			}
		}
		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("[Tinker I/O]Pure Metal casting registry loaded. Cost "+totalTime+" ms");
		
	}
	
	private static ItemStack getOutput(FluidStack stack){
		if(stack == null){return null;}
		NBTTagCompound nbt = stack.tag;
		ItemStack oreStack = null;
		ItemStack resultStack = null;
		
		if(nbt != null){
			oreStack = getItemByOreDic(nbt.getString("oreDic"));
			if(oreStack != null){
				resultStack = FurnaceRecipes.instance().getSmeltingResult(oreStack);
			}
		}
		
		return resultStack;
	}
	
	private static ItemStack getItemByOreDic(String oreDic){
		List<ItemStack> oreStack = OreDictionary.getOres(oreDic);
		
		ItemStack result = null;
		
		if(!oreStack.isEmpty()){
			result = oreStack.get(0);
		}
		return result;
	}
}
