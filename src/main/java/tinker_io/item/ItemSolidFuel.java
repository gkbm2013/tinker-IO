package tinker_io.item;

import net.minecraft.item.ItemStack;
import tinker_io.config.TinkerIOConfig;

public class ItemSolidFuel extends ItemBase {
    public ItemSolidFuel(String name) {
        super(name);
    }

    @Override
    public int getItemBurnTime(ItemStack i){
        int solidFuelBurnTime = TinkerIOConfig.CONFIG_TINKER_IO.SolidFuelBurnTime;
        return solidFuelBurnTime;
    }
}
