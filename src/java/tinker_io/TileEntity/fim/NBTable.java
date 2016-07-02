package tinker_io.TileEntity.fim;

import net.minecraft.nbt.NBTTagCompound;

public interface NBTable //Sorry, I have no better ideas
{
	NBTTagCompound readFromNBT(NBTTagCompound tag);
	NBTTagCompound writeToNBT(NBTTagCompound tag);
}
