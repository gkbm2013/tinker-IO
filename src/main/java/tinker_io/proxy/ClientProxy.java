package tinker_io.proxy;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import tinker_io.TinkerIO;
import tinker_io.handler.GuiHandler;
import tinker_io.registry.ItemRegistry;

@EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	
    @Override
    public void preInit(FMLPreInitializationEvent event) {
    	
    }
	
    @Override
    public void init(FMLInitializationEvent event) {
    	
    }
    
    @Override    
    public void postInit(FMLPostInitializationEvent event) {
        ItemRegistry.registerItemColors();
    }
    
    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(TinkerIO.MOD_ID + ":" + id, "inventory"));
    }

    @Override
    public void registerItemColor(Item item, IItemColor iItemColor) {
        Minecraft minecraft = Minecraft.getMinecraft();
        final ItemColors itemColors = minecraft.getItemColors();
        itemColors.registerItemColorHandler(iItemColor, item);
    }

    @Override
    public <T extends TileEntity> void registerTileEntitySpecialRender(Class<T> tileEntityClass, TileEntitySpecialRenderer<? super T> specialRenderer) {
        ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, specialRenderer);
    }

    @Override
    public EntityPlayer getClientPlayer(){
        return Minecraft.getMinecraft().player;
    }
}
