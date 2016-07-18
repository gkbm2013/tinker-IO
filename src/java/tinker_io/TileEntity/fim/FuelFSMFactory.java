package tinker_io.TileEntity.fim;

public class FuelFSMFactory {
	public static FuelFSM getNewFuelFSM(FIMTileEntity tile) {
		return new FuelFSM(tile);
	}
}