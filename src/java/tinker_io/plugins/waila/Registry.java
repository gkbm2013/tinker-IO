package tinker_io.plugins.waila;

import tinker_io.blocks.SmartOutput;
import tinker_io.blocks.StirlingEngine;
import net.minecraftforge.fluids.FluidStack;
import mcp.mobius.waila.api.IWailaRegistrar;

public class Registry {
	public static void wailaCallback(IWailaRegistrar registrar){
		
		registrar.addConfig("Tinker I/O", "tio.frozen", "Smart Output");
		registrar.registerBodyProvider(new SmartOutputInfo(), SmartOutput.class);
		registrar.registerBodyProvider(new StirlingEngineInfo(), StirlingEngine.class);
		
	}
	
    public static String fluidNameHelper (FluidStack f)
    {
        return f.getFluid().getName();
    }

}
