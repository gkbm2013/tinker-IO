package tinker_io.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Iterator;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import slimeknights.tconstruct.TConstruct;
import tinker_io.TileEntity.SOTileEntity;
import tinker_io.inventory.ContainerSO;
import tinker_io.main.Main;
import tinker_io.packet.PacketDispatcher;
import tinker_io.packet.VoidLiquidPacket;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.EnumChatFormatting;
//import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
//import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class SOGui extends GuiContainer{

	private static final ResourceLocation SOGuiTextures = new ResourceLocation(Main.MODID, "textures/gui/SmartOutput.png");
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
		
		this.fontRendererObj.drawString(string, (this.xSize - this.fontRendererObj.getStringWidth(string))/2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 94, 4210752);
		
		this.fontRendererObj.drawString(EnumChatFormatting.DARK_GREEN + "Max : " + outputSize, 120, 17, 4210752);
		
		FluidTankInfo[] info = tileSO.getTankInfo();
		FluidStack Wliquid = info[0].fluid;
		
//		Tool Tips (For tank)
		FluidStack liquid = info[0].fluid;
		int cornerX = (width - xSize) / 2;
        int cornerY = (height - ySize) / 2;
        if (liquid != null){
        	if(mouseX >= cornerX + 26 && mouseX <= cornerX + 38 && mouseY <= cornerY + 67 && mouseY >= cornerY + 67 - 52/*liquidAmount*/){
	        	drawFluidStackTooltip(liquid, mouseX-cornerX, mouseY-cornerY);
	        }
        }
        
      //Tool Tips (For button)
        if (btn0.isMouseOver()) { // Tells you if the button is hovered by mouse
            String[] desc = { EnumChatFormatting.RED + StatCollector.translateToLocal("tio.gui.SO.toolTips.button_head"), EnumChatFormatting.GRAY + StatCollector.translateToLocal("tio.gui.SO.toolTips.button_info") };
            @SuppressWarnings("rawtypes")
            List temp = Arrays.asList(desc);
            drawHoveringText(temp, mouseX - cornerX + 10, mouseY - cornerY + 10, fontRendererObj);
        }
		
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int mouseX, int mouseY) {
		FluidTankInfo[] info = tileSO.getTankInfo();
		FluidStack liquid = info[0].fluid;

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
        	
        	
	        
			int liquidAmount = tileSO.getLiquidAmount(52);
			
			//Get Liquid Icon
			IIcon renderIndex = Blocks.lava.getIcon(0, 0);
			if (liquid != null){
				 renderIndex = liquid.getFluid().getStill();
	        }
	        
	        //Draw Liquid (For tank)
			this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture); 
	        this.drawLiquidRect(cornerX + 26, cornerY + 15 + 52 - liquidAmount, renderIndex, 12, liquidAmount);
	        
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
	
	
	//The code below are copy from Tinker's Construct AdptiveSmelteryGui.class
	/*
	 *2015/3/29
	 */
	
	protected void drawFluidStackTooltip (FluidStack par1ItemStack, int par2, int par3)
    {
        this.zLevel = 100;
        List list = getLiquidTooltip(par1ItemStack, this.mc.gameSettings.advancedItemTooltips);

        for (int k = 0; k < list.size(); ++k)
        {
            list.set(k, EnumChatFormatting.GRAY + (String) list.get(k));
        }

        this.drawToolTip(list, par2, par3);
        this.zLevel = 0;
    }
	
	public void drawLiquidRect (int startU, int startV, IIcon par3Icon, int endU, int endV)
    {
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(startU + 0, startV + endV, this.zLevel, par3Icon.getMinU(), par3Icon.getMaxV());// Bottom left
        tessellator.addVertexWithUV(startU + endU, startV + endV, this.zLevel, par3Icon.getMaxU(), par3Icon.getMaxV());// Bottom right
        tessellator.addVertexWithUV(startU + endU, startV + 0, this.zLevel, par3Icon.getMaxU(), par3Icon.getMinV());// Top right
        tessellator.addVertexWithUV(startU + 0, startV + 0, this.zLevel, par3Icon.getMinU(), par3Icon.getMinV()); // Top left
        tessellator.draw();
    }
	
	public List getLiquidTooltip (FluidStack liquid, boolean par2)
    {
        ArrayList list = new ArrayList();
        if (liquid.getFluidID() == -37)
        {
            //list.add("\u00A7f" + StatCollector.translateToLocal("gui.smeltery1"));
        	list.add("\u00A7f" + I18n.format("gui.smeltery1", new Object[0]));
            list.add("mB: " + liquid.amount);
        }
        else
        {
            //String name = StatCollector.translateToLocal(FluidRegistry.getFluidName(liquid));
        	String name = I18n.format("fluid."+FluidRegistry.getFluidName(liquid), new Object[0]);
            list.add("\u00A7f" + name);
            if (name.equals("liquified emerald"))
            {
                list.add("Emeralds: " + liquid.amount / 320f);
            }
            else if (name.contains("Molten"))
            {
                int ingots = liquid.amount / TConstruct.ingotLiquidValue;
                if (ingots > 0)
                    list.add("Ingots: " + ingots);
                int mB = liquid.amount % TConstruct.ingotLiquidValue;
                if (mB > 0)
                {
                    int nuggets = mB / TConstruct.nuggetLiquidValue;
                    int junk = (mB % TConstruct.nuggetLiquidValue);
                    if (nuggets > 0)
                        list.add("Nuggets: " + nuggets);
                    if (junk > 0)
                        list.add("mB: " + junk);
                }
            }
            else if (name.equals("Seared Stone"))
            {
                int ingots = liquid.amount / TConstruct.ingotLiquidValue;
                if (ingots > 0)
                    list.add("Blocks: " + ingots);
                int mB = liquid.amount % TConstruct.ingotLiquidValue;
                if (mB > 0)
                    list.add("mB: " + mB);
            }
            else if (name.equals("Molten Glass"))
            {
                int blocks = liquid.amount / 1000;
                if (blocks > 0)
                    list.add("Blocks: " + blocks);
                int panels = (liquid.amount % 1000) / 250;
                if (panels > 0)
                    list.add("Panels: " + panels);
                int mB = (liquid.amount % 1000) % 250;
                if (mB > 0)
                    list.add("mB: " + mB);
            }
            else
            {
                list.add("mB: " + liquid.amount);
            }
        }
        return list;
    }
	
	protected void drawToolTip (List par1List, int par2, int par3)
    {
        if (!par1List.isEmpty())
        {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
            Iterator iterator = par1List.iterator();

            while (iterator.hasNext())
            {
                String s = (String) iterator.next();
                int l = this.fontRendererObj.getStringWidth(s);

                if (l > k)
                {
                    k = l;
                }
            }

            int i1 = par2 + 12;
            int j1 = par3 - 12;
            int k1 = 8;

            if (par1List.size() > 1)
            {
                k1 += 2 + (par1List.size() - 1) * 10;
            }

            if (i1 + k > this.width)
            {
                i1 -= 28 + k;
            }

            if (j1 + k1 + 6 > this.height)
            {
                j1 = this.height - k1 - 6;
            }

            this.zLevel = 300.0F;
            itemRender.zLevel = 300.0F;
            int l1 = -267386864;
            this.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            this.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
            this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 1347420415;
            int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
            this.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
            this.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);

            for (int k2 = 0; k2 < par1List.size(); ++k2)
            {
                String s1 = (String) par1List.get(k2);
                this.fontRendererObj.drawStringWithShadow(s1, i1, j1, -1);

                if (k2 == 0)
                {
                    j1 += 2;
                }

                j1 += 10;
            }

            this.zLevel = 0.0F;
            itemRender.zLevel = 0.0F;
        }
    }
}

