package tinker_io.api;

public interface IStateMachine {
	void setState(IState state);
	void request();
}
