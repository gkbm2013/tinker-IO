package tinker_io.registry;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityRegistry {
    public static void register(){
        GameRegistry.registerTileEntity(BlockRegistry.fuelInputMachine.getTileEntityClass(), BlockRegistry.fuelInputMachine.getRegistryName().toString());
        GameRegistry.registerTileEntity(BlockRegistry.smartOutput.getTileEntityClass(), BlockRegistry.smartOutput.getRegistryName().toString());
        GameRegistry.registerTileEntity(BlockRegistry.oreCrusher.getTileEntityClass(), BlockRegistry.oreCrusher.getRegistryName().toString());
        GameRegistry.registerTileEntity(BlockRegistry.stirlingEngine.getTileEntityClass(), BlockRegistry.stirlingEngine.getRegistryName().toString());
    }
}
