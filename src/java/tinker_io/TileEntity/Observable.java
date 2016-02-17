package tinker_io.TileEntity;

import tinker_io.inventory.Observer;

public interface Observable {
	
	void addObserver(Observer o);
	void removeObserver(Observer o);
	void notifyObservers();
}
