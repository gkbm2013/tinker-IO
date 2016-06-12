package tinker_io.api;

import java.util.function.Consumer;

import tinker_io.TileEntity.fim.FuelFSM;

public interface IState<T> extends Consumer<T> {
	void change(IStateMachine m);
}
