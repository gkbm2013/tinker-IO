package tinker_io.handler;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreCrusherRecipe {
	public static boolean isOreDicAccepted(String oreDic){
		if(oreDic != null && oreDic.length() >= 3){
			String title = oreDic.substring(0, 3);
			List<String> banList = OreCrusherBanList.getBanList();
			
			if(title.equals("ore") && !banList.contains(oreDic)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isOreDicExisted(String oreDic){
		if(oreDic != null){
			List<ItemStack> list = OreDictionary.getOres(oreDic);
			if(!list.isEmpty()){
				return true;
			}
		}
		return false;
	}

}
