package tinker_io.plugins.jei;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Triple;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.smeltery.Cast;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import tinker_io.gui.FIMGui;
import tinker_io.gui.OreCrusherGui;
import tinker_io.gui.SOGui;
import tinker_io.handler.OreCrusherRecipe;
import tinker_io.handler.SORecipe;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {

	public static IJeiHelpers jeiHelpers;

	@Override
	public void register(IModRegistry registry) {
		jeiHelpers = registry.getJeiHelpers();
	    IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		
		OreCrusherRecipeCategory OCCategory = new OreCrusherRecipeCategory(guiHelper);
		OreCrusherRecipeHandler OCRecipeHandler = new OreCrusherRecipeHandler();
		
		SmartOutputRecipeCategory SOCategory = new SmartOutputRecipeCategory(guiHelper);
		SmartOutputRecipeHandler SORecipeHandler = new SmartOutputRecipeHandler();
		
		FuelInputMachineRecipeCategory FIMCategory = new FuelInputMachineRecipeCategory(guiHelper);
		FuelInputMachineRecipeHandler FIMRecipeHandler = new FuelInputMachineRecipeHandler();
		
		registry.addRecipeCategories(OCCategory, SOCategory, FIMCategory);
		registry.addRecipeHandlers(OCRecipeHandler, SORecipeHandler, FIMRecipeHandler);
		
		String[] oreDicArray = OreDictionary.getOreNames();
		List<String> oreDicList = Lists.newArrayList(oreDicArray);
		
		//Fuel Input Machine
				List<FuelInputMachineRecipeWrapper> FIMrecipeList = Lists.newLinkedList();
				Item.itemRegistry.forEach(item ->{
					ItemStack itemstack = new ItemStack(item);
					if(TileEntityFurnace.isItemFuel(itemstack)){
						FIMrecipeList.add(new FuelInputMachineRecipeWrapper(itemstack));
					}
				});
				registry.addRecipes(FIMrecipeList);
		
		//Ore Crusher
		List<OreCrusherRecipeWrapper> OCrecipeList = Lists.newLinkedList();
		oreDicList.stream().forEach(oreDic ->{
			if(OreCrusherRecipe.isOreDicAccepted(oreDic)){
				OCrecipeList.add(new OreCrusherRecipeWrapper(oreDic));
			}
		});
		registry.addRecipes(OCrecipeList);
		
		//Smart Output
		Map<Triple<Item, Item, Fluid>, List<ItemStack >> castDict = Maps.newHashMap();
		
	    for(CastingRecipe recipe : TinkerRegistry.getAllTableCastingRecipes()) {
	        if(recipe.cast != null && recipe.getResult() != null && recipe.getResult().getItem() instanceof Cast) {
	        	Triple<Item, Item, Fluid> output = Triple.of(recipe.getResult().getItem(), Cast.getPartFromTag(recipe.getResult()), recipe.getFluid().getFluid());
	        	if(!castDict.containsKey(output)) {
	        		
	        		// recipe for the cast doesn't exist yet. create list and recipe and add it
	        		List<ItemStack> list = Lists.newLinkedList();
	        		castDict.put(output, list);
	        		registry.addRecipes(ImmutableList.of(new SmartOutputRecipeWrapper(list, recipe)));
	        	}
	        	// add the item to the list
	        	castDict.get(output).addAll(recipe.cast.getInputs());
	        }else{
	          registry.addRecipes(ImmutableList.of(new SmartOutputRecipeWrapper(recipe)));
	        }
	      }
	    
	    List<CastingRecipe> castingRecipeWithNBT = SORecipe.getCastingRecipeWithNBT();
	    castingRecipeWithNBT.stream()
	    	.forEach(nbtRecipe -> registry.addRecipes(ImmutableList.of(new SmartOutputRecipeWrapper(nbtRecipe))));
	    
	  //Click area
	    registry.addRecipeClickArea(SOGui.class, 94, 34, 24, 15, SmartOutputRecipeCategory.CATEGORY);
	    registry.addRecipeClickArea(OreCrusherGui.class, 82, 35, 24, 15, OreCrusherRecipeCategory.CATEGORY);
	    registry.addRecipeClickArea(FIMGui.class, 102, 35, 18, 18, FuelInputMachineRecipeCategory.CATEGORY);
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {}

}
