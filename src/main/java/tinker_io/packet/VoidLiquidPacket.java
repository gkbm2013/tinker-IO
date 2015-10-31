package tinker_io.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import tinker_io.TileEntity.SOTileEntity;
import tinker_io.main.Main;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class VoidLiquidPacket implements IMessage{
	
	//private int amount;
	
	private NBTTagCompound data;	
	
	public VoidLiquidPacket(){}
	
	public VoidLiquidPacket(int[] coord){
		data = new NBTTagCompound();
		data.setIntArray("Coord", coord);
	}
	
	
	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO 自動產生的方法 Stub
		//amount = buf.readInt();
		data = ByteBufUtils.readTag(buf);

	}

	@Override
	public void toBytes(ByteBuf buf) {
		// TODO 自動產生的方法 Stub
		//buf.writeInt(amount);
		 ByteBufUtils.writeTag(buf, data);
	}
	public static class Handler extends AbstractServerMessageHandler<VoidLiquidPacket> {
		boolean DEBUG = false;
		
		@Override
		public IMessage handleServerMessage(EntityPlayer player, VoidLiquidPacket message, MessageContext ctx) {
			/*MovingObjectPosition mop = Minecraft.getMinecraft().renderViewEntity.rayTrace(200, 1.0F);
			if(mop != null){
				int blockHitSide = mop.sideHit;
				int x = mop.blockX;
				int y = mop.blockY;
				int z = mop.blockZ;
				
				if(player.worldObj.isRemote == false){
					SOTileEntity tileSO = (SOTileEntity) player.worldObj.getTileEntity(x, y, z);
					if(tileSO != null){
						tileSO.voidLiquid();
						if(DEBUG==true) System.out.println("[Tinker I/O] [DEBUG] voided!");
					}else{
						System.out.println("[Tinker I/O] [Error] Failed to get the TileEntity of the block !");
					}
				}
			}else{
				System.out.println("[Tinker I/O] [Error] Failed to get the coord of the block !");
			}*/
			
			int x = message.data.getIntArray("Coord")[0];
			int y = message.data.getIntArray("Coord")[1];
			int z = message.data.getIntArray("Coord")[2];
			
			if(player.worldObj.isRemote == false){
				SOTileEntity tileSO = (SOTileEntity) player.worldObj.getTileEntity(x, y, z);
				if(tileSO != null){
					tileSO.voidLiquid();
					if(DEBUG==true) System.out.println("[Tinker I/O] [DEBUG] voided!");
				}else{
					System.out.println("[Tinker I/O] [Error] Failed to get the TileEntity of the block !");
				}
			}
			
			
			
			 return null;
		}
		
	}
}

