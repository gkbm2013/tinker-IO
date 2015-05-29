package tinker_io.TileEntity;

import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.smeltery.logic.SmelteryLogic;
import tinker_io.mainRegistry.ItemRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class FIMTileEntity extends TileEntity implements ISidedInventory  {
	
	private static final int[] slotsSpeedUPG = new int[] { 0 };
	private static final int[] slotsFuel = new int[] { 1 };
	
	private ItemStack[] itemStacksASC = new ItemStack[2];
	
	private String nameFIM;
	
	public void nameFIM(String string){
		this.nameFIM = string;
	}
	
	
	public int speedASC;
	public int catalystASC;
	public int speed = 300;
	
	private ItemStack fuel = new ItemStack(ItemRegistry.SolidFuel);
	
	private int x1;
	private int y1;
	private int z1;
	World world1;
	
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

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (this.itemStacksASC[slot] != null) {
			ItemStack itemstack = this.itemStacksASC[slot];
			this.itemStacksASC[slot] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		this.itemStacksASC[slot] = itemstack;

		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}
		
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.nameFIM : I18n.format("tile.FuelInputMachine.name", new Object[0]);
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.nameFIM != null && this.nameFIM.length() > 0;
	}

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

		this.speedASC = tagCompound.getShort("SpeedASC");
		this.catalystASC = tagCompound.getShort("catalystASC");
		this.inputTime = tagCompound.getShort("InputTime");
		this.x1 = tagCompound.getShort("x");
		this.y1 = tagCompound.getShort("y");
		this.z1 = tagCompound.getShort("z");

		if (tagCompound.hasKey("CustomName", 8)) {
			this.nameFIM = tagCompound.getString("CustomName");
		}
	}
	
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setShort("SpeedASC", (short) this.speedASC);
		tagCompound.setShort("catalystASC", (short) this.catalystASC);
		tagCompound.setShort("InputTime", (short) this.inputTime);
		tagCompound.setShort("x", (short) this.x1);
		tagCompound.setShort("y", (short) this.y1);
		tagCompound.setShort("z", (short) this.z1);
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

		if (this.hasCustomInventoryName()) {
			tagCompound.setString("CustomName", this.nameFIM);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1) {
		return this.inputTime * par1 / speed;
	}
	

	public void updateEntity() {
		boolean flag1 = false;
		if (!this.worldObj.isRemote) {
			this.checkConnection(worldObj, xCoord, yCoord, zCoord);
			if (this.canSmelt()) {
				//this.checkConnection(world1, x1, y1, z1);
					flag1 = true;
					if (this.itemStacksASC[1] != null) {

						if (this.itemStacksASC[1].stackSize == 0) {
							this.itemStacksASC[1] = itemStacksASC[1].getItem().getContainerItem(this.itemStacksASC[1]);
						}
					}
			}

			
			if (this.canSmelt()) {
				speedUPG();
				++this.inputTime;
				if (this.inputTime >= speed) {
					this.inputTime = 0;
					this.smeltItem();
					flag1 = true;
					connectToTConstruct();
				}
			} else {
				this.inputTime = 0;
			}
		}

		if (flag1) {
			this.markDirty();
		}
		//canConnect = false;
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
		int x = xCoord;
		int y = yCoord;
		int z = zCoord;
		World world = worldObj;
		
		if(canConnect == true){
			SmelteryLogic smeltery = null;
			if(connection == 6){
				smeltery = (SmelteryLogic) world.getTileEntity(x, y -1, z);
			}else if(connection == 5){
				smeltery = (SmelteryLogic) world.getTileEntity(x, y +1, z);
			}else if(connection == 1){
				smeltery = (SmelteryLogic) world.getTileEntity(x -1, y, z);
			}else if(connection == 2){
				smeltery = (SmelteryLogic) world.getTileEntity(x +1, y, z);
			}else if(connection == 3){
				smeltery = (SmelteryLogic) world.getTileEntity(x, y, z -1);
			}else if(connection == 4){
				smeltery = (SmelteryLogic) world.getTileEntity(x, y, z +1);
			}
			
			if(smeltery != null){
				int[] activeTemps = smeltery.activeTemps;
				int fuelAmount = smeltery.fuelAmount;
				//activeTemps
				if(activeTemps != null && fuelAmount >= 120){
					for(int i = 0; i < activeTemps.length; i++){
						if(activeTemps[i] < 200 || activeTemps[i] > 7999){
							
						}else{
							activeTemps[i] = 8000;
							//System.out.println(activeTemps[i]);	
						}
					}
				}				
			}
			
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
	
	public void checkConnection(World world,int x,int y,int z){
		x1 = x;
		y1 = y;
		z1 = z;
		world1 = world;
		
		int amount = 0;
		connection = 0;
		int error = 0;
		
		if(xCoord != 0 || yCoord != 0 || zCoord != 0 || worldObj != null){
			/*
			 * the value of connection : 
			 * 0 = not found
			 * 1 = x-1
			 * 2 = x+1
			 * 3 = z-1
			 * 4 = z+1
			 * 5 = y+1
			 */
			
			
			if(world.getBlock(x -1, y, z) == TinkerSmeltery.smeltery && world.getBlockMetadata(x -1, y, z) == 0){
				connection = 1;
				amount = amount+1;
			}else{
				error++;
			}
			if(world.getBlock(x +1, y, z) == TinkerSmeltery.smeltery && world.getBlockMetadata(x +1, y, z) == 0){
				connection = 2;
				amount = amount+1;
			}else{
				error++;
			}
			if(world.getBlock(x, y, z -1) == TinkerSmeltery.smeltery && world.getBlockMetadata(x, y, z -1) == 0){
				connection = 3;
				amount = amount+1;
			}else{
				error++;
			}
			if(world.getBlock(x, y, z +1) == TinkerSmeltery.smeltery && world.getBlockMetadata(x, y, z +1) == 0){
				connection = 4;
				amount = amount+1;
			}else{
				error++;
			}
			if(world.getBlock(x, y +1, z) == TinkerSmeltery.smeltery && world.getBlockMetadata(x, y +1, z) == 0){
				connection = 5;
				amount = amount+1;
			}else{
				error++;
			}
			if(world.getBlock(x, y -1, z) == TinkerSmeltery.smeltery && world.getBlockMetadata(x, y -1, z) == 0){
				connection = 6;
				amount = amount+1;
			}else{
				error++;
			}
			
			
			if(error == 5){
				if(amount == 1 || connection != 0){
					canConnect = true;
				}else{
					canConnect = false;
				}
			}else{
				canConnect = false;
			}
			
		}else{
			canConnect = false;
		}
	}
	
	private boolean checkTemps(){
		int x = xCoord;
		int y = yCoord;
		int z = zCoord;
		World world = worldObj;
		int start = 0;
		int stop = 0;
		
		if(canConnect == true){
			if(canConnect == true){
				SmelteryLogic smeltery = null;
				if(connection == 6){
					if(world.getTileEntity(x, y -1, z) instanceof SmelteryLogic){
						smeltery = (SmelteryLogic) world.getTileEntity(x, y -1, z);
					}		
				}else if(connection == 5){
					if(world.getTileEntity(x, y +1, z) instanceof SmelteryLogic){
						smeltery = (SmelteryLogic) world.getTileEntity(x, y +1, z);
					}					
				}else if(connection == 1){
					if(world.getTileEntity(x -1, y, z) instanceof SmelteryLogic){
						smeltery = (SmelteryLogic) world.getTileEntity(x -1, y, z);
					}		
				}else if(connection == 2){
					if(world.getTileEntity(x +1, y, z) instanceof SmelteryLogic){
						smeltery = (SmelteryLogic) world.getTileEntity(x +1, y, z);
					}		
				}else if(connection == 3){
					if(world.getTileEntity(x, y, z -1) instanceof SmelteryLogic){
						smeltery = (SmelteryLogic) world.getTileEntity(x, y, z -1);
					}		
				}else if(connection == 4){
					if(world.getTileEntity(x, y, z +1) instanceof SmelteryLogic){
						smeltery = (SmelteryLogic) world.getTileEntity(x, y, z +1);
					}		
				}
				
				
				
				if(smeltery != null){
					int[] activeTemps = smeltery.activeTemps;
					int fuelAmount = smeltery.fuelAmount;
					
					//activeTemps
					if(activeTemps != null && fuelAmount >= 120){		
						for(int i = 0; i < activeTemps.length; i++){
							if(activeTemps[i] > 200 && activeTemps[i] < 7900){
								start++;
							}
						}
					}				
				}
				
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
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {
				
	}

	@Override
	public void closeInventory() {
				
	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack itemstack) {
		if(itemstack.isItemEqual(fuel)){
			return true;
		}else{
			return false;
		}		
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int par1) {
		return slotsFuel;
	}

	@Override
	public boolean canInsertItem(int par1, ItemStack itemstack, int par3) {
		return this.isItemValidForSlot(par1, itemstack);
	}

	@Override
	public boolean canExtractItem(int par1, ItemStack itemstack, int par3) {
		//return par3 != 0 || par1 != 1 || itemstack.getItem() == Items.bucket;
		return false;
	}

}
