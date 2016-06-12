package tinker_io.api;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface NeighborBlockChange
{
	void onNeighborChange(IBlockAccess world, BlockPos neighbor);
}
