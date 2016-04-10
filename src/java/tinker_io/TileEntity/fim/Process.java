package tinker_io.TileEntity.fim;

import java.util.function.Consumer;

import tinker_io.api.IState;

public interface Process extends Consumer<FuelFSM>  {
	
	 void accept(FuelFSM m);
	 
}
