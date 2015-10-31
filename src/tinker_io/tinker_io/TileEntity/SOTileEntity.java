package tinker_io.TileEntity;

import tconstruct.smeltery.TinkerSmeltery;
import tinker_io.handler.SOEliminateList;
import tinker_io.handler.SORecipes;
import tinker_io.mainRegistry.ItemRegistry;
import mantle.blocks.abstracts.MultiServantLogic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SOTileEntity extends MultiServantLogic implements IFluidHandler , ISidedInventory{
	public FluidTank tank;
	
	public FluidStack otherLiquid;
	public FluidTankInfo[] otherTankInfo = null;
	
	public ItemStack[] itemStacksSO = new ItemStack[4];
	
	private static final int[] slotsPattern = new int[] { 0 };
	private static final int[] slotsProduct = new int[] { 1 };
	private static final int[] slotsUPGup = new int[] { 2 };
	private static final int[] slotsUPGdown = new int[] { 3 };
	
	int x,y,z;	
	int connection = 0;
	
	//public int mode;
	
	public int currentFrozenTime = 0;
	public int frozenTimeMax = 2; //Ticks
	
	SORecipes recipes = new SORecipes();
	
	private String nameSO;
	
	public void nameASC(String string){
		this.nameSO = string;
	}
	
	public SOTileEntity(){
		tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 10);
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		int amount = tank.fill(resource, doFill);
        if (amount > 0 && doFill)
        {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }

        return amount;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (tank.getFluidAmount() == 0)
            return null;
        if (tank.getFluid().getFluid() != resource.getFluid())
            return null;

        return this.drain(from, resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		FluidStack amount = tank.drain(maxDrain, doDrain);
        if (amount != null && doDrain)
        {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
        return amount;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return tank.getFluidAmount() == 0 || (tank.getFluid().getFluid() == fluid && tank.getFluidAmount() < tank.getCapacity());
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return tank.getFluidAmount() > 0;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		FluidStack fluid = null;
        if (tank.getFluid() != null)
            fluid = tank.getFluid().copy();
        return new FluidTankInfo[] { new FluidTankInfo(fluid, tank.getCapacity()) };
	}
	
	@Override
    public void readFromNBT (NBTTagCompound tags)
    {
        super.readFromNBT(tags);
        readCustomNBT(tags);
    }

    @Override
    public void writeToNBT (NBTTagCompound tags)
    {
        super.writeToNBT(tags);
        writeCustomNBT(tags);
    }
    
    //NBT

    @Override
    public void readCustomNBT (NBTTagCompound tags)
    {   	
    	currentFrozenTime = tags.getInteger("CurrentFrozenTime");
    	/*canBasin = tags.getBoolean("CanBasin");
    	canFrozen = tags.getBoolean("CanFrozen");*/
    	
        if (tags.getBoolean("hasFluid"))
        {
            if (tags.getInteger("itemID") != 0)
            {
                tank.setFluid(new FluidStack(tags.getInteger("itemID"), tags.getInteger("amount")));
            }
            else
            {
                tank.setFluid(FluidRegistry.getFluidStack(tags.getString("fluidName"), tags.getInteger("amount")));
            }
        }else{
        	tank.setFluid(null);
        }
        
        
        NBTTagList tagList = tags.getTagList("Items", 10);
		this.itemStacksSO = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < tagList.tagCount(); ++i) {
			NBTTagCompound tabCompound1 = tagList.getCompoundTagAt(i);
			byte byte0 = tabCompound1.getByte("Slot");

			if (byte0 >= 0 && byte0 < this.itemStacksSO.length) {
				this.itemStacksSO[byte0] = ItemStack.loadItemStackFromNBT(tabCompound1);
			}
		}
		
		//mode = tags.getInteger("Mode");
        
    }

    @Override
    public void writeCustomNBT (NBTTagCompound tags)
    {
        
        tags.setInteger("CurrentFrozenTime", currentFrozenTime);
        /*tags.setBoolean("CanBasin", canBasin);
        tags.setBoolean("CanFrozen", canFrozen);*/
        
    	FluidStack liquid = tank.getFluid();
        tags.setBoolean("hasFluid", liquid != null);
        if (liquid != null)
        {
            tags.setString("fluidName", liquid.getFluid().getName());
            tags.setInteger("amount", liquid.amount);
        }
        
        
        NBTTagList tagList = new NBTTagList();
		for (int i = 0; i < this.itemStacksSO.length; ++i) {
			if (this.itemStacksSO[i] != null) {
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte) i);
				this.itemStacksSO[i].writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		}

		tags.setTag("Items", tagList);
		//tags.setInteger("Mode", mode);
    }

    /* Packets */
    @Override
    public Packet getDescriptionPacket ()
    {
        NBTTagCompound tag = new NBTTagCompound();
        writeCustomNBT(tag);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tag);
    }

    @Override
    public void onDataPacket (NetworkManager net, S35PacketUpdateTileEntity packet)
    {
        readCustomNBT(packet.func_148857_g());
        worldObj.func_147479_m(xCoord, yCoord, zCoord);
    }
    
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public int getSizeInventory() {
		return this.itemStacksSO.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.itemStacksSO[slot];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (this.itemStacksSO[par1] != null) {
			ItemStack itemstack;
			if (this.itemStacksSO[par1].stackSize <= par2) {
				itemstack = this.itemStacksSO[par1];
				this.itemStacksSO[par1] = null;
				return itemstack;
			} else {
				itemstack = this.itemStacksSO[par1].splitStack(par2);

				if (this.itemStacksSO[par1].stackSize == 0) {
					this.itemStacksSO[par1] = null;
				}
				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (this.itemStacksSO[slot] != null) {
			ItemStack itemstack = this.itemStacksSO[slot];
			this.itemStacksSO[slot] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		this.itemStacksSO[slot] = itemstack;

		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.nameSO : StatCollector.translateToLocal("tile.SmartOutput.name");
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.nameSO != null && this.nameSO.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void openInventory() {
		
	}

	@Override
	public void closeInventory() {
		
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int par1) {
		return slotsProduct;
	}

	@Override
	public boolean canInsertItem(int par1, ItemStack itemstack, int par3) {
		//return this.isItemValidForSlot(par1, itemstack);
		return false;
	}

	@Override
	public boolean canExtractItem(int par1, ItemStack itemstack, int par3) {
		return itemstack != null;
	}
    
	
	
	public int getLiquidAmount(int par1){
		FluidTankInfo[] info = getTankInfo(ForgeDirection.UNKNOWN);
		FluidStack liquid = info[0].fluid;
		
		
		int capacity = 0;
		int liquidAmount = 0;
		int bar = 0;
		
		 if (liquid != null && info != null){
			 liquidAmount = liquid.amount;
			 capacity = info[0].capacity;
        }else{
        	liquidAmount = 0;
        	capacity = 0;
        }
		
		if(liquidAmount != 0 && capacity != 0){
			bar = (int) Math.floor(((double) liquidAmount / (double) capacity) * par1);
		}else{
			bar = 0;
		}
		return bar;
		
	}
	
	
	public void getCoord(int x1, int y1, int z1){
		/*x = x1;
		y = y1;
		z = z1;*/
	}
	
	
	
	/*public void checkConnection(){
		TileEntity TankX1 = worldObj.getTileEntity(xCoord +1, yCoord, zCoord);
		TileEntity TankX2 = worldObj.getTileEntity(xCoord -1, yCoord, zCoord);
		TileEntity TankZ1 = worldObj.getTileEntity(xCoord, yCoord, zCoord +1);
		TileEntity TankZ2 = worldObj.getTileEntity(xCoord, yCoord, zCoord -1);
		TileEntity TankY1 = worldObj.getTileEntity(xCoord, yCoord +1, zCoord);
		TileEntity TankY2 = worldObj.getTileEntity(xCoord, yCoord -1, zCoord);
		/*
		 *The key of connection :
		 * x+1 = 1
		 * x-1 = 2
		 * z+1 = 3
		 * z-1 = 4
		 * y+1 = 5
		 * y-1 = 6
		 * null = 0
		 *//*
		if(TankX1 != null && TankX1 instanceof IFluidHandler){
			connection = 1;
		}
		if(TankX2 != null && TankX2 instanceof IFluidHandler){
			connection = 2;
		}
		if(TankZ1 != null && TankZ1 instanceof IFluidHandler){
			connection = 3;
		}
		if(TankZ2 != null && TankZ2 instanceof IFluidHandler){
			connection = 4;
		}
		if(TankY1 != null && TankY1 instanceof IFluidHandler){
			connection = 5;
		}
		if(TankY2 != null && TankY2 instanceof IFluidHandler){
			connection = 6;
		}
		
	}*/
	
	/*public void setLiquid(){
		x = xCoord;
		y = yCoord;
		z = zCoord;
		
		if(connection == 1){
			x++;
		}else if(connection == 2){
			x--;
		}else if(connection == 3){
			z++;
		}else if(connection == 4){
			z--;
		}else if(connection == 5){
			y++;
		}else if(connection == 6){
			y--;
		}
		
		if (connection != 0)
        {			
			TileEntity otherTank = worldObj.getTileEntity(x, y, z);
        	
        	if(otherTank != null && otherTank instanceof IFluidHandler){
            	FluidTankInfo[] info = ((IFluidHandler) otherTank).getTankInfo(ForgeDirection.UNKNOWN);
            	
            	if(info != null){
            		FluidStack liquid = info[0].fluid;
            		otherLiquid = liquid;
            		otherTankInfo = info;
            	}
        	}
        }
	}*/

	/*
	 *The value of "mode"
	 *0 = off
	 *1 = input
	 *2 = output
	 */
	/*public void liquidIO(){
		//mode = 1;
		TileEntity otherTank = worldObj.getTileEntity(x, y, z);
		FluidTankInfo[] info = getTankInfo(ForgeDirection.UNKNOWN);
		
		if(otherTank != null && connection != 0){
			if(mode == 0){
				//NULL
			}
			if(mode == 1 && otherLiquid != null && info != null){
				//fill
				if(otherTankInfo != null){
					if(otherLiquid != null){
						int fillAmount = fill(ForgeDirection.UNKNOWN, otherLiquid, false);
						if (fillAmount == otherLiquid.amount){
							fill(ForgeDirection.UNKNOWN, otherLiquid, true);
							((IFluidHandler) otherTank).drain(ForgeDirection.UNKNOWN, otherLiquid.amount = fillAmount, true);
						}
					}	
            	}
				
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				markDirty();
			}
			if(mode == 2){
				//output
				if(otherTankInfo != null){
					if(info[0].fluid != null){
						int fillAmount = ((IFluidHandler) otherTank).fill(ForgeDirection.UNKNOWN, info[0].fluid, false);
						if(fillAmount != 0){
							((IFluidHandler) otherTank).fill(ForgeDirection.UNKNOWN, info[0].fluid, true);
							drain(ForgeDirection.UNKNOWN, fillAmount, true);
						}
					}	
            	}
				
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				markDirty();
            	
			}
		}
		
	}*/

	/*public void changeMode(int modex){
		modRest();
		if(modex == 1){
			mode++;			
		}else if(modex == 2){
			mode++;
			mode++;
		}else if(modex == 0){
			modRest();
		}
	}*/
	
	/*public void modRest(){
		if(mode != 0){
			mode--;
			if(mode != 0){
				mode--;
			}
		}
	}*/
	
	public int getOutputSize(){
		int size = 1;
		
		ItemStack slotUPG1 = new ItemStack(ItemRegistry.Upgrade, 1, 1);
		ItemStack slotUPG2 = new ItemStack(ItemRegistry.Upgrade, 1, 2);
		ItemStack slotUPG3 = new ItemStack(ItemRegistry.Upgrade, 1, 3);
		ItemStack slotUPG4 = new ItemStack(ItemRegistry.Upgrade, 1, 4);
		
		if(this.itemStacksSO[2] != null){
			if(this.itemStacksSO[2].isItemEqual(slotUPG1)){
				size = size+(itemStacksSO[2].stackSize)*1;
			}else if(this.itemStacksSO[2].isItemEqual(slotUPG2)){
				size = size+(itemStacksSO[2].stackSize)*2;
			}else if(this.itemStacksSO[2].isItemEqual(slotUPG3)){
				size = size+(itemStacksSO[2].stackSize)*3;
			}else if(this.itemStacksSO[2].isItemEqual(slotUPG4)){
				size = size+(itemStacksSO[2].stackSize)*4;
			}
		}
		
		if(this.itemStacksSO[3] != null){
			if(this.itemStacksSO[3].isItemEqual(slotUPG1)){
				size = size+(itemStacksSO[3].stackSize)*1;
			}else if(this.itemStacksSO[3].isItemEqual(slotUPG2)){
				size = size+(itemStacksSO[3].stackSize)*2;
			}else if(this.itemStacksSO[3].isItemEqual(slotUPG3)){
				size = size+(itemStacksSO[3].stackSize)*3;
			}else if(this.itemStacksSO[3].isItemEqual(slotUPG4)){
				size = size+(itemStacksSO[3].stackSize)*4;
			}
		}
		
		if(size > 64){
			return 64;
		}else{
			return size;
		}
	}
	
	public boolean hasRedstoneUPG(){
		ItemStack redStoneUPG = new ItemStack(ItemRegistry.Upgrade, 1, 5);
		
		if(this.itemStacksSO[2] != null){
			if(this.itemStacksSO[2].isItemEqual(redStoneUPG)){
				return true;
			}
		}
		
		if(this.itemStacksSO[3] != null){
			if(this.itemStacksSO[3].isItemEqual(redStoneUPG)){
				return true;
			}
		}

		return false;
	}
	
	/*boolean canFrozen = false;
	boolean canBasin = false;*/
	
	//Frozen!? Let it go! Let it go! Can't hold it back anymore~  - GKB 2015/4/4 22:22 (Tired...)
	public boolean canFrozen(){
		FluidTankInfo[] info = getTankInfo(ForgeDirection.UNKNOWN);
		FluidStack liquid = info[0].fluid;
		
		boolean hasPowered = true;
		boolean canStart = false;
		
		if(!worldObj.isRemote){
        	int power = worldObj.getBlockPowerInput(xCoord, yCoord, zCoord);
        	int strongestsPower = worldObj.getStrongestIndirectPower(xCoord, yCoord, zCoord);
        	
        	if(power != 0 || strongestsPower != 0){
        		hasPowered = true;
        	}else{
        		hasPowered = false;
        	}
		}
		
		if(hasRedstoneUPG()){
			if(hasPowered){
				return false;
			}else{
				canStart = true;
			}
		}else{
			canStart = true;
		}
		
		if(info != null && liquid != null && this.itemStacksSO[0] != null && canStart == true){
			if(recipes.getCastingFluidCost(liquid, itemStacksSO[0]) != null && recipes.getCastingFluidCost(liquid, itemStacksSO[0]).amount <= liquid.amount){
				if(recipes.getCastingRecipes(liquid, this.itemStacksSO[0]) != null){
					ItemStack itemstack = recipes.getCastingRecipes(liquid, this.itemStacksSO[0]);
					if(this.itemStacksSO[1] != null){
						int result = itemStacksSO[1].stackSize + itemstack.stackSize;
						return result <= this.getOutputSize() && result <= getInventoryStackLimit() && result <= this.itemStacksSO[1].getMaxStackSize() && this.itemStacksSO[1].isItemEqual(itemstack);
					}else{
						return true;
					}
				}
			}			
		}
		return false;
	}
	
	/*public boolean canBasin(){
		FluidTankInfo[] info = getTankInfo(ForgeDirection.UNKNOWN);
		FluidStack liquid = info[0].fluid;
		if(info != null && liquid != null && this.itemStacksSO[0] != null && hasPowered){
			if(recipes.getBasinRecipes(liquid, this.itemStacksSO[0], hasPowered) != null){
				ItemStack itemstack = recipes.getBasinRecipes(liquid, this.itemStacksSO[0], hasPowered);
				if(this.itemStacksSO[1] != null){
					int result = itemStacksSO[1].stackSize + itemstack.stackSize;
					return result <= getInventoryStackLimit() && result <= this.itemStacksSO[1].getMaxStackSize() && this.itemStacksSO[1].isItemEqual(itemstack);
				}else{
					return true;
				}
			}
		}
		return false;
	}*/
	
	/*public void checkMode(){
		if(canBasin()){
			canBasin = true;
		}else if(canFrozen()){
			canFrozen = true;
		}
	}*/
	
	public void frozen(){
		FluidTankInfo[] info = getTankInfo(ForgeDirection.UNKNOWN);
		FluidStack liquid = info[0].fluid;
		if(canFrozen() == true && info != null){
			ItemStack itemstack = recipes.getCastingRecipes(liquid, this.itemStacksSO[0]); // Product
			ItemStack bucket = new ItemStack(Items.bucket);
			//ItemStack basin = new ItemStack(TinkerSmeltery.searedBlock ,1 ,2);
			
			if(recipes.getCastingFluidCost(liquid, itemStacksSO[0]) != null && recipes.getCastingFluidCost(liquid, itemStacksSO[0]).amount <= liquid.amount){
				this.drain(ForgeDirection.UNKNOWN, recipes.getCastingFluidCost(liquid, itemStacksSO[0]), true);
				
				if (this.itemStacksSO[1] == null) {
					this.itemStacksSO[1] = itemstack.copy();
				} else if (this.itemStacksSO[1].getItem() == itemstack.getItem()) {
					this.itemStacksSO[1].stackSize += itemstack.stackSize;
				}
				
				if(SOEliminateList.eliminatedList().itemNeedToEliminate(itemStacksSO[0].toString())){
					if(itemStacksSO[0].stackSize == 1){
						itemStacksSO[0] = null;
					}else{
						--itemStacksSO[0].stackSize;
					}
				}
				
				if(recipes.isPattern(itemStacksSO[1])){
					if(itemStacksSO[0].stackSize == 1){
						itemStacksSO[0] = null;
					}else{
						--itemStacksSO[0].stackSize;
					}
				}
				
				/*if(itemStacksSO[0].isItemEqual(bucket)){
					if(itemStacksSO[0].stackSize == 1){
						itemStacksSO[0] = null;
					}else{
						--itemStacksSO[0].stackSize;
					}
				}*/
			}
		}
	}
	
	/*public void basin(){
		FluidTankInfo[] info = getTankInfo(ForgeDirection.UNKNOWN);
		FluidStack liquid = new FluidStack(info[0].fluid, 72);
		if(canBasin() == true && info != null){
			ItemStack itemstack = recipes.getBasinRecipes(liquid, this.itemStacksSO[0], hasPowered);
			
			
			if (this.itemStacksSO[1] == null) {
				this.itemStacksSO[1] = itemstack.copy();
			} else if (this.itemStacksSO[1].getItem() == itemstack.getItem()) {
				this.itemStacksSO[1].stackSize += itemstack.stackSize;
			}
			
			if(this.itemStacksSO[0] != null){
				if(itemStacksSO[0].stackSize == 1){
					itemStacksSO[0] = null;
				}else{
					--itemStacksSO[0].stackSize;
				}
			}
			
			this.drain(ForgeDirection.UNKNOWN, liquid, true);
		}
	}*/
	
	//Change mode (Speed)
	/*public void changeMode(){
        if(!worldObj.isRemote){
        	int power = worldObj.getBlockPowerInput(x, y, z);
        	int strongestsPower = worldObj.getStrongestIndirectPower(x, y, z);
        	
        	if(power != 0 || strongestsPower != 0){
        		this.hasPowered = true;
        	}else{
        		this.hasPowered = false;
        	}
        }
        //System.out.println(hasPowered);
	}*/	
	
	public void voidLiquid(){
		FluidTankInfo[] info = getTankInfo(ForgeDirection.UNKNOWN);
		FluidStack liquid = info[0].fluid;
		if(liquid != null){
			int amount = liquid.amount;
			int toVoid = this.tank.drain(amount, false).amount;
			this.tank.drain(toVoid, true);
			this.markDirty();
		}
	}
	
	@SideOnly(Side.CLIENT)
	public int getFrozenProgressScaled(int par1) {
		return this.currentFrozenTime * par1 / frozenTimeMax;
	}
	
    /* Updating */
    @Override
    public boolean canUpdate ()
    {
        return true;
    }
    

    @Override
    public void updateEntity ()
    {   	
    	//checkConnection();
    	//setLiquid();
    	//liquidIO();

    	//System.out.println(mode);
    	//checkMode();
    	//voidLiquid();
    	if(canFrozen()){
    		if(currentFrozenTime >= frozenTimeMax){
    			currentFrozenTime = 0;
    			frozen();
    			//notifyMasterOfChange();
    		}else{
    				currentFrozenTime++;
    		}
    		
    		this.markDirty();
    	}else{
    		currentFrozenTime = 0;
    	}
    	
    	/*if(canBasin() || canFrozen()){
    		if(currentFrozenTime == frozenTimeMax){
    			currentFrozenTime = 0;
    			if(hasPowered && canBasin){
    				basin();
    			}else{
    				frozen();
    			}
    			notifyMasterOfChange();
    		}else{
    			currentFrozenTime++;
    		}
    		this.markDirty();
    	}else{
    		currentFrozenTime = 0;
    	}*/
    }    

}

