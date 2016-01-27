package tinker_io.mainRegistry;

import net.minecraftforge.fml.common.registry.GameRegistry;
import tinker_io.blocks.FuelInputMachine;
import tinker_io.blocks.SmartOutput;
import tinker_io.blocks.WhatABeautifulBlock;
import net.minecraft.block.Block;

public class BlockRegistry {
	public static void mainRegistry() {
		preLoadBlock();
		registerBlock();
	}

	public static Block fuelInputMachine;
	public static Block smartOutput;
	public static Block whatABeautifulBlock;
	
	
	private static void preLoadBlock() {
		fuelInputMachine = new FuelInputMachine();
		smartOutput = new SmartOutput();
		whatABeautifulBlock = new WhatABeautifulBlock();
		
	}
	
    private static void registerBlock() {
    	GameRegistry.registerBlock(fuelInputMachine, "fuelInputMachine");
    	GameRegistry.registerBlock(smartOutput, "SmartOutput");
    	GameRegistry.registerBlock(whatABeautifulBlock, "WhatABeautifulBlock");
	}
}
