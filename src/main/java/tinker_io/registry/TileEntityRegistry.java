package tinker_io.registry;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityRegistry {
    public static void register(){
        GameRegistry.registerTileEntity(BlockRegistry.blockFuelInputMachine.getTileEntityClass(), BlockRegistry.blockFuelInputMachine.getRegistryName().toString());
    }
}
