package tinker_io.TileEntity.fim;

import net.minecraft.tileentity.TileEntityFurnace;
import tinker_io.api.IStateMachine;

public class ProcessWaitFuel implements Process
{

    @Override
    public void accept(FuelFSM m)
    {
        m.isActive = false;
        if (m.canChangeState && m.getFuelStackSize() > 0)
        {
            m.tile.fuelTemp = m.computeFuelTemp();
            m.tile.keepInputTime = m.tile.inputTime = m.computeInputTime();

            this.cousumeFuel(m);
            m.setProcess(m.speedup);
        }
    }


    private void cousumeFuel(FuelFSM m)
    {
        m.tile.getSlot(1).stackSize -= 1;
        if (m.tile.getSlot(1).stackSize == 0)
        {
            m.tile.setSlot(1, m.tile.getSlot(1).getItem().getContainerItem(m.tile.getSlot(1)));
        }
    }
}
