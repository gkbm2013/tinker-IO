package tinker_io.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import tinker_io.tileentity.TileEntityFuelInputMachine;
import tinker_io.tileentity.TileSmelteryAdapter;

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
        int temp = message.data.getInteger("temp");
        int x = message.data.getInteger("x");
        int y = message.data.getInteger("y");
        int z = message.data.getInteger("z");
        TileEntity te = player.world.getTileEntity(new BlockPos(x, y, z));
        if(te != null && te instanceof TileEntityFuelInputMachine){
            TileEntityFuelInputMachine tileFIM = (TileEntityFuelInputMachine) te;
            TileSmelteryAdapter adapter = new TileSmelteryAdapter(player.world, tileFIM.getMasterPosition());
            adapter.setTemp(temp);
        }
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
