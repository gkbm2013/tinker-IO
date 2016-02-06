package tinker_io.TileEntity.fim;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.smeltery.tileentity.TileSmeltery;
import tinker_io.api.vanilla.PosInfo;

public class SCInfo{
	final private BlockPos FIMBlockPos;
	final private World worldObj;
	
	public BlockPos pos;
	public List<BlockPos> posList;
	
	@Deprecated
	public TileSmeltery tile;
	
	public SCInfo(
			BlockPos FIMBlockPos, World worldObj)
	{
		this.FIMBlockPos = FIMBlockPos;
		this.worldObj = worldObj;
	}
	
	protected void update() {
		posList = findSCPos(FIMBlockPos);
		if (isOnlyOneSmelteryController(posList)) {
			pos = posList.get(0);
		} else {
			pos = null;
		}
	}
	
	private List<BlockPos> findSCPos(BlockPos pos) {
		List<BlockPos> posList = PosInfo.getFacingList().stream()
			.filter(f -> isSmelteryController(pos, f))
			.map(pos::offset)
			.collect(Collectors.toList());
		return posList;
	}
	
	private boolean isOnlyOneSmelteryController(List list) {
		int amount = list.size();
		if (amount == 1) {return true; }
		else {return false; }
	}
	
	@Deprecated
	private boolean isOnlyOneSmelteryController() {
		int num = getSmelteryControllerAmount();
		if (num == 1) {
			return true;
		} else  {
			return false;
		}
	}
	
	private int getSmelteryControllerAmount() {
		int amount = 0;
		List<Block> blocks = getAllAroundBlocks();
		amount = blocks.stream()
				.filter(this::isSmelteryController)
				.collect(Collectors.toList())
				.size();
		return amount;
	}
	
	private List<Block> getAllAroundBlocks(BlockPos pos) {
		List<Block> blocks = PosInfo.getAllAmountBlockPosList(pos).stream()
			.map(this::getBlock)
			.collect(Collectors.toList());
		return blocks;
	}

	private List<Block> getAllAroundBlocks() {
		List<Block> blocks  = PosInfo.getFacingList().stream()
				.map(this::getBlock)
				.collect(Collectors.toList());
		return blocks;
	}
	
	private boolean isSmelteryController(BlockPos pos, EnumFacing facing) {
		return isSmelteryController(getBlock(pos, facing));
	}

	private boolean isSmelteryController(EnumFacing facing) {
		return isSmelteryController(getBlock(facing));
	}
	
	private  boolean isSmelteryController(Block block) {
		return block == TinkerSmeltery.smelteryController;
	}
	
	@Deprecated
	private Block getBlock(EnumFacing facing) {
		BlockPos pos = this.FIMBlockPos.offset(facing);
		return getBlock(pos);
	}
	
	private Block  getBlock(BlockPos pos) {
		Block block = worldObj.getBlockState(pos).getBlock();
		return block;
	}
	
	private Block getBlock(BlockPos pos, EnumFacing facing) {
		return getBlock(pos.offset(facing));
	}
	
	@Deprecated
	private TileSmeltery getTileSmeltery(BlockPos pos) {
		return (TileSmeltery) this.worldObj.getTileEntity(pos);
	}
	
	public static TileSmeltery getTileSmeltery(World world, BlockPos pos) {
		return (TileSmeltery) world.getTileEntity(pos);
	}
	
	public boolean isSCHeatingItem() {
		TileSmeltery tile = SCInfo.getTileSmeltery(worldObj, pos);
		int scInvSize = tile.getSizeInventory();
		for(int i = 0; i<scInvSize; ++i) {
			if (tile.getTemperature(i) > 0 ) {
				return true;
			}
		}
		return false;
	}
}