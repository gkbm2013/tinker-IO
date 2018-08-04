package tinker_io.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import tinker_io.TinkerIO;
import tinker_io.registry.BlockRegistry;
import tinker_io.tileentity.TileEntityFuelInputMachine;

public class GuiFuelInputMachine extends GuiContainer {

    private InventoryPlayer playerInv;
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(TinkerIO.MOD_ID, "textures/gui/fuel_input_machine.png");
    public static final int WIDTH = 176;
    public static final int HEIGHT = 166;
    private TileEntityFuelInputMachine tile;

    public GuiFuelInputMachine(Container container, TileEntityFuelInputMachine tile, InventoryPlayer playerInv) {
        super(container);
        this.playerInv = playerInv;

        this.xSize = WIDTH;
        this.ySize = HEIGHT;

        this.tile = tile;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();
        mc.getTextureManager().bindTexture(BG_TEXTURE);

        //Main GUI
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        //Left hand side panel
        drawTexturedModalRect(guiLeft - 110, guiTop + 10, 146, 170, 110, 60);

        //Burning Progress
        int progress = tile.getScaledBurningCount(13);
//        System.out.println(progress);
        if(tile.getCurrentSolidFuelTemp() == 0)
            progress = 13;

        drawTexturedModalRect(guiLeft + 103, guiTop + 36 + progress, 176, 33 + progress, 13, 13 - progress);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = I18n.format(BlockRegistry.fuelInputMachine.getUnlocalizedName() + ".name");
        fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
        fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
    }
}
