package tinker_io.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tinker_io.registry.ItemRegistry;

public class CrushedOreHelper {

    public static final String TAG_ORE_DICT = "oreDict";

    public static ItemStack getCrushedOre(String oreDict, int amount) {
        ItemStack itemStack = new ItemStack(ItemRegistry.crushedOre, amount);
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString(TAG_ORE_DICT, oreDict);
        itemStack.setTagCompound(nbt);
        return itemStack;
    }

}
