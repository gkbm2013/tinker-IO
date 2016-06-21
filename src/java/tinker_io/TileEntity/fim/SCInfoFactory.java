package tinker_io.TileEntity.fim;

public class SCInfoFactory {
	public static SCInfo getSmelyeryControllerInfo(
			FIMTileEntity tile)
	{
		return new SCInfo(tile);
	}
}
