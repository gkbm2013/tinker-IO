package tinker_io.registry;

import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.shared.TinkerCommons;
import slimeknights.tconstruct.shared.block.BlockOre;

public class OreDictionaryRegistry {
	public static void register(){
		OreDictionary.registerOre("oreCobalt", TinkerCommons.blockOre.getStateFromMeta(BlockOre.OreTypes.COBALT.getMeta()).getBlock());
		OreDictionary.registerOre("oreArdite", TinkerCommons.blockOre.getStateFromMeta(BlockOre.OreTypes.ARDITE.getMeta()).getBlock());
	}
}
