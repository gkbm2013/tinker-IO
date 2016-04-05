package tinker_io.api;

import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import tinker_io.api.vanilla.PosInfo;

public class BlockFinder {
	
	private BlockPos pos;
	final private World worldObj;
	
	public BlockFinder(BlockPos pos, World worldObj){
		this.pos = pos;
		this.worldObj = worldObj;
	}
	
	public List<BlockPos> getSurroundingBlockPos(BlockPos pos, Block targetBlock) {
		List<BlockPos> posList = PosInfo.getFacingList().stream()
			.filter(facing -> isTargetBlock(pos, facing, targetBlock))
			.map(pos::offset)
			.collect(Collectors.toList());
		return posList;
	}
	
	/**
	 * About the list
	 * Index -> the block
	 * 0 -> Down;
	 * 1 -> Up;
	 * 2 -> North;
	 * 3 -> South;
	 * 4 -> West;
	 * 5 -> East;
	 * 
	 * @return The Surrounding Block List.
	 */
	public List<Block> getSurroundingBlockList(Block targetBlock){
		List<Block> blocks  = PosInfo.getFacingList().stream()
				.map(this::getBlock)
				.filter(block -> block == targetBlock)
				.collect(Collectors.toList());
		return blocks;
	}
	
	public int getSurroundingTargetBlockAmount(Block targetBlock) {
		int amount = 0;
		List<Block> blocks = getAllAroundBlocks();
		amount = blocks.stream()
				.filter(block -> block == targetBlock)
				.collect(Collectors.toList())
				.size();
		return amount;
	}
	
	private List<Block> getAllAroundBlocks() {
		List<Block> blocks  = PosInfo.getFacingList().stream()
				.map(this::getBlock)
				.collect(Collectors.toList());
		return blocks;
	}
	
	private Block getBlock(EnumFacing facing) {
		BlockPos pos = this.pos.offset(facing);
		return getBlock(pos);
	}
	
	
	private Block  getBlock(BlockPos pos) {
		Block block = worldObj.getBlockState(pos).getBlock();
		return block;
	}
	
	private boolean isTargetBlock(BlockPos pos, EnumFacing f, Block targetBlock){
		return getBlock(pos, f) == targetBlock;
	}
	
	private Block getBlock(BlockPos pos, EnumFacing facing) {
		return getBlock(pos.offset(facing));
	}
}
