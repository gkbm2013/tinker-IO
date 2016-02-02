package tinker_io.TileEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.smeltery.tileentity.TileSmeltery;
import tinker_io.registry.ItemRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
//import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class FIMTileEntity extends TileEntity implements ISidedInventory, ITickable  {
	
	private static final int[] slotsSpeedUPG = new int[] { 0 };
	private static final int[] slotsFuel = new int[] { 1 };
	
	private static final int[] slotsUPG1 = new int[] { 2 };
	private static final int[] slotsUPG2 = new int[] { 3 };
	
	private ItemStack[] itemStacksASC = new ItemStack[4];
	
	private String nameFIM;
	
	public void nameFIM(String string){
		this.nameFIM = string;
	}
	public int speed = 300;
	
	public ItemStack fuel = new ItemStack(ItemRegistry.SolidFuel);
	
	public int inputTime;
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1) {
		return this.inputTime * par1 / speed;
	}
	
	public boolean hasFuel(){
		if(this.itemStacksASC[1]!= null && this.itemStacksASC[1].isItemEqual(fuel)){
			return true;
		}
		return false;
	}
	
	protected SmelteryControllerInfo scInfo;
	protected FuelFSM fuelFSM;
	
	@Override
	public void onLoad() {
		scInfo = SCInfoFactory.getSmelyeryControllerInfo(this);
		fuelFSM = new FuelFSM(isActive);
	}
	
	protected int heatingTemp = 1000;
	protected int tick = 0;
	
	public void update() {
		boolean isDirty = false;
		try {
			if (!this.worldObj.isRemote && scInfo != null) {
				++tick;
				if (tick % 4 == 0) {
					this.scInfo.update();
					if (this.scInfo.pos != null) {
						this.fuelFSM.update();
						if (isActive) {
							int x = getStackSize(this.itemStacksASC[0]) * 200 + 1000;
							this.scInfo.tile.updateTemperatureFromPacket(x);
	//						System.out.println(scInfo.tile.getTemperature());
	//						System.out.println(scInfo.tile.getTemperature(0));
						} else {
							this.scInfo.tile.updateTemperatureFromPacket(1000);
						}
					} else {
						this.fuelFSM.revert();
					}
				}
			}
		}catch(Throwable ex) {
				ex.printStackTrace();
		}
		
		if (isDirty) {
			this.markDirty();
		}
	}
	
	private boolean isActive;
	
	/**
	 * finite-state machine
	 */
	protected class FuelFSM {
		private boolean state;		
		FuelFSM(boolean state) {
			this.state = state;
		}
		
		 void update() {
//			System.out.println(inputTime);
			if (state) {
				heat();
			} else {
				waitFuel();
			}
		}
		
		 void revert() {
			if (state) {
				heat();
			}
		}
		
		 void heat() {
			isActive = true;
			inputTime -= 10;
			if (inputTime == 0) {
				state = false;
			}
		}
		
		 void waitFuel() {
			isActive = false;
			if (getStackSize(itemStacksASC[1]) > 0) {
				cousumeFuel();
				inputTime = 300;
				
				state = true;
			}
		}
		
		 void cousumeFuel() {
			itemStacksASC[1].stackSize -= 1;
			if (itemStacksASC[1].stackSize == 0) {
				itemStacksASC[1] = itemStacksASC[1].getItem().getContainerItem(itemStacksASC[1]);
			}
		}
	}
	
	
	
//	public void updateEntity() {
//		boolean dirtyFlag = false;		
//		if (!this.worldObj.isRemote) {//server do it
//			if (this.canSmelt()) {
//					dirtyFlag = true;
//					if (this.itemStacksASC[1] != null) {
//
//						if (this.itemStacksASC[1].stackSize == 0) {
//							this.itemStacksASC[1] = itemStacksASC[1].getItem().getContainerItem(this.itemStacksASC[1]);
//						}
//					}
//					
//				speedUPG();
//				++this.inputTime;
//				if (this.inputTime >= speed) {
//					this.inputTime = 0;
//					this.smeltItem();
//					dirtyFlag = true;
//					connectToTConstruct();
//				}
//
//			}
//			
//			if (dirtyFlag) {
//				// this is like : Don't forge save the game.
//				this.markDirty();
//			}
//		}
//	}
	
	public int getStackSize(ItemStack stack) {
		return stack == null ? 0 : stack.stackSize;
	}
	
//	private boolean canSmelt() {
//		this.checkConnection();
//		if(canConnect == true){
//			if(checkTemps() == true){
//				if (this.itemStacksASC[1] == null) return false;
//				if (this.itemStacksASC[1].isItemEqual(this.fuel)) return true;
//			}else{
//				return false;
//			}
//		}else{
//			return false;
//		}
//		return false;
//	}
//	
//	public void smeltItem() {
//		if (this.canSmelt() && this.checkTemps()) {
//			ItemStack itemstack = new ItemStack(ItemRegistry.SolidFuel);
//
//			if (this.itemStacksASC[1] == null) {
//				this.itemStacksASC[1] = itemstack.copy();
//			} else if (this.itemStacksASC[1].getItem() == itemstack.getItem()) {
//				--this.itemStacksASC[1].stackSize;
//			}
//		}
//	}
	
	public int getInputSize(){
		int size = 1;
		boolean infinity = false;
		
		ItemStack slotUPG1 = new ItemStack(ItemRegistry.Upgrade, 1, 1);
		ItemStack slotUPG2 = new ItemStack(ItemRegistry.Upgrade, 1, 2);
		ItemStack slotUPG3 = new ItemStack(ItemRegistry.Upgrade, 1, 3);
		ItemStack slotUPG4 = new ItemStack(ItemRegistry.Upgrade, 1, 4);
		ItemStack slotUPGinfinity = new ItemStack(ItemRegistry.Upgrade, 1, 6);
		
		if(this.itemStacksASC[2] != null){
			if(this.itemStacksASC[2].isItemEqual(slotUPG1)){
				size = size+(itemStacksASC[2].stackSize)*1;
			}else if(this.itemStacksASC[2].isItemEqual(slotUPG2)){
				size = size+(itemStacksASC[2].stackSize)*2;
			}else if(this.itemStacksASC[2].isItemEqual(slotUPG3)){
				size = size+(itemStacksASC[2].stackSize)*3;
			}else if(this.itemStacksASC[2].isItemEqual(slotUPG4)){
				size = size+(itemStacksASC[2].stackSize)*4;
			}else if(this.itemStacksASC[2].isItemEqual(slotUPGinfinity)){
				infinity = true;
			}
		}
		
		if(this.itemStacksASC[3] != null){
			if(this.itemStacksASC[3].isItemEqual(slotUPG1)){
				size = size+(itemStacksASC[3].stackSize)*1;
			}else if(this.itemStacksASC[3].isItemEqual(slotUPG2)){
				size = size+(itemStacksASC[3].stackSize)*2;
			}else if(this.itemStacksASC[3].isItemEqual(slotUPG3)){
				size = size+(itemStacksASC[3].stackSize)*3;
			}else if(this.itemStacksASC[3].isItemEqual(slotUPG4)){
				size = size+(itemStacksASC[3].stackSize)*4;
			}else if(this.itemStacksASC[3].isItemEqual(slotUPGinfinity)){
				infinity = true;
			}
		}
		
		if(infinity == true){
			return 2048;
		}
			return size * 30;

	}
	
	/**
	 * interface
	 */
	
	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory() {
		return this.itemStacksASC.length;
	}
	
	/**
	 * Returns the stack in the given slot.
	 */
	@Override
	public ItemStack getStackInSlot(int index) {
		return this.itemStacksASC[index];
	}
	
	/**
	 * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
	 */
	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (this.itemStacksASC[index] != null) {
			ItemStack itemstack;
			if (this.itemStacksASC[index].stackSize <= count) {
				itemstack = this.itemStacksASC[index];
				this.itemStacksASC[index] = null;
				return itemstack;
			} else {
				itemstack = this.itemStacksASC[index].splitStack(count);

				if (this.itemStacksASC[index].stackSize == 0) {
					this.itemStacksASC[index] = null;
				}
				return itemstack;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * Removes a stack from the given slot and returns it.
	 */
	public ItemStack removeStackFromSlot(int index) {
		if (this.itemStacksASC[index] != null) {
			ItemStack itemstack = this.itemStacksASC[index];
			this.itemStacksASC[index] = null;
			return itemstack;
		} else {
			return null;
		}
	}
	
	/**
	 * Sets the given item stack to the specified slot in the inventory
	 * (can be crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(
			int slot, ItemStack itemstack) {
		this.itemStacksASC[slot] = itemstack;

		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}
		
	}
	
	/**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.pos) != this ? false :
			player.getDistanceSq(this.pos.add(0.5D, 0.5D, 0.5D)) <= 64.0D;
	}

	public void openInventory(EntityPlayer player) {
		// TODO 
		
	}

	public void closeInventory(EntityPlayer player) {
		// TODO 
		
	}
	
	/**
	 * Returns true if automation is allowed to insert the given stack
	 * (ignoring stack size) into the given slot.
	 */
	@Override
	public boolean isItemValidForSlot(
			int index, ItemStack itemstack) {
		if(itemstack.isItemEqual(fuel)){
			return true;
		}else{
			return false;
		}		
	}
	
	@Override
	public int getField(int id) {
		// TODO
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO 
		
	}

	@Override
	public int getFieldCount() {
		// TODO ������瘜� Stub
		return 0;
	}

	
	public void clear() {
		// TODO
	}

	 /**
     * Get the name of this object.
     * For players this returns their username
     */
	@Override
	public String getName() {
		return this.hasCustomName() ?
				this.nameFIM : I18n.format("tile.FuelInputMachine.name", new Object[0]);
	}
	
	@Override
	public boolean hasCustomName() {
		return this.nameFIM != null && this.nameFIM.length() > 0;
	}
	
	/**
     * Get the formatted ChatComponent that will be used for the sender's username in chat
     */
	@Override
	public IChatComponent getDisplayName() {
		// TODO ������瘜� Stub
		return null;
	}

	
	public int[] getSlotsForFace(EnumFacing side) {
		return slotsFuel;
	}

	/**
     * Returns true if automation can insert the given item in the given slot from the given side.
     * Args: slot, item, side
     */
	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, 
			EnumFacing direction) {
		return this.isItemValidForSlot(index, itemStackIn);
	}
	
	/**
     * Returns true if automation can extract the given item in the given slot from the given side.
     * Args: slot, item, side
     */
	@Override
	public boolean canExtractItem(int index, ItemStack stack,
			EnumFacing direction) {
		//return par3 != 0 || par1 != 1 || itemstack.getItem() == Items.bucket;
		return false;
	}
	
	/**
	 *  loading and saving
	 */
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		NBTTagList tagList = tagCompound.getTagList("Items", 10);
		this.itemStacksASC = new ItemStack[this.getSizeInventory()];
		

		for (int i = 0; i < tagList.tagCount(); ++i) {
			NBTTagCompound tabCompound1 = tagList.getCompoundTagAt(i);
			byte byte0 = tabCompound1.getByte("Slot");
			

			if (byte0 >= 0 && byte0 < this.itemStacksASC.length) {
				this.itemStacksASC[byte0] = ItemStack.loadItemStackFromNBT(tabCompound1);
			}
		}

		//this.speedASC = tagCompound.getShort("SpeedASC");
		this.inputTime = tagCompound.getShort("InputTime");
		this.isActive = tagCompound.getBoolean("isActive");

		if (tagCompound.hasKey("CustomName", 8)) {
			this.nameFIM = tagCompound.getString("CustomName");
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		//tagCompound.setShort("SpeedASC", (short) this.speedASC);
		tagCompound.setShort("InputTime", (short) this.inputTime);
		tagCompound.setBoolean("isActive", isActive);
		//tagCompound.setTag("world", world1);
		
		//tagCompound.
		NBTTagList tagList = new NBTTagList();

		for (int i = 0; i < this.itemStacksASC.length; ++i) {
			if (this.itemStacksASC[i] != null) {
				NBTTagCompound tagCompound1 = new NBTTagCompound();
				tagCompound1.setByte("Slot", (byte) i);
				this.itemStacksASC[i].writeToNBT(tagCompound1);
				tagList.appendTag(tagCompound1);
			}
		}

		tagCompound.setTag("Items", tagList);

		if (this.hasCustomName()) {
			tagCompound.setString("CustomName", this.nameFIM);
		}
	}

}

class SCInfoFactory {
	public static SmelteryControllerInfo getSmelyeryControllerInfo(
			FIMTileEntity tile) {
		return new SmelteryControllerInfo(tile.getPos(), tile.getWorld());
	}
}

class SmelteryControllerInfo {
	final private BlockPos FIMBlockPos;
	final private World worldObj;
	
	public EnumFacing facing;
	public BlockPos pos;
	public TileSmeltery tile;
	public boolean connectionSuccess;
	
	public SmelteryControllerInfo(
			BlockPos FIMBlockPos, World worldObj) {
		this.FIMBlockPos = FIMBlockPos;
		this.worldObj = worldObj;
	}
	
	protected void update() {
		if (isOnlyOneSmelteryController()) {
			updateInfo();
			connectionSuccess = true;
		} else {
			initInfo();
			connectionSuccess = false;
		}
	}
	
	private void updateInfo() {
		EnumFacing[] facings = EnumFacing.values();
		for (EnumFacing facing : facings) {
			setInfo(facing);
		}
	}
	
	private void setInfo(EnumFacing facing) {
		Block block = getBlock(facing);
		if (isSmelteryController(block)) {
			this.facing = facing;
			this.pos = FIMBlockPos.offset(facing);
			this.tile = getTileSmeltery(pos);
		}
	}
	
	private void initInfo() {
		this.facing = null;
		this.pos = null;
		this.tile = null;
	}
	
	private boolean isOnlyOneSmelteryController() {
		int num = checkSmelteryControllerAmount();
		if (num == 1) {
			return true;
		} else  {
			return false;
		}
	}
	
	private int checkSmelteryControllerAmount() {
		int amount = 0;
		ArrayList<Block> blocks = getAllAroundBlocks();
		amount = blocks.stream()
				.filter(SmelteryControllerInfo::isSmelteryController)
				.collect(Collectors.toList())
				.size();
		return amount;
	}

	private ArrayList<Block> getAllAroundBlocks() {
		ArrayList<Block> blocks = new ArrayList<Block>(6);
		List<EnumFacing> facings = Arrays.asList(EnumFacing.values());
		for (EnumFacing facing : facings) {
			blocks.add(getBlock(facing));
		}
		return blocks;
	}

	static  boolean isSmelteryController(Block block) {
		return block == TinkerSmeltery.smelteryController;
	}
	
	private Block getBlock(EnumFacing facing) {
		Block block = worldObj.getBlockState(
				this.FIMBlockPos.offset(facing)).getBlock();
		return block != null ? block : Blocks.air;
	}
	
	private TileSmeltery getTileSmeltery(BlockPos pos) {
		return (TileSmeltery) this.worldObj.getTileEntity(pos);
	}
}
