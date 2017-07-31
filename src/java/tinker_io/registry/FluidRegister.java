package tinker_io.registry;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import tinker_io.fluids.BlockPureMetal;
import tinker_io.fluids.PureMetal;

public class FluidRegister {
	
	public static void mainRegistry(FMLPreInitializationEvent event){
		fluidPreload();
		registerFluids();
		// Fluid registry MUST to before fluid block registry.
		fluidBlockPreload();
		registerFluidBlocks(event);
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
	
	private static void registerFluidBlocks(FMLPreInitializationEvent event){
		if(event.getSide() == Side.CLIENT){
			ForgeRegistries.BLOCKS.register(blockPureMetal.setRegistryName("blockPureMetal"));
		}
	}
}
