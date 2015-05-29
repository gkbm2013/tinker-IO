package tinker_io.mainRegistry;

import java.util.Arrays;

import tinker_io.blocks.FIMpart;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import codechicken.lib.vec.BlockCoord;
import codechicken.multipart.MultiPartRegistry.IPartConverter;
import codechicken.multipart.MultiPartRegistry.IPartFactory;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.TMultiPart;

public class PartRegister implements IPartFactory, IPartConverter{

	@Override
	public Iterable<Block> blockTypes() {
		return Arrays.asList(BlockRegistry.fuelInputMachine);
	}
	
	public void init()
    {
        MultiPartRegistry.registerConverter(this);
        MultiPartRegistry.registerParts(this, new String[]{
        		"tinker_io|advancedSmelteryController"
            });
    }

	@Override
	public TMultiPart convert(World world, BlockCoord pos) {
		 Block b = world.getBlock(pos.x, pos.y, pos.z);
		 if(b == BlockRegistry.fuelInputMachine) return new FIMpart();
		return null;
	}

	@Override
	public TMultiPart createPart(String name, boolean client) {
		if(name.equals("tinker_io|fuelInputMachine")) return new FIMpart();
		return null;
	}

}
