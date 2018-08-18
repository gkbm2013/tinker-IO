package tinker_io.tileentity;

import cofh.redstoneflux.api.IEnergyReceiver;
import cofh.redstoneflux.impl.EnergyStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import tinker_io.helper.OreCrusherRecipe;
import tinker_io.inventory.ContainerOreCrusher;
import tinker_io.registry.ItemRegistry;
import tinker_io.registry.OreCrusherRecipeRegister;

public class TileEntityOreCrusher extends TileEntityItemCapacity implements ITickable, IEnergyReceiver {

    public static final String TAG_ENERGY_CONSUME = "energyConsume";
    public static final String TAG_PROGRESS = "progress";
    public static final String TAG_CHANCE = "chance";
    public static final String TAG_SPEED = "speed";
    public static final String TAG_TARGET_ITEMSTACK = "targetItemStack";

    private static final int SLOT_SIZE = 6;
    private static final int PROGRESS_MAX = 300;

    private OreCrusherRecipe recipe = OreCrusherRecipe.EMPTY;
    private ItemStack targetItemStack = ItemStack.EMPTY;
    private ItemStack lastOre = ItemStack.EMPTY;
    private EnergyStorage storage = new EnergyStorage(100000, 2000, 0);

    private int energyConsume = 0;
    private int tick = 0;
    private int progress = 0;
    private int chance = 0;
    private int speed = 1;
    private int initCount = 0;

    public TileEntityOreCrusher() {
        super(SLOT_SIZE);
    }

    @Override
    public void update() {
        if(initCount <= 40) {
            initCount++;
            notifyBlockUpdate();
        }

        if(tick % 2 == 0) {
            calculateChance();
            calculateSpeed();
            calculateEnergyConsume();
            if(isChanged()) {
                ItemStack oreIn = inventory.getStackInSlot(ContainerOreCrusher.ORE);
                recipe = OreCrusherRecipeRegister.find(oreIn);
            }
            doCrush();
        }

        tick = (tick + 1) % 200;
    }

    private void doCrush() {
        if(progress == 0) {
            if(!recipe.isEmpty() && canOutput() && storage.getEnergyStored() - energyConsume > 0) {
                inventory.extractItem(ContainerOreCrusher.ORE, 1, false);
                targetItemStack = recipe.getItemStackOutput().copy();
                progress += speed;
                markDirty();
            }
        } else {
            if(storage.getEnergyStored() - energyConsume > 0 && !targetItemStack.isEmpty()) {
                progress = progress + speed;
                storage.setEnergyStored(storage.getEnergyStored() - energyConsume);
                notifyBlockUpdate();
            }
            if(progress >= PROGRESS_MAX) {
                progress = 0;

                int rate = (int)(Math.random()*99);
                int amount = (rate <= chance)? 3 : 2;
                ItemStack product = targetItemStack;
                product.setCount(amount);
                inventory.insertItem(ContainerOreCrusher.PRODUCT, product, false);
                targetItemStack = ItemStack.EMPTY;
                markDirty();
            }
        }
    }

    private void calculateEnergyConsume() {
        energyConsume = 45;
        ItemStack speedUpg = inventory.getStackInSlot(ContainerOreCrusher.SPEED_UPG);
        if(!speedUpg.isEmpty()){
            energyConsume += speedUpg.getCount() * 2;
        }
    }

    private void calculateChance() {
        chance = 0;
        int bookAmount = 0;

        ItemStack slotFortune1 = inventory.getStackInSlot(ContainerOreCrusher.FORTUNE_UPG_1);
        ItemStack slotFortune2 = inventory.getStackInSlot(ContainerOreCrusher.FORTUNE_UPG_2);
        ItemStack slotFortune3 = inventory.getStackInSlot(ContainerOreCrusher.FORTUNE_UPG_3);

        if(isFortuneEnchantedBook(slotFortune1)){
            bookAmount = bookAmount+2;
        }

        if(isFortuneEnchantedBook(slotFortune2)){
            bookAmount = bookAmount+2;
        }

        if(!slotFortune3.isEmpty() && slotFortune3.isItemEqual(new ItemStack(ItemRegistry.upgrade, 1, 6))){
            bookAmount = bookAmount + 3;
        }

        chance = 30 + bookAmount * 10;
    }

    private void calculateSpeed() {
        speed = 1;
        ItemStack speedUpg = inventory.getStackInSlot(ContainerOreCrusher.SPEED_UPG);
        if(!speedUpg.isEmpty()){
            speed = speedUpg.getCount() / 2 / 3;
        }
    }

    private boolean isChanged() {
        ItemStack itemStack = inventory.getStackInSlot(ContainerOreCrusher.ORE);

        boolean changed = !ItemStack.areItemsEqual(lastOre, itemStack)
                || !ItemStack.areItemStackTagsEqual(lastOre, itemStack);

        lastOre = itemStack;

        return changed;
    }

    private static boolean isFortuneEnchantedBook(ItemStack itemstack){
        if(itemstack != null && !itemstack.isEmpty()){
            return getEnchantID(itemstack) == 35;
        }
        return false;
    }

    private static int getEnchantID(ItemStack itemstack){
        if(itemstack != null && !itemstack.isEmpty() && itemstack.getItem() instanceof ItemEnchantedBook){
            NBTTagCompound tag = (NBTTagCompound) ItemEnchantedBook.getEnchantments(itemstack).get(0);
            return tag.getInteger("id");
        }
        return 0;
    }

    private boolean canOutput() {
        ItemStack productSlot = inventory.getStackInSlot(ContainerOreCrusher.PRODUCT);
        return (productSlot.isEmpty()
                || (productSlot.isItemEqual(recipe.getItemStackOutput())
                && ItemStack.areItemStackTagsEqual(productSlot, recipe.getItemStackOutput())))
                && inventory.getStackInSlot(ContainerOreCrusher.PRODUCT).getMaxStackSize() - productSlot.getCount() >= 3;
    }

    public int getProgress(int pixel) {
        return (int) ((float) progress / (float) PROGRESS_MAX * (float) pixel);
    }

    public int getChance() {
        return chance;
    }

    public int getEnergyConsume() {
        return energyConsume;
    }

    public int getEnergyBar(int pixel) {
        return (int) ((float) storage.getEnergyStored() / (float) storage.getMaxEnergyStored() * (float) pixel);
    }

    /* RF Energy */
    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
        markDirty();
        notifyBlockUpdate();
        return storage.receiveEnergy(Math.min(storage.getMaxReceive(), maxReceive), simulate);
    }

    @Override
    public int getEnergyStored(EnumFacing from) {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {
        return storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        return true;
    }

    /* NBT */
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        this.storage.writeToNBT(compound);
        compound.setInteger(TAG_CHANCE, chance);
        compound.setInteger(TAG_ENERGY_CONSUME, energyConsume);
        compound.setInteger(TAG_PROGRESS, progress);
        compound.setInteger(TAG_SPEED, speed);

        NBTTagCompound nbt = new NBTTagCompound();
        targetItemStack.writeToNBT(nbt);
        compound.setTag(TAG_TARGET_ITEMSTACK, nbt);

        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.storage.readFromNBT(compound);
        chance = compound.getInteger(TAG_CHANCE);
        energyConsume = compound.getInteger(TAG_ENERGY_CONSUME);
        progress = compound.getInteger(TAG_PROGRESS);
        speed = compound.getInteger(TAG_SPEED);
        targetItemStack = new ItemStack(compound.getCompoundTag(TAG_TARGET_ITEMSTACK));
        super.readFromNBT(compound);
    }

    /* Packet */
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(this.pos, 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        readFromNBT(packet.getNbtCompound());
    }

    private void notifyBlockUpdate(){
        if(world!=null && pos != null){
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
        }
    }
}
