package tinker_io.block;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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
import tinker_io.block.base.BlockFacingTileEntity;
import tinker_io.plugins.theoneprob.TOPInfoProvider;
import tinker_io.tileentity.TileEntityStirlingEngine;

import javax.annotation.Nullable;
import java.util.List;

import static tinker_io.item.ItemBase.isShiftKeyDown;

public class BlockStirlingEngine extends BlockFacingTileEntity<TileEntityStirlingEngine> implements TOPInfoProvider {
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
        String fuelAmountText = I18n.format("tio.toolTips.StirlingEngine.liquidAmount");
        String fuelTempText = I18n.format("tio.toolTips.StirlingEngine.liquidTemp");
        String energyStoredText = I18n.format("tio.toolTips.StirlingEngine.energyStored");
        String generatePerTickText = I18n.format("tio.toolTips.StirlingEngine.generatePerTick");

        TileEntityStirlingEngine te = (TileEntityStirlingEngine) worldIn.getTileEntity(pos);

        if (te != null) {
            playerIn.sendMessage(new TextComponentString(TextFormatting.WHITE + fuelAmountText + " : " + te.getFluidAmount()));
            playerIn.sendMessage(new TextComponentString(TextFormatting.WHITE + fuelTempText + " : " + te.getTemperature()));

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

    @Override
    public Item createItemBlock() {
        ItemBlock itemBlock = new ItemBlock(this){
            @Override
            public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
                super.addInformation(stack, worldIn, tooltip, flagIn);
                NBTTagCompound nbt = stack.getTagCompound();
                if(nbt != null) {
                    int energy = nbt.getInteger("energy");
                    if(isShiftKeyDown()){
                        String tips = TextFormatting.GREEN + I18n.format("tio.toolTips.itemBlock.StirlingEngine.energyStored", energy);
                        tooltip.add(tips);
                    }else{
                        tooltip.add(TextFormatting.GOLD + I18n.format("tio.toolTips.common.holdShift"));
                    }
                }
            }
        };
        itemBlock.setRegistryName(getRegistryName());
        return itemBlock;
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        TileEntity te = world.getTileEntity(data.getPos());
        if(te instanceof TileEntityStirlingEngine) {
            TileEntityStirlingEngine tile = (TileEntityStirlingEngine) te;

            String fuelAmountText = I18n.format("tio.toolTips.StirlingEngine.liquidAmount");
            String fuelTempText = I18n.format("tio.toolTips.StirlingEngine.liquidTemp");
            String energyStoredText = I18n.format("tio.toolTips.StirlingEngine.energyStored");
            String generatePerTickText = I18n.format("tio.toolTips.StirlingEngine.generatePerTick");

            probeInfo.vertical()
                    .text(TextFormatting.WHITE + fuelAmountText + " : " + tile.getFluidAmount())
                    .text(TextFormatting.WHITE + fuelTempText + " : " + tile.getTemperature())
                    .text("");

            int energy = tile.getEnergyStored(null);
            probeInfo.vertical()

                .text(TextFormatting.WHITE + energyStoredText + " : " + energy + " / " + tile.getMaxEnergyStored(null) + " RF")
                .text(TextFormatting.WHITE + generatePerTickText + " : " + tile.getRfPerTick() + " RF");
        }
    }
}
