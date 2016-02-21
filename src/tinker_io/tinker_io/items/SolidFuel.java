package tinker_io.items;

import tinker_io.main.Main;
import net.minecraft.item.Item;

public class SolidFuel extends Item {
	public SolidFuel(){
		super();
		setUnlocalizedName("SolidFuel");
		setCreativeTab(Main.TinkerIOTabs);
		setTextureName(Main.MODID + ":" + "solidFuel");
	}

}
