package tinker_io.items;

import java.util.List;

import tinker_io.main.Main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class CombinedOre extends Item {
	public CombinedOre(String unlocalizedName){
		super();
		setUnlocalizedName(unlocalizedName);
		setCreativeTab(Main.TinkerIOTabs);
	}
	
	
	public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean par4) {
		if (itemStack.getTagCompound() != null) {
            String oreDic = itemStack.getTagCompound().getString("oreDic");
            list.add(TextFormatting.RED + "oreDic : "+oreDic);   
        }
	}
}
