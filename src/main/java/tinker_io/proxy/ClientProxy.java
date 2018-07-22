package tinker_io.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import tinker_io.TinkerIO;

public class ClientProxy extends CommonProxy {
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
}
