package tinker_io.packet;

import cofh.api.energy.EnergyStorage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tinker_io.TileEntity.SOTileEntity;

public class EnergySynchronizationPacket implements IMessage{

	private NBTTagCompound data;
	
	private EnergyStorage storage;
	
	public EnergySynchronizationPacket(){}
	
	public EnergySynchronizationPacket(EnergyStorage storage, int maxExtract, boolean simulate){
		data = new NBTTagCompound();
		this.storage = storage;
		this.data.setInteger("maxExtract", maxExtract);
		this.data.setBoolean("simulate", simulate);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		data = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, data);
		
	}
	public static class Handler extends AbstractServerMessageHandler<EnergySynchronizationPacket> {
		boolean DEBUG = false;
		
		@Override
		public IMessage handleServerMessage(EntityPlayer player, EnergySynchronizationPacket message, MessageContext ctx) {
			
			if(player.worldObj.isRemote == false){
				message.storage.extractEnergy(message.data.getInteger("maxExtract"), message.data.getBoolean("simulate"));
			}
			 return null;
		}
		
	}
}
