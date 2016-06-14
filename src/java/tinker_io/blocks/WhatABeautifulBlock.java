package tinker_io.blocks;

//import java.util.Random;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
//import tinker_io.TileEntity.TileEntityWhatABeautifulBlockEntity;
import tinker_io.main.Main;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
//import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
//import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.world.World;

public class WhatABeautifulBlock extends Block{

	//private static final String __OBFID = "CL_00000065";
	
	public WhatABeautifulBlock(String unlocalizedName) {
		super(Material.GLASS);
		setHardness(3F);
        setResistance(20F);
        setCreativeTab(Main.TinkerIOTabs);
        this.setSoundType(SoundType.GLASS);
        setLightLevel(1.0F);
        this.setUnlocalizedName(unlocalizedName);
	}
	
	 @Override
     public boolean isOpaqueCube(IBlockState state) {
             return false;
     }
	 
	 public boolean renderAsNormalBlock() {
         return true;
	 }

	 public MapColor getMapColor(int p_149728_1_)
	    {
	        return MapColor.SAND;
	    }
	 
	 @SideOnly(Side.CLIENT)
	    public boolean hasEffect(ItemStack p_77636_1_)
	    {
	        return true;
	    }
}
