package tinker_io.items;

import java.util.List;

import tinker_io.main.Main;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class Upgrade extends Item {
	public Upgrade(String unlocalizedName){
		super();
		setUnlocalizedName(unlocalizedName);
		setCreativeTab(Main.TinkerIOTabs);
		//setTextureName(Main.MODID + ":" + "Upgrade");
		setHasSubtypes(true);
		setMaxStackSize(8);
	}
	
	public IIcon[] icons = new IIcon[6];
	
	@Override
	public void registerIcons(IIconRegister reg) {
		this.icons[0] = reg.registerIcon(Main.MODID + ":" + "UPG_Base");
		this.icons[1] = reg.registerIcon(Main.MODID + ":" + "UPG_slot1");
		this.icons[2] = reg.registerIcon(Main.MODID + ":" + "UPG_slot2");
		this.icons[3] = reg.registerIcon(Main.MODID + ":" + "UPG_slot3");
		this.icons[4] = reg.registerIcon(Main.MODID + ":" + "UPG_slot4");
		this.icons[5] = reg.registerIcon(Main.MODID + ":" + "UPG_Redstone");
	}
	
	@Override
	public IIcon getIconFromDamage(int meta) {
	    if (meta > 5)
	        meta = 0;

	    return this.icons[meta];
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
	    for (int i = 0; i < 6; i ++) {
	        list.add(new ItemStack(item, 1, i));
	    }
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
	    return this.getUnlocalizedName() + "_" + stack.getItemDamage();
	}
	
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
		//list.add(EnumChatFormatting.GREEN + "code: HA~");
	}

}
