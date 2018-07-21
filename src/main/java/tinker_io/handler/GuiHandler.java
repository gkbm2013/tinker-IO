package tinker_io.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import tinker_io.gui.GuiFuelInputMachine;
import tinker_io.gui.GuiOreCrusher;
import tinker_io.gui.GuiSmartOutput;
import tinker_io.inventory.ContainerFuelInputMachine;
import tinker_io.inventory.ContainerOreCrusher;
import tinker_io.inventory.ContainerSmartOutput;
import tinker_io.tileentity.TileEntityFuelInputMachine;
import tinker_io.tileentity.TileEntityOreCrusher;
import tinker_io.tileentity.TileEntitySmartOutput;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    public static final int FUEL_INPUT_MACHINE = 0, SMART_OUTPUT = 1, ORE_CRUSHER = 2;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case FUEL_INPUT_MACHINE:
                return new ContainerFuelInputMachine(player.inventory, (TileEntityFuelInputMachine) world.getTileEntity(new BlockPos(x, y, z)));
            case SMART_OUTPUT:
                return new ContainerSmartOutput(player.inventory, (TileEntitySmartOutput) world.getTileEntity(new BlockPos(x, y, z)));
            case ORE_CRUSHER:
                return new ContainerOreCrusher(player.inventory, (TileEntityOreCrusher) world.getTileEntity(new BlockPos(x, y, z)));
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
            case SMART_OUTPUT:
                return new GuiSmartOutput((Container) getServerGuiElement(ID, player, world, x, y, z), player.inventory);
            case ORE_CRUSHER:
                return new GuiOreCrusher((Container) getServerGuiElement(ID, player, world, x, y, z), player.inventory);
            default:
                return null;
        }
    }
}
