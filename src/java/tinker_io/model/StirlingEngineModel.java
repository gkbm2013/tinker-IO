package tinker_io.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * StirlingEngine - Undefined
 * Created using Tabula 5.1.0
 */
public class StirlingEngineModel extends ModelBase {
    //major
	ModelRenderer major1;
    ModelRenderer major2;
    ModelRenderer major3;
    ModelRenderer major4;
    ModelRenderer major5;
    ModelRenderer major6;
    ModelRenderer major7;
    ModelRenderer major8;
    ModelRenderer major9;
    ModelRenderer major10;
    ModelRenderer major11;
    ModelRenderer major12;
    ModelRenderer major13;
    ModelRenderer major14;
    ModelRenderer major15;
    ModelRenderer major16;
    ModelRenderer major17;
    ModelRenderer major18;
    ModelRenderer major19;
    ModelRenderer major20;
    ModelRenderer major21;
    ModelRenderer major22;
    ModelRenderer major23;
    ModelRenderer major24;
    
    //plate
    ModelRenderer plate1;
    ModelRenderer plate2;
    
    //pool
    ModelRenderer pool1;
    ModelRenderer pool2;
    
    //wheel
    ModelRenderer wheel1;
    ModelRenderer wheel2;
    ModelRenderer wheel3;
    ModelRenderer wheel4;
    ModelRenderer wheel5;
    ModelRenderer wheel6;
    ModelRenderer wheel7;
    ModelRenderer wheel8;
    ModelRenderer wheel9;
    ModelRenderer wheel10;
    ModelRenderer wheel11;
    ModelRenderer wheel12;
    ModelRenderer wheel13;
    ModelRenderer wheel14;
    ModelRenderer wheel15;
    ModelRenderer wheel16;
    ModelRenderer wheel17;
    ModelRenderer wheel18;
    ModelRenderer wheel19;
    ModelRenderer wheel20;
    ModelRenderer wheel21;
    ModelRenderer wheel22;
    ModelRenderer wheel23;
    ModelRenderer wheel24;
    ModelRenderer wheel25;
    
    //wraper
    ModelRenderer majorModelWraper;
    ModelRenderer plateModelWraper;
    ModelRenderer poolModelWraper;
    ModelRenderer wheelModelWraper;

    public StirlingEngineModel() {
    	textureWidth = 64;
        textureHeight = 50;
        
        //Major        
          major1 = new ModelRenderer(this, 3, 13);
          major1.addBox(-8F, 0F, -2F, 16, 1, 4);
          major1.setRotationPoint(0F, 18F, 0F);
          major1.setTextureSize(64, 50);
          major1.mirror = true;
          setRotateAngle(major1, 0F, 0F, 0F);
          major2 = new ModelRenderer(this, 3, 19);
          major2.addBox(-7F, 0F, -4F, 14, 1, 2);
          major2.setRotationPoint(0F, 18F, 0F);
          major2.setTextureSize(64, 50);
          major2.mirror = true;
          setRotateAngle(major2, 0F, 0F, 0F);
          major3 = new ModelRenderer(this, 3, 23);
          major3.addBox(-7F, 0F, 2F, 14, 1, 2);
          major3.setRotationPoint(0F, 18F, 0F);
          major3.setTextureSize(64, 50);
          major3.mirror = true;
          setRotateAngle(major3, 0F, 0F, 0F);
          major4 = new ModelRenderer(this, 3, 27);
          major4.addBox(-6F, 0F, 4F, 12, 1, 2);
          major4.setRotationPoint(0F, 18F, 0F);
          major4.setTextureSize(64, 50);
          major4.mirror = true;
          setRotateAngle(major4, 0F, 0F, 0F);
          major5 = new ModelRenderer(this, 4, 10);
          major5.addBox(-4F, 0F, 6F, 8, 1, 1);
          major5.setRotationPoint(0F, 18F, 0F);
          major5.setTextureSize(64, 50);
          major5.mirror = true;
          setRotateAngle(major5, 0F, 0F, 0F);
          major6 = new ModelRenderer(this, 36, 24);
          major6.addBox(-2F, 0F, 7F, 4, 1, 1);
          major6.setRotationPoint(0F, 18F, 0F);
          major6.setTextureSize(64, 50);
          major6.mirror = true;
          setRotateAngle(major6, 0F, 0F, 0F);
          major7 = new ModelRenderer(this, 36, 27);
          major7.addBox(-6F, 0F, -6F, 12, 1, 2);
          major7.setRotationPoint(0F, 18F, 0F);
          major7.setTextureSize(64, 50);
          major7.mirror = true;
          setRotateAngle(major7, 0F, 0F, 0F);
          major8 = new ModelRenderer(this, 36, 21);
          major8.addBox(-4F, 0F, -7F, 8, 1, 1);
          major8.setRotationPoint(0F, 18F, 0F);
          major8.setTextureSize(64, 50);
          major8.mirror = true;
          setRotateAngle(major8, 0F, 0F, 0F);
          major9 = new ModelRenderer(this, 48, 24);
          major9.addBox(-2F, 0F, -8F, 4, 1, 1);
          major9.setRotationPoint(0F, 18F, 0F);
          major9.setTextureSize(64, 50);
          major9.mirror = true;
          setRotateAngle(major9, 0F, 0F, 0F);
          major10 = new ModelRenderer(this, 3, 13);
          major10.addBox(-8F, 0F, -2F, 16, 1, 4);
          major10.setRotationPoint(0F, 23F, 0F);
          major10.setTextureSize(64, 50);
          major10.mirror = true;
          setRotateAngle(major10, 0F, 0F, 0F);
          major11 = new ModelRenderer(this, 3, 19);
          major11.addBox(-7F, 0F, -4F, 14, 1, 2);
          major11.setRotationPoint(0F, 23F, 0F);
          major11.setTextureSize(64, 50);
          major11.mirror = true;
          setRotateAngle(major11, 0F, 0F, 0F);
          major12 = new ModelRenderer(this, 3, 23);
          major12.addBox(-7F, 0F, 2F, 14, 1, 2);
          major12.setRotationPoint(0F, 23F, 0F);
          major12.setTextureSize(64, 50);
          major12.mirror = true;
          setRotateAngle(major12, 0F, 0F, 0F);
          major13 = new ModelRenderer(this, 3, 27);
          major13.addBox(-6F, 0F, 4F, 12, 1, 2);
          major13.setRotationPoint(0F, 23F, 0F);
          major13.setTextureSize(64, 50);
          major13.mirror = true;
          setRotateAngle(major13, 0F, 0F, 0F);
          major14 = new ModelRenderer(this, 4, 10);
          major14.addBox(-4F, 0F, 6F, 8, 1, 1);
          major14.setRotationPoint(0F, 23F, 0F);
          major14.setTextureSize(64, 50);
          major14.mirror = true;
          setRotateAngle(major14, 0F, 0F, 0F);
          major15 = new ModelRenderer(this, 36, 24);
          major15.addBox(-2F, 0F, 7F, 4, 1, 1);
          major15.setRotationPoint(0F, 23F, 0F);
          major15.setTextureSize(64, 50);
          major15.mirror = true;
          setRotateAngle(major15, 0F, 0F, 0F);
          major16 = new ModelRenderer(this, 36, 27);
          major16.addBox(-6F, 0F, -6F, 12, 1, 2);
          major16.setRotationPoint(0F, 23F, 0F);
          major16.setTextureSize(64, 50);
          major16.mirror = true;
          setRotateAngle(major16, 0F, 0F, 0F);
          major17 = new ModelRenderer(this, 36, 21);
          major17.addBox(-4F, 0F, -7F, 8, 1, 1);
          major17.setRotationPoint(0F, 23F, 0F);
          major17.setTextureSize(64, 50);
          major17.mirror = true;
          setRotateAngle(major17, 0F, 0F, 0F);
          major18 = new ModelRenderer(this, 48, 24);
          major18.addBox(-2F, 0F, -8F, 4, 1, 1);
          major18.setRotationPoint(0F, 23F, 0F);
          major18.setTextureSize(64, 50);
          major18.mirror = true;
          setRotateAngle(major18, 0F, 0F, 0F);
          major19 = new ModelRenderer(this, 0, 0);
          major19.addBox(0F, 0F, 0F, 1, 4, 1);
          major19.setRotationPoint(4F, 19F, 4F);
          major19.setTextureSize(64, 50);
          major19.mirror = true;
          setRotateAngle(major19, 0F, 0F, 0F);
          major20 = new ModelRenderer(this, 0, 0);
          major20.addBox(0F, 0F, 0F, 1, 4, 1);
          major20.setRotationPoint(-5F, 19F, 4F);
          major20.setTextureSize(64, 50);
          major20.mirror = true;
          setRotateAngle(major20, 0F, 0F, 0F);
          major21 = new ModelRenderer(this, 0, 0);
          major21.addBox(0F, 0F, 0F, 1, 4, 1);
          major21.setRotationPoint(-5F, 19F, -5F);
          major21.setTextureSize(64, 50);
          major21.mirror = true;
          setRotateAngle(major21, 0F, 0F, 0F);
          major22 = new ModelRenderer(this, 0, 0);
          major22.addBox(0F, 0F, 0F, 1, 4, 1);
          major22.setRotationPoint(4F, 19F, -5F);
          major22.setTextureSize(64, 50);
          major22.mirror = true;
          setRotateAngle(major22, 0F, 0F, 0F);
          major23 = new ModelRenderer(this, 0, 0);
          major23.addBox(0F, 0F, 0F, 1, 4, 1);
          major23.setRotationPoint(0F, 14F, -1F);
          major23.setTextureSize(64, 50);
          major23.mirror = true;
          setRotateAngle(major23, 0F, 0F, 0F);
          major24 = new ModelRenderer(this, 0, 0);
          major24.addBox(0F, 0F, 0F, 1, 3, 3);
          major24.setRotationPoint(0F, 11F, -2F);
          major24.setTextureSize(64, 50);
          major24.mirror = true;
          setRotateAngle(major24, 0F, 0F, 0F);
          
          majorModelWraper = new ModelRenderer(this, 0, 0);
          majorModelWraper.setRotationPoint(0F, 0F, 0F);
          majorModelWraper.addChild(major1);
          majorModelWraper.addChild(major2);
          majorModelWraper.addChild(major3);
          majorModelWraper.addChild(major4);
          majorModelWraper.addChild(major5);
          majorModelWraper.addChild(major6);
          majorModelWraper.addChild(major7);
          majorModelWraper.addChild(major8);
          majorModelWraper.addChild(major9);
          majorModelWraper.addChild(major10);
          majorModelWraper.addChild(major11);
          majorModelWraper.addChild(major12);
          majorModelWraper.addChild(major13);
          majorModelWraper.addChild(major14);
          majorModelWraper.addChild(major15);
          majorModelWraper.addChild(major16);
          majorModelWraper.addChild(major17);
          majorModelWraper.addChild(major18);
          majorModelWraper.addChild(major19);
          majorModelWraper.addChild(major20);
          majorModelWraper.addChild(major21);
          majorModelWraper.addChild(major22);
          majorModelWraper.addChild(major23);
          majorModelWraper.addChild(major24);
          
          //Plate
          plate1 = new ModelRenderer(this, 32, 38);
          plate1.addBox(-4F, 0F, -4F, 8, 1, 8);
          plate1.setRotationPoint(0F, 4F, 0F);
          plate1.setTextureSize(64, 50);
          plate1.mirror = true;
          setRotateAngle(plate1, 0F, 0F, 0F);
          plate2 = new ModelRenderer(this, 32, 38);
          plate2.addBox(0F, 0F, 0F, 1, 4, 1);
          plate2.setRotationPoint(0F, 0F, -1F);
          plate2.setTextureSize(64, 50);
          plate2.mirror = true;
          setRotateAngle(plate2, 0F, 0F, 0F);
          
          plateModelWraper = new ModelRenderer(this, 32, 38);
          plateModelWraper.setRotationPoint(0, 18, 0);
          plateModelWraper.addChild(plate1);
          plateModelWraper.addChild(plate2);
          
          //pool
          pool1 = new ModelRenderer(this, 3, 38);
          pool1.addBox(0F, 0F, 0F, 2, 3, 3);
          pool1.setRotationPoint(2F, -2F, -2F);
          pool1.setTextureSize(64, 50);
          pool1.mirror = true;
          setRotateAngle(pool1, 0F, 0F, 0F);
          pool2 = new ModelRenderer(this, 3, 38);
          pool2.addBox(0F, -1F, 0F, 1, 6, 1);
          pool2.setRotationPoint(2F, -7F, -1F);
          pool2.setTextureSize(64, 50);
          pool2.mirror = true;
          setRotateAngle(pool2, 0F, 0F, 0F);
          
          poolModelWraper = new ModelRenderer(this, 3, 38);
          poolModelWraper.setRotationPoint(0, 19, 0);
          poolModelWraper.addChild(pool1);
          poolModelWraper.addChild(pool2);
          
          //wheel
          float wheelXTransport = 0F;
          float wheelYTransport = -0.5F;
          float wheelZTransport = 0.5F;
          
          wheel1 = new ModelRenderer(this, 12, 0);
          wheel1.addBox(0F, 0F, -3F, 1, 1, 5);
          wheel1.setRotationPoint(1F+wheelXTransport, 5F+wheelYTransport, 0F+wheelZTransport);
          wheel1.setTextureSize(64, 50);
          wheel1.mirror = true;
          setRotateAngle(wheel1, 0F, 0F, 0F);
          wheel2 = new ModelRenderer(this, 12, 0);
          wheel2.addBox(0F, 0F, 0F, 1, 1, 5);
          wheel2.setRotationPoint(1F+wheelXTransport, 3F+wheelYTransport, 4F+wheelZTransport);
          wheel2.setTextureSize(64, 50);
          wheel2.mirror = true;
          setRotateAngle(wheel2, 1.570796F, 0F, 0F);
          wheel3 = new ModelRenderer(this, 12, 0);
          wheel3.addBox(0F, 0F, -3F, 1, 1, 5);
          wheel3.setRotationPoint(1F+wheelXTransport, -5F+wheelYTransport, 0F+wheelZTransport);
          wheel3.setTextureSize(64, 50);
          wheel3.mirror = true;
          setRotateAngle(wheel3, 0F, 0F, 0F);
          wheel4 = new ModelRenderer(this, 12, 0);
          wheel4.addBox(0F, 0F, 0F, 1, 1, 5);
          wheel4.setRotationPoint(1F+wheelXTransport, 3F+wheelYTransport, -6F+wheelZTransport);
          wheel4.setTextureSize(64, 50);
          wheel4.mirror = true;
          setRotateAngle(wheel4, 1.570796F, 0F, 0F);
          wheel5 = new ModelRenderer(this, 12, 0);
          wheel5.addBox(0F, 0F, 0F, 1, 1, 1);
          wheel5.setRotationPoint(1F+wheelXTransport, 4F+wheelYTransport, -4F+wheelZTransport);
          wheel5.setTextureSize(64, 50);
          wheel5.mirror = true;
          setRotateAngle(wheel5, 0F, 0F, 0F);
          wheel6 = new ModelRenderer(this, 12, 0);
          wheel6.addBox(0F, 0F, 0F, 1, 1, 1);
          wheel6.setRotationPoint(1F+wheelXTransport, 3F+wheelYTransport, -5F+wheelZTransport);
          wheel6.setTextureSize(64, 50);
          wheel6.mirror = true;
          setRotateAngle(wheel6, 0F, 0F, 0F);
          wheel7 = new ModelRenderer(this, 12, 0);
          wheel7.addBox(0F, 0F, 0F, 1, 1, 1);
          wheel7.setRotationPoint(1F+wheelXTransport, 4F+wheelYTransport, 2F+wheelZTransport);
          wheel7.setTextureSize(64, 50);
          wheel7.mirror = true;
          setRotateAngle(wheel7, 0F, 0F, 0F);
          wheel8 = new ModelRenderer(this, 12, 0);
          wheel8.addBox(0F, 0F, 0F, 1, 1, 1);
          wheel8.setRotationPoint(1F+wheelXTransport, 3F+wheelYTransport, 3F+wheelZTransport);
          wheel8.setTextureSize(64, 50);
          wheel8.mirror = true;
          setRotateAngle(wheel8, 0F, 0F, 0F);
          wheel9 = new ModelRenderer(this, 12, 0);
          wheel9.addBox(0F, 0F, 0F, 1, 1, 1);
          wheel9.setRotationPoint(1F+wheelXTransport, -4F+wheelYTransport, -4F+wheelZTransport);
          wheel9.setTextureSize(64, 50);
          wheel9.mirror = true;
          setRotateAngle(wheel9, 0F, 0F, 0F);
          wheel10 = new ModelRenderer(this, 12, 0);
          wheel10.addBox(0F, 0F, 0F, 1, 1, 1);
          wheel10.setRotationPoint(1F+wheelXTransport, -3F+wheelYTransport, -5F+wheelZTransport);
          wheel10.setTextureSize(64, 50);
          wheel10.mirror = true;
          setRotateAngle(wheel10, 0F, 0F, 0F);
          wheel11 = new ModelRenderer(this, 12, 0);
          wheel11.addBox(0F, 0F, 0F, 1, 1, 1);
          wheel11.setRotationPoint(1F+wheelXTransport, -3F+wheelYTransport, 3F+wheelZTransport);
          wheel11.setTextureSize(64, 50);
          wheel11.mirror = true;
          setRotateAngle(wheel11, 0F, 0F, 0F);
          wheel12 = new ModelRenderer(this, 12, 0);
          wheel12.addBox(0F, 0F, 0F, 1, 1, 1);
          wheel12.setRotationPoint(1F+wheelXTransport, -4F+wheelYTransport, 2F+wheelZTransport);
          wheel12.setTextureSize(64, 50);
          wheel12.mirror = true;
          setRotateAngle(wheel12, 0F, 0F, 0F);
          wheel13 = new ModelRenderer(this, 12, 0);
          wheel13.addBox(0F, 0F, 0F, 1, 1, 1);
          wheel13.setRotationPoint(1F+wheelXTransport, 3F+wheelYTransport, -4F+wheelZTransport);
          wheel13.setTextureSize(64, 50);
          wheel13.mirror = true;
          setRotateAngle(wheel13, 0F, 0F, 0F);
          wheel14 = new ModelRenderer(this, 12, 0);
          wheel14.addBox(0F, 0F, -1F, 1, 1, 1);
          wheel14.setRotationPoint(1F+wheelXTransport, 2F+wheelYTransport, -2F+wheelZTransport);
          wheel14.setTextureSize(64, 50);
          wheel14.mirror = true;
          setRotateAngle(wheel14, 0F, 0F, 0F);
          wheel15 = new ModelRenderer(this, 12, 0);
          wheel15.addBox(0F, 0F, 0F, 1, 1, 1);
          wheel15.setRotationPoint(1F+wheelXTransport, 1F+wheelYTransport, -2F+wheelZTransport);
          wheel15.setTextureSize(64, 50);
          wheel15.mirror = true;
          setRotateAngle(wheel15, 0F, 0F, 0F);
          wheel16 = new ModelRenderer(this, 12, 0);
          wheel16.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1);
          wheel16.setRotationPoint(1.5F+wheelXTransport, 0.5F+wheelYTransport, -0.5F+wheelZTransport);
          wheel16.setTextureSize(64, 50);
          wheel16.mirror = true;
          setRotateAngle(wheel16, 0F, 0F, 0F);
          wheel17 = new ModelRenderer(this, 12, 0);
          wheel17.addBox(0F, 0F, 0F, 1, 1, 1);
          wheel17.setRotationPoint(1F+wheelXTransport, -1F+wheelYTransport, 0F+wheelZTransport);
          wheel17.setTextureSize(64, 50);
          wheel17.mirror = true;
          setRotateAngle(wheel17, 0F, 0F, 0F);
          wheel18 = new ModelRenderer(this, 12, 0);
          wheel18.addBox(0F, 0F, 0F, 1, 1, 1);
          wheel18.setRotationPoint(1F+wheelXTransport, -3F+wheelYTransport, 2F+wheelZTransport);
          wheel18.setTextureSize(64, 50);
          wheel18.mirror = true;
          setRotateAngle(wheel18, 0F, 0F, 0F);
          wheel19 = new ModelRenderer(this, 12, 0);
          wheel19.addBox(0F, 0F, 0F, 1, 1, 1);
          wheel19.setRotationPoint(1F+wheelXTransport, -2F+wheelYTransport, 1F+wheelZTransport);
          wheel19.setTextureSize(64, 50);
          wheel19.mirror = true;
          setRotateAngle(wheel19, 0F, 0F, 0F);
          wheel20 = new ModelRenderer(this, 12, 0);
          wheel20.addBox(0F, 0F, 0F, 1, 1, 1);
          wheel20.setRotationPoint(1F+wheelXTransport, 3F+wheelYTransport, 2F+wheelZTransport);
          wheel20.setTextureSize(64, 50);
          wheel20.mirror = true;
          setRotateAngle(wheel20, 0F, 0F, 0F);
          wheel21 = new ModelRenderer(this, 12, 0);
          wheel21.addBox(0F, 0F, 0F, 1, 1, 1);
          wheel21.setRotationPoint(1F+wheelXTransport, 2F+wheelYTransport, 1F+wheelZTransport);
          wheel21.setTextureSize(64, 50);
          wheel21.mirror = true;
          setRotateAngle(wheel21, 0F, 0F, 0F);
          wheel22 = new ModelRenderer(this, 12, 0);
          wheel22.addBox(0F, 0F, 0F, 1, 1, 1);
          wheel22.setRotationPoint(1F+wheelXTransport, 1F+wheelYTransport, 0F+wheelZTransport);
          wheel22.setTextureSize(64, 50);
          wheel22.mirror = true;
          setRotateAngle(wheel22, 0F, 0F, 0F);
          wheel23 = new ModelRenderer(this, 12, 0);
          wheel23.addBox(0F, 0F, 0F, 1, 1, 1);
          wheel23.setRotationPoint(1F+wheelXTransport, -1F+wheelYTransport, -2F+wheelZTransport);
          wheel23.setTextureSize(64, 50);
          wheel23.mirror = true;
          setRotateAngle(wheel23, 0F, 0F, 0F);
          wheel24 = new ModelRenderer(this, 12, 0);
          wheel24.addBox(0F, 0F, 0F, 1, 1, 1);
          wheel24.setRotationPoint(1F+wheelXTransport, -2F+wheelYTransport, -3F+wheelZTransport);
          wheel24.setTextureSize(64, 50);
          wheel24.mirror = true;
          setRotateAngle(wheel24, 0F, 0F, 0F);
          wheel25 = new ModelRenderer(this, 12, 0);
          wheel25.addBox(0F, 0F, 0F, 1, 1, 1);
          wheel25.setRotationPoint(1F+wheelXTransport, -3F+wheelYTransport, -4F+wheelZTransport);
          wheel25.setTextureSize(64, 50);
          wheel25.mirror = true;
          setRotateAngle(wheel25, 0F, 0F, 0F);
          
          wheelModelWraper = new ModelRenderer(this, 12, 0);
          //wheelModelWraper.setRotationPoint(0, 17, 0);
          wheelModelWraper.addChild(wheel1);
          wheelModelWraper.addChild(wheel2);
          wheelModelWraper.addChild(wheel3);
          wheelModelWraper.addChild(wheel4);
          wheelModelWraper.addChild(wheel5);
          wheelModelWraper.addChild(wheel6);
          wheelModelWraper.addChild(wheel7);
          wheelModelWraper.addChild(wheel8);
          wheelModelWraper.addChild(wheel9);
          wheelModelWraper.addChild(wheel10);
          wheelModelWraper.addChild(wheel11);
          wheelModelWraper.addChild(wheel12);
          wheelModelWraper.addChild(wheel13);
          wheelModelWraper.addChild(wheel14);
          wheelModelWraper.addChild(wheel15);
          wheelModelWraper.addChild(wheel16);
          wheelModelWraper.addChild(wheel17);
          wheelModelWraper.addChild(wheel18);
          wheelModelWraper.addChild(wheel19);
          wheelModelWraper.addChild(wheel20);
          wheelModelWraper.addChild(wheel21);
          wheelModelWraper.addChild(wheel22);
          wheelModelWraper.addChild(wheel23);
          wheelModelWraper.addChild(wheel24);
          wheelModelWraper.addChild(wheel25);

    }


    public double angel;
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        
    	this.setOffset(plateModelWraper, 0, (float)getSinFrequency(-0.18f, 0f, angel), 0);
    	this.setOffset(poolModelWraper, 0, (float)getSinFrequency(-0.1f, 0.18f, angel), 0);
    	this.setOffset(wheelModelWraper, 0, 0.75f, -0.0315f);
    	wheelModelWraper.setRotationPoint(wheelModelWraper.offsetX, wheelModelWraper.offsetY, wheelModelWraper.offsetZ);
    	//1point = 0.063 offset
    	this.setRotateAngle(wheelModelWraper, (float)angel, 0, 0);

    	/*majorModelWraper.setTextureOffset(0, 0);
    	plateModelWraper.setTextureOffset(32, 38);
    	poolModelWraper.setTextureOffset(3, 38);
    	wheelModelWraper.setTextureOffset(12, 0);*/
    	
    	//System.out.println(offsetTimer);
    	majorModelWraper.render(f5);
    	plateModelWraper.render(f5);
    	poolModelWraper.render(f5);
    	wheelModelWraper.render(f5);
    	
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    public void setOffset(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.offsetX = x;
        modelRenderer.offsetY = y;
        modelRenderer.offsetZ = z;
    }
    
    public double getSinFrequency(double min, double max, double timer){
    	double a = (max - min)/2;
    	double b = (max + min)/2;
    	double sin = Math.sin(timer);
    	return a * sin + b;
    }
}
