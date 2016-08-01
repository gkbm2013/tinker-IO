package tinker_io.registry;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import tinker_io.blocks.FuelInputMachine;
import tinker_io.blocks.OreCrusher;
import tinker_io.blocks.SmartOutput;
import tinker_io.blocks.StirlingEngine;
import tinker_io.blocks.WhatABeautifulBlock;
import tinker_io.main.Main;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

public class BlockRegistry {
	public static void mainRegistry() {
		preLoadBlock();
		registerBlock();
	}

	public static Block fuelInputMachine;
	public static Block smartOutput;
	public static Block whatABeautifulBlock;
	public static Block oreCrusher;
	public static Block stirlingEngine; 
	
	
	private static void preLoadBlock() {
		fuelInputMachine = new FuelInputMachine("fuel_input_machine");
		smartOutput = new SmartOutput("smart_output");
		whatABeautifulBlock = new WhatABeautifulBlock("WhatABeautifulBlock");
		oreCrusher = new OreCrusher("Ore_Crusher");
		stirlingEngine = new StirlingEngine("Stirling_Engine");
	}
	
    private static void registerBlock() {
    	registerBlock(fuelInputMachine, "fuel_input_machine");
    	registerBlock(smartOutput, "smart_output");
    	registerBlock(whatABeautifulBlock, "WhatABeautifulBlock");
    	registerBlock(oreCrusher, "Ore_Crusher");
    	registerBlock(stirlingEngine, "Stirling_Engine");
	}
    
    public static <K extends IForgeRegistryEntry<?>> K  registerBlock(K object, String name){
    	ResourceLocation rsl = new ResourceLocation(Main.MODID, name);
    	object.setRegistryName(rsl);
    	GameRegistry.register(object);
    	GameRegistry.register(new ItemBlock((Block) object).setRegistryName(object.getRegistryName()));
    	return object;
    }
}
