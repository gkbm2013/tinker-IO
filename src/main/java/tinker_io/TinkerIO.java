package tinker_io;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import slimeknights.tconstruct.tools.TinkerTools;
import tinker_io.proxy.CommonProxy;
import tinker_io.registry.*;
import tinker_io.handler.GuiHandler;

@Mod(modid = TinkerIO.MOD_ID,
        version = TinkerIO.VERSION,
        name = TinkerIO.NAME,
        dependencies = "required-after:forge@[14.23.1.2594,);"
                + "required-after:tconstruct@[1.12.2-2.10.1.87,);"
                + "after:waila;"
                + "after:jei@[4.8.5.138,)",
        acceptedMinecraftVersions = "[1.12.2,]")

public class TinkerIO {

    public static final String MOD_ID = "tinker_io";
    public static final String VERSION = "rw2.7.1";
    public static final String NAME = "Tinker I/O";

    @SidedProxy(serverSide = "tinker_io.proxy.CommonProxy", clientSide = "tinker_io.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Instance(MOD_ID)
    public static TinkerIO instance;

    //Creative Tabs
    public static CreativeTabs creativeTabs = new CreativeTabs("TinkerIO_Tabs"){
        public ItemStack getTabIconItem() {
            return new ItemStack(TinkerTools.largePlate);
        }
    };

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    	proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	proxy.postInit(event);
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }

    @EventBusSubscriber
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            ItemRegistry.register(event.getRegistry());
            BlockRegistry.registerItemBlocks(event.getRegistry());
            SmelteryStructureRegister.init();
        }

        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            BlockRegistry.register(event.getRegistry());
            TileEntityRegistry.register();
            FluidRegister.register();
        }

        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            ItemRegistry.registerModels();
            BlockRegistry.registerModels();
        }
    }

}
