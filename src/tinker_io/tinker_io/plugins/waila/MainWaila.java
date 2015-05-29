package tinker_io.plugins.waila;

import tinker_io.main.Main;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(Main.MODID)
@Pulse(id = "Tinker I/O Waila Plugin", description = "The tool tips of Tinker I/O", modsRequired = "Waila", forced = true)
public class MainWaila {
	@Handler
	public static void startPlugin() {
		load();
	}

	private static void load() {
		System.out.println("[Tinker I/O] Tinker I/O Waila Plugin Started!");
        FMLInterModComms.sendMessage("Waila", "register", "tinker_io.plugins.waila.Registry.wailaCallback");		
	}
}
