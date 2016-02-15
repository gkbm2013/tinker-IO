package tinker_io.TileEntity.fim;

import java.util.List;

public interface Adapter
{
	boolean isHeatingItem();
	boolean canFuelTempHeatThisItem(int index);
	boolean isStructureActive();
	boolean isAllItemFinishHeating();
	int getFuelTemp();
	void setFuelTemp(int temp);
}
