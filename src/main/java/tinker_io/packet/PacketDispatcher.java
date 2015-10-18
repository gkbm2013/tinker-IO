package tinker_io.packet;

import net.minecraft.entity.player.EntityPlayerMP;
import tinker_io.main.Main;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

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
	
	/**
	 * Send this message to the specified player.
	 * See {@link SimpleNetworkWrapper#sendTo(IMessage, EntityPlayerMP)}
	 */
	 public static final void sendTo(IMessage message, EntityPlayerMP player) {
	 PacketDispatcher.dispatcher.sendTo(message, player);
	 }
	 
	 /**
	  * Send this message to the server.
	  * See {@link SimpleNetworkWrapper#sendToServer(IMessage)}
	  */
	  public static final void sendToServer(IMessage message) {
	  PacketDispatcher.dispatcher.sendToServer(message);
	  }
	
}
