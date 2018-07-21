package tinker_io.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import tinker_io.block.base.BlockBase;

public class BlockWhatABeautifal extends BlockBase {
    public BlockWhatABeautifal(String name) {
        super(Material.GLASS, name);
        setHardness(3F);
        setResistance(20F);
        setSoundType(SoundType.GLASS);
        setLightLevel(1.0F);
    }
}
