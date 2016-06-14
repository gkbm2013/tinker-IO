package tinker_io.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tinker_io.TileEntity.TileEntityContainerAdapter;
import tinker_io.main.Main;

public abstract class BlockContainerAdapter extends BlockContainer{
	
	public final static EnumBlockRenderType renderTypeForStandardBlockModels = EnumBlockRenderType.MODEL;
	
	protected BlockContainerAdapter() {
		super(Material.ROCK);
		setHarvestLevel("pickaxe", 1);
		setHardness(3);
		setCreativeTab(Main.TinkerIOTabs);
	}
	
	 @Override public abstract boolean onBlockActivated(
			 World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ);
	
	  @Override
	  public boolean hasTileEntity(IBlockState state) {
	    return true;
	  }
	  
	/**
     * The type of render function called.
     * 3 for standard block models,
     * 2 for TESR's,
     * 1 for liquids,
     * -1 is no render.
     * 
     * We need number 3.
     */
	@Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return renderTypeForStandardBlockModels;
    }
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
	    TileEntity tile = worldIn.getTileEntity(pos);

	    if(tile instanceof TileEntityContainerAdapter) {
	      InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tile);
	      worldIn.updateComparatorOutputLevel(pos, this);
	    }
	    super.breakBlock(worldIn, pos, state);
	  }

}
