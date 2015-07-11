package tinker_io.gui;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import tinker_io.TileEntity.FIMTileEntity;
import tinker_io.inventory.ContainerFIM;
import tinker_io.main.Main;

public class FIMGui extends GuiContainer {
	private static final ResourceLocation ASCGuiTextures = new ResourceLocation(Main.MODID, "textures/gui/ASC.png");
	private FIMTileEntity tileASC;
	public World world;

	public FIMGui(InventoryPlayer invPlayer, FIMTileEntity tile) {
		super(new ContainerFIM(invPlayer, tile));
		this.tileASC = tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2){
		String string = this.tileASC.hasCustomInventoryName() ? this.tileASC.getInventoryName() : I18n.format(this.tileASC.getInventoryName(), new Object[0]);
		//String tip1 = I18n.format("tio.gui.fim.slot1", new Object[0]);
		String tip2 = I18n.format("tio.gui.fim.slot2", new Object[0]);
		
		String connectInfo = I18n.format("tio.gui.fim.direction", new Object[0]) + " :";
		String connectInfoDiraction = tileASC.getDirection();
		String warn = EnumChatFormatting.RED+I18n.format("tio.gui.fim.error_message", new Object[0]);
		int max = tileASC.getInputSize();
		String msgMax = EnumChatFormatting.DARK_GREEN + "Max : " + max;
		
		int cornerX = (width - xSize) / 2;
        int cornerY = (height - ySize) / 2;
		
		this.fontRendererObj.drawString(string, (this.xSize - this.fontRendererObj.getStringWidth(string))/2, 6, 4210752);
		//this.fontRendererObj.drawString(tip1, (this.xSize - 145 - (this.fontRendererObj.getStringWidth(tip1))/2), 9, 4210752);
		this.fontRendererObj.drawString(tip2, (this.xSize - 87 - (this.fontRendererObj.getStringWidth(tip2))/2), 25, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 94, 4210752);
		
		this.fontRendererObj.drawString(connectInfo, (-55 - (this.fontRendererObj.getStringWidth(connectInfo))/2), 14, 4210752);
		this.fontRendererObj.drawString(connectInfoDiraction, (-55 - (this.fontRendererObj.getStringWidth(connectInfoDiraction))/2), 26, 4210752);
		if(max == 2048){
			msgMax = EnumChatFormatting.DARK_GREEN + "Max : \u221e";
		}
		this.fontRendererObj.drawString(msgMax, (-55 - (this.fontRendererObj.getStringWidth(msgMax))/2), 42, 4210752);
		
		if(!tileASC.hasFuel()){
			this.fontRendererObj.drawString(warn, (-55 - (this.fontRendererObj.getStringWidth(warn))/2), 54, 4210752);			
		}
		
		
		
		/*if(!tileASC.hasFuel() && par1 >= cornerX +159 && par1 <= cornerX + 170 && par2 >= cornerY + 4 && par2 <= cornerY + 15 ){
			String[] info = {EnumChatFormatting.RED+I18n.format("tio.gui.fim.error_message", new Object[0])};
            @SuppressWarnings("rawtypes")
            List temp = Arrays.asList(info);
            drawHoveringText(temp, par1 - cornerX, par2 - cornerY, fontRendererObj);
        }*/
		//System.out.println(cornerX);
		
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		 GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.mc.getTextureManager().bindTexture(ASCGuiTextures);
	        int k = (this.width - this.xSize) / 2;
	        int l = (this.height - this.ySize) / 2;
	        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	        
	        int i1;
	        i1 = this.tileASC.getCookProgressScaled(24);
	        this.drawTexturedModalRect(k + 98, l + 34, 176, 14, i1 + 1, 16);
	        
	        /*if(!tileASC.hasFuel()){
	        	this.drawTexturedModalRect(k + 159, l + 4, 177, 33, 11, 11);	
	        }*/
	        this.drawTexturedModalRect(k - 110, l + 10, 146, 170, 110, 60);

	}



}
