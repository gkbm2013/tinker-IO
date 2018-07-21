package tinker_io.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import tinker_io.block.BlockFuelInputMachine;
import tinker_io.block.BlockOreCrusher;
import tinker_io.block.BlockSmartOutput;
import tinker_io.block.BlockWhatABeautiful;

public class BlockRegistry {

    public static BlockFuelInputMachine fuelInputMachine = new BlockFuelInputMachine("fuel_input_machine");
    public static BlockSmartOutput smartOutput = new BlockSmartOutput("smart_output");
    public static BlockWhatABeautiful whatABeautifulBlock = new BlockWhatABeautiful("what_a_beautiful_block");
    //TODO Change the name of ore crusher
    public static BlockOreCrusher oreCrusher = new BlockOreCrusher("Ore_Crusher");

    public static void register(IForgeRegistry<Block> registry) {
        registry.registerAll(
                fuelInputMachine,
                smartOutput,
                whatABeautifulBlock,
                oreCrusher
        );
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.registerAll(
                fuelInputMachine.createItemBlock(),
                smartOutput.createItemBlock(),
                whatABeautifulBlock.createItemBlock(),
                oreCrusher.createItemBlock()
        );
    }

    public static void registerModels() {
        fuelInputMachine.registerItemModel(Item.getItemFromBlock(fuelInputMachine));
        smartOutput.registerItemModel(Item.getItemFromBlock(smartOutput));
        whatABeautifulBlock.registerItemModel(Item.getItemFromBlock(whatABeautifulBlock));
        oreCrusher.registerItemModel(Item.getItemFromBlock(oreCrusher));
    }
}
