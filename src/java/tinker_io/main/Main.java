package tinker_io.main;

import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.TinkerTools;
import tinker_io.packet.PacketDispatcher;
import tinker_io.plugins.waila.MainWaila;
import tinker_io.proxy.CommonProxy;
import tinker_io.registry.BlockRegistry;
import tinker_io.registry.FluidRegister;
import tinker_io.registry.ItemRegistry;
import tinker_io.registry.OreCrusherBanLiatRegistry;
import tinker_io.registry.SmelteryRecipeRegistry;
import tinker_io.registry.SoundEventRegistry;
import tinker_io.registry.RecipeRegistry;

import com.google.common.collect.ImmutableSet;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
		modid = Main.MODID,
		version = Main.VERSION,
		name = Main.Name,
		dependencies="required-after:Forge@[12.18.1.2024,);"
				+ "required-after:tconstruct@[1.10.2-2.4.0,);"
				+ "after:JEI;"
				+ "after:Waila;",
		acceptedMinecraftVersions = "[1.10.2,]")
public class Main
{
    public static final String MODID = "tinker_io";
    public static final String VERSION = "beta 2.3.5";
    public static final String Name = "Tinker I/O";
    
    //public static boolean iguanas_support;
    
    //Proxy
    @SidedProxy(modId=Main.MODID, clientSide="tinker_io.proxy.ClientProxy", serverSide="tinker_io.proxy.ServerProxy")
	public static CommonProxy proxy;
    
    //MOD
    @Instance(Main.MODID)
    public static Main instance;
    
	
    //Creative Tabs
    public static CreativeTabs TinkerIOTabs = new CreativeTabs("TinkerIO_Tabs"){
    		public Item getTabIconItem() {
    			return TinkerTools.largePlate;
    		}
        };
        
    /**
     * read your config file, create Blocks, Items, etc. and register them with the GameRegistry.
     * @param event
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	proxy.preInit(event);
		/*
		 * iguanas_support_cofg and Smart Output Eliminate List list will be removed to come.
		 * 
		 * Configuration config = new Configuration(event.getSuggestedConfigurationFile());
	  	
    	config.load();
    	
    	Property iguanas_support_cofg = config.get(Configuration.CATEGORY_GENERAL, "iguanas_support", true);
    	iguanas_support = config.get(Configuration.CATEGORY_GENERAL, "iguanas_support", true).getBoolean(true);
    	
    	config.save();
    	
    	if (!Loader.isModLoaded("IguanaTweaksTConstruct")) {
    		iguanas_support = false;
    	}*/
    	
		//magma heater
		FluidRegister.mainRegistry();
    	BlockRegistry.mainRegistry();
    	ItemRegistry.mainRegistry();
    	SoundEventRegistry.registerSounds();

    	PacketDispatcher.registerPackets();
    	
    	
		proxy.registerTileEntities();
		//These was moved to ClientProxy
		//proxy.registerRenderThings();
		
		MainWaila.startPlugin();
    }
    
    /**
     * build up data structures, add Crafting Recipes and register new handler
     * @param event
     */
	@Mod.EventHandler
	public static void init(FMLInitializationEvent event)
	{
		proxy.init(event);
		//Register model in Client Proxy instead of here!
    	RecipeRegistry.mainRegistry();
    	OreCrusherBanLiatRegistry.registry();
	}
    
	/**
	 * communicate with other mods and adjust your setup based on this
	 * @param event
	 */
	  @Mod.EventHandler
	  public void postInit(FMLPostInitializationEvent event)
	  {
		  proxy.postInit(event);
		  proxy.registerNetworkStuff();
		  SmelteryRecipeRegistry.registerMeltingCasting();
		  
		  ImmutableSet.Builder<Block> builder = ImmutableSet.builder();
          for(Block block : TinkerSmeltery.validSmelteryBlocks)
          {
              builder.add(block);
          }
          builder.add(BlockRegistry.fuelInputMachine);
          TinkerSmeltery.validSmelteryBlocks = builder.build();
	  }
}
