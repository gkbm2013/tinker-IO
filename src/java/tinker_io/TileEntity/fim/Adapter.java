package tinker_io.TileEntity.fim;

import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface Adapter
{
	boolean isHeatingItem();
	boolean canFuelTempHeatThisItem(int index);
	boolean isStructureActive();
	boolean isAllItemFinishHeating();
	int getFuelTemp();
	
	@SideOnly(Side.CLIENT)
	void setFuelTemp(int temp);
}
