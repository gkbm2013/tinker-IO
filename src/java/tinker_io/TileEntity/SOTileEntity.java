package tinker_io.TileEntity;

//import tconstruct.smeltery.TinkerSmeltery;
import tinker_io.handler.SORecipe;
import tinker_io.registry.RegisterUtil;
import slimeknights.mantle.multiblock.MultiServantLogic;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
//import net.minecraftforge.common.util.ForgeDirection;1.7
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SOTileEntity extends MultiServantLogic implements IFluidHandler, IFluidTank, ISidedInventory, ITickable{
	public FluidTank tank;
	
	public FluidStack otherLiquid;
	public FluidTankInfo[] otherTankInfo = null;
	
	public ItemStack[] itemStacksSO = new ItemStack[4];
	
	final int[] slotsPattern = new int[] { 0 };
	final int[] slotsProduct = new int[] { 1 };
	final int[] slotsUPGup = new int[] { 2 };
	final int[] slotsUPGdown = new int[] { 3 };
	
	int connection = 0;
	int tick = 0;
	
	public int currentFrozenTime = 0;
	public int frozenTimeMax = 2; //Ticks
	
	SORecipe recipes = new SORecipe();
	
	private String nameSO;
	
	public SOTileEntity(){
		tank = new FluidTank(Fluid.BUCKET_VOLUME * 10);
		for(int i = 0; i < itemStacksSO.length; i++){
			this.itemStacksSO[i] = ItemStack.EMPTY;
		}
	}

	/**
	 * Fills fluid into internal tanks, distribution is left entirely to the IFluidHandler.
	 */
	@Override
	public int fill(FluidStack resource, boolean doFill) {
		//TODO
		int amount = tank.fill(resource, doFill);
        /*if (amount > 0 && doFill){
        	this.notifyBlockUpdate();
        }*/
		this.notifyBlockUpdate();
        return amount;
	}
	
	
	/**
	 * Drains fluid out of internal tanks, distribution is left entirely to the IFluidHandler.
	 */
	/*@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return this.drain(resource, doDrain);
	}*/
	
	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if (tank.getFluidAmount() == 0)
            return null;
        if (tank.getFluid().getFluid() != resource.getFluid())
            return null;
        this.notifyBlockUpdate();
		return this.drain(resource.amount, doDrain);
	}

	/*@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return this.drain(maxDrain, doDrain);
	}*/
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		FluidStack amount = tank.drain(maxDrain, doDrain);
        if (amount != null && doDrain)
        {
            //worldObj.markBlockForUpdate(this.pos);
        	this.notifyBlockUpdate();
        }
        return amount;
	}

	/*@Override
	public boolean canFill(EnumFacing from, Fluid fluid) {
		return tank.getFluidAmount() == 0 || (tank.getFluid().getFluid() == fluid && tank.getFluidAmount() < tank.getCapacity());
	}*/

	/*@Override
	public boolean canDrain(EnumFacing from, Fluid fluid) {
		return tank.getFluidAmount() > 0;
	}*/

	@Override
	public FluidTankInfo getInfo() {
		return this.tank.getInfo();
	}
	
	/*@Override
	public FluidTankInfo[] getTankInfo() {
		FluidStack fluid = null;
        if (tank.getFluid() != null)
            fluid = tank.getFluid().copy();
        return new FluidTankInfo[] { new FluidTankInfo(fluid, tank.getCapacity()) };
	}*/

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return this.tank.getTankProperties();
	}
	
	@Override
	public FluidStack getFluid() {
		return tank.getFluid();
	}

	@Override
	public int getFluidAmount() {
		if(tank.getFluid() != null){
			return tank.getFluid().amount;
		}else{
			return 0;
		}
	}

	@Override
	public int getCapacity() {
		return tank.getCapacity();
	}
	
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	@SuppressWarnings("unchecked")
	@Nonnull
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return (T) tank;
		}
		return super.getCapability(capability, facing);
	}
	
	//NBT
	@Override
    public void readFromNBT (NBTTagCompound tags){
        super.readFromNBT(tags);
        readCustomNBT(tags);
    }

    @Override
    public NBTTagCompound writeToNBT (NBTTagCompound tags){
        super.writeToNBT(tags);
        writeCustomNBT(tags);
        return tags;
    }

    @Override
    public void readCustomNBT (NBTTagCompound tags){   	
    	currentFrozenTime = tags.getInteger("CurrentFrozenTime");
    	
        if (tags.getBoolean("hasFluid")){
            if (tags.getInteger("itemID") != 0){
//                tank.setFluid(new FluidStack(tags.getInteger("itemID"), tags.getInteger("amount")));
            }else{
            	FluidStack tankFluid = FluidRegistry.getFluidStack(tags.getString("fluidName"), tags.getInteger("amount"));
                if(tags.getString("fluidTag") != null){
                	tankFluid = new FluidStack(tankFluid.getFluid(), tankFluid.amount, (NBTTagCompound) tags.getTag("fluidTag"));
                }
                tank.setFluid(tankFluid);
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
				//this.itemStacksSO[byte0] = ItemStack.loadItemStackFromNBT(tabCompound1);
				this.itemStacksSO[byte0] = new ItemStack(tabCompound1);
			}
		}
//mode = tags.getInteger("Mode");
    }

    @Override
    public NBTTagCompound writeCustomNBT (NBTTagCompound tags)
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
            if(this.tank.getFluid() != null && this.tank.getFluid().tag != null){
            	tags.setTag("fluidTag", this.tank.getFluid().tag);
            }else{
            	//tags.setTag("fluidTag", new NBTTagCompound());
            }
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
		return tags;
    }
    
	//Packet
	 @Override
	 public SPacketUpdateTileEntity getUpdatePacket() {	 
	     NBTTagCompound tag = new NBTTagCompound();
	     this.writeToNBT(tag);
	     return new SPacketUpdateTileEntity(pos, 1, tag);
	 }
	 
	 @Override
	 public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
	     readFromNBT(packet.getNbtCompound());
	 }
    
    @Override
	public int getSizeInventory() {
		return this.itemStacksSO.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if(this.itemStacksSO[slot] == null){
			this.itemStacksSO[slot] = ItemStack.EMPTY;
		}
		return this.itemStacksSO[slot];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (this.itemStacksSO[par1] != null) {
			ItemStack itemstack;
			if (this.itemStacksSO[par1].getCount() <= par2) {
				itemstack = this.itemStacksSO[par1];
				this.itemStacksSO[par1] = ItemStack.EMPTY;
				return itemstack;
			} else {
				itemstack = this.itemStacksSO[par1].splitStack(par2);

				if (this.itemStacksSO[par1].getCount() == 0) {
					this.itemStacksSO[par1] = ItemStack.EMPTY;
				}
				return itemstack;
			}
		} else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		this.itemStacksSO[slot] = itemstack;

		if (itemstack != null && itemstack.getCount() > this.getInventoryStackLimit()) {
			itemstack.setCount(this.getInventoryStackLimit());
		}
	}

	public int getLiquidAmount(int par1){
		//FluidTankInfo[] info = getTankInfo();
		FluidTankInfo info = getInfo();
		FluidStack liquid = info.fluid;
		
		
		int capacity = 0;
		int liquidAmount = 0;
		int bar = 0;
		
		 if (liquid != null && info != null){
			 liquidAmount = liquid.amount;
			 capacity = info.capacity;
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
	
	public int getOutputSize(){
		int size = 1;
		
		ItemStack slotUPG1 = new ItemStack(RegisterUtil.Upgrade, 1, 1);
		ItemStack slotUPG2 = new ItemStack(RegisterUtil.Upgrade, 1, 2);
		ItemStack slotUPG3 = new ItemStack(RegisterUtil.Upgrade, 1, 3);
		ItemStack slotUPG4 = new ItemStack(RegisterUtil.Upgrade, 1, 4);
		
		if(this.itemStacksSO[2] != null){
			if(this.itemStacksSO[2].isItemEqual(slotUPG1)){
				size = size+(itemStacksSO[2].getCount())*1;
			}else if(this.itemStacksSO[2].isItemEqual(slotUPG2)){
				size = size+(itemStacksSO[2].getCount())*2;
			}else if(this.itemStacksSO[2].isItemEqual(slotUPG3)){
				size = size+(itemStacksSO[2].getCount())*3;
			}else if(this.itemStacksSO[2].isItemEqual(slotUPG4)){
				size = size+(itemStacksSO[2].getCount())*4;
			}
		}
		
		if(this.itemStacksSO[3] != null){
			if(this.itemStacksSO[3].isItemEqual(slotUPG1)){
				size = size+(itemStacksSO[3].getCount())*1;
			}else if(this.itemStacksSO[3].isItemEqual(slotUPG2)){
				size = size+(itemStacksSO[3].getCount())*2;
			}else if(this.itemStacksSO[3].isItemEqual(slotUPG3)){
				size = size+(itemStacksSO[3].getCount())*3;
			}else if(this.itemStacksSO[3].isItemEqual(slotUPG4)){
				size = size+(itemStacksSO[3].getCount())*4;
			}
		}
		
		if(size > 64){
			return 64;
		}else{
			return size;
		}
	}
	
	public boolean hasRedstoneUPG(){
		ItemStack redStoneUPG = new ItemStack(RegisterUtil.Upgrade, 1, 5);
		
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
	
	public boolean hasBasinUPG(){
		ItemStack basinUPG = new ItemStack(RegisterUtil.Upgrade ,1 ,7);
		
		if(this.itemStacksSO[2] != null){
			if(this.itemStacksSO[2].isItemEqual(basinUPG)){
				return true;
			}
		}
		
		if(this.itemStacksSO[3] != null){
			if(this.itemStacksSO[3].isItemEqual(basinUPG)){
				return true;
			}
		}

		return false;
	}
	
	//boolean canFrozen = false;
	//boolean canBasin = false;
	
	public String getMode(){
		String mode = null;
		
		FluidTankInfo info = getInfo();
		FluidStack liquid = info.fluid;
		ItemStack resultItem = ItemStack.EMPTY;
		
		if(info != null && liquid != null){			
			if(hasBasinUPG()){
				if(recipes.getBasinFluidCost(liquid, itemStacksSO[0]) != null && recipes.getBasinFluidCost(liquid, itemStacksSO[0]).amount <= liquid.amount){
					if(recipes.getBasinResult(liquid, itemStacksSO[0]) != null){
						resultItem = recipes.getBasinResult(liquid, this.itemStacksSO[0]);
						mode = "basin";
					}
				}
			}else{
				if(recipes.getCastingFluidCost(liquid, itemStacksSO[0]) != null && recipes.getCastingFluidCost(liquid, itemStacksSO[0]).amount <= liquid.amount){
					if(recipes.getCastingRecipes(liquid, this.itemStacksSO[0]) != null){
						resultItem = recipes.getCastingRecipes(liquid, this.itemStacksSO[0]);
						mode = "table";
					}
				}
			}
			
			if(this.itemStacksSO[1] != null && !this.itemStacksSO[1].isEmpty() && resultItem != null && !resultItem.isEmpty()){
				int resultSize = itemStacksSO[1].getCount() + resultItem.getCount();
				mode = (resultSize <= this.getOutputSize() &&
						resultSize <= getInventoryStackLimit() && 
						resultSize <= this.itemStacksSO[1].getMaxStackSize() && 
						this.itemStacksSO[1].isItemEqual(resultItem))
						? mode : null;
			}
		}
		
		return mode;
	}
	
	//Frozen!? Let it go! Let it go! Can't hold it back anymore~  - GKB 2015/4/4 22:22 (Tired...)
	public boolean canFrozen(){
		
		boolean hasPowered = world.isBlockPowered(this.pos);
		boolean canStart = false;
		
		if(hasRedstoneUPG()){
			if(hasPowered){
				canStart = false;
			}else{
				canStart = true;
			}
		}else{
			canStart = true;
		}
		
		if(canStart && getMode() != null){
			return true;
		}
		
		/*if(info != null && liquid != null && this.itemStacksSO[0] != null && canStart == true){
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
		}*/
		return false;
	}
	
	public void frozen(){
		FluidTankInfo info = getInfo();
		FluidStack liquid = info.fluid;
		ItemStack product = ItemStack.EMPTY;
		FluidStack fluidCost = null;
		String mode = getMode();
		
		if(canFrozen() == true && info != null && mode.equals("table")){
			product = recipes.getCastingRecipes(liquid, this.itemStacksSO[0]); // Product
			fluidCost = recipes.getCastingFluidCost(liquid, itemStacksSO[0]);
		}else if(canFrozen() == true && info != null && mode.equals("basin")){
			product = recipes.getBasinResult(liquid, this.itemStacksSO[0]); // Product
			fluidCost = recipes.getBasinFluidCost(liquid, itemStacksSO[0]);
		}
		if(product != null && !product.isEmpty() && fluidCost != null && fluidCost.amount <= liquid.amount){
			this.drain(fluidCost, true);
			
			if (this.itemStacksSO[1] != null && this.itemStacksSO[1].isEmpty()) {
				this.itemStacksSO[1] = product.copy();
			} else if (this.itemStacksSO[1].getItem() == product.getItem()) {
				this.itemStacksSO[1].grow(product.getCount());
			}
			
			if(itemStacksSO[0] != null && (mode.equals("basin") || recipes.isConsumeCast(liquid, itemStacksSO[0]))){
				if(itemStacksSO[0].getCount() == 1){
					itemStacksSO[0] = ItemStack.EMPTY;
				}else{
					//--itemStacksSO[0].stackSize;
					itemStacksSO[0].grow(-1);
				}
			}
		}
	}
	
	public void voidLiquid(){
		FluidTankInfo info = getInfo();
		FluidStack liquid = info.fluid;
		if(liquid != null){
			int amount = liquid.amount;
			int toVoid = this.tank.drain(amount, false).amount;
			this.tank.drain(toVoid, true);
			this.notifyBlockUpdate();
		}
	}
	
	@SideOnly(Side.CLIENT)
	public int getFrozenProgressScaled(int par1) {
		return this.currentFrozenTime * par1 / frozenTimeMax;
	}
	
    /* Updating */
    
    public boolean canUpdate ()
    {
        return true;
    }
    
    
    public void update() {
    	if (world.isRemote){return;}
    	//this.notifyBlockUpdate();
    	
    	if(canFrozen()){
    		if(currentFrozenTime >= frozenTimeMax){
    			currentFrozenTime = 0;
    			frozen();
    			
    		}else{
    				currentFrozenTime++;
    		}
    		
    		this.notifyBlockUpdate();
    	}
    	
    	if(tank.canFill() || tank.canDrain()){
    		if(tick % 4 == 0){
    			this.notifyBlockUpdate();
    			tick = 0;
    		}
    		tick++;
    	}
    }

	@Override
	public ItemStack removeStackFromSlot(int index) {
		if (this.itemStacksSO[index] != null) {
			ItemStack itemstack = this.itemStacksSO[index];
			this.itemStacksSO[index] = ItemStack.EMPTY;
			return itemstack;
		} else {
			return ItemStack.EMPTY;
		}
	}
	
	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq(this.pos.add(0.5D, 0.5D, 0.5D)) <= 64.0D;
	}
	
	@Override
	public void openInventory(EntityPlayer player) {
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		
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
		// TODO
		return 0;
	}

	@Override
	public void clear() {
		// TODO 
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.nameSO : I18n.format("tile.smart_output.name");
	}

	@Override
	public boolean hasCustomName() {
		return this.nameSO != null && this.nameSO.length() > 0;
	}
	
	@Override
	public ITextComponent getDisplayName() {
		// TODO
		return null;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return this.slotsProduct;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn,
			EnumFacing direction) {
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack,
			EnumFacing direction) {
		return stack != null && !stack.isEmpty();
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return true;
	}
	
	private void notifyBlockUpdate(){
		if(world!=null && pos != null){
			IBlockState state = world.getBlockState(pos);
			world.notifyBlockUpdate(pos, state, state, 3);
			world.markChunkDirty(pos, this);
		}
	}

	@Override
	public boolean isEmpty() {
		// TODO
		return false;
	}

	//Tank
	
	/*@Override
	public FluidStack getFluid() {
		return tank.getFluid();
	}

	@Override
	public int getFluidAmount() {
		if(tank.getFluid() != null){
			return tank.getFluid().amount;
		}else{
			return 0;
		}
	}

	@Override
	public int getCapacity() {
		return tank.getCapacity();
	}

	@Override
	public FluidTankInfo getInfo() {
		FluidStack fluid = null;
        if (tank.getFluid() != null)
            fluid = tank.getFluid().copy();
        return new FluidTankInfo(fluid, tank.getCapacity());
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		int amount = tank.fill(resource, doFill);
        if (amount > 0 && doFill)
        {
            //worldObj.markBlockForUpdate(this.pos);
        	this.notifyBlockUpdate();
        }
        return amount;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		System.out.println("~");
		FluidStack amount = tank.drain(maxDrain, doDrain);
        if (amount != null && doDrain)
        {
            //worldObj.markBlockForUpdate(this.pos);
        	this.notifyBlockUpdate();
        }
        return amount;
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return tank.getTankProperties();
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if (tank.getFluidAmount() == 0)
            return null;
        if (tank.getFluid().getFluid() != resource.getFluid())
            return null;
		return this.drain(resource.amount, doDrain);
	}*/
}

