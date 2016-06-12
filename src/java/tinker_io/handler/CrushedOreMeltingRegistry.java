package tinker_io.handler;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.mantle.util.RecipeMatch.ItemCombination;
import slimeknights.tconstruct.library.materials.Material;
import tinker_io.registry.FluidRegister;
import tinker_io.registry.ItemRegistry;
import tinker_io.registry.SmelteryRecipeRegistry;

public class CrushedOreMeltingRegistry {
	
	public static void registerCrushedOre(){
		String[] oreDicts = OreDictionary.getOreNames();
		
		long startTime = System.currentTimeMillis();
		
		for(int i = 0; i < oreDicts.length; i++){
			if(OreCrusherRecipe.isOreDicAccepted(oreDicts[i])){
				Fluid pureMetal = FluidRegister.pureMetal;
				FluidStack pureMetalStack = new FluidStack(pureMetal, Material.VALUE_Ingot);
				
				ItemStack crushedOreStack = new ItemStack(ItemRegistry.CrushedOre);
				
				crushedOreStack.setTagCompound(new NBTTagCompound());
				crushedOreStack.getTagCompound().setString("oreDic", oreDicts[i]);
				
				SmelteryRecipeRegistry.registerMeltingWithNBT(crushedOreStack, itemNBTtoFluidNBT(pureMetalStack, crushedOreStack), 350);
			}
		}
		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("[Tinker I/O]Crushed Ore melting registry loaded. Cost "+totalTime+" ms");
	}
	
	private static FluidStack itemNBTtoFluidNBT(FluidStack fluidStack, ItemStack itemStack){		
		NBTTagCompound fluidNBT = new NBTTagCompound();
		NBTTagCompound itemNBT = null;
		
		ItemStack itemInputStack = null;		
		ItemCombination itemCombination = new ItemCombination(1, itemStack);
		
		List<ItemStack> itemInput = itemCombination.getInputs();
		
		if(!itemInput.isEmpty()){
			itemInputStack = itemInput.get(0);
			itemNBT = itemInputStack.getTagCompound();
		}
		
		if(itemNBT != null){
			fluidNBT = (NBTTagCompound) itemNBT.copy();
			fluidStack = new FluidStack(fluidStack.getFluid(), fluidStack.amount, fluidNBT);
		}
		
		return fluidStack;
	}

}
