package com.mcraichu.obeliskoflight.stealthtank;

import java.util.List;
import java.util.Random;

import com.mcraichu.obeliskoflight.utilities.Utilities;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class NodRocket extends EntityThrowable {

	private boolean isHoming;
	private Entity target;
	public boolean hit = false;
	public float speed = 1.0F;
	public int arrowShake;
	private float gravity = 0.00F;
	public float damage = 5.0f;
	private double radius = 1.5;

	public NodRocket(World worldIn) {
		super(worldIn);
		this.target = null;
		isHoming = false;
		initNodRocket();
	}

	public NodRocket(World worldIn, EntityLivingBase entity) {
		super(worldIn,entity);
		this.target = null;
		isHoming = false;
		initNodRocket();
	}

	public NodRocket(World worldIn, double par1, double par2, double par3) {
		super(worldIn,par1,par2,par3);
		this.target = null;
		isHoming = false;
		initNodRocket();
	}

	public NodRocket(World worldIn, boolean isHoming, Entity target) {
		super(worldIn);
		this.target = target;
		this.isHoming = isHoming;
		initNodRocket();
	}

	private void initNodRocket(){
		this.renderDistanceWeight = 10.0D;
		//this.setThrowableHeading(this.motionX,this.motionY,this.motionZ,speed,0.00f);
	}

	@Override
	public void onEntityUpdate() {

		super.onEntityUpdate();

		if (ticksExisted >= 100) {
			this.setDead();
		}
		if (!worldObj.isRemote) {
			if (isHoming && target != null) {
				double d0 = target.posX - this.posX;
				double d1 = target.posY + (double) target.getEyeHeight() - this.posY;
				double d2 = target.posZ - this.posZ;

				this.setThrowableHeading(d0, d1, d2, speed, 0.0F);
				speed = speed + 0.06F;
			} else if (isHoming && target == null) {
				this.setDead();
			}
		}
	}


	@Override
	protected void onImpact(MovingObjectPosition movingobjectposition) {
		if (this.ticksExisted <= 5) {
			return;
		}
		if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			Block hitBlock = worldObj.getBlockState(movingobjectposition.getBlockPos()).getBlock();
			if (hitBlock != null && !hitBlock.getMaterial().isSolid() || worldObj.isAirBlock(movingobjectposition.getBlockPos())) {
				// Go through non solid block
				return;
			}
		}
		if (!worldObj.isRemote) {
			worldObj.createExplosion(null, posX, posY, posZ, 0.1F, true);
			AxisAlignedBB bb = AxisAlignedBB.fromBounds(this.posX, this.posY,this.posZ, this.posX+1 , this.posY+1, this.posZ+1);
			AxisAlignedBB axis = bb.expand(radius, radius, radius);
			List<EntityLivingBase> targets = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axis);

			for (EntityLivingBase mob : targets) {
				if(!StealthTank.class.isInstance(mob)){
					mob.attackEntityFrom(new DamageSource("rocket"), damage);
				}
				//mob.hurtResistantTime = 0;
			}
		}
		this.setDead();
	}

	@Override
	protected float getGravityVelocity() {
		return this.gravity;
	}

	@Override
	public boolean writeToNBTOptional(NBTTagCompound p_70039_1_) {
		this.setDead();
		return false;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
	}

}
