package com.mcraichu.obeliskoflight.orca;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAIHover extends  EntityAIBase{

    private EntityFlyingTameable theEntity;
    /** If the EntityTameable is sitting. */
    private boolean isHovering;
    private static final String __OBFID = "CL_00001613";
	
	public EntityAIHover(EntityFlyingTameable entityIn) {
		this.theEntity = entityIn;
        this.setMutexBits(5);
	}




    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!this.theEntity.isTamed())
        {
            return false;
        }
        else if (this.theEntity.isInWater())
        {
            return false;
        }
        else
        {
            EntityLivingBase entitylivingbase = this.theEntity.getOwnerEntity();
            return entitylivingbase == null ? true : (this.theEntity.getDistanceSqToEntity(entitylivingbase) < 144.0D && entitylivingbase.getAITarget() != null ? false : this.isHovering);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.theEntity.getNavigator().clearPathEntity();
        this.theEntity.setHovering(true);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.theEntity.setHovering(false);
    }

    /**
     * Sets the sitting flag.
     */
    public void setHovering(boolean hovering)
    {
        this.isHovering = hovering;
    }
}
