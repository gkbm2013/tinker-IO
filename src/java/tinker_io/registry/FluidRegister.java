package tinker_io.registry;

import net.minecraftforge.fluids.FluidRegistry;
import tinker_io.fluids.BlockPureMetal;
import tinker_io.fluids.PureMetal;

public class FluidRegister {
	
	public static void mainRegistry(){
		fluidPreload();
		registerFluids();
		// Fluid registry MUST to before fluid block registry.
		fluidBlockPreload();
		registerFluidBlocks();
	}
	
	public static PureMetal pureMetal;
	public static BlockPureMetal blockPureMetal;
	
	private static void fluidPreload(){
		pureMetal = new PureMetal("pureMetal");
	}
	
	private static void registerFluids() {
		FluidRegistry.registerFluid(pureMetal);
	}
	
	private static void fluidBlockPreload(){
		blockPureMetal = new BlockPureMetal(FluidRegister.pureMetal, "blockPureMetal");
	}
	
	private static void registerFluidBlocks(){
		BlockRegistry.registerBlock(blockPureMetal, "blockPureMetal");
	}
}
