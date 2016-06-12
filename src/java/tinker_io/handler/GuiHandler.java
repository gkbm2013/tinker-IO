package tinker_io.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import tinker_io.TileEntity.OreCrusherTileEntity;
import tinker_io.TileEntity.SOTileEntity;
import tinker_io.TileEntity.fim.FIMTileEntity;
import tinker_io.gui.FIMGui;
import tinker_io.gui.OreCrusherGui;
import tinker_io.gui.SOGui;
import tinker_io.inventory.ContainerFIM;
import tinker_io.inventory.ContainerOreCrusher;
import tinker_io.inventory.ContainerSO;

public class GuiHandler implements IGuiHandler {
public GuiHandler (){
		
	}
	
public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
	if(ID == 0){
		FIMTileEntity tileFIM = (FIMTileEntity) world.getTileEntity(new BlockPos(x, y, z));
		return new ContainerFIM(player.inventory, tileFIM);
	}
	if(ID == 1){
		SOTileEntity tileEntitySO = (SOTileEntity) world.getTileEntity(new BlockPos(x, y, z));
		return new ContainerSO(player.inventory, tileEntitySO);
	}
	if(ID == 2){
		OreCrusherTileEntity tileEntityOreCrusher = (OreCrusherTileEntity) world.getTileEntity(new BlockPos(x, y, z));
		return new ContainerOreCrusher(player.inventory, tileEntityOreCrusher);
	}
	return null;
}

public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
	if(ID == 0){
		FIMTileEntity tileFIMContainer = (FIMTileEntity) world.getTileEntity(new BlockPos(x, y, z));
		return new FIMGui(player.inventory, tileFIMContainer);
	}
	if(ID == 1){
		SOTileEntity tileEntitySOContainer = (SOTileEntity) world.getTileEntity(new BlockPos(x, y, z));
		return new SOGui(player.inventory, tileEntitySOContainer);
	}
	if(ID == 2){
		OreCrusherTileEntity tileEntityOreCrusherContainer = (OreCrusherTileEntity) world.getTileEntity(new BlockPos(x, y, z));
		return new OreCrusherGui(player.inventory, tileEntityOreCrusherContainer);
	}
	return null;
}

}
