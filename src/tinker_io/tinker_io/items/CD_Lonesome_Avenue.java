package tinker_io.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;

import tinker_io.main.Main;
import net.minecraft.block.BlockJukebox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class CD_Lonesome_Avenue extends ItemRecord {

	private static final Map records = new HashMap();
	
	public final String recordName;
	
	public CD_Lonesome_Avenue(String recordName) {
		super(recordName);
		this.recordName = recordName;
		this.maxStackSize = 1;
		records.put(recordName, this);
		setUnlocalizedName("CD_Lonesome_Avenue");
		setCreativeTab(Main.TinkerIOTabs);
		setTextureName(Main.MODID + ":" + "record_Lonesome_Avenue");
	}
	@Override
	 public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
	 {
	 //TODO: world.getBlock()
	 if (world.getBlock(x, y, z) == Blocks.jukebox && world.getBlockMetadata(x, y, z) == 0)
	 {
	 if (world.isRemote)
	 return true;
	 else
	 {
	 //TODO: .insertRecord()
	 ((BlockJukebox)Blocks.jukebox).func_149926_b(world, x, y, z, itemStack);
	 //TODO: Item.getIdFromItem()
	 world.playAuxSFXAtEntity((EntityPlayer)null, 1005, x, y, z, Item.getIdFromItem(this));
	 --itemStack.stackSize;
	 return true;
	 }
	 } 
	 else
	 return false;
	 }


	 @Override
	 public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	 {
	 par3List.add(this.getRecordNameLocal());

	 if(this.isShiftKeyDown()){
			par3List.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("tio.toolTips.CD_Lonesome_Avenue"));
		}else{
			par3List.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("tio.toolTips.common.holdShift"));			
		}
	 }
	 
	@Override
	 //TODO: getRecordTitle()
	 public String getRecordNameLocal()
	 {
	 return StatCollector.translateToLocal(this.getUnlocalizedName() + ".desc");
	 }


	 @Override
	 public EnumRarity getRarity(ItemStack itemStack)
	 {
	 return EnumRarity.rare;
	 }


	 public static CD_Lonesome_Avenue getRecord(String par0Str)
	 {
	 return (CD_Lonesome_Avenue)records.get(par0Str);
	 }


	 @Override
	 public ResourceLocation getRecordResource(String name)
	 {
	 return new ResourceLocation("tinker_io:" + name);
	 }
	 
	 public static boolean isShiftKeyDown(){
	        return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
	    }
}
