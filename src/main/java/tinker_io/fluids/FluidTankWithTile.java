package tinker_io.fluids;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class FluidTankWithTile extends FluidTank {
    public FluidTankWithTile(final TileEntity tileEntity, final int capacity) {
        super(capacity);
        tile = tileEntity;
    }

    public FluidTankWithTile(final TileEntity tileEntity, final FluidStack stack, final int capacity) {
        super(stack, capacity);
        tile = tileEntity;
    }

    public FluidTankWithTile(final TileEntity tileEntity, final Fluid fluid, final int amount, final int capacity) {
        super(fluid, amount, capacity);
        tile = tileEntity;
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        notifyBlockUpdate();
        tile.markDirty();
        return super.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        notifyBlockUpdate();
        tile.markDirty();
        return super.drain(resource, doDrain);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        notifyBlockUpdate();
        tile.markDirty();
        return super.drain(maxDrain, doDrain);
    }

    private void notifyBlockUpdate(){
        World world = tile.getWorld();
        BlockPos pos = tile.getPos();
        IBlockState state = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, state, state, 3);
    }
}
