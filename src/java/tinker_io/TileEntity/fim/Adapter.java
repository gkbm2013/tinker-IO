package tinker_io.TileEntity.fim;

import net.minecraft.util.BlockPos;

public interface Adapter
{
	boolean isHeatingItem();
	boolean canFuelTempHeatThisItem(int index);
	boolean isStructureActive();
	boolean isAllItemFinishHeating();
	int getFuelTemp();
	BlockPos getPos();
	
	/*@SideOnly(Side.CLIENT)
	void setFuelTemp(int temp);*/
}
