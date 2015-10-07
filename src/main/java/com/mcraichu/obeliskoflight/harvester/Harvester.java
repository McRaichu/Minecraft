package com.mcraichu.obeliskoflight.harvester;

import com.mcraichu.obeliskoflight.Reference;

import net.minecraft.block.Block;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIControlledByPlayer;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class Harvester extends EntityCreature
{
    /** AI task for player control. */
    private final EntityAIControlledByPlayer aiControlledByPlayer;
    public boolean carrying;
    public int harvesting;

    public Harvester(World worldIn)
    {
    	
        super(worldIn);
        this.setCanPickUpLoot(true);
        this.setSize(0.7F, 0.7F);
        ((PathNavigateGround)this.getNavigator()).func_179690_a(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25D));
        this.tasks.addTask(2, this.aiControlledByPlayer = new EntityAIControlledByPlayer(this, 0.3F));
//        this.tasks.addTask(4, new EntityAITempt(this, 1.2D, Items.carrot_on_a_stick, false));
//        this.tasks.addTask(4, new EntityAITempt(this, 1.2D, Items.carrot, false));
        //this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIHarvestTiberium(this, 0.6));
        this.tasks.addTask(6, new EntityAIReturnTiberium(this, 0.6));
//        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        

        //this.tasks.addTask(8, new EntityAILookIdle(this));
        
        carrying = false;
        harvesting = 0;
        
        DataWatcher dw = this.getDataWatcher();
        dw.addObject(20, harvesting);
        
    }
    
    public Harvester(World worldIn, double p_i1705_2_, double p_i1705_4_, double p_i1705_6_)
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
    
    public boolean isHarvesting()
    {
    	DataWatcher dw = this.getDataWatcher();
    	return dw.getWatchableObjectInt(20) == 0 ? false : true; 
    }
    
    public void setHarvesting(boolean val)
    {
    	DataWatcher dw = this.getDataWatcher();
    	int i = val == true ? 1 : 0;
    	dw.updateObject(20, i);
    }
    
    public void onLivingUpdate()
    {
    	this.setCanPickUpLoot(true);

		ItemStack itemstack = this.getHeldItem();

		if (itemstack != null)
		{
			if(itemstack.getItem() != com.mcraichu.obeliskoflight.tiberium.StartupClient.itemTiberium){
//				this.dropItemWithOffset(itemstack.getItem(), 1, 3.0F);
				this.setCurrentItemOrArmor(0, (ItemStack)null);
			}
		}
        super.onLivingUpdate();
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    }

    /**
     * returns true if all the conditions for steering the entity are met. For pigs, this is true if it is being ridden
     * by a player and the player is holding a carrot-on-a-stick
     */
    public boolean canBeSteered()
    {
        ItemStack itemstack = ((EntityPlayer)this.riddenByEntity).getHeldItem();
        return itemstack != null && itemstack.getItem() == Items.carrot_on_a_stick;
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setBoolean("Saddle", this.getSaddled());
        tagCompound.setBoolean("Carrying", this.carrying);
        tagCompound.setInteger("Harvesting", this.harvesting);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        super.readEntityFromNBT(tagCompund);
        this.setSaddled(tagCompund.getBoolean("Saddle"));
        this.carrying = tagCompund.getBoolean("Carrying");
        this.harvesting = tagCompund.getInteger("Harvesting");
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

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer player)
    {
        if (super.interact(player))
        {
            return true;
        }
        else if (this.getSaddled() && !this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == player))
        {
            player.mountEntity(this);
            return true;
        }
        else
        {
            return false;
        }
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

        if (this.getSaddled())
        {
            this.dropItem(Items.saddle, 1);
        }
    }

    /**
     * Returns true if the pig is saddled.
     */
    public boolean getSaddled()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    /**
     * Set or remove the saddle of the pig.
     */
    public void setSaddled(boolean p_70900_1_)
    {
        if (p_70900_1_)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)1));
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)0));
        }
    }

    /**
     * Called when a lightning bolt hits the entity.
     */
    public void onStruckByLightning(EntityLightningBolt lightningBolt)
    {
        if (!this.worldObj.isRemote)
        {
            EntityPigZombie entitypigzombie = new EntityPigZombie(this.worldObj);
            entitypigzombie.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
            entitypigzombie.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.worldObj.spawnEntityInWorld(entitypigzombie);
            this.setDead();
        }
    }

    public void fall(float distance, float damageMultiplier)
    {
        super.fall(distance, damageMultiplier);

        if (distance > 5.0F && this.riddenByEntity instanceof EntityPlayer)
        {
            ((EntityPlayer)this.riddenByEntity).triggerAchievement(AchievementList.flyPig);
        }
    }

    public Harvester createChild(EntityAgeable ageable)
    {
        return new Harvester(this.worldObj);
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    public boolean isBreedingItem(ItemStack stack)
    {
        return stack != null && stack.getItem() == Items.carrot;
    }

    /**
     * Return the AI task for player control.
     */
    public EntityAIControlledByPlayer getAIControlledByPlayer()
    {
        return this.aiControlledByPlayer;
    }
}