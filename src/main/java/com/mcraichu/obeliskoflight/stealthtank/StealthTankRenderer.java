package com.mcraichu.obeliskoflight.stealthtank;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.mcraichu.obeliskoflight.Reference;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerSaddle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class StealthTankRenderer extends RenderLiving
{
	private static final ResourceLocation stealthTankTextures = new ResourceLocation(Reference.MODID+ ":textures/entity/stealth_tank.png");
	private static final ResourceLocation stealthTankHidden1Textures = new ResourceLocation(Reference.MODID+ ":textures/entity/stealth_tank_hidden1.png");
	private static final ResourceLocation stealthTankHidden2Textures = new ResourceLocation(Reference.MODID+ ":textures/entity/stealth_tank_hidden2.png");
	private static final ResourceLocation stealthTankHidden3Textures = new ResourceLocation(Reference.MODID+ ":textures/entity/stealth_tank_hidden3.png");

	public StealthTankRenderer(RenderManager p_i46149_1_, ModelBase p_i46149_2_, float p_i46149_3_)
	{
		super(p_i46149_1_, p_i46149_2_, p_i46149_3_);
	}

	protected ResourceLocation func_180583_a(StealthTank p_180583_1_)
	{
		if(p_180583_1_.getAttacking())
		{
			if(p_180583_1_.getUnstealthTimer() > 7){
				return stealthTankHidden1Textures;
			}else if(p_180583_1_.getUnstealthTimer() > 4){
				return stealthTankHidden2Textures;
			}else if(p_180583_1_.getUnstealthTimer() > 1){
				return stealthTankHidden3Textures;
			}else {
				return stealthTankTextures;
			}			
		}else{
			if(p_180583_1_.getStealthTimer() > 7){
				return stealthTankTextures;
			}else if(p_180583_1_.getStealthTimer() > 4){
				return stealthTankHidden3Textures;
			}else if(p_180583_1_.getStealthTimer() > 1){
				return stealthTankHidden2Textures;
			}else {
				return stealthTankHidden1Textures;
			}
		}
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.func_180583_a((StealthTank)entity);
	}
}