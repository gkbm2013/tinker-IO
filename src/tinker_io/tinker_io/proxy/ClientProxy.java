package tinker_io.proxy;

import tinker_io.TileEntity.TileEntityWhatABeautifulBlockEntity;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends ServerProxy{
	public void registerRenderThings() {
		//ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWhatABeautifulBlockEntity.class, new TileEntityWhatABeautifulBlockRenderer());
	}
}
