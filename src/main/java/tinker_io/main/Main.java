package tinker_io.main;

import tconstruct.plugins.mfr.TinkerMFR;
import tconstruct.plugins.te4.TinkerTE4;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.tools.TinkerTools;
import tinker_io.TileEntity.FIMTileEntity;
import tinker_io.blocks.FuelInputMachine;
import tinker_io.handler.GuiHandler;
import tinker_io.handler.SORecipes;
import tinker_io.mainRegistry.BlockRegistry;
import tinker_io.mainRegistry.ItemRegistry;
import tinker_io.mainRegistry.PartRegister;
import tinker_io.mainRegistry.RecipeRegistry;
import tinker_io.packet.PacketDispatcher;
import tinker_io.plugins.ee3.EE3Main;
import tinker_io.plugins.fmp.MainFmp;
import tinker_io.plugins.waila.MainWaila;
import tinker_io.proxy.ClientProxy;
import tinker_io.proxy.ServerProxy;
import mantle.pulsar.config.ForgeCFG;
import mantle.pulsar.control.PulseManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;


import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Main.MODID, version = Main.VERSION, name = Main.Name, dependencies="required-after:Forge@[10.13.1.1217,);required-after:TConstruct@[1.7.10-1.8.2a,);after:NotEnoughItems;after:Waila;after:ForgeMultipart;after:EE3;")


public class Main
{
    public static final String MODID = "tinker_io";
    public static final String VERSION = "beta 1.4.1";
    public static final String Name = "Tinker I/O";
    
    public static boolean iguanas_support;
    
    //Proxy
    @SidedProxy(modId=Main.MODID, clientSide="tinker_io.proxy.ClientProxy", serverSide="tinker_io.proxy.ServerProxy")
	public static ServerProxy proxy;
    
    
    //MOD
    @Instance(Main.MODID)
    public static Main instance;
    
	
    //Creative Tabs
    public static CreativeTabs TinkerIOTabs = new CreativeTabs("TinkerIO_Tabs"){
    		public Item getTabIconItem() {
    			return TinkerTools.largePlate;
    		}
        	
        };
        
     
        
    @EventHandler
    public void preLoad(FMLPreInitializationEvent event){
    	BlockRegistry.mainRegistry();
    	ItemRegistry.mainRegistry();
    	RecipeRegistry.mainRegistry();
    	
    	PacketDispatcher.registerPackets();
    	
		proxy.registerTileEntities();
		
		//MainFmp.startPlugin(); // Developing...
		MainWaila.startPlugin();
    	
    	 }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
    	/*if (Loader.isModLoaded("EE3")) {
    		EE3Main.startPlugin();
    	} */  
    	
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    	
    	config.load();
    	
    	Property iguanas_support_cofg = config.get(Configuration.CATEGORY_GENERAL, "iguanas_support", false);
    	iguanas_support = config.get(Configuration.CATEGORY_GENERAL, "iguanas_support", false).getBoolean(false);
    	
    	config.save();
    }
    
    
	@EventHandler
	public static void load(FMLInitializationEvent event){
		proxy.registerNetworkStuff();
	}
    
       
    
}
