package tinker_io.handler.color;

import java.awt.Color;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import tinker_io.handler.CrushedOreColorList;

public class CrushedOreItemColorHandler implements IItemColor{

	/*@Override
	public int getColorFromItemstack(ItemStack stack, int renderLayer) {
		CrushedOreColorList colorlist = new CrushedOreColorList();
	    switch (renderLayer) {
	    	case 0: return Color.WHITE.getRGB();
	    	case 1: {
	    		return colorlist.getColor(stack);
	    	}
	    	default: {
	    		// oops! should never get here.
	    		return Color.black.getRGB();
	    	}
	    }
	}*/

	@Override
	public int colorMultiplier(ItemStack stack, int tintIndex) {
		CrushedOreColorList colorlist = new CrushedOreColorList();
	    switch (tintIndex) {
	    	case 0: return Color.WHITE.getRGB();
	    	case 1: {
	    		return colorlist.getColor(stack);
	    	}
	    	default: {
	    		// oops! should never get here.
	    		return Color.black.getRGB();
	    	}
	    }
	}

}
