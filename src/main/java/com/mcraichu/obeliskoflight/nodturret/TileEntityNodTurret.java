package com.mcraichu.obeliskoflight.nodturret;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.mcraichu.obeliskoflight.Reference;
import com.mcraichu.obeliskoflight.utilities.Utilities;

import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityNodTurret extends TileEntity implements IUpdatePlayerListBox{

	private final long INVALID_TIME = 0;
	private long lastTime = INVALID_TIME;  // used for animation
	private double lastAngularPosition; // used for animation
	public double targetYaw = 0.0;
	public double targetPitch = 0.0;
	public double currentTargetYaw = 0.0;
	public double currentTargetPitch = 0.0;

	EntityMob target = null;

	public int ticks = 0;
	public int untilReady = 0;
	public int cooldown = 30;
	public boolean aimed = true;


	private float damage = 10.0f;
	private float distance = 16.0f;
	private float player_distance = 5.0f;

	@Override
	public void update() {

		if (ticks % 5 == 0) {
			worldObj.markBlockForUpdate(this.pos);
		}

		ticks++;

		if(target != null){
			if(target.getDistanceSqToCenter(this.pos) > (((distance+2)*(distance+2))*3)){ //16*16 + 16*16 + 16*16 aka 256 * 3 
				//out of range
				target = null;
			}else if(target.isDead){
				//target died
				target = null;
			}else if(Utilities.inLineOfSight(this, new Vec3(0.5,0.8,0.5), target)){
				//can't see the target
				target = null;
			}

		}


		if(target == null){
			AxisAlignedBB bb = new AxisAlignedBB(this.pos, this.pos.add(1, 1, 1));
			List list = this.worldObj.getEntitiesWithinAABB(EntityMob.class, bb.expand((double)distance, (double)distance, (double)distance));

			EntityEnderCrystal entityendercrystal = null;
			double d0 = Double.MAX_VALUE;
			Iterator iterator = list.iterator();

			while (iterator.hasNext())
			{
				EntityMob targetMob = (EntityMob)iterator.next();
				double d1 = targetMob.getDistanceSqToCenter(this.pos);

				if(Utilities.inLineOfSight(this, new Vec3(0.5,0.8,0.5), targetMob)){
					if (d1 < d0)
					{
						d0 = d1;
						target = targetMob;
					}
				}				
			}
		}

		if(target == null){
			untilReady = 0;
			//aimed = false;
			targetYaw = 0.0f;
			targetPitch = 0.0f;
			aimBarrelYaw();
			aimBarrelPitch();
			return;
		}

		Vec3 targetVec = Utilities.pos2vec(target.getPosition());
		targetVec = targetVec.addVector(0.5, target.getEyeHeight(), 0.5);
		Vec3 sourceVec = Utilities.pos2vec(this.pos);
		sourceVec = sourceVec.addVector(0.5, 0.8, 0.5);

		targetPitch = Utilities.pitchInDegree(sourceVec, targetVec);
		targetYaw = Utilities.yawInDegree(sourceVec, targetVec);



		aimed = true;

		aimed = aimed && aimBarrelYaw();
		aimed = aimed && aimBarrelPitch();

		if(untilReady < cooldown){
			untilReady++;
			return;
		}





		if(aimed){
			Vec3 d0 = targetVec.subtract(sourceVec);
			if(!this.worldObj.isRemote){
				NodShell projectile = new NodShell(this.getWorld(), false, target);
				//EntitySnowball projectile = new EntitySnowball(this.worldObj);
				projectile.setPosition(sourceVec.xCoord,sourceVec.yCoord,sourceVec.zCoord);
				projectile.setThrowableHeading(d0.xCoord,d0.yCoord,d0.zCoord,projectile.speed,0.00f);



				this.worldObj.spawnEntityInWorld(projectile);
			}
			untilReady = 0;

			Vec3 dist = d0.normalize();
			dist = sourceVec.add(dist);

			for (int i = 0; i <= 20; i++) {
				Random random = new Random();
				this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, dist.xCoord + (random.nextGaussian() / 10), dist.yCoord + (random.nextGaussian() / 10),
						dist.zCoord + (random.nextGaussian() / 10), (0), (0), (0));
			}
			this.worldObj.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), Reference.MODID + ":" + "tank_fire",0.5f, 1.0f, false);

		}
		ticks = 0;
	}

	private boolean aimBarrelYaw(){
		float turnPerTick = 6.0f;
		//close enough 
		if(Math.abs(targetYaw - currentTargetYaw) <= turnPerTick){
			currentTargetYaw = targetYaw;
			return true;
		}

		boolean bothPos = (0 <= targetYaw) && ( 0 <=  currentTargetYaw);
		boolean bothNeg = (0 > targetYaw) && ( 0 > currentTargetYaw);
		boolean posNeg = (0 <= targetYaw) && ( 0 >  currentTargetYaw);
		boolean negPos = (0 > targetYaw) && ( 0 <=  currentTargetYaw);
		if(bothPos || bothNeg){
			if(targetYaw > currentTargetYaw){
				currentTargetYaw += turnPerTick;
			}else{
				currentTargetYaw -= turnPerTick;
			}
		}else if(posNeg){
			if((Math.abs(targetYaw) + Math.abs(currentTargetYaw) ) <= 180.0f){
				currentTargetYaw += turnPerTick;
			}else{
				currentTargetYaw -= turnPerTick;
			}
		}else if(negPos){
			if((Math.abs(targetYaw) + Math.abs(currentTargetYaw) ) <= 180.0f){
				currentTargetYaw -= turnPerTick;
			}else{
				currentTargetYaw += turnPerTick;
			}
		}

		if(currentTargetYaw >= 180.0f){
			currentTargetYaw -= 360.0f;
		}
		if(currentTargetYaw < -180.0f){
			currentTargetYaw += 360.0f;
		}
		return false;
	}

	private boolean aimBarrelPitch(){
		float turnPerTick = 4.0f;
		if(Math.abs(targetPitch - currentTargetPitch) <= turnPerTick){
			currentTargetPitch = targetPitch;
			return true;
		}

		boolean b1 = targetPitch < currentTargetPitch;
		boolean b2 = (Math.abs(targetPitch) + Math.abs(currentTargetPitch)) < 180.0f;
		if(b1 && b2){
			//rotate right
			currentTargetPitch -= turnPerTick;
		}else{
			//rotate left
			currentTargetPitch += turnPerTick;
		}
		if(currentTargetPitch >= 180.0f){
			currentTargetPitch -= 360.0f;
		}
		if(currentTargetPitch < -180.0f){
			currentTargetPitch += 360.0f;
		}	
		return false;

	}

	/**
	 * Calculate the next angular position of the gem, given its current speed.
	 * @param revsPerSecond
	 * @return the angular position in degrees (0 - 360)
	 */
	public double getNextAngularPosition(double revsPerSecond)
	{
		// we calculate the next position as the angular speed multiplied by the elapsed time since the last position.
		// Elapsed time is calculated using the system clock, which means the animations continue to
		//  run while the game is paused.
		// Alternatively, the elapsed time can be calculated as
		//  time_in_seconds = (number_of_ticks_elapsed + partialTick) / 20.0;
		//  where your tileEntity's update() method increments number_of_ticks_elapsed, and partialTick is passed by vanilla
		//   to your TESR renderTileEntityAt() method.
		long timeNow = System.nanoTime();
		if (lastTime == INVALID_TIME) {   // automatically initialise to 0 if not set yet
			lastTime = timeNow;
			lastAngularPosition = 0.0;
		}
		final double DEGREES_PER_REV = 360.0;
		final double NANOSECONDS_PER_SECOND = 1e9;
		double nextAngularPosition = lastAngularPosition + (timeNow - lastTime) * revsPerSecond * DEGREES_PER_REV / NANOSECONDS_PER_SECOND;
		nextAngularPosition = nextAngularPosition % DEGREES_PER_REV;
		lastAngularPosition = nextAngularPosition;
		lastTime = timeNow;
		return nextAngularPosition;
	}

	/**
	 * Don't render the gem if the player is too far away
	 * @return the maximum distance squared at which the TESR should render
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public double getMaxRenderDistanceSquared()
	{
		final int MAXIMUM_DISTANCE_IN_BLOCKS = 32;
		return MAXIMUM_DISTANCE_IN_BLOCKS * MAXIMUM_DISTANCE_IN_BLOCKS;
	}

	/** Return an appropriate bounding box enclosing the TESR
	 * This method is used to control whether the TESR should be rendered or not, depending on where the player is looking.
	 * The default is the AABB for the parent block, which might be too small if the TESR renders outside the borders of the
	 *   parent block.
	 * If you get the boundary too small, the TESR may disappear when you aren't looking directly at it.
	 * @return an appropriately size AABB for the TileEntity
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		// if your render should always be performed regardless of where the player is looking, use infinite
		AxisAlignedBB infiniteExample = INFINITE_EXTENT_AABB;

		// our gem will stay above the block, up to 1 block higher, so our bounding box is from [x,y,z] to  [x+1, y+3, z+1]
		AxisAlignedBB aabb = new AxisAlignedBB(getPos(), getPos().add(1, 3, 1));
		return infiniteExample;
	}

}
