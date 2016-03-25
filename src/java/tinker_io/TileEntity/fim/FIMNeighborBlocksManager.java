package tinker_io.TileEntity.fim;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import tinker_io.api.NeighborBlocksManager;
import tinker_io.blocks.FuelInputMachine;

public class FIMNeighborBlocksManager extends NeighborBlocksManager
{
	public FIMNeighborBlocksManager(IBlockAccess world, BlockPos pos)
	{
		super(world, pos);
	}
	
	@Override public void onNeighborChange(IBlockAccess world, BlockPos neighbor)
	{
		super.onNeighborChange(world, neighbor);
	}
}
