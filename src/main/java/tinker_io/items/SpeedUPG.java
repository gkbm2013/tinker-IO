package tinker_io.items;

import java.util.List;

import tinker_io.main.Main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class SpeedUPG extends Item {
	public SpeedUPG(){
		super();
		setUnlocalizedName("speedUPG");
		setCreativeTab(Main.TinkerIOTabs);
		setTextureName(Main.MODID + ":" + "speedUPG");
	}
	
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
		list.add(EnumChatFormatting.GRAY + "For : Fuel Input Machine");
	}

}
