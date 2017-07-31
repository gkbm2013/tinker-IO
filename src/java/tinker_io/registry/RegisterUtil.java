package tinker_io.registry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import tinker_io.blocks.FuelInputMachine;
import tinker_io.blocks.OreCrusher;
import tinker_io.blocks.SmartOutput;
import tinker_io.blocks.StirlingEngine;
import tinker_io.blocks.WhatABeautifulBlock;
import tinker_io.items.CD_Lonesome_Avenue;
import tinker_io.items.CrushedOre;
import tinker_io.items.SolidFuel;
import tinker_io.items.SpeedUPG;
import tinker_io.items.Upgrade;
import tinker_io.main.Main;

public class RegisterUtil {	
	
	public static Block fuelInputMachine;
	public static Block smartOutput;
	public static Block whatABeautifulBlock;
	public static Block oreCrusher;
	public static Block stirlingEngine; 
	
	public static Item SpeedUPG;
	public static Item SolidFuel;
	public static Item Upgrade;
	public static Item Lonesome_Avenue;
	public static Item CrushedOre;
	
	private static void preLoad() {
		fuelInputMachine = new FuelInputMachine("fuel_input_machine");
		smartOutput = new SmartOutput("smart_output");
		whatABeautifulBlock = new WhatABeautifulBlock("what_a_beautiful_block");
		oreCrusher = new OreCrusher("Ore_Crusher");
		stirlingEngine = new StirlingEngine("Stirling_Engine");
		
		SpeedUPG = new SpeedUPG("speedUPG");
		SolidFuel = new SolidFuel("SolidFuel");
		Upgrade = new Upgrade("upg");
		Lonesome_Avenue= new CD_Lonesome_Avenue("Lonesome_Avenue"); //record name
		CrushedOre = new CrushedOre("Crushed_ore");
	}
	
	public static void registerAll(FMLPreInitializationEvent event){
		preLoad();		
		registerBlocks(event, 
				fuelInputMachine,
				smartOutput,
				whatABeautifulBlock,
				oreCrusher,
				stirlingEngine);
		
		registerItems(event, 
				SpeedUPG.setRegistryName("speed_upg"),
				SolidFuel.setRegistryName("solid_fuel"),
				Upgrade,
				Lonesome_Avenue,
				CrushedOre);
	}
	
	private static void registerBlocks(FMLPreInitializationEvent event, Block... blocks){
		for(Block block : blocks){
			block.setRegistryName(block.getUnlocalizedName().substring(5));
			ForgeRegistries.BLOCKS.register(block);
			final ItemBlock itemblock = new ItemBlock(block);
			itemblock.setRegistryName(block.getRegistryName());
			ForgeRegistries.ITEMS.register(itemblock);
			if(event.getSide() == Side.CLIENT){
				ModelResourceLocation rsl = getModelResourceLocation(block.getUnlocalizedName().substring(5));
				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, rsl);
			}
		}
	}
	
	private static void registerItems(FMLPreInitializationEvent event, Item... items){
		for(Item item : items){
			if(item.getRegistryName() == null){
				item.setRegistryName(item.getUnlocalizedName().substring(5));
			}
			ForgeRegistries.ITEMS.register(item);
			if(event.getSide() == Side.CLIENT){
				if(item.getHasSubtypes()){
					NonNullList<ItemStack> list = NonNullList.create();
					list.add(new ItemStack(item));
					item.getSubItems(Main.TinkerIOTabs, list);
					for(int i = 0; i < list.size(); i++){
						ModelResourceLocation rsl = getModelResourceLocation(item.getUnlocalizedName().substring(5)+"_"+i);
						ModelLoader.setCustomModelResourceLocation(item, i, rsl);
					}
				}else{
					//ModelResourceLocation rsl = getModelResourceLocation(item.getUnlocalizedName().substring(5));
					ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString()));
				}
			}
		}
	}
	
	private static ModelResourceLocation getModelResourceLocation(String name) {
		return new ModelResourceLocation(Main.MODID + ":" + name, "inventory");
	}
}
