package tinker_io.registry;

import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.smeltery.block.BlockCasting;
import slimeknights.tconstruct.tools.TinkerTools;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import tinker_io.TinkerIO;

public class RecipeRegister {

    //TODO: use json file instead...
    public static void register() {
        registerRecipe();
    }
    static ItemStack speedUPG = new ItemStack(ItemRegistry.speedUpgrade,4);
    static ItemStack sugar = new ItemStack(Items.SUGAR,1);

    static ItemStack FIM = new ItemStack(BlockRegistry.fuelInputMachine,1);
    static ItemStack drain = new ItemStack(TinkerSmeltery.smelteryIO, 1, 1);
    static ItemStack hopper = new ItemStack(Blocks.HOPPER, 1);

    static ItemStack searedBrick = new ItemStack(TinkerSmeltery.searedBlock, 1, 0);
    static ItemStack SO = new ItemStack(BlockRegistry.smartOutput, 1);
    static ItemStack ice = new ItemStack(Blocks.ICE, 1);

    static ItemStack UpgBase = new ItemStack(ItemRegistry.upgrade, 1, 0);
    static ItemStack lapis = new ItemStack(Items.DYE, 1, 4);
    static ItemStack paper = new ItemStack(Items.PAPER, 1);

    static ItemStack slotUPG1 = new ItemStack(ItemRegistry.upgrade, 1, 1);
    static ItemStack chest = new ItemStack(Blocks.CHEST, 1);

    static ItemStack slotUPG2 = new ItemStack(ItemRegistry.upgrade, 1, 2);
    static ItemStack iron_ingot = new ItemStack(Items.IRON_INGOT, 1);

    static ItemStack slotUPG3 = new ItemStack(ItemRegistry.upgrade, 1, 3);
    //static ItemStack alumite_nugget = new ItemStack(TinkerSmeltery.searedBlock, 1 , 32);
    //static ItemStack knightslime_nugget = new ItemStack(TinkerSmeltery.searedBlock, 1 , 32);

    static ItemStack slotUPG4 = new ItemStack(ItemRegistry.upgrade, 1, 4);
    //static ItemStack manyullyn_nugget = new ItemStack(TinkerSmeltery.searedBlock, 1 , 30);


    static ItemStack redstoneUPG = new ItemStack(ItemRegistry.upgrade ,1 ,5);
    static ItemStack comparator = new ItemStack(Items.COMPARATOR ,1);
    static ItemStack repeater = new ItemStack(Items.REPEATER ,1);

    static ItemStack whatABeautifulBlock = new ItemStack(BlockRegistry.whatABeautifulBlock ,1);
    static ItemStack glowstone = new ItemStack(Blocks.GLOWSTONE ,1);
    static ItemStack nether_star = new ItemStack(Items.NETHER_STAR ,1);

    static ItemStack slotUPGInfinity = new ItemStack(ItemRegistry.upgrade ,1 ,6);
    static ItemStack obsidian = new ItemStack(Blocks.OBSIDIAN ,1);

    static ItemStack stirlingEngine = new ItemStack(BlockRegistry.stirlingEngine ,1);
    static ItemStack searedBlock = new ItemStack(TinkerSmeltery.searedBlock, 1);

    static ItemStack oreCrusher = new ItemStack(BlockRegistry.oreCrusher ,1);
    static ItemStack redstone_lamp = new ItemStack(Blocks.REDSTONE_LAMP ,1);

    static ItemStack basinUPG = new ItemStack(ItemRegistry.upgrade ,1 ,7);
    static ItemStack castingBasin = new ItemStack(TinkerSmeltery.castingBlock, 1, BlockCasting.CastingType.BASIN.getMeta());

    private static void registerRecipe() {

        ItemStack manyullyn_hammer_head = new ItemStack(TinkerTools.hammerHead);
        NBTTagCompound nbt = new NBTTagCompound();
        manyullyn_hammer_head.setTagCompound(nbt);
        manyullyn_hammer_head.getTagCompound().setString("Material", "manyullyn");

        String ingotTinOrIron = "ingotTin";
        String ingotAluminumOrLeadOrGold = "ingotAluminum";
        String ingotLeadOrIron = "ingotLead";
        String ingotCopperOrIron = "ingotCopper";

        if(OreDictionary.getOres("ingotTin").isEmpty()){
            ingotTinOrIron = "ingotIron";
        }

        if(OreDictionary.getOres("ingotAluminum").isEmpty()){
            if(!OreDictionary.getOres("ingotLead").isEmpty()){
                ingotAluminumOrLeadOrGold = "ingotLead";
            }else{
                ingotAluminumOrLeadOrGold = "ingotGold";
            }
        }

        if(OreDictionary.getOres("ingotLead").isEmpty()){
            ingotLeadOrIron = "ingotIron";
        }

        if(OreDictionary.getOres("ingotCopper").isEmpty()){
            ingotCopperOrIron = "ingotIron";
        }

        ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(TinkerIO.MOD_ID), oreCrusher, true, new Object[]{"ABA", "CDC", "DED", 'A', searedBlock, 'B', ingotTinOrIron, 'C', redstone_lamp, 'D', manyullyn_hammer_head, 'E', "blockDiamond"}).setRegistryName("oreCrusher_0"));

        ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(TinkerIO.MOD_ID), oreCrusher, true, new Object[]{"ABA", "CDC", "DED", 'A', searedBlock, 'B', ingotTinOrIron, 'C', redstone_lamp, 'D', manyullyn_hammer_head, 'E', "blockDiamond"}).setRegistryName("oreCrusher_1"));

        ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(TinkerIO.MOD_ID), stirlingEngine, true, new Object[]{"FAF", "BCB", "DED", 'A', "blockGold", 'B', "blockQuartz", 'C', "stickWood", 'D', searedBlock, 'E', "blockGlass", 'F', ingotTinOrIron}).setRegistryName("stirlingEngine"));

        ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(TinkerIO.MOD_ID), FIM, new Object[]{"AAA", "ABA","AAA", 'A', searedBrick, 'B', hopper}).setRegistryName("RIM"));
        ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(TinkerIO.MOD_ID), new ItemStack(ItemRegistry.solidFuel,8), ingotAluminumOrLeadOrGold,Items.GUNPOWDER,Items.COAL).setRegistryName("SolidFuel_0"));
        ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(TinkerIO.MOD_ID), new ItemStack(ItemRegistry.solidFuel,8), "dustAluminium",Items.GUNPOWDER,"dustCoal").setRegistryName("SolidFuel_1"));

        ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(TinkerIO.MOD_ID), SO, new Object[]{"ABA", "B B","ABA", 'A', searedBrick, 'B', ice}).setRegistryName("SO"));
        ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(TinkerIO.MOD_ID), whatABeautifulBlock, true, new Object[]{"AAA", "ABA","AAA", 'A', glowstone, 'B', nether_star}).setRegistryName("whatABeautifulBlock"));

        ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(TinkerIO.MOD_ID), new ItemStack(ItemRegistry.cdLonesomeAvenue,1), BlockRegistry.smartOutput, BlockRegistry.fuelInputMachine, ItemRegistry.solidFuel).setRegistryName("Lonesome_Avenue"));

        ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(TinkerIO.MOD_ID), UpgBase, true, new Object[]{"AAA", "BCB","AAA", 'A', lapis, 'B', ingotAluminumOrLeadOrGold, 'C', paper}).setRegistryName("UpgBase_0"));
        ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(TinkerIO.MOD_ID), UpgBase, true, new Object[]{"AAA", "BCB","AAA", 'A', lapis, 'B', ingotLeadOrIron, 'C', paper}).setRegistryName("UpgBase_1"));

        ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(TinkerIO.MOD_ID), speedUPG, true, new Object[]{"ABA", "CDC","ABA", 'A', sugar, 'B', "ingotGold", 'C', ingotTinOrIron, 'D', UpgBase}).setRegistryName("speedUPG"));
        ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(TinkerIO.MOD_ID), slotUPG1, true, new Object[]{"ABA", "BCB","ABA", 'A', ingotCopperOrIron, 'B', chest, 'C', UpgBase}).setRegistryName("slotUPG1"));

        ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(TinkerIO.MOD_ID), slotUPG2, true, new Object[]{"ABA", "BCB","ABA", 'A', ingotAluminumOrLeadOrGold, 'B', iron_ingot, 'C', slotUPG1}).setRegistryName("slotUPG2"));

        ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(TinkerIO.MOD_ID), slotUPG3, true, new Object[]{"ABA", "BCB","ABA", 'A', "nuggetKnightslime", 'B', "ingotGold", 'C', slotUPG2}).setRegistryName("slotUPG3"));
        ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(TinkerIO.MOD_ID), slotUPG4, true, new Object[]{"ABA", "BCB","ABA", 'A', "nuggetManyullyn", 'B', "gemDiamond", 'C', slotUPG3}).setRegistryName("slotUPG4"));
        ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(TinkerIO.MOD_ID), slotUPGInfinity, true, new Object[]{"ABA", "ACA","ABA", 'A', obsidian, 'B', whatABeautifulBlock, 'C', slotUPG4}).setRegistryName("slotUPGInfinity"));
        ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(TinkerIO.MOD_ID), redstoneUPG, true, new Object[]{"ABA", "CDC","AEA", 'A', "blockRedstone", 'B', comparator, 'C', "gemQuartz", 'D', UpgBase, 'E', repeater}).setRegistryName("redstoneUPG"));
        ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(TinkerIO.MOD_ID), basinUPG, true, new Object[]{"ABA", "BCB","ABA", 'A', obsidian, 'B', castingBasin, 'C', slotUPG4}).setRegistryName("basinUPG"));
    }

}
