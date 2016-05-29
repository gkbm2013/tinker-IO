package tinker_io.handler;

import tinker_io.TileEntity.OreCrusherTileEntity;
import tinker_io.TileEntity.SOTileEntity;
import tinker_io.TileEntity.fim.FIMTileEntity;
import tinker_io.gui.FIMGui;
import tinker_io.gui.OreCrusherGui;
import tinker_io.gui.SOGui;
import tinker_io.inventory.ContainerFIM;
import tinker_io.inventory.ContainerOreCrusher;
import tinker_io.inventory.ContainerSO;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
public GuiHandler (){
		
	}

public final int GUI_ID_FuelInputMechine = 0;
public final int GUI_ID_SmartOutput = 1;
public final int GUI_ID_OreCrusher = 2;

public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
	if(ID == GUI_ID_FuelInputMechine){
		FIMTileEntity tileFIM = (FIMTileEntity) world.getTileEntity(new BlockPos(x, y, z));
		return new ContainerFIM(player.inventory, tileFIM);
	}
	if(ID == GUI_ID_SmartOutput){
		SOTileEntity tileEntitySO = (SOTileEntity) world.getTileEntity(new BlockPos(x, y, z));
		return new ContainerSO(player.inventory, tileEntitySO);
	}
	if(ID == GUI_ID_OreCrusher){
		OreCrusherTileEntity tileEntityOreCrusher = (OreCrusherTileEntity) world.getTileEntity(new BlockPos(x, y, z));
		return new ContainerOreCrusher(player.inventory, tileEntityOreCrusher);
	}
	return null;
}

public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
	if(ID == GUI_ID_FuelInputMechine){
		FIMTileEntity tileFIMContainer = (FIMTileEntity) world.getTileEntity(new BlockPos(x, y, z));
		return new FIMGui(player.inventory, tileFIMContainer);
	}
	if(ID == GUI_ID_SmartOutput){
		SOTileEntity tileEntitySOContainer = (SOTileEntity) world.getTileEntity(new BlockPos(x, y, z));
		return new SOGui(player.inventory, tileEntitySOContainer);
	}
	if(ID == GUI_ID_OreCrusher){
		OreCrusherTileEntity tileEntityOreCrusherContainer = (OreCrusherTileEntity) world.getTileEntity(new BlockPos(x, y, z));
		return new OreCrusherGui(player.inventory, tileEntityOreCrusherContainer);
	}
	return null;
}

}
