package tinker_io.mainRegistry;

import tinker_io.blocks.FuelInputMachine;
import tinker_io.main.Main;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class BlockRenderRegister {
	public static String modid = Main.MODID;
	public static void registerBlockRenderer() {
		reg(BlockRegistry.whatABeautifulBlock);
	}
	
	public static void reg(Block block) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5), "inventory"));
	}
}
