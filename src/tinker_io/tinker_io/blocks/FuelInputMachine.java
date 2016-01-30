package tinker_io.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
//import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
//import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import slimeknights.mantle.tileentity.TileInventory;
import tinker_io.TileEntity.FIMTileEntity;
import tinker_io.main.Main;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FuelInputMachine extends BlockContainer {
	
	 public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	private final Random random = new Random();
	
	public FuelInputMachine(String unlocalizedName)
	{
		super(Material.rock);
		setUnlocalizedName(unlocalizedName);
		setCreativeTab(Main.TinkerIOTabs);
		setHarvestLevel("pickaxe", 1);
		setHardness(3);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
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
        return true;
    }
	
//	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
//		 player.openGui(Main.instance, 0, world, x, y, z);
//		 if(!world.isRemote){
//			 FIMTileEntity te = (FIMTileEntity) world.getTileEntity(x, y, z);
//			 te.checkConnection();
//         }
//		
//		 
//		int power = world.getBlockPowerInput(x, y, z);
//     	int strongestsPower = world.getStrongestIndirectPower(x, y, z);
//     	//System.out.println(power);
//     	if(power != 0 && strongestsPower != 0){
//     		String name = player.getDisplayName();
//     		if(world.isRemote){
//     			if(name.equals("frankboy89722")){
//     	   			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u55ef?\u9019\u4e0d\u662f\u9999\u8349\u55ce? \u6211\u731c\u8eab\u5206\u8b49\u7684\u7b2c100\u4f4d\u6578\u5b57\u53ef\u4ee5\u8fa8\u8b58\u6027\u5225..."));
//     	   		    player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u5c0d\u4e86\uff0c\u6211\u7d55\u5c0d\u4e0d\u6703\u8aaa\u662f\u9999\u8349\u8981\u6211\u52a0\u7206\u70b8\u97f3\u6548\u4f86\u5687\u4eba\u7684..."));
//     	   			player.playSound("random.explode", 2f, 1f); 
//     	   		 }else if(name.equals("KwongFong")){
//     	   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u98a8\u7684\u50b3\u4eba : \u9059\u9060\u7684\u6771\u65b9\u6709\u4e00\u9663\u98a8\uff0c\u4ed6\u7684\u540d\u5b57\u5c31\u53eb\u72c2\u98a8..."));
//      	   		     player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u5c0d\u4e86\uff0c\u6211\u7d55\u5c0d\u4e0d\u6703\u8aaa\u662f\u9999\u8349\u8981\u6211\u52a0\u7206\u70b8\u97f3\u6548\u4f86\u5687\u4eba\u7684..."));
//     	   			 player.playSound("random.explode", 2f, 1f); 
//     	   		 }else if(name.equals("alan6716")){
//     	   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "HI \u609f\u8a22~ \u5077\u5077\u544a\u8a34\u4f60\u4e00\u4ef6\u4e8b : Alan's fuel was stolen by Alien!"));
//      	   		     player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u5c0d\u4e86\uff0c\u6211\u7d55\u5c0d\u4e0d\u6703\u8aaa\u662f\u9999\u8349\u8981\u6211\u52a0\u7206\u70b8\u97f3\u6548\u4f86\u5687\u4eba\u7684..."));
//     	   			 player.playSound("random.explode", 2f, 1f); 
//     	   		 }else if(name.equals("gkbXkinG")){
//     	   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u4f60\u597d~"));
//     	   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u5c0d\u4e86\uff0c\u6211\u7d55\u5c0d\u4e0d\u6703\u8aaa\u662f\u9999\u8349\u8981\u6211\u52a0\u7206\u70b8\u97f3\u6548\u4f86\u5687\u4eba\u7684..."));
//     	   			 player.playSound("random.explode", 2f, 1f); 
//     	   		 }else if(name.equals("eating555")){
//     	   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "HI eating555! \u6211\u662fGKB~"));
//     	   			 player.playSound("random.explode", 2f, 1f); 
//     	   		 }else if(name.equals("codespawner")){
//     	   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u54ce\u5440~\u9019\u4e0d\u662fcode\u55ce?\u8a71\u8aaa\u9019\u4e32\u6587\u5b57\u7de8\u78bc\u7a76\u7adf\u662fGBK\u9084\u662fGKB\u5462?"));
//      	   		     player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u5c0d\u4e86\uff0c\u6211\u7d55\u5c0d\u4e0d\u6703\u8aaa\u662f\u9999\u8349\u8981\u6211\u52a0\u7206\u70b8\u97f3\u6548\u4f86\u5687\u4eba\u7684..."));
//     	   			 player.playSound("random.explode", 2f, 1f); 
//     	   		 }else if(name.equals("BigRice1018")){
//     	   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u7c73\u5927!!!!"));
//      	   		     player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u5c0d\u4e86\uff0c\u6211\u7d55\u5c0d\u4e0d\u6703\u8aaa\u662f\u9999\u8349\u8981\u6211\u52a0\u7206\u70b8\u97f3\u6548\u4f86\u5687\u4eba\u7684..."));
//     	   			 player.playSound("random.explode", 2f, 1f); 
//     	   		 }else if(name.equals("nightmare9913256")){
//     	   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u597d\u9762\u719f\u554a...\u55ef?\u9019\u4e0d\u662fnight\u55ce?"));
//     	   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u5c0d\u4e86\uff0c\u6211\u7d55\u5c0d\u4e0d\u6703\u8aaa\u662f\u9999\u8349\u8981\u6211\u52a0\u7206\u70b8\u97f3\u6548\u4f86\u5687\u4eba\u7684..."));
//     	   			 player.playSound("random.explode", 2f, 1f); 
//     	   		 }else if(name.equals("con2000us")){
//     	   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u4f60\u597d~ (\u4e0d\u8981\u61f7\u7591...\u5c31\u662f\u4f60\u597d...)"));
//     	   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u5c0d\u4e86\uff0c\u6211\u7d55\u5c0d\u4e0d\u6703\u8aaa\u662f\u9999\u8349\u8981\u6211\u52a0\u7206\u70b8\u97f3\u6548\u4f86\u5687\u4eba\u7684..."));
//     	   			 player.playSound("random.explode", 2f, 1f); 
//     	   		 }/*else if(name.equals("nightmare9913256")){
//     	   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u54ce\u5440~\u9019\u4e0d\u662fcode\u55ce?\u8a71\u8aaa\u9019\u4e32\u6587\u5b57\u7de8\u78bc\u7a76\u7adf\u662fGBK\u9084\u662fGKB\u5462?"));
//     	   			 player.playSound("random.explode", 2f, 1f); 
//     	   		 }*/
//     		}
//     	}
//     	//player.addChatMessage(new ChatComponentText(name));
//		 
//		return true;
//	}
	
	@Override
	 public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor)
	{
		
	}
	
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) { //Called on placing the block.
		return new FIMTileEntity();
	}
	
//	@SideOnly(Side.CLIENT)
//	public void onBlockAdded(World world, int x, int y, int z) {
//		super.onBlockAdded(world, x, y, z);
//		this.direction(world, x, y, z);
//		
//		FIMTileEntity te = (FIMTileEntity) world.getTileEntity(x, y, z);
//		te.checkConnection();
//	}
//	
//	private void direction(World world, int x, int y, int z) {
//		if (!world.isRemote) {
//			Block direction = world.getBlock(x, y, z - 1);
//			Block direction1 = world.getBlock(x, y, z + 1);
//			Block direction2 = world.getBlock(x - 1, y, z);
//			Block direction3 = world.getBlock(x + 1, y, z);
//			byte byte0 = 5;
//
//			if (direction.func_149730_j() && direction.func_149730_j()) {
//				byte0 = 3;
//			}
//
//			if (direction1.func_149730_j() && direction1.func_149730_j()) {
//				byte0 = 2;
//			}
//
//			if (direction2.func_149730_j() && direction2.func_149730_j()) {
//				byte0 = 5;
//			}
//
//			if (direction3.func_149730_j() && direction3.func_149730_j()) {
//				byte0 = 4;
//			}
//
//			world.setBlockMetadataWithNotify(x, y, z, byte0, 3);
//			
//			
//			
//		}
//	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.setDefaultFacing(worldIn, pos, state);
    }
	
	private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            Block block = worldIn.getBlockState(pos.north()).getBlock();
            Block block1 = worldIn.getBlockState(pos.south()).getBlock();
            Block block2 = worldIn.getBlockState(pos.west()).getBlock();
            Block block3 = worldIn.getBlockState(pos.east()).getBlock();
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && block.isFullBlock() && !block1.isFullBlock())
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && block1.isFullBlock() && !block.isFullBlock())
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && block2.isFullBlock() && !block3.isFullBlock())
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && block3.isFullBlock() && !block2.isFullBlock())
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }
	
//	/**
//     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
//     * IBlockstate
//     */
//	@Override
//    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
//    {
//        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
//    }
	
    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
	@Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }
	
	/**
     * The type of render function called. 3 for standard block models, 2 for TESR's, 1 for liquids, -1 is no render
     */
	@Override
    public int getRenderType()
    {
        return 3 ;//number 3 for standrd block models
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
	
//	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack) {
//		byte b0 = 5;
//		int direction = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
//
//		   if (direction == 0)
//	        {
//	            b0 = 2;
//	        }
//
//	        if (direction == 1)
//	        {
//	            b0 = 5;
//	        }
//
//	        if (direction == 2)
//	        {
//	            b0 = 3;
//	        }
//
//	        if (direction == 3)
//	        {
//	            b0 = 4;
//	        }
//	        world.setBlockMetadataWithNotify(x, y, z, b0, 2);
//
//		if (itemstack.hasDisplayName()) {
//			((FIMTileEntity) world.getTileEntity(x, y, z)).nameFIM(itemstack.getDisplayName());
//		}
//	}
	

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        if (hasTileEntity(state)) {
            world.removeTileEntity(pos);
        }
        super.breakBlock(world, pos, state);
    }
	
//	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
//			FIMTileEntity tileentitytutfurnace = (FIMTileEntity) world.getTileEntity(x, y, z);
//
//			if (tileentitytutfurnace != null) {
//				for (int i = 0; i < tileentitytutfurnace.getSizeInventory(); ++i) {
//					ItemStack itemstack = tileentitytutfurnace.getStackInSlot(i);
//
//					if (itemstack != null) {
//						float f = this.random.nextFloat() * 0.6F + 0.1F;
//						float f1 = this.random.nextFloat() * 0.6F + 0.1F;
//						float f2 = this.random.nextFloat() * 0.6F + 0.1F;
//
//						while (itemstack.stackSize > 0) {
//							int j = this.random.nextInt(21) + 10;
//
//							if (j > itemstack.stackSize) {
//								j = itemstack.stackSize;
//							}
//
//							itemstack.stackSize -= j;
//							EntityItem entityitem = new EntityItem(world, (double) ((float) x + f), (double) ((float) y + f1), (double) ((float) z + f2), new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));
//
//							if (itemstack.hasTagCompound()) {
//								entityitem.getEntityItem().setTagCompound(((NBTTagCompound) itemstack.getTagCompound().copy()));
//							}
//
//							float f3 = 0.025F;
//							entityitem.motionX = (double) ((float) this.random.nextGaussian() * f3);
//							entityitem.motionY = (double) ((float) this.random.nextGaussian() * f3 + 0.1F);
//							entityitem.motionZ = (double) ((float) this.random.nextGaussian() * f3);
//							world.spawnEntityInWorld(entityitem);
//						}
//					}
//				}
//				world.func_147453_f(x, y, z, block);
//			}
//		super.breakBlock(world, x, y, z, block, meta);
//	}
}
