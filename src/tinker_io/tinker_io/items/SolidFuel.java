package tinker_io.items;

import tinker_io.main.Main;
import net.minecraft.item.Item;

public class SolidFuel extends Item {
	public SolidFuel(String unlocalizedName){
		super();
		setUnlocalizedName(unlocalizedName);
		setCreativeTab(Main.TinkerIOTabs);
	}
}
