package tinker_io.TileEntity;

import java.util.List;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import slimeknights.tconstruct.smeltery.tileentity.TileTank;
import tinker_io.api.BlockFinder;
import tinker_io.registry.BlockRegistry;

public class StirlingEngineTileEntity extends TileEntity implements  ITickable, IEnergyProvider {

	public double angel;
	
	protected EnergyStorage storage = new EnergyStorage(500000, 0, 2000);
	
	public StirlingEngineTileEntity() {
		// TODO 自動產生的建構子 Stub
	}	

	private void generateEnergy(){
		BlockPos tankPos = pos.down();
		//int liquidAmount = 0;
		int fuildTemp = 0;
		int generateEnergy = 0;
		
	    TileEntity te = this.worldObj.getTileEntity(tankPos);
	    if(te instanceof TileTank){
	    	TileTank teTank = (TileTank) this.worldObj.getTileEntity(tankPos);
	    	IFluidHandler toDrain = (IFluidHandler) teTank;
	    	FluidStack canDrain = toDrain.drain(EnumFacing.DOWN, 1, false);
		    if(teTank != null){
		    	FluidStack fulid = teTank.tank.getFluid();
		    	if(fulid != null && this.getEnergyStored(null) < storage.getMaxEnergyStored()){
		    		fuildTemp = fulid.getFluid().getTemperature();
		    		generateEnergy = (fuildTemp - 300)*30/100;
		    		storage.setEnergyStored(storage.getEnergyStored() + generateEnergy);
		    		if(canDrain != null){
			    		toDrain.drain(EnumFacing.DOWN, 1, true);
			    	}
		    	}
		    }
	    }
	    //System.out.println(storedEnergy);
	}
	
	private void angel(){
		BlockPos tankPos = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
		int fuildTemp = 0;
		double anglePlus = 0;
		
		TileEntity te = this.worldObj.getTileEntity(tankPos);
		if(te instanceof TileTank){
	    	TileTank teTank = (TileTank) this.worldObj.getTileEntity(tankPos);
		    if(teTank != null){
		    	//liquidAmount = teTank.tank.getFluidAmount();
		    	FluidStack fulid = teTank.tank.getFluid();
		    	teTank.tank.drain(1, true);
		    	if(fulid != null){
		    		fuildTemp = fulid.getFluid().getTemperature();
		    	}
		    }
	    }
		if((fuildTemp - 300) > 0 && this.getEnergyStored(null) < storage.getMaxEnergyStored()){
			anglePlus = 1;
		}
		
		if(angel <= 60){
			angel = angel + anglePlus;
		}else{
			angel = 0;
		}
	}
	
	private int extraEnergyID;
	
	private void extraEnergyToSurroundingMechine(){
		int extraPerTick = Math.min(this.storage.getEnergyStored(), 1000);
		
		BlockFinder blockFinder = new BlockFinder(pos, worldObj);
		List<BlockPos> blocPoskList = blockFinder.getSurroundingBlockPos(pos, BlockRegistry.oreCrusher);
		if(blocPoskList.size() == 0){
			return;
		}
		if(extraEnergyID >= blocPoskList.size()){
			extraEnergyID = 0;
		}
		//System.out.println(extraEnergyID);
		
		BlockPos blockPos = blocPoskList.get(extraEnergyID);
		//BlockPos blockPos = blocPoskList.get(0);
		if(blockPos == null){
			extraEnergyID = 0;
			return;
		}
		int averageExtraEnergy = (int)Math.floor(extraPerTick / blocPoskList.size());
		//averageExtraEnergy = Math.min(averageExtraEnergy, this.storage.getEnergyStored());
		
		IEnergyReceiver rfStorage = (IEnergyReceiver) worldObj.getTileEntity(blockPos);
		if(rfStorage != null){
			if(this.storage.getEnergyStored() > 0 && rfStorage.getEnergyStored(EnumFacing.DOWN) < rfStorage.getMaxEnergyStored(EnumFacing.DOWN)){
				storage.setEnergyStored(storage.getEnergyStored() - averageExtraEnergy);
				rfStorage.receiveEnergy(EnumFacing.DOWN, averageExtraEnergy, false);
			}
		}
		
		extraEnergyID++;

	}
	
	@Override
	public void update() {
		if (worldObj.isRemote) return;
		angel();
		worldObj.markBlockForUpdate(pos);
		generateEnergy();
		extraEnergyToSurroundingMechine();
		//System.out.println(angel);
	}
	
	//Packet
	@Override
	public Packet getDescriptionPacket()
    {
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		return new S35PacketUpdateTileEntity(this.pos, 1, nbtTag);
    }
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet){
		readFromNBT(packet.getNbtCompound());
    }
	 
	
	//NBT
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		this.angel = tagCompound.getDouble("Angel");
		this.storage.readFromNBT(tagCompound);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setDouble("Angel", (short) this.angel);
		this.storage.writeToNBT(tagCompound);
	}

	//RF Energy
	@Override
	public int getEnergyStored(EnumFacing from) {
		return this.storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return this.storage.getMaxEnergyStored();
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		return this.storage.receiveEnergy(Math.min(storage.getMaxExtract(), maxExtract), simulate);
	}
}
