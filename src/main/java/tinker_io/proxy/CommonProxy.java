package tinker_io.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;

public class CommonProxy {
    public void registerItemRenderer(Item item, int meta, String id) {

    }

    public void registerItemColor(Item item, IItemColor iItemColor){

    }

    public <T extends TileEntity> void registerTileEntitySpecialRender(Class<T> tileEntityClass, TileEntitySpecialRenderer<? super T> specialRenderer){

    }
}
