package tinker_io.registry;

import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tinker_io.blocks.FuelInputMachine;
import tinker_io.blocks.OreCrusher;
import tinker_io.blocks.SmartOutput;
import tinker_io.blocks.StirlingEngine;
import tinker_io.blocks.WhatABeautifulBlock;
import tinker_io.fluids.BlockPureMetal;
import net.minecraft.block.Block;

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
    	GameRegistry.registerBlock(fuelInputMachine, "fuel_input_machine");
    	GameRegistry.registerBlock(smartOutput, "smart_output");
    	GameRegistry.registerBlock(whatABeautifulBlock, "WhatABeautifulBlock");
    	GameRegistry.registerBlock(oreCrusher, "Ore_Crusher");
    	GameRegistry.registerBlock(stirlingEngine, "Stirling_Engine");
	}
}
