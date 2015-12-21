package com.mcraichu.obeliskoflight.orca;

import com.mcraichu.obeliskoflight.utilities.Utilities;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class EntityFlyingAIFollowOwner extends EntityAIBase
{
    private EntityFlyingTameable thePet;
    private EntityLivingBase theOwner;
    World theWorld;
    private double speed;
    private PathNavigate petPathfinder;
    private int field_75343_h;
    float maxDist;
    float minDist;
    private boolean field_75344_i;
    private static final String __OBFID = "CL_00001585";

    public EntityFlyingAIFollowOwner(EntityFlyingTameable p_i1625_1_, double p_i1625_2_, float p_i1625_4_, float p_i1625_5_)
    {
        this.thePet = p_i1625_1_;
        this.theWorld = p_i1625_1_.worldObj;
        this.speed = p_i1625_2_;
        this.petPathfinder = p_i1625_1_.getNavigator();
        this.minDist = p_i1625_4_;
        this.maxDist = p_i1625_5_;
        this.setMutexBits(3);

        if (!(p_i1625_1_.getNavigator() instanceof PathNavigateAir))
        {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        EntityLivingBase entitylivingbase = this.thePet.getOwnerEntity();

        if (entitylivingbase == null)
        {
            return false;
        }
        else if (this.thePet.isSitting())
        {
            return false;
        }
        else if (this.thePet.getDistanceSqToEntity(entitylivingbase) < (double)(this.minDist * this.minDist))
        {
            return false;
        }
        else
        {
            this.theOwner = entitylivingbase;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !this.petPathfinder.noPath() && this.thePet.getDistanceSqToEntity(this.theOwner) > (double)(this.maxDist * this.maxDist) && !this.thePet.isSitting();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.field_75343_h = 0;
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.theOwner = null;
        this.petPathfinder.clearPathEntity();
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        this.thePet.getLookHelper().setLookPositionWithEntity(this.theOwner, 10.0F, (float)this.thePet.getVerticalFaceSpeed());

        double d1 = this.theOwner.posX - this.thePet.posX;
        double d2 = this.theOwner.posZ - this.thePet.posZ;
        this.thePet.renderYawOffset = this.thePet.rotationYaw = -((float)Math.atan2(d1, d2)) * 180.0F / (float)Math.PI;

        if (!this.thePet.isSitting())
        {
            if (--this.field_75343_h <= 0)
            {
                this.field_75343_h = 10;

//                this.petPathfinder.tryMoveToXYZ(this.theOwner.posX, this.theOwner.posY + 4, this.theOwner.posZ, this.field_75336_f)
                
                double oX = this.theOwner.posX;
                double oY = this.theOwner.posY;
                double oZ = this.theOwner.posZ;
                BlockPos pos1 = getTopNoneAirBlock(this.theOwner.worldObj, new BlockPos(oX, oY, oZ));
                oY = pos1.getY() + 4;           
                this.petPathfinder.tryMoveToXYZ(oX, oY, oZ, this.speed);
                if (this.thePet.getDistanceSqToEntity(this.theOwner) > (double)((this.maxDist*2) * (this.maxDist*2)))
                {
                    if (!this.thePet.getLeashed())
                    {
                        if (this.thePet.getDistanceSqToEntity(this.theOwner) >= 144.0D)
                        {
                            int i = MathHelper.floor_double(this.theOwner.posX) - 2;
                            int j = MathHelper.floor_double(this.theOwner.posZ) - 2;
                            int k = MathHelper.floor_double(this.theOwner.getEntityBoundingBox().minY + 4.0);

                            for (int l = 0; l <= 4; ++l)
                            {
                                for (int i1 = 0; i1 <= 4; ++i1)
                                {
                                	boolean not_on_owner = (l < 1 || i1 < 1 || l > 3 || i1 > 3) ;
                                	Block place1 = this.theWorld.getBlockState(new BlockPos(i + l, k, j + i1)).getBlock();
                                	boolean hasPlace1 = !place1.isSolidFullCube();
                                	Block place2 = this.theWorld.getBlockState(new BlockPos(i + l, k+1, j + i1)).getBlock();
                                	boolean hasPlace2 = !place2.isSolidFullCube();
                                	
                                    if (not_on_owner && hasPlace1 && hasPlace2)
                                    {
                                        this.thePet.setLocationAndAngles((double)((float)(i + l) + 0.5F), (double)k, (double)((float)(j + i1) + 0.5F), this.thePet.rotationYaw, this.thePet.rotationPitch);
                                        this.petPathfinder.clearPathEntity();
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public BlockPos getTopNoneAirBlock(World worldIn, BlockPos pos)
    {
        Chunk chunk = worldIn.getChunkFromBlockCoords(pos);
        BlockPos blockpos1;
        BlockPos blockpos2;

        for (blockpos1 = new BlockPos(pos.getX(), chunk.getTopFilledSegment() + 16, pos.getZ()); blockpos1.getY() >= 0; blockpos1 = blockpos2)
        {
            blockpos2 = blockpos1.down();
            Block block = chunk.getBlock(blockpos2);

            if (block.getMaterial() != Material.air)
            {
                break;
            }
        }

        return blockpos1;
    }
}
