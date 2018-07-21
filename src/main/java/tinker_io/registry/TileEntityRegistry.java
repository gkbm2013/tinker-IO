package tinker_io.registry;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityRegistry {
    public static void register(){
        GameRegistry.registerTileEntity(BlockRegistry.fuelInputMachine.getTileEntityClass(), BlockRegistry.fuelInputMachine.getRegistryName().toString());
        GameRegistry.registerTileEntity(BlockRegistry.smartOutput.getTileEntityClass(), BlockRegistry.smartOutput.getRegistryName().toString());
    }
}
