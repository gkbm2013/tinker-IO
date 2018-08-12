package tinker_io.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import tinker_io.TinkerIO;
import tinker_io.handler.EnumHandler;

import javax.annotation.Nullable;
import java.util.List;

public class ItemUpgrade extends ItemBase {

    public ItemUpgrade(String name) {
        super(name);
        setHasSubtypes(true);
        setMaxDamage(0);
        setMaxStackSize(16);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        for(int i = 0; i < EnumHandler.ItemUpgradeTypes.values().length; i++){
            if(isInCreativeTab(tab)){
                items.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        for(int i = 0; i < EnumHandler.ItemUpgradeTypes.values().length; i++){
            if(stack.getItemDamage() == i){
                return this.getUnlocalizedName() + "_" + EnumHandler.ItemUpgradeTypes.values()[i].getName();
            } else {
                continue;
            }
        }
        return this.getUnlocalizedName() + "_" + EnumHandler.ItemUpgradeTypes.BASE_UPG.getName();
    }

    @Override
    public void registerItemModel() {
        for(int i = 0; i < EnumHandler.ItemUpgradeTypes.values().length; i++){
            TinkerIO.proxy.registerItemRenderer(this, i,
                    this.name+"_"+EnumHandler.ItemUpgradeTypes.values()[i].getName());
        }
    }

    @Override
    public void addInformation(ItemStack itemStack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        ItemStack upgBase = new ItemStack(this, 1, 0);
        ItemStack slotUPG1 = new ItemStack(this, 1, 1);
        ItemStack slotUPG2 = new ItemStack(this, 1, 2);
        ItemStack slotUPG3 = new ItemStack(this, 1, 3);
        ItemStack slotUPG4 = new ItemStack(this, 1, 4);
        ItemStack slotUpgInfinity = new ItemStack(this, 1, 6);
        ItemStack redstoneUPG = new ItemStack(this, 1 ,5);
        ItemStack basinUPG = new ItemStack(this, 1 ,7);

        if(isShiftKeyDown()){
            if(itemStack.isItemEqual(upgBase)){
                tooltip.add(TextFormatting.GRAY + I18n.format("tio.toolTips.UpgBase"));
            }else if(itemStack.isItemEqual(slotUPG1)){
                tooltip.add(TextFormatting.GRAY + I18n.format("tio.toolTips.slotUPG1"));
                tooltip.add(TextFormatting.GREEN + I18n.format("tio.toolTips.slotUPG1.usage1"));
                //list.add(TextFormatting.GREEN + I18n.format("tio.toolTips.slotUPG1.usage2"));
            }else if(itemStack.isItemEqual(slotUPG2)){
                tooltip.add(TextFormatting.GRAY + I18n.format("tio.toolTips.slotUPG2"));
                tooltip.add(TextFormatting.GREEN + I18n.format("tio.toolTips.slotUPG2.usage1"));
                //list.add(TextFormatting.GREEN + I18n.format("tio.toolTips.slotUPG2.usage2"));
            }else if(itemStack.isItemEqual(slotUPG3)){
                tooltip.add(TextFormatting.GRAY + I18n.format("tio.toolTips.slotUPG3"));
                tooltip.add(TextFormatting.GREEN + I18n.format("tio.toolTips.slotUPG3.usage1"));
                //list.add(TextFormatting.GREEN + I18n.format("tio.toolTips.slotUPG3.usage2"));
            }else if(itemStack.isItemEqual(slotUPG4)){
                tooltip.add(TextFormatting.GRAY + I18n.format("tio.toolTips.slotUPG4"));
                tooltip.add(TextFormatting.GREEN + I18n.format("tio.toolTips.slotUPG4.usage1"));
                //list.add(TextFormatting.GREEN + I18n.format("tio.toolTips.slotUPG4.usage2"));
            }else if(itemStack.isItemEqual(slotUpgInfinity)){
                tooltip.add(TextFormatting.GRAY + I18n.format("tio.toolTips.slotUPGinfinity"));
                tooltip.add(TextFormatting.GREEN + I18n.format("tio.toolTips.slotUPGinfinity.usage1"));
            }else if(itemStack.isItemEqual(redstoneUPG)){
                tooltip.add(TextFormatting.GRAY + I18n.format("tio.toolTips.redstoneUPG"));
                tooltip.add(TextFormatting.GREEN + I18n.format("tio.toolTips.redstoneUPG.usage1"));
            }else if(itemStack.isItemEqual(basinUPG)){
                tooltip.add(TextFormatting.GRAY + I18n.format("tio.toolTips.basinUPG"));
                tooltip.add(TextFormatting.GREEN + I18n.format("tio.toolTips.basinUPG.usage1"));
            }
        }else{
            tooltip.add(TextFormatting.GOLD + I18n.format("tio.toolTips.common.holdShift"));
        }
    }
}
