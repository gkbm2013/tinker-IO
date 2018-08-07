package tinker_io.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import tinker_io.TinkerIO;

public class NetworkHandler {

    /* Tutorial
     * https://github.com/MineMaarten/AdvancedMod/blob/master/src/main/java/com/minemaarten/advancedmod/network/NetworkHandler.java
     * */

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(TinkerIO.MOD_ID);
    public static int discriminator = 0;

    public static void init() {
        INSTANCE.registerMessage(MessageEmptyTank.class, MessageEmptyTank.class, discriminator++, Side.SERVER);
        INSTANCE.registerMessage(MessageHeatSmeltery.class, MessageHeatSmeltery.class, discriminator++, Side.SERVER);
    }

    public static void sendToServer(IMessage message){
        INSTANCE.sendToServer(message);
    }

    public static void sendTo(IMessage message, EntityPlayerMP player){
        INSTANCE.sendTo(message, player);
    }

    public static void sendToAllAround(IMessage message, TargetPoint point){
        INSTANCE.sendToAllAround(message, point);
    }


    public static void sendToAll(IMessage message){
        INSTANCE.sendToAll(message);
    }

    public static void sendToDimension(IMessage message, int dimensionId){
        INSTANCE.sendToDimension(message, dimensionId);
    }
}
