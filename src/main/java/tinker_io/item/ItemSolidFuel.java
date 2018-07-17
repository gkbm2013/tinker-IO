package tinker_io.item;

import net.minecraft.item.ItemStack;

public class ItemSolidFuel extends ItemBase {
    public ItemSolidFuel(String name) {
        super(name);
    }

    @Override
    public int getItemBurnTime(ItemStack i){
        //TODO Config burn time
//        int solidFuelBurnTime = TIOConfig.solidFuelBurnTime;
        int solidFuelBurnTime = 1000;
        return solidFuelBurnTime;
    }
}
