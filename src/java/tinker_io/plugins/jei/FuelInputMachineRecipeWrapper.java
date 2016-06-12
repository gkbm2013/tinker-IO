package tinker_io.plugins.jei;

import java.util.List;

import com.google.common.collect.Lists;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;

public class FuelInputMachineRecipeWrapper extends BlankRecipeWrapper {
	
	protected final List<ItemStack> fuelInputList;
	protected ItemStack itemstack;
	
	public FuelInputMachineRecipeWrapper(ItemStack itemstack){
		fuelInputList = Lists.newLinkedList();
		this.itemstack = itemstack;
		setFuelInput();
	}
	
	@Override
	public List<ItemStack> getInputs() {
		if(fuelInputList == null){
			return this.getInputs();
		}
		return fuelInputList;
	}
	
	protected void setFuelInput(){
		fuelInputList.add(itemstack);
	}

}
