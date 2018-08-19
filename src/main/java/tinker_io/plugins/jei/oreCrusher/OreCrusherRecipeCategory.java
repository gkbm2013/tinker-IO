package tinker_io.plugins.jei.oreCrusher;

import com.google.common.collect.Lists;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import tinker_io.TinkerIO;
import tinker_io.registry.BlockRegistry;
import tinker_io.registry.ItemRegistry;

import java.util.List;

public class OreCrusherRecipeCategory implements IRecipeCategory {

    public static String CATEGORY = TinkerIO.MOD_ID + ":" + "ore_crusher";
    private static ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(TinkerIO.MOD_ID, "textures/gui/jei/ore_crusher_jei_recipe.png");

    private final IDrawable background;
    protected final IDrawableAnimated arrow;

    public OreCrusherRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(BACKGROUND_TEXTURE, 0, 0, 140, 60);

        IDrawableStatic arrowDrawable = guiHelper.createDrawable(BACKGROUND_TEXTURE, 142, 0, 24, 17);
        this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public String getUid() {
        return CATEGORY;
    }

    @Override
    public String getTitle() {
        return I18n.format(BlockRegistry.fuelInputMachine.getUnlocalizedName() + ".name");
    }

    @Override
    public String getModName() {
        return TinkerIO.NAME;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, IRecipeWrapper iRecipeWrapper, IIngredients iIngredients) {
        IGuiItemStackGroup items = iRecipeLayout.getItemStacks();

        items.init(0, true, 58, 22);
        items.set(0, iIngredients.getInputs(ItemStack.class).get(0));
        //items.set(0, recipe.getInputs());

        items.init(1, false, 109, 22);
        items.set(1, iIngredients.getOutputs(ItemStack.class).get(0));

        items.set(iIngredients);

        List<ItemStack> speedUpg = Lists.newLinkedList();
        speedUpg.add(new ItemStack(ItemRegistry.speedUpgrade));

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

        ItemStack infUPG = new ItemStack(ItemRegistry.upgrade, 1, 6);

        items.init(5, false, 77, 42);
        items.set(5, infUPG);
    }
}
