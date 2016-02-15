package tinker_io.TileEntity.fim;

import tinker_io.api.IState;
import tinker_io.api.IStateMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class FuelFSM implements IStateMachine, ITickable, NBTable {
	
	private IState currentState;
	public boolean isActive = false;
	public boolean canChangeState = false;
	FIMTileEntity tile;
	
	public FuelFSM(FIMTileEntity tile)
	{
		this.tile = tile;
	}
	
	public void init()
	{
		this.currentState = this.isActive? new ProcessSpeedUp() : new ProcessWaitFuel();
	}
	
	@Override
	 public void update() {
		 this.currentState.accept(this);
	}
	
	 public boolean isSpeedingUp() {
		 return tile.inputTime > 0;
	 }
	 
	 public int getFuelStackSize() {
		 ItemStack stack = tile.getSlots()[1];
		 return FuelFSM.getStackSize(stack);
	 }
	 
	public static int getStackSize(ItemStack stack) {
		return stack == null ? 0 : stack.stackSize;
	}

	@Override
	public void setState(IState state) {
		this.currentState = state;
	}
	
	public void startChangeState()
	{
		this.canChangeState = true;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		this.isActive = tag.getBoolean("isActive");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		tag.setBoolean("isActive", isActive);
	}
}