package tinker_io.TileEntity.fim;

import net.minecraft.nbt.NBTTagCompound;

public interface NBTable //Sorry, I have no better ideas
{
	void readFromNBT(NBTTagCompound tag);
	void writeToNBT(NBTTagCompound tag);
}
