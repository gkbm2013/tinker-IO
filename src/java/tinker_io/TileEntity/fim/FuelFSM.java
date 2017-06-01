package tinker_io.TileEntity.fim;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import tinker_io.config.TIOConfig;

public class FuelFSM implements ITickable, NBTable
{

    public final Process speedup = new ProcessSpeedUp();
    public final Process waitFuel = new ProcessWaitFuel();

    private Process currentState;
    public boolean isActive = false;
    public boolean canChangeState = false;
    FIMTileEntity tile;


    public FuelFSM(FIMTileEntity tile)
    {
        this.tile = tile;
    }


    public void init()
    {
        this.currentState = this.isActive ? this.speedup : this.waitFuel;
    }


    @Override
    public void update()
    {
        this.currentState.accept(this);
    }


    public boolean isSpeedingUp()
    {
        return tile.inputTime > 0;
    }


    public int computeFuelTemp()
    {
        final ItemStack stack = tile.getSlot(1);

        return TileEntityFurnace.getItemBurnTime(stack);
    }


    public int computeInputTime()
    {
        return TIOConfig.fuelConsumptionRateFIM;
    	//return 200;
    }


    public int getFuelStackSize()
    {
        ItemStack stack = tile.getSlot(1);
        return FuelFSM.getStackSize(stack);
    }


    public static int getStackSize(ItemStack stack)
    {
        return (stack == null || (stack != null && stack.isEmpty())) ? 0 : stack.getCount();
    }


    public void setProcess(Process state)
    {
        this.currentState = state;
    }


    public void startChangeState()
    {
        this.canChangeState = true;
    }


    void heat()
    {
        isActive = true;
        tile.inputTime -= 4;
    }


    @Override
    public NBTTagCompound readFromNBT(NBTTagCompound tag)
    {
        this.isActive = tag.getBoolean("isActive");
        return tag;
    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        tag.setBoolean("isActive", isActive);
        return tag;
    }
}