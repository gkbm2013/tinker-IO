package tinker_io.fluids;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
//import tinker_io.main.Main;

public class BlockPureMetal extends BlockFluidClassic{

	public BlockPureMetal(Fluid fluid, String unlocalizedName) {
		super(fluid, new MaterialLiquid(MapColor.BROWN));
		this.setUnlocalizedName(unlocalizedName);
		//I am wandering why the model can not use...
		//this.setCreativeTab(Main.TinkerIOTabs);
	}

}
