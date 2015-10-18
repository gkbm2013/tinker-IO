package tinker_io.plugins.ee3;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;
import tconstruct.tools.TinkerTools;
import tinker_io.main.Main;
import tinker_io.mainRegistry.BlockRegistry;
import tinker_io.mainRegistry.ItemRegistry;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(Main.MODID)
@Pulse(id = "Tinker I/O EE3 Plugin", description = "Give the exchange value for Tinker I/O", modsRequired = "EE3", forced = true)
public class EE3Main {
	@Handler
	public static void startPlugin() {
		load();
	}
	
	private static void load() {
		System.out.println("[Tinker I/O] Tinker I/O EE3 Plugin Started!");
        //FMLInterModComms.sendMessage("EE3", "register", "tinker_io.plugins.ee3.EE3Registry.TIOEnergyValueRegistry");
        EE3Registry.TIOEnergyValueRegistry();
	}
	
}
