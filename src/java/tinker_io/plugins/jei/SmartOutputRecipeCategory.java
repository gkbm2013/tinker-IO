package tinker_io.plugins.jei;

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
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.materials.Material;
import tinker_io.main.Main;
import tinker_io.registry.ItemRegistry;

public class SmartOutputRecipeCategory implements IRecipeCategory {
	public static String CATEGORY = Main.MODID + ":" + "smart_output";
	public static ResourceLocation backgroundLocation = new ResourceLocation(Main.MODID, "textures/gui/jei/Smart_Output_jei_recipe.png");
	
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
	public void drawExtras(Minecraft minecraft) {}

	@Override
	public void drawAnimations(Minecraft minecraft) {
		arrow.draw(minecraft, 71, 21);
	}

	@Override
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
	}

}
