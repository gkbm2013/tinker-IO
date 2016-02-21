package tinker_io.api;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

public abstract class NeighborBlocksManager
{
	protected final BlockPos pos;

	protected  Map<BlockPos, IBlockState> blocks = new HashMap();
	
	public NeighborBlocksManager(IBlockAccess world, BlockPos pos)
	{
		this.pos = pos;
		
		initHashMap(world, pos);
	}
	
	 public void onNeighborChange(IBlockAccess world, BlockPos neighbor)
	{
		blocks.put(neighbor, world.getBlockState(neighbor));
	}
	 
	 public void initHashMap(IBlockAccess world, BlockPos pos)
	 {
		for (EnumFacing facing: EnumFacing.VALUES)
		{
			BlockPos neighbor = pos.offset(facing);
			
			blocks.put(neighbor, world.getBlockState(neighbor));
		}
	 }
}
