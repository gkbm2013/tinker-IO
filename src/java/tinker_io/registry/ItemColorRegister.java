package tinker_io.registry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import tinker_io.handler.color.CrushedOreItemColorHandler;

public class ItemColorRegister {
	
	public static void regItemColor(){
		Minecraft minecraft = Minecraft.getMinecraft();
		final ItemColors itemColors = minecraft.getItemColors();
		registerItemColourHandlers(itemColors);
	}
	
	private static void registerItemColourHandlers(final ItemColors itemColors) {
		final IItemColor itemColourHandler = new CrushedOreItemColorHandler();
		itemColors.registerItemColorHandler(itemColourHandler, ItemRegistry.CrushedOre);
	}
}
