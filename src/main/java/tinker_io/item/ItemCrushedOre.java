package tinker_io.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import tinker_io.helper.CrushedOreColorHelper;
import tinker_io.helper.CrushedOreHelper;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

public class ItemCrushedOre extends ItemBase {
    public ItemCrushedOre(String name) {
        super(name);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.getTagCompound() != null) {
            String oreDic = stack.getTagCompound().getString(CrushedOreHelper.TAG_ORE_DICT);
            tooltip.add(TextFormatting.RED + "oreDic : " + oreDic);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
        String name = ("" + I18n.format(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();

        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt != null) {
            String oreDicName = nbt.getString("oreDic");
            List<ItemStack> oreList = OreDictionary.getOres(oreDicName);
            if (!oreList.isEmpty()) {
                ItemStack oreItem = oreList.get(0);
                String oreName = oreItem.getDisplayName();
                name = name + " (" + oreName + ")";
            }
        }
        return name;
    }

    public static class ItemColor implements IItemColor{

        @Override
        public int colorMultiplier(ItemStack stack, int tintIndex) {
            switch (tintIndex) {
                case 0: return Color.WHITE.getRGB();
                case 1: {
                    return CrushedOreColorHelper.getColor(stack);
                }
                default: {
                    // oops! should never get here.
                    return Color.black.getRGB();
                }
            }
        }
    }
}
