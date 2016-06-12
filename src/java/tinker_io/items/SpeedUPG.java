package tinker_io.items;

import java.util.List;

import org.lwjgl.input.Keyboard;

import tinker_io.main.Main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

public class SpeedUPG extends Item {
	public SpeedUPG(String unlocalizedName){
		super();
		setUnlocalizedName(unlocalizedName);
		setCreativeTab(Main.TinkerIOTabs);
	}
	
	public static boolean isShiftKeyDown(){
        return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
    }
	
	/**
	 * when mouseover
	 */
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
		if(this.isShiftKeyDown()){
			list.add(TextFormatting.GRAY + I18n.translateToLocalFormatted("tio.toolTips.UpgSpeed"));
		}else{
			list.add(TextFormatting.GOLD + I18n.translateToLocalFormatted("tio.toolTips.common.holdShift"));			
		}
		
	}

}
