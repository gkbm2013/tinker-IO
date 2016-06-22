package tinker_io.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
			System.out.println(banedOreDicList.get("oreCobalt"));
			return false;
		}
		return true;
	}
	
	/**
	 * The ore dictionary added by this method will be removed when ore crusher want to crush it. 
	 * 
	 * @param oreDic The ore dictionary to ban.
	 */
	public static void addBanOreDic(String oreDic){
		addOreDicLists(oreDic);
	}
	
	/**
	 * The ore dictionary added by this method will be unlock when ore crusher want to crush it.
	 * Note : The method will UNLOCK an object instead of add recipe for ore crusher. 
	 * 
	 * @param oreDic The ore dictionary to ban.
	 */
	public static void addExceptionOreDic(String oreDic){
		banedOreDicList.remove(oreDic);
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
	
	public static List<String> getBanList(){
		List<String> list = new ArrayList<String>(banedOreDicList.values());
		return list;
	}
}
