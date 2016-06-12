package tinker_io.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.mantle.util.RecipeMatch.ItemCombination;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.library.smeltery.MeltingRecipe;
import tinker_io.handler.CrushedOreMeltingRegistry;
import tinker_io.handler.PureMetalCastingRegistry;
import tinker_io.handler.SORecipe;

public class SmelteryRecipeRegistry {
	
	public int VALUE_Ingot = Material.VALUE_Ingot;
	
	public static void registerMeltingCasting() {
		
		//Melting
		CrushedOreMeltingRegistry.registerCrushedOre();
		//TinkerRegistry.registerMelting(new MeltingRecipe(ItemCombination.ofNBT(crushedOreStack, Material.VALUE_Ingot), itemNBTtoFluidNBT(pureMetalStack, crushedOreStack), 350));
		
		//Cast
		PureMetalCastingRegistry.registerPureMetalCasting();
		
	}
	
	public static void registerMeltingWithNBT(ItemStack inputItem, FluidStack outputFluidStack, int meltingTime){
		TinkerRegistry.registerMelting(new MeltingRecipe(ItemCombination.ofNBT(inputItem, outputFluidStack.amount), outputFluidStack, meltingTime));
	}
	
	public static void registerMelting(Item item, Fluid fluid, int outputAmount, int meltingTime){
		TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(item, outputAmount), fluid, meltingTime));
	}
	
	public static void registerTableCasting(ItemStack output, ItemStack cast, FluidStack fluid){
		TinkerRegistry.registerTableCasting(new CastingRecipe(output, RecipeMatch.of(cast.getItem()), fluid, false, true));
		//ItemStack output, RecipeMatch cast, FluidStack fluid, boolean consumesCast, boolean switchOutputs
	}
	
	public static void registerSmaetOutputCasting(ItemStack output, ItemStack cast, FluidStack fluid){
		SORecipe.registerTableCastingWithFluidStack(new CastingRecipe(output, RecipeMatch.of(cast.getItem()), fluid, false, true));
		//ItemStack output, RecipeMatch cast, FluidStack fluid, boolean consumesCast, boolean switchOutputs
	}
	
}
