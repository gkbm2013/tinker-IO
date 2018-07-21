package tinker_io.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tinker_io.TinkerIO;
import tinker_io.block.base.BlockTileEntity;
import tinker_io.handler.GuiHandler;
import tinker_io.tileentity.TileEntitySmartOutput;

import javax.annotation.Nullable;

public class BlockSmartOutput extends BlockTileEntity<TileEntitySmartOutput> {
    public BlockSmartOutput(String name) {
        super(Material.ROCK, name);
    }

    @Override
    public Class getTileEntityClass() {
        return TileEntitySmartOutput.class;
    }

    @Nullable
    @Override
    public TileEntitySmartOutput createTileEntity(World world, IBlockState state) {
        return new TileEntitySmartOutput();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            if (player.isSneaking()) {
                return false;
            } else {
                player.openGui(TinkerIO.instance, GuiHandler.SMART_OUTPUT, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }
}
