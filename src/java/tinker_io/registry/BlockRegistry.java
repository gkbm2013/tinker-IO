package tinker_io.registry;

import com.google.common.collect.ImmutableSet;

import net.minecraftforge.fml.common.registry.GameRegistry;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import tinker_io.blocks.FuelInputMachine;
import tinker_io.blocks.OreCrusher;
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
	public static Block oreCrusher;
	
	
	private static void preLoadBlock() {
		fuelInputMachine = new FuelInputMachine("fuel_input_machine");
		smartOutput = new SmartOutput("smart_output");
		whatABeautifulBlock = new WhatABeautifulBlock("WhatABeautifulBlock");
		oreCrusher = new OreCrusher("Ore_Crusher");
		
	}
	
    private static void registerBlock() {
    	GameRegistry.registerBlock(fuelInputMachine, "fuel_input_machine");
    	GameRegistry.registerBlock(smartOutput, "smart_output");
    	GameRegistry.registerBlock(whatABeautifulBlock, "WhatABeautifulBlock");
    	GameRegistry.registerBlock(oreCrusher, "Ore_Crusher");
	}
    
    public static void postInit()
    {
	  ImmutableSet.Builder<Block> builder = ImmutableSet.builder();
	  for (Block b: TinkerSmeltery.validSmelteryBlocks)
	  {
		  builder.add(b);
	  }
	  builder.add(fuelInputMachine);
	  TinkerSmeltery.validSmelteryBlocks = builder.build();
    }
}
