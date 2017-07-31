package tinker_io.items;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
//import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import tinker_io.main.Main;

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
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
		if(SpeedUPG.isShiftKeyDown()){
			tooltip.add(TextFormatting.GRAY + I18n.format("tio.toolTips.UpgSpeed"));
		}else{
			tooltip.add(TextFormatting.GOLD + I18n.format("tio.toolTips.common.holdShift"));			
		}
		
	}

}
