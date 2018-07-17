package tinker_io.config;

import net.minecraftforge.common.config.Config;
import tinker_io.TinkerIO;

public class TinkerIOConfig {
    @Config.LangKey("tio.config.fuel_input_machine")
    @Config(modid = TinkerIO.MOD_ID, type = Config.Type.INSTANCE, name = "TinkerIO")
    public static class CONFIG_FUEL_INPUT_MACHINE {

        @Config.RequiresMcRestart
        @Config.Name("Solid Fuel Burn Time")
        @Config.Comment({"The burn time of Solid Fuel.", "Default: 40000"})
        @Config.RangeInt(min = 1)
        public static int SolidFuelBurnTime = 40000;

        @Config.RequiresMcRestart
        @Config.Name("Fuel Input Machine Fuel Consumption Rate")
        @Config.Comment({"Fuel consumption rate of Fuel Input Machine.",
                "(How long will a fuel burn)",
                "Default: 200"})
        @Config.RangeInt(min = 1)
        public static int FuelInputMachineFuelConsumptionRate = 200;

    }
}
