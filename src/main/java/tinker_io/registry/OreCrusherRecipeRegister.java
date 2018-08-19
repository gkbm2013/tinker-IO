package tinker_io.registry;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import tinker_io.helper.CrushedOreHelper;
import tinker_io.helper.OreCrusherRecipe;
import tinker_io.helper.OreDictionaryHelper;

import java.util.List;

public class OreCrusherRecipeRegister {

    public static List<OreCrusherRecipe> oreCrusherRecipes = Lists.newLinkedList();
    public static List<String> oreCrusherBanList = Lists.newLinkedList();

    public static void init() {
        banRecipe();
        loadFromOreDict();
        loadOtherRecipe();
    }

    public static void banRecipe() {
        addBanOreDict("oreDiamond");
        addBanOreDict("oreEmerald");
        addBanOreDict("oreRedstone");
        addBanOreDict("oreQuartz");
        addBanOreDict("oreCoal");
        addBanOreDict("oreLapis");
        addBanOreDict("oreApatite");
        addBanOreDict("oreBlazium");
        addBanOreDict("oreBurnium");
        addBanOreDict("oreEndimium");
        addBanOreDict("oreJade");
        addBanOreDict("oreLanite");
        addBanOreDict("oreMeurodite");
        addBanOreDict("oreOnyx");
        addBanOreDict("oreQuartz");
        addBanOreDict("oreRadiantQuartz");
        addBanOreDict("oreRuby");
        addBanOreDict("oreSalt");
        addBanOreDict("oreSapphire");
        addBanOreDict("oreSoul");
        addBanOreDict("oreSunstone");
        addBanOreDict("oreCrystal");
        addBanOreDict("oreZimphnode");
        addBanOreDict("oreSalt");
        addBanOreDict("oreKnightslime");
        addBanOreDict("orePigiron");
        addBanOreDict("oreManyullyn");
    }

    private static void loadFromOreDict() {
        String[] oreDicts = OreDictionary.getOreNames();
        for (String oreDict : oreDicts) {
            if (!oreCrusherBanList.contains(oreDict) && oreDict.substring(0, 3).equals("ore")) {
                if(OreDictionaryHelper.getItemStacksFromOreDict(oreDict).size() > 0) {
                    ItemStack itemStack = CrushedOreHelper.getCrushedOre(oreDict, 1);
                    oreCrusherRecipes.add(new OreCrusherRecipe(oreDict, itemStack));
                }
            }
        }
    }

    private static void addBanOreDict(String oreDict) {
        oreCrusherBanList.add(oreDict);
    }

    private static void loadOtherRecipe() {
        addOreCrusherRecipe("oreCobalt");
        addOreCrusherRecipe("oreArdite");
    }

    public static void addOreCrusherRecipe(String oreDict) {
        OreCrusherRecipe recipe = new OreCrusherRecipe(oreDict, CrushedOreHelper.getCrushedOre(oreDict, 1));
        addOreCrusherRecipe(recipe);
    }

    public static void addOreCrusherRecipe(OreCrusherRecipe oreCrusherRecipe) {
        oreCrusherRecipes.add(oreCrusherRecipe);
    }

    public static OreCrusherRecipe find(ItemStack oreIn) {
        for(OreCrusherRecipe recipe : oreCrusherRecipes) {
            if(recipe.match(oreIn)) {
                return recipe;
            }
        }
        return OreCrusherRecipe.EMPTY;
    }

}
