package tinker_io.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.smeltery.tileentity.TileHeatingStructure;
import slimeknights.tconstruct.smeltery.tileentity.TileSmeltery;
import tinker_io.inventory.ContainerFuelInputMachine;
import tinker_io.network.MessageHeatSmeltery;
import tinker_io.network.NetworkHandler;

import javax.annotation.Nonnull;

public class TileEntityFuelInputMachine extends TileEntitySmelteryItemCapacity implements ITickable {

    public static final int MAX_PROGRESS_COUNT = 250;
    private static final int SLOTS_SIZE = 2;

    public static final String TAG_BURNING_COUNT = "burningCount";
    public static final String TAG_RATIO = "ratio";
    public static final String TAG_TARGET_TEMPERATURE = "targetTemp";
    public static final String TAG_CURRENT_SOLID_FUEL_TEMPERATURE = "currentSolidFuelTemp";

    private TileSmeltery tileSmeltery;

    private int tick = 0;
    private int burningCount = 0;
    private double ratio = 1;
    private int targetTemp = 0;
    private boolean isSmelteryHeatingItem = false;

    private int currentSolidFuelTemp = 0;

    public TileEntityFuelInputMachine() {
        super(SLOTS_SIZE);
    }

    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (slot == ContainerFuelInputMachine.FUEL && TileEntityFurnace.isItemFuel(stack)) {
            return inventory.insertItem(slot, stack, simulate);
        }
        return super.insertItem(slot, stack, simulate);
    }

    @Override
    public void update() {
        if(/*!getWorld().isRemote || */getMasterPosition() == null) return;

        if(tick % 2 == 0) {
            tileSmeltery = getMasterTile();
            if(tileSmeltery != null) {
                updateSmelteryHeatingState();
                calculateRatio();
                calculateTemperature();
                burnSolidFuel();
            }
        }

        tick = (tick + 1) % 20;
    }

    private void calculateRatio() {
        ItemStack stack = inventory.getStackInSlot(ContainerFuelInputMachine.SPEED_UPG);
        if(stack == ItemStack.EMPTY){
            this.ratio = 1;
        }
        this.ratio = (double) stack.getCount() / 10.0 + 1.0;
    }

    private void burnSolidFuel() {
        if(currentSolidFuelTemp == 0 && isSmelteryHeatingItem && inventory.getStackInSlot(ContainerFuelInputMachine.FUEL) != ItemStack.EMPTY) {
            currentSolidFuelTemp = getSolidFuelTemp();
            consumeItemStack(ContainerFuelInputMachine.FUEL, 1);
            markDirty();
        }

        int lastSolidFuelTemp = currentSolidFuelTemp;

        if(currentSolidFuelTemp != 0) {
            if(targetTemp - 300 != getNBT().getInteger(TileHeatingStructure.TAG_TEMPERATURE)){
                setSmelteryTemp(targetTemp);
            }

            if(burningCount == 0) {
                currentSolidFuelTemp = 0;
            }

            burningCount = (burningCount + 1) % MAX_PROGRESS_COUNT;
        }
        if(lastSolidFuelTemp != 0 && currentSolidFuelTemp == 0) {
            resetTemp();
        }
    }

    public TileSmeltery getMasterTile() {
        TileSmeltery tileSmeltery = null;
        BlockPos masterPos = getMasterPosition();
        World world = getWorld();
        if (getHasMaster() && masterPos != null && world.getTileEntity(masterPos) instanceof TileSmeltery) {
            tileSmeltery = (TileSmeltery) world.getTileEntity(masterPos);
        }
        return tileSmeltery;
    }

    private int getFuelTemp(){
        if(tileSmeltery == null) return 0;
        FluidStack fluidStack = tileSmeltery.currentFuel;
        if(fluidStack != null){
            return fluidStack.getFluid().getTemperature();
        }else{
            return 0;
        }
    }

    private int getSolidFuelTemp() {
        ItemStack solidFuel = inventory.getStackInSlot(ContainerFuelInputMachine.FUEL);
        if(solidFuel != ItemStack.EMPTY) {
            int burnTime = TileEntityFurnace.getItemBurnTime(solidFuel);
            if(burnTime > 0){
                return burnTime;
            }
        }
        return 0;
    }

    private void calculateTemperature(){
        int originFuelTemp = getFuelTemp();
        if(!isSmelteryHeatingItem) {
            targetTemp = getFuelTemp();
            return;
        }

        int fuelTempWithRatio = (int)(currentSolidFuelTemp * ratio);

        int f = fuelTempWithRatio / 2 + originFuelTemp;

        if(fuelTempWithRatio <= 20000){
            f = (fuelTempWithRatio * 6) / 100 + originFuelTemp;
        }
        if(f >= 200000){
            f = 200000;
        }
        targetTemp = f;
    }

    private NBTTagCompound getNBT() {
        final NBTTagCompound nbt = new NBTTagCompound();
        if(tileSmeltery != null){
            tileSmeltery.writeToNBT(nbt);
            return nbt;
        }else{
            return null;
        }
    }

    private void setSmelteryTempNBT(int temperature) {
        final NBTTagCompound nbt = getNBT();
        if(nbt == null) return;
        nbt.setInteger(TileHeatingStructure.TAG_TEMPERATURE, temperature);
        if(tileSmeltery != null){
            tileSmeltery.readFromNBT(nbt);
        }
    }

    private void setSmelteryTemp(int temperature) {
        temperature = (temperature - 300 > 0) ? temperature - 300 : temperature;
        if(!world.isRemote)
            NetworkHandler.sendToServer(new MessageHeatSmeltery(getPos(), temperature));
        setSmelteryTempNBT(temperature);
    }

    private int[] getSmelteryTemps() {
        NBTTagCompound nbt = getNBT();
        if(nbt != null) {
            return nbt.getIntArray(TileHeatingStructure.TAG_ITEM_TEMPERATURES);
        }
        return null;
    }

    private void updateSmelteryHeatingState() {
        NBTTagCompound nbt = getNBT();
        if(nbt != null) {
            int temps[] = getSmelteryTemps();
            for(int temp : temps) {
                if(temp > 0) {
                    isSmelteryHeatingItem = true;
                    return;
                }
            }
        }
        isSmelteryHeatingItem = false;
    }

    public int getTargetTemp() {
        return targetTemp;
    }

    public void resetTemp(){
        setSmelteryTemp(getFuelTemp());
    }

    public boolean isSmelteryHeatingItem() {
        return isSmelteryHeatingItem;
    }

    @SideOnly(Side.CLIENT)
    public double getRatio() {
        return ratio;
    }

    @SideOnly(Side.CLIENT)
    public int getScaledBurningCount(int pixel) {
        return (int) (((float)burningCount / (float)MAX_PROGRESS_COUNT) * pixel);
    }

    public int getCurrentSolidFuelTemp() {
        return currentSolidFuelTemp;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger(TAG_BURNING_COUNT, burningCount);
        compound.setDouble(TAG_RATIO, ratio);
        compound.setInteger(TAG_TARGET_TEMPERATURE, targetTemp);
        compound.setInteger(TAG_CURRENT_SOLID_FUEL_TEMPERATURE, currentSolidFuelTemp);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        burningCount = compound.getInteger(TAG_BURNING_COUNT);
        ratio = compound.getDouble(TAG_RATIO);
        targetTemp = compound.getInteger(TAG_TARGET_TEMPERATURE);
        currentSolidFuelTemp = compound.getInteger(TAG_CURRENT_SOLID_FUEL_TEMPERATURE);
        super.readFromNBT(compound);
    }
}
