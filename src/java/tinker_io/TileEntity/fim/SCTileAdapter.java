package tinker_io.TileEntity.fim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.smeltery.SmelteryTank;
import slimeknights.tconstruct.smeltery.tileentity.TileSmeltery;

public class SCTileAdapter extends TileSmeltery implements Adapter
{
	public static final String ITEM_TEMP = "itemTemperatures";
	public static final String ITEM_TEMP_REQ = "itemTempRequired";
	
	final private TileSmeltery tile;
	
	public SCTileAdapter(TileSmeltery tile)
	{
		this.tile = tile;
	}
	
	@Override public boolean isAllItemFinishHeating()
	{
		final int size = tile.getSizeInventory();
		for (int i = 0; i < size; ++i)
		{
			if (canFillIntoWhenFinishHeating(i)) return false;
		}
		return true;
	}

	private boolean canFillIntoWhenFinishHeating(int index)
	{
		if (getItemTemp(index) <= getItemTempRequire(index)) return true;
		return false;
	}
	
	@Override public boolean isStructureActive()
	{
		return tile.isActive();
	}
	
	@Override public boolean isHeatingItem()
	{
		final int[] temps = this.getItemTemps();
		for (int x: temps)
		{
			if (x > 0) return true;
		}
		return false;
	}
	
	@Override public boolean canFuelTempHeatThisItem(int index)
	{
		return tile.canHeat(index);
	}
	
	private int getItemTemp(int index)
	{
		final int[] temps =  getItemTemps();
		if (index > temps.length) return 0;
		return temps[index];
	}
	
	private int getItemTempRequire(int index)
	{
		final int[] requires = getItemTempRequires();
		if(index > requires.length) return 0;
		return requires[index];
	}
	
	private ArrayList<Integer> getItemTempList()
	{
		final int[] temps = getItemTemps();
		return new ArrayList(Arrays.asList(temps));
	}
	
	private ArrayList<Integer> getItemTempRequireList()
	{
		final int[] requires = getItemTempRequires();
		return new ArrayList(Arrays.asList(requires));
	}
	
	private int[] getItemTemps()
	{
		return getNBT().getIntArray(ITEM_TEMP);
	}
	
	private int[]getItemTempRequires()
	{
		return getNBT().getIntArray(ITEM_TEMP_REQ);
	}
	
	private NBTTagCompound getNBT()
	{
		final NBTTagCompound nbt = new NBTTagCompound();
		tile.writeToNBT(nbt);
		return nbt;
	}
	
	private SmelteryTank getLiquids()
	{
		return this.liquids;
	}

}
