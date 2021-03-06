// Date: 09.10.2015 20:49:14
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX






package com.mcraichu.obeliskoflight.orca;

import org.lwjgl.opengl.GL11;

import com.mcraichu.obeliskoflight.stealthtank.StealthTank;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelOrca extends ModelBase
{
	protected boolean isAttacking = false;

	protected double distanceMovedTotal = 0.0D;

	protected double prevDistanceMovedTotal = 0.0D;


	// don't make this too large or animations will be skipped
	protected static final double CYCLES_PER_BLOCK = 1.7D; 
	protected static final int MOTIONLESS_LIMIT = 16; 
	protected int cycleIndex = 0;	

	protected float[][] walkingCycle = new float[][]
	{
		/*
		 * leg, clawX, clawY
		 * */	

		{ 0F, -70F, 30F },
		{ 45F, -70F, 30F },
		{ 90F, -70F, 30F },
		{ 135F, -70F, 30F },
		{ 180F, -70F, 30F },
		{ 225F, -70F, 30F },
		{ 270F, -70F, 30F },
		{ 315F, -70F, 30F },
	};

	//fields
	ModelRenderer Base;
	ModelRenderer Gun;
	ModelRenderer SeatFront;
	ModelRenderer SeatLeft;
	ModelRenderer SeatRight;
	ModelRenderer SeatBack;
	ModelRenderer WindowFront;
	ModelRenderer WindowLeft;
	ModelRenderer WindowRight;
	ModelRenderer Back1;
	ModelRenderer Back2;
	ModelRenderer Back3;
	ModelRenderer TurbineBack;
	ModelRenderer WingLeft1;
	ModelRenderer WingLeft2;
	ModelRenderer WingRight1;
	ModelRenderer WingRight2;
	ModelRenderer TurbineMid;
	ModelRenderer TurbineLeft;
	ModelRenderer TurbineRight;

	public ModelOrca()
	{
		textureWidth = 128;
		textureHeight = 128;

		Base = new ModelRenderer(this, 0, 0);
		Base.addBox(-9F, 0F, -20F, 18, 4, 32);
		Base.setRotationPoint(0F, 18F, 0F);
		Base.setTextureSize(128, 128);
		Base.mirror = true;
		setRotation(Base, 0F, 0F, 0F);
		Gun = new ModelRenderer(this, 100, 54);
		Gun.addBox(-2F, -1F, -24F, 4, 4, 4);
		Gun.setRotationPoint(0F, 18F, 0F);
		Gun.setTextureSize(128, 128);
		Gun.mirror = true;
		setRotation(Gun, 0F, 0F, 0F);
		SeatFront = new ModelRenderer(this, 0, 0);
		SeatFront.addBox(-7F, -4F, -20F, 14, 4, 8);
		SeatFront.setRotationPoint(0F, 18F, 0F);
		SeatFront.setTextureSize(128, 128);
		SeatFront.mirror = true;
		setRotation(SeatFront, 0F, 0F, 0F);
		SeatLeft = new ModelRenderer(this, 25, 0);
		SeatLeft.addBox(7F, -4F, -20F, 2, 4, 24);
		SeatLeft.setRotationPoint(0F, 18F, 0F);
		SeatLeft.setTextureSize(128, 128);
		SeatLeft.mirror = true;
		setRotation(SeatLeft, 0F, 0F, 0F);
		SeatRight = new ModelRenderer(this, 25, 0);
		SeatRight.addBox(-9F, -4F, -20F, 2, 4, 24);
		SeatRight.setRotationPoint(0F, 18F, 0F);
		SeatRight.setTextureSize(128, 128);
		SeatRight.mirror = true;
		setRotation(SeatRight, 0F, 0F, 0F);
		SeatBack = new ModelRenderer(this, 0, 0);
		SeatBack.addBox(-9F, -12F, 4F, 18, 12, 8);
		SeatBack.setRotationPoint(0F, 18F, 0F);
		SeatBack.setTextureSize(128, 128);
		SeatBack.mirror = true;
		setRotation(SeatBack, 0F, 0F, 0F);
		WindowFront = new ModelRenderer(this, 95, 100);
		WindowFront.addBox(-8F, -8F, -9.5F, 16, 24, 0);
		WindowFront.setRotationPoint(0F, 18F, 0F);
		WindowFront.setTextureSize(128, 128);
		WindowFront.mirror = true;
		setRotation(WindowFront, -1.256637F, 0F, 0F);
		WindowLeft = new ModelRenderer(this, 75, 80);
		WindowLeft.addBox(8F, -8F, -9.5F, 0, 24, 8);
		WindowLeft.setRotationPoint(0F, 18F, 0F);
		WindowLeft.setTextureSize(128, 128);
		WindowLeft.mirror = true;
		setRotation(WindowLeft, -1.256637F, 0F, 0F);
		WindowRight = new ModelRenderer(this, 75, 80);
		WindowRight.addBox(-8F, -8F, -9.5F, 0, 24, 8);
		WindowRight.setRotationPoint(0F, 18F, 0F);
		WindowRight.setTextureSize(128, 128);
		WindowRight.mirror = true;
		setRotation(WindowRight, -1.256637F, 0F, 0F);
		Back1 = new ModelRenderer(this, 0, 0);
		Back1.addBox(-8F, -12F, 12F, 16, 10, 6);
		Back1.setRotationPoint(0F, 18F, 0F);
		Back1.setTextureSize(128, 128);
		Back1.mirror = true;
		setRotation(Back1, 0F, 0F, 0F);
		Back2 = new ModelRenderer(this, 0, 0);
		Back2.addBox(-5F, -12F, 18F, 10, 6, 6);
		Back2.setRotationPoint(0F, 18F, 0F);
		Back2.setTextureSize(128, 128);
		Back2.mirror = true;
		setRotation(Back2, 0F, 0F, 0F);
		Back3 = new ModelRenderer(this, 0, 0);
		Back3.addBox(-2F, 24F, 8F, 4, 26, 4);
		Back3.setRotationPoint(0F, 18F, 0F);
		Back3.setTextureSize(128, 128);
		Back3.mirror = true;
		setRotation(Back3, 1.570796F, 0F, 0F);
		TurbineBack = new ModelRenderer(this, 0, 90);
		TurbineBack.addBox(-4F, -12F, 50F, 8, 4, 8);
		TurbineBack.setRotationPoint(0F, 18F, 0F);
		TurbineBack.setTextureSize(128, 128);
		TurbineBack.mirror = true;
		setRotation(TurbineBack, 0F, 0F, 0F);
		WingLeft1 = new ModelRenderer(this, 0, 0);
		WingLeft1.addBox(3F, -11F, 52F, 12, 1, 6);
		WingLeft1.setRotationPoint(0F, 18F, 0F);
		WingLeft1.setTextureSize(128, 128);
		WingLeft1.mirror = true;
		setRotation(WingLeft1, 0F, 0F, 0F);
		WingLeft2 = new ModelRenderer(this, 0, 0);
		WingLeft2.addBox(15F, -16F, 52F, 1, 12, 6);
		WingLeft2.setRotationPoint(0F, 18F, 0F);
		WingLeft2.setTextureSize(128, 128);
		WingLeft2.mirror = true;
		setRotation(WingLeft2, 0F, 0F, 0F);
		WingRight1 = new ModelRenderer(this, 0, 0);
		WingRight1.addBox(-15F, -11F, 52F, 12, 1, 6);
		WingRight1.setRotationPoint(0F, 18F, 0F);
		WingRight1.setTextureSize(128, 128);
		WingRight1.mirror = true;
		setRotation(WingRight1, 0F, 0F, 0F);
		WingRight2 = new ModelRenderer(this, 0, 0);
		WingRight2.addBox(-16F, -16F, 52F, 1, 12, 6);
		WingRight2.setRotationPoint(0F, 18F, 0F);
		WingRight2.setTextureSize(128, 128);
		WingRight2.mirror = true;
		setRotation(WingRight2, 0F, 0F, 0F);
		TurbineMid = new ModelRenderer(this, 0, 0);
		TurbineMid.addBox(-11F, -5F, 13F, 22, 4, 4);
		TurbineMid.setRotationPoint(0F, 18F, 0F);
		TurbineMid.setTextureSize(128, 128);
		TurbineMid.mirror = true;
		setRotation(TurbineMid, 0F, 0F, 0F);
		TurbineLeft = new ModelRenderer(this, 0, 105);
		TurbineLeft.addBox(0F, -5F, -6F, 12, 10, 12);
		TurbineLeft.setRotationPoint(11F, 15F, 15F);
		TurbineLeft.setTextureSize(128, 128);
		TurbineLeft.mirror = true;
		setRotation(TurbineLeft, 0.4363323F, 0F, 0F);
		TurbineRight = new ModelRenderer(this, 0, 105);
		TurbineRight.addBox(-12F, -5F, -6F, 12, 10, 12);
		TurbineRight.setRotationPoint(-11F, 15F, 15F);
		TurbineRight.setTextureSize(128, 128);
		TurbineRight.mirror = true;
		setRotation(TurbineRight, 0.4363323F, 0F, 0F);
		
		
		convertToChild(TurbineMid, TurbineLeft);
		convertToChild(TurbineMid, TurbineRight);

		convertToChild(Base, Gun);
		convertToChild(Base, SeatFront);
		convertToChild(Base, SeatLeft);
		convertToChild(Base, SeatRight);
		convertToChild(Base, SeatBack);
		convertToChild(Base, WindowFront);
		convertToChild(Base, WindowLeft);
		convertToChild(Base, WindowRight);
		convertToChild(Base, Back1);
		convertToChild(Base, Back2);
		convertToChild(Base, Back3);
		convertToChild(Base, TurbineBack);
		convertToChild(Base, WingLeft1);
		convertToChild(Base, WingLeft2);
		convertToChild(Base, WingRight1);
		convertToChild(Base, WingRight2);
		convertToChild(Base, TurbineMid);
		
	}

	@Override
	public void render(Entity parEntity, float parTime, float parSwingSuppress, float par4, float parHeadAngleY, float parHeadAngleX, float par7)
	{
		renderOrca((Orca) parEntity, parTime, parSwingSuppress, par4, parHeadAngleY, parHeadAngleX, par7);
	}

	public void renderOrca(Orca parEntity, float parTime, float parSwingSuppress, float par4, float parHeadAngleY, float parHeadAngleX, float par7) {

		//		super.render(parEntity, f, f1, f2, f3, f4, f5);
		//		setRotationAngles(f, f1, f2, f3, f4, f5, parEntity);

		isAttacking = false;

		setRotationAngles(parTime, parSwingSuppress, par4, parHeadAngleY, parHeadAngleX, par7, parEntity);

		GL11.glPushMatrix();
		//      GL11.glScalef(1.1F,1.1F,1.1F);
		GlStateManager.translate(0.0,0.0,0.0);
		GL11.glScalef(1.0F,1.0F,1.0F);

		//System.out.println("-----------------------renderAtlas()");

		Base.render(par7);

		//		GlStateManager.popMatrix();
		//		
		//		GL11.glPushMatrix();
		//		
		//		if (parEntity.getAttacking())
		//		{
		//			float temp = parEntity.getRotationYawHead();
		//			float temp2 = parEntity.rotationYawHead;
		//			float temp3 = parEntity.rotationYaw;
		//			float temp4 = temp3-temp2;
		//			GlStateManager.rotate(temp4, 0.0F, 1.0F, 0.0F);
		//			setRotation(RocketLauncher, 0F, temp4, 0F);
		//		}
		//		//this.RocketLauncher.render(par7);

		//		
		//		this.RocketLauncher.postRender(1.0F);


		GlStateManager.popMatrix();


	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float parTime, float parSwingSuppress, float par3, float parHeadAngleY, float parHeadAngleX, float par6, Entity parEntity)
	{
		updateDistanceMovedTotal(parEntity);

		cycleIndex = 0; //(int) ((getDistanceMovedTotal(parEntity)*CYCLES_PER_BLOCK)%walkingCycle.length);

		cycleIndex = (int) ((getDistanceMovedTotal(parEntity)*CYCLES_PER_BLOCK)%walkingCycle.length);
		if(getDistanceMovedTotal(parEntity) < 0.05)
		{
			cycleIndex = 0;
		}

		/*
		 * turbine
		 * */	

//		TurbineLeft.rotateAngleX = degToRad(walkingCycle[cycleIndex][0]) ;
//		TurbineRight.rotateAngleX = degToRad(walkingCycle[cycleIndex][0]) ;

	}

	protected void updateDistanceMovedTotal(Entity parEntity) 
	{
		prevDistanceMovedTotal = distanceMovedTotal;
		distanceMovedTotal += parEntity.getDistance(parEntity.prevPosX, parEntity.prevPosY, parEntity.prevPosZ);
	}

	protected double getDistanceMovedTotal(Entity parEntity) 
	{
		return (distanceMovedTotal);
	}

	protected float degToRad(float degrees)
	{
		return degrees * (float)Math.PI / 180 ;
	}

	protected void setRotationDeg(ModelRenderer model, float rotX, float rotY, float rotZ)
	{
		model.rotateAngleX = degToRad(rotX);
		model.rotateAngleY = degToRad(rotY);
		model.rotateAngleZ = degToRad(rotZ);        
	}

	// This is really useful for converting the source from a Techne model
	// export
	// which will have absolute rotation points that need to be converted before
	// creating the addChild() relationship
	protected void convertToChild(ModelRenderer parParent,
			ModelRenderer parChild) {
		// move child rotation point to be relative to parent
		parChild.rotationPointX -= parParent.rotationPointX;
		parChild.rotationPointY -= parParent.rotationPointY;
		parChild.rotationPointZ -= parParent.rotationPointZ;
		// make rotations relative to parent
		parChild.rotateAngleX -= parParent.rotateAngleX;
		parChild.rotateAngleY -= parParent.rotateAngleY;
		parChild.rotateAngleZ -= parParent.rotateAngleZ;
		// create relationship
		parParent.addChild(parChild);
	}

}
