package tinker_io.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import tinker_io.block.BlockFuelInputMachine;

public class BlockRegistry {

    public static BlockFuelInputMachine blockFuelInputMachine = new BlockFuelInputMachine("fuel_input_machine");

    public static void register(IForgeRegistry<Block> registry) {
        registry.registerAll(
                blockFuelInputMachine
        );
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.registerAll(
                blockFuelInputMachine.createItemBlock()
        );
    }

    public static void registerModels() {
        blockFuelInputMachine.registerItemModel(Item.getItemFromBlock(blockFuelInputMachine));
    }
}
