package tinker_io.TileEntity.fim;

import tinker_io.api.IState;

public abstract class Process implements IState<FuelFSM>  {
	
	 abstract public void accept(FuelFSM m);
	 
}
