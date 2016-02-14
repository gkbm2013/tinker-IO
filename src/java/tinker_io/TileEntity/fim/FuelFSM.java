package tinker_io.TileEntity.fim;

import tinker_io.api.IState;
import tinker_io.api.IStateMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;

public class FuelFSM implements IStateMachine, ITickable {
	
	private IState currentState;
	FIMTileEntity tile;
	
	FuelFSM(FIMTileEntity tile, boolean state) {
		this.currentState = (state)?
				new ProcessSpeedUp() : new ProcessWaitFuel();
		this.tile = tile;
	}
	
	@Override
	 public void update() {
		 this.currentState.accept(this);
	}
	
	 public void revert() {
		 this.revertWhenNotFindSC();
	}
	 
	 private void revertWhenNotFindSC() {
		 if (currentState instanceof ProcessSpeedUp) {
			 this.update();
		 }
	 }
	 
	 void heat() {
		tile.isActive = true;
		tile.inputTime -= 10;
	}
	
	 void waitFuel() {
		tile.isActive = false;
		if (FuelFSM.getStackSize(tile.getSlots()[1]) > 0) {
			tile.inputTime = 300;
		}
	}
	
	 void cousumeFuel() {
		tile.getSlots()[1].stackSize -= 1;
		if (tile.getSlots()[1].stackSize == 0) {
			tile.getSlots()[1] = tile.getSlots()[1].getItem().getContainerItem(tile.getSlots()[1]);
		}
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
}