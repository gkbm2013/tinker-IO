package tinker_io.items;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import tinker_io.main.Main;

public class CrushedOre extends Item{
	public CrushedOre(String unlocalizedName){
		super();
		setUnlocalizedName(unlocalizedName);
		setCreativeTab(Main.TinkerIOTabs);
	}
	
	public CrushedOre(){}
	
	/*private ModelResourceLocation setLocation(String filename){
		return new ModelResourceLocation(Main.MODID + ":" + this.getUnlocalizedName().substring(5)+ "_" + filename, "inventory");
	}*/
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
		if (stack.getTagCompound() != null) {
            String oreDic = stack.getTagCompound().getString("oreDic");
            tooltip.add(TextFormatting.RED + "oreDic : "+oreDic);   
        }
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack)
    {
		String name = ("" + I18n.format(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
		
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt != null){
			String oreDicName = nbt.getString("oreDic");
			List<ItemStack> oreList = OreDictionary.getOres(oreDicName);
			if(!oreList.isEmpty()){
				ItemStack oreItem = oreList.get(0);
				String oreName = oreItem.getDisplayName();
				name = name + " (" + oreName + ")";
			}
		}
		return name;
    }
}