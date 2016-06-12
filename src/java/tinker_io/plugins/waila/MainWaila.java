package tinker_io.plugins.waila;

import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import tinker_io.main.Main;


@ObjectHolder(Main.MODID)
@slimeknights.mantle.pulsar.pulse.Pulse(id = "Tinker I/O Waila Plugin", description = "The tool tips of Tinker I/O", modsRequired = "Waila", forced = true)
public class MainWaila {
	public static void startPlugin() {
		load();
	}

	private static void load() {
		System.out.println("[Tinker I/O] Tinker I/O Waila Plugin Started!");
        FMLInterModComms.sendMessage("Waila", "register", "tinker_io.plugins.waila.Registry.wailaCallback");		
	}
}
