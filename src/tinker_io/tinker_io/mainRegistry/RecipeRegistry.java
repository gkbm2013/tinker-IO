package tinker_io.mainRegistry;

import java.util.ArrayList;

import tconstruct.TConstruct;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.LiquidCasting;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.tools.TinkerTools;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeRegistry {
	public static void mainRegistry() {
		registerRecipe();
	}
	static ItemStack speedUPG = new ItemStack(ItemRegistry.SpeedUPG,4);
	static ItemStack sugar = new ItemStack(Items.sugar,1);
	
	static ItemStack FIM = new ItemStack(BlockRegistry.fuelInputMachine,1);
	static ItemStack drain = new ItemStack(TinkerSmeltery.smeltery, 1, 1);
	static ItemStack hopper = new ItemStack(Blocks.hopper, 1);
	
	static ItemStack searedBrick = new ItemStack(TinkerTools.materials, 1, 2);
	static ItemStack SO = new ItemStack(BlockRegistry.smartOutput, 1);
	static ItemStack ice = new ItemStack(Blocks.ice, 1);
	
	static ItemStack UpgBase = new ItemStack(ItemRegistry.Upgrade, 1, 0);
	static ItemStack lapis = new ItemStack(Items.dye, 1, 4);
	static ItemStack paper = new ItemStack(Items.paper, 1);
	
	static ItemStack slotUPG1 = new ItemStack(ItemRegistry.Upgrade, 1, 1);
	static ItemStack chest = new ItemStack(Blocks.chest, 1);
	
	static ItemStack slotUPG2 = new ItemStack(ItemRegistry.Upgrade, 1, 2);
	static ItemStack iron_ingot = new ItemStack(Items.iron_ingot, 1);
	
	static ItemStack slotUPG3 = new ItemStack(ItemRegistry.Upgrade, 1, 3);
	static ItemStack alumite_nugget = new ItemStack(TinkerTools.materials, 1 , 32);
	
	static ItemStack slotUPG4 = new ItemStack(ItemRegistry.Upgrade, 1, 4);
	static ItemStack manyullyn_nugget = new ItemStack(TinkerTools.materials, 1 , 30);
	
	
	static ItemStack redstoneUPG = new ItemStack(ItemRegistry.Upgrade ,1 ,5);
	static ItemStack comparator = new ItemStack(Items.comparator ,1);
	static ItemStack repeater = new ItemStack(Items.repeater ,1);
	

	private static void registerRecipe() {		
		GameRegistry.addRecipe(FIM, new Object[]{"AAA", "ABA","AAA", 'A', searedBrick, 'B', hopper});
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemRegistry.SolidFuel,32), "ingotAluminum",Items.gunpowder,Items.coal));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemRegistry.SolidFuel,32), "dustAluminium",Items.gunpowder,"dustCoal"));
		GameRegistry.addRecipe(SO, new Object[]{"ABA", "B B","ABA", 'A', searedBrick, 'B', ice});
		
		GameRegistry.addRecipe(new ShapedOreRecipe(UpgBase, true, new Object[]{"AAA", "BCB","AAA", 'A', lapis, 'B', "ingotAluminum", 'C', paper}));
		GameRegistry.addRecipe(new ShapedOreRecipe(speedUPG, true, new Object[]{"ABA", "CDC","ABA", 'A', sugar, 'B', "ingotGold", 'C', "ingotTin", 'D', UpgBase}));
		GameRegistry.addRecipe(new ShapedOreRecipe(slotUPG1, true, new Object[]{"ABA", "BCB","ABA", 'A', "ingotCopper", 'B', chest, 'C', UpgBase}));
		GameRegistry.addRecipe(new ShapedOreRecipe(slotUPG2, true, new Object[]{"ABA", "BCB","ABA", 'A', "ingotAluminum", 'B', iron_ingot, 'C', slotUPG1}));
		GameRegistry.addRecipe(new ShapedOreRecipe(slotUPG3, true, new Object[]{"ABA", "BCB","ABA", 'A', alumite_nugget, 'B', "ingotGold", 'C', slotUPG2}));
		GameRegistry.addRecipe(new ShapedOreRecipe(slotUPG4, true, new Object[]{"ABA", "BCB","ABA", 'A', manyullyn_nugget, 'B', "gemDiamond", 'C', slotUPG3}));
		GameRegistry.addRecipe(new ShapedOreRecipe(redstoneUPG, true, new Object[]{"ABA", "CDC","AEA", 'A', "blockRedstone", 'B', comparator, 'C', "gemQuartz", 'D', UpgBase, 'E', repeater}));
	}

}
