package com.mcraichu.obeliskoflight.reconbike;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.mcraichu.obeliskoflight.Reference;
import com.mcraichu.obeliskoflight.stealthtank.StealthTank;

@SideOnly(Side.CLIENT)
public class ReconBikeRenderer extends RenderLiving
{
	private static final ResourceLocation reconBikeTextures = new ResourceLocation(Reference.MODID+ ":textures/entity/reconbike.png");

	public ReconBikeRenderer(RenderManager p_i46149_1_, ModelBase p_i46149_2_, float p_i46149_3_)
	{
		super(p_i46149_1_, p_i46149_2_, p_i46149_3_);
	}

	protected ResourceLocation func_180583_a(ReconBike p_180583_1_)
	{
		return reconBikeTextures;
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.func_180583_a((ReconBike)entity);
	}
		
}