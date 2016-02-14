package tinker_io.items;

import java.util.List;

import org.lwjgl.input.Keyboard;

import tinker_io.main.Main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

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
			list.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("tio.toolTips.UpgSpeed"));
		}else{
			list.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("tio.toolTips.common.holdShift"));			
		}
		
	}

}
