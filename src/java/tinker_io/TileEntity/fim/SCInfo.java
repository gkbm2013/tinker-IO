package tinker_io.TileEntity.fim;

import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.smeltery.tileentity.TileSmeltery;
import tinker_io.api.vanilla.PosInfo;

public class SCInfo{
	final private BlockPos FIMBlockPos;
	final private World worldObj;
	
	public BlockPos pos;
	public List<BlockPos> posList;
	
	public FIMNeighborBlocksManager manager;
	
	public SCInfo(
			BlockPos FIMBlockPos, World worldObj)
	{
		this.FIMBlockPos = FIMBlockPos;
		this.worldObj = worldObj;
		
		this.manager = new FIMNeighborBlocksManager(worldObj, FIMBlockPos);
	}
	
	public boolean canFindSCPos()
	{
		return pos != null;
	}
	
	private boolean initFlag = false;
	
	protected void update()
	{
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
	
	private List<Block> getAllAroundBlocks(BlockPos pos) {
		List<Block> blocks = PosInfo.getAllAmountBlockPosList(pos).stream()
			.map(this::getBlock)
			.collect(Collectors.toList());
		return blocks;
	}
	
	private boolean isSmelteryController(BlockPos pos, EnumFacing facing) {
		return isSmelteryController(getBlock(pos, facing));
	}
	
	public static boolean isSmelteryController(Block block) {
		return block == TinkerSmeltery.smelteryController;
	}
	
	private Block  getBlock(BlockPos pos) {
		Block block = worldObj.getBlockState(pos).getBlock();
		return block;
	}
	
	private Block getBlock(BlockPos pos, EnumFacing facing) {
		return getBlock(pos.offset(facing));
	}
	
	public static TileSmeltery getTileSmeltery(World world, BlockPos pos) {
		return (TileSmeltery) world.getTileEntity(pos);
	}
	
	public boolean isSCHeatingItem() {
		final Adapter adap = this.getAdapter();
		return adap.isHeatingItem() && !adap.isAllItemFinishHeating();
	}

	public Adapter getAdapter(){
		return  new SCTileAdapter(SCInfo.getTileSmeltery(worldObj, pos));
	}
	
	public String getFacing()
	{
		String facing = PosInfo.getFacingList().stream()
				.filter(f -> isSmelteryController(FIMBlockPos, f))
				.map(EnumFacing::name)
				.reduce(((f, b) -> String.join(", ", f, b)))
				.orElse("NONE");
		return facing;
	}
	
	public BlockPos getSCpos(){
		return this.pos;
	}
	
}