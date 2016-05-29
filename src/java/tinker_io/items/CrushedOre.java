package tinker_io.items;

import java.awt.Color;
import java.util.List;

import tinker_io.handler.CrushedOreColorList;
import tinker_io.main.Main;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CrushedOre extends Item {
	public CrushedOre(String unlocalizedName){
		super();
		setUnlocalizedName(unlocalizedName);
		setCreativeTab(Main.TinkerIOTabs);
	}
	
	// when rendering, choose the colour multiplier based on the contents
	  // we want layer 0 (the bottle glass) to be unaffected (return white as the multiplier)
	  // layer 1 will change colour depending on the contents.
	  @SideOnly(Side.CLIENT)
	  public int getColorFromItemStack(ItemStack itemStack, int renderLayer){
		  CrushedOreColorList colorlist = new CrushedOreColorList();
	    switch (renderLayer) {
	      case 0: return Color.WHITE.getRGB();
	      case 1: {
	    	  return colorlist.getColor(itemStack);
	      }
	      default: {
	        // oops! should never get here.
	    	  return Color.black.getRGB();
	      }
	    }
	  }
	  
	  
	
	private ModelResourceLocation setLocation(String filename){
		return new ModelResourceLocation(Main.MODID + ":" + this.getUnlocalizedName().substring(5)+ "_" + filename, "inventory");
	}
	
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
		if (itemStack.getTagCompound() != null) {
            String oreDic = itemStack.getTagCompound().getString("oreDic");
            list.add(EnumChatFormatting.RED + "oreDic : "+oreDic);   
        }
		
	}
}