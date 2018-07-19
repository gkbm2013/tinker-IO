package tinker_io.registry;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import tinker_io.TinkerIO;
import tinker_io.item.*;

public class ItemRegistry {

    public static ItemCdLonesomeAvenue cdLonesomeAvenue = new ItemCdLonesomeAvenue("Lonesome_Avenue");
    public static ItemUpgrade upgrade = new ItemUpgrade("upg");
    //TODO Change name of solid fuel, Speed Upg and Crushed Ore
    public static ItemSolidFuel solidFuel = new ItemSolidFuel("SolidFuel");
    public static ItemSpeedUpgrade speedUpgrade = new ItemSpeedUpgrade("speedUPG");
    public static ItemCrushedOre crushedOre = new ItemCrushedOre("Crushed_ore");



    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(
                cdLonesomeAvenue,
                upgrade,
                solidFuel,
                speedUpgrade,
                crushedOre
        );
    }

    public static void registerModels() {
        TinkerIO.proxy.registerItemRenderer(cdLonesomeAvenue, 0, "cd_lonesome_avenue");
        upgrade.registerItemModel();
        solidFuel.registerItemModel("solid_fuel");
        speedUpgrade.registerItemModel("speed_upg");
        crushedOre.registerItemModel("crushed_ore");
    }

    public static void registerItemColors(){
        crushedOre.registerItemColor(new ItemCrushedOre.ItemColor());
    }
}
