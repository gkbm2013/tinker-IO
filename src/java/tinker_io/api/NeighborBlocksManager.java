package tinker_io.api;


import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public abstract class NeighborBlocksManager implements NeighborBlockChange
{
	protected final IBlockAccess world;
	protected final BlockPos pos;

	protected  Map<BlockPos, IBlockState> blocks = new HashMap<BlockPos, IBlockState>();
	
	public NeighborBlocksManager(IBlockAccess world, BlockPos pos)
	{
		this.world = world;
		this.pos = pos;
	}
	
	private boolean initFlag = false;
	
	@Override public void onNeighborChange(IBlockAccess world, BlockPos neighbor)
	{
		blocks.put(neighbor, world.getBlockState(neighbor));
		
		if(!initFlag)
		{
			initHashMap();
			initFlag = true;
		}
	}
	 
	public void initHashMap()
	 {
		for (EnumFacing facing: EnumFacing.VALUES)
		{
			BlockPos neighbor = pos.offset(facing);
			
			blocks.put(neighbor, world.getBlockState(neighbor));
		}
	 }	 
}
