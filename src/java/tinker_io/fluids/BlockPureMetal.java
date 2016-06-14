package tinker_io.fluids;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
//import tinker_io.main.Main;

public class BlockPureMetal extends BlockFluidClassic{

	public BlockPureMetal(Fluid fluid, String unlocalizedName) {
		super(fluid, Material.LAVA);
		this.setUnlocalizedName(unlocalizedName);
		//I am wandering why the model can not use...
		//this.setCreativeTab(Main.TinkerIOTabs);
	}

}
