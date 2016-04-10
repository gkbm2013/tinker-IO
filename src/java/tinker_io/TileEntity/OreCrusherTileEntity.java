package tinker_io.TileEntity;

import slimeknights.tconstruct.smeltery.tileentity.TileSmeltery;
import tinker_io.registry.ItemRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class OreCrusherTileEntity extends TileEntity implements ISidedInventory, ITickable  {
	private static final int[] slotsSpeedUPG = new int[] { 0 };
	private static final int[] slotsOre = new int[] { 1 };
	private static final int[] slotsProduce = new int[] { 2 };
	
	private int speed = 300;
	
	private int crushTime = 0;
	
	private ItemStack[] itemStacksOreCrusher = new ItemStack[3];
	
	private String nameOreCrusher;
	
	public void nameOreCrusher(String string){
		this.nameOreCrusher = string;
	}
	
	public int getSizeInventory() {
		return this.itemStacksOreCrusher.length;
	}
	
	public ItemStack getStackInSlot(int slot) {
		return this.itemStacksOreCrusher[slot];
	}
	
	public ItemStack decrStackSize(int par1, int par2) {
		if (this.itemStacksOreCrusher[par1] != null) {
			ItemStack itemstack;
			if (this.itemStacksOreCrusher[par1].stackSize <= par2) {
				itemstack = this.itemStacksOreCrusher[par1];
				this.itemStacksOreCrusher[par1] = null;
				return itemstack;
			} else {
				itemstack = this.itemStacksOreCrusher[par1].splitStack(par2);

				if (this.itemStacksOreCrusher[par1].stackSize == 0) {
					this.itemStacksOreCrusher[par1] = null;
				}
				return itemstack;
			}
		} else {
			return null;
		}
	}
	
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		this.itemStacksOreCrusher[slot] = itemstack;

		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}
		
	}
	
	public int getInventoryStackLimit() {
		return 64;
	}
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.pos) != this ? false :
			player.getDistanceSq(this.pos.add(0.5D, 0.5D, 0.5D)) <= 64.0D;
	}
	
	public boolean isItemValidForSlot(int par1, ItemStack itemstack) {
		/*if(itemstack.isItemEqual(fuel)){
			return true;
		}else{
			return false;
		}*/
		return true;
	}

	public ItemStack removeStackFromSlot(int index) {
		// TODO 自動產生的方法 Stub
		return null;
	}

	public void openInventory(EntityPlayer player) {
		// TODO 自動產生的方法 Stub
		
	}

	public void closeInventory(EntityPlayer player) {
		// TODO 自動產生的方法 Stub
		
	}

	public int getField(int id) {
		// TODO 自動產生的方法 Stub
		return 0;
	}

	public void setField(int id, int value) {
		// TODO 自動產生的方法 Stub
		
	}

	public int getFieldCount() {
		// TODO 自動產生的方法 Stub
		return 0;
	}

	public void clear() {
		// TODO 自動產生的方法 Stub
		
	}

	public String getName() {
		// TODO 自動產生的方法 Stub
		return null;
	}

	public boolean hasCustomName() {
		// TODO 自動產生的方法 Stub
		return false;
	}

	public IChatComponent getDisplayName() {
		// TODO 自動產生的方法 Stub
		return null;
	}

	public int[] getSlotsForFace(EnumFacing side) {
		// TODO 自動產生的方法 Stub
		return null;
	}

	public boolean canInsertItem(int index, ItemStack itemStackIn,
			EnumFacing direction) {
		// TODO 自動產生的方法 Stub
		return this.isItemValidForSlot(index, itemStackIn);
	}

	public boolean canExtractItem(int index, ItemStack stack,
			EnumFacing direction) {
		// TODO 自動產生的方法 Stub
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public int getCrushProgressScaled(int par1) {
		return this.crushTime * par1 / speed;
	}
	
	public void update() {
		crush();
	}
	
	public void updateEntity() {
		//crush();
	}
	
	/**
	 * Return true if the item stack is not null and is an ore registered in Ore Dictionary. 
	 * 
	 * @param itemStack
	 * @return
	 */
	public boolean isOreInOreDic(ItemStack itemStack){
		if(itemStack != null){
			if(OreDictionary.getOreIDs(itemStack).length > 0){
				int oreID = OreDictionary.getOreIDs(itemStack)[0];
				String oreName = OreDictionary.getOreName(oreID);
				String title = oreName.substring(0, 3);
				if(title.equals("ore")){
					return true;
				}else{
					
					return false;
				}
			}else{
				
				return false;
			}
		}else{
			return false;
		}
	}
	
	public String getOreDicName(ItemStack itemStack){
		String oreDicName = null;
		if(itemStack != null && OreDictionary.getOreIDs(itemStack).length > 0){
			int oreID = OreDictionary.getOreIDs(itemStack)[0];
			oreDicName = OreDictionary.getOreName(oreID);
		}
		return oreDicName;
	}
	
	private boolean isOreDicNBTTagEqual(ItemStack itemStack1, ItemStack itemStack2){
		if(itemStack1 != null && itemStack2 != null && itemStack1.getTagCompound() != null && itemStack2.getTagCompound() != null){
			NBTTagCompound nbt1 = itemStack1.getTagCompound();
			NBTTagCompound nbt2 = itemStack2.getTagCompound();
			if(nbt1.getString("oreDic").equals(nbt2.getString("oreDic"))){
				return true;
			}
		}
		return false;
	}
	
	public void crush(){
		if(isOreInOreDic(itemStacksOreCrusher[1])){
			ItemStack produce = new ItemStack(ItemRegistry.CrushedOre);
			produce.setTagCompound(new NBTTagCompound());
			NBTTagCompound nbt = produce.getTagCompound();
			nbt.setString("oreDic", getOreDicName(itemStacksOreCrusher[1]));
			if(itemStacksOreCrusher[2] == null || (itemStacksOreCrusher[2].isItemEqual(produce) && isOreDicNBTTagEqual(itemStacksOreCrusher[2], produce))){
				if(crushTime >= speed){
					crushTime = 0;
					if (this.itemStacksOreCrusher[2] == null) {
						this.itemStacksOreCrusher[2] = produce.copy();
					} else if (this.itemStacksOreCrusher[2].getItem() == produce.getItem()) {
						this.itemStacksOreCrusher[2].stackSize += produce.stackSize;
					}
					if(itemStacksOreCrusher[1].stackSize == 1){
						itemStacksOreCrusher[1] = null;
					}else{
						--itemStacksOreCrusher[1].stackSize;
					}
				}else{
					crushTime++;
					speedUPG();
				}
			}else{
				crushTime = 0;
			}
		}else{
			crushTime = 0;
		}
	}
	
	private void speedUPG(){
		ItemStack stackSpeedUPG = new ItemStack(ItemRegistry.SpeedUPG);
		
		if(this.itemStacksOreCrusher[0] == null){

		}else{
			if(this.itemStacksOreCrusher[0].isItemEqual(stackSpeedUPG)){
				crushTime = crushTime+(this.itemStacksOreCrusher[0].stackSize/3/2);
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		NBTTagList tagList = tagCompound.getTagList("Items", 10);
		this.itemStacksOreCrusher = new ItemStack[this.getSizeInventory()];
		

		for (int i = 0; i < tagList.tagCount(); ++i) {
			NBTTagCompound tabCompound1 = tagList.getCompoundTagAt(i);
			byte byte0 = tabCompound1.getByte("Slot");
			
			if (byte0 >= 0 && byte0 < this.itemStacksOreCrusher.length) {
				this.itemStacksOreCrusher[byte0] = ItemStack.loadItemStackFromNBT(tabCompound1);
			}
		}


		if (tagCompound.hasKey("CustomName", 8)) {
			this.nameOreCrusher = tagCompound.getString("CustomName");
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setShort("CrushTime", (short) this.crushTime);
		
		//tagCompound.
		NBTTagList tagList = new NBTTagList();

		for (int i = 0; i < this.itemStacksOreCrusher.length; ++i) {
			if (this.itemStacksOreCrusher[i] != null) {
				NBTTagCompound tagCompound1 = new NBTTagCompound();
				tagCompound1.setByte("Slot", (byte) i);
				this.itemStacksOreCrusher[i].writeToNBT(tagCompound1);
				tagList.appendTag(tagCompound1);
			}
		}

		tagCompound.setTag("Items", tagList);

		if (this.hasCustomName()) {
			tagCompound.setString("CustomName", this.nameOreCrusher);
		}
	}
		
}
