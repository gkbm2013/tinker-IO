package tinker_io.items;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
//import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import tinker_io.main.Main;
import tinker_io.registry.RegisterUtil;
import tinker_io.registry.RegisterUtil;
//import net.minecraft.util.IIcon;
public class Upgrade extends Item {
	public Upgrade(String unlocalizedName){
		super();
		setUnlocalizedName(unlocalizedName);
		setCreativeTab(Main.TinkerIOTabs);
		setHasSubtypes(true);
		setMaxStackSize(8);
	}
	
	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	/*@Override
	public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> list) {
	    for (int i = 0; i < 8; i ++) {
	        list.add(new ItemStack(item, 1, i));
	    }
	}*/
	/**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (int i = 0; i < 7; i ++) {
        	if (this.isInCreativeTab(tab)){
        		items.add(new ItemStack(this, 1, i));
        	}
	    }
    }
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
	    return this.getUnlocalizedName() + "_" + stack.getItemDamage();
	}
	
	public boolean isShiftKeyDown(){
        return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
    }
	
	@Override
	public void addInformation(ItemStack itemStack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn){
		ItemStack UpgBase = new ItemStack(RegisterUtil.Upgrade, 1, 0);
		ItemStack slotUPG1 = new ItemStack(RegisterUtil.Upgrade, 1, 1);
		ItemStack slotUPG2 = new ItemStack(RegisterUtil.Upgrade, 1, 2);
		ItemStack slotUPG3 = new ItemStack(RegisterUtil.Upgrade, 1, 3);
		ItemStack slotUPG4 = new ItemStack(RegisterUtil.Upgrade, 1, 4);
		ItemStack slotUPGinfinity = new ItemStack(RegisterUtil.Upgrade, 1, 6);
		ItemStack redstoneUPG = new ItemStack(RegisterUtil.Upgrade ,1 ,5);
		ItemStack basinUPG = new ItemStack(RegisterUtil.Upgrade ,1 ,7);
		
		if(this.isShiftKeyDown()){
			if(itemStack.isItemEqual(UpgBase)){
				list.add(TextFormatting.GRAY + I18n.format("tio.toolTips.UpgBase"));
			}else if(itemStack.isItemEqual(slotUPG1)){
				list.add(TextFormatting.GRAY + I18n.format("tio.toolTips.slotUPG1"));
				list.add(TextFormatting.GREEN + I18n.format("tio.toolTips.slotUPG1.usage1"));
				//list.add(TextFormatting.GREEN + I18n.format("tio.toolTips.slotUPG1.usage2"));
			}else if(itemStack.isItemEqual(slotUPG2)){
				list.add(TextFormatting.GRAY + I18n.format("tio.toolTips.slotUPG2"));
				list.add(TextFormatting.GREEN + I18n.format("tio.toolTips.slotUPG2.usage1"));
				//list.add(TextFormatting.GREEN + I18n.format("tio.toolTips.slotUPG2.usage2"));
			}else if(itemStack.isItemEqual(slotUPG3)){
				list.add(TextFormatting.GRAY + I18n.format("tio.toolTips.slotUPG3"));
				list.add(TextFormatting.GREEN + I18n.format("tio.toolTips.slotUPG3.usage1"));
				//list.add(TextFormatting.GREEN + I18n.format("tio.toolTips.slotUPG3.usage2"));
			}else if(itemStack.isItemEqual(slotUPG4)){
				list.add(TextFormatting.GRAY + I18n.format("tio.toolTips.slotUPG4"));
				list.add(TextFormatting.GREEN + I18n.format("tio.toolTips.slotUPG4.usage1"));
				//list.add(TextFormatting.GREEN + I18n.format("tio.toolTips.slotUPG4.usage2"));
			}else if(itemStack.isItemEqual(slotUPGinfinity)){
				list.add(TextFormatting.GRAY + I18n.format("tio.toolTips.slotUPGinfinity"));
				list.add(TextFormatting.GREEN + I18n.format("tio.toolTips.slotUPGinfinity.usage1"));
			}else if(itemStack.isItemEqual(redstoneUPG)){
				list.add(TextFormatting.GRAY + I18n.format("tio.toolTips.redstoneUPG"));
				list.add(TextFormatting.GREEN + I18n.format("tio.toolTips.redstoneUPG.usage1"));
			}else if(itemStack.isItemEqual(basinUPG)){
				list.add(TextFormatting.GRAY + I18n.format("tio.toolTips.basinUPG"));
				list.add(TextFormatting.GREEN + I18n.format("tio.toolTips.basinUPG.usage1"));
			}
		}else{
			list.add(TextFormatting.GOLD + I18n.format("tio.toolTips.common.holdShift"));
		}
		
		
	}

}

