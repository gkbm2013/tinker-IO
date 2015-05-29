package tinker_io.mainRegistry;

import cpw.mods.fml.common.registry.GameRegistry;
import tinker_io.items.SolidFuel;
import tinker_io.items.SpeedUPG;
import tinker_io.items.Upgrade;
import net.minecraft.item.Item;

public class ItemRegistry {
	public static void mainRegistry() {
		preLoadItem();
		registerItem();
	}
	
	public static Item SpeedUPG;
	public static Item SolidFuel;
	public static Item Upgrade;

	private static void preLoadItem() {
		SpeedUPG = new SpeedUPG();
		SolidFuel = new SolidFuel();
		Upgrade = new Upgrade("upg");
		
	}
	
	private static void registerItem() {
		GameRegistry.registerItem(SpeedUPG, "SpeedUPG");
		GameRegistry.registerItem(SolidFuel, "SolidFuel");
		GameRegistry.registerItem(Upgrade, "Upgrade");
	}
}
