package tinker_io.registry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import tinker_io.gui.GuiFuelInputMachine;
import tinker_io.inventory.ContainerFuelInputMachine;
import tinker_io.tileentity.TileEntityFuelInputMachine;

import javax.annotation.Nullable;

public class GuiRegistry implements IGuiHandler {

    public static final int FUEL_INPUT_MACHINE = 0;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case FUEL_INPUT_MACHINE:
                return new ContainerFuelInputMachine(player.inventory, (TileEntityFuelInputMachine) world.getTileEntity(new BlockPos(x, y, z)));
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case FUEL_INPUT_MACHINE:
                return new GuiFuelInputMachine((Container) getServerGuiElement(ID, player, world, x, y, z), player.inventory);
            default:
                return null;
        }
    }
}
