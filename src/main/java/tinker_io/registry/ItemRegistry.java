package tinker_io.registry;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import tinker_io.TinkerIO;
import tinker_io.item.ItemCdLonesomeAvenue;
import tinker_io.item.ItemUpgrade;

public class ItemRegistry {

    public static ItemCdLonesomeAvenue cdLonesomeAvenue = new ItemCdLonesomeAvenue("Lonesome_Avenue");
    public static ItemUpgrade upgrade = new ItemUpgrade("upg");

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(
                cdLonesomeAvenue,
                upgrade
        );
    }

    public static void registerModels() {
        TinkerIO.proxy.registerItemRenderer(cdLonesomeAvenue, 0, "cd_lonesome_avenue");
        upgrade.registerItemModel();
    }
}
