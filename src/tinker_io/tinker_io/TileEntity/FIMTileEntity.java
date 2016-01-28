package tinker_io.TileEntity;

import scala.reflect.internal.Trees.This;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.smeltery.tileentity.TileSmeltery;
import tinker_io.mainRegistry.ItemRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
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
	
	public boolean canConnect = false;
	public int connection;
	
	public int inputTime;

	@Override
	public int getSizeInventory() {
		return this.itemStacksASC.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.itemStacksASC[slot];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (this.itemStacksASC[par1] != null) {
			ItemStack itemstack;
			if (this.itemStacksASC[par1].stackSize <= par2) {
				itemstack = this.itemStacksASC[par1];
				this.itemStacksASC[par1] = null;
				return itemstack;
			} else {
				itemstack = this.itemStacksASC[par1].splitStack(par2);

				if (this.itemStacksASC[par1].stackSize == 0) {
					this.itemStacksASC[par1] = null;
				}
				return itemstack;
			}
		} else {
			return null;
		}
	}

//	@Override
//	public ItemStack getStackInSlotOnClosing(int slot) {
//		if (this.itemStacksASC[slot] != null) {
//			ItemStack itemstack = this.itemStacksASC[slot];
//			this.itemStacksASC[slot] = null;
//			return itemstack;
//		} else {
//			return null;
//		}
//	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		this.itemStacksASC[slot] = itemstack;

		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}
		
	}

//	1.7
//	@Override
//	public String getInventoryName() {
//		return this.hasCustomInventoryName() ? this.nameFIM : I18n.format("tile.FuelInputMachine.name", new Object[0]);
//	}

//	1.7
//	@Override
//	public boolean hasCustomInventoryName() {
//		return this.nameFIM != null && this.nameFIM.length() > 0;
//	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

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

		if (tagCompound.hasKey("CustomName", 8)) {
			this.nameFIM = tagCompound.getString("CustomName");
		}
	}
	
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		//tagCompound.setShort("SpeedASC", (short) this.speedASC);
		tagCompound.setShort("InputTime", (short) this.inputTime);
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
	
	int tick = 0;
	@Override
	public void update() {
		try {
			if (!this.worldObj.isRemote) {
				++tick;
				if (tick % 20 == 0) {
					this.checkConnection();
					System.out.println(connection);
					System.out.println(this.smelteryController_blockPos != null? this.smelteryController_blockPos.toString():"null");
				}
			}
		}catch(Throwable ex) {
			System.out.println(ex.toString());
		}
	}
	
	public void updateEntity() {
		boolean dirtyFlag = false;		
		if (!this.worldObj.isRemote) {//server do it
			if (this.canSmelt()) {
					dirtyFlag = true;
					if (this.itemStacksASC[1] != null) {

						if (this.itemStacksASC[1].stackSize == 0) {
							this.itemStacksASC[1] = itemStacksASC[1].getItem().getContainerItem(this.itemStacksASC[1]);
						}
					}
					
				speedUPG();
				++this.inputTime;
				if (this.inputTime >= speed) {
					this.inputTime = 0;
					this.smeltItem();
					dirtyFlag = true;
					connectToTConstruct();
				}

			}
			
			if (dirtyFlag) {
				// this is like : Don't forge save the game.
				this.markDirty();
			}
		}
	}
	
	private void speedUPG(){
		ItemStack stackSpeedUPG = new ItemStack(ItemRegistry.SpeedUPG);
		
		if(this.itemStacksASC[0] == null){

		}else{
			if(this.itemStacksASC[0].isItemEqual(stackSpeedUPG)){
				inputTime = inputTime+(this.itemStacksASC[0].stackSize/3/2);
			}
		}
		//System.out.println(inputTime);
	}

	private boolean canSmelt() {
		this.checkConnection();
		if(canConnect == true){
			if(checkTemps() == true){
				if (this.itemStacksASC[1] == null) return false;
				if (this.itemStacksASC[1].isItemEqual(this.fuel)) return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
		return false;
	}
	
	private void connectToTConstruct(){
		World world = worldObj;
		
		if(canConnect == true){
//			TileSmeltery smeltery = null;
//			if(connection == 6){
//				smeltery = (TileSmeltery) world.getTileEntity(this.pos.up());
//			}else if(connection == 5){
//				smeltery = (TileSmeltery) world.getTileEntity(this.pos.down());
//			}else if(connection == 1){
//				smeltery = (TileSmeltery) world.getTileEntity(this.pos.west());
//			}else if(connection == 2){
//				smeltery = (TileSmeltery) world.getTileEntity(this.pos.east());
//			}else if(connection == 3){
//				smeltery = (TileSmeltery) world.getTileEntity(this.pos.north());
//			}else if(connection == 4){
//				smeltery = (TileSmeltery) world.getTileEntity(this.pos.south());
//			}
//			
//			if(smeltery != null){
//				int[] activeTemps = smeltery.activeTemps;
//				int fuelAmount = smeltery.fuelAmount;
//				int[] meltingTemps = smeltery.meltingTemps;
//				//activeTemps
//				if(activeTemps != null && fuelAmount >= 120){
//					for(int i = 0; i < activeTemps.length; i++){
//						if(activeTemps[i] < 200 || activeTemps[i] == meltingTemps[i]){
//							
//						}else{
//							if(this.getInputSize() == 2048){
//								activeTemps[i] = meltingTemps[i];
//							}else if(this.getInputSize() >= i){
//								activeTemps[i] = meltingTemps[i];
//							}
//							//System.out.println(activeTemps[i]);	
//						}
//						
//					}
//				}				
//			}
			
		}else{
			System.out.println("[Tinker I/O] Error! (Maybe I will fix it ...)");
		}
		
	}
	
	public void smeltItem() {
		if (this.canSmelt() && this.checkTemps()) {
			ItemStack itemstack = new ItemStack(ItemRegistry.SolidFuel);

			if (this.itemStacksASC[1] == null) {
				this.itemStacksASC[1] = itemstack.copy();
			} else if (this.itemStacksASC[1].getItem() == itemstack.getItem()) {
				--this.itemStacksASC[1].stackSize;
			}
		}
	}
	
	private BlockPos smelteryController_blockPos ;
	
	public void checkConnection(){
		int amount = 0;
		connection = 0;
		smelteryController_blockPos = null;
		int error = 0;
		
		if(worldObj != null && !worldObj.isRemote){
			/*
			 * the value of connection : 
			 * 0 = not found
			 * 1 = x-1 w
			 * 2 = x+1 e
			 * 3 = z-1 s
			 * 4 = z+1 n
			 * 5 = y+1 u
			 */
			EnumFacing[] facings = EnumFacing.values();
			for (EnumFacing facing : facings) { //d-u-n-s-w-e
				if(worldObj.getBlockState(this.pos.offset(facing)).getBlock() == TinkerSmeltery.smelteryController) {
					this.smelteryController_blockPos = new BlockPos(this.pos.offset(facing));
					this.connection = facing.getIndex();
					amount += 1;
				} else {
					++error;
				}
			}
			
//			if(world.getBlockState(this.pos.west()).getBlock() == TinkerSmeltery.smelteryController){
//				connection = 1;
//				amount = amount+1;
//			}else{
//				error++;
//			}
//			if(world.getBlockState(this.pos.east()).getBlock() == TinkerSmeltery.smelteryController){
//				connection = 2;
//				amount = amount+1;
//			}else{
//				error++;
//			}
//			if(world.getBlockState(this.pos.north()).getBlock() == TinkerSmeltery.smelteryController){
//				connection = 3;
//				amount = amount+1;
//			}else{
//				error++;
//			}
//			if(world.getBlockState(this.pos.south()).getBlock() == TinkerSmeltery.smelteryController){
//				connection = 4;
//				amount = amount+1;
//			}else{
//				error++;
//			}
//			if(world.getBlockState(this.pos.up()).getBlock() == TinkerSmeltery.smelteryController){
//				connection = 5;
//				amount = amount+1;
//			}else{
//				error++;
//			}
//			if(world.getBlockState(this.pos.down()).getBlock() == TinkerSmeltery.smelteryController){
//				connection = 6;
//				amount = amount+1;
//			}else{
//				error++;
//			}
			if (error == 5 && amount == 1 || connection != 0) {
				canConnect = true;
			}else{
				canConnect = false;
			}
		}else{
			canConnect = false;
		}
	}
	
	private boolean checkTemps(){
		int start = 0;
		int stop = 0;
		
		if(!worldObj.isRemote){
			if(canConnect){
				TileSmeltery smeltery = (TileSmeltery) worldObj.getTileEntity(smelteryController_blockPos);
//				if(connection == 6){
//					if(world.getTileEntity(x, y -1, z) instanceof TileSmeltery){
//						smeltery = (TileSmeltery) world.getTileEntity(x, y -1, z);
//					}		
//				}else if(connection == 5){
//					if(world.getTileEntity(x, y +1, z) instanceof TileSmeltery){
//						smeltery = (TileSmeltery) world.getTileEntity(x, y +1, z);
//					}					
//				}else if(connection == 1){
//					if(world.getTileEntity(x -1, y, z) instanceof TileSmeltery){
//						smeltery = (TileSmeltery) world.getTileEntity(x -1, y, z);
//					}		
//				}else if(connection == 2){
//					if(world.getTileEntity(x +1, y, z) instanceof TileSmeltery){
//						smeltery = (TileSmeltery) world.getTileEntity(x +1, y, z);
//					}		
//				}else if(connection == 3){
//					if(world.getTileEntity(x, y, z -1) instanceof TileSmeltery){
//						smeltery = (TileSmeltery) world.getTileEntity(x, y, z -1);
//					}		
//				}else if(connection == 4){
//					if(world.getTileEntity(x, y, z +1) instanceof TileSmeltery){
//						smeltery = (TileSmeltery) world.getTileEntity(x, y, z +1);
//					}		
//				}
				
//				if(smeltery != null){
//					int[] activeTemps = smeltery.activeTemps;
//					int fuelAmount = smeltery.fuelAmount;
//					int[] meltingTemps = smeltery.meltingTemps;
//					
//					//activeTemps
//					if(activeTemps != null && fuelAmount >= 120){
//						
//						if(this.getInputSize() == 2048){
//							for(int i = 0; i < activeTemps.length; i++){
//								if(activeTemps[i] > 200 && activeTemps[i] < meltingTemps[i]){
//									start++;
//								}
//							}							
//						}else{
//							int num = 0;
//							if(activeTemps.length > this.getInputSize()){
//								num = this.getInputSize();
//							}else{
//								num = activeTemps.length;
//							}
//							
//							for(int i = 0; i < num; i++){
//								if(activeTemps[i] > 200 && activeTemps[i] < meltingTemps[i]){
//									start++;
//								}
//							}
//						}
//					}				
//				}
				
			}else{
				System.out.println("[Tinker I/O] Error! (Maybe I will fix it ...)");
			}

			if(start > 0){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	/*public String getDirection(){
		this.checkConnection();
		String dir;
		if(canConnect == true){
			if(connection == 6){
				dir = "y - 1";
			}else if(connection == 5){
				dir = "y + 1";
			}else if(connection == 1){
				dir = "x - 1";
			}else if(connection == 2){
				dir = "x + 1";
			}else if(connection == 3){
				dir = "z - 1";
			}else if(connection == 4){
				dir = "z + 1";
			}else{
				dir = "Not Found";
			}
		}else{
			dir = "Not Found";
		}
		
		return dir;
	}*/
	
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
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return false;
//		return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
	}

//	@Override
//	public void openInventory() {
//				
//	}
//
//	@Override
//	public void closeInventory() {
//				
//	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack itemstack) {
		if(itemstack.isItemEqual(fuel)){
			return true;
		}else{
			return false;
		}		
	}
	
	/**
	 * Removes a stack from the given slot and returns it.
	 */
	@Override
	public ItemStack removeStackFromSlot(int index) {
		// TODO 自動產生的方法 Stub
		return null;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		// TODO 自動產生的方法 Stub
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		// TODO 自動產生的方法 Stub
		
	}

	@Override
	public int getField(int id) {
		// TODO 自動產生的方法 Stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO 自動產生的方法 Stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO 自動產生的方法 Stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO 自動產生的方法 Stub
		
	}

	@Override
	public String getName() {
		// TODO 自動產生的方法 Stub
		return null;
	}

	@Override
	public boolean hasCustomName() {
		// TODO 自動產生的方法 Stub
		return false;
	}

	@Override
	public IChatComponent getDisplayName() {
		// TODO 自動產生的方法 Stub
		return null;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return slotsFuel;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn,
			EnumFacing direction) {
		return this.isItemValidForSlot(index, itemStackIn);
	}
	
	@Override
	public boolean canExtractItem(int index, ItemStack stack,
			EnumFacing direction) {
		//return par3 != 0 || par1 != 1 || itemstack.getItem() == Items.bucket;
		return false;
	}

	

}
