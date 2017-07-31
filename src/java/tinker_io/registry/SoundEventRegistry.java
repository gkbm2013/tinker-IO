package tinker_io.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import tinker_io.main.Main;

public class SoundEventRegistry {
	public static SoundEvent Lonesome_Avenue;
	
	public static void registerSounds() {
		Lonesome_Avenue = registerSound("record.Lonesome_Avenue");
	}
	
	private static SoundEvent registerSound(String soundName) {
		final ResourceLocation soundID = new ResourceLocation(Main.MODID, soundName);
		SoundEvent event = new SoundEvent(soundID).setRegistryName(soundID);
		ForgeRegistries.SOUND_EVENTS.register(event);
		return event;
		//return GameRegistry.register(new SoundEvent(soundID).setRegistryName(soundID));
	}
}
