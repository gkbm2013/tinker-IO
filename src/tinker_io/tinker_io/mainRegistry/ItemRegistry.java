package tinker_io.mainRegistry;

import net.minecraftforge.fml.common.registry.GameRegistry;
import tinker_io.items.CD_Lonesome_Avenue;
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
	public static Item Lonesome_Avenue;

	private static void preLoadItem() {
		SpeedUPG = new SpeedUPG();
		SolidFuel = new SolidFuel();
		Upgrade = new Upgrade("upg");
		Lonesome_Avenue= new CD_Lonesome_Avenue("Lonesome_Avenue");
		
	}
	
	private static void registerItem() {
		GameRegistry.registerItem(SpeedUPG, "SpeedUPG");
		GameRegistry.registerItem(SolidFuel, "SolidFuel");
		GameRegistry.registerItem(Upgrade, "Upgrade");
		GameRegistry.registerItem(Lonesome_Avenue, "CD_Lonesome_Avenue");
	}
}
