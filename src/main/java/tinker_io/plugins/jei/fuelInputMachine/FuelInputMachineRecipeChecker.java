package tinker_io.plugins.jei.fuelInputMachine;

import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

import java.util.List;

public class FuelInputMachineRecipeChecker {

    public static List<FuelInputMachineRecipeWrapper> getFuel() {
        List<FuelInputMachineRecipeWrapper> list = Lists.newLinkedList();
        for(Item item : Item.REGISTRY) {
            ItemStack itemstack = new ItemStack(item);
            if(TileEntityFurnace.isItemFuel(itemstack)){
                list.add(new FuelInputMachineRecipeWrapper(itemstack));
            }
        }
        return list;
    }

}
