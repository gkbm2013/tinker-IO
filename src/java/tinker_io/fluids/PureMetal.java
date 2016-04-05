package tinker_io.fluids;


import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.TConstruct;
import tinker_io.handler.CrushedOreColorList;

public class PureMetal extends Fluid{

	private static String tinkersDomain = TConstruct.modID + ":";
	
	public PureMetal(String fluidName) {
		super(fluidName, new ResourceLocation(tinkersDomain + "blocks/fluids/molten_metal"), new ResourceLocation(tinkersDomain + "blocks/fluids/molten_metal_flow"));
		this.setLuminosity(10).setDensity(1600).setViscosity(1500).setTemperature(600);
	}
	
	@Override
	public int getColor(FluidStack stack){
		CrushedOreColorList colorList = new CrushedOreColorList();
		return colorList.getColor(stack);
	}
	
}
