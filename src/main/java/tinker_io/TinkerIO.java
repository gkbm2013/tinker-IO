package tinker_io;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tinker_io.proxy.CommonProxy;

@Mod(modid = TinkerIO.MOD_ID,
        version = TinkerIO.VERSION,
        name = TinkerIO.NAME,
        dependencies = "required-after:forge@[14.23.1.2594,);"
//                + "required-after:tconstruct@[1.12.2-2.9.1.65,);"
//                + "required-after:redstoneflux@[1.12-2.0.1.2,);"
                + "after:waila;"
                + "after:jei@[4.8.5.138,)",
        acceptedMinecraftVersions = "[1.12.2,]")

public class TinkerIO {

    public static final String MOD_ID = "tinker_io";
    public static final String VERSION = "${version}";
    public static final String NAME = "Tinker I/O";

    @SidedProxy(serverSide = "tinker_io.proxy.CommonProxy", clientSide = "tinker_io.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.Instance(MOD_ID)
    public static TinkerIO instance;

    //Creative Tabs
    public static CreativeTabs creativeTabs = new CreativeTabs("TinkerIO_Tabs"){
        public ItemStack getTabIconItem() {
//            return new ItemStack(TinkerTools.largePlate);
            return new ItemStack(Items.CAKE);
        }
    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

}
