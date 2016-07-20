package tinker_io.plugins.jei;

import java.awt.Color;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import tinker_io.registry.ItemRegistry;

public class OreCrusherRecipeWrapper extends BlankRecipeWrapper {
	
	protected final List<ItemStack> oreInputList;
	protected final List<ItemStack> outputList;
	
	public OreCrusherRecipeWrapper(String oreDic) {
		oreInputList = Lists.newLinkedList();
		outputList = Lists.newLinkedList();
		setInputList(oreDic);
		setOutputList(oreDic);
	}

	@Override
	public List<ItemStack> getInputs() {
		if(oreInputList == null){
			return this.getInputs();
		}
		return oreInputList;
	}
	
	@Override
	public List<ItemStack> getOutputs() {
		if(outputList == null){
			return this.getOutputs();
		}
		return outputList;
	}
	
	@Override
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		String s = "45 RF/tick";
	    int x = 15;
	    x -= minecraft.fontRendererObj.getStringWidth(s)/2;

	    /*String s2 = "100% + 1";
	    int x2 = 118;
	    x2 -= minecraft.fontRendererObj.getStringWidth(s2)/2;*/

	    
	    minecraft.fontRendererObj.drawString(s, x, 57, Color.white.getRGB());
	    //minecraft.fontRendererObj.drawString(s2, x2, 45, Color.red.getRGB());
	}

	protected void setInputList(String oreDic){
		List<ItemStack> oreList = OreDictionary.getOres(oreDic);
		
		oreList.stream().forEach(ore -> oreInputList.add(ore));
	}
	
	protected void setOutputList(String oreDic){
		outputList.add(getProduce(oreDic));
	}
	
	private ItemStack getProduce(String oreDic){
		ItemStack produce = new ItemStack(ItemRegistry.CrushedOre, 3);
		produce.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbt = produce.getTagCompound();
		nbt.setString("oreDic", oreDic);
		
		return produce;
	}
	
	public String getOreDicName(ItemStack itemStack){
		String oreDicName = null;
		if(itemStack != null && OreDictionary.getOreIDs(itemStack).length > 0){
			int oreID = OreDictionary.getOreIDs(itemStack)[0];
			oreDicName = OreDictionary.getOreName(oreID);
		}
		return oreDicName;
	}
	
	
}
