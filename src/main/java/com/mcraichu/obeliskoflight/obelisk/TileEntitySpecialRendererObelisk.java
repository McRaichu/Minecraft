package com.mcraichu.obeliskoflight.obelisk;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.mcraichu.obeliskoflight.Reference;
import com.mcraichu.obeliskoflight.utilities.Utilities;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class TileEntitySpecialRendererObelisk extends TileEntitySpecialRenderer{
	
	 private static final ResourceLocation obeliskTexture = new ResourceLocation(Reference.MODID + ":" + "textures/entity/obelisk.png");
	 private static final ResourceLocation beamTexture = new ResourceLocation(Reference.MODID + ":" + "textures/entity/laser.png");

	/**
	 * render the tile entity - called every frame while the tileentity is in view of the player
	 * @param tileEntity the associated tile entity
	 * @param relativeX the x distance from the player's eye to the tileentity
	 * @param relativeY the y distance from the player's eye to the tileentity
	 * @param relativeZ the z distance from the player's eye to the tileentity
	 * @param partialTicks the fraction of a tick that this frame is being rendered at - used to interpolate frames between
	 *                     ticks, to make animations smoother.  For example - if the frame rate is steady at 80 frames per second,
	 *                     this method will be called four times per tick, with partialTicks spaced 0.25 apart, (eg) 0, 0.25, 0.5, 0.75
	 * @param blockDamageProgress the progress of the block being damaged (0 - 10), if relevant.  -1 if not relevant.
	 */
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double relativeX, double relativeY, double relativeZ, float partialTicks, int blockDamageProgress) {
		if (!(tileEntity instanceof TileEntityObelisk)) return; // should never happen
		TileEntityObelisk tileEntityObelisk = (TileEntityObelisk) tileEntity;

		// the gem changes its appearance and animation as the player approaches.
		// When the player is a long distance away, the gem is dark, resting in the hopper, and does not rotate.
		// As the player approaches closer than 16 blocks, the gem first starts to glow brighter and to spin anti-clockwise
		// When the player gets closer than 4 blocks, the gem is at maximum speed and brightness, and starts to levitate above the pedestal
		// Once the player gets closer than 2 blocks, the gem reaches maximum height.

		// the appearance and animation of the gem is hence made up of several parts:
		// 1) the colour of the gem, which is contained in the tileEntity
		// 2) the brightness of the gem, which depends on player distance
		// 3) the distance that the gem rises above the pedestal, which depends on player distance
		// 4) the speed at which the gem is spinning, which depends on player distance.

		double centreOffsetX = 0.5;
		double centreOffsetY = 0.0;
		double centreOffsetZ = 0.5;

		try {
			// save the transformation matrix and the rendering attributes, so that we can restore them after rendering.  This
			//   prevents us disrupting any vanilla TESR that render after ours.
			//  using try..finally is not essential but helps make it more robust in case of exceptions
			// For further information on rendering using the Tessellator, see http://greyminecraftcoder.blogspot.co.at/2014/12/the-tessellator-and-worldrenderer-18.html
			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_ENABLE_BIT);

			// First we need to set up the translation so that we render our gem with the bottom point at 0,0,0
			// when the renderTileEntityAt method is called, the tessellator is set up so that drawing a dot at [0,0,0] corresponds to the player's eyes
			// This means that, in order to draw a dot at the TileEntity [x,y,z], we need to translate the reference frame by the difference between the
			// two points, i.e. by the [relativeX, relativeY, relativeZ] passed to the method.  If you then draw a cube from [0,0,0] to [1,1,1], it will
			// render exactly over the top of the TileEntity's block.
			// In this example, the zero point of our model needs to be in the middle of the block, not at the [x,y,z] of the block, so we need to
			// add an extra offset as well, i.e. [gemCentreOffsetX, gemCentreOffsetY, gemCentreOffsetZ]
			GlStateManager.translate(relativeX + centreOffsetX, relativeY + centreOffsetY, relativeZ + centreOffsetZ);

			final double RENDER_HEIGHT = 0.5;        // desired render height
			final double MODEL_HEIGHT = 1.0;      // actual height of the gem in the vertexTable
			final double SCALE_FACTOR = RENDER_HEIGHT / MODEL_HEIGHT;
			GlStateManager.scale(SCALE_FACTOR, SCALE_FACTOR, SCALE_FACTOR);

			Tessellator tessellator = Tessellator.getInstance();
			WorldRenderer worldrenderer = tessellator.getWorldRenderer();
			
			this.bindTexture(obeliskTexture);         // texture for the gem appearance

			// set the key rendering flags appropriately...
			GL11.glDisable(GL11.GL_LIGHTING);     // turn off "item" lighting (face brightness depends on which direction it is facing)
			GL11.glDisable(GL11.GL_BLEND);        // turn off "alpha" transparency blending
			GL11.glDepthMask(true);               // gem is hidden behind other objects

			// change the "multitexturing" lighting value (default value is the brightness of the tile entity's block)
			// - this will make the gem "glow" brighter than the surroundings if it is dark.
			// glowmultiplier (0-1)
			float glowMultiplier = 1.0f; 
			final int SKY_LIGHT_VALUE = (int)(15 * glowMultiplier);
			final int BLOCK_LIGHT_VALUE = 0;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, SKY_LIGHT_VALUE * 16.0F, BLOCK_LIGHT_VALUE * 16.0F);

			worldrenderer.startDrawingQuads();
			//worldrenderer.startDrawing(GL11.GL_TRIANGLES);
			addObeliskVertices(worldrenderer);
			addTipVertices(worldrenderer,tileEntityObelisk.currentCharge(),tileEntityObelisk.maxCharge());
			
			tessellator.draw();

		} finally {
			GL11.glPopAttrib();
			GL11.glPopMatrix();
		}

		if(tileEntityObelisk.shot && (tileEntityObelisk.target != null)){// && (tileEntityObelisk.ticksUntilReady < (int)(tileEntityObelisk.untilReady/2))){
			drawLaserVertices(tileEntity, relativeX, relativeY, relativeZ, partialTicks, blockDamageProgress);
		}
		
	}

	// add the vertices for drawing the gem.  Generated using a model builder and pasted manually because the object model
	//   loader is (not yet?) implemented.
	private void addObeliskVertices(WorldRenderer worldrenderer) {
		final double[][] vertexTable = {
				
				//bottom
				{ 0.8, 0.0,-1.0, 1.0,0.8},  //d
				{ 0.8, 0.0, 1.0, 1.0,0.0},  //g
				{-0.8, 0.0, 1.0, 0.0,0.0},  //e
				{-0.8, 0.0,-1.0, 0.0,0.8},  //a
				
				//1 etage
				{-0.8, 0.0,-1.0, 1.0,0.8},  //a
				{-0.8, 2.0, 0.0, 1.0,0.0},  //b
				{ 0.8, 2.0, 0.0, 0.0,0.0},  //c
				{ 0.8, 0.0,-1.0, 0.0,0.8},  //d
				
				{-0.8, 0.0, 1.0, 1.0,0.8},  //e
				{-0.8, 2.0, 1.0, 1.0,0.0},  //f
				{-0.8, 2.0, 0.0, 0.0,0.0},  //b
				{-0.8, 0.0,-1.0, 0.0,0.8},  //a
				
				{ 0.8, 0.0, 1.0, 1.0,0.8},  //g
				{ 0.8, 2.0, 1.0, 1.0,0.0},  //h
				{-0.8, 2.0, 1.0, 0.0,0.0},  //f
				{-0.8, 0.0, 1.0, 0.0,0.8},  //e
				
				{ 0.8, 0.0,-1.0, 1.0,0.8},  //d
				{ 0.8, 2.0, 0.0, 1.0,0.0},  //c				
				{ 0.8, 2.0, 1.0, 0.0,0.0},  //h
				{ 0.8, 0.0, 1.0, 0.0,0.8},   //g
				
				//2 etage
				{-0.8, 2.0, 0.0, 1.0,0.8},  //b
				{-0.6, 4.0, 0.0, 1.0,0.0},  //i
				{ 0.6, 4.0, 0.0, 0.0,0.0},  //j				
				{ 0.8, 2.0, 0.0, 0.0,0.8},  //c

				{-0.8, 2.0, 1.0, 1.0,0.8},  //f
				{-0.6, 4.0, 0.6, 1.0,0.0},  //k
				{-0.6, 4.0, 0.0, 0.0,0.0},  //i
				{-0.8, 2.0, 0.0, 0.0,0.8},  //b

				{ 0.8, 2.0, 1.0, 1.0,0.8},  //h
				{ 0.6, 4.0, 0.6, 1.0,0.0},  //l
				{-0.6, 4.0, 0.6, 0.0,0.0},  //k
				{-0.8, 2.0, 1.0, 0.0,0.8},  //f

				{ 0.8, 2.0, 0.0, 1.0,0.8},  //c
				{ 0.6, 4.0, 0.0, 1.0,0.0},  //j
				{ 0.6, 4.0, 0.6, 0.0,0.0},  //l
				{ 0.8, 2.0, 1.0, 0.0,0.8},  //h
				
				//3 etage
				{-0.6 , 4.0, 0.0 , 1.0,0.8},  //i
				{-0.35, 5.3,-0.65, 1.0,0.0},  //m
				{0.35 , 5.3,-0.65, 0.0,0.0},  //n
				{ 0.6 , 4.0, 0.0 , 0.0,0.8},  //j				

				{-0.6 , 4.0, 0.6 , 1.0,0.8},  //k
				{-0.35, 5.5,-0.3 , 1.0,0.0},  //o
				{-0.35, 5.3,-0.65, 0.0,0.0},  //m
				{-0.6 , 4.0, 0.0 , 0.0,0.8},  //i

				{ 0.6 , 4.0, 0.6 , 1.0,0.8},  //l
				{ 0.35, 5.5,-0.3 , 1.0,0.0},  //p
				{-0.35, 5.5,-0.3 , 0.0,0.0},  //o
				{-0.6 , 4.0, 0.6 , 0.0,0.8},  //k
		
				{ 0.6 , 4.0, 0.0 , 1.0,0.8},  //j
				{ 0.35, 5.3,-0.65, 1.0,0.0},  //n
				{ 0.35, 5.5,-0.3 , 0.0,0.0},  //p
				{ 0.6 , 4.0, 0.6 , 0.0,0.8},  //l
				
		};

		for (double [] vertex : vertexTable) {
			worldrenderer.addVertexWithUV(vertex[0], vertex[1], vertex[2], vertex[3], vertex[4]);
		}
	}
	
	private void addTipVertices(WorldRenderer worldrenderer, int current, int max) {
		
		float chargingSteps = 10.0f; //wir haben 5 stufen
		float temp = (float)current/(float)max;
		float temp1 = 1/(chargingSteps);
		int temp2 = (int)( (temp*10)/ (temp1*10));		
		float offset1,offset2;
		offset1 = (1/chargingSteps) * temp2;
		offset2 = (1/chargingSteps) * (temp2+1);
		if(current >= max){
			offset2 = 1.0f;
			offset1 = 1.0f -(1/chargingSteps);
		}
		
		float up2 = (1/chargingSteps);
		float up1 = 0.0f;
		if(temp2 > 0){
			up2 = 1.0f;
			up1 = 1.0f -(1/chargingSteps);
		}
	
		final double[][] vertexTable = {
				//tip
				{-0.35, 5.3,-0.65, offset2,1.0},  //m
				{-0.05, 6.0,-1.0 , offset2,0.8},  //q
				{ 0.05, 6.0,-1.0 , offset1,0.8},  //r
				{0.35 , 5.3,-0.65, offset1,1.0},  //n

				{-0.35, 5.5,-0.3 , offset2,1.0},  //o
				{-0.05, 6.0,-0.95, offset2,0.8},  //s
				{-0.05, 6.0,-1.0 , offset1,0.8},  //q
				{-0.35, 5.3,-0.65, offset1,1.0},  //m

				{ 0.35, 5.5,-0.3 , offset2,1.0},  //p
				{ 0.05, 6.0,-0.95, offset2,0.8},  //t
				{-0.05, 6.0,-0.95, offset1,0.8},  //s
				{-0.35, 5.5,-0.3 , offset1,1.0},  //o

				{ 0.35, 5.3,-0.65, offset2,1.0},  //n				
				{ 0.05, 6.0,-1.0 , offset2,0.8},  //r
				{ 0.05, 6.0,-0.95, offset1,0.8},  //t
				{ 0.35, 5.5,-0.3 , offset1,1.0},  //p				

				//top
				{-0.05, 6.0,-1.0 , up2,1.0},  //q
				{-0.05, 6.0,-0.95, up2,0.8},  //s
				{ 0.05, 6.0,-0.95, up1,0.8},  //t
				{ 0.05, 6.0,-1.0 , up1,1.0},  //r
				
		};

		for (double [] vertex : vertexTable) {
			worldrenderer.addVertexWithUV(vertex[0], vertex[1], vertex[2], vertex[3], vertex[4]);
		}
	}

    public void drawLaserVertices(TileEntity tileEntity, double relativeX, double relativeY, double relativeZ, float partialTicks, int blockDamageProgress){
   
    	if (!(tileEntity instanceof TileEntityObelisk)) return; // should never happen
		TileEntityObelisk tileEntityObelisk = (TileEntityObelisk) tileEntity;

    	double centreOffsetX = 0.5;
		double centreOffsetY = 0.0;
		double centreOffsetZ = 0.5;

		try {
			// save the transformation matrix and the rendering attributes, so that we can restore them after rendering.  This
			//   prevents us disrupting any vanilla TESR that render after ours.
			//  using try..finally is not essential but helps make it more robust in case of exceptions
			// For further information on rendering using the Tessellator, see http://greyminecraftcoder.blogspot.co.at/2014/12/the-tessellator-and-worldrenderer-18.html
			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_ENABLE_BIT);

			// First we need to set up the translation so that we render our gem with the bottom point at 0,0,0
			// when the renderTileEntityAt method is called, the tessellator is set up so that drawing a dot at [0,0,0] corresponds to the player's eyes
			// This means that, in order to draw a dot at the TileEntity [x,y,z], we need to translate the reference frame by the difference between the
			// two points, i.e. by the [relativeX, relativeY, relativeZ] passed to the method.  If you then draw a cube from [0,0,0] to [1,1,1], it will
			// render exactly over the top of the TileEntity's block.
			// In this example, the zero point of our model needs to be in the middle of the block, not at the [x,y,z] of the block, so we need to
			// add an extra offset as well, i.e. [gemCentreOffsetX, gemCentreOffsetY, gemCentreOffsetZ]
			GlStateManager.translate(relativeX + centreOffsetX, relativeY + centreOffsetY, relativeZ + centreOffsetZ);

			final double RENDER_HEIGHT = 0.5;        // desired render height
			final double MODEL_HEIGHT = 1.0;      // actual height of the gem in the vertexTable
			final double SCALE_FACTOR = RENDER_HEIGHT / MODEL_HEIGHT;
			GlStateManager.scale(SCALE_FACTOR, SCALE_FACTOR, SCALE_FACTOR);

			Tessellator tessellator = Tessellator.getInstance();
			WorldRenderer worldrenderer = tessellator.getWorldRenderer();
			
			this.bindTexture(beamTexture);         // texture for the gem appearance

			// set the key rendering flags appropriately...
			GL11.glDisable(GL11.GL_LIGHTING);     // turn off "item" lighting (face brightness depends on which direction it is facing)
			GL11.glDisable(GL11.GL_BLEND);        // turn off "alpha" transparency blending
			GL11.glDepthMask(true);               // gem is hidden behind other objects

			// change the "multitexturing" lighting value (default value is the brightness of the tile entity's block)
			// - this will make the gem "glow" brighter than the surroundings if it is dark.
			// glowmultiplier (0-1)
			float glowMultiplier = 1.0f; 
			final int SKY_LIGHT_VALUE = (int)(15 * glowMultiplier);
			final int BLOCK_LIGHT_VALUE = 0;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, SKY_LIGHT_VALUE * 16.0F, BLOCK_LIGHT_VALUE * 16.0F);

			worldrenderer.startDrawingQuads();
			//TRANSFORM target into local coordinate system
			double tipX = 0.0f;
			double tipY = 6.0f;
			double tipZ = -1.0f;
			
			double scale_correction =  MODEL_HEIGHT / RENDER_HEIGHT ;
			
			
			double target_x_offset = tileEntityObelisk.target.width;
			double target_y_offset = tileEntityObelisk.target.height * 0.75;
			double target_z_offset = tileEntityObelisk.target.width;
			
			double relative_targetX = ((tileEntityObelisk.target.posX - target_x_offset) - tileEntityObelisk.getPos().getX());
			double relative_targetY = ((tileEntityObelisk.target.posY + target_y_offset) - tileEntityObelisk.getPos().getY());
			double relative_targetZ = ((tileEntityObelisk.target.posZ - target_z_offset) - tileEntityObelisk.getPos().getZ());
			
			double targetX = scale_correction * (relative_targetX);
			double targetY = scale_correction * (relative_targetY);
			double targetZ = scale_correction * (relative_targetZ);
			
			
			double playerX = (-1.0) * (scale_correction * relativeX);
			double playerY = (-1.0) * (scale_correction * relativeY);
			double playerZ = (-1.0) * (scale_correction * relativeZ);
			
//			System.out.println("playerX=" + playerX);
//			System.out.println("playerY=" + playerY);
//			System.out.println("playerZ=" + playerZ);
//			
//			System.out.println("targetX=" + targetX);
//			System.out.println("targetY=" + targetY);
//			System.out.println("targetZ=" + targetZ);
			
			Vec3 pl = new Vec3(playerX , playerY , playerZ);			
			Vec3 en = new Vec3(targetX,targetY,targetZ);
			Vec3 st = new Vec3(tipX,tipY,tipZ);
			Vec3 ps = pl.subtractReverse(st);
			Vec3 se = st.subtractReverse(en);
			Vec3 cross = ps.crossProduct(se);
			cross = cross.normalize();
			double b = 0.15f;
			Vec3 across = new Vec3(cross.xCoord*b,cross.yCoord*b,cross.zCoord*b);
			Vec3 v1 = st.add(across);
			Vec3 v2 = st.subtract(across);
			Vec3 v3 = en.add(across);
			Vec3 v4 = en.subtract(across);
			
			Vec3 cross2 = cross.crossProduct(se);
			cross2 = cross2.normalize();
			Vec3 across2 = new Vec3(cross2.xCoord*b,cross2.yCoord*b,cross2.zCoord*b);
			Vec3 v5 = st.add(across2);
			Vec3 v6 = st.subtract(across2);
			Vec3 v7 = en.add(across2);
			Vec3 v8 = en.subtract(across2);

			final double[][] vertexTable = {	

				{v1.xCoord, v1.yCoord, v1.zCoord , 1.0,1.0},  
				{v5.xCoord, v5.yCoord, v5.zCoord, 1.0,0.0},  
				{v7.xCoord, v7.yCoord, v7.zCoord, 0.0,0.0},  
				{v3.xCoord, v3.yCoord, v4.zCoord, 0.0,1.0},
				
				{v5.xCoord, v5.yCoord, v5.zCoord , 1.0,1.0},  
				{v2.xCoord, v2.yCoord, v2.zCoord, 1.0,0.0},  
				{v4.xCoord, v4.yCoord, v4.zCoord, 0.0,0.0},  
				{v7.xCoord, v7.yCoord, v7.zCoord, 0.0,1.0},
				
				{v2.xCoord, v2.yCoord, v2.zCoord , 1.0,1.0},  
				{v6.xCoord, v6.yCoord, v6.zCoord, 1.0,0.0},  
				{v8.xCoord, v8.yCoord, v8.zCoord, 0.0,0.0},  
				{v4.xCoord, v4.yCoord, v4.zCoord, 0.0,1.0},
				
				{v6.xCoord, v6.yCoord, v6.zCoord , 1.0,1.0},  
				{v1.xCoord, v1.yCoord, v1.zCoord, 1.0,0.0},  
				{v3.xCoord, v3.yCoord, v3.zCoord, 0.0,0.0},  
				{v8.xCoord, v8.yCoord, v8.zCoord, 0.0,1.0},
								
			};

		for (double [] vertex : vertexTable) {
			worldrenderer.addVertexWithUV(vertex[0], vertex[1], vertex[2], vertex[3], vertex[4]);
		}
			
			tessellator.draw();

		} finally {
			GL11.glPopAttrib();
			GL11.glPopMatrix();
		}

    	

    }
}
