package tinker_io.TileEntity.fim;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import tinker_io.api.NeighborBlocksManager;

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