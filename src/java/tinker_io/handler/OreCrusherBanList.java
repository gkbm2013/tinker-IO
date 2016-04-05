package tinker_io.handler;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreCrusherBanList {
	private static Map<String, String> banedOreDicList = new HashMap<String, String>();
	
	public static OreCrusherBanList banedOreDicList(){
		return Searching_eliminated_BASE;
	}
	
	private static final OreCrusherBanList Searching_eliminated_BASE = new OreCrusherBanList();
	
	private OreCrusherBanList(){
		
	}
	
	public boolean canItemCrush(ItemStack itemstack){
		if(banedOreDicList.get(getOreDicName(itemstack)) != null){
			return false;
		}
		return true;
	}
	
	/**
	 * The ore dictionary added by this method will be removed when ore crusher want crush it. 
	 * 
	 * @param oreDic The ore dictionary to ban.
	 */
	public static void addBanOreDic(String oreDic){
		addOreDicLists(oreDic);
	}

	private static void addOreDicLists(String oreDic) {
		banedOreDicList.put(oreDic, oreDic);
	}
	
	private String getOreDicName(ItemStack itemStack){
		String oreDicName = null;
		if(itemStack != null && OreDictionary.getOreIDs(itemStack).length > 0){
			int oreID = OreDictionary.getOreIDs(itemStack)[0];
			oreDicName = OreDictionary.getOreName(oreID);
		}
		return oreDicName;
	}
}
