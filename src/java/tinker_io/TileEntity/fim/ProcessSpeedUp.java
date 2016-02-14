package tinker_io.TileEntity.fim;

import tinker_io.api.IStateMachine;

public class ProcessSpeedUp extends Process {
	
	@Override public void accept(FuelFSM m) {
		m.heat();
		if (!m.isSpeedingUp()) {
			change(m);
		}
	}
	
	@Override public void change(IStateMachine m) {
		m.setState(new ProcessWaitFuel());
	}
}
