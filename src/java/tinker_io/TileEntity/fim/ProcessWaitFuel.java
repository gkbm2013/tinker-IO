package tinker_io.TileEntity.fim;

import tinker_io.api.IStateMachine;

public class ProcessWaitFuel extends Process {
	
	@Override public void accept(FuelFSM m) {
		m.waitFuel();
		if (m.getFuelStackSize() > 0) {
			m.cousumeFuel();
			change(m);
		}
	}
	
	@Override public void change(IStateMachine m) {
		m.setState(new ProcessSpeedUp());
	}
}
