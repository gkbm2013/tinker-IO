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
			 te.checkConnection();
         }
		
		 
		int power = world.getBlockPowerInput(x, y, z);
     	int strongestsPower = world.getStrongestIndirectPower(x, y, z);
     	//System.out.println(power);
     	if(power != 0 && strongestsPower != 0){
     		String name = player.getDisplayName();
   		 if(name.equals("frankboy89722")){
   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u55ef?\u9019\u4e0d\u662f\u9999\u8349\u55ce? \u6211\u731c\u8eab\u5206\u8b49\u7684\u7b2c100\u4f4d\u6578\u5b57\u53ef\u4ee5\u8fa8\u8b58\u6027\u5225..."));
   		 }else if(name.equals("KwongFong")){
   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u98a8\u7684\u50b3\u4eba : \u9059\u9060\u7684\u6771\u65b9\u6709\u4e00\u9663\u98a8\uff0c\u4ed6\u7684\u540d\u5b57\u5c31\u53eb\u72c2\u98a8..."));
   		 }else if(name.equals("alan6716")){
   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "HI \u609f\u8a22~ \u5077\u5077\u544a\u8a34\u4f60\u4e00\u4ef6\u4e8b : Alan's fuel was stolen by Alien!"));
   		 }else if(name.equals("gkbXkinG")){
   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "\u4f60\u597d~"));
   		 }else if(name.equals("eating555")){
   			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "HI eating555! \u6211\u662fGKB~"));
   		 }
     	}
     	//player.addChatMessage(new ChatComponentText(name));
		 
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
		te.checkConnection();
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
