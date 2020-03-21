package tinker_io.tileentity.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.IEnergyStorage;

public class EnergyCapability {

    private int energy;
    private int capacity;
    private int maxReceive;
    private int maxExtract;

    private IEnergyStorage storage = new IEnergyStorage() {
        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            int energyReceived = Math.min(capacity - energy, Math.min(EnergyCapability.this.maxReceive, maxReceive));

            if (!simulate) {
                energy += energyReceived;
            }
            return energyReceived;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            int energyExtracted = Math.min(energy, Math.min(EnergyCapability.this.maxExtract, maxExtract));

            if (!simulate) {
                energy -= energyExtracted;
            }
            return energyExtracted;
        }

        @Override
        public int getEnergyStored() {
            return EnergyCapability.this.energy;
        }

        @Override
        public int getMaxEnergyStored() {
            return EnergyCapability.this.capacity;
        }

        @Override
        public boolean canExtract() {
            return true;
        }

        @Override
        public boolean canReceive() {
            return true;
        }
    };

    public EnergyCapability(int capacity, int maxReceive, int maxExtract) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    public int getEnergyStored() {
        return this.energy;
    }

    public int getMaxEnergyStored() {
        return this.capacity;
    }

    public void setEnergyStored(int energy) {
        this.energy = energy;
    }

    public void readFromNBT(NBTTagCompound nbt) {

        this.energy = nbt.getInteger("Energy");

        if (energy > capacity) {
            energy = capacity;
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {

        if (energy < 0) {
            energy = 0;
        }
        nbt.setInteger("Energy", energy);
        return nbt;
    }

    public IEnergyStorage getStorage() {
        return storage;
    }
}
