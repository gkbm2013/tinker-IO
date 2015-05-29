package tinker_io.plugins.fmp;

import tinker_io.main.Main;
import tinker_io.mainRegistry.PartRegister;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;

@ObjectHolder(Main.MODID)
@Pulse(id = "Tinker I/O Forge Mmultipart", description = "Make Tinker I/O support Forge Mmultipart", modsRequired = "ForgeMultipart", forced = true)
public class MainFmp {
	@Handler
	public static void startPlugin() {
		load();
	}
	private static void load() {
		new PartRegister().init();
		
	}
}
