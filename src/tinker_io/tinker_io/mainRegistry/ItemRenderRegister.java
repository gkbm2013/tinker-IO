package tinker_io.mainRegistry;

import tinker_io.main.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class ItemRenderRegister {
	
	public static String  modid = Main.MODID;
	
	public static void registerItemRenderer() {
		reg(ItemRegistry.SpeedUPG);
		reg(ItemRegistry.SolidFuel);
		reg(ItemRegistry.Lonesome_Avenue);
	}
	
	public static void reg(Item item) {
	    Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(item, 0, new ModelResourceLocation(modid + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
