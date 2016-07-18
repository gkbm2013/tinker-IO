package tinker_io.api;

import java.util.function.Consumer;

public interface IState<T> extends Consumer<T> {
	void change(IStateMachine m);
}
