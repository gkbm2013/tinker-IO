package tinker_io.handler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import tinker_io.main.Main;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class SOEliminateList {
private Map<String, ItemStack> eliminateItemList = new HashMap();
private Map eliminateItemListControlledByConfig = new HashMap();
	
	public static SOEliminateList eliminatedList(){
		return Searching_eliminated_BASE;
	}
	
	private static final SOEliminateList Searching_eliminated_BASE = new SOEliminateList();
	
	private SOEliminateList(){
		this.addEliminateItemRecipie(new ItemStack(Items.bucket).toString(), new ItemStack(Items.bucket), false);

	}
	
	public boolean itemNeedToEliminate(String itemstack){		
		//System.out.println(eliminateItemList.get(new ItemStack(Items.bucket).toString()));
		//System.out.println(eliminateItemListControlledByConfig.get(new ItemStack(Items.bucket).toString()));
		if(eliminateItemList.get(itemstack) != null){
			if((Boolean) eliminateItemListControlledByConfig.get(itemstack) == true){
				return Main.iguanas_support;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * The item added by this method will be removed when smart output export the product. 
	 * 
	 * @param name The name to identify item to eliminate.
	 * @param itemstack The item to eliminate.
	 * @param followConfig If true, the item will be remove when "Main.iguanas_support" is true
	 */
	public void addEliminateItemRecipie(String name, ItemStack itemstack, boolean followConfig){
		this.addItemLists(name, itemstack);
		this.addBooleanLists(name, followConfig);
	}

	private void addItemLists(String name, ItemStack itemstack) {
		eliminateItemList.put(name, itemstack);
	}
	
	private void addBooleanLists(String name, boolean followConfig) {
		eliminateItemListControlledByConfig.put(name, followConfig);
	}
}
