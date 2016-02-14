package tinker_io.blocks;

//import java.util.Random;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.multiblock.IServantLogic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
//import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
/*import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;*/
//import slimeknights.tconstruct.smeltery.logic.LavaTankLogic;1.7
import tinker_io.TileEntity.SOTileEntity;
import tinker_io.TileEntity.fim.FIMTileEntity;
import tinker_io.main.Main;

public class SmartOutput extends BlockContainer {

	public SmartOutput(String unlocalizedName) {
		super(Material.rock);
		this.setHardness(3F);
        this.setResistance(20F);
        this.setCreativeTab(Main.TinkerIOTabs);
        this.setUnlocalizedName(unlocalizedName);
	}
	
	@Override
    public int getRenderType()
    {
        return 3 ;//number 3 for standrd block models
    }
	
	@Override
    public boolean isOpaqueCube ()
    {
        return false;
    }
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
    {
//		fillAndDrain(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn);
		if (!worldIn.isRemote) {
			playerIn.openGui(Main.instance, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
        return true;
    }

//	  public void fillAndDrain(World world, int x, int y, int z, EntityPlayer entityplayer){
//		  ItemStack currentItem = entityplayer.inventory.getCurrentItem();
//			if(currentItem != null){
//				FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(currentItem);
//				SOTileEntity te = (SOTileEntity)world.getTileEntity(x, y, z);
//				if(liquid != null){
//					int amount = te.fill(ForgeDirection.UNKNOWN, liquid, false);
//					if (amount == liquid.amount){
//						te.fill(ForgeDirection.UNKNOWN, liquid, true);
//						if (!entityplayer.capabilities.isCreativeMode){
//							entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, consumeItem(currentItem));
//						}
//					}else{
//						entityplayer.openGui(Main.instance, 1, world, x, y, z);
//					}
//				}else if(FluidContainerRegistry.isBucket(currentItem)){
//					FluidTankInfo[] tanks = te.getTankInfo(ForgeDirection.UNKNOWN);
//	                FluidStack fillFluid = tanks[0].fluid;
//	                ItemStack fillStack = FluidContainerRegistry.fillFluidContainer(fillFluid, currentItem);
//	                if (fillStack != null){
//	                	te.drain(ForgeDirection.UNKNOWN, FluidContainerRegistry.getFluidForFilledItem(fillStack).amount, true);
//	                	if (!entityplayer.capabilities.isCreativeMode){
//	                		if (currentItem.stackSize == 1){
//	                			entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, fillStack);
//	                		}else{
//	                			entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, consumeItem(currentItem));
//	                			
//	                			if (!entityplayer.inventory.addItemStackToInventory(fillStack)){
//	                                entityplayer.dropPlayerItemWithRandomChoice(fillStack, false);
//	                            }
//	                		}
//	                	}
//	                }else{
//	                	entityplayer.openGui(Main.instance, 1, world, x, y, z);
//	                }
//				}else{
//					entityplayer.openGui(Main.instance, 1, world, x, y, z);
//				}
//			}else{
//				entityplayer.openGui(Main.instance, 1, world, x, y, z);
//			}
//	  }
	
	public static ItemStack consumeItem (ItemStack stack)
    {
        if (stack.stackSize == 1)
        {
            if (stack.getItem().hasContainerItem())
                return stack.getItem().getContainerItem(stack);
            else
                return null;
        }
        else
        {
            stack.splitStack(1);

            return stack;
        }
    }

	//@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new SOTileEntity();
	}

	@Override
	 public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor)
	{
        TileEntity logic = world.getTileEntity(pos);
        if (logic instanceof IServantLogic)
        {
            ((IServantLogic) logic).notifyMasterOfChange();
        }
	}
	
	
//	protected void dropTankBlock (World world, int x, int y, int z, ItemStack stack)
//    {
//        if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops"))
//        {
//            float f = 0.7F;
//            double d0 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
//            double d1 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
//            double d2 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
//            EntityItem entityitem = new EntityItem(world, (double) x + d0, (double) y + d1, (double) z + d2, stack);
//            entityitem.delayBeforeCanPickup = 10;
//            world.spawnEntityInWorld(entityitem);
//        }
//    }
	
    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
	@Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
//		if (stack.hasTagCompound())
//        {
//            NBTTagCompound liquidTag = stack.getTagCompound().getCompoundTag("Fluid");
//            if (liquidTag != null)
//            {
//                FluidStack liquid = FluidStack.loadFluidStackFromNBT(liquidTag);
//                LavaTankLogic logic = (LavaTankLogic) world.getTileEntity(x, y, z);
//                logic.tank.setFluid(liquid);
//            }
//        }
    }
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
		TileEntity tile =  worldIn.getTileEntity(pos);

	    if(tile instanceof SOTileEntity) {
		      InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tile);
		      worldIn.updateComparatorOutputLevel(pos, this);
		    }
	super.breakBlock(worldIn, pos, state);
    }
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		
	}
	
	  /**
     * Called on both Client and Server when World#addBlockEvent is called
     */
	@Override
    public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam)
    {
        super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
    }
}

