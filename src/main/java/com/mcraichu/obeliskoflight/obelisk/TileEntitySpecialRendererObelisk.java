package com.mcraichu.obeliskoflight.obelisk;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.mcraichu.obeliskoflight.Reference;
import com.mcraichu.obeliskoflight.utilities.utilities;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class TileEntitySpecialRendererObelisk extends TileEntitySpecialRenderer{
	
	 private static final ResourceLocation gemTexture = new ResourceLocation(Reference.MODID + ":" + "textures/entity/obelisk.png");

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
		TileEntityObelisk tileEntityMBE21 = (TileEntityObelisk) tileEntity;

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

		final double pedestalCentreOffsetX = 0.5;
		final double pedestalCentreOffsetY = 0.0;
		final double pedestalCentreOffsetZ = 0.5;
		Vec3 playerEye = new Vec3(0.0, 0.0, 0.0);
		Vec3 pedestalCentre = new Vec3(relativeX + pedestalCentreOffsetX, relativeY + pedestalCentreOffsetY, relativeZ + pedestalCentreOffsetZ);
		double playerDistance = playerEye.distanceTo(pedestalCentre);

		final double DISTANCE_FOR_MIN_SPIN = 8.0;
		final double DISTANCE_FOR_MAX_SPIN = 4.0;
		final double DISTANCE_FOR_MIN_GLOW = 16.0;
		final double DISTANCE_FOR_MAX_GLOW = 4.0;
		final double DISTANCE_FOR_MIN_LEVITATE = 4.0;
		final double DISTANCE_FOR_MAX_LEVITATE = 2.0;

		final double MIN_LEVITATE_HEIGHT = 0.0;
		final double MAX_LEVITATE_HEIGHT = 0.5;
		double gemCentreOffsetX = pedestalCentreOffsetX;
		double gemCentreOffsetY = 0.0;// pedestalCentreOffsetY + utilities.interpolate(playerDistance, DISTANCE_FOR_MIN_LEVITATE, DISTANCE_FOR_MAX_LEVITATE,
			//	MIN_LEVITATE_HEIGHT, MAX_LEVITATE_HEIGHT);
		double gemCentreOffsetZ = pedestalCentreOffsetZ;

		final double MIN_GLOW = 0.0;
		final double MAX_GLOW = 1.0;
		double glowMultiplier = utilities.interpolate(playerDistance, DISTANCE_FOR_MIN_GLOW, DISTANCE_FOR_MAX_GLOW,
				MIN_GLOW, MAX_GLOW);

		final double MIN_REV_PER_SEC = 0.0;
		final double MAX_REV_PER_SEC = 0.5;
		double revsPerSecond = utilities.interpolate(playerDistance, DISTANCE_FOR_MIN_SPIN, DISTANCE_FOR_MAX_SPIN,
				MIN_REV_PER_SEC, MAX_REV_PER_SEC);
		double angularPositionInDegrees = tileEntityMBE21.getNextAngularPosition(revsPerSecond);

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
			GlStateManager.translate(relativeX + gemCentreOffsetX, relativeY + gemCentreOffsetY, relativeZ + gemCentreOffsetZ);

			//GlStateManager.rotate((float)angularPositionInDegrees, 0, 1, 0);   // rotate around the vertical axis

			final double GEM_HEIGHT = 0.5;        // desired render height of the gem
			final double MODEL_HEIGHT = 1.0;      // actual height of the gem in the vertexTable
			final double SCALE_FACTOR = GEM_HEIGHT / MODEL_HEIGHT;
			GlStateManager.scale(SCALE_FACTOR, SCALE_FACTOR, SCALE_FACTOR);

			Tessellator tessellator = Tessellator.getInstance();
			WorldRenderer worldrenderer = tessellator.getWorldRenderer();
			
			this.bindTexture(gemTexture);         // texture for the gem appearance

			// set the key rendering flags appropriately...
			GL11.glDisable(GL11.GL_LIGHTING);     // turn off "item" lighting (face brightness depends on which direction it is facing)
			GL11.glDisable(GL11.GL_BLEND);        // turn off "alpha" transparency blending
			GL11.glDepthMask(true);               // gem is hidden behind other objects

			// set the rendering colour as the gem base colour
			Color fullBrightnessColor = tileEntityMBE21.getGemColour();
			float red = 0, green = 0, blue = 0;
			if (fullBrightnessColor != TileEntityObelisk.INVALID_COLOR) {
				red = (float) (fullBrightnessColor.getRed() / 255.0);
				green = (float) (fullBrightnessColor.getGreen() / 255.0);
				blue = (float) (fullBrightnessColor.getBlue() / 255.0);
			}
			GlStateManager.color(red, green, blue);     // change the rendering colour

			// change the "multitexturing" lighting value (default value is the brightness of the tile entity's block)
			// - this will make the gem "glow" brighter than the surroundings if it is dark.
			final int SKY_LIGHT_VALUE = (int)(15 * glowMultiplier);
			final int BLOCK_LIGHT_VALUE = 0;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, SKY_LIGHT_VALUE * 16.0F, BLOCK_LIGHT_VALUE * 16.0F);

			worldrenderer.startDrawingQuads();
			//worldrenderer.startDrawing(GL11.GL_TRIANGLES);
			addGemVertices(worldrenderer);
			tessellator.draw();

		} finally {
			GL11.glPopAttrib();
			GL11.glPopMatrix();
		}
	}

	// add the vertices for drawing the gem.  Generated using a model builder and pasted manually because the object model
	//   loader is (not yet?) implemented.
	private void addGemVertices(WorldRenderer worldrenderer) {
		final double[][] vertexTable = {
				
				//bottom
				{ 0.8, 0.0,-1.0, 1.0,0.8},  //d
				{ 0.8, 0.0, 1.0, 1.0,0.0},  //g
				{-0.8, 0.0, 1.0, 0.0,0.0},  //e
				{-0.8, 0.0,-1.0, 0.0,0.8},  //a
				
				//1 etage
				{-0.8, 0.0,-1.0, 1.0,0.8},  //a
				{-0.8, 2.0  , 0.0, 1.0,0.0},  //b
				{ 0.8, 2.0  , 0.0, 0.0,0.0},  //c
				{ 0.8, 0.0,-1.0, 0.0,0.8},  //d
				
				{-0.8, 0.0, 1.0, 1.0,0.8},  //e
				{-0.8, 2.0  , 1.0, 1.0,0.0},  //f
				{-0.8, 2.0  , 0.0, 0.0,0.0},  //b
				{-0.8, 0.0,-1.0, 0.0,0.8},  //a
				
				{ 0.8, 0.0, 1.0, 1.0,0.8},  //g
				{ 0.8, 2.0  , 1.0, 1.0,0.0},  //h
				{-0.8, 2.0  , 1.0, 0.0,0.0},  //f
				{-0.8, 0.0, 1.0, 0.0,0.8},  //e
				
				{ 0.8, 0.0,-1.0, 1.0,0.8},  //d
				{ 0.8, 2.0  , 0.0, 1.0,0.0},  //c				
				{ 0.8, 2.0  , 1.0, 0.0,0.0},  //h
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

				//tip
				{-0.35, 5.3,-0.65, 1.0,1.0},  //m
				{-0.05, 6.0,-1.0 , 1.0,0.8},  //q
				{ 0.05, 6.0,-1.0 , 0.0,0.8},  //r
				{0.35 , 5.3,-0.65, 0.0,1.0},  //n

				{-0.35, 5.5,-0.3 , 1.0,1.0},  //o
				{-0.05, 6.0,-0.95, 1.0,0.8},  //s
				{-0.05, 6.0,-1.0 , 0.0,0.8},  //q
				{-0.35, 5.3,-0.65, 0.0,1.0},  //m

				{ 0.35, 5.5,-0.3 , 1.0,1.0},  //p
				{ 0.05, 6.0,-0.95, 1.0,0.8},  //t
				{-0.05, 6.0,-0.95, 0.0,0.8},  //s
				{-0.35, 5.5,-0.3 , 0.0,1.0},  //o

				{ 0.35, 5.3,-0.65, 1.0,1.0},  //n				
				{ 0.05, 6.0,-1.0 , 1.0,0.8},  //r
				{ 0.05, 6.0,-0.95, 0.0,0.8},  //t
				{ 0.35, 5.5,-0.3 , 0.0,1.0},  //p

				//top
				{-0.05, 6.0,-1.0 , 1.0,1.0},  //q
				{-0.05, 6.0,-0.95, 1.0,0.8},  //s
				{ 0.05, 6.0,-0.95, 0.0,0.8},  //t
				{ 0.05, 6.0,-1.0 , 0.0,1.0},  //r
				
		};

		for (double [] vertex : vertexTable) {
			worldrenderer.addVertexWithUV(vertex[0], vertex[1], vertex[2], vertex[3], vertex[4]);
		}
	}


}
