package tinker_io.handler;

import java.awt.Color;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class CrushedOreColorList {
	public CrushedOreColorList(){}
	public int getColor(ItemStack itemStack){
		  int colorRGB = Color.gray.getRGB();
		  NBTTagCompound nbt = itemStack.getTagCompound();
			if(nbt != null){
				String oreDic = nbt.getString("oreDic");
				if(oreDic.equals("oreIron")){colorRGB = hex2Rgb("#B39886").getRGB();}
				if(oreDic.equals("oreGold")){colorRGB = hex2Rgb("#FCEE4B").getRGB();}
			}
		  return colorRGB;
	  }
	  
	public static Color hex2Rgb(String colorStr) {
		    return new Color(
		            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
		            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
		            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
		}
}
