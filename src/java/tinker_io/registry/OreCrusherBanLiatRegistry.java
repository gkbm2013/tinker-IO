package tinker_io.registry;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;
import tinker_io.handler.OreCrusherBanList;

public class OreCrusherBanLiatRegistry {
	public static void  registry(){
		OreCrusherBanList.addBanOreDic("oreDiamond");
		OreCrusherBanList.addBanOreDic("oreEmerald");
		OreCrusherBanList.addBanOreDic("oreRedstone");
		OreCrusherBanList.addBanOreDic("oreQuartz");
		OreCrusherBanList.addBanOreDic("oreCoal");
		OreCrusherBanList.addBanOreDic("oreLapis");
		OreCrusherBanList.addBanOreDic("oreApatite");
		OreCrusherBanList.addBanOreDic("oreBlazium");
		OreCrusherBanList.addBanOreDic("oreBurnium");
		OreCrusherBanList.addBanOreDic("oreEndimium");
		OreCrusherBanList.addBanOreDic("oreJade");
		OreCrusherBanList.addBanOreDic("oreLanite");
		OreCrusherBanList.addBanOreDic("oreMeurodite");
		OreCrusherBanList.addBanOreDic("oreOnyx");
		OreCrusherBanList.addBanOreDic("oreQuartz");
		OreCrusherBanList.addBanOreDic("oreRadiantQuartz");
		OreCrusherBanList.addBanOreDic("oreRuby");
		OreCrusherBanList.addBanOreDic("oreSalt");
		OreCrusherBanList.addBanOreDic("oreSapphire");
		OreCrusherBanList.addBanOreDic("oreSoul");
		OreCrusherBanList.addBanOreDic("oreSunstone");
		OreCrusherBanList.addBanOreDic("oreCrystal");
		OreCrusherBanList.addBanOreDic("oreZimphnode");
		OreCrusherBanList.addBanOreDic("oreSalt");
		OreCrusherBanList.addBanOreDic("oreKnightslime");
		OreCrusherBanList.addBanOreDic("orePigiron");
		OreCrusherBanList.addBanOreDic("oreManyullyn");
		
		banItemCannotSmelt();
		
		OreCrusherBanList.addExceptionOreDic("oreCobalt");
		OreCrusherBanList.addExceptionOreDic("oreArdite");
	}
	
	private static void banItemCannotSmelt(){
		String[] oreDicts = OreDictionary.getOreNames();
		
		for(int i = 0; i < oreDicts.length; i++){
			if(!hasFurnaceResult(oreDicts[i]) || !isAccepted(oreDicts[i])){
				OreCrusherBanList.addBanOreDic(oreDicts[i]);
			}
		}
	}
	
	private static boolean isAccepted(String oreDic){
		String title = oreDic.substring(0, 3);
		List<String> banList = OreCrusherBanList.getBanList();
		
		if(title.equals("ore") && !banList.contains(oreDic)){
			return true;
		}
		return false;
	}
	
	private static boolean hasFurnaceResult(String oreDic){
		if(oreDic != null){
			ItemStack oreStack = getItemByOreDic(oreDic);
			if(oreStack != null){
				return FurnaceRecipes.instance().getSmeltingResult(oreStack) != null;
			}
		}
		return false;
	}
	
	private static ItemStack getItemByOreDic(String oreDic){
		List<ItemStack> oreStack = OreDictionary.getOres(oreDic);
		
		ItemStack result = null;
		
		if(!oreStack.isEmpty()){
			result = oreStack.get(0);
		}
		return result;
	}
}
