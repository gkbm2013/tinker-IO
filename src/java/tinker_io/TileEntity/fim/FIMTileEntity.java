package tinker_io.TileEntity.fim;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.smeltery.tileentity.TileSmeltery;
import slimeknights.tconstruct.smeltery.tileentity.TileSmelteryComponent;
import tinker_io.TileEntity.SidedInventory;
import tinker_io.api.Observable;
import tinker_io.api.Observer;
import tinker_io.reflection.TempField;
import tinker_io.registry.ItemRegistry;
import tinker_io.registry.RegisterUtil;

public class FIMTileEntity extends TileSmelteryComponent implements ITickable, Observable<Observer>, ISidedInventory
{
    static final int[] slotsSpeedUPG = new int[] { 0 };
    static final int[] slotsFuel = new int[] { 1 };

    public FIMTileEntity(){
    	
    }

    public ItemStack fuel = new ItemStack(RegisterUtil.SolidFuel);
    private List<Observer> obs = new ArrayList<Observer>();

    protected SCInfo scInfo;
    protected FuelFSM fuelFSM = FuelFSMFactory.getNewFuelFSM(this);


    @Override
    public void onLoad()
    {
        scInfo = SCInfoFactory.getSmelyeryControllerInfo(this);
        fuelFSM.init();
    }

    protected int tick = 0;
    protected int timer = 0;

    public int keepInputTime;
    public int inputTime;
    public int fuelTemp;
    private int currentTemp;


    @Override
    public void update()
    {
        if (!this.world.isRemote)
        {
            if (tick % 4 == 0)
            {
                toUpdateSCInfoAndSpeedUpSC();
            }
            tick = (tick + 1) % 20;
        }
    }

    private void toUpdateSCInfoAndSpeedUpSC()
    {
        this.scInfo.update(this.getSmeltery());
        this.fuelFSM.update();
        
        if (scInfo.canFindSCPos() /*&& scInfo.isSCHeatingItem()*/)
        {
        	if(scInfo.isSCHeatingItem()){
        		fuelFSM.startChangeState();
        	}
        	
        	if(inputTime > 0){
        		notifyBlockUpdate();
        	}
        	
        	Adapter adap = scInfo.getAdapter();
            this.updateTemp(adap.getFuelTemp(), adap);
        }
        if (fuelFSM.isActive)
        {
            this.notifyObservers();
        }
    }



	private void updateTemp(final int originFuelTemp, Adapter adap)
    {       
    	//I am reluctant to use reflection. However to preserve Fuel Input Machine, I have to do this.
    	calculateTemperature(originFuelTemp);
        TempField field = new TempField(world, adap.getPos());
        field.setTemp(currentTemp);
    }
    
    public void resetTemp(){
    	this.scInfo.update(this.getSmeltery());
    	if (scInfo.canFindSCPos()){
    		Adapter adap = scInfo.getAdapter();
    		TempField field = new TempField(world, adap.getPos());
            field.setTemp(adap.getFuelTemp());
    	}
    }
    
    public TileSmeltery getSmeltery() {
    	TileSmeltery smelteryTileEntity = null;
    	
    	TileEntity te = this.getSmelteryTankHandler();
    	if(te != null && te instanceof TileSmeltery){
    		smelteryTileEntity = (TileSmeltery) te;
    	}
		return smelteryTileEntity;
	}
    
    public TileSmeltery getMasterTE(){
    	TileSmeltery smelteryTileEntity = null;
    	if(this.getMasterPosition() != null){
    		TileEntity te = world.getTileEntity(this.getMasterPosition());
    		if(te instanceof TileSmeltery){
    			smelteryTileEntity = (TileSmeltery) te;
    		}
    	}
    	return smelteryTileEntity; 
    }
    
    private void calculateTemperature(int originFuelTemp){
    	double ratio = this.getSpeedUpInfo().ratio;
        int fuelTempWithRatio = fuelTemp * (int) ratio;
        
        int f = fuelTempWithRatio / 2 + originFuelTemp;
        
        if(fuelTempWithRatio <= 20000){
            f = (fuelTempWithRatio * 6) / 100  + originFuelTemp;
        }
        if(f >= 200000){
            f = 200000;
        }
        currentTemp = f;
    }


    public int getSpeedUpTemp(final int originFuelTemp)
    {
        //      return getStackSize(this.getSlots()[0]) * 200 + originFuelTemp;
        return TileEntityFurnace.getItemBurnTime(inv.getSlot(1)) + originFuelTemp;
    }


    public void onNeighborChange(IBlockAccess world, BlockPos neighbor)
    {
//        TODO
//        scInfo.manager.onNeighborChange(world, neighbor);
    }


    public int getStackSize(ItemStack stack)
    {
        return stack == null ? 0 : stack.getCount();
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
        for (Observer o : this.obs)
        {
            o.receivedTopic();
        }
    }

    
    /**
     *  loading and saving
     */

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        this.inputTime = tag.getShort("InputTime");
        this.keepInputTime = tag.getShort("keepInputTime");
        this.currentTemp = tag.getInteger("currentTemp");

        this.inv.readFromNBT(tag);
        this.fuelFSM.readFromNBT(tag);
    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        tag.setShort("InputTime", (short) this.inputTime);
        tag.setShort("keepInputTime", (short) this.keepInputTime);
        tag.setInteger("currentTemp", this.currentTemp);

        this.inv.writeToNBT(tag);
        this.fuelFSM.writeToNBT(tag);
        
        return tag; 
    }


    /*
     * GUI
     */

    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int pixels)
    {
        final int n = (this.inputTime < 0) ? 0 : this.inputTime;

        int i = this.keepInputTime;
        if (i == 0)
        {
            i = 1000;
        }

        return pixels - (n * pixels / i);
    }
    
    public int getCurrentTempture(){
        return currentTemp;
    }

    public boolean hasFuel()
    {
        if (inv.getSlot(1) != null && TileEntityFurnace.getItemBurnTime(inv.getSlot(1)) > 0)
        {
            return true;
        }
        return false;
    }


    public SpeedUpRatio getSpeedUpInfo()
    {
        double temp = this.getStackSize(inv.getSlot(0)) * 0.1 + 1.0;
        return new SpeedUpRatio(temp);
    }


    @SideOnly(Side.CLIENT)
    public String getDirection()
    {
//        TODO
//        return this.scInfo.getFacing();
        return "NONE";
    }


    public int getInputTime()
    {
        return this.inputTime;
    }
    /*
     * Data
     */

    public static class SpeedUpRatio
    {
        public double ratio;


        public SpeedUpRatio(double temp)
        {
            this.ratio = temp;
        }
    }
    
    /*
     * sideinventory
     */
    
    private SidedInventory inv = new SidedInventory(this, null, 2)
    {

        /**
         * Returns true if automation is allowed to insert the given stack
         * (ignoring stack size) into the given slot.
         */
        @Override
        public boolean isItemValidForSlot(int index, ItemStack itemstack)
        {
            if (TileEntityFurnace.getItemBurnTime(itemstack) > 0)
            {
                return true;
            }
            return false;
        }


        @Override
        public int[] getSlotsForFace(EnumFacing side)
        {
            return slotsFuel;
        }


        /**
         * Returns true if automation can insert the given item in the given slot from the given side.
         * Args: slot, item, side
         */
        @Override
        public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
        {
            return this.isItemValidForSlot(index, itemStackIn);
        }


        /**
         * Returns true if automation can extract the given item in the given slot from the given side.
         * Args: slot, item, side
         */
        @Override
        public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
        {
            //return par3 != 0 || par1 != 1 || itemstack.getItem() == Items.bucket;
            return false;
        }


        @Override
        public void markDirty()
        {
            throw new UnsupportedOperationException();            
        }


		@Override
		public boolean isEmpty() {
			return false;
		}

    };
    
    

    @Override
    public int getSizeInventory()
    {
        return inv.getSizeInventory();
    }


    @Override
    public ItemStack getStackInSlot(int index)
    {
        return inv.getStackInSlot(index);
    }


    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        return inv.decrStackSize(index, count);
    }


    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        return inv.removeStackFromSlot(index);
    }


    @Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        inv.setInventorySlotContents(index, stack);
    }


    @Override
    public int getInventoryStackLimit()
    {
        return inv.getInventoryStackLimit();
    }


    @Override
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        return inv.isUsableByPlayer(player);
    }


    @Override
    public void openInventory(EntityPlayer player)
    {
        inv.openInventory(player);
    }


    @Override
    public void closeInventory(EntityPlayer player)
    {
        inv.closeInventory(player);
    }


    @Override
    public int getField(int id)
    {
        throw new UnsupportedOperationException();
    }


    @Override
    public void setField(int id, int value)
    {
        throw new UnsupportedOperationException();        
    }


    @Override
    public int getFieldCount()
    {
        throw new UnsupportedOperationException();
    }


    @Override
    public void clear()
    {
        inv.clear();
    }


    @Override
    public String getName()
    {
        return inv.getName();
    }


    @Override
    public boolean hasCustomName()
    {
        return inv.hasCustomName();
    }


    @Override
    public ITextComponent getDisplayName()
    {
        return inv.getDisplayName();
    }


    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return inv.isItemValidForSlot(index, stack);
    }


    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        return inv.getSlotsForFace(side);
    }


    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
    	return inv.canInsertItem(index, itemStackIn, direction);
    }


    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        return inv.canExtractItem(index, stack, direction);
    }
    
    public ItemStack getSlot(int i)
    {
        return inv.getSlot(i);
    }
    
    public void setSlot(int i, ItemStack stack)
    {
        inv.setSlot(i, stack);
    }
    
    private void notifyBlockUpdate(){
		if(world!=null && pos != null){
			IBlockState state = world.getBlockState(pos);
			world.notifyBlockUpdate(pos, state, state, 3);
		}
	}

	@Override
	public boolean isEmpty() {
		return false;
	}
}