package tinker_io.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tinker_io.TileEntity.FIMTileEntity;
import tinker_io.main.Main;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FuelInputMachine extends BlockContainer {
	

	@SideOnly(Side.CLIENT)
	private IIcon front;
	
	private final Random random = new Random();
	
	public FuelInputMachine() {
		super(Material.rock);
		setBlockName("FuelInputMachine");
		setCreativeTab(Main.TinkerIOTabs);
		setHarvestLevel("pickaxe", 1);
		setHardness(3);
		setBlockTextureName(Main.MODID + ":" + "asc_side");
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister) {
		this.blockIcon = iconregister.registerIcon(Main.MODID + ":fim_side");
		this.front = iconregister.registerIcon(Main.MODID + ":fim_front");
	}
	
	public IIcon getIcon(int side, int meta) {
		//return side == 3 ? this.front : (side == 0 ? this.blockIcon : (side != meta ? this.blockIcon : this.front));
		
		if(side == 3 && meta == 0){
			return this.front;
		}else{
			if(side != meta){
				return this.blockIcon;				
			}else{
				return this.front;
			}			
		}
		
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		 player.openGui(Main.instance, 0, world, x, y, z);
		 if(!world.isRemote){
			 FIMTileEntity te = (FIMTileEntity) world.getTileEntity(x, y, z);
			 te.checkConnection(world, x, y, z);
         }
		
		 
		
		 
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int par1) {
		return new FIMTileEntity();
	}
	
	@SideOnly(Side.CLIENT)
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		this.direction(world, x, y, z);
		
		FIMTileEntity te = (FIMTileEntity) world.getTileEntity(x, y, z);
		te.checkConnection(world, x, y, z);
	}
	
	private void direction(World world, int x, int y, int z) {
		if (!world.isRemote) {
			Block direction = world.getBlock(x, y, z - 1);
			Block direction1 = world.getBlock(x, y, z + 1);
			Block direction2 = world.getBlock(x - 1, y, z);
			Block direction3 = world.getBlock(x + 1, y, z);
			byte byte0 = 5;

			if (direction.func_149730_j() && direction.func_149730_j()) {
				byte0 = 3;
			}

			if (direction1.func_149730_j() && direction1.func_149730_j()) {
				byte0 = 2;
			}

			if (direction2.func_149730_j() && direction2.func_149730_j()) {
				byte0 = 5;
			}

			if (direction3.func_149730_j() && direction3.func_149730_j()) {
				byte0 = 4;
			}

			world.setBlockMetadataWithNotify(x, y, z, byte0, 3);
			
			
			
		}
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack) {
		byte b0 = 5;
		int direction = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		   if (direction == 0)
	        {
	            b0 = 2;
	        }

	        if (direction == 1)
	        {
	            b0 = 5;
	        }

	        if (direction == 2)
	        {
	            b0 = 3;
	        }

	        if (direction == 3)
	        {
	            b0 = 4;
	        }
	        world.setBlockMetadataWithNotify(x, y, z, b0, 2);

		if (itemstack.hasDisplayName()) {
			((FIMTileEntity) world.getTileEntity(x, y, z)).nameFIM(itemstack.getDisplayName());
		}
	}
	
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
			FIMTileEntity tileentitytutfurnace = (FIMTileEntity) world.getTileEntity(x, y, z);

			if (tileentitytutfurnace != null) {
				for (int i = 0; i < tileentitytutfurnace.getSizeInventory(); ++i) {
					ItemStack itemstack = tileentitytutfurnace.getStackInSlot(i);

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
