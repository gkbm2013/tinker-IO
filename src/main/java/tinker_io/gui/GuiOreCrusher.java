package tinker_io.gui;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import tinker_io.TinkerIO;
import tinker_io.registry.BlockRegistry;
import tinker_io.tileentity.TileEntityOreCrusher;

import java.util.List;

public class GuiOreCrusher extends GuiContainer {

    private TileEntityOreCrusher tile;
    private InventoryPlayer playerInv;
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(TinkerIO.MOD_ID, "textures/gui/ore_crusher.png");
    public static final int WIDTH = 176;
    public static final int HEIGHT = 166;

    public GuiOreCrusher(Container container, TileEntityOreCrusher tile, InventoryPlayer playerInv) {
        super(container);
        this.tile = tile;
        this.playerInv = playerInv;

        this.xSize = WIDTH;
        this.ySize = HEIGHT;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        int progress = tile.getProgress(24);
        this.drawTexturedModalRect(guiLeft + 81, guiTop + 35, 176, 14, progress + 1, 16);

        int energyBar = tile.getEnergyBar(54);
        this.drawTexturedModalRect(guiLeft + 11, guiTop + 13 + 54 - energyBar, 178, 34, 10, energyBar);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = I18n.format(BlockRegistry.oreCrusher.getUnlocalizedName() + ".name");
        fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
        fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);

        //Chance
        String rate = tile.getChance() + "% + 1";
        this.fontRenderer.drawString(rate, (this.xSize - this.fontRenderer.getStringWidth(rate))/2 + 50, 66, 4210752);

        IEnergyStorage iEnergyStorage = tile.getCapability(CapabilityEnergy.ENERGY, null);

        if(iEnergyStorage != null) {
            //Energy Bar Tooltip
            List<String> text = Lists.newArrayList();
            text.add(TextFormatting.WHITE.toString() + I18n.format("tio.toolTips.oreCrusher.energy"));
            text.add(TextFormatting.WHITE.toString() + iEnergyStorage.getEnergyStored() + " / " + iEnergyStorage.getMaxEnergyStored()+ " " + I18n.format("tio.toolTips.oreCrusher.rf"));
            text.add(TextFormatting.WHITE.toString() + tile.getEnergyConsume() + " " + I18n.format("tio.toolTips.oreCrusher.rf_per_tick"));

            if(mouseX >= guiLeft + 11 && mouseX <= guiLeft + 21 && mouseY <= guiTop + 67 && mouseY >= guiTop + 67 - 54){
                this.drawHoveringText(text, mouseX-guiLeft, mouseY-guiTop);
            }
        }
    }
}
