package tinker_io.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import tinker_io.block.base.BlockFacingTileEntity;
import tinker_io.tileentity.TileEntityStirlingEngine;

import javax.annotation.Nullable;
import java.util.List;

public class BlockStirlingEngine extends BlockFacingTileEntity<TileEntityStirlingEngine> {
    public BlockStirlingEngine(String name) {
        super(Material.ROCK, name);
        setHarvestLevel("pickaxe", 1);
        setHardness(3);
    }

    @Override
    public Class<TileEntityStirlingEngine> getTileEntityClass() {
        return TileEntityStirlingEngine.class;
    }

    @Nullable
    @Override
    public TileEntityStirlingEngine createTileEntity(World world, IBlockState state) {
        return new TileEntityStirlingEngine();
    }

    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (playerIn.isSneaking()) {
            return false;
        }
        //TODO Stirling Engine
        String fuelAmountText = I18n.format("tio.toolTips.StirlingEngine.liquidAmount", new Object[0]);
        String fuelTempText = I18n.format("tio.toolTips.StirlingEngine.liquidTemp", new Object[0]);
        String energyStoredText = I18n.format("tio.toolTips.StirlingEngine.energyStored", new Object[0]);
        String generatePerTickText = I18n.format("tio.toolTips.StirlingEngine.generatePerTick", new Object[0]);

        TileEntityStirlingEngine te = (TileEntityStirlingEngine) worldIn.getTileEntity(pos);

        if (te != null) {
            FluidTank tank = te.getTankBelow();
            if (tank != null) {
                int liquidAmount = tank.getFluidAmount();
                FluidStack fluid = tank.getFluid();
                int fuildTemp = 0;
                if (fluid != null) {
                    fuildTemp = fluid.getFluid().getTemperature();
                }
                if (worldIn.isRemote) {
                    playerIn.sendMessage(new TextComponentString(TextFormatting.WHITE + fuelAmountText + " : " + liquidAmount));
                    playerIn.sendMessage(new TextComponentString(TextFormatting.WHITE + fuelTempText + " : " + fuildTemp));
                }
            }

            int energy = te.getEnergyStored(null);
            if (worldIn.isRemote) {
                playerIn.sendMessage(new TextComponentString(TextFormatting.WHITE + energyStoredText + " : " + energy + " / " + te.getMaxEnergyStored(null) + " RF"));
                playerIn.sendMessage(new TextComponentString(TextFormatting.WHITE + generatePerTickText + " : " + te.getRfPerTick() + " RF"));
                playerIn.sendMessage(new TextComponentString(TextFormatting.YELLOW + "-----"));
            }
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        TileEntity te = worldIn.getTileEntity(pos);
        if (te != null) {
            TileEntityStirlingEngine engine = (TileEntityStirlingEngine) te;
            NBTTagCompound nbt = stack.getTagCompound();
            if (nbt != null) {
                engine.setEnergyAmount(nbt.getInteger("energy"));
            }
        }
    }

    //Render

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;//number 3 for standard block models
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if (world.isRemote) {
            return false;
        }

        //IBlockState state = world.getBlockState(pos);
        this.onBlockDestroyedByPlayer(world, pos, state);
        if (willHarvest) {
            this.harvestBlock(world, player, pos, state, world.getTileEntity(pos), ItemStack.EMPTY);
        }

        world.setBlockToAir(pos);
        return false;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        List<ItemStack> items = super.getDrops(world, pos, state, fortune);

        TileEntity te = world.getTileEntity(pos);

        if(te != null && te instanceof TileEntityStirlingEngine) {
            TileEntityStirlingEngine engine = (TileEntityStirlingEngine) te;
            for(ItemStack item : items) {
                if(item.getItem() == Item.getItemFromBlock(this)) {
                    NBTTagCompound tag = new NBTTagCompound();
                    tag.setInteger("energy", engine.getEnergyStored());
                    item.setTagCompound(tag);

                }
            }
        }
        return items;
    }

    @Override
    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
        return false;
    }

}
