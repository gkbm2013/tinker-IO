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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tinker_io.plugins.theoneprob.MainCompatHandler;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        MainCompatHandler.registerTOP();
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

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
