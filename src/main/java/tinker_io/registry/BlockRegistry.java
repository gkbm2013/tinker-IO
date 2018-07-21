package tinker_io.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import tinker_io.block.BlockFuelInputMachine;
import tinker_io.block.BlockSmartOutput;

public class BlockRegistry {

    public static BlockFuelInputMachine fuelInputMachine = new BlockFuelInputMachine("fuel_input_machine");
    public static BlockSmartOutput smartOutput = new BlockSmartOutput("smart_output");

    public static void register(IForgeRegistry<Block> registry) {
        registry.registerAll(
                fuelInputMachine,
                smartOutput
        );
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.registerAll(
                fuelInputMachine.createItemBlock(),
                smartOutput.createItemBlock()
        );
    }

    public static void registerModels() {
        fuelInputMachine.registerItemModel(Item.getItemFromBlock(fuelInputMachine));
        smartOutput.registerItemModel(Item.getItemFromBlock(smartOutput));
    }
}
