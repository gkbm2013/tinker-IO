package tinker_io.gui;

import org.lwjgl.opengl.GL11;

import tinker_io.TileEntity.OreCrusherTileEntity;
import tinker_io.inventory.ContainerOreCrusher;
import tinker_io.main.Main;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class OreCrusherGui extends GuiContainer{
	private static final ResourceLocation OCGuiTextures = new ResourceLocation(Main.MODID, "textures/gui/OreCrusher.png");
	public World world;
	private OreCrusherTileEntity tileOC;
	
	public OreCrusherGui(InventoryPlayer invPlayer, OreCrusherTileEntity tile) {
		super(new ContainerOreCrusher(invPlayer, tile));
		this.tileOC = tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		String string = "Ore Crusher"; // :p
		
		this.fontRendererObj.drawString(string, (this.xSize - this.fontRendererObj.getStringWidth(string))/2, 6, 4210752);
		
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		 GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.mc.getTextureManager().bindTexture(OCGuiTextures);
	        int k = (this.width - this.xSize) / 2;
	        int l = (this.height - this.ySize) / 2;
	        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	        
	        int i1;
	        i1 = this.tileOC.getCrushProgressScaled(24);
	        this.drawTexturedModalRect(k + 81, l + 35, 176, 14, i1 + 1, 16);
	        
	       /*if(!tileASC.hasFuel()){
	        	this.drawTexturedModalRect(k + 159, l + 4, 177, 33, 11, 11);	
	        }*/
	}

}
