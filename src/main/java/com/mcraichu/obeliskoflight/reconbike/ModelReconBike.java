package com.mcraichu.obeliskoflight.reconbike;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

// Date: 25.10.2015 01:33:15
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX







public class ModelReconBike extends ModelBase
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
	ModelRenderer leftback;
	ModelRenderer base;
	ModelRenderer rightgun1;
	ModelRenderer window;
	ModelRenderer leftfront;
	ModelRenderer wheelback;
	ModelRenderer wheelback1;
	ModelRenderer wheelback2;
	ModelRenderer wheelback3;
	ModelRenderer wheelback4;
	ModelRenderer wheelback5;
	ModelRenderer wheelback6;
	ModelRenderer wheelback7;
	ModelRenderer wheelback8;
	ModelRenderer wheelfront;
	ModelRenderer wheelfront1;
	ModelRenderer wheelfront2;
	ModelRenderer wheelfront4;
	ModelRenderer wheelfront3;
	ModelRenderer wheelfront5;
	ModelRenderer wheelfront6;
	ModelRenderer wheelfront7;
	ModelRenderer wheelfront8;
//	ModelRenderer headrest;
	ModelRenderer seatcenter;
	ModelRenderer seatright;
	ModelRenderer basefront;
	ModelRenderer baseback1;
	ModelRenderer rightfront;
	ModelRenderer rightback;
	ModelRenderer monitor;
	ModelRenderer rightgun2;
	ModelRenderer baseback2;
	ModelRenderer leftgun2;
	ModelRenderer leftgun1;
	ModelRenderer seatleft;

	public ModelReconBike()
	{
		textureWidth = 128;
		textureHeight = 128;

		leftback = new ModelRenderer(this, 80, 80);
		leftback.addBox(3F, -13F, 8F, 2, 7, 18);
		leftback.setRotationPoint(0F, 20F, 0F);
		leftback.setTextureSize(128, 128);
		leftback.mirror = true;
		setRotation(leftback, -0.2792527F, 0F, 0F);
		base = new ModelRenderer(this, 0, 90);
		base.addBox(-6F, 0F, -15F, 12, 4, 26);
		base.setRotationPoint(0F, 18F, 0F);
		base.setTextureSize(128, 128);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		rightgun1 = new ModelRenderer(this, 40, 0);
		rightgun1.addBox(-20F, -8F, 11F, 7, 2, 4);
		rightgun1.setRotationPoint(0F, 20F, 0F);
		rightgun1.setTextureSize(128, 128);
		rightgun1.mirror = true;
		setRotation(rightgun1, 0F, 0F, 0.7853982F);
		window = new ModelRenderer(this, 40, 40);
		window.addBox(-5F, -26F, -13F, 10, 10, 12);
		window.setRotationPoint(0F, 20F, 0F);
		window.setTextureSize(128, 128);
		window.mirror = true;
		setRotation(window, 0.6108652F, 0F, 0F);
		leftfront = new ModelRenderer(this, 80, 80);
		leftfront.addBox(3F, -7F, -33F, 2, 7, 13);
		leftfront.setRotationPoint(0F, 20F, 0F);
		leftfront.setTextureSize(128, 128);
		leftfront.mirror = true;
		setRotation(leftfront, 0F, 0F, 0F);
		wheelback = new ModelRenderer(this, 100, 0);
		wheelback.addBox(-3F, -7F, -2.5F, 6, 14, 5);
		wheelback.setRotationPoint(0F, 17F, 24F);
		wheelback.setTextureSize(128, 128);
		wheelback.mirror = true;
		setRotation(wheelback, 0F, 0F, 0F);
		wheelback1 = new ModelRenderer(this, 100, 0);
		wheelback1.addBox(-3F, -6F, -4.5F, 6, 12, 2);
		wheelback1.setRotationPoint(0F, 17F, 24F);
		wheelback1.setTextureSize(128, 128);
		wheelback1.mirror = true;
		setRotation(wheelback1, 0F, 0F, 0F);
		wheelback2 = new ModelRenderer(this, 100, 0);
		wheelback2.addBox(-3F, -6F, 2.5F, 6, 12, 2);
		wheelback2.setRotationPoint(0F, 17F, 24F);
		wheelback2.setTextureSize(128, 128);
		wheelback2.mirror = true;
		setRotation(wheelback2, 0F, 0F, 0F);
		wheelback3 = new ModelRenderer(this, 100, 0);
		wheelback3.addBox(-3F, -5F, -5.5F, 6, 10, 1);
		wheelback3.setRotationPoint(0F, 17F, 24F);
		wheelback3.setTextureSize(128, 128);
		wheelback3.mirror = true;
		setRotation(wheelback3, 0F, 0F, 0F);
		wheelback4 = new ModelRenderer(this, 100, 0);
		wheelback4.addBox(-3F, -5F, 4.5F, 6, 10, 1);
		wheelback4.setRotationPoint(0F, 17F, 24F);
		wheelback4.setTextureSize(128, 128);
		wheelback4.mirror = true;
		setRotation(wheelback4, 0F, 0F, 0F);
		wheelback5 = new ModelRenderer(this, 100, 0);
		wheelback5.addBox(-3F, -4F, -6.5F, 6, 8, 1);
		wheelback5.setRotationPoint(0F, 17F, 24F);
		wheelback5.setTextureSize(128, 128);
		wheelback5.mirror = true;
		setRotation(wheelback5, 0F, 0F, 0F);
		wheelback6 = new ModelRenderer(this, 100, 0);
		wheelback6.addBox(-3F, -4F, 5.5F, 6, 8, 1);
		wheelback6.setRotationPoint(0F, 17F, 24F);
		wheelback6.setTextureSize(128, 128);
		wheelback6.mirror = true;
		setRotation(wheelback6, 0F, 0F, 0F);
		wheelback7 = new ModelRenderer(this, 100, 0);
		wheelback7.addBox(-3F, -2.5F, -7.5F, 6, 5, 1);
		wheelback7.setRotationPoint(0F, 17F, 24F);
		wheelback7.setTextureSize(128, 128);
		wheelback7.mirror = true;
		setRotation(wheelback7, 0F, 0F, 0F);
		wheelback8 = new ModelRenderer(this, 100, 0);
		wheelback8.addBox(-3F, -2.5F, 6.5F, 6, 5, 1);
		wheelback8.setRotationPoint(0F, 17F, 24F);
		wheelback8.setTextureSize(128, 128);
		wheelback8.mirror = true;
		setRotation(wheelback8, 0F, 0F, 0F);
		wheelfront = new ModelRenderer(this, 100, 0);
		wheelfront.addBox(-3F, -7F, -2.5F, 6, 14, 5);
		wheelfront.setRotationPoint(0F, 17F, -28F);
		wheelfront.setTextureSize(128, 128);
		wheelfront.mirror = true;
		setRotation(wheelfront, 0F, 0F, 0F);
		wheelfront1 = new ModelRenderer(this, 100, 0);
		wheelfront1.addBox(-3F, -6F, -4.5F, 6, 12, 2);
		wheelfront1.setRotationPoint(0F, 17F, -28F);
		wheelfront1.setTextureSize(128, 128);
		wheelfront1.mirror = true;
		setRotation(wheelfront1, 0F, 0F, 0F);
		wheelfront2 = new ModelRenderer(this, 100, 0);
		wheelfront2.addBox(-3F, -6F, 2.5F, 6, 12, 2);
		wheelfront2.setRotationPoint(0F, 17F, -28F);
		wheelfront2.setTextureSize(128, 128);
		wheelfront2.mirror = true;
		setRotation(wheelfront2, 0F, 0F, 0F);
		wheelfront4 = new ModelRenderer(this, 100, 0);
		wheelfront4.addBox(-3F, -5F, 4.5F, 6, 10, 1);
		wheelfront4.setRotationPoint(0F, 17F, -28F);
		wheelfront4.setTextureSize(128, 128);
		wheelfront4.mirror = true;
		setRotation(wheelfront4, 0F, 0F, 0F);
		wheelfront3 = new ModelRenderer(this, 100, 0);
		wheelfront3.addBox(-3F, -5F, -5.5F, 6, 10, 1);
		wheelfront3.setRotationPoint(0F, 17F, -28F);
		wheelfront3.setTextureSize(128, 128);
		wheelfront3.mirror = true;
		setRotation(wheelfront3, 0F, 0F, 0F);
		wheelfront5 = new ModelRenderer(this, 100, 0);
		wheelfront5.addBox(-3F, -4F, -6.5F, 6, 8, 1);
		wheelfront5.setRotationPoint(0F, 17F, -28F);
		wheelfront5.setTextureSize(128, 128);
		wheelfront5.mirror = true;
		setRotation(wheelfront5, 0F, 0F, 0F);
		wheelfront6 = new ModelRenderer(this, 100, 0);
		wheelfront6.addBox(-3F, -4F, 5.5F, 6, 8, 1);
		wheelfront6.setRotationPoint(0F, 17F, -28F);
		wheelfront6.setTextureSize(128, 128);
		wheelfront6.mirror = true;
		setRotation(wheelfront6, 0F, 0F, 0F);
		wheelfront7 = new ModelRenderer(this, 100, 0);
		wheelfront7.addBox(-3F, -2.5F, -7.5F, 6, 5, 1);
		wheelfront7.setRotationPoint(0F, 17F, -28F);
		wheelfront7.setTextureSize(128, 128);
		wheelfront7.mirror = true;
		setRotation(wheelfront7, 0F, 0F, 0F);
		wheelfront8 = new ModelRenderer(this, 100, 0);
		wheelfront8.addBox(-3F, -2.5F, 6.5F, 6, 5, 1);
		wheelfront8.setRotationPoint(0F, 17F, -28F);
		wheelfront8.setTextureSize(128, 128);
		wheelfront8.mirror = true;
		setRotation(wheelfront8, 0F, 0F, 0F);
//		headrest = new ModelRenderer(this, 0, 30);
//		headrest.addBox(-3F, -22F, 4F, 6, 6, 3);
//		headrest.setRotationPoint(0F, 20F, 0F);
//		headrest.setTextureSize(128, 128);
//		headrest.mirror = true;
//		setRotation(headrest, 0F, 0F, 0F);
		seatcenter = new ModelRenderer(this, 0, 50);
		seatcenter.addBox(-2F, -14F, 3F, 4, 12, 2);
		seatcenter.setRotationPoint(0F, 20F, 0F);
		seatcenter.setTextureSize(128, 128);
		seatcenter.mirror = true;
		setRotation(seatcenter, 0F, 0F, 0F);
		seatright = new ModelRenderer(this, 0, 50);
		seatright.addBox(-6F, -11F, -1F, 2, 9, 5);
		seatright.setRotationPoint(0F, 20F, 0F);
		seatright.setTextureSize(128, 128);
		seatright.mirror = true;
		setRotation(seatright, 0F, 0F, 0F);
		basefront = new ModelRenderer(this, 0, 90);
		basefront.addBox(-6F, -14F, -26F, 12, 12, 17);
		basefront.setRotationPoint(0F, 20F, 0F);
		basefront.setTextureSize(128, 128);
		basefront.mirror = true;
		setRotation(basefront, 0F, 0F, 0F);
		baseback1 = new ModelRenderer(this, 0, 90);
		baseback1.addBox(-6F, -16F, 4F, 12, 14, 11);
		baseback1.setRotationPoint(0F, 20F, 0F);
		baseback1.setTextureSize(128, 128);
		baseback1.mirror = true;
		setRotation(baseback1, 0F, 0F, 0F);
		rightfront = new ModelRenderer(this, 80, 80);
		rightfront.addBox(-5F, -7F, -33F, 2, 7, 13);
		rightfront.setRotationPoint(0F, 20F, 0F);
		rightfront.setTextureSize(128, 128);
		rightfront.mirror = true;
		setRotation(rightfront, 0F, 0F, 0F);
		rightback = new ModelRenderer(this, 80, 80);
		rightback.addBox(-5F, -13F, 8F, 2, 7, 18);
		rightback.setRotationPoint(0F, 20F, 0F);
		rightback.setTextureSize(128, 128);
		rightback.mirror = true;
		setRotation(rightback, -0.2792527F, 0F, 0F);
		monitor = new ModelRenderer(this, 0, 0);
		monitor.addBox(-3F, -19F, -17F, 6, 6, 9);
		monitor.setRotationPoint(0F, 20F, 0F);
		monitor.setTextureSize(128, 128);
		monitor.mirror = true;
		setRotation(monitor, 0.2617994F, 0F, 0F);
		rightgun2 = new ModelRenderer(this, 70, 0);
		rightgun2.addBox(-12F, -20F, 9F, 6, 3, 8);
		rightgun2.setRotationPoint(0F, 20F, 0F);
		rightgun2.setTextureSize(128, 128);
		rightgun2.mirror = true;
		setRotation(rightgun2, 0F, 0F, 0F);
		baseback2 = new ModelRenderer(this, 0, 90);
		baseback2.addBox(-6F, -16F, 15F, 12, 4, 6);
		baseback2.setRotationPoint(0F, 20F, 0F);
		baseback2.setTextureSize(128, 128);
		baseback2.mirror = true;
		setRotation(baseback2, 0F, 0F, 0F);
		leftgun2 = new ModelRenderer(this, 70, 0);
		leftgun2.addBox(6F, -20F, 9F, 6, 3, 8);
		leftgun2.setRotationPoint(0F, 20F, 0F);
		leftgun2.setTextureSize(128, 128);
		leftgun2.mirror = true;
		setRotation(leftgun2, 0F, 0F, 0F);
		leftgun1 = new ModelRenderer(this, 40, 0);
		leftgun1.addBox(13F, -8F, 11F, 7, 2, 4);
		leftgun1.setRotationPoint(0F, 20F, 0F);
		leftgun1.setTextureSize(128, 128);
		leftgun1.mirror = true;
		setRotation(leftgun1, 0F, 0F, -0.7853982F);
		seatleft = new ModelRenderer(this, 0, 50);
		seatleft.addBox(4F, -11F, -1F, 2, 9, 5);
		seatleft.setRotationPoint(0F, 20F, 0F);
		seatleft.setTextureSize(128, 128);
		seatleft.mirror = true;
		setRotation(seatleft, 0F, 0F, 0F);

		convertToChild(wheelback, wheelback1);
		convertToChild(wheelback, wheelback2);
		convertToChild(wheelback, wheelback3);
		convertToChild(wheelback, wheelback4);
		convertToChild(wheelback, wheelback5);
		convertToChild(wheelback, wheelback6);
		convertToChild(wheelback, wheelback7);
		convertToChild(wheelback, wheelback8);

		convertToChild(wheelfront, wheelfront1);
		convertToChild(wheelfront, wheelfront2);
		convertToChild(wheelfront, wheelfront3);
		convertToChild(wheelfront, wheelfront4);
		convertToChild(wheelfront, wheelfront5);
		convertToChild(wheelfront, wheelfront6);
		convertToChild(wheelfront, wheelfront7);
		convertToChild(wheelfront, wheelfront8);


		
		convertToChild(base, leftback);
		convertToChild(base, rightback);
		convertToChild(base, leftfront);
		convertToChild(base, rightfront);
		convertToChild(base, seatcenter);
		convertToChild(base, seatright);
		convertToChild(base, seatleft);
//		convertToChild(base, headrest);
		convertToChild(base, basefront);
		convertToChild(base, baseback1);
		convertToChild(base, baseback2);
		convertToChild(base, window);
		convertToChild(base, monitor);
		convertToChild(base, leftgun1);
		convertToChild(base, leftgun2);
		convertToChild(base, rightgun1);
		convertToChild(base, rightgun2);
		convertToChild(base, wheelfront);
		convertToChild(base, wheelback);
		
	}

	@Override
	public void render(Entity parEntity, float parTime, float parSwingSuppress, float par4, float parHeadAngleY, float parHeadAngleX, float par7)
	{
		renderReconBike((ReconBike) parEntity, parTime, parSwingSuppress, par4, parHeadAngleY, parHeadAngleX, par7);
	}

	public void renderReconBike(ReconBike parEntity, float parTime, float parSwingSuppress, float par4, float parHeadAngleY, float parHeadAngleX, float par7) {

		//		super.render(parEntity, f, f1, f2, f3, f4, f5);
		//		setRotationAngles(f, f1, f2, f3, f4, f5, parEntity);

		isAttacking = false;

		setRotationAngles(parTime, parSwingSuppress, par4, parHeadAngleY, parHeadAngleX, par7, parEntity);

		GL11.glPushMatrix();
		//      GL11.glScalef(1.1F,1.1F,1.1F);
		GlStateManager.translate(0.0,0.0,0.0);
		GL11.glScalef(1.0F,1.0F,1.0F);

		//System.out.println("-----------------------renderAtlas()");

		base.render(par7);

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


		//        else if(!useStartingCycle)
		//        {
		//        	cycleIndex = 6;
		//        }

		// DEBUG
		//System.out.println("ModelSerpent setRotationAngles(), distanceMoved ="+getDistanceMovedTotal(parEntity)+", cycleIndex ="+cycleIndex+", motionX ="+parEntity.motionX);

		/*
		 * wheels
		 * */	

		wheelfront.rotateAngleX = degToRad(walkingCycle[cycleIndex][0]) ;
		wheelback.rotateAngleX = degToRad(walkingCycle[cycleIndex][0]) ;

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
