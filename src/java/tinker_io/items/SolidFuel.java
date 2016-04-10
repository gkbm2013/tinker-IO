package tinker_io.items;

import tinker_io.main.Main;
import net.minecraft.item.Item;

public class SolidFuel extends Item {
	public SolidFuel(String unlocalizedName){
		super();
		this.setUnlocalizedName(unlocalizedName);
        this.setMaxDamage(0);
		this.setCreativeTab(Main.TinkerIOTabs);
	}
}
