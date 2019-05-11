package tinker_io.block;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tinker_io.TinkerIO;
import tinker_io.block.base.BlockFacingTileEntity;
import tinker_io.handler.GuiHandler;
import tinker_io.helper.CrushedOreHelper;
import tinker_io.helper.OreDictionaryHelper;
import tinker_io.plugins.theoneprob.TOPInfoProvider;
import tinker_io.tileentity.TileEntityOreCrusher;

import javax.annotation.Nullable;

public class BlockOreCrusher extends BlockFacingTileEntity<TileEntityOreCrusher> implements TOPInfoProvider {
    public BlockOreCrusher(String name) {
        super(Material.ROCK, name);
        this.setHardness(2.5f);
        this.setHarvestLevel("pickaxe", 1);
    }

    @Override
    public Class<TileEntityOreCrusher> getTileEntityClass() {
        return TileEntityOreCrusher.class;
    }

    @Nullable
    @Override
    public TileEntityOreCrusher createTileEntity(World world, IBlockState state) {
        return new TileEntityOreCrusher();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            if (player.isSneaking()) {
                return false;
            } else {
                player.openGui(TinkerIO.instance, GuiHandler.ORE_CRUSHER, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        TileEntity te = world.getTileEntity(data.getPos());
        if(te instanceof TileEntityOreCrusher) {
            TileEntityOreCrusher tile = (TileEntityOreCrusher) te;
            ItemStack itemStack = tile.getTargetItemStack();
            if(itemStack != null && !itemStack.isEmpty()) {
                NBTTagCompound nbt = itemStack.getTagCompound();


                if(nbt != null) {
                    probeInfo.horizontal()
                            .item(tile.getTargetItemStack())
                            .text(tile.getTargetItemStack().getDisplayName())
                            .text(" ("+OreDictionaryHelper.getDisplayNameFromOreDict(nbt.getString(CrushedOreHelper.TAG_ORE_DICT)) + ")");
                } else {
                    probeInfo.horizontal()
                            .item(tile.getTargetItemStack())
                            .text(tile.getTargetItemStack().getDisplayName());
                }

                probeInfo.horizontal()
                        .progress(tile.getProgress(100) % 100, 100, probeInfo.defaultProgressStyle().suffix("%"));
            }
        }
    }
}
