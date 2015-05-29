package tinker_io.blocks;

import tinker_io.mainRegistry.BlockRegistry;
import net.minecraft.block.Block;
import codechicken.lib.vec.Cuboid6;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.minecraft.McMetaPart;

public class FIMpart extends McMetaPart {

	@Override
	public String getType() {
		return "tinker_io|fuelInputMachine";
	}

	@Override
	public Cuboid6 getBounds() {
		return new Cuboid6(0.2, 0, 0.2, 0.8, 1, 0.8);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.fuelInputMachine;
	}

}
