package tinker_io.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tinker_io.TileEntity.OreCrusherTileEntity;
import tinker_io.TileEntity.SOTileEntity;
import tinker_io.TileEntity.StirlingEngineTileEntity;
import tinker_io.TileEntity.fim.FIMTileEntity;
import tinker_io.handler.GuiHandler;
import tinker_io.main.Main;

public class CommonProxy {
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		 return ctx.getServerHandler().playerEntity;
	}
	
	public void registerTileEntities(){
		GameRegistry.registerTileEntity(FIMTileEntity.class, Main.MODID+"TileEntityFIM");
		GameRegistry.registerTileEntity(SOTileEntity.class, Main.MODID+"SOTileEntity");
		GameRegistry.registerTileEntity(OreCrusherTileEntity.class, Main.MODID+"OreCrusherTileEntity");
		GameRegistry.registerTileEntity(StirlingEngineTileEntity.class, Main.MODID+"StirlingEngineTileEntity");
		//GameRegistry.registerTileEntity(TileEntityWhatABeautifulBlockEntity.class, Main.MODID+"WABTileEntity");
	}
	
	public void registerNetworkStuff(){
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());
		System.out.println("2016228");
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		
	}
}
