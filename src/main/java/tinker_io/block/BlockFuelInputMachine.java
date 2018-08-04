package tinker_io.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.smeltery.tileentity.TileSmelteryComponent;
import tinker_io.TinkerIO;
import tinker_io.block.base.BlockFacingTileEntity;
import tinker_io.handler.GuiHandler;
import tinker_io.tileentity.TileEntityFuelInputMachine;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockFuelInputMachine extends BlockFacingTileEntity<TileEntityFuelInputMachine> {
    public BlockFuelInputMachine(String name) {
        super(Material.ROCK, name);
    }

    @Override
    public Class<TileEntityFuelInputMachine> getTileEntityClass() {
        return TileEntityFuelInputMachine.class;
    }

    @Nullable
    @Override
    public TileEntityFuelInputMachine createTileEntity(World world, IBlockState state) {
        return new TileEntityFuelInputMachine();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            if (player.isSneaking()) {
                return false;
            } else {
                player.openGui(TinkerIO.instance, GuiHandler.FUEL_INPUT_MACHINE, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    /* BlockContainer TE handling */
    @Override
    public void breakBlock(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        TileEntity te = worldIn.getTileEntity(pos);
        if(te instanceof TileSmelteryComponent) {
            ((TileSmelteryComponent) te).notifyMasterOfChange();
        }

        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if (world.isRemote) {
            return true;
        }
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileSmelteryComponent) {
            ((TileSmelteryComponent) te).notifyMasterOfChange();
        }
        if (te instanceof TileEntityFuelInputMachine) {
            ((TileEntityFuelInputMachine) te).resetTemp();
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }


}
