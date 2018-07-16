package tinker_io.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import tinker_io.TinkerIO;
import tinker_io.registry.BlockRegistry;

public class GuiFuelInputMachine extends GuiContainer {

    private InventoryPlayer playerInv;
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(TinkerIO.MOD_ID, "textures/gui/fuel_input_machine.png");
    public static final int WIDTH = 176;
    public static final int HEIGHT = 166;

    public GuiFuelInputMachine(Container container, InventoryPlayer playerInv) {
        super(container);
        this.playerInv = playerInv;

        this.xSize = WIDTH;
        this.ySize = HEIGHT;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = I18n.format(BlockRegistry.blockFuelInputMachine.getUnlocalizedName() + ".name");
        fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
        fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
    }
}
