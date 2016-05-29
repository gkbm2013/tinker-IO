package tinker_io.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.smeltery.tileentity.TileHeatingStructure;
import slimeknights.tconstruct.smeltery.tileentity.TileSmeltery;

public class TempField{
	private World world;
	private BlockPos scPos;
	
	public  TempField(World world, BlockPos scPos){
		this.world = world;
		this.scPos = scPos;
	}
	
	public void setTemp(int temp){
		if(world != null && scPos != null){
			TileEntity te = world.getTileEntity(scPos);
			if(te instanceof TileSmeltery){
				TileSmeltery tileSC = (TileSmeltery) te;
				
				try {
					@SuppressWarnings("rawtypes")
					Class[] oParam = new Class[2];
						oParam[0] = Integer.TYPE;
						oParam[1] = Integer.TYPE;
						
					Object[] mParam = {new Integer(0), new Integer(temp)};
			        	
					Method addFuelMethod = TileHeatingStructure.class.getDeclaredMethod("addFuel", oParam);
					addFuelMethod.setAccessible(true);
					addFuelMethod.invoke(tileSC, mParam);
				} catch (NoSuchMethodException e) {
					// TODO
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO
					e.printStackTrace();
				}
				
				/*try {
					Field tempFieldx = TileHeatingStructure.class.getDeclaredField("temperature");
					tempFieldx.setAccessible(true);
					int c = (int)tempFieldx.getInt(tileSC);
					System.out.println(c);
					//fieldValue = temp;
					//System.out.println(fieldValue);
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}*/
			}
		}
	}
}
