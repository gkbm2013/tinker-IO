package tinker_io.registry;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import tinker_io.TinkerIO;
import tinker_io.item.ItemCdLonesomeAvenue;
import tinker_io.item.ItemSolidFuel;
import tinker_io.item.ItemSpeedUpgrade;
import tinker_io.item.ItemUpgrade;

public class ItemRegistry {

    public static ItemCdLonesomeAvenue cdLonesomeAvenue = new ItemCdLonesomeAvenue("Lonesome_Avenue");
    public static ItemUpgrade upgrade = new ItemUpgrade("upg");
    //TODO Change name of solid fuel and Speed U[g
    public static ItemSolidFuel solidFuel = new ItemSolidFuel("SolidFuel");
    public static ItemSpeedUpgrade speedUpgrade = new ItemSpeedUpgrade("speedUPG");



    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(
                cdLonesomeAvenue,
                upgrade,
                solidFuel,
                speedUpgrade
        );
    }

    public static void registerModels() {
        TinkerIO.proxy.registerItemRenderer(cdLonesomeAvenue, 0, "cd_lonesome_avenue");
        upgrade.registerItemModel();
        solidFuel.registerItemModel("solid_fuel");
        speedUpgrade.registerItemModel("speed_upg");
    }
}
