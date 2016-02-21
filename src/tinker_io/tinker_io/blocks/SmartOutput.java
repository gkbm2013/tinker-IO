package tinker_io.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mantle.blocks.iface.IServantLogic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import tconstruct.smeltery.logic.LavaTankLogic;
import tinker_io.TileEntity.SOTileEntity;
import tinker_io.main.Main;

public class SmartOutput extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private IIcon side;
	private IIcon top;
	
	public SmartOutput() {
		super(Material.rock);
		setHardness(3F);
        setResistance(20F);
        setCreativeTab(Main.TinkerIOTabs);
        this.setBlockName("SmartOutput");
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister) {
		this.blockIcon = iconregister.registerIcon(Main.MODID + ":SO_side");
		this.side = iconregister.registerIcon(Main.MODID + ":fim_side");
		this.top = iconregister.registerIcon(Main.MODID + ":SO_top");
	}
	
	public IIcon getIcon(int side, int meta) {
		//return side == 3 ? this.front : (side == 0 ? this.blockIcon : (side != meta ? this.blockIcon : this.front));
		if(side == 0){
			return this.side;
		}else if(side == 1){
			return this.top;
		}else{
			return this.blockIcon;
		}
	}
	
	private final Random random = new Random();
	
	@Override
    public boolean isOpaqueCube ()
    {
        return false;
    }
	
	@Override
    public boolean onBlockActivated (World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
		fillAndDrain(world, x, y, z, player);		
		return true;
    }
	
	  public void fillAndDrain(World world, int x, int y, int z, EntityPlayer entityplayer){
		  ItemStack currentItem = entityplayer.inventory.getCurrentItem();
			if(currentItem != null){
				FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(currentItem);
				SOTileEntity te = (SOTileEntity)world.getTileEntity(x, y, z);
				if(liquid != null){
					int amount = te.fill(ForgeDirection.UNKNOWN, liquid, false);
					if (amount == liquid.amount){
						te.fill(ForgeDirection.UNKNOWN, liquid, true);
						if (!entityplayer.capabilities.isCreativeMode){
							entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, consumeItem(currentItem));
						}
					}else{
						entityplayer.openGui(Main.instance, 1, world, x, y, z);
					}
				}else if(FluidContainerRegistry.isBucket(currentItem)){
					FluidTankInfo[] tanks = te.getTankInfo(ForgeDirection.UNKNOWN);
	                FluidStack fillFluid = tanks[0].fluid;
	                ItemStack fillStack = FluidContainerRegistry.fillFluidContainer(fillFluid, currentItem);
	                if (fillStack != null){
	                	te.drain(ForgeDirection.UNKNOWN, FluidContainerRegistry.getFluidForFilledItem(fillStack).amount, true);
	                	if (!entityplayer.capabilities.isCreativeMode){
	                		if (currentItem.stackSize == 1){
	                			entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, fillStack);
	                		}else{
	                			entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, consumeItem(currentItem));
	                			
	                			if (!entityplayer.inventory.addItemStackToInventory(fillStack)){
	                                entityplayer.dropPlayerItemWithRandomChoice(fillStack, false);
	                            }
	                		}
	                	}
	                }else{
	                	entityplayer.openGui(Main.instance, 1, world, x, y, z);
	                }
				}else{
					entityplayer.openGui(Main.instance, 1, world, x, y, z);
				}
			}else{
				entityplayer.openGui(Main.instance, 1, world, x, y, z);
			}
	  }
	
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

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new SOTileEntity();
	}
	
	public void onNeighborBlockChange (World world, int x, int y, int z, Block nBlockID)
    {
        TileEntity logic = world.getTileEntity(x, y, z);
        if (logic instanceof IServantLogic)
        {
            ((IServantLogic) logic).notifyMasterOfChange();
        }
    }
	
	protected void dropTankBlock (World world, int x, int y, int z, ItemStack stack)
    {
        if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops"))
        {
            float f = 0.7F;
            double d0 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d1 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d2 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, (double) x + d0, (double) y + d1, (double) z + d2, stack);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
        }
    }
	
	@Override
    public void onBlockPlacedBy (World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
    {
        if (stack.hasTagCompound())
        {
            NBTTagCompound liquidTag = stack.getTagCompound().getCompoundTag("Fluid");
            if (liquidTag != null)
            {
                FluidStack liquid = FluidStack.loadFluidStackFromNBT(liquidTag);
                LavaTankLogic logic = (LavaTankLogic) world.getTileEntity(x, y, z);
                logic.tank.setFluid(liquid);
            }
        }
        SOTileEntity tileSO = (SOTileEntity) world.getTileEntity(x, y, z);
        if(tileSO != null){
        	tileSO.getCoord(x, y, z);
        }
    }
	
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		SOTileEntity tileSO = (SOTileEntity) world.getTileEntity(x, y, z);

		if (tileSO != null) {
			for (int i = 0; i < tileSO.getSizeInventory(); ++i) {
				ItemStack itemstack = tileSO.getStackInSlot(i);

				if (itemstack != null) {
					float f = this.random.nextFloat() * 0.6F + 0.1F;
					float f1 = this.random.nextFloat() * 0.6F + 0.1F;
					float f2 = this.random.nextFloat() * 0.6F + 0.1F;

					while (itemstack.stackSize > 0) {
						int j = this.random.nextInt(21) + 10;

						if (j > itemstack.stackSize) {
							j = itemstack.stackSize;
						}

						itemstack.stackSize -= j;
						EntityItem entityitem = new EntityItem(world, (double) ((float) x + f), (double) ((float) y + f1), (double) ((float) z + f2), new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));

						if (itemstack.hasTagCompound()) {
							entityitem.getEntityItem().setTagCompound(((NBTTagCompound) itemstack.getTagCompound().copy()));
						}

						float f3 = 0.025F;
						entityitem.motionX = (double) ((float) this.random.nextGaussian() * f3);
						entityitem.motionY = (double) ((float) this.random.nextGaussian() * f3 + 0.1F);
						entityitem.motionZ = (double) ((float) this.random.nextGaussian() * f3);
						world.spawnEntityInWorld(entityitem);
					}
				}
			}
			world.func_147453_f(x, y, z, block);
		}
	super.breakBlock(world, x, y, z, block, meta);
}
	
	

}

