package com.mcraichu.obeliskoflight.nodturret;

import org.lwjgl.opengl.GL11;

import com.mcraichu.obeliskoflight.Reference;
import com.mcraichu.obeliskoflight.gemoflight.TileEntityGemOfLight;
import com.mcraichu.obeliskoflight.utilities.Utilities;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntitySpecialRendererNodTurret extends TileEntitySpecialRenderer{

	private static final ResourceLocation nodTurretBaseTexture = new ResourceLocation(Reference.MODID + ":" + "textures/entity/nod_turret_base.png");
	private static final ResourceLocation nodTurretTopTexture = new ResourceLocation(Reference.MODID + ":" + "textures/entity/rustywhitemetal.png");
	private static final ResourceLocation nodTurretBarrelTexture = new ResourceLocation(Reference.MODID + ":" + "textures/entity/nod_turret_barrel.png");

	private double angularPositionInDegrees;
	private double angularHeightInDegrees; 


	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double relativeX, double relativeY, double relativeZ, float partialTicks, int blockDamageProgress) {
		if (!(tileEntity instanceof TileEntityNodTurret)) return; // should never happen
		TileEntityNodTurret tileEntityNodTurret = (TileEntityNodTurret) tileEntity;

		angularPositionInDegrees = tileEntityNodTurret.currentTargetYaw; //getNextAngularPosition(0.3);
		angularHeightInDegrees = tileEntityNodTurret.currentTargetPitch; //getNextAngularPosition(0.3);
		drawBase(tileEntityNodTurret, relativeX, relativeY, relativeZ, partialTicks, blockDamageProgress);
		drawTurret(tileEntityNodTurret, relativeX, relativeY, relativeZ, partialTicks, blockDamageProgress);
		drawBarrel(tileEntityNodTurret, relativeX, relativeY, relativeZ, partialTicks, blockDamageProgress);
		//tileEntityNodTurret.aimed = true;

	}

	private void drawBase(TileEntityNodTurret tileEntity, double relativeX, double relativeY, double relativeZ, float partialTicks, int blockDamageProgress){

		double centreOffsetX = 0.5;
		double centreOffsetY = 0.0;
		double centreOffsetZ = 0.5;
		Vec3 playerEye = new Vec3(0.0, 0.0, 0.0);
		Vec3 pedestalCentre = new Vec3(relativeX + centreOffsetX, relativeY + centreOffsetY, relativeZ + centreOffsetZ);
		double playerDistance = playerEye.distanceTo(pedestalCentre);

		//double angularPositionInDegrees = tileEntityNodTurret.getNextAngularPosition(revsPerSecond);

		try {
			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
			RenderHelper.disableStandardItemLighting();

			

			GlStateManager.translate(relativeX + centreOffsetX, relativeY + centreOffsetY, relativeZ + centreOffsetZ);

			//GlStateManager.rotate((float)angularPositionInDegrees, 0, 1, 0);   // rotate around the vertical axis

			final double GEM_HEIGHT = 0.5;        // desired render height of the gem
			final double MODEL_HEIGHT = 1.0;      // actual height of the gem in the vertexTable
			final double SCALE_FACTOR = GEM_HEIGHT / MODEL_HEIGHT;
			GlStateManager.scale(SCALE_FACTOR, SCALE_FACTOR, SCALE_FACTOR);

			Tessellator tessellator = Tessellator.getInstance();
			WorldRenderer worldrenderer = tessellator.getWorldRenderer();
			this.bindTexture(nodTurretBaseTexture);         // texture for the gem appearance

			// set the key rendering flags appropriately...
			GL11.glDisable(GL11.GL_LIGHTING);     // turn off "item" lighting (face brightness depends on which direction it is facing)
			GL11.glDisable(GL11.GL_BLEND);        // turn off "alpha" transparency blending
			GL11.glDepthMask(true);               // gem is hidden behind other objects

			final int SKY_LIGHT_VALUE = (int)(15 * 1.0f);//glowMultiplier);
			final int BLOCK_LIGHT_VALUE = 0;
			
			World world = this.getWorld();
			BlockPos pos = tileEntity.getPos();
			Block block = world.getBlockState(pos).getBlock();
			
            float brightness = block.getLightValue(world,pos);
            float skyLight = world.getLightBrightness(pos);
            float modulousModifier = (skyLight * 15);
            float divModifier =  (brightness * 15);
                        
            //worldrenderer.setColorOpaque_F(brightness, brightness, brightness);
            //OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,  (float)modulousModifier*16,  (float)divModifier*16);
			
			//OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, SKY_LIGHT_VALUE * 15.0F, BLOCK_LIGHT_VALUE);

            
			worldrenderer.startDrawingQuads();
			addBaseVertices(worldrenderer);
			tessellator.draw();


		} finally {
			
			RenderHelper.enableStandardItemLighting();
			GL11.glPopAttrib();
			GL11.glPopMatrix();
		}
	}

	private void drawTurret(TileEntityNodTurret tileEntity, double relativeX, double relativeY, double relativeZ, float partialTicks, int blockDamageProgress){

		double centreOffsetX = 0.5;
		double centreOffsetY = 0.7;
		double centreOffsetZ = 0.5;
		Vec3 playerEye = new Vec3(0.0, 0.0, 0.0);
		Vec3 pedestalCentre = new Vec3(relativeX + centreOffsetX, relativeY + centreOffsetY, relativeZ + centreOffsetZ);
		double playerDistance = playerEye.distanceTo(pedestalCentre);

		//double angularPositionInDegrees = tileEntityNodTurret.getNextAngularPosition(revsPerSecond);

		try {
			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
			RenderHelper.disableStandardItemLighting();

			GlStateManager.translate(relativeX + centreOffsetX, relativeY + centreOffsetY, relativeZ + centreOffsetZ);

			GlStateManager.rotate((float)angularPositionInDegrees, 0, 1, 0);   // rotate around the vertical axis

			final double GEM_HEIGHT = 0.5;        // desired render height of the gem
			final double MODEL_HEIGHT = 1.0;      // actual height of the gem in the vertexTable
			final double SCALE_FACTOR = GEM_HEIGHT / MODEL_HEIGHT;
			GlStateManager.scale(SCALE_FACTOR, SCALE_FACTOR, SCALE_FACTOR);

			Tessellator tessellator = Tessellator.getInstance();
			WorldRenderer worldrenderer = tessellator.getWorldRenderer();
			this.bindTexture(nodTurretTopTexture);         // texture for the gem appearance

			// set the key rendering flags appropriately...
			GL11.glDisable(GL11.GL_LIGHTING);     // turn off "item" lighting (face brightness depends on which direction it is facing)
			GL11.glDisable(GL11.GL_BLEND);        // turn off "alpha" transparency blending
			GL11.glDepthMask(true);               // gem is hidden behind other objects

			final int SKY_LIGHT_VALUE = (int)(15 * 1.0f);//glowMultiplier);
			final int BLOCK_LIGHT_VALUE = 0;
			//OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, SKY_LIGHT_VALUE * 15.0F, BLOCK_LIGHT_VALUE * 15.0F);
			//RenderHelper.disableStandardItemLighting();
			//Minecraft.getMinecraft().entityRenderer.disableLightmap();

			worldrenderer.startDrawingQuads();
			addTurretVertices(worldrenderer);
			tessellator.draw();

		} finally {
			RenderHelper.enableStandardItemLighting();
			GL11.glPopAttrib();
			GL11.glPopMatrix();
		}
	}

	private void drawBarrel(TileEntityNodTurret tileEntity, double relativeX, double relativeY, double relativeZ, float partialTicks, int blockDamageProgress){

		double centreOffsetX = 0.5;
		double centreOffsetY = 0.85;
		double centreOffsetZ = 0.5;
		Vec3 playerEye = new Vec3(0.0, 0.0, 0.0);
		Vec3 pedestalCentre = new Vec3(relativeX + centreOffsetX, relativeY + centreOffsetY, relativeZ + centreOffsetZ);
		double playerDistance = playerEye.distanceTo(pedestalCentre);

		//double angularPositionInDegrees = tileEntityNodTurret.getNextAngularPosition(revsPerSecond);

		try {
			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
			RenderHelper.disableStandardItemLighting();

			GlStateManager.translate(relativeX + centreOffsetX, relativeY + centreOffsetY, relativeZ + centreOffsetZ);

			GlStateManager.rotate((float)angularPositionInDegrees, 0, 1, 0);   // rotate around the vertical axis
			GlStateManager.rotate((float)angularHeightInDegrees, 0, 0, 1); //30 - 345

			final double GEM_HEIGHT = 0.5;        // desired render height of the gem
			final double MODEL_HEIGHT = 1.0;      // actual height of the gem in the vertexTable
			final double SCALE_FACTOR = GEM_HEIGHT / MODEL_HEIGHT;
			GlStateManager.scale(SCALE_FACTOR, SCALE_FACTOR, SCALE_FACTOR);

			Tessellator tessellator = Tessellator.getInstance();
			WorldRenderer worldrenderer = tessellator.getWorldRenderer();
			this.bindTexture(nodTurretBarrelTexture);         // texture for the gem appearance

			// set the key rendering flags appropriately...
			GL11.glDisable(GL11.GL_LIGHTING);     // turn off "item" lighting (face brightness depends on which direction it is facing)
			GL11.glDisable(GL11.GL_BLEND);        // turn off "alpha" transparency blending
			GL11.glDepthMask(true);               // gem is hidden behind other objects

			final int SKY_LIGHT_VALUE = (int)(15 * 1.0f);//glowMultiplier);
			final int BLOCK_LIGHT_VALUE = 0;
			//OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, SKY_LIGHT_VALUE * 15.0F, BLOCK_LIGHT_VALUE * 15.0F);
			//RenderHelper.disableStandardItemLighting();
			//Minecraft.getMinecraft().entityRenderer.disableLightmap();

			worldrenderer.startDrawingQuads();
			addBarrelVertices(worldrenderer);
			tessellator.draw();

		} finally {
			RenderHelper.enableStandardItemLighting();
			GL11.glPopAttrib();
			GL11.glPopMatrix();
		}
	}

	// add the vertices for drawing.
	private void addBaseVertices(WorldRenderer worldrenderer) {
		final double[][] vertexTable = {
				//bottom
				{ 1.0 , 0.0 ,-1.0 , 1.0 , 1.0},
				{ 1.0 , 0.0 , 1.0 , 1.0 , 0.0},  //g
				{-1.0 , 0.0 , 1.0 , 0.0 , 0.0},
				{-1.0 , 0.0 ,-1.0 , 0.0 , 1.0},

				//base part 1
				{-1.0 , 0.0 ,-1.0 , 1.0 , 1.0},  //a
				{-0.7 , 1.3 ,-0.7 , 1.0 , 0.0},  //b
				{ 0.7 , 1.3 ,-0.7 , 0.0 , 0.0},  //c
				{ 1.0 , 0.0 ,-1.0 , 0.0 , 1.0},  //d


				{-1.0 , 0.0 , 1.0 , 1.0 , 1.0},  //e
				{-0.7 , 1.3 , 0.7 , 1.0 , 0.0},  //f
				{-0.7 , 1.3 ,-0.7 , 0.0 , 0.0},  //b
				{-1.0 , 0.0 ,-1.0 , 0.0 , 1.0},  //a


				{ 1.0 , 0.0 , 1.0 , 1.0 , 1.0},  //g
				{ 0.7 , 1.3 , 0.7 , 1.0 , 0.0},  //h
				{-0.7 , 1.3 , 0.7 , 0.0 , 0.0},  //f
				{-1.0 , 0.0 , 1.0 , 0.0 , 1.0},  //e


				{ 1.0 , 0.0 ,-1.0 , 1.0 , 1.0},  //d
				{ 0.7 , 1.3 ,-0.7 , 1.0 , 0.0},  //c
				{ 0.7 , 1.3 , 0.7 , 0.0 , 0.0},  //h
				{ 1.0 , 0.0 , 1.0 , 0.0 , 1.0},  //g


				//base top 1
				{-0.7 , 1.3 ,-0.7 , 1.0 , 1.0},  //b
				{-0.7 , 1.3 , 0.7 , 1.0 , 0.0},  //f
				{ 0.7 , 1.3 , 0.7 , 0.0 , 0.0},  //h
				{ 0.7 , 1.3 ,-0.7 , 0.0 , 1.0},  //c

				//base part 2
				{-0.25 , 1.3 ,-0.25 , 1.0 , 1.0},  //a
				{-0.25 , 1.75 ,-0.25 , 1.0 , 0.0},  //b
				{ 0.25 , 1.75 ,-0.25 , 0.0 , 0.0},  //c
				{ 0.25 , 1.3 ,-0.25 , 0.0 , 1.0},  //d


				{-0.25 , 1.3 , 0.25 , 1.0 , 1.0},  //e
				{-0.25 , 1.75 , 0.25 , 1.0 , 0.0},  //f
				{-0.25 , 1.75 ,-0.25 , 0.0 , 0.0},  //b
				{-0.25 , 1.3 ,-0.25 , 0.0 , 1.0},  //a


				{ 0.25 , 1.3 , 0.25 , 1.0 , 1.0},  //g
				{ 0.25 , 1.75 , 0.25 , 1.0 , 0.0},  //h
				{-0.25 , 1.75 , 0.25 , 0.0 , 0.0},  //f
				{-0.25 , 1.3 , 0.25 , 0.0 , 1.0},  //e


				{ 0.25 , 1.3 ,-0.25 , 1.0 , 1.0},  //d
				{ 0.25 , 1.75 ,-0.25 , 1.0 , 0.0},  //c
				{ 0.25 , 1.75 , 0.25 , 0.0 , 0.0},  //h
				{ 0.25 , 1.3 , 0.25 , 0.0 , 1.0},  //g


				//base top 2
				{-0.25 , 1.75 ,-0.25 , 1.0 , 1.0},  //b
				{-0.25 , 1.75 , 0.25 , 1.0 , 0.0},  //f
				{ 0.25 , 1.75 , 0.25 , 0.0 , 0.0},  //h
				{ 0.25 , 1.75 ,-0.25 , 0.0 , 1.0},  //c


		};

		for (double [] vertex : vertexTable) {
			worldrenderer.addVertexWithUV(vertex[0], vertex[1], vertex[2], vertex[3], vertex[4]);
		}
	}

	// add the vertices for drawing.
	private void addTurretVertices(WorldRenderer worldrenderer) {
		final double[][] vertexTable = {
				//bottom					
				{ 0.6 , 0.0 ,-1.0 , 0.8 , 1.0}, //ba
				{ 1.0 , 0.0 ,-0.6 , 1.0 , 0.8}, //bb
				{ 1.0 , 0.0 , 0.6 , 1.0 , 0.2}, //bc
				{ 0.6 , 0.0 , 1.0 , 0.8 , 0.0}, //bd

				{-0.6 , 0.0 ,-1.0 , 0.2 , 1.0}, //ba2
				{ 0.6 , 0.0 ,-1.0 , 0.8 , 1.0}, //ba
				{ 0.6 , 0.0 , 1.0 , 0.8 , 0.2}, //bd
				{-0.6 , 0.0 , 1.0 , 0.2 , 0.2}, //bd2

				{-1.0 , 0.0 ,-0.6 , 0.0 , 0.8}, //ba3
				{-0.6 , 0.0 ,-1.0 , 0.2 , 1.0}, //ba2
				{-0.6 , 0.0 , 1.0 , 0.2 , 0.0}, //bd2
				{-1.0 , 0.0 , 0.6 , 0.0 , 0.2}, //bd3

				//side1 front
				{ 0.6 , 0.0 ,-1.0 , 1.0 , 1.0}, //ba
				{ 0.2 , 0.6 ,-1.0 , 1.0 , 0.0}, //ba
				{ 0.6 , 0.6 ,-0.6 , 0.0 , 0.0}, //bb
				{ 1.0 , 0.0 ,-0.6 , 0.0 , 1.0}, //bb

				//side2 front
				{ 1.0 , 0.0 ,-0.6 , 1.0 , 1.0}, //bb
				{ 0.6 , 0.6 ,-0.6 , 1.0 , 0.0}, //bb
				{ 0.6 , 0.6 , 0.6 , 0.0 , 0.0}, //bc
				{ 1.0 , 0.0 , 0.6 , 0.0 , 1.0}, //bc

				//side3 front
				{ 1.0 , 0.0 , 0.6 , 1.0 , 1.0}, //bc
				{ 0.6 , 0.6 , 0.6 , 1.0 , 0.0}, //bc
				{ 0.2 , 0.6 , 1.0 , 0.0 , 0.0}, //bd
				{ 0.6 , 0.0 , 1.0 , 0.0 , 1.0}, //bd

				//side4
				{-0.6 , 0.0 ,-1.0 , 1.0 , 1.0}, //ba2
				{-0.6 , 0.6 ,-1.0 , 1.0 , 0.0}, //ba2
				{ 0.2 , 0.6 ,-1.0 , 0.0 , 0.0}, //ba
				{ 0.6 , 0.0 ,-1.0 , 0.0 , 1.0}, //ba

				//side5
				{ 0.6 , 0.0 , 1.0 , 1.0 , 1.0}, //bd
				{ 0.2 , 0.6 , 1.0 , 1.0 , 0.0}, //bd
				{-0.6 , 0.6 , 1.0 , 0.0 , 0.0}, //bd2
				{-0.6 , 0.0 , 1.0 , 0.0 , 1.0}, //bd2

				//side6
				{-1.0 , 0.0 ,-0.6 , 1.0 , 1.0}, //ba3
				{-1.0 , 0.6 ,-0.6 , 1.0 , 0.0}, //ba3
				{-0.6 , 0.6 ,-1.0 , 0.0 , 0.0}, //ba2
				{-0.6 , 0.0 ,-1.0 , 0.0 , 1.0}, //ba2

				//side7

				{-1.0 , 0.6 ,-0.6 , 1.0 , 1.0}, //ba3
				{-1.0 , 0.0 ,-0.6 , 1.0 , 0.0}, //ba3
				{-1.0 , 0.0 , 0.6 , 0.0 , 0.0}, //bd3
				{-1.0 , 0.6 , 0.6 , 0.0 , 1.0}, //bd3


				//side8
				{-0.6 , 0.0 , 1.0 , 1.0 , 1.0}, //bd2
				{-0.6 , 0.6 , 1.0 , 1.0 , 0.0}, //bd2
				{-1.0 , 0.6 , 0.6 , 0.0 , 0.0}, //bd3
				{-1.0 , 0.0 , 0.6 , 0.0 , 1.0}, //bd3

				//top
				{ 0.6 , 0.6 ,-0.6 , 0.8 , 0.8}, //bb
				{ 0.2 , 0.6 ,-1.0 , 0.6 , 1.0}, //ba
				{ 0.2 , 0.6 , 1.0 , 0.6 , 0.0}, //bd
				{ 0.6 , 0.6 , 0.6 , 0.8 , 0.2}, //bc

				{ 0.2 , 0.6 ,-1.0 , 0.6 , 1.0}, //ba
				{-0.6 , 0.6 ,-1.0 , 0.2 , 1.0}, //ba2
				{-0.6 , 0.6 , 1.0 , 0.2 , 0.0}, //bd2
				{ 0.2 , 0.6 , 1.0 , 0.6 , 0.0}, //bd

				{-0.6 , 0.6 ,-1.0 , 0.2 , 1.0}, //ba2
				{-1.0 , 0.6 ,-0.6 , 0.0 , 0.8}, //ba3
				{-1.0 , 0.6 , 0.6 , 0.0 , 0.2}, //bd3
				{-0.6 , 0.6 , 1.0 , 0.2 , 0.0}, //bd2
		};

		for (double [] vertex : vertexTable) {
			worldrenderer.addVertexWithUV(vertex[0], vertex[1], vertex[2], vertex[3], vertex[4]);
		}
	}

	// add the vertices for drawing.
	private void addBarrelVertices(WorldRenderer worldrenderer) {
		final double[][] vertexTable = {
				//bottom					
				{ 0.0 ,-0.15 ,-0.15 , 0.75 , 1.0}, //
				{ 0.0 , 0.15 ,-0.15 , 0.75 , 0.0}, //
				{ 1.75 , 0.15 ,-0.15 , 0.0 , 0.0}, //
				{ 1.75 ,-0.15 ,-0.15 , 0.0 , 1.0}, //

				{ 0.0 , 0.15 ,-0.15 , 0.75 , 1.0}, //
				{ 0.0 , 0.15 , 0.15 , 0.75 , 0.0}, //
				{ 1.75 , 0.15 , 0.15 , 0.0 , 0.0}, //
				{ 1.75 , 0.15 ,-0.15 , 0.0 , 1.0}, //

				{ 0.0 , 0.15 , 0.15 , 0.75 , 1.0}, //
				{ 0.0 ,-0.15 , 0.15 , 0.75 , 0.0}, //
				{ 1.75 ,-0.15 , 0.15 , 0.0 , 0.0}, //
				{ 1.75 , 0.15 , 0.15 , 0.0 , 1.0}, //
				
				{ 0.0 ,-0.15 , 0.15 , 0.75 , 1.0}, //
				{ 0.0 ,-0.15 ,-0.15 , 0.75 , 0.0}, //
				{ 1.75 ,-0.15 ,-0.15 , 0.0 , 0.0}, //
				{ 1.75 ,-0.15 , 0.15 , 0.0 , 1.0}, //
				
				{ 1.75 ,-0.15 ,-0.15 , 1.0 , 1.0}, //
				{ 1.75 , 0.15 ,-0.15 , 1.0 , 0.8}, //
				{ 1.75 , 0.15 , 0.15 , 0.75 , 0.8}, //
				{ 1.75 ,-0.15 , 0.15 , 0.75 , 1.0}, //
				


		};

		for (double [] vertex : vertexTable) {
			worldrenderer.addVertexWithUV(vertex[0], vertex[1], vertex[2], vertex[3], vertex[4]);
		}
	}

}
