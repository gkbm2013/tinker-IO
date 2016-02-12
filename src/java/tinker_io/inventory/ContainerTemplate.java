package tinker_io.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public abstract class ContainerTemplate extends Container {
	
	
	public void addPlayerInventorySlotToContainer(InventoryPlayer player) {
		final int column = 9;
		final int row = 3;
		
		//player's inventory
		int i;
		for(i = 0; i < row; ++i){
			for(int j = 0; j < column; ++j){
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		//player's action bar
		for(i = 0; i < column; ++i){
			this.addSlotToContainer(new Slot(player, i , 8 + i * 18 , 142));
		}
	}
	
}
