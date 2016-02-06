package tinker_io.TileEntity.fim;

import tinker_io.api.IState;
import tinker_io.api.IStateMachine;
import net.minecraft.item.ItemStack;

public class FuelFSM {
	private boolean state;
	FIMTileEntity tile;
	FuelFSM(FIMTileEntity tile, boolean state) {
		this.state = state;
		this.tile = tile;
	}
	
	 void update() {
//		System.out.println(inputTime);
		if (state) {
			heat();
		} else {
			waitFuel();
		}
	}
	
	 void revert() {
		if (state) {
			heat();
		}
	}
	
	 void heat() {
		tile.isActive = true;
		tile.inputTime -= 10;
		if (tile.inputTime == 0) {
			state = false;
		}
	}
	
	 void waitFuel() {
		tile.isActive = false;
		if (FuelFSM.getStackSize(tile.getSlots()[1]) > 0) {
			cousumeFuel();
			tile.inputTime = 300;
			
			state = true;
		}
	}
	
	 void cousumeFuel() {
		tile.getSlots()[1].stackSize -= 1;
		if (tile.getSlots()[1].stackSize == 0) {
			tile.getSlots()[1] = tile.getSlots()[1].getItem().getContainerItem(tile.getSlots()[1]);
		}
	}
	 
	public static int getStackSize(ItemStack stack) {
		return stack == null ? 0 : stack.stackSize;
	}
}

abstract class Process implements IState {
	@Override
	abstract public void change(IStateMachine m);
}

class ProcessSpeedUp extends Process {
	@Override
	public void change(IStateMachine m){
		
	}
}

class ProcessWait extends Process {
	@Override
	public void change(IStateMachine m) {
		
	}
}



