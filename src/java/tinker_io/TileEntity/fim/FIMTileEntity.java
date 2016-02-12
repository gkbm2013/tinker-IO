package tinker_io.TileEntity.fim;

import tinker_io.TileEntity.TileEntityContainerAdapter;
import tinker_io.registry.ItemRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class FIMTileEntity extends TileEntityContainerAdapter implements  ITickable  {
	
	private static final int[] slotsSpeedUPG = new int[] { 0 };
	private static final int[] slotsFuel = new int[] { 1 };
	private static final int[] slotsUPG1 = new int[] { 2 };
	private static final int[] slotsUPG2 = new int[] { 3 };
	
	public FIMTileEntity() {
		super(null, 4);
	}
	
	public int speed = 300;
	
	public ItemStack fuel = new ItemStack(ItemRegistry.SolidFuel);
	
	public int inputTime;
	boolean isActive;

	
	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1) {
		return this.inputTime * par1 / speed;
	}
	
	public boolean hasFuel(){
		if(slots[1]!= null && this.getSlots()[1].isItemEqual(fuel)){
			return true;
		}
		return false;
	}
	
	protected SCInfo scInfo;
	protected FuelFSM fuelFSM;
	
	@Override
	public void onLoad() {
		scInfo = SCInfoFactory.getSmelyeryControllerInfo(this);
		fuelFSM = FuelFSMFactory.getNewFuelFSM(this);
	}
	
	protected int tick = 0;
	
	@Override
	public void update() {
			if (!this.worldObj.isRemote && this.scInfo != null) {
				++tick;
				if (tick % 4 == 0)
				{
					toUpdateSCInfoAndSpeedUpSC();
				}
			}
	}
	
	public void toUpdateSCInfoAndSpeedUpSC() {
		int x = 1000;
		this.scInfo.update();
		if (this.scInfo.pos != null && scInfo.isSCHeatingItem())
		{
			toSpeedUpSC(x);
		}
		else
		{
			this.fuelFSM.revert();
		}
	}
	
	public void toSpeedUpSC(int x) {
		this.fuelFSM.update();
		if (isActive)
		{
			x = getStackSize(this.getSlots()[0]) * 200 + 1000;
		}
		SCInfo.getTileSmeltery(worldObj, this.scInfo.pos).updateTemperatureFromPacket(x);
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
		
		if(this.getSlots()[2] != null){
			if(this.getSlots()[2].isItemEqual(slotUPG1)){
				size = size+(getSlots()[2].stackSize)*1;
			}else if(this.getSlots()[2].isItemEqual(slotUPG2)){
				size = size+(getSlots()[2].stackSize)*2;
			}else if(this.getSlots()[2].isItemEqual(slotUPG3)){
				size = size+(getSlots()[2].stackSize)*3;
			}else if(this.getSlots()[2].isItemEqual(slotUPG4)){
				size = size+(getSlots()[2].stackSize)*4;
			}else if(this.getSlots()[2].isItemEqual(slotUPGinfinity)){
				infinity = true;
			}
		}
		
		if(this.getSlots()[3] != null){
			if(this.getSlots()[3].isItemEqual(slotUPG1)){
				size = size+(getSlots()[3].stackSize)*1;
			}else if(this.getSlots()[3].isItemEqual(slotUPG2)){
				size = size+(getSlots()[3].stackSize)*2;
			}else if(this.getSlots()[3].isItemEqual(slotUPG3)){
				size = size+(getSlots()[3].stackSize)*3;
			}else if(this.getSlots()[3].isItemEqual(slotUPG4)){
				size = size+(getSlots()[3].stackSize)*4;
			}else if(this.getSlots()[3].isItemEqual(slotUPGinfinity)){
				infinity = true;
			}
		}
		
		if(infinity == true){
			return 2048;
		}
			return size * 30;

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
		this.inputTime = tagCompound.getShort("InputTime");
		this.isActive = tagCompound.getBoolean("isActive");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setShort("InputTime", (short) this.inputTime);
		tagCompound.setBoolean("isActive", isActive);
	}
}
