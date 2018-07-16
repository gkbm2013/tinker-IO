package tinker_io.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import tinker_io.TinkerIO;
import tinker_io.block.base.BlockFacingTileEntity;
import tinker_io.registry.GuiRegistry;
import tinker_io.tileentity.TileEntityFuelInputMachine;

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
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntityFuelInputMachine tile = getTileEntity(world, pos);
        IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        ItemStack stack = itemHandler.getStackInSlot(0);
        if (!stack.isEmpty()) {
            EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            world.spawnEntity(item);
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            if (player.isSneaking()) {
                return false;
            } else {
                player.openGui(TinkerIO.instance, GuiRegistry.FUEL_INPUT_MACHINE, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }
}
