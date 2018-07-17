package tinker_io.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemSpeedUpgrade extends ItemBase {
    public ItemSpeedUpgrade(String name) {
        super(name);
    }

    /**
     * when mouseover
     */
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
        if(isShiftKeyDown()){
            tooltip.add(TextFormatting.GRAY + I18n.format("tio.toolTips.UpgSpeed"));
        }else{
            tooltip.add(TextFormatting.GOLD + I18n.format("tio.toolTips.common.holdShift"));
        }

    }
}
