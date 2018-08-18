package tinker_io.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class OreCrusherRecipe {

    public static final String TAG_ITEMSTACK_INPUT = "itemStackInput";
    public static final String TAG_ORE_DICT_INPUT = "oreDictInput";
    public static final String TAG_ITEMSTACK_OUTPUT = "itemStackOutput";

    public static final OreCrusherRecipe EMPTY = new OreCrusherRecipe();

    private ItemStack itemStackInput = ItemStack.EMPTY;
    private ItemStack itemStackOutput = ItemStack.EMPTY;
    private String oreDictInput;

    public OreCrusherRecipe() {}

    public OreCrusherRecipe(ItemStack itemStackInput, ItemStack itemStackOutput) {
        this.itemStackInput = itemStackInput;
        this.itemStackOutput = itemStackOutput;
    }

    public OreCrusherRecipe(String oreDictInput, ItemStack itemStackOutput) {
        this.oreDictInput = oreDictInput;
        this.itemStackOutput = itemStackOutput;
    }

    public boolean match(ItemStack itemStack) {
        if(itemStack == null || itemStack.isEmpty()) {
            return false;
        }
        if(!itemStackInput.isEmpty() && itemStack.isItemEqual(itemStackInput)) {
            return true;
        }
        int ids[] = OreDictionary.getOreIDs(itemStack);
        if(ids.length > 0){
            String oreName = OreDictionary.getOreName(OreDictionary.getOreIDs(itemStack)[0]);
            return oreName.equals(oreDictInput);
        }else{
            return false;
        }
    }

    public ItemStack getItemStackOutput() {
        return itemStackOutput;
    }

    public boolean isEmpty() {
        return (itemStackInput.isEmpty() && oreDictInput == null) || itemStackOutput.isEmpty();
    }

    public ItemStack getItemStackInput() {
        return itemStackInput;
    }

    public String getOreDictInput() {
        return oreDictInput;
    }

    public OreCrusherRecipe copy() {
        if(oreDictInput != null) {
            return new OreCrusherRecipe(oreDictInput, itemStackOutput);
        } else if(!itemStackInput.isEmpty()) {
            return new OreCrusherRecipe(itemStackInput, itemStackOutput);
        } else {
            return OreCrusherRecipe.EMPTY;
        }
    }
}
