package tinker_io.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryHelper {

    public static NonNullList<ItemStack> getItemStacksFromOreDict(String oreDict) {
        int id = OreDictionary.getOreID(oreDict);
        String name = OreDictionary.getOreName(id);
        return OreDictionary.getOres(name);
    }

}
