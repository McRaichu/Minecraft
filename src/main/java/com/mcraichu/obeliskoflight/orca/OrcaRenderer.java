package com.mcraichu.obeliskoflight.orca;

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
public class OrcaRenderer extends RenderLiving
{
	private static final ResourceLocation orcaTextures = new ResourceLocation(Reference.MODID+ ":textures/entity/orca.png");

	public OrcaRenderer(RenderManager p_i46149_1_, ModelBase p_i46149_2_, float p_i46149_3_)
	{
		super(p_i46149_1_, p_i46149_2_, p_i46149_3_);
	}

	protected ResourceLocation func_180583_a(Orca p_180583_1_)
	{
		return orcaTextures;
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.func_180583_a((Orca)entity);
	}
}