package tinker_io.gui;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;

import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.client.GuiUtil;
import tinker_io.TileEntity.SOTileEntity;
import tinker_io.inventory.ContainerSO;
import tinker_io.main.Main;
import tinker_io.packet.PacketDispatcher;
import tinker_io.packet.VoidLiquidPacket;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
//import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
//import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

public class SOGui extends GuiContainer{

	private static final ResourceLocation SOGuiTextures = new ResourceLocation(Main.MODID, "textures/gui/smart_output.png");
	private SOTileEntity tileSO;
	InventoryPlayer invPlayer2 = null;

	static GuiButton btn0;

	public void initGui(){
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		super.initGui();		

		btn0 = new GuiButton(0, k - 20,  l + ySize - 150, 20, 20, "");
		buttonList.add(btn0);

	}
	
	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	public SOGui(InventoryPlayer invPlayer, SOTileEntity tile) {
		super(new ContainerSO(invPlayer, tile));
		invPlayer2 = invPlayer;
		this.tileSO = tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){

		this.mc.getTextureManager().bindTexture(SOGuiTextures);
		btn0.drawTexturedModalRect(-20,  15, 178, 82, 20, 20);

		String string = this.tileSO.hasCustomName() ? this.tileSO.getName() : I18n.format(this.tileSO.getName(), new Object[0]);
		int outputSize = tileSO.getOutputSize();

		this.fontRenderer.drawString(string, (this.xSize - this.fontRenderer.getStringWidth(string))/2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 94, 4210752);

		this.fontRenderer.drawString(TextFormatting.DARK_GREEN + "Max : " + outputSize, 120, 17, 4210752);

		FluidTankInfo info = tileSO.getInfo();
		//FluidStack Wliquid = info.fluid;

		//		Tool Tips (For tank)
		FluidStack liquid = info.fluid;
		int cornerX = (width - xSize) / 2;
		int cornerY = (height - ySize) / 2;
		if (liquid != null){
			List<String> tooltip = getTankTooltip(tileSO, liquid, mouseX, mouseY, cornerX + 26, cornerY + 15, cornerX + 38, cornerY + 67);
			if(tooltip != null) {
				this.drawHoveringText(tooltip, mouseX-cornerX, mouseY-cornerY);
			}
		}

		//Tool Tips (For button)
		if (btn0.isMouseOver()) { // Tells you if the button is hovered by mouse
			String[] desc = { TextFormatting.RED + I18n.format("tio.gui.SO.toolTips.button_head"), TextFormatting.GRAY + I18n.format("tio.gui.SO.toolTips.button_info") };
			List<String> temp = Arrays.asList(desc);
			drawHoveringText(temp, mouseX - cornerX + 10, mouseY - cornerY + 10, fontRenderer);
		}

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int mouseX, int mouseY) {
		FluidTankInfo info = tileSO.getInfo();
		FluidStack liquid = info.fluid;

		int cornerX = (width - xSize) / 2;
		int cornerY = (height - ySize) / 2;

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(SOGuiTextures);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

		int i1;
		i1 = this.tileSO.getFrozenProgressScaled(24);
		this.drawTexturedModalRect(k + 93, l + 33, 176, 0, i1 + 1, 17);

		//Redstone icon
		//	        int power = tileSO.getWorldObj().getBlockPowerInput(tileSO.xCoord, tileSO.yCoord, tileSO.zCoord);
		//        	int strongestsPower = tileSO.getWorldObj().getStrongestIndirectPower(tileSO.xCoord, tileSO.yCoord, tileSO.zCoord);
		boolean hasPower = tileSO.getWorld().isBlockPowered(tileSO.getPos());

		if(tileSO.hasRedstoneUPG()){
			//        		if(power != 0 && strongestsPower != 0){
			//            		this.drawTexturedModalRect(k + 119, l + 55, 176, 18, 22, 20);
			//            		this.drawTexturedModalRect(k + 92, l + 32, 176, 60, 26, 20);
			//            	}else{
			//            		this.drawTexturedModalRect(k + 120, l + 55, 177, 39, 21, 20);
			//            	}
			if(hasPower) {
				this.drawTexturedModalRect(k + 119, l + 55, 176, 18, 22, 20);
				this.drawTexturedModalRect(k + 92, l + 32, 176, 60, 26, 20);
			} else {
				this.drawTexturedModalRect(k + 120, l + 55, 177, 39, 21, 20);
			}
		}

		//Basin icon
		if(tileSO.hasBasinUPG()){
			this.drawTexturedModalRect(k + 77, l + 52, 177, 104, 16, 16);
		}



		int liquidAmount = tileSO.getLiquidAmount(52);

		//Get Liquid Icon
		//1.7.10 No longer use
		/*IIcon renderIndex = Blocks.lava.getIcon(0, 0);
			if (liquid != null){
				 renderIndex = liquid.getFluid().getStill();
	        }*/


		//Draw Liquid (For tank)
		this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE); 
		//this.drawLiquidRect(cornerX + 26, cornerY + 15 + 52 - liquidAmount, renderIndex, 12, liquidAmount);
		if(liquid != null){
			GuiUtil.renderTiledFluid(cornerX + 26, cornerY + 15 + 52 - liquidAmount, 12, liquidAmount, this.zLevel, liquid);
		}
		//Control the button of void liquid
		if(isShiftKeyDown()){
			btn0.enabled = true;
		}else{
			btn0.enabled = false;
		}


	}

	@Override
	public void actionPerformed(GuiButton button){
		switch(button.id){
		case 0:

			int[] coord = new int[3];

			coord[0] = tileSO.getPos().getX();
			coord[1] = tileSO.getPos().getY();
			coord[2] = tileSO.getPos().getZ();

			tileSO.voidLiquid();
			PacketDispatcher.sendToServer(new VoidLiquidPacket(coord));
			break;
		default:
		}

	}

	@Override
	public void onGuiClosed ()
	{
		super.onGuiClosed();
		// tileSO.getWorldObj().markBlockForUpdate(tileSO.xCoord, tileSO.yCoord, tileSO.zCoord);
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

