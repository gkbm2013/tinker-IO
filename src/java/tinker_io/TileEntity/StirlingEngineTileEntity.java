package tinker_io.TileEntity;

import java.util.List;
import java.util.stream.Collectors;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
//import net.minecraftforge.fluids.IFluidHandler;
import slimeknights.tconstruct.smeltery.tileentity.TileTank;
import tinker_io.api.BlockFinder;

public class StirlingEngineTileEntity extends TileEntity implements  ITickable, IEnergyProvider {

	public double angel;
	
	protected EnergyStorage storage = new EnergyStorage(500000, 0, 2000);
	
	protected int generatePerTick;
	
	public StirlingEngineTileEntity() {
		// TODO
	}	

	private void generateEnergy(){
		BlockPos tankPos = pos.down();
		//int liquidAmount = 0;
		int fluidTemp = 0;
		int generateEnergy = 0;
		
	    TileEntity te = this.world.getTileEntity(tankPos);
	    if(te instanceof TileTank){
	    	TileTank teTank = (TileTank) this.world.getTileEntity(tankPos);
		    if(teTank != null){
		    	FluidTank toDrain = teTank.getInternalTank();
		    	FluidStack canDrain = toDrain.drain(1, false);
		    	
		    	FluidStack fulid = teTank.getInternalTank().getFluid();
		    	if(fulid != null && this.getEnergyStored(null) < storage.getMaxEnergyStored()){
		    		fluidTemp = fulid.getFluid().getTemperature();
		    		fluidTemp = fluidTemp - 300; //Fix
		    		
		    		generateEnergy = fluidTemp * 15 / 100;
		    		
		    		if(generateEnergy > 0){
		    			generatePerTick = generateEnergy;
			    		storage.setEnergyStored(storage.getEnergyStored() + generateEnergy);
			    		if(canDrain != null){
				    		toDrain.drain(2, true);
				    	}
		    		}
		    	}else{
		    		generatePerTick = 0;
		    	}
		    }else{
		    	generatePerTick = 0;
		    }
	    }
	    //System.out.println(storedEnergy);
	}
	
	public int getGeneratePetTick(){
		return generatePerTick;
	}
	
	private void angel(){
		BlockPos tankPos = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
		int fluidTemp = 0;
		double anglePlus = 0;
		
		TileEntity te = this.world.getTileEntity(tankPos);
		if(te instanceof TileTank){
	    	TileTank teTank = (TileTank) this.world.getTileEntity(tankPos);
		    if(teTank != null){
		    	//liquidAmount = teTank.tank.getFluidAmount();
		    	FluidStack fulid = teTank.getInternalTank().getFluid();
		    	if(fulid != null){
		    		fluidTemp = fulid.getFluid().getTemperature();
		    	}
		    }
	    }
		if((fluidTemp - 300) > 0 && this.getEnergyStored(null) < storage.getMaxEnergyStored()){
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
		
		BlockFinder blockFinder = new BlockFinder(pos, world);
		List<BlockPos> blocPoskList = blockFinder.getSurroundingTileEntityPos(pos);
		blocPoskList = blocPoskList.stream().filter(pos -> world.getTileEntity(pos) instanceof IEnergyReceiver).collect(Collectors.toList());
		
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
		
		IEnergyReceiver rfStorage = (IEnergyReceiver) world.getTileEntity(blockPos);
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
		if (world.isRemote) return;
		angel();
		//world.markBlockForUpdate(pos);
		this.notifyBlockUpdate();
		generateEnergy();
		extraEnergyToSurroundingMechine();
		//System.out.println(angel);
		//System.out.println(this.pos);
	}
	
	public TileTank getTETank(){
		BlockPos tankPos = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
	    TileEntity teUnknow = world.getTileEntity(tankPos);
	    if(teUnknow instanceof TileTank){
	    	TileTank teTank = (TileTank) world.getTileEntity(tankPos);
	    	if(teTank != null){
	    		return teTank;
	    	}
	    }
	    return null;
	}
	
	//Packet
	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
    {
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		return new SPacketUpdateTileEntity(this.pos, 1, nbtTag);
    }
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet){
		readFromNBT(packet.getNbtCompound());
    }
	 
	
	//NBT
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		this.angel = tagCompound.getDouble("Angel");
		this.generatePerTick = tagCompound.getInteger("generatePerTick");
		this.storage.readFromNBT(tagCompound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setDouble("Angel", (short) this.angel);
		tagCompound.setInteger("generatePerTick", this.generatePerTick);
		this.storage.writeToNBT(tagCompound);
		return tagCompound;
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
		return this.storage.extractEnergy(Math.min(storage.getMaxExtract(), maxExtract), simulate);
	}
	
	public EnergyStorage getStorage(){
		return this.storage;
	}
	
	private void notifyBlockUpdate(){
		if(world!=null && pos != null){
			IBlockState state = world.getBlockState(pos);
			world.notifyBlockUpdate(pos, state, state, 3);
		}
	}
}
