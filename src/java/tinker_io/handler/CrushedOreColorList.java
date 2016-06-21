package tinker_io.handler;

import java.awt.Color;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class CrushedOreColorList {
	public CrushedOreColorList(){}
	
	private Minecraft mc = Minecraft.getMinecraft();
	
	int deBUG = 0;
	
	/**
	 * 
	 * @param itemStack A ItemStack with string NBT Tag "oreDic". 
	 * @return The color from the oreDic.
	 */
	public int getColor(ItemStack itemStack){
		  int colorRGB = Color.gray.getRGB();
		  
		  NBTTagCompound nbt = itemStack.getTagCompound();
			if(nbt != null){
				String oreDicName = nbt.getString("oreDic");
				ItemStack oreItem;
				
				if(this.getOreByOreDic(oreDicName) != null && !this.getOreByOreDic(oreDicName).isEmpty()){
					oreItem = this.getOreByOreDic(oreDicName).get(0);
				}else{
					return colorRGB;
				}
				
				TextureAtlasSprite particleIcon = mc.getRenderItem().getItemModelMesher().getParticleIcon(oreItem.getItem(), oreItem.getMetadata());
				if(particleIcon != null){					
					int[] frameTextureData = particleIcon.getFrameTextureData(0)[0];
					colorRGB = frameTextureData[151];
				}
				
				/*if(oreDicName.equals("oreIron")){colorRGB = hex2Rgb("#B39886").getRGB();}
				if(oreDicName.equals("oreGold")){colorRGB = hex2Rgb("#FCEE4B").getRGB();}*/
			}
		  return colorRGB;
	  }
	
	/**
	 * 
	 * @param fluidStack A FluidStack with string NBT Tag "oreDic". 
	 * @return The color from the oreDic.
	 */
	public int getColor(FluidStack fluidStack){
		  int colorRGB = Color.gray.getRGB();
		  
		  NBTTagCompound nbt = fluidStack.tag;
			if(nbt != null){
				String oreDicName = nbt.getString("oreDic");
				ItemStack oreItem;
				
				if(this.getOreByOreDic(oreDicName) != null && !this.getOreByOreDic(oreDicName).isEmpty()){
					oreItem = this.getOreByOreDic(oreDicName).get(0);
				}else{
					return colorRGB;
				}
				
				//return this.getColor(oreItem);
				
				TextureAtlasSprite particleIcon = mc.getRenderItem().getItemModelMesher().getParticleIcon(oreItem.getItem(), oreItem.getMetadata());
				if(particleIcon != null){					
					int[] frameTextureData = particleIcon.getFrameTextureData(0)[0];
					colorRGB = frameTextureData[151];
				}
			}
		  return colorRGB;
	  }
	  
	public static Color hex2Rgb(String colorStr) {
		    return new Color(
		            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
		            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
		            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
	}
	
	public List<ItemStack> getOreByOreDic(String oreDicName){
		return OreDictionary.getOres(oreDicName);
	}
}
