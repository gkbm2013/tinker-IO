package tinker_io.plugins.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.SpecialChars;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.smeltery.tileentity.TileTank;
import tinker_io.TileEntity.StirlingEngineTileEntity;

public class StirlingEngineInfo implements IWailaDataProvider{

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor,	IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		if (accessor.getTileEntity() instanceof StirlingEngineTileEntity){
			
			String fuelAmountText = I18n.format("tio.toolTips.StirlingEngine.liquidAmount", new Object[0]);
		    String fuelTempText = I18n.format("tio.toolTips.StirlingEngine.liquidTemp", new Object[0]);
		    String energyStoredText = I18n.format("tio.toolTips.StirlingEngine.energyStored", new Object[0]);
		    String generatePerTickText = I18n.format("tio.toolTips.StirlingEngine.generatePerTick", new Object[0]);
		    
		    StirlingEngineTileEntity te = (StirlingEngineTileEntity) accessor.getTileEntity();
		    
		    if(te != null){
		    	TileTank teTank = te.getTETank();
		    	if(teTank != null){
			    	int liquidAmount = teTank.tank.getFluidAmount();
			    	FluidStack fluid = teTank.tank.getFluid();
			    	int fuildTemp = 0;
			    	if(fluid != null){
			    		fuildTemp = fluid.getFluid().getTemperature();
			    	}
			    	currenttip.add(SpecialChars.ITALIC + EnumChatFormatting.GRAY + fuelAmountText + " : " + liquidAmount);
		    		currenttip.add(SpecialChars.ITALIC + EnumChatFormatting.GRAY + fuelTempText + " : " + fuildTemp);
			    }
		    	
		    	int energy = te.getEnergyStored(null);
		    	currenttip.add(SpecialChars.ITALIC + EnumChatFormatting.GRAY + energyStoredText + " : " + energy + " / " + te.getMaxEnergyStored(null) + " RF");
		    	currenttip.add(SpecialChars.ITALIC + EnumChatFormatting.GRAY + generatePerTickText + " : " + te.getGeneratePetTick() + " RF");
		    }
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return null;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world,
			BlockPos pos) {
		return null;
	}

}
