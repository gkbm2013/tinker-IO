package tinker_io.TileEntity.fim;

import tinker_io.api.IStateMachine;

public class ProcessWaitFuel implements Process {
	
	@Override public void accept(FuelFSM m) {
		m.isActive = false;
		if (m.canChangeState && m.getFuelStackSize() > 0)
		{
			m.tile.fuelTemp = m.tile.inputTime = m.getFuelBurnTime();
			this.cousumeFuel(m);
			m.setProcess(m.speedup);
		}
	}
	
	 private void cousumeFuel(FuelFSM m)
	 {
		m.tile.getSlots()[1].stackSize -= 1;
		if (m.tile.getSlots()[1].stackSize == 0)
		{
			m.tile.getSlots()[1] = m.tile.getSlots()[1].getItem().getContainerItem(m.tile.getSlots()[1]);
		}
	}
}
