package tinker_io.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.smeltery.tileentity.TileSmeltery;
import tinker_io.inventory.ContainerFuelInputMachine;
import tinker_io.network.MessageHeatSmeltery;
import tinker_io.network.NetworkHandler;

import javax.annotation.Nonnull;

public class TileEntityFuelInputMachine extends TileEntitySmelteryItemCapacity implements ITickable {

    private static final int SLOTS_SIZE = 2;
    private int tick = 0;

    public TileEntityFuelInputMachine() {
        super(SLOTS_SIZE);
    }

    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (slot == ContainerFuelInputMachine.FUEL) {
            return inventory.insertItem(slot, stack, simulate);
        }
        return stack;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
//            if (slot == ContainerFuelInputMachine.PRODUCT) {
//                return inventory.extractItem(slot, amount, simulate);
//            }
        return ItemStack.EMPTY;
    }

    @Override
    public void update() {
        if(!getWorld().isRemote || getMasterPosition() == null) return;

        if(tick % 4 < 100) {
            NetworkHandler.sendToServer(new MessageHeatSmeltery(getPos(), 125000));
        }

        tick = (tick + 1) % 20;
    }

    public void resetTemp(){
        NetworkHandler.sendToServer(new MessageHeatSmeltery(getPos(), getFuelTemp()));
    }

    private TileSmeltery getMasterTile() {
        TileSmeltery tileSmeltery = null;
        BlockPos masterPos = getMasterPosition();
        World world = getWorld();
        if (getHasMaster() && masterPos != null) {
            //TODO
            tileSmeltery = (TileSmeltery) world.getTileEntity(masterPos);
        }
        return tileSmeltery;
    }

    private int getFuelTemp(){
        FluidStack fluidStack = getMasterTile().getTank().getFluid();
        if(fluidStack != null){
            return fluidStack.getFluid().getTemperature();
        }else{
            return 300;
        }
    }
}
