package tinker_io.TileEntity;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import tinker_io.handler.OreCrusherBanList;
import tinker_io.registry.ItemRegistry;

public class OreCrusherTileEntity extends TileEntityContainerAdapter implements ITickable, IEnergyReceiver  {
	public OreCrusherTileEntity() {
		super(null, 3);
	}

	private final int[] slotsSpeedUPG = new int[] { 0 };
	private final int[] slotsOre = new int[] { 1 };
	private final int[] slotsProduce = new int[] { 2 };
	
	protected EnergyStorage storage = new EnergyStorage(100000, 2000, 0);
	
	private int speed = 300;
	
	private int crushTime = 0;
	
	//private ItemStack[] getSlots() = this.getSlots();
	
	private String nameOreCrusher;
	
	
	public void nameOreCrusher(String string){
		this.nameOreCrusher = string;
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.nameOreCrusher : StatCollector.translateToLocal("tile.Ore_Crusher.name");
	}

	@Override
	public boolean hasCustomName() {
		return this.nameOreCrusher != null && this.nameOreCrusher.length() > 0;
	}

	//Slot
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		int[] slot = null;
		if(side == EnumFacing.DOWN){
			slot = this.slotsProduce;
		}else{
			slot = this.slotsOre;
		}
		return slot;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		OreCrusherBanList banList = OreCrusherBanList.banedOreDicList();
		return this.isOreInOreDic(itemStackIn) && banList.canItemCrush(itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		if(index == 2){
			return true;
		}
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack itemstack) {
		OreCrusherBanList banList = OreCrusherBanList.banedOreDicList();
		if(this.isOreInOreDic(itemstack) && banList.canItemCrush(itemstack)){
			return true;
		}
		return false;
	}
	
	//Gui
	@SideOnly(Side.CLIENT)
	public int getCrushProgressScaled(int par1) {
		return this.crushTime * par1 / speed;
	}
	
	@SideOnly(Side.CLIENT)
	public int getEnergyBar(int par1) {
		return this.storage.getEnergyStored() * par1 / this.storage.getMaxEnergyStored();
	}
	
	public void update() {
		if (worldObj.isRemote) return;
		crush();
		worldObj.markBlockForUpdate(pos);
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
	
	private ItemStack getProduce(){
		ItemStack produce = new ItemStack(ItemRegistry.CrushedOre, 3);
		produce.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbt = produce.getTagCompound();
		if(isOreInOreDic(getSlots()[1])){
			nbt.setString("oreDic", getOreDicName(getSlots()[1]));
		}
		return produce;
	}
	
	private boolean canCrush(){
		ItemStack produce = this.getProduce();
		OreCrusherBanList banList = OreCrusherBanList.banedOreDicList();
		
		if(getSlots()[1] == null){
			return false;
		}
		
		if(!isOreInOreDic(getSlots()[1]) || !banList.canItemCrush(getSlots()[1])){
			return false;
		}
		
		if(getSlots()[2] == null && this.storage.getEnergyStored() > speed){
			return true;
		}else if(getSlots()[2] != null && getSlots()[2].isItemEqual(produce) && isOreDicNBTTagEqual(getSlots()[2], produce) && this.storage.getEnergyStored() > speed){
			if(this.getSlots()[2].stackSize + produce.stackSize <= produce.getMaxStackSize()){
				return true;
			}
		}
		return false;
	}
	
	public void crush(){
		ItemStack produce = this.getProduce();
		
		if(canCrush()){
			if(crushTime >= speed){
				crushTime = 0;
				if (this.getSlots()[2] == null) {
					this.getSlots()[2] = produce.copy();
				} else if (this.getSlots()[2].getItem() == produce.getItem()) {
					this.getSlots()[2].stackSize += produce.stackSize;
				}
				if(getSlots()[1].stackSize == 1){
					getSlots()[1] = null;
				}else{
					--getSlots()[1].stackSize;
				}
			}else{
				crushTime++;
				speedUPG();
				this.storage.setEnergyStored(this.storage.getEnergyStored() - 45);
			}
		}else{
			crushTime = 0;
		}
	}
	
	private void speedUPG(){
		ItemStack stackSpeedUPG = new ItemStack(ItemRegistry.SpeedUPG);
		
		if(this.getSlots()[0] == null){

		}else{
			if(this.getSlots()[0].isItemEqual(stackSpeedUPG)){
				crushTime = crushTime+(this.getSlots()[0].stackSize/3/2);
			}
		}
	}
	
	//NBT
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		this.crushTime = tagCompound.getShort("CrushTime");
		
		/*NBTTagList tagList = tagCompound.getTagList("Items", 10);
		this.getSlots() = new ItemStack[this.getSizeInventory()];
		
		for (int i = 0; i < tagList.tagCount(); ++i) {
			NBTTagCompound tabCompound1 = tagList.getCompoundTagAt(i);
			byte byte0 = tabCompound1.getByte("Slot");
			
			if (byte0 >= 0 && byte0 < this.getSlots().length) {
				this.getSlots()[byte0] = ItemStack.loadItemStackFromNBT(tabCompound1);
			}
		}*/

		if (tagCompound.hasKey("CustomName", 8)) {
			this.nameOreCrusher = tagCompound.getString("CustomName");
		}
		storage.readFromNBT(tagCompound);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setShort("CrushTime", (short) this.crushTime);
		
		/*NBTTagList tagList = new NBTTagList();

		for (int i = 0; i < this.getSlots().length; ++i) {
			if (this.getSlots()[i] != null) {
				NBTTagCompound tagCompound1 = new NBTTagCompound();
				tagCompound1.setByte("Slot", (byte) i);
				this.getSlots()[i].writeToNBT(tagCompound1);
				tagList.appendTag(tagCompound1);
			}
		}

		tagCompound.setTag("Items", tagList);
		if (this.hasCustomName()) {
			tagCompound.setString("CustomName", this.nameOreCrusher);
		}*/
		
		storage.writeToNBT(tagCompound);
	}
	
	//Packet
	 @Override
	 public Packet getDescriptionPacket() {	 
	     NBTTagCompound tag = new NBTTagCompound();
	     this.writeToNBT(tag);
	     return new S35PacketUpdateTileEntity(pos, 1, tag);
	 }
	 
	 @Override
	 public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
	     readFromNBT(packet.getNbtCompound());
	 }

	//RF Energy
	@Override
	public int getEnergyStored(EnumFacing from) {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return storage.getMaxEnergyStored();
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		return this.storage.receiveEnergy(Math.min(storage.getMaxReceive(), maxReceive), simulate);
	}
	
	public EnergyStorage getStorage(){
		return this.storage;
	}
		
}
