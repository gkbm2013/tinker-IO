package tinker_io.registry;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import tinker_io.handler.SmartOutputRecipeHandler;
import tinker_io.helper.CrushedOreHelper;

import java.util.List;

public class SmartOutputRecipeReigster {
    public static void register() {
        registerPureMetal();
    }

    private static void registerPureMetal() {
        MeltingRecipeRegister.customMeltingRecipes.forEach(recipe -> {
            FluidStack fluidStack = recipe.getResult();
            ItemStack output = getOutput(fluidStack);
            if(output != null) {
                SmartOutputRecipeHandler.registerSmartOutputCasting(output, TinkerSmeltery.castIngot, fluidStack);
            }
        });
    }

    private static ItemStack getOutput(FluidStack stack){
        if(stack == null){return null;}
        NBTTagCompound nbt = stack.tag;
        ItemStack oreStack;
        ItemStack resultStack = null;

        if(nbt != null){
            oreStack = getItemByOreDic(nbt.getString(CrushedOreHelper.TAG_ORE_DICT));
            if(oreStack != null){
                resultStack = FurnaceRecipes.instance().getSmeltingResult(oreStack);
            }
        }

        return resultStack;
    }

    private static ItemStack getItemByOreDic(String oreDic){
        List<ItemStack> oreStack = OreDictionary.getOres(oreDic);

        ItemStack result = null;

        if(!oreStack.isEmpty()){
            result = oreStack.get(0);
        }
        return result;
    }
}
