package tinker_io.item;

import net.minecraft.item.Item;
import org.lwjgl.input.Keyboard;
import tinker_io.TinkerIO;

public class ItemBase extends Item {
    protected String name;

    public ItemBase(String name) {
        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(TinkerIO.creativeTabs);
    }

    public boolean isShiftKeyDown(){
        return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
    }

    public String getName(){
        return name;
    }

    public void registerItemModel() {
        TinkerIO.proxy.registerItemRenderer(this, 0, name);
    }
}
