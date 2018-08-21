package tinker_io.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class OreDictionaryHelper {

    public static NonNullList<ItemStack> getItemStacksFromOreDict(String oreDict) {
        int id = OreDictionary.getOreID(oreDict);
        String name = OreDictionary.getOreName(id);
        return OreDictionary.getOres(name);
    }

    public static String  getDisplayNameFromOreDict(String oreDicName) {
        String name = "";
        List<ItemStack> oreList = OreDictionary.getOres(oreDicName);
        if (!oreList.isEmpty()) {
            ItemStack oreItem = oreList.get(0);
            String oreName = oreItem.getDisplayName();
            name = oreName;
        }
        return name;
    }

}
