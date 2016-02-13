package tinker_io.TileEntity.fim;

import java.util.List;

interface Adapter
{
	boolean isHeatingItem();
	boolean canFuelTempHeatThisItem(int index);
	boolean isStructureActive();
	boolean isAllItemFinishHeating();
}
