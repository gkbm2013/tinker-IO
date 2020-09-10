package tinker_io.config;

import net.minecraftforge.common.config.Config;
import tinker_io.TinkerIO;

public class TinkerIOConfig {
    @Config.LangKey("tio.config.name")
    @Config(modid = TinkerIO.MOD_ID, type = Config.Type.INSTANCE, name = "TinkerIO")
    public static class CONFIG_TINKER_IO {

        @Config.RequiresMcRestart
        @Config.Name("Solid Fuel Burn Time")
        @Config.Comment({"The burn time of Solid Fuel.", "Default: 3200"})
        @Config.RangeInt(min = 1)
        public static int SolidFuelBurnTime = 3200;

        @Config.RequiresMcRestart
        @Config.Name("Fuel Input Machine Fuel Consumption Rate")
        @Config.Comment({"Fuel consumption rate of Fuel Input Machine.",
                "(How long will a fuel burn)",
                "Default: 200"})
        @Config.RangeInt(min = 1)
        public static int FuelInputMachineFuelConsumptionRate = 200;

        @Config.RequiresMcRestart
        @Config.Name("Smart Output Speed")
        @Config.Comment({"The speed of Smart Output.", "Default: 15"})
        @Config.RangeInt(min = 1)
        public static int SmartOutputSpeed = 15;

    }
}
