package tinker_io.plugins.waila;

import tinker_io.blocks.SmartOutput;
import net.minecraftforge.fluids.FluidStack;
import mcp.mobius.waila.api.IWailaRegistrar;

public class Registry {
	public static void wailaCallback(IWailaRegistrar registrar){
		
		registrar.addConfig("Tinker I/O", "tio.frozen", "Smart Output");
		registrar.registerBodyProvider(new SmartOutputInfo(), SmartOutput.class);
		
	}
	
    public static String fluidNameHelper (FluidStack f)
    {
        return f.getFluid().getLocalizedName();
    }

}
