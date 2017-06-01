package tinker_io.gui;

/*import java.util.Arrays;
import java.util.List;*/

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
//import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import tinker_io.TileEntity.fim.FIMTileEntity;
import tinker_io.TileEntity.fim.FIMTileEntity.SpeedUpRatio;
import tinker_io.inventory.ContainerFIM;
import tinker_io.main.Main;

public class FIMGui extends GuiContainer {
	private static final ResourceLocation ASCGuiTextures = new ResourceLocation(Main.MODID, "textures/gui/fuel_input_machine.png");
	private FIMTileEntity tile;
	public World world;

	public FIMGui(InventoryPlayer invPlayer, FIMTileEntity tile) {
		super(new ContainerFIM(invPlayer, tile));
		this.tile = tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		String string = this.tile.hasCustomName() ? this.tile.getName() : I18n.format(this.tile.getName(), new Object[0]);
		//String tip1 = I18n.format("tio.gui.fim.slot1", new Object[0]);
		String tip2 = I18n.format("tio.gui.fim.slot2", new Object[0]);
		

		String warn = TextFormatting.RED+I18n.format("tio.gui.fim.error_message", new Object[0]);
		
		/*int cornerX = (width - xSize) / 2;
        int cornerY = (height - ySize) / 2;*/
		
		this.fontRendererObj.drawString(string, (this.xSize - this.fontRendererObj.getStringWidth(string))/2, 6, 4210752);
		//this.fontRendererObj.drawString(tip1, (this.xSize - 145 - (this.fontRendererObj.getStringWidth(tip1))/2), 9, 4210752);
		this.fontRendererObj.drawString(tip2, (this.xSize - 87 - (this.fontRendererObj.getStringWidth(tip2))/2), 25, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 94, 4210752);
		
		String temperatureInfo = I18n.format("tio.gui.fim.temperature", new Object[0]) + " :";
		this.fontRendererObj.drawString(temperatureInfo, (-55 - (this.fontRendererObj.getStringWidth(temperatureInfo))/2), 14, 4210752);
		
		String currentTempture = tile.getCurrentTempture() + "";
		this.fontRendererObj.drawString(currentTempture, (-55 - (this.fontRendererObj.getStringWidth(currentTempture))/2), 26, 4210752);
		
		double ratio = this.getSpeedUpTimes();
		String msgRatio = TextFormatting.DARK_GREEN + "Ratio : " + ratio;
		this.fontRendererObj.drawString(msgRatio, (-55 - (this.fontRendererObj.getStringWidth(msgRatio))/2), 37, 4210752);
		
		if(!tile.hasFuel()){
			this.fontRendererObj.drawString(warn, (-55 - (this.fontRendererObj.getStringWidth(warn))/2), 49, 4210752);			
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
	        i1 = this.tile.getCookProgressScaled(13);
	        this.drawTexturedModalRect(k + 103, l + 36 + i1, 176, 33 + i1, 13, 13 - i1);
	        
	        /*if(!tile.hasFuel()){
	        	this.drawTexturedModalRect(k + 159, l + 4, 177, 33, 11, 11);	
	        }*/
	        this.drawTexturedModalRect(k - 110, l + 10, 146, 170, 110, 60);
	}
	
	private double getSpeedUpTimes()
	{
		SpeedUpRatio info = tile.getSpeedUpInfo();
		
		return info.ratio;
	}
	
}
