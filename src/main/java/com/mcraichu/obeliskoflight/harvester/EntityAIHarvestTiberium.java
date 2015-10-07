package com.mcraichu.obeliskoflight.harvester;

import com.mcraichu.obeliskoflight.obeliskpart.ObeliskPart;
import com.mcraichu.obeliskoflight.tiberium.Tiberium;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

public class EntityAIHarvestTiberium  extends EntityAIMoveNextToBlock
{
    /** Villager that is harvesting */
    private final Harvester harvester;
    private boolean harvested;
    private int harvestTime; 
    private int harvestCounter;

    public EntityAIHarvestTiberium(Harvester harvester, double speed)
    {
    	//harvester, 0.6D
        super(harvester, speed, 16);
        this.harvester = harvester;
        harvested = false;
        harvestTime = 48;
        harvestCounter = 0;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.pauseTicks <= 10)
        {
        	
            if (!this.harvester.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"))
            {
                return false;
            }

            if(this.harvester.getHeldItem() != null)
            {
            	harvested = true;
                harvester.setHarvesting(false);
            }else
            {
            	harvested = false;
            }
            
            if(harvested)
            {
            	return false;
            }

        }

        return super.shouldExecute();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return super.continueExecuting();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        super.startExecuting();
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        super.resetTask();
    }

    /**
     * Updates the task
     */
    @Override
    public void updateTask()
    {
        super.updateTask();
        this.harvester.getLookHelper().setLookPosition((double)this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 0.5), (double)this.destinationBlock.getZ() + 0.5D, 10.0F, (float)this.harvester.getVerticalFaceSpeed());
                
        if (this.hasReached())
        {
            World world = this.harvester.worldObj;
            BlockPos blockpos = this.destinationBlock;
            IBlockState iblockstate = world.getBlockState(blockpos);
            Block block = iblockstate.getBlock();
            harvester.setHarvesting(true);
            
            if (!harvested && block instanceof Tiberium && harvestCounter > harvestTime)
            {
            	int j = block.getMetaFromState(iblockstate);
            	if(j > 0)
            	{
            		harvester.setCurrentItemOrArmor(0, new ItemStack(block));
            		world.setBlockState(blockpos, block.getStateFromMeta(j-1));
            	}else{
            		harvester.setCurrentItemOrArmor(0, new ItemStack(block));
            		world.setBlockState(blockpos, Blocks.air.getDefaultState());
            	}
                harvested = true;
                harvestCounter=0;
                return;
            }
            if(harvestCounter <= harvestTime){
            	harvestCounter ++;
            }
            
        }else{
        	//this.harvester.getNavigator().tryMoveToXYZ((double)((float)this.destinationBlock.up().getX()) + 0.5D, (double)(this.destinationBlock.up().getY() + 0.5), (double)((float)this.destinationBlock.up().getZ()) + 0.5D, 2*this.movementSpeed);
        }
    }
    
    protected boolean isDestination(World worldIn, BlockPos blockPos)
    {
        Block block = worldIn.getBlockState(blockPos).getBlock();

        if (block instanceof Tiberium && !harvested)
        {
//        	Vec3 b_pos = new Vec3(blockPos.getX()+0.5,blockPos.getY()+0.5,blockPos.getZ()+0.5); 
//        	Vec3 vec = harvester.getPositionVector().subtract(b_pos);
//        	Vec3 vec2 = vec.normalize();
//        	Vec3i vec3 = new Vec3i((int)vec2.xCoord,(int)vec2.yCoord,(int)vec2.zCoord);
//        	blockPos = blockPos.add(vec3);     	
            return true;
        }

        return false;
    }
}