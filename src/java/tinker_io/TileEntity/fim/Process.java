package tinker_io.TileEntity.fim;

import java.util.function.Consumer;

public interface Process extends Consumer<FuelFSM>  {
	
	 void accept(FuelFSM m);
	 
}
