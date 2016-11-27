package tinker_io.gui;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.client.GuiUtil;
import slimeknights.tconstruct.library.client.RenderUtil;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.library.smeltery.ICastingRecipe;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
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
import net.minecraft.item.ItemStack;
//import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.Fluid;
//import net.minecraftforge.common.util.ForgeDirection;
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
		
		this.fontRendererObj.drawString(TextFormatting.DARK_GREEN + "Max : " + outputSize, 120, 17, 4210752);
		
		FluidTankInfo info = tileSO.getInfo();
		//FluidStack Wliquid = info.fluid;
		
//		Tool Tips (For tank)
		FluidStack liquid = info.fluid;
		int cornerX = (width - xSize) / 2;
        int cornerY = (height - ySize) / 2;
        if (liquid != null){
        	List<String> text = Lists.newArrayList();
        	text.add(TextFormatting.WHITE.toString() + liquid.getLocalizedName());
        	liquidToString(liquid, text);
            //text.add(TextFormatting.GRAY.toString() + liquid.amount + " " + Util.translate("gui.smeltery.liquid.millibucket"));
        	if(mouseX >= cornerX + 26 && mouseX <= cornerX + 38 && mouseY <= cornerY + 67 && mouseY >= cornerY + 67 - 52/*liquidAmount*/){
	        	//1.7.10 // drawFluidStackTooltip(liquid, mouseX-cornerX, mouseY-cornerY);
        		this.drawHoveringText(text, mouseX-cornerX, mouseY-cornerY);
	        }
        }
        
      //Tool Tips (For button)
        if (btn0.isMouseOver()) { // Tells you if the button is hovered by mouse
            String[] desc = { TextFormatting.RED + I18n.format("tio.gui.SO.toolTips.button_head"), TextFormatting.GRAY + I18n.format("tio.gui.SO.toolTips.button_info") };
            @SuppressWarnings("rawtypes")
            List temp = Arrays.asList(desc);
            drawHoveringText(temp, mouseX - cornerX + 10, mouseY - cornerY + 10, fontRendererObj);
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
	
	
	//The code below were copied from Tinker's Construct SmelteryGui.class
	/*
	 * Copied by GKB
	 * 2016/2/11
	 * 2016/8/3 Update
	 */
	
	/* Fluid amount displays */
	private static Map<Fluid, List<FluidGuiEntry>> fluidGui = Maps.newHashMap();

	  public void liquidToString(FluidStack fluid, List<String> text) {
	    int amount = fluid.amount;
	    if(!Util.isShiftKeyDown()) {
	      List<FluidGuiEntry> entries = fluidGui.get(fluid.getFluid());
	      if(entries == null) {
	        entries = calcFluidGuiEntries(fluid.getFluid());
	        fluidGui.put(fluid.getFluid(), entries);
	      }

	      for(FluidGuiEntry entry : entries) {
	        amount = calcLiquidText(amount, entry.amount, entry.getText(), text);
	      }
	    }

	    // standard display: bucket amounts
	    // we go up to kiloBuckets because we can
	    amount = calcLiquidText(amount, 1000000, Util.translate("gui.smeltery.liquid.kilobucket"), text);
	    amount = calcLiquidText(amount, 1000, Util.translate("gui.smeltery.liquid.bucket"), text);
	    calcLiquidText(amount, 1, Util.translate("gui.smeltery.liquid.millibucket"), text);
	  }

	  private List<FluidGuiEntry> calcFluidGuiEntries(Fluid fluid) {
	    List<FluidGuiEntry> list = Lists.newArrayList();

	    // go through all casting recipes for the fluids and check for known "units" like blocks, ingots,...
	    for(ICastingRecipe irecipe : TinkerRegistry.getAllBasinCastingRecipes()) {
	      if(irecipe instanceof CastingRecipe) {
	        CastingRecipe recipe = (CastingRecipe) irecipe;
	        // search for a block recipe
	        if(recipe.getFluid().getFluid() == fluid && recipe.cast == null) {
	          // it's a block that is cast solely from the material, using no cast, therefore it's a block made out of the material
	          list.add(new FluidGuiEntry(recipe.getFluid().amount, "gui.smeltery.liquid.block"));
	        }
	      }
	    }
	    // table casting
	    for(ICastingRecipe irecipe : TinkerRegistry.getAllTableCastingRecipes()) {
	      if(irecipe instanceof CastingRecipe) {
	        CastingRecipe recipe = (CastingRecipe) irecipe;
	        if(recipe.getFluid().getFluid() == fluid && recipe.cast != null) {
	          // nugget
	          if(recipe.cast.matches(new ItemStack[]{TinkerSmeltery.castNugget}) != null) {
	            list.add(new FluidGuiEntry(recipe.getFluid().amount, "gui.smeltery.liquid.nugget"));
	          }
	          // ingot
	          if(recipe.cast.matches(new ItemStack[]{TinkerSmeltery.castIngot}) != null) {
	            list.add(new FluidGuiEntry(recipe.getFluid().amount, "gui.smeltery.liquid.ingot"));
	          }
	          // gem
	          if(recipe.cast.matches(new ItemStack[]{TinkerSmeltery.castGem}) != null) {
	            list.add(new FluidGuiEntry(recipe.getFluid().amount, "gui.smeltery.liquid.gem"));
	          }
	        }
	      }
	    }

	    // sort by amount descending because the order in which they're accessed is important since it changes the remaining value during processing
	    Collections.sort(list, new Comparator<FluidGuiEntry>() {
	      @Override
	      public int compare(FluidGuiEntry o1, FluidGuiEntry o2) {
	        return o2.amount - o1.amount;
	      }
	    });

	    return ImmutableList.copyOf(list);
	  }

	  private int calcLiquidText(int amount, int divider, String unit, List<String> text) {
	    int full = amount / divider;
	    if(full > 0) {
	      text.add(String.format("%d %s%s", full, TextFormatting.GRAY, unit));
	    }

	    return amount % divider;
	  }

	  private static class FluidGuiEntry {

	    public final int amount;
	    public final String unlocName;

	    private FluidGuiEntry(int amount, String unlocName) {
	      this.amount = amount;
	      this.unlocName = unlocName;
	    }

	    public String getText() {
	      return Util.translate(unlocName);
	    }
	  }
}

