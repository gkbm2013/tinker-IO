package tinker_io.TileEntity.fim;

public class ProcessSpeedUp implements Process
{

    @Override
    public void accept(FuelFSM m)
    {
        m.heat();
        if (m.canChangeState && !m.isSpeedingUp())
        {
            m.tile.fuelTemp = m.tile.keepInputTime = 0;
            m.setProcess(m.waitFuel);
            m.canChangeState = false;
        }
    }
}