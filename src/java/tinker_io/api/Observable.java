package tinker_io.api;


public interface Observable<E> {
	
	void addObserver(E e);
	void removeObserver(E e);
	void notifyObservers();
}
