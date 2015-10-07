package com.mcraichu.obeliskoflight.stealthtank;

import com.google.common.base.Predicate;
import com.mcraichu.obeliskoflight.Reference;
import com.mcraichu.obeliskoflight.nodturret.NodShell;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIControlledByPlayer;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class StealthTank extends EntityMob implements IRangedAttackMob
{
	public static String unl_name = "stealth_tank";

	private EntityAIRocketAttack aiArrowAttack = new EntityAIRocketAttack(this, 1.0D, 20, 60, 15.0F, 15.0F);
	//	private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false);

	/** AI task for player control. */
	public boolean attacking = false;
	private int unstealthTimer;
	private int stealthTimer;
	private int stealthTicks = 20;

	public StealthTank(World worldIn)
	{

		super(worldIn);
		//this.setCanPickUpLoot(true);
		this.setSize(1.4F, 1.4F);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.field_175455_a);
		//this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));

		this.tasks.addTask(4, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));

		if (worldIn != null && !worldIn.isRemote)
		{
			this.setCombatTask();
		}

	}

	public StealthTank(World worldIn, double p_i1705_2_, double p_i1705_4_, double p_i1705_6_)
	{
		this(worldIn);
		this.setPosition(p_i1705_2_, p_i1705_4_, p_i1705_6_);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = p_i1705_2_;
		this.prevPosY = p_i1705_4_;
		this.prevPosZ = p_i1705_6_;

		attacking = false;
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_,float p_82196_2_) {
		//this.unstealthTimer = stealthTicks;
		if(this.attacking == false){
			this.worldObj.setEntityState(this, (byte)4);
		}
		this.attacking = true;

//		boolean flag = p_82196_1_.attackEntityFrom(DamageSource.causeMobDamage(this), (float)(7 + this.rand.nextInt(15)));
//
//		EntityArrow entityarrow = new EntityArrow(this.worldObj, this, p_82196_1_, 1.6F, (float)(14 - this.worldObj.getDifficulty().getDifficultyId() * 4));
//		entityarrow.setDamage(0.5); //(double)(p_82196_2_ * 2.0F) + this.rand.nextGaussian() * 0.25D + (double)((float)this.worldObj.getDifficulty().getDifficultyId() * 0.11F));
//
//		this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
//		this.worldObj.spawnEntityInWorld(entityarrow);

		NodRocket projectile = new NodRocket(this.worldObj, true, p_82196_1_);

		this.posY = this.posY + (double)this.getEyeHeight() - 0.10000000149011612D;
		double d0 = p_82196_1_.posX - this.posX;
		double d1 = p_82196_1_.getEntityBoundingBox().minY + (double)(p_82196_1_.height / 3.0F) - this.posY;
		double d2 = p_82196_1_.posZ - this.posZ;
		double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);
		if(!this.worldObj.isRemote){
			if (d3 >= 1.0E-7D)
			{
				float f2 = (float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
				float f3 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
				double d4 = d0 / d3;
				double d5 = d2 / d3;
				projectile.setLocationAndAngles(this.posX + d4, this.posY, this.posZ + d5, f2, f3);
				float f4 = (float)(d3 * 0.20000000298023224D);
				projectile.setThrowableHeading(d0, d1 + (double)f4, d2, projectile.speed, (float)(14 - this.worldObj.getDifficulty().getDifficultyId() * 4));
				projectile.damage = (float) (projectile.damage  * (double)this.worldObj.getDifficulty().getDifficultyId());
				this.worldObj.spawnEntityInWorld(projectile);
			}
		}



	}

	/**
	 * sets this entity's combat AI.
	 */
	public void setCombatTask()
	{
		//		this.tasks.removeTask(this.aiAttackOnCollide);
		this.tasks.removeTask(this.aiArrowAttack);
		ItemStack itemstack = this.getHeldItem();

		if (itemstack != null && itemstack.getItem() == Items.bow)
		{
			this.tasks.addTask(2, this.aiArrowAttack);
		}
		else
		{
			this.tasks.addTask(2, this.aiArrowAttack);
		}
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if (this.unstealthTimer > 0)
		{
			--this.unstealthTimer;
		}else{
			this.unstealthTimer = 0;
		}
		if (this.stealthTimer > 0)
		{
			--this.stealthTimer;
		}else{
			this.stealthTimer = 0;
		}
		if(!this.worldObj.isRemote){
			if((this.getAttackTarget() == null)&&(attacking == true)){
				this.attacking = false;
				this.worldObj.setEntityState(this, (byte)5);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte p_70103_1_)
	{
		if (p_70103_1_ == 4)
		{
			this.unstealthTimer = stealthTicks;
			this.attacking = true;
		}
		else if (p_70103_1_ == 5)
		{
			this.stealthTimer = stealthTicks;
			this.attacking = false;
		}
		else
		{
			super.handleHealthUpdate(p_70103_1_);
		}
	}

	@SideOnly(Side.CLIENT)
	public int getUnstealthTimer()
	{
		return this.unstealthTimer;
	}

	@SideOnly(Side.CLIENT)
	public boolean getAttacking()
	{
		return this.attacking;
	}

	@SideOnly(Side.CLIENT)
	public int getStealthTimer()
	{
		return this.stealthTimer;
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
	}


	protected void entityInit()
	{
		super.entityInit();
	}



	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	protected String getLivingSound()
	{
		return Reference.MODID + ":" + "harvester_say";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound()
	{
		return Reference.MODID + ":" + "harvester_hurt";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound()
	{
		return Reference.MODID + ":" + "harvester_death";
	}

	protected void playStepSound(BlockPos p_180429_1_, Block p_180429_2_)
	{
		this.playSound(Reference.MODID + ":" + "harvester_step", 0.25F, 1.0F);
	}


	protected Item getDropItem()
	{
		return this.isBurning() ? Items.cooked_porkchop : Items.porkchop;
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		int j = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + p_70628_2_);

		for (int k = 0; k < j; ++k)
		{
			if (this.isBurning())
			{
				this.dropItem(Items.cooked_porkchop, 1);
			}
			else
			{
				this.dropItem(Items.porkchop, 1);
			}
		}


	}


}