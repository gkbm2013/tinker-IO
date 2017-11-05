package tinker_io.items;

import tinker_io.config.TIOConfig;
import tinker_io.main.Main;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SolidFuel extends Item {
	public SolidFuel(String unlocalizedName){
		super();
		this.setUnlocalizedName(unlocalizedName);
        this.setMaxDamage(0);
		this.setCreativeTab(Main.TinkerIOTabs);
	}
	
	@Override
	public int getItemBurnTime(ItemStack i){
		int solidFuelBurnTime = TIOConfig.solidFuelBurnTime;
		return solidFuelBurnTime;
	}
}
