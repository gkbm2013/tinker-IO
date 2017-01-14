package tinker_io.plugins.jei;

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
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.resources.I18n;
import tinker_io.main.Main;
import tinker_io.registry.ItemRegistry;

public class OreCrusherRecipeCategory implements IRecipeCategory<OreCrusherRecipeWrapper> {

	public static String CATEGORY = Main.MODID + ":" + "ore_crusher";
	public static ResourceLocation backgroundLocation = new ResourceLocation(Main.MODID, "textures/gui/jei/Ore_Crusher_jei_recipe.png");
	
	protected final IDrawable background;
	protected final IDrawableAnimated arrow;
	
	protected OreCrusherRecipeCategory(IGuiHelper guiHelper) {
		this.background = guiHelper.createDrawable(backgroundLocation, 0, 0, 140, 60);
		
		IDrawableStatic arrowDrawable = guiHelper.createDrawable(backgroundLocation, 142, 0, 24, 17);
		this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);
	}
	
	@Nonnull
	@Override
	public String getUid() {
		return CATEGORY;
	}

	@Nonnull
	@Override
	public String getTitle() {
		return I18n.format("tile.Ore_Crusher.name", new Object[0]);
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
		arrow.draw(minecraft, 79, 24);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, OreCrusherRecipeWrapper recipe, IIngredients ingredients) {
		IGuiItemStackGroup items = recipeLayout.getItemStacks();
		
		items.init(0, true, 58, 22);
	    items.set(0, ingredients.getInputs(ItemStack.class).get(0));
	    //items.set(0, recipe.getInputs());
		
		items.init(1, false, 109, 22);
	    items.set(1, ingredients.getOutputs(ItemStack.class));
	    
	    items.set(ingredients);
	    
	    List<ItemStack> speedUpg = Lists.newLinkedList();
	    speedUpg.add(new ItemStack(ItemRegistry.SpeedUPG));
	    
	    items.init(2, false, 34, 22);
	    items.set(2, speedUpg);
	    
	    List<ItemStack> enchantedBook = Lists.newLinkedList();
	    
	    ItemStack fortune1 = new ItemStack(Items.ENCHANTED_BOOK);
	    fortune1.addEnchantment(Enchantment.REGISTRY.getObjectById(35), 1);
	    enchantedBook.add(fortune1);
	    
	    ItemStack fortune2 = new ItemStack(Items.ENCHANTED_BOOK);
	    fortune2.addEnchantment(Enchantment.REGISTRY.getObjectById(35), 2);
	    enchantedBook.add(fortune2);
	    
	    ItemStack fortune3 = new ItemStack(Items.ENCHANTED_BOOK);
	    fortune3.addEnchantment(Enchantment.REGISTRY.getObjectById(35), 3);
	    enchantedBook.add(fortune3);
    	
    	items.init(3, false, 40, 42);
	    items.set(3, enchantedBook);
	    
	    items.init(4, false, 58, 42);
	    items.set(4, enchantedBook);
	    
	    ItemStack infUPG = new ItemStack(ItemRegistry.Upgrade, 1, 6);
	    
	    items.init(5, false, 77, 42);
	    items.set(5, infUPG);
	}

	@Override
	public IDrawable getIcon() {
		return null;
	}
	
	/*@Override
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
		if(recipeWrapper instanceof OreCrusherRecipeWrapper){
			OreCrusherRecipeWrapper recipe = (OreCrusherRecipeWrapper) recipeWrapper;
			IGuiItemStackGroup items = recipeLayout.getItemStacks();
			
			items.init(0, true, 58, 22);
		    items.setFromRecipe(0, recipe.getInputs());
		    //items.set(0, recipe.getInputs());
			
			items.init(1, false, 109, 22);
		    items.setFromRecipe(1, recipe.getOutputs());
		    
		    List<ItemStack> speedUpg = Lists.newLinkedList();
		    speedUpg.add(new ItemStack(ItemRegistry.SpeedUPG));
		    
		    items.init(2, false, 34, 22);
		    items.setFromRecipe(2, speedUpg);
		    
		    List<ItemStack> enchantedBook = Lists.newLinkedList();
		    
		    ItemStack fortune1 = new ItemStack(Items.ENCHANTED_BOOK);
		    fortune1.addEnchantment(Enchantment.REGISTRY.getObjectById(35), 1);
		    enchantedBook.add(fortune1);
		    
		    ItemStack fortune2 = new ItemStack(Items.ENCHANTED_BOOK);
		    fortune2.addEnchantment(Enchantment.REGISTRY.getObjectById(35), 2);
		    enchantedBook.add(fortune2);
		    
		    ItemStack fortune3 = new ItemStack(Items.ENCHANTED_BOOK);
		    fortune3.addEnchantment(Enchantment.REGISTRY.getObjectById(35), 3);
		    enchantedBook.add(fortune3);
	    	
	    	items.init(3, false, 40, 42);
		    items.setFromRecipe(3, enchantedBook);
		    
		    items.init(4, false, 58, 42);
		    items.setFromRecipe(4, enchantedBook);
		    
		    ItemStack infUPG = new ItemStack(ItemRegistry.Upgrade, 1, 6);
		    
		    items.init(5, false, 77, 42);
		    items.setFromRecipe(5, infUPG);
		}
		
	}*/

	@Override
	@Deprecated
	public void setRecipe(IRecipeLayout recipeLayout, OreCrusherRecipeWrapper recipeWrapper) {
		// TODO 自動產生的方法 Stub
		
	}
}
