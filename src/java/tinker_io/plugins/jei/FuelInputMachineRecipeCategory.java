package tinker_io.plugins.jei;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import tinker_io.main.Main;
import tinker_io.registry.ItemRegistry;

public class FuelInputMachineRecipeCategory implements IRecipeCategory<FuelInputMachineRecipeWrapper> {

	public static String CATEGORY = Main.MODID + ":" + "fuel_input_mechine";
	public static ResourceLocation backgroundLocation = new ResourceLocation(Main.MODID, "textures/gui/jei/fuel_input_mechine_jei_recipe.png");
	
	protected final IDrawable background;
	protected final IDrawableAnimated arrow;
	
	protected FuelInputMachineRecipeCategory(IGuiHelper guiHelper) {
		this.background = guiHelper.createDrawable(backgroundLocation, 0, 0, 140, 60);
		
		IDrawableStatic arrowDrawable = guiHelper.createDrawable(backgroundLocation, 142, 23, 14, 14);
		this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 100, IDrawableAnimated.StartDirection.TOP, true);
	}
	
	@Nonnull
	@Override
	public String getUid() {
		return CATEGORY;
	}

	@Nonnull
	@Override
	public String getTitle() {
		return I18n.format("tile.fuel_input_machine.name");
	}

	@Nonnull
	@Override
	public IDrawable getBackground() {
		return this.background;
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		arrow.draw(minecraft, 81, 24);
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, FuelInputMachineRecipeWrapper recipe, IIngredients ingredients) {
		IGuiItemStackGroup items = recipeLayout.getItemStacks();
		
		items.init(0, true, 57, 22);
		items.set(0, ingredients.getInputs(ItemStack.class).get(0));
	    //items.setFromRecipe(0, recipe.getInputs());
	    
	    List<ItemStack> speedUpg = Lists.newLinkedList();
	    speedUpg.add(new ItemStack(ItemRegistry.SpeedUPG));
	    
	    items.init(2, false, 3, 22);
	    items.set(2, speedUpg);
	    //items.setFromRecipe(2, speedUpg);
	}

	@Override
	public IDrawable getIcon() {
		return null;
	}

	/*@Override
	@Deprecated
	public void setRecipe(IRecipeLayout recipeLayout, FuelInputMachineRecipeWrapper recipeWrapper) {
		
	}*/

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		return new ArrayList<String>();
	}
	
	/*@Override
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
		if(recipeWrapper instanceof FuelInputMachineRecipeWrapper){
			FuelInputMachineRecipeWrapper recipe = (FuelInputMachineRecipeWrapper) recipeWrapper;
			IGuiItemStackGroup items = recipeLayout.getItemStacks();
			
			items.init(0, true, 57, 22);
			items.set(0, recipe.getInputs());
		    //items.setFromRecipe(0, recipe.getInputs());
		    
		    List<ItemStack> speedUpg = Lists.newLinkedList();
		    speedUpg.add(new ItemStack(ItemRegistry.SpeedUPG));
		    
		    items.init(2, false, 3, 22);
		    items.set(2, speedUpg);
		    //items.setFromRecipe(2, speedUpg);
		}
		
	}*/

}
