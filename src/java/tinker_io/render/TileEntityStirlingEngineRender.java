package tinker_io.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import tinker_io.TileEntity.StirlingEngineTileEntity;
import tinker_io.main.Main;
import tinker_io.model.StirlingEngineModel;

public class TileEntityStirlingEngineRender extends TileEntitySpecialRenderer<StirlingEngineTileEntity>{
	
	//The model of your block
    private final StirlingEngineModel model;
    
    public TileEntityStirlingEngineRender() {
            this.model = new StirlingEngineModel();
    }
    
    @Override
    public void render(StirlingEngineTileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha){
		int meta = te.getBlockMetadata();
		
		model.angel = te.angel / 10;
		
		//System.out.println("x");
		
		//The PushMatrix tells the renderer to "start" doing something.
        GL11.glPushMatrix();
		//This is setting the initial location.
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		//This is the texture of your block. It's pathed to be the same place as your other blocks here.
        ResourceLocation textures = (new ResourceLocation(Main.MODID, "textures/models/special_render/stirling_engine.png")); 
		//binding the textures
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);

		//This rotation part is very important! Without it, your model will render upside-down! And for some reason you DO need PushMatrix again!                       
        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        
        //Rotate by meta
        switch(meta) { 
        case 2: 
        	GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
            break; 
        case 5: 
        	GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
            break; 
        case 3: 
        	GL11.glRotatef(270F, 0.0F, 1.0F, 0.0F); 
            break; 
        }
        
        
		//A reference to your Model file. Again, very important.
        this.model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		//Tell it to stop rendering for both the PushMatrix's
        GL11.glPopMatrix();
        GL11.glPopMatrix();
		
	}


}
