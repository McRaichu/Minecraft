package com.mcraichu.obeliskoflight.hovercraft;

import com.mcraichu.obeliskoflight.Reference;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HovercraftRenderer extends Render {


	    private static final ResourceLocation hovercraftTextures = new ResourceLocation(Reference.MODID+ ":textures/entity/hovercraft.png");
	    /** instance of ModelBoat for rendering */
	    protected ModelBase modelHovercraft = new ModelHovercraft();

	    public HovercraftRenderer(RenderManager p_i46190_1_)
	    {
	        super(p_i46190_1_);
	        this.shadowSize = 0.5F;
	    }

	    public void doRender(Hovercraft p_180552_1_, double p_180552_2_, double p_180552_4_, double p_180552_6_, float p_180552_8_, float p_180552_9_)
	    {
	        GlStateManager.pushMatrix();
	        GlStateManager.translate((float)p_180552_2_, (float)p_180552_4_ + 0.05, (float)p_180552_6_);
	        GlStateManager.rotate(90.0F - p_180552_8_, 0.0F, 1.0F, 0.0F);
	        float f2 = (float)p_180552_1_.getTimeSinceHit() - p_180552_9_;
	        float f3 = p_180552_1_.getDamageTaken() - p_180552_9_;

	        if (f3 < 0.0F)
	        {
	            f3 = 0.0F;
	        }

	        if (f2 > 0.0F)
	        {
	            GlStateManager.rotate(MathHelper.sin(f2) * f2 * f3 / 10.0F * (float)p_180552_1_.getForwardDirection(), 1.0F, 0.0F, 0.0F);
	        }

	        float f4 = 0.75F;
	        GlStateManager.scale(f4, f4, f4);
	        GlStateManager.scale(1.0F / f4, 1.0F / f4, 1.0F / f4);
	        this.bindEntityTexture(p_180552_1_);
	        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
	        this.modelHovercraft.render(p_180552_1_, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
	        GlStateManager.popMatrix();
	        super.doRender(p_180552_1_, p_180552_2_, p_180552_4_, p_180552_6_, p_180552_8_, p_180552_9_);
	    }

	    protected ResourceLocation getEntityTexture(Hovercraft p_180553_1_)
	    {
	        return hovercraftTextures;
	    }

	    /**
	     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	     */
	    protected ResourceLocation getEntityTexture(Entity entity)
	    {
	        return this.getEntityTexture((Hovercraft)entity);
	    }

	    /**
	     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	     * (Render<T extends Entity>) and this method has signature public void func_76986_a(T entity, double d, double d1,
	     * double d2, float f, float f1). But JAD is pre 1.5 so doe
	     */
	    public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	    {
	        this.doRender((Hovercraft)entity, x, y, z, p_76986_8_, partialTicks);
	    }
}
