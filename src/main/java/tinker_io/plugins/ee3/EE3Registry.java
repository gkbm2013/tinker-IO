package tinker_io.plugins.ee3;

import net.minecraft.item.ItemStack;
import tconstruct.tools.TinkerTools;
import tinker_io.mainRegistry.BlockRegistry;
import tinker_io.mainRegistry.ItemRegistry;

//import com.pahimar.ee3.api.exchange.EnergyValueRegistryProxy;

public class EE3Registry {
	public static void TIOEnergyValueRegistry(){
		System.out.println("[Tinker I/O] Tinker I/O EE3 Plugin Started!");
		
		ItemStack UpgBase = new ItemStack(ItemRegistry.Upgrade, 1, 0);
		ItemStack slotUPG1 = new ItemStack(ItemRegistry.Upgrade, 1, 1);
		ItemStack slotUPG2 = new ItemStack(ItemRegistry.Upgrade, 1, 2);
		ItemStack slotUPG3 = new ItemStack(ItemRegistry.Upgrade, 1, 3);
		ItemStack slotUPG4 = new ItemStack(ItemRegistry.Upgrade, 1, 4);
		ItemStack redstoneUPG = new ItemStack(ItemRegistry.Upgrade ,1 ,5);
		ItemStack slotUPGInfinity = new ItemStack(ItemRegistry.Upgrade ,1 ,6);
		
		/*EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(TinkerTools.materials, 1, 15), (float) 618.663);
		
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ItemRegistry.SolidFuel), 480);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(UpgBase, 5728);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(slotUPG1, 6496);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(slotUPG2, 8544);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(slotUPG4, (float) 17011.961);
		
		
		
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(BlockRegistry.fuelInputMachine), 2448);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(BlockRegistry.smartOutput), 540);
		EnergyValueRegistryProxy.addPreAssignedEnergyValue(BlockRegistry.smartOutput, 540);*/
	}
}
