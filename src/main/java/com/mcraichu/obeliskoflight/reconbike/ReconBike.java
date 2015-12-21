package com.mcraichu.obeliskoflight.reconbike;

import java.util.Iterator;
import java.util.List;

import com.mcraichu.obeliskoflight.Reference;
import com.mcraichu.obeliskoflight.nodturret.NodShell;
import com.mcraichu.obeliskoflight.stealthtank.NodRocket;
import com.mcraichu.obeliskoflight.tiberium.Tiberium;
import com.mcraichu.obeliskoflight.utilities.Utilities;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIRunAroundLikeCrazy;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ReconBike extends EntityTameable implements IInvBasic
{
	protected boolean renderPlayer;
	protected int weaponCooldown = 0;

	public ReconBike(World worldIn) {
		super(worldIn);

		this.setTamed(false);
		this.setOwnerId("");

		this.isImmuneToFire = true;
		//this.setChested(false);
		this.setSize(1.2F, 1.4F);
		this.tasks.addTask(0, new EntityAISwimming(this));
	}
	
	public ReconBike(World worldIn, double p_i1705_2_, double p_i1705_4_, double p_i1705_6_)
	{
	    this(worldIn);
	    this.setPosition(p_i1705_2_, p_i1705_4_, p_i1705_6_);
	    this.motionX = 0.0D;
	    this.motionY = 0.0D;
	    this.motionZ = 0.0D;
	    this.prevPosX = p_i1705_2_;
	    this.prevPosY = p_i1705_4_;
	    this.prevPosZ = p_i1705_6_;
	}

	@Override
	public void onInventoryChanged(InventoryBasic p_76316_1_) {
		// TODO Auto-generated method stub

	}

	@Override
	public EntityAgeable createChild(EntityAgeable p_90011_1_) {
		return null;
	}


	// you don't have to call this as it is called automatically during entityLiving subclass creation
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes(); 

		// standard attributes registered to EntityLivingBase
		if (isTamed())
		{
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
		}
		else
		{
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
		}
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1.60D);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.0D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);

		// need to register any additional attributes
		//        getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
		//        getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
	}

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	//	@Override
	//	public boolean isAIEnabled()
	//	{
	//		return true;
	//	}

	/**
	 * main AI tick function, replaces updateEntityActionState
	 */
	@Override
	protected void updateAITick()
	{
		this.dataWatcher.updateObject(18, Float.valueOf(getHealth()));
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(18, new Float(getHealth()));
		dataWatcher.addObject(19, new Byte((byte)0));
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);

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
		this.playSound(Reference.MODID + ":" + "harvester_step", 0.35F, 1.0F);
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume()
	{
		return 0.9F;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{
		super.onUpdate();
		weaponCooldown--;
		if(weaponCooldown < 0)
		{
			weaponCooldown = 0;
		}
	}

	@Override
	public float getEyeHeight()
	{
		return height * 0.8F;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if (isEntityInvulnerable(par1DamageSource))
		{
			System.out.println("--------------------------invlnerable.....");
			return false;
		}
		else
		{
			return super.attackEntityFrom(par1DamageSource, par2);
		}
	}

	@Override
	public void setTamed(boolean par1)
	{
		super.setTamed(par1);

		if (par1)
		{
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
		}
		else
		{
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
		}
	}

	@Override
	public boolean interact(EntityPlayer par1EntityPlayer)
	{

		if (this.riddenByEntity != null)
		{
			
			if(((EntityPlayer)this.riddenByEntity == par1EntityPlayer)&& (getOwner() == par1EntityPlayer))
			{
				Entity target = Utilities.isLookingAtEntity(par1EntityPlayer,new Vec3(0, 0, 0), 20.0, this);
				fireWeapon(target, par1EntityPlayer.getLookVec());

//				return super.interact(par1EntityPlayer);
			}
			return false;
		}
		// DEBUG

		ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();

		Item item = com.mcraichu.obeliskoflight.tiberium.StartupClient.itemTiberium;

		// heal tamed with food
		if (isTamed() && (getOwner() == par1EntityPlayer))
		{
			if(par1EntityPlayer.isSneaking()){
				//
				System.out.println("--------------------opengui");
				//this.openGUI(p_70085_1_);
				return true;
			}
			if (itemstack != null)
			{
				if (itemstack.getItem() == item)
				{
					if (dataWatcher.getWatchableObjectFloat(18) < 20.0F)
					{
						if (!par1EntityPlayer.capabilities.isCreativeMode)
						{
							--itemstack.stackSize;
						}
						heal(20.0F);

						if (itemstack.stackSize <= 0)
						{
							par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
						}

						return true;
					}
				}

			}
			//reached if itemstack == null or item is not energy crystal
			this.mountWalker(par1EntityPlayer);
			return super.interact(par1EntityPlayer);

		}else if (itemstack != null && itemstack.getItem() == item) // tame
		{
			if (!par1EntityPlayer.capabilities.isCreativeMode)
			{
				--itemstack.stackSize;
			}

			if (itemstack.stackSize <= 0)
			{
				par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
			}

			if (!worldObj.isRemote)
			{
				if (true)
				{
					setTamed(true);
					setHealth(40.0F);
					this.setOwnerId(par1EntityPlayer.getUniqueID().toString()); // used to be setOwner()
					playTameEffect(true);
					worldObj.setEntityState(this, (byte)7);
				}
				//			    else
				//			    {
				//				    playTameEffect(false);
				//				    worldObj.setEntityState(this, (byte)6);
				//			    }
			}
		}


		return super.interact(par1EntityPlayer);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte par1)
	{
		super.handleHealthUpdate(par1);
	}

	/**
	 * Will return how many at most can spawn in a chunk at once.
	 */
	@Override
	public int getMaxSpawnedInChunk()
	{
		return 1;
	}

	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	@Override
	protected boolean canDespawn()
	{
		return !isTamed() && ticksExisted > 2400;
	}


	private void mountWalker(EntityPlayer p_110237_1_)
	{
		p_110237_1_.rotationYaw = this.rotationYaw;
		p_110237_1_.rotationPitch = this.rotationPitch;

		if (!this.worldObj.isRemote)
		{
			p_110237_1_.mountEntity(this);
		}
	}

	@Override
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
			p_70612_1_ = ((EntityLivingBase)this.riddenByEntity).moveStrafing * 0.07F;
			p_70612_2_ = ((EntityLivingBase)this.riddenByEntity).moveForward * 0.15F;

			if (p_70612_2_ <= 0.0F)
			{
				p_70612_2_ *= 0.5F;
			}

			this.stepHeight = 1.0F;
			this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;

			if (!this.worldObj.isRemote)
			{
				this.setAIMoveSpeed((float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
				super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
			}

		}
		else
		{
			this.stepHeight = 0.5F;
			this.jumpMovementFactor = 0.02F;
			super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
		}
	}

	@Override
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

	@Override
	public boolean shouldRiderSit() { return true;}

	@Override
	public double getMountedYOffset()
	{
		return 0.1D;//(double)this.height * 0.75D;
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	public void onLivingUpdate(){
		super.onLivingUpdate();
	}

	/*
	 * weapon right click 
	 * */
	private boolean fireWeapon(Entity target, Vec3 direction)
	{
//		Vec3 d0 = targetVec.subtract(sourceVec);
		if(!this.worldObj.isRemote){
			
			if(weaponCooldown > 0)
			{
				return false;
			}
			
			//EntitySnowball projectile = new EntitySnowball(this.worldObj);
			Vec3 look = direction;
			Vec3 straight = new Vec3(look.xCoord,0,look.zCoord);
			Vec3 pos1 = straight.normalize();
			pos1 = Utilities.vecMul(pos1, -0.5);
			pos1 = pos1.add(this.getPositionVector());
			pos1 = pos1.addVector(0, 1.4, 0);
			Vec3 up = new Vec3(0,1,0);
			Vec3 right = straight.crossProduct(up);
			Vec3 left = up.crossProduct(straight);
			right = Utilities.vecMul(right, 0.7);
			left = Utilities.vecMul(left, 0.7);
			Vec3 pos2 = pos1.add(left);
			pos1 = pos1.add(right);
			
			//pos1 is in the middle now we need to go left and right
			
			NodRocket projectile;
			NodRocket projectile2;
			if(target != null && !target.isDead){
				projectile = new NodRocket(this.worldObj, this, true, target);
				projectile2 = new NodRocket(this.worldObj, this, true, target);
			}else{
				projectile = new NodRocket(this.worldObj, false, target);
				projectile2 = new NodRocket(this.worldObj, false, target);
			}
			
			projectile.setPosition(pos1.xCoord,pos1.yCoord,pos1.zCoord);
			projectile.setThrowableHeading(look.xCoord,look.yCoord,look.zCoord,projectile.speed,0.00f);
			this.playSound(Reference.MODID + ":" + "rocket", 0.6f, 1.0f);
			projectile2.setPosition(pos2.xCoord,pos2.yCoord,pos2.zCoord);
			projectile2.setThrowableHeading(look.xCoord,look.yCoord,look.zCoord,projectile.speed,0.00f);
			this.playSound(Reference.MODID + ":" + "rocket", 0.6f, 1.0f);

			this.worldObj.spawnEntityInWorld(projectile);
			this.worldObj.spawnEntityInWorld(projectile2);
			weaponCooldown = 30;
			return true;
		}
		
		return false;
	}



}