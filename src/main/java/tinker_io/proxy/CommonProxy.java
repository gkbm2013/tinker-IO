package tinker_io.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import tinker_io.handler.GuiHandler;
import tinker_io.network.NetworkHandler;
import tinker_io.plugins.theoneprob.MainCompatHandler;
import tinker_io.registry.MeltingRecipeRegister;
import tinker_io.registry.OreCrusherRecipeRegister;
import tinker_io.registry.OreDictionaryRegister;
import tinker_io.registry.RecipeRegister;
import tinker_io.registry.SmartOutputRecipeReigster;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        NetworkHandler.init();
        MainCompatHandler.registerTOP();
    }

    public void init(FMLInitializationEvent event) {
        OreDictionaryRegister.init();
        RecipeRegister.register();
    }
    
    public void postInit(FMLPostInitializationEvent event) {
        OreCrusherRecipeRegister.init();
        MeltingRecipeRegister.register();
        SmartOutputRecipeReigster.register(); 	
    }
    
    public void registerItemRenderer(Item item, int meta, String id) {

    }

    @SideOnly(Side.CLIENT)
    public void registerItemColor(Item item, IItemColor iItemColor){

    }

    public <T extends TileEntity> void registerTileEntitySpecialRender(Class<T> tileEntityClass, TileEntitySpecialRenderer<? super T> specialRenderer){

    }

    public EntityPlayer getClientPlayer(){
        return null;
    }
}
