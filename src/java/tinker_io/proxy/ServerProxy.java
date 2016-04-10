package tinker_io.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tinker_io.TileEntity.OreCrusherTileEntity;
//import tconstruct.util.config.PHConstruct;
import tinker_io.TileEntity.SOTileEntity;
import tinker_io.TileEntity.StirlingEngineTileEntity;
import tinker_io.TileEntity.fim.FIMTileEntity;
import tinker_io.handler.GuiHandler;
import tinker_io.main.Main;

public class ServerProxy extends CommonProxy{
	
	@Override
	public void preInit(FMLPreInitializationEvent e) {
	}
	@Override
	public void init(FMLInitializationEvent e) {
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
	}
}
