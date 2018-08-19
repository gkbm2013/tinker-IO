package tinker_io.registry;

import net.minecraftforge.fluids.FluidRegistry;
import tinker_io.TinkerIO;
import tinker_io.fluids.FluidPureMetal;

public class FluidRegister {
    private static final String FLUID_MODEL_PATH = TinkerIO.MOD_ID + ":" + "fluid";

    public static FluidPureMetal pureMetal = new FluidPureMetal("pure_metal");

    public static void register() {
        FluidRegistry.registerFluid(pureMetal);
    }

}
