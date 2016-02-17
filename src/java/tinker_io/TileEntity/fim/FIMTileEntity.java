package tinker_io.TileEntity.fim;

import java.util.ArrayList;
import java.util.List;

import tinker_io.TileEntity.Observable;
import tinker_io.TileEntity.TileEntityContainerAdapter;
import tinker_io.inventory.Observer;
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

public class FIMTileEntity extends TileEntityContainerAdapter implements  ITickable, Observable  {
	
	private static final int[] slotsSpeedUPG = new int[] { 0 };
	private static final int[] slotsFuel = new int[] { 1 };
	private static final int[] slotsUPG1 = new int[] { 2 };
	private static final int[] slotsUPG2 = new int[] { 3 };
	
	public FIMTileEntity() {
		super(null, 4);
	}
	
	public ItemStack fuel = new ItemStack(ItemRegistry.SolidFuel);
	private List<Observer> obs = new ArrayList();
	
	@Deprecated
	public int speed = 300;
	@Deprecated
	public int inputTime;

	protected SCInfo scInfo;
	protected FuelFSM fuelFSM = FuelFSMFactory.getNewFuelFSM(this);
	
	@Override
	public void onLoad() {
		scInfo = SCInfoFactory.getSmelyeryControllerInfo(this);
		fuelFSM.init();
	}
	
	protected int tick = 0;
	
	@Override
	public void update() {
			if (!this.worldObj.isRemote)
			{
				if (tick % 4 == 0)
				{
					toUpdateSCInfoAndSpeedUpSC();
				}
				tick = (tick + 1) % 20;
			}
	}
	
	public void toUpdateSCInfoAndSpeedUpSC() {
		this.scInfo.update();
		this.fuelFSM.update();
		if (scInfo.canFindSCPos() && scInfo.isSCHeatingItem())
		{
			fuelFSM.startChangeState();
			Adapter adap = scInfo.getAdapter();
			final int fuelTemp = adap.getFuelTemp();
			toSpeedUpSC(fuelTemp, adap);
		}
	}
	
	public void toSpeedUpSC(int fuelTemp, Adapter adap) {
		if (fuelFSM.isActive)
		{
			fuelTemp = getSpeedUpTemp(fuelTemp);
		}
		adap.setFuelTemp(fuelTemp);
	}
	
	public int getSpeedUpTemp(int fuelTemp)
	{
		return getStackSize(this.getSlots()[0]) * 200 + fuelTemp;
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
	
	@Override
	public void addObserver(Observer o)
	{
		this.obs.add(o);
	}
	
	@Override
	public void removeObserver(Observer o)
	{
		int index = this.obs.indexOf(o);
		if (index >= 0)
		{
			obs.remove(index);
		}
	}
	
	@Override
	public void notifyObservers()
	{
		for (Observer o: this.obs)
		{
			o.receivedTopic();
		}
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
	
	/**
	 * Returns true if automation is allowed to insert the given stack
	 * (ignoring stack size) into the given slot.
	 */
	@Override
	public boolean isItemValidForSlot(
			int index, ItemStack itemstack) {
		if(itemstack.isItemEqual(fuel)){
			return true;
		}
		return false;
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
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.inputTime = tag.getShort("InputTime");
		
		this.fuelFSM.readFromNBT(tag);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setShort("InputTime", (short) this.inputTime);
		
		this.fuelFSM.writeToNBT(tag);
	}
	
	/*
	 * GUI
	 */
	
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
	
	public SpeedUpRatio getSpeedUpInfo()
	{
		double temp = this.getStackSize(slots[0]) * 0.2 + 1.0;
		return new SpeedUpRatio(temp);
	}
	
	@SideOnly(Side.CLIENT)
	public String getDirection()
	{
		return this.scInfo.getFacing();
	}
	
	public int getInputTime()
	{
		return this.inputTime;
	}
	/*
	 * Data
	 */
	
	public static class SpeedUpRatio {
		public double ratio;
		
		public SpeedUpRatio(double temp) {
			this.ratio = temp;
		}
		
	}
	
}
