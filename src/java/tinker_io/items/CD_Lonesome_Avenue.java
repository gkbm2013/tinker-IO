package tinker_io.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import tinker_io.main.Main;

public class CD_Lonesome_Avenue extends ItemRecord {

	private static final Map records = new HashMap();
	
	public final String recordName;
	public final static SoundEvent sound_CD = new SoundEvent(new ResourceLocation(Main.MODID, "records.Lonesome_Avenue"));
	
	public CD_Lonesome_Avenue(String recordname) {
		super(recordname, sound_CD);
		this.recordName = recordname;
		this.maxStackSize = 1;
		records.put(recordName, this);
		setUnlocalizedName("CD_" + recordname);
		setCreativeTab(Main.TinkerIOTabs);
	}
	
    /**
     * Called when a Block is right-clicked with this Item
     */
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);

        if (iblockstate.getBlock() == Blocks.JUKEBOX && !((Boolean)iblockstate.getValue(BlockJukebox.HAS_RECORD)).booleanValue())
        {
            if (worldIn.isRemote)
            {
                return true;
            }
            else
            {
                ((BlockJukebox)Blocks.JUKEBOX).insertRecord(worldIn, pos, iblockstate, stack);
                //worldIn.playAuxSFXAtEntity((EntityPlayer)null, 1005, pos, Item.getIdFromItem(this));
                --stack.stackSize;
                playerIn.addStat(StatList.RECORD_PLAYED);
                return true;
            }
        }
        else
        {
            return false;
        }
    }	
	
//	@Override
//	 public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
//	 {
//	 //TODO: world.getBlock()
//	 if (world.getBlock(x, y, z) == Blocks.jukebox && world.getBlockMetadata(x, y, z) == 0)
//	 {
//	 if (world.isRemote)
//	 return true;
//	 else
//	 {
//	 //TODO: .insertRecord()
//	 ((BlockJukebox)Blocks.jukebox).func_149926_b(world, x, y, z, itemStack);
//	 //TODO: Item.getIdFromItem()
//	 world.playAuxSFXAtEntity((EntityPlayer)null, 1005, x, y, z, Item.getIdFromItem(this));
//	 --itemStack.stackSize;
//	 return true;
//	 }
//	 } 
//	 else
//	 return false;
//	 }


	 @Override
	 public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	 {
	 par3List.add(this.getRecordNameLocal());

	 if(this.isShiftKeyDown()){
			par3List.add(TextFormatting.GRAY + I18n.format("tio.toolTips.CD_Lonesome_Avenue"));
		}else{
			par3List.add(TextFormatting.GOLD + I18n.format("tio.toolTips.common.holdShift"));			
		}
	 }
	 
	@Override
	 //TODO: getRecordTitle()
	 public String getRecordNameLocal()
	 {
	 return I18n.format(this.getUnlocalizedName() + ".desc");
	 }


	 @Override
	 public EnumRarity getRarity(ItemStack itemStack)
	 {
	 return EnumRarity.RARE;
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
