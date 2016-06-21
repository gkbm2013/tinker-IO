package tinker_io.blocks;

//import java.util.Random;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.smeltery.tileentity.TileSmelteryComponent;
import tinker_io.TileEntity.TileEntityContainerAdapter;
import tinker_io.TileEntity.fim.FIMNeighborBlocksManager;
import tinker_io.TileEntity.fim.FIMTileEntity;
import tinker_io.api.NeighborBlockChange;
import tinker_io.api.NeighborBlocksManager;
import tinker_io.api.Observable;
import tinker_io.api.Observer;
import tinker_io.main.Main;

public class FuelInputMachine extends BlockContainerAdapter
{
	 public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	//private final Random random = new Random();
	
	public FuelInputMachine(String unlocalizedName)
	{
		super();
		setUnlocalizedName(unlocalizedName);
		setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}
	
	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { FACING });
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }
		return getDefaultState().withProperty(FACING, enumfacing);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
        return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
	
    @Override
    public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam) {
        super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
    }
	
    /**
     * right-click block 
     */
	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
    {
	    if(playerIn.isSneaking()) {
	        return false;
	      }
		if (!worldIn.isRemote) {
		    playerIn.openGui(Main.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		
		toSpecialPlayerMessage(worldIn, pos, playerIn);
        return true;
    }
	
	private void toSpecialPlayerMessage(World world, BlockPos pos,  EntityPlayer player) {
		
     	if(world.isBlockPowered(pos)){
     		String name = player.getName();
     		if(world.isRemote){
     			if(name.equals("frankboy89722")){
     	   			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u55ef?\u9019\u4e0d\u662f\u9999\u8349\u55ce? \u6211\u731c\u8eab\u5206\u8b49\u7684\u7b2c100\u4f4d\u6578\u5b57\u53ef\u4ee5\u8fa8\u8b58\u6027\u5225..."));
     	   		    player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u5c0d\u4e86\uff0c\u6211\u7d55\u5c0d\u4e0d\u6703\u8aaa\u662f\u9999\u8349\u8981\u6211\u52a0\u7206\u70b8\u97f3\u6548\u4f86\u5687\u4eba\u7684..."));
     	   			player.playSound("random.explode", 2f, 1f); 
     	   		 }else if(name.equals("KwongFong")){
     	   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u98a8\u7684\u50b3\u4eba : \u9059\u9060\u7684\u6771\u65b9\u6709\u4e00\u9663\u98a8\uff0c\u4ed6\u7684\u540d\u5b57\u5c31\u53eb\u72c2\u98a8..."));
      	   		     player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u5c0d\u4e86\uff0c\u6211\u7d55\u5c0d\u4e0d\u6703\u8aaa\u662f\u9999\u8349\u8981\u6211\u52a0\u7206\u70b8\u97f3\u6548\u4f86\u5687\u4eba\u7684..."));
     	   			 player.playSound("random.explode", 2f, 1f); 
     	   		 }else if(name.equals("alan6716")){
     	   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "HI \u609f\u8a22~ \u5077\u5077\u544a\u8a34\u4f60\u4e00\u4ef6\u4e8b : Alan's fuel was stolen by Alien!"));
      	   		     player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u5c0d\u4e86\uff0c\u6211\u7d55\u5c0d\u4e0d\u6703\u8aaa\u662f\u9999\u8349\u8981\u6211\u52a0\u7206\u70b8\u97f3\u6548\u4f86\u5687\u4eba\u7684..."));
     	   			 player.playSound("random.explode", 2f, 1f); 
     	   		 }else if(name.equals("gkbXkinG")){
     	   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u4f60\u597d~"));
     	   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u5c0d\u4e86\uff0c\u6211\u7d55\u5c0d\u4e0d\u6703\u8aaa\u662f\u9999\u8349\u8981\u6211\u52a0\u7206\u70b8\u97f3\u6548\u4f86\u5687\u4eba\u7684..."));
     	   			 player.playSound("random.explode", 2f, 1f); 
     	   		 }else if(name.equals("eating555")){
     	   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "HI eating555! \u6211\u662fGKB~"));
     	   			 player.playSound("random.explode", 2f, 1f); 
     	   		 }else if(name.equals("codespawner")){
     	   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u54ce\u5440~\u9019\u4e0d\u662fcode\u55ce?\u8a71\u8aaa\u9019\u4e32\u6587\u5b57\u7de8\u78bc\u7a76\u7adf\u662fGBK\u9084\u662fGKB\u5462?"));
      	   		     player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u5c0d\u4e86\uff0c\u6211\u7d55\u5c0d\u4e0d\u6703\u8aaa\u662f\u9999\u8349\u8981\u6211\u52a0\u7206\u70b8\u97f3\u6548\u4f86\u5687\u4eba\u7684..."));
     	   			 player.playSound("random.explode", 2f, 1f); 
     	   		 }else if(name.equals("BigRice1018")){
     	   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u7c73\u5927!!!!"));
      	   		     player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u5c0d\u4e86\uff0c\u6211\u7d55\u5c0d\u4e0d\u6703\u8aaa\u662f\u9999\u8349\u8981\u6211\u52a0\u7206\u70b8\u97f3\u6548\u4f86\u5687\u4eba\u7684..."));
     	   			 player.playSound("random.explode", 2f, 1f); 
     	   		 }else if(name.equals("nightmare9913256")){
     	   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u597d\u9762\u719f\u554a...\u55ef?\u9019\u4e0d\u662fnight\u55ce?"));
     	   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u5c0d\u4e86\uff0c\u6211\u7d55\u5c0d\u4e0d\u6703\u8aaa\u662f\u9999\u8349\u8981\u6211\u52a0\u7206\u70b8\u97f3\u6548\u4f86\u5687\u4eba\u7684..."));
     	   			 player.playSound("random.explode", 2f, 1f); 
     	   		 }else if(name.equals("con2000us")){
     	   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u4f60\u597d~ (\u4e0d\u8981\u61f7\u7591...\u5c31\u662f\u4f60\u597d...)"));
     	   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u5c0d\u4e86\uff0c\u6211\u7d55\u5c0d\u4e0d\u6703\u8aaa\u662f\u9999\u8349\u8981\u6211\u52a0\u7206\u70b8\u97f3\u6548\u4f86\u5687\u4eba\u7684..."));
     	   			 player.playSound("random.explode", 2f, 1f); 
     	   		 	}
     			}
     		}
	}
	
	@Override
	 public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor)
	{
		((FIMTileEntity) world.getTileEntity(pos)).onNeighborChange(world, neighbor);
	}
	
	/**
	 *  createNewTileEntity() & onBlockAdded()
	 *  Called on different:
	 * 	createNewTileEntity() was call Client and Server,
	 * but onBlockAdded() was call only Server.
	 */
	
	/**
	 * Returns a new instance of a block's tile entity class. Called on placing the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new FIMTileEntity();
	}
	
	/**
	 * Called on placing the block.
	 */
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.setFacing(worldIn, pos, state);
    }
	
	private void setFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
        Block blockNorth = worldIn.getBlockState(pos.north()).getBlock();
        Block blockSouth = worldIn.getBlockState(pos.south()).getBlock();
        Block blockWest = worldIn.getBlockState(pos.west()).getBlock();
        Block blockEast = worldIn.getBlockState(pos.east()).getBlock();
        EnumFacing facing = (EnumFacing)state.getValue(FACING);

        if (facing == EnumFacing.NORTH && blockNorth.isFullBlock() && !blockSouth.isFullBlock())
            {
                facing = EnumFacing.SOUTH;
            }
            else if (facing == EnumFacing.SOUTH && blockSouth.isFullBlock() && !blockNorth.isFullBlock())
            {
                facing = EnumFacing.NORTH;
            }
            else if (facing == EnumFacing.WEST && blockWest.isFullBlock() && !blockEast.isFullBlock())
            {
                facing = EnumFacing.EAST;
            }
            else if (facing == EnumFacing.EAST && blockEast.isFullBlock() && !blockWest.isFullBlock())
            {
                facing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, facing), 2);
        }
    }
	
	/**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
	@Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos,
    		EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
	
    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
	@Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }
	
    /**
     * Possibly modify the given BlockState before rendering it on an Entity (Minecarts, Endermen, ...)
     */
	@Override
    @SideOnly(Side.CLIENT)
    public IBlockState getStateForEntityRender(IBlockState state)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
    }
	
	
	
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileSmelteryComponent)
        {
            ((TileSmelteryComponent) te).notifyMasterOfChange();
        }
        super.breakBlock(worldIn, pos, state);
    }
}
