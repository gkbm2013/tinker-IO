package tinker_io.registry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import tinker_io.items.CrushedOre;

public class ItemColorRegister {
	
	public static void regItemColor(){
		registerItemColourHandlers(new CrushedOre(), ItemRegistry.CrushedOre);
	}
	
	private static <H extends IItemColor> void registerItemColourHandlers(final H handler, final Item item) {
		Minecraft minecraft = Minecraft.getMinecraft();
		final ItemColors itemColors = minecraft.getItemColors();
		
		itemColors.registerItemColorHandler(handler, item);
	}
}
