package tinker_io.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tinker_io.TinkerIO;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCdLonesomeAvenue extends ItemRecord {

    public final String recordName;
    public final static SoundEvent soundEvent = new SoundEvent(new ResourceLocation(TinkerIO.MOD_ID, "records.lonesome_avenue"));

    public ItemCdLonesomeAvenue(String recordName) {
        super(recordName, soundEvent);
        this.recordName = recordName;
        setMaxStackSize(1);
        setUnlocalizedName("CD_" + recordName);
        setRegistryName("CD_" + recordName);
        setCreativeTab(TinkerIO.creativeTabs);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(this.getRecordNameLocal());

        if (ItemBase.isShiftKeyDown()) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tio.toolTips.CD_Lonesome_Avenue"));
        } else {
            tooltip.add(TextFormatting.GOLD + I18n.format("tio.toolTips.common.holdShift"));
        }
    }

    @Override
    //TODO: getRecordTitle()
    public String getRecordNameLocal() {
        return I18n.format(this.getUnlocalizedName() + ".desc");
    }


    @Override
    public EnumRarity getRarity(ItemStack itemStack) {
        return EnumRarity.RARE;
    }


//    public static ItemCdLonesomeAvenue getRecord(String par0Str)
//    {
//        return (ItemCdLonesomeAvenue) records.get(par0Str);
//    }
}
