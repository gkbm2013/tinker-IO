package tinker_io.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tinker_io.TileEntity.TileEntityWhatABeautifulBlockEntity;
import tinker_io.main.Main;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class WhatABeautifulBlock extends Block{

	private static final String __OBFID = "CL_00000065";
	
	public WhatABeautifulBlock() {
		super(Material.grass);
		setHardness(3F);
        setResistance(20F);
        setCreativeTab(Main.TinkerIOTabs);
        setStepSound(soundTypeGlass);
        setLightLevel(1.0F);
        
        setBlockTextureName(Main.MODID + ":" + "whatABeautifulBlock");
        this.setBlockName("WhatABeautifulBlock");
	}
	
	 @Override
     public boolean isOpaqueCube() {
             return false;
     }
	 
	 public boolean renderAsNormalBlock() {
         return true;
	 }

	 public MapColor getMapColor(int p_149728_1_)
	    {
	        return MapColor.sandColor;
	    }
	 
	 @SideOnly(Side.CLIENT)
	    public boolean hasEffect(ItemStack p_77636_1_)
	    {
	        return true;
	    }
}
