package com.mcraichu.obeliskoflight.harvester;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class EntityAIMoveNextToBlock extends EntityAIBase
{
    private final EntityCreature entity;
    private final double movementSpeed;
    protected int pauseTicks;
    private int taskDuration;
    private int maxDuration;
    /** Block to move to */
    protected BlockPos destinationBlock;
    private boolean reached;
    private int searchRange;

    public EntityAIMoveNextToBlock(EntityCreature entity, double movementSpeed, int searchRange)
    {
        this.destinationBlock = BlockPos.ORIGIN;
        this.entity = entity;
        this.movementSpeed = movementSpeed;
        this.searchRange = searchRange;
        this.setMutexBits(5);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.pauseTicks > 0)
        {
            --this.pauseTicks;
            return false;
        }
        else
        {
            this.pauseTicks = 10 + this.entity.getRNG().nextInt(10);
            return this.searchDestination();
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return this.taskDuration >= -this.maxDuration && this.taskDuration <= 600 && this.isDestination(this.entity.worldObj, this.destinationBlock);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.entity.getNavigator().tryMoveToXYZ((double)((float)this.destinationBlock.getX()) + 0.5D, (double)(this.destinationBlock.getY()), (double)((float)this.destinationBlock.getZ()) + 0.5D, this.movementSpeed);
        this.taskDuration = 0;
        this.maxDuration = this.entity.getRNG().nextInt(this.entity.getRNG().nextInt(600) + 600) + 600;
    }

    /**
     * Resets the task
     */
    public void resetTask() {}

    /**
     * Updates the task
     */
    public void updateTask()
    {
    	Vec3 b_pos = new Vec3((double)this.destinationBlock.getX() + 0.5D, (double)this.destinationBlock.getY() + 1, (double)this.destinationBlock.getZ() + 0.5D); 
    	Vec3 vec = this.entity.getPositionVector().subtract(b_pos);
//    	 System.out.println("vec: " + vec.lengthVector());
    	if (vec.lengthVector() > 1.5D)//if (this.entity.getDistanceSqToCenter(this.destinationBlock) > 2.25D)
        {
            this.reached = false;
            ++this.taskDuration;

            if (this.taskDuration % 20 == 0)
            {
            	
            	Vec3 vec2 = vec.normalize();
//            	Vec3i vec3 = new Vec3i((int)vec2.xCoord,(int)vec2.yCoord,(int)vec2.zCoord);
            	vec = b_pos;//.add(vec2);  
            	boolean moved = this.entity.getNavigator().tryMoveToXYZ(vec.xCoord, vec.yCoord, vec.zCoord, this.movementSpeed);
            	int temp = 0;
//                this.entity.getNavigator().tryMoveToXYZ((double)((float)this.destinationBlock.getX()) + 0.5D, (double)(this.destinationBlock.getY() + 0.5), (double)((float)this.destinationBlock.getZ()) + 0.5D, this.movementSpeed);
            }
        }
        else
        {
        	vec = this.entity.getPositionVector();
        	boolean moved = this.entity.getNavigator().tryMoveToXYZ(vec.xCoord, vec.yCoord, vec.zCoord, this.movementSpeed);
            this.reached = true;
            --this.taskDuration;
        }
    }

    protected boolean hasReached()//func_179487_f()
    {
        return this.reached;
    }

    private boolean searchDestination()//func_179489_g()
    {
        int i = this.searchRange;
        boolean flag = true;
        BlockPos blockpos = new BlockPos(this.entity);

        for (int j = 0; j <= 2; j = j > 0 ? -j : 1 - j)
        {
            for (int k = 0; k < i; ++k)
            {
                for (int l = 0; l <= k; l = l > 0 ? -l : 1 - l)
                {
                    for (int i1 = l < k && l > -k ? k : 0; i1 <= k; i1 = i1 > 0 ? -i1 : 1 - i1)
                    {
                        BlockPos blockpos1 = blockpos.add(l, j - 1, i1);

                        if (this.entity.func_180485_d(blockpos1) && this.isDestination(this.entity.worldObj, blockpos1))
                        {
                            this.destinationBlock = blockpos1;
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * If BlockPos is destination, return true. else false.
     */
    protected abstract boolean isDestination(World worldIn, BlockPos blockPos);
}