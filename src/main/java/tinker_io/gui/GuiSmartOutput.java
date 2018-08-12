package tinker_io.gui;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.client.GuiUtil;
import tinker_io.TinkerIO;
import tinker_io.network.MessageEmptyTank;
import tinker_io.network.NetworkHandler;
import tinker_io.registry.BlockRegistry;
import tinker_io.tileentity.TileEntitySmartOutput;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class GuiSmartOutput extends GuiContainer {

    public static final int BUTTON_EMPTY_TANK = 0;

    private InventoryPlayer playerInv;
    private TileEntitySmartOutput tile;
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(TinkerIO.MOD_ID, "textures/gui/smart_output.png");
    public static final int WIDTH = 176;
    public static final int HEIGHT = 166;

    private GuiButton buttonEmptyTank;

    public GuiSmartOutput(Container container, TileEntitySmartOutput tile, InventoryPlayer playerInv) {
        super(container);
        this.playerInv = playerInv;
        this.tile = tile;

        this.xSize = WIDTH;
        this.ySize = HEIGHT;
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonEmptyTank = new GuiButton(BUTTON_EMPTY_TANK, guiLeft - 20,  guiTop + ySize - 150, 20, 20, ""){
            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
                super.drawButton(mc, mouseX, mouseY, partialTicks);
                GuiSmartOutput.this.mc.getTextureManager().bindTexture(GuiSmartOutput.BG_TEXTURE);
                this.drawTexturedModalRect(x, y, 178, 82, 20, 20);
            }
        };
        buttonList.add(buttonEmptyTank);
        buttonEmptyTank.enabled = false;
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

        //Progress
        int progress = tile.getProgress(24);
        this.drawTexturedModalRect(guiLeft + 93, guiTop + 33, 176, 0, progress + 1, 17);

        //Redstone Control
        if(tile.isCanControlledByRedstone()) {
            if(!tile.canWork()) {
                this.drawTexturedModalRect(guiLeft + 119, guiTop + 55, 176, 18, 22, 20);
                this.drawTexturedModalRect(guiLeft + 92, guiTop + 32, 176, 60, 26, 20);
            } else {
                this.drawTexturedModalRect(guiLeft + 120, guiTop + 55, 177, 39, 21, 20);
            }
        }

        //Cast / Basin Mode
        if(tile.getCurrentMode() == TileEntitySmartOutput.MODE_BASIN) {
            this.drawTexturedModalRect(guiLeft + 77, guiTop + 52, 177, 104, 16, 16);
            this.drawTexturedModalRect(guiLeft + 59, guiTop + 52, 177, 121, 16, 16);
        }

        //Liquid
        this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        FluidStack fluid = tile.getFluid();
        int fluidAmount = tile.getFluidBarHeight(52);
        if(fluid != null) {
            GuiUtil.renderTiledFluid(guiLeft + 26, guiTop + 15 + 52 - fluidAmount, 12, fluidAmount, this.zLevel, fluid);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = I18n.format(BlockRegistry.smartOutput.getUnlocalizedName() + ".name");
        fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
        fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);


        //TODO Add translation
        //Max Output
        int outputSize = tile.getMaxOutputStackSize();
        this.fontRenderer.drawString(TextFormatting.DARK_GREEN + I18n.format("tio.gui.SO.max_output") + outputSize, 100, 17, 0x404040);

        FluidStack fluid = tile.getFluid();
        if(fluid != null) {
            List<String> tooltip = getTankTooltip(tile.getTank(), fluid, mouseX, mouseY, guiLeft + 26, guiTop + 15, guiLeft + 38, guiTop + 67);
            if(tooltip != null) {
                this.drawHoveringText(tooltip, mouseX-guiLeft, mouseY-guiTop);
            }
        }

        buttonEmptyTank.enabled = Util.isShiftKeyDown();

        //Tool Tips (For button)
        if (buttonEmptyTank.isMouseOver()) { // Tells you if the button is hovered by mouse
            String[] desc = { TextFormatting.RED + I18n.format("tio.gui.SO.toolTips.button_head"), TextFormatting.GRAY + I18n.format("tio.gui.SO.toolTips.button_info") };
            List<String> temp = Arrays.asList(desc);
            drawHoveringText(temp, mouseX - guiLeft, mouseY - guiTop, fontRenderer);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id){
            case BUTTON_EMPTY_TANK:
                tile.emptyTank();
                NetworkHandler.sendToServer(new MessageEmptyTank(tile.getPos()));
                break;
            default:
                break;
        }
    }

    /**
     * The method is borrowed from slimeknights.tconstruct.smeltery.client.GuiSmeltery.class
     * Copied by GKB
     * 2017/9/25 21:53 UTC+8
     */
    private static List<String> getTankTooltip(IFluidTank tank, FluidStack fluid, int mouseX, int mouseY, int xmin, int ymin, int xmax, int ymax) {

        // Liquids
        if(xmin <= mouseX && mouseX < xmax && ymin <= mouseY && mouseY < ymax) {
            FluidStack hovered = fluid;
            List<String> text = Lists.newArrayList();

            Consumer<Integer> stringFn = Util.isShiftKeyDown() ? (i) -> GuiUtil.amountToString(i, text) : (i) -> GuiUtil.amountToIngotString(i, text);

            if(hovered == null) {
                int usedCap = tank.getFluidAmount();
                int maxCap = tank.getCapacity();
                text.add(TextFormatting.WHITE + Util.translate("gui.smeltery.capacity"));
                stringFn.accept(maxCap);
                text.add(Util.translateFormatted("gui.smeltery.capacity_available"));
                stringFn.accept(maxCap - usedCap);
                text.add(Util.translateFormatted("gui.smeltery.capacity_used"));
                stringFn.accept(usedCap);
                if(!Util.isShiftKeyDown()) {
                    text.add("");
                    text.add(Util.translate("tooltip.tank.holdShift"));
                }
            }
            else {
                text.add(TextFormatting.WHITE + hovered.getLocalizedName());
                GuiUtil.liquidToString(hovered, text);
            }

            return text;
        }

        return null;
    }
}
