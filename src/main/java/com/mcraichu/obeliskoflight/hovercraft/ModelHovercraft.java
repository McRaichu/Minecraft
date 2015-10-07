package com.mcraichu.obeliskoflight.hovercraft;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;


public class ModelHovercraft extends ModelBase
{
  //fields
    ModelRenderer base2;
    ModelRenderer rightside;
    ModelRenderer leftback1;
    ModelRenderer rightback1;
    ModelRenderer leftfront1;
    ModelRenderer base;
    ModelRenderer leftside;
    ModelRenderer rightfront1;
    ModelRenderer leftback2;
    ModelRenderer rightback2;
    ModelRenderer rightfront2;
    ModelRenderer leftfront2;
    ModelRenderer leftback3;
    ModelRenderer leftback4;
    ModelRenderer rightback3;
    ModelRenderer rightback4;
    ModelRenderer leftfront3;
    ModelRenderer leftfront4;
    ModelRenderer rightfront3;
    ModelRenderer rightfront4;
    ModelRenderer leftback5t;
    ModelRenderer rightback5t;
    ModelRenderer leftfront5t;
    ModelRenderer rightfront5t;
  
  public ModelHovercraft()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      base2 = new ModelRenderer(this, 0, 29);
      base2.addBox(-10F, -11F, -2F, 20, 22, 2);
      base2.setRotationPoint(0F, 0F, 0F);
      base2.setTextureSize(64, 64);
      base2.mirror = true;
      setRotation(base2, -1.570796F, 0F, 0F);
      rightside = new ModelRenderer(this, 51, 0);
      rightside.addBox(-10F, -11F, 2F, 2, 22, 4);
      rightside.setRotationPoint(0F, 0F, 0F);
      rightside.setTextureSize(64, 64);
      rightside.mirror = true;
      setRotation(rightside, 1.570796F, 0F, 0F);
      leftback1 = new ModelRenderer(this, 47, 44);
      leftback1.addBox(6F, -7F, 9F, 6, 1, 2);
      leftback1.setRotationPoint(0F, 0F, 0F);
      leftback1.setTextureSize(64, 64);
      leftback1.mirror = true;
      setRotation(leftback1, 0F, 0F, 0F);
      rightback1 = new ModelRenderer(this, 47, 44);
      rightback1.addBox(-12F, -7F, 9F, 6, 1, 2);
      rightback1.setRotationPoint(0F, 0F, 0F);
      rightback1.setTextureSize(64, 64);
      rightback1.mirror = true;
      setRotation(rightback1, 0F, 0F, 0F);
      leftfront1 = new ModelRenderer(this, 47, 44);
      leftfront1.addBox(6F, -7F, -11F, 6, 1, 2);
      leftfront1.setRotationPoint(0F, 0F, 0F);
      leftfront1.setTextureSize(64, 64);
      leftfront1.mirror = true;
      setRotation(leftfront1, 0F, 0F, 0F);
      base = new ModelRenderer(this, 0, 0);
      base.addBox(-11F, -12F, 0F, 22, 24, 2);
      base.setRotationPoint(0F, 0F, 0F);
      base.setTextureSize(64, 64);
      base.mirror = true;
      setRotation(base, -1.570796F, 0F, 0F);
      leftside = new ModelRenderer(this, 51, 0);
      leftside.addBox(8F, -11F, 2F, 2, 22, 4);
      leftside.setRotationPoint(0F, 0F, 0F);
      leftside.setTextureSize(64, 64);
      leftside.mirror = true;
      setRotation(leftside, 1.570796F, 0F, 0F);
      rightfront1 = new ModelRenderer(this, 47, 44);
      rightfront1.addBox(-12F, -7F, -11F, 6, 1, 2);
      rightfront1.setRotationPoint(0F, 0F, 0F);
      rightfront1.setTextureSize(64, 64);
      rightfront1.mirror = true;
      setRotation(rightback1, 0F, 0F, 0F);
      leftback2 = new ModelRenderer(this, 47, 44);
      leftback2.addBox(6F, -12F, 9F, 6, 1, 2);
      leftback2.setRotationPoint(0F, 0F, 0F);
      leftback2.setTextureSize(64, 64);
      leftback2.mirror = true;
      setRotation(leftback2, 0F, 0F, 0F);
      rightback2 = new ModelRenderer(this, 47, 44);
      rightback2.addBox(-12F, -12F, 9F, 6, 1, 2);
      rightback2.setRotationPoint(0F, 0F, 0F);
      rightback2.setTextureSize(64, 64);
      rightback2.mirror = true;
      setRotation(rightback2, 0F, 0F, 0F);
      rightfront2 = new ModelRenderer(this, 47, 44);
      rightfront2.addBox(-12F, -12F, -11F, 6, 1, 2);
      rightfront2.setRotationPoint(0F, 0F, 0F);
      rightfront2.setTextureSize(64, 64);
      rightfront2.mirror = true;
      setRotation(rightfront2, 0F, 0F, 0F);
      leftfront2 = new ModelRenderer(this, 47, 44);
      leftfront2.addBox(6F, -12F, -11F, 6, 1, 2);
      leftfront2.setRotationPoint(0F, 0F, 0F);
      leftfront2.setTextureSize(64, 64);
      leftfront2.mirror = true;
      setRotation(leftfront2, 0F, 0F, 0F);
      leftback3 = new ModelRenderer(this, 47, 44);
      leftback3.addBox(-11F, -12F, 9F, 4, 1, 2);
      leftback3.setRotationPoint(0F, 0F, 0F);
      leftback3.setTextureSize(64, 64);
      leftback3.mirror = true;
      setRotation(leftback3, 0F, 0F, 1.570796F);
      leftback4 = new ModelRenderer(this, 47, 44);
      leftback4.addBox(-11F, -7F, 9F, 4, 1, 2);
      leftback4.setRotationPoint(0F, 0F, 0F);
      leftback4.setTextureSize(64, 64);
      leftback4.mirror = true;
      setRotation(leftback4, 0F, 0F, 1.570796F);
      rightback3 = new ModelRenderer(this, 47, 44);
      rightback3.addBox(-11F, 6F, 9F, 4, 1, 2);
      rightback3.setRotationPoint(0F, 0F, 0F);
      rightback3.setTextureSize(64, 64);
      rightback3.mirror = true;
      setRotation(rightback3, 0F, 0F, 1.570796F);
      rightback4 = new ModelRenderer(this, 47, 44);
      rightback4.addBox(-11F, 11F, 9F, 4, 1, 2);
      rightback4.setRotationPoint(0F, 0F, 0F);
      rightback4.setTextureSize(64, 64);
      rightback4.mirror = true;
      setRotation(rightback4, 0F, 0F, 1.570796F);
      leftfront3 = new ModelRenderer(this, 47, 44);
      leftfront3.addBox(-11F, -12F, -11F, 4, 1, 2);
      leftfront3.setRotationPoint(0F, 0F, 0F);
      leftfront3.setTextureSize(64, 64);
      leftfront3.mirror = true;
      setRotation(leftfront3, 0F, 0F, 1.570796F);
      leftfront4 = new ModelRenderer(this, 47, 44);
      leftfront4.addBox(-11F, -7F, -11F, 4, 1, 2);
      leftfront4.setRotationPoint(0F, 0F, 0F);
      leftfront4.setTextureSize(64, 64);
      leftfront4.mirror = true;
      setRotation(leftfront4, 0F, 0F, 1.570796F);
      rightfront3 = new ModelRenderer(this, 47, 44);
      rightfront3.addBox(-11F, 11F, -11F, 4, 1, 2);
      rightfront3.setRotationPoint(0F, 0F, 0F);
      rightfront3.setTextureSize(64, 64);
      rightfront3.mirror = true;
      setRotation(rightfront3, 0F, 0F, 1.570796F);
      rightfront4 = new ModelRenderer(this, 47, 44);
      rightfront4.addBox(-11F, 6F, -11F, 4, 1, 2);
      rightfront4.setRotationPoint(0F, 0F, 0F);
      rightfront4.setTextureSize(64, 64);
      rightfront4.mirror = true;
      setRotation(rightfront4, 0F, 0F, 1.570796F);
      leftback5t = new ModelRenderer(this, 50, 50);
      leftback5t.addBox(7F, -11F, 10F, 4, 4, 0);
      leftback5t.setRotationPoint(0F, 0F, 0F);
      leftback5t.setTextureSize(64, 64);
      leftback5t.mirror = true;
      setRotation(leftback5t, 0F, 0F, 0F);
      rightback5t = new ModelRenderer(this, 50, 50);
      rightback5t.addBox(-11F, -11F, 10F, 4, 4, 0);
      rightback5t.setRotationPoint(0F, 0F, 0F);
      rightback5t.setTextureSize(64, 64);
      rightback5t.mirror = true;
      setRotation(rightback5t, 0F, 0F, 0F);
      leftfront5t = new ModelRenderer(this, 50, 50);
      leftfront5t.addBox(7F, -11F, -10F, 4, 4, 0);
      leftfront5t.setRotationPoint(0F, 0F, 0F);
      leftfront5t.setTextureSize(64, 64);
      leftfront5t.mirror = true;
      setRotation(leftfront5t, 0F, 0F, 0F);
      rightfront5t = new ModelRenderer(this, 50, 50);
      rightfront5t.addBox(-11F, -11F, -10F, 4, 4, 0);
      rightfront5t.setRotationPoint(0F, 0F, 0F);
      rightfront5t.setTextureSize(64, 64);
      rightfront5t.mirror = true;
      setRotation(rightfront5t, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    base2.render(f5);
    rightside.render(f5);
    leftback1.render(f5);
    rightback1.render(f5);
    leftfront1.render(f5);
    base.render(f5);
    leftside.render(f5);
    rightfront1.render(f5);
    leftback2.render(f5);
    rightback2.render(f5);
    rightfront2.render(f5);
    leftfront2.render(f5);
    leftback3.render(f5);
    leftback4.render(f5);
    rightback3.render(f5);
    rightback4.render(f5);
    leftfront3.render(f5);
    leftfront4.render(f5);
    rightfront3.render(f5);
    rightfront4.render(f5);
    leftback5t.render(f5);
    rightback5t.render(f5);
    leftfront5t.render(f5);
    rightfront5t.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
  }

}
