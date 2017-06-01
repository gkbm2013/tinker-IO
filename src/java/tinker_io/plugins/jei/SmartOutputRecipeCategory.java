package tinker_io.plugins.jei;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.materials.Material;
import tinker_io.main.Main;
import tinker_io.registry.ItemRegistry;

public class SmartOutputRecipeCategory implements IRecipeCategory<SmartOutputRecipeWrapper> {
	public static String CATEGORY = Main.MODID + ":" + "smart_output";
	public static ResourceLocation backgroundLocation = new ResourceLocation(Main.MODID, "textures/gui/jei/smart_output_jei_recipe.png");
	
	protected final IDrawable background;
	protected final IDrawableAnimated arrow;
	 
	protected SmartOutputRecipeCategory(IGuiHelper guiHelper) {
		this.background = guiHelper.createDrawable(backgroundLocation, 0, 0, 149, 63);
		
		IDrawableStatic arrowDrawable = guiHelper.createDrawable(backgroundLocation, 150, 0, 24, 17);
		this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 30, IDrawableAnimated.StartDirection.LEFT, false);
	}
	
	@Nonnull
	@Override
	public String getUid() {
		return CATEGORY;
	}
	
	@Nonnull
	@Override
	public String getTitle() {
		return I18n.format("tile.smart_output.name");
	}

	@Nonnull
	@Override
	public IDrawable getBackground() {
		return this.background;
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		arrow.draw(minecraft, 71, 21);
	}

	/**
	 * Modified from slimeknights.tconstruct.plugin.jei.CastingRecipeCategory;
	 * */
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, SmartOutputRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup items = recipeLayout.getItemStacks();
	    IGuiFluidStackGroup fluids = recipeLayout.getFluidStacks();
		
	    List<FluidStack> input = ingredients.getInputs(FluidStack.class).get(0);
	    //List<List<ItemStack>> castsList = ingredients.getInputs(ItemStack.class);

	    /*
	     * INPUT
	     */
	    
	    /*List<ItemStack> casts = null;
	    if(castsList.size() > 0) {
	    	casts = castsList.get(0);
	    }*/

	    int cap = input.get(0).amount;

	    items.init(0, true, 58, 25);
	    items.init(1, false, 113, 24);
	    items.set(ingredients);

	    fluids.init(0, true, 3, 2, 12, 52, Material.VALUE_Block, false, null);
	    fluids.set(ingredients);
	    
	    fluids.init(1, true, 3, 2, 12, 52, cap, false, null);
	    // otherwise it tries to get the second input fluidstack
	    fluids.set(1, input);

	    items.init(0, true, 44, 19);
	    items.init(1, false, 104, 20);
	    items.set(ingredients);
	    
	    List<ItemStack> upg = Lists.newLinkedList();
	    upg.add(new ItemStack(ItemRegistry.Upgrade, 1, 1));
	    upg.add(new ItemStack(ItemRegistry.Upgrade, 1, 2));
	    upg.add(new ItemStack(ItemRegistry.Upgrade, 1, 3));
	    upg.add(new ItemStack(ItemRegistry.Upgrade, 1, 4));
	    upg.add(new ItemStack(ItemRegistry.Upgrade, 1, 5));

	    items.init(2, false, 129, 11);
	    items.set(2, upg);

	    items.init(3, false, 129, 29);
	    if(!recipeWrapper.isBasin){
	    	items.set(3, upg);
	    }else{
	    	items.set(3, new ItemStack(ItemRegistry.Upgrade, 1, 7));
	    }


	    fluids.init(0, true, 3, 2, 12, 52, Material.VALUE_Block, false, null);
	}
	
	@Override
	public IDrawable getIcon() {
		return null;
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		return new ArrayList<String>();
	}
	
	/*@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper) {
		 if(recipeWrapper instanceof SmartOutputRecipeWrapper) {
		      SmartOutputRecipeWrapper recipe = (SmartOutputRecipeWrapper) recipeWrapper;
		      IGuiItemStackGroup items = recipeLayout.getItemStacks();
		      IGuiFluidStackGroup fluids = recipeLayout.getFluidStacks();

		      List<ItemStack> upg = Lists.newLinkedList();
		      upg.add(new ItemStack(ItemRegistry.Upgrade, 1, 1));
		      upg.add(new ItemStack(ItemRegistry.Upgrade, 1, 2));
		      upg.add(new ItemStack(ItemRegistry.Upgrade, 1, 3));
		      upg.add(new ItemStack(ItemRegistry.Upgrade, 1, 4));
		      upg.add(new ItemStack(ItemRegistry.Upgrade, 1, 5));
		      

		      items.init(0, true, 44, 19);
		      items.setFromRecipe(0, recipe.getInputs());

		      items.init(1, false, 104, 20);
		      items.setFromRecipe(1, recipe.getOutputs());
		      
		      items.init(2, false, 129, 11);
		      items.setFromRecipe(2, upg);
		      
		      items.init(3, false, 129, 29);
		      if(!recipe.isBasin){
		    	  items.setFromRecipe(3, upg);
		      }else{
		    	  items.setFromRecipe(3, new ItemStack(ItemRegistry.Upgrade, 1, 7));
		      }
		      

		      fluids.init(0, true, 3, 2, 12, 52, Material.VALUE_Block, false, null);
		      fluids.set(0, recipe.getFluidInputs());
		    }
	}*/
}
