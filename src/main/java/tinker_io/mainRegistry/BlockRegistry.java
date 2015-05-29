package tinker_io.mainRegistry;

import cpw.mods.fml.common.registry.GameRegistry;
import tinker_io.blocks.FuelInputMachine;
import tinker_io.blocks.SmartOutput;
import net.minecraft.block.Block;

public class BlockRegistry {
	public static void mainRegistry() {
		preLoadBlock();
		registerBlock();
	}

	public static Block fuelInputMachine;
	public static Block smartOutput;
	
	
	private static void preLoadBlock() {
		fuelInputMachine = new FuelInputMachine();
		smartOutput = new SmartOutput();
		
	}
	
    private static void registerBlock() {
    	GameRegistry.registerBlock(fuelInputMachine, "fuelInputMachine");
    	GameRegistry.registerBlock(smartOutput, "SmartOutput");
	}
}
