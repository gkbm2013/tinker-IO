package tinker_io.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import tinker_io.main.Main;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * These files are reference from
 * http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/2137055-1-7-x-1-8-customizing-packet-handling-with
 * @author coolAlias
 *
 */
public class PacketDispatcher {
	
	private static byte packetId = 0;
	private static final SimpleNetworkWrapper dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(Main.MODID);
	
	public static final void registerPackets() {
		 // Using an incrementing field instead of hard-coded numerals, I don't need to think
		 // about what number comes next or if I missed on should I ever rearrange the order
		 // of registration (for instance, if you wanted to alphabetize them... yeah...)
		 // It's even easier if you create a convenient 'registerMessage' method:
		 
		//PacketDispatcher.registerMessage(OpenGuiMessage.OpenGuiMessageHandler.class, OpenGuiMessage.class, Side.SERVER);
		 //PacketDispatcher.registerMessage(SyncPlayerPropsMessage.SyncPlayerPropsMessageHandler.class, SyncPlayerPropsMessage.class, Side.CLIENT);
		 
		 // If you don't want to make a 'registerMessage' method, you can do it directly:
		 //PacketDispatcher.dispatcher.registerMessage(OpenGuiMessage.OpenGuiMessageHandler.class, OpenGuiMessage.class, packetId++, Side.SERVER);
		 //PacketDispatcher.dispatcher.registerMessage(SyncPlayerPropsMessage.SyncPlayerPropsMessageHandler.class, SyncPlayerPropsMessage.class, packetId++, Side.CLIENT);
		
		//PacketDispatcher.registerMessage(OpenGuiMessage.OpenGuiMessageHandler.class, OpenGuiMessage.class, Side.SERVER);
		PacketDispatcher.registerMessage(VoidLiquidPacket.Handler.class, VoidLiquidPacket.class, Side.SERVER);
	}
	
	private static final void registerMessage(Class handlerClass, Class messageClass, Side side) {
		PacketDispatcher.dispatcher.registerMessage(handlerClass, messageClass, packetId++, side);
	}
	
	 //========================================================//
	 // The following methods are the 'wrapper' methods; again,
	 // this just makes sending a message slightly more compact
	 // and is purely a matter of stylistic preference
	 //========================================================//
	
	/**
	 * Send this message to the specified player.
	 * See {@link SimpleNetworkWrapper#sendTo(IMessage, EntityPlayerMP)}
	 */
	 public static final void sendTo(IMessage message, EntityPlayerMP player) {
	 PacketDispatcher.dispatcher.sendTo(message, player);
	 }

	 /**
	 * Send this message to everyone within a certain range of a point.
	 * See {@link SimpleNetworkWrapper#sendToDimension(IMessage, NetworkRegistry.TargetPoint)}
	 */
	 public static final void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
	 PacketDispatcher.dispatcher.sendToAllAround(message, point);
	 }

	 /**
	 * Sends a message to everyone within a certain range of the coordinates in the same dimension.
	 */
	 public static final void sendToAllAround(IMessage message, int dimension, double x, double y, double z, 

	double range) {
	 PacketDispatcher.sendToAllAround(message, new NetworkRegistry.TargetPoint(dimension, x, y, z, 

	range));
	 }

	 /**
	 * Sends a message to everyone within a certain range of the player provided.
	 */
	 public static final void sendToAllAround(IMessage message, EntityPlayer player, double range) {
	 PacketDispatcher.sendToAllAround(message, player.worldObj.provider.getDimensionId(), player.posX, player.posY, player.posZ, range);
	 }

	 /**
	 * Send this message to everyone within the supplied dimension.
	 * See {@link SimpleNetworkWrapper#sendToDimension(IMessage, int)}
	 */
	 public static final void sendToDimension(IMessage message, int dimensionId) {
	 PacketDispatcher.dispatcher.sendToDimension(message, dimensionId);
	 }

	 /**
	 * Send this message to the server.
	 * See {@link SimpleNetworkWrapper#sendToServer(IMessage)}
	 */
	 public static final void sendToServer(IMessage message) {
	 PacketDispatcher.dispatcher.sendToServer(message);
	 }
	
}
