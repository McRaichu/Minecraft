package com.mcraichu.obeliskoflight.orca;

import java.util.Random;

import com.mcraichu.obeliskoflight.Reference;
import com.mcraichu.obeliskoflight.stealthtank.NodRocket;
import com.mcraichu.obeliskoflight.tiberium.Tiberium;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Orca extends EntityFlyingTameable
{
	/** The explosion radius of spawned fireballs. */
	private int explosionStrength = 1;

	private Orca.AIRandomFly airandomfly = new Orca.AIRandomFly();

	public Orca(World worldIn)
	{
		super(worldIn);
		this.setSize(2.0F, 1.5F);
		this.isImmuneToFire = true;
		this.moveHelper = new Orca.GhastMoveHelper();
		this.navigator = new PathNavigateAir(this, worldIn);
		this.tasks.addTask(2, this.aiHover);
		this.tasks.addTask(3, new Orca.AIRocketAttack());
		this.tasks.addTask(4, new EntityFlyingAIFollowOwner(this, 2.5D, 8.0F, 2.0F));
		this.tasks.addTask(5, airandomfly);
		this.tasks.addTask(7, new Orca.AILookAround());
		this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true, new Class[0]));
		this.setTamed(false);
	}

	@SideOnly(Side.CLIENT)
	public boolean func_110182_bF()
	{
		return this.dataWatcher.getWatchableObjectByte(21) != 0;
	}

	public void func_175454_a(boolean p_175454_1_)
	{
		this.dataWatcher.updateObject(21, Byte.valueOf((byte)(p_175454_1_ ? 1 : 0)));
	}

	public int func_175453_cd()
	{
		return this.explosionStrength;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{
		super.onUpdate();

		if (!this.worldObj.isRemote && this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL)
		{
			this.setDead();
		}
	}

	protected void updateAITasks()
	{
		this.dataWatcher.updateObject(19, Float.valueOf(this.getHealth()));
	}

	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (this.isEntityInvulnerable(source))
		{
			return false;
		}
		else
		{
			this.aiHover.setHovering(false);
			return super.attackEntityFrom(source, amount);
		}
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(19, new Float(this.getHealth()));
		this.dataWatcher.addObject(20, new Byte((byte)0));
		this.dataWatcher.addObject(21, Byte.valueOf((byte)0));
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(100.0D);

		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);

		if (this.isTamed())
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
		}
		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
		}
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	protected String getLivingSound()
	{
		return Reference.MODID + ":" + "orca_living";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound()
	{
		return Reference.MODID + ":" + "orca_hurt";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound()
	{
		return Reference.MODID + ":" + "orca_death";
	}

	protected Item getDropItem()
	{
		return Items.gunpowder;
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		int j = this.rand.nextInt(2) + this.rand.nextInt(1 + p_70628_2_);
		int k;

		for (k = 0; k < j; ++k)
		{
			this.dropItem(Items.ghast_tear, 1);
		}

		j = this.rand.nextInt(3) + this.rand.nextInt(1 + p_70628_2_);

		for (k = 0; k < j; ++k)
		{
			this.dropItem(Items.gunpowder, 1);
		}
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	protected float getSoundVolume()
	{
		return 10.0F;
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this entity.
	 */
	public boolean getCanSpawnHere()
	{
		return this.rand.nextInt(20) == 0 && super.getCanSpawnHere() && this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL;
	}

	/**
	 * Will return how many at most can spawn in a chunk at once.
	 */
	public int getMaxSpawnedInChunk()
	{
		return 1;
	}

	public void setTamed(boolean tamed)
	{
		super.setTamed(tamed);

		if (tamed)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
			this.tasks.removeTask(airandomfly);
		}
		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
			this.tasks.addTask(5, airandomfly);
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("ExplosionPower", this.explosionStrength);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);

		if (tagCompund.hasKey("ExplosionPower", 99))
		{
			this.explosionStrength = tagCompund.getInteger("ExplosionPower");
		}
	}

	public float getEyeHeight()
	{
		return 1.0F;
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	public boolean interact(EntityPlayer player)
	{
		ItemStack itemstack = player.inventory.getCurrentItem();
		Item item = com.mcraichu.obeliskoflight.tiberium.StartupClient.itemTiberium;

		if (this.isTamed())
		{
			if (itemstack != null)
			{

				if (itemstack.getItem() == item)
				{
					if (this.dataWatcher.getWatchableObjectFloat(19) < 20.0F)
					{
						if (!player.capabilities.isCreativeMode)
						{
							--itemstack.stackSize;
						}

						this.heal(5.0F);

						if (itemstack.stackSize <= 0)
						{
							player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
						}

						return true;
					}
				}
				if (this.isOwner(player) && !this.worldObj.isRemote && !this.isBreedingItem(itemstack))
				{
					this.worldObj.playSoundAtEntity(this, Reference.MODID + ":" + "interact", 0.5f, 1.0f);
					this.aiHover.setHovering(!this.isHovering());
					this.isJumping = false;
					this.navigator.clearPathEntity();
					this.setAttackTarget((EntityLivingBase)null);
				}
			}else{
				//mount orca
				if(this.riddenByEntity == null)
				{
					mountOrca(player);
				}
			}
		}
		else if (itemstack != null && itemstack.getItem() == item)
		{
			if (!player.capabilities.isCreativeMode)
			{
				--itemstack.stackSize;
			}

			if (itemstack.stackSize <= 0)
			{
				player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
			}

			if (!this.worldObj.isRemote)
			{
				if (this.rand.nextInt(3) == 0)
				{
					this.setTamed(true);
					this.navigator.clearPathEntity();
					this.setAttackTarget((EntityLivingBase)null);
					this.aiHover.setHovering(true);
					this.setHealth(20.0F);
					this.setOwnerId(player.getUniqueID().toString());
					this.playTameEffect(true);
					this.worldObj.setEntityState(this, (byte)7);
				}
				else
				{
					this.playTameEffect(false);
					this.worldObj.setEntityState(this, (byte)6);
				}
			}

			return true;
		}

		return super.interact(player);
	}


	public void func_70918_i(boolean p_70918_1_)
	{
		if (p_70918_1_)
		{
			this.dataWatcher.updateObject(20, Byte.valueOf((byte)1));
		}
		else
		{
			this.dataWatcher.updateObject(20, Byte.valueOf((byte)0));
		}
	}

	public boolean func_70922_bv()
	{
		return this.dataWatcher.getWatchableObjectByte(20) == 1;
	}

	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	protected boolean canDespawn()
	{
		return !this.isTamed() && this.ticksExisted > 2400;
	}

	public boolean func_142018_a(EntityLivingBase p_142018_1_, EntityLivingBase p_142018_2_)
	{
		if (!(p_142018_1_ instanceof EntityCreeper) && !(p_142018_1_ instanceof EntityGhast))
		{
			if (p_142018_1_ instanceof EntityWolf)
			{
				Orca orca = (Orca)p_142018_1_;

				if (orca.isTamed() && orca.getOwnerEntity() == p_142018_2_)
				{
					return false;
				}
			}

			return p_142018_1_ instanceof EntityPlayer && p_142018_2_ instanceof EntityPlayer && !((EntityPlayer)p_142018_2_).canAttackPlayer((EntityPlayer)p_142018_1_) ? false : !(p_142018_1_ instanceof EntityHorse) || !((EntityHorse)p_142018_1_).isTame();
		}
		else
		{
			return false;
		}
	}

	private void mountOrca(EntityPlayer p_110237_1_)
	{
		p_110237_1_.rotationYaw = this.rotationYaw;
		p_110237_1_.rotationPitch = this.rotationPitch;

		if (!this.worldObj.isRemote)
		{
			p_110237_1_.mountEntity(this);
		}
	}

	/**
	 * Moves the entity based on the specified heading.  Args: strafe, forward
	 */
	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
	{
		if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase)
		{
			this.prevRotationYaw = this.rotationYaw = this.riddenByEntity.rotationYaw;
			this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5F;
			this.setRotation(this.rotationYaw, this.rotationPitch);
			this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
			p_70612_1_ = ((EntityLivingBase)this.riddenByEntity).moveStrafing;
			p_70612_2_ = ((EntityLivingBase)this.riddenByEntity).moveForward * 0.5F;

			Vec3 moveDirection = new Vec3(0.0,0.0,0.0);
			if (p_70612_2_ > 0.0F)
			{
				//moving forward, get lookvec
				Vec3 lookDirection = this.riddenByEntity.getLookVec();
				moveDirection = moveDirection.add(new Vec3(lookDirection.xCoord*(1.0),lookDirection.yCoord*(1.0),lookDirection.zCoord*(1.0)));

			}else if (p_70612_2_ < 0.0F)
			{
				Vec3 lookDirection = this.riddenByEntity.getLookVec();
				moveDirection = moveDirection.add(new Vec3(lookDirection.xCoord*(-1.0),lookDirection.yCoord*(-1.0),lookDirection.zCoord*(-1.0)));

			}
			if (p_70612_1_ > 0.0F)
			{
				Vec3 lookDirection = this.riddenByEntity.getLookVec();
				Vec3 up = new Vec3(0.0,1.0,0.0);
				Vec3 left = up.crossProduct(lookDirection);
				moveDirection = moveDirection.add(new Vec3(left.xCoord*(1.0),left.yCoord*(1.0),left.zCoord*(1.0)));
			}else if (p_70612_1_ < 0.0F)
			{
				Vec3 lookDirection = this.riddenByEntity.getLookVec();
				Vec3 up = new Vec3(0.0,1.0,0.0);
				Vec3 right = lookDirection.crossProduct(up);
				moveDirection = moveDirection.add(new Vec3(right.xCoord*(1.0),right.yCoord*(1.0),right.zCoord*(1.0)));
			}

			if(moveDirection.lengthVector() != 0.0)
			{
				moveDirection = moveDirection.normalize();
				moveDirection = new Vec3(moveDirection.xCoord*(1.5),moveDirection.yCoord*(1.5),moveDirection.zCoord*(1.5));
				BlockPos pos = this.getPosition();
				Vec3 moveToVec = new Vec3(moveDirection.xCoord + (double)pos.getX(),moveDirection.yCoord + (double)pos.getY(),moveDirection.zCoord + (double)pos.getZ());
				this.getMoveHelper().setMoveTo(moveToVec.xCoord, moveToVec.yCoord, moveToVec.zCoord, 3.0D);
			}

			if (!this.worldObj.isRemote)
			{
				this.setAIMoveSpeed((float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
				super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
			}

		}
		else
		{
			super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
		}
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities when colliding.
	 */
	public boolean canBePushed()
	{
		return this.riddenByEntity == null;
	}

	public void updateRiderPosition()
	{
		super.updateRiderPosition();

		float f = MathHelper.sin(this.renderYawOffset * (float)Math.PI / 180.0F);
		float f1 = MathHelper.cos(this.renderYawOffset * (float)Math.PI / 180.0F);
		float f2 = 0.7F * 0.0F;
		float f3 = 0.15F * 0.0F;
		this.riddenByEntity.setPosition(this.posX + (double)(f2 * f), this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset() + (double)f3, this.posZ - (double)(f2 * f1));

		if (this.riddenByEntity instanceof EntityLivingBase)
		{
			((EntityLivingBase)this.riddenByEntity).renderYawOffset = this.renderYawOffset;
		}

	}

	/**
	 * Returns the Y offset from the entity's position for any entity riding this one.
	 */
	public double getMountedYOffset()
	{
		return -(double)this.height * 0.0D;
	}

	/*
	 * ------------------------------------------------------------------------------------------------------------------------
	 * CUSTOM AI FOR Flying units
	 * TODO: NOTHING JUST AS REMINDER
	 * ------------------------------------------------------------------------------------------------------------------------
	 * */
	class AIRocketAttack extends EntityAIBase
	{
		private Orca orca = Orca.this;
		public int attackTimer;

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute()
		{
			return this.orca.getAttackTarget() != null;
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void startExecuting()
		{
			this.attackTimer = 0;
		}

		/**
		 * Resets the task
		 */
		public void resetTask()
		{
			this.orca.func_175454_a(false);
		}

		/**
		 * Updates the task
		 */
		public void updateTask()
		{
			EntityLivingBase target = this.orca.getAttackTarget();
			double d0 = 64.0D;

			if (target.getDistanceSqToEntity(this.orca) < d0 * d0 && this.orca.canEntityBeSeen(target))
			{
				World world = this.orca.worldObj;
				++this.attackTimer;

				if (this.attackTimer == 10)
				{
					//world.playAuxSFXAtEntity((EntityPlayer)null, 1007, new BlockPos(this.orca), 0);
				}

				if (this.attackTimer == 20)
				{
					//					 double spawnOffset = 4.0D;
					//					 Vec3 targetDirection = this.orca.getLook(1.0F);
					//					 double d2 = target.posX - (this.orca.posX + targetDirection.xCoord * spawnOffset);
					//					 double d3 = target.getEntityBoundingBox().minY + (double)(target.height / 2.0F) - (0.5D + this.orca.posY + (double)(this.orca.height / 2.0F));
					//					 double d4 = target.posZ - (this.orca.posZ + targetDirection.zCoord * spawnOffset);
					//					 world.playAuxSFXAtEntity((EntityPlayer)null, 1008, new BlockPos(this.orca), 0);
					//					 EntityLargeFireball entitylargefireball = new EntityLargeFireball(world, this.orca, d2, d3, d4);
					//					 entitylargefireball.explosionPower = this.orca.func_175453_cd();
					//					 entitylargefireball.posX = this.orca.posX + targetDirection.xCoord * spawnOffset;
					//					 entitylargefireball.posY = this.orca.posY + (double)(this.orca.height / 2.0F) + 0.0D;
					//					 entitylargefireball.posZ = this.orca.posZ + targetDirection.zCoord * spawnOffset;
					//					 world.spawnEntityInWorld(entitylargefireball);

					NodRocket projectile = new NodRocket(world, this.orca, true, target);

					double spawnOffset = 2.0D;
					Vec3 targetDirection = this.orca.getLook(1.0F);
					double d00 = target.posX - (this.orca.posX + targetDirection.xCoord * spawnOffset);
					double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 2.0F) - (0.5D + this.orca.posY + (double)(this.orca.height / 2.0F));
					double d2 = target.posZ - (this.orca.posZ + targetDirection.zCoord * spawnOffset);
					double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);
					if(!world.isRemote){
						if (d3 >= 1.0E-7D)
						{
							float f2 = (float)(Math.atan2(d2, d00) * 180.0D / Math.PI) - 90.0F;
							float f3 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
							double d4 = targetDirection.xCoord;//d00 / d3;
							double d5 = targetDirection.zCoord;//d2 / d3;
							projectile.setLocationAndAngles(this.orca.posX + d4 * spawnOffset, this.orca.posY, this.orca.posZ + d5 * spawnOffset, f2, f3);
							float f4 = (float)(d3 * 0.20000000298023224D);
							projectile.setThrowableHeading(d00, d1 + (double)f4, d2, projectile.speed, (float)(14 - world.getDifficulty().getDifficultyId() * 4));
							projectile.damage = (float) (projectile.damage  * (double)world.getDifficulty().getDifficultyId());
							world.spawnEntityInWorld(projectile);
							this.orca.playSound(Reference.MODID + ":" + "rocket", 1.0f, 1.0f);
						}
					}
					this.attackTimer = -40;
				}
			}
			else if (this.attackTimer > 0)
			{
				--this.attackTimer;
			}

			this.orca.func_175454_a(this.attackTimer > 10);
		}
	}

	class AILookAround extends EntityAIBase
	{
		private Orca orca = Orca.this;

		Random rand = new Random();

		public AILookAround()
		{
			this.setMutexBits(2);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute()
		{
			if (this.orca.isSitting())
			{
				return false;
			}
			return true;
		}

		/**
		 * Updates the task
		 */
		public void updateTask()
		{
			if (this.orca.getAttackTarget() == null)
			{
				if(rand.nextInt(100) == 0){
					this.orca.renderYawOffset = this.orca.rotationYaw = -((float)Math.atan2(this.orca.motionX, this.orca.motionZ)) * 180.0F / (float)Math.PI;
				}
			}
			else
			{
				EntityLivingBase entitylivingbase = this.orca.getAttackTarget();
				double d0 = 64.0D;

				if (entitylivingbase.getDistanceSqToEntity(this.orca) < d0 * d0)
				{
					double d1 = entitylivingbase.posX - this.orca.posX;
					double d2 = entitylivingbase.posZ - this.orca.posZ;
					this.orca.renderYawOffset = this.orca.rotationYaw = -((float)Math.atan2(d1, d2)) * 180.0F / (float)Math.PI;
				}
			}
		}
	}

	class AIRandomFly extends EntityAIBase
	{
		private Orca orca = Orca.this;

		public AIRandomFly()
		{
			this.setMutexBits(1);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute()
		{
			EntityMoveHelper entitymovehelper = this.orca.getMoveHelper();

			if (!entitymovehelper.isUpdating())
			{
				return true;
			}
			else
			{
				double d0 = entitymovehelper.func_179917_d() - this.orca.posX;
				double d1 = entitymovehelper.func_179919_e() - this.orca.posY;
				double d2 = entitymovehelper.func_179918_f() - this.orca.posZ;
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				return d3 < 1.0D || d3 > 3600.0D;
			}
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		public boolean continueExecuting()
		{
			return false;
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void startExecuting()
		{
			Random random = this.orca.getRNG();
			double d0 = this.orca.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			BlockPos ground = this.orca.worldObj.getTopSolidOrLiquidBlock(this.orca.getPosition());
			double d1;
			if((ground.getY() + 10) > this.orca.posY){
				d1 = this.orca.posY + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			}else{
				d1 = this.orca.posY + (double)((random.nextFloat() - 1.5F) * 16.0F);
			}            
			double d2 = this.orca.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			this.orca.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
		}
	}

	class GhastMoveHelper extends EntityMoveHelper
	{
		private Orca orca = Orca.this;
		private int waitTimer;

		public GhastMoveHelper()
		{
			super(Orca.this);
		}

		public void onUpdateMoveHelper()
		{
			if (this.update)
			{
				double diffX = this.posX - this.orca.posX;
				double diffY = this.posY - this.orca.posY;
				double diffZ = this.posZ - this.orca.posZ;
				double distance = diffX * diffX + diffY * diffY + diffZ * diffZ;

				if (this.waitTimer-- <= 0)
				{
					if(this.orca.riddenByEntity != null)
					{ 
						this.waitTimer = 0;
					}else{
						this.waitTimer += this.orca.getRNG().nextInt(5) + 2;
					}
					distance = (double)MathHelper.sqrt_double(distance);

					if (this.canGoAlong(this.posX, this.posY, this.posZ, distance))
					{
						this.orca.motionX += diffX / distance * 0.1D;
						this.orca.motionY += diffY / distance * 0.1D;
						this.orca.motionZ += diffZ / distance * 0.1D;
					}
					else
					{
						this.update = false;
					}
				}
			}
		}

		public boolean canGoAlong(double posX, double posY, double posZ, double distance)
		{
			double offsetX = (posX - this.orca.posX) / distance;
			double offsetY = (posY - this.orca.posY) / distance;
			double offsetZ = (posZ - this.orca.posZ) / distance;
			AxisAlignedBB axisalignedbb = this.orca.getEntityBoundingBox();

			for (int i = 1; (double)i < distance; ++i)
			{
				axisalignedbb = axisalignedbb.offset(offsetX, offsetY, offsetZ);

				if (!this.orca.worldObj.getCollidingBoundingBoxes(this.orca, axisalignedbb).isEmpty())
				{
					return false;
				}
			}

			return true;
		}
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return null;
	}
}