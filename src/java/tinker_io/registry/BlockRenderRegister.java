package tinker_io.registry;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import tinker_io.main.Main;

public class BlockRenderRegister {
	public static String modid = Main.MODID;
	public static void registerBlockRenderer() {
		reg(BlockRegistry.whatABeautifulBlock, "what_a_beautiful_block");
		reg(BlockRegistry.fuelInputMachine);
		reg(BlockRegistry.smartOutput);
		reg(BlockRegistry.oreCrusher);
		reg(BlockRegistry.stirlingEngine);
		
		//pureOre
		//reg(FluidRegister.blockPureMetal);
	}
	
	public static void reg(Block block) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5), "inventory"));
	}
	
	public static void reg(Block block, String jsonName) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(modid + ":" + jsonName, "inventory"));
	}
}
