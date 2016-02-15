package tinker_io.TileEntity.fim;

import tinker_io.api.IStateMachine;

public class ProcessSpeedUp extends Process {
	
	@Override public void accept(FuelFSM m) {
		heat(m);
		if (m.canChangeState && !m.isSpeedingUp())
		{
			change(m);
			m.canChangeState = false;
		}
	}
	
	@Override public void change(IStateMachine m) {
		m.setState(new ProcessWaitFuel());
	}
	
	 
	 private void heat(FuelFSM m)
	 {
		 m.isActive = true;
		m.tile.inputTime -= 10;
	}

}
