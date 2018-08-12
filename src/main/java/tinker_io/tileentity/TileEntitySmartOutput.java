package tinker_io.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.smeltery.ICastingRecipe;
import tinker_io.fluids.FluidTankWithTile;
import tinker_io.handler.EnumHandler.ItemUpgradeTypes;
import tinker_io.handler.SmartOutputRecipeHandler;
import tinker_io.inventory.ContainerSmartOutput;
import tinker_io.registry.ItemRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntitySmartOutput extends TileEntityItemCapacity implements ITickable {

    public static final String TAG_CURRENT_MODE = "currentMode";
    public static final String TAG_PROGRESS = "progress";
    public static final String TAG_MAX_OUTPUT_STACK_SIZE = "maxOutputStackSize";
    public static final String TAG_TARGET_ITEM_STACK = "targetItemStack";

    public static final int CAPACITY = Fluid.BUCKET_VOLUME * 10;
    private static final int PROGRESS_MAX = 20;

    public static final int MODE_CAST = 0;
    public static final int MODE_BASIN = 1;
    private static final int SLOTS_SIZE = 4;

    private FluidTank tank;

    private int tick = 0;
    private int progress;
    private int maxOutputStackSize = 0;
    private int currentMode = MODE_CAST;

    private boolean canControlledByRedstone = false;
    private boolean blockPowered = false;

    private ICastingRecipe currentRecipe;
    private ItemStack targetItemStack = ItemStack.EMPTY;

    private int lastMode = MODE_CAST;
    private FluidStack lastFluidStack;
    private ItemStack lastCast = ItemStack.EMPTY;

    public TileEntitySmartOutput() {
        super(SLOTS_SIZE);
        tank = new FluidTankWithTile(this, CAPACITY);
    }

    @Override
    public void update() {
        if(tick % 2 == 0) {
            checkUpgrade();
            blockPowered = world.isBlockPowered(pos);

            if(isChanged()){
                updateRecipe();
            }

            if(canWork()) {
                doCasting();
            }
        }
        tick = (tick + 1) % 200;
    }

    private void updateRecipe() {
        ItemStack cast = inventory.getStackInSlot(ContainerSmartOutput.PATTERN);
        FluidStack fluidStack = tank.getFluid();
        if(currentMode == MODE_BASIN) {
            currentRecipe = SmartOutputRecipeHandler.findBasinCastingRecipe(ItemStack.EMPTY, fluidStack);
        } else if(currentMode == MODE_CAST) {
            currentRecipe = SmartOutputRecipeHandler.findTableCastingRecipe(cast, fluidStack);
        }
    }


    /**
     * casting() or frozen() in old Tinker I/O version......
     * Frozen!? Let it go! Let it go! Can't hold it back anymore~  - GKB 2015/4/4 22:22 (Tired...)
     * */
    private void doCasting() {
        if(progress == 0){
            if(targetItemStack.isEmpty() && currentRecipe != null) {
                ItemStack cast = inventory.getStackInSlot(ContainerSmartOutput.PATTERN);
                FluidStack fluidStack = tank.getFluid();

                if(fluidStack != null && fluidStack.amount >= currentRecipe.getFluidAmount()){
                    targetItemStack = getResult(cast, fluidStack);
                    if(!targetItemStack.isEmpty() && canOutput()){
                        if(currentRecipe.consumesCast())
                            inventory.extractItem(ContainerSmartOutput.PATTERN, 1, false);
                        tank.drain(currentRecipe.getFluidAmount(), true);
                        progress++;
                        markDirty();
                    }else{
                        targetItemStack = ItemStack.EMPTY;
                    }
                }
            }
        }else{
            if(progress >= PROGRESS_MAX - 1) {
                inventory.insertItem(ContainerSmartOutput.PRODUCT, targetItemStack, false);
                targetItemStack = ItemStack.EMPTY;
                progress = 0;
            }else{
                progress = (progress + 1) % PROGRESS_MAX;
            }
            markDirty();
        }
    }


    private ItemStack getResult(ItemStack cast, FluidStack fluidStack) {
        if(fluidStack != null)
            return currentRecipe.getResult(cast, fluidStack.getFluid());
        else
            return ItemStack.EMPTY;
    }

    private boolean canOutput() {
        ItemStack productSlot = inventory.getStackInSlot(ContainerSmartOutput.PRODUCT);
        return (productSlot.isEmpty()
                || (productSlot.isItemEqual(targetItemStack)
                    && ItemStack.areItemStackTagsEqual(productSlot, targetItemStack)))
                && maxOutputStackSize - productSlot.getCount() > 0;
    }

    private boolean isChanged() {
        FluidStack fluidStack = tank.getFluid();
        ItemStack itemStack = inventory.getStackInSlot(ContainerSmartOutput.PATTERN);

        boolean changed = (lastMode != currentMode)
                || (lastFluidStack != null && fluidStack == null)
                || (lastFluidStack == null && fluidStack != null)
                || (fluidStack != null && !fluidStack.isFluidEqual(lastFluidStack))
                || (fluidStack != null && !FluidStack.areFluidStackTagsEqual(lastFluidStack, fluidStack))
                || !ItemStack.areItemsEqual(lastCast, itemStack)
                || !ItemStack.areItemStackTagsEqual(lastCast, itemStack);

        lastMode = currentMode;
        lastFluidStack = fluidStack;
        lastCast = itemStack;

        return changed;
    }

    private void checkUpgrade() {
        ItemStack stackUp = inventory.getStackInSlot(ContainerSmartOutput.UPG_UP);
        ItemStack stackDown = inventory.getStackInSlot(ContainerSmartOutput.UPG_DOWN);
        maxOutputStackSize = 1;
        canControlledByRedstone = false;
        currentMode = MODE_CAST;
        checkUpgrade(stackUp);
        checkUpgrade(stackDown);
        if(maxOutputStackSize > 64)
            maxOutputStackSize = 64;
    }

    private void checkUpgrade(ItemStack itemStack) {
        if(itemStack != ItemStack.EMPTY && itemStack.getItem().equals(ItemRegistry.upgrade)) {
            maxOutputStackSize += getSlotExpendSize(itemStack);
            if(itemStack.isItemEqual(new ItemStack(ItemRegistry.upgrade, 1, ItemUpgradeTypes.REDETONE_UPG.getID()))) {
                canControlledByRedstone = true;
            }
            if(itemStack.isItemEqual(new ItemStack(ItemRegistry.upgrade, 1, ItemUpgradeTypes.BASIN_UPGRADE.getID()))) {
                currentMode = MODE_BASIN;
            }
        }
    }

    private int getSlotExpendSize(ItemStack itemStack) {
        int size = 0;
        int meta = itemStack.getItemDamage();
        int count = itemStack.getCount();
        if(meta == ItemUpgradeTypes.SLOT_UPG_1.getID()) {
            size = count * 1;
        } else if(meta == ItemUpgradeTypes.SLOT_UPG_2.getID()) {
            size = count * 2;
        } else if(meta == ItemUpgradeTypes.SLOT_UPG_3.getID()) {
            size = count * 3;
        } else if(meta == ItemUpgradeTypes.SLOT_UPG_4.getID()) {
            size = count * 4;
        }
        return size;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        tank.readFromNBT(tag);
        currentMode = tag.getInteger(TAG_CURRENT_MODE);
        progress = tag.getInteger(TAG_PROGRESS);
        maxOutputStackSize = tag.getInteger(TAG_MAX_OUTPUT_STACK_SIZE);
        targetItemStack = new ItemStack(tag.getCompoundTag(TAG_TARGET_ITEM_STACK));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag = super.writeToNBT(tag);
        tank.writeToNBT(tag);
        tag.setInteger(TAG_CURRENT_MODE, currentMode);
        tag.setInteger(TAG_PROGRESS, progress);
        tag.setInteger(TAG_MAX_OUTPUT_STACK_SIZE, maxOutputStackSize);

        NBTTagCompound tagItemStack = new NBTTagCompound();
        tagItemStack = targetItemStack.writeToNBT(tagItemStack);
        tag.setTag(TAG_TARGET_ITEM_STACK, tagItemStack);

        return tag;
    }

    public FluidTank getTank() {
        return tank;
    }

    public FluidStack getFluid() {
        return tank.getFluid();
    }

    public int getFluidAmount() {
        if(getFluid() != null) {
            return getFluid().amount;
        }
        return 0;
    }

    public void emptyTank() {
        tank.drain(getFluidAmount(), true);
        markDirty();
    }

    /* Capability */
    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return (T) tank;
        return super.getCapability(capability, facing);
    }

    //Packet
    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        readFromNBT(packet.getNbtCompound());
    }

    public int getCurrentMode() {
        return currentMode;
    }

    public int getMaxOutputStackSize() {
        return maxOutputStackSize;
    }

    public boolean isCanControlledByRedstone() {
        return canControlledByRedstone;
    }

    public boolean isBlockPowered() {
        return blockPowered;
    }

    public boolean canWork() {
        if(!canControlledByRedstone)
            return true;
        else
            return !blockPowered;
    }

    @SideOnly(Side.CLIENT)
    public int getFluidBarHeight(int pixel) {
        return (int) (((float)tank.getFluidAmount() / (float)CAPACITY) * pixel);
    }

    @SideOnly(Side.CLIENT)
    public int getProgress(int pixel) {
        return (int) ( (float)pixel * (float) progress / (float) PROGRESS_MAX );
    }
}
