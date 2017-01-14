package tinker_io.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.smeltery.tileentity.TileTank;
import tinker_io.TileEntity.StirlingEngineTileEntity;
import tinker_io.main.Main;

public class StirlingEngine extends BlockContainer {
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	public StirlingEngine(String unlocalizedName){
		super(Material.ROCK);
		setUnlocalizedName(unlocalizedName);
		setCreativeTab(Main.TinkerIOTabs);
		setHarvestLevel("pickaxe", 1);
		setHardness(3);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING );
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new StirlingEngineTileEntity();
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
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
    }
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
	    if(playerIn.isSneaking()) {
	        return false;
	    }
	    String fuelAmountText = I18n.format("tio.toolTips.StirlingEngine.liquidAmount", new Object[0]);
	    String fuelTempText = I18n.format("tio.toolTips.StirlingEngine.liquidTemp", new Object[0]);
	    String energyStoredText = I18n.format("tio.toolTips.StirlingEngine.energyStored", new Object[0]);
	    String generatePerTickText = I18n.format("tio.toolTips.StirlingEngine.generatePerTick", new Object[0]);
	    
	    StirlingEngineTileEntity te = (StirlingEngineTileEntity) worldIn.getTileEntity(pos);
	    
	    if(te != null){
	    	TileTank teTank = te.getTETank();
	    	if(teTank != null){
		    	int liquidAmount = teTank.getInternalTank().getFluidAmount();
		    	FluidStack fluid = teTank.getInternalTank().getFluid();
		    	int fuildTemp = 0;
		    	if(fluid != null){
		    		fuildTemp = fluid.getFluid().getTemperature();
		    	}
		    	if(worldIn.isRemote){
		    		playerIn.addChatMessage(new TextComponentString(TextFormatting.WHITE + fuelAmountText + " : " + liquidAmount));
		    		playerIn.addChatMessage(new TextComponentString(TextFormatting.WHITE + fuelTempText + " : " + fuildTemp));
		    	}
		    }
	    	
	    	int energy = te.getEnergyStored(null);
		    if(worldIn.isRemote){
	    		playerIn.addChatMessage(new TextComponentString(TextFormatting.WHITE + energyStoredText + " : " + energy + " / " + te.getMaxEnergyStored(null) + " RF"));
	    		playerIn.addChatMessage(new TextComponentString(TextFormatting.WHITE + generatePerTickText + " : " + te.getGeneratePetTick() + " RF"));
	    		playerIn.addChatMessage(new TextComponentString(TextFormatting.YELLOW + "-----"));
	    	}
	    }
        return true;
    }
	
	@Override
	 public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor)
	{
		
	}
	
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

            if (enumfacing == EnumFacing.NORTH && block.isFullBlock(state) && !block1.isFullBlock(state))
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && block1.isFullBlock(state) && !block.isFullBlock(state))
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && block2.isFullBlock(state) && !block3.isFullBlock(state))
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && block3.isFullBlock(state) && !block2.isFullBlock(state))
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }
	
	@Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
		TileEntity te = worldIn.getTileEntity(pos);
		if(te != null){
			StirlingEngineTileEntity engine = (StirlingEngineTileEntity) te;
			NBTTagCompound nbt = stack.getTagCompound();
			if(nbt != null){
				engine.getStorage().setEnergyStored(nbt.getInteger("energy"));
			}
		}
    }
	
	//Render
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
	   return false;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
		return false;
    }
	
	@Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.INVISIBLE;//number 3 for standard block models
    }
	
	/*@Override
    @SideOnly(Side.CLIENT)
    public IBlockState getStateForEntityRender(IBlockState state)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
    }*/
	
	/*@Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
		super.breakBlock(world, pos, state);
		
        if (hasTileEntity(state)) {
            world.removeTileEntity(pos);
        }
    }*/
	
	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest){
		if(world.isRemote){
			 return false;
		 }
		
	    //IBlockState state = world.getBlockState(pos);
	    this.onBlockDestroyedByPlayer(world, pos, state);
	    if(willHarvest) {
	      this.harvestBlock(world, player, pos, state, world.getTileEntity(pos), null);
	    }

	    world.setBlockToAir(pos);
	    return false;
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		
	    List<ItemStack> items = super.getDrops(world, pos, state, fortune);
	    
	    TileEntity te = world.getTileEntity(pos);
	    
	    if(te != null && te instanceof StirlingEngineTileEntity) {
	    	StirlingEngineTileEntity engine = (StirlingEngineTileEntity) te;
	    	for(ItemStack item : items) {
		        if(item.getItem() == Item.getItemFromBlock(this)) {
		          NBTTagCompound tag = new NBTTagCompound();
		          tag.setInteger("energy", engine.getStorage().getEnergyStored());
		          item.setTagCompound(tag);
		          
		        }
		      }
	    }
	    return items;
	}
	
	@Override
	public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis)
    {
        return false;
    }
}
