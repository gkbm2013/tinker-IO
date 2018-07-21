package tinker_io.tileentity;

import net.minecraft.util.ITickable;

public class TileEntityOreCrusher extends TileEntityItemCapacity implements ITickable {

    private static final int SLOT_SIZE = 6;

    public TileEntityOreCrusher() {
        super(SLOT_SIZE);
    }

    @Override
    public void update() {

    }
}
