package tinker_io.config;

import org.apache.logging.log4j.Level;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import tinker_io.main.Main;

public class TIOConfig {
	public static int solidFuelBurnTime = 40000;
	public static int fuelConsumptionRateFIM = 200;
	
	public static void syncConfig(){
		Configuration config = Main.config;
		try {
			// Load config
			config.load();
			
	        // Read props from config
	        Property solidFuelBurnTimeProp = config.get(Configuration.CATEGORY_GENERAL,
	                "solidFuelBurnTime",
	                "40000",
	                "The burn time of Solid Fuel. (Type : Natural number)");
	        
	        Property fuelConsumptionRatePropFIM = config.get(Configuration.CATEGORY_GENERAL,
	                "fuelConsumedSpeedFIM",
	                "200",
	                "Fuel consumption rate of Fuel Input Machine. (Type : Natural number)");

	        solidFuelBurnTime = solidFuelBurnTimeProp.getInt();
	        fuelConsumptionRateFIM = fuelConsumptionRatePropFIM.getInt();
	        
	    } catch (Exception e) {
	    	Main.logger.log(Level.ERROR, "Config setting error! %d", e);
	    } finally {
	        if (config.hasChanged()){
	        	config.save();
	        	Main.logger.log(Level.INFO, "Config successfully reloaded!", "");
	        }
	    }
	}
}
