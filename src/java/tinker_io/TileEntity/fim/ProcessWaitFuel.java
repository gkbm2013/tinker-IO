package tinker_io.TileEntity.fim;

import tinker_io.api.IStateMachine;

public class ProcessWaitFuel extends Process {
	
	@Override public void accept(FuelFSM m) {
		m.tile.isActive = false;
		if (m.getFuelStackSize() > 0) {
			m.tile.inputTime = 300;
			m.cousumeFuel();
			change(m);
		}
	}
	
	@Override public void change(IStateMachine m) {
		m.setState(new ProcessSpeedUp());
	}
}
