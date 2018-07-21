package tinker_io.tileentity;

import net.minecraft.util.ITickable;

public class TileEntitySmartOutput extends TileEntityItemCapacity implements ITickable {

    private static final int SLOTS_SIZE = 4;

    public TileEntitySmartOutput() {
        super(SLOTS_SIZE);
    }

    @Override
    public void update() {

    }
}
