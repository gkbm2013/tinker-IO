package tinker_io.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class MessageHeatSmeltery extends MessageBase<MessageHeatSmeltery> {

    private NBTTagCompound data;

    public MessageHeatSmeltery(){

    }

    public MessageHeatSmeltery(BlockPos pos, int temp){
        data = new NBTTagCompound();
        data.setInteger("temp", temp);
        data.setInteger("x", pos.getX());
        data.setInteger("y", pos.getY());
        data.setInteger("z", pos.getZ());
    }

    @Override
    public void handleClientSide(MessageHeatSmeltery message, EntityPlayer player) {

    }

    @Override
    public void handleServerSide(MessageHeatSmeltery message, EntityPlayer player) {
        // Deliberately ignored. The Fuel Input Machine now sets the smeltery temperature on the server itself
        // (TileEntityFuelInputMachine#setSmelteryTemp), so no client needs to send this, and honouring it let any
        // client set the temperature of any smeltery by position. The message stays registered so the
        // discriminators of NetworkHandler's other messages do not shift.
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        data = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, data);
    }
}
