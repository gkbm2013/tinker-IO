package tinker_io.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import tinker_io.main.Main;

public class CrushedOre extends Item {
	public CrushedOre(String unlocalizedName){
		super();
		setUnlocalizedName(unlocalizedName);
		setCreativeTab(Main.TinkerIOTabs);
	}
	
	/*private ModelResourceLocation setLocation(String filename){
		return new ModelResourceLocation(Main.MODID + ":" + this.getUnlocalizedName().substring(5)+ "_" + filename, "inventory");
	}*/
	
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
		if (itemStack.getTagCompound() != null) {
            String oreDic = itemStack.getTagCompound().getString("oreDic");
            list.add(TextFormatting.RED + "oreDic : "+oreDic);   
        }
		
	}
}