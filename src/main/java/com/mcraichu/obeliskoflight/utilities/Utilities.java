package com.mcraichu.obeliskoflight.utilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class Utilities {

	private static final Method methodIsChunkLoaded;

	static {
	    Method m;
	    try {
	        m = World.class.getDeclaredMethod("isChunkLoaded", int.class, int.class, boolean.class);
	        m.setAccessible(true);
	   } catch (Exception e) {
	        throw new RuntimeException(e);
	   }
	   methodIsChunkLoaded = m;
	}
	
	public static double MAX_ENTITY_RADIUS = 2.0D;
	/** linearly interpolate for y between [x1, y1] to [x2, y2] using x
	 *  y = y1 + (y2 - y1) * (x - x1) / (x2 - x1)
	 *  For example:  if [x1, y1] is [0, 100], and [x2,y2] is [1, 200], then as x increases from 0 to 1, this function
	 *    will increase from 100 to 200
	 * @param x  the x value to linearly interpolate on
	 * @param x1
	 * @param x2
	 * @param y1
	 * @param y2
	 * @return linearly interpolated value.  If x is outside the range, clip it to the nearest end
	 */
	public static double interpolate(double x, double x1, double x2, double y1, double y2)
	{
		if (x1 > x2) {
			double temp = x1; x1 = x2; x2 = temp;
			temp = y1; y1 = y2; y2 = temp;
		}

		if (x <= x1) return y1;
		if (x >= x2) return y2;
		double xFraction = (x - x1) / (x2 - x1);
		return y1 + xFraction * (y2 - y1);
	}

	//--------------------------------------------------------------------------------------------------------------

	public static List getTileEntitiesWithinAABB(World world, Class tileEntityClass, AxisAlignedBB aabb)
	{
		int i = MathHelper.floor_double((aabb.minX - MAX_ENTITY_RADIUS) / 16.0D);
		int j = MathHelper.floor_double((aabb.maxX + MAX_ENTITY_RADIUS) / 16.0D);
		int k = MathHelper.floor_double((aabb.minZ - MAX_ENTITY_RADIUS) / 16.0D);
		int l = MathHelper.floor_double((aabb.maxZ + MAX_ENTITY_RADIUS) / 16.0D);
		ArrayList arraylist = Lists.newArrayList();

		for (int i1 = i; i1 <= j; ++i1)
		{
			for (int j1 = k; j1 <= l; ++j1)
			{
				boolean chunkLoaded = false;
				try {
	
					chunkLoaded = (Boolean) methodIsChunkLoaded.invoke((World)world, i1, j1, true);
					
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
				
				if(chunkLoaded)
				{
					Map tileMap = world.getChunkFromChunkCoords(i1, j1).getTileEntityMap();
					Collection tileEntityCollection = tileMap.values();
					Iterator iterator = tileEntityCollection.iterator();
	
					while (iterator.hasNext())
					{

						TileEntity te = (TileEntity)iterator.next();
						if(tileEntityClass.isInstance(te))
						{
							if (te.getRenderBoundingBox().intersectsWith(aabb))
			                {
								arraylist.add(te);
			                }
						}
					}
				}
			}
		}
		return arraylist;
	}


	//--------------------------------------------------------------------------------------------------------------

	public static boolean inLineOfSight(TileEntity source, Vec3 offest, EntityLivingBase target){

		Vec3 start = pos2vec(source.getPos()).add(offest);
		Vec3 stop = target.getPositionVector().addVector(0,target.getEyeHeight(),0);
		Vec3 delta = stop.subtract(start);

		// Normalize vector to the largest delta axis
		delta = delta.normalize();


		// Limit how many non solid block a turret can see through
		for (int i = 0; i < 10; i++) {
			// Offset start position toward the target to prevent self collision
			start = start.add(delta);

			MovingObjectPosition traced = source.getWorld().rayTraceBlocks(new Vec3(start.xCoord, start.yCoord, start.zCoord),new Vec3(stop.xCoord, stop.yCoord, stop.zCoord));

			if (traced != null && traced.typeOfHit == traced.typeOfHit.BLOCK) {
				Block hitBlock = source.getWorld().getBlockState(traced.getBlockPos()).getBlock();

				// If non solid block is in the way then proceed to continue
				// tracing
				double diff = start.subtract(stop).lengthVector();
				boolean reachedStop = diff < 1.0;

				if (hitBlock != null && !hitBlock.getMaterial().isSolid() && !reachedStop) {
					// Start at new position and continue
					start = traced.hitVec;
					continue;
				}
			}

			EntityLivingBase targeted = target != null && traced == null ? target : null;

			if (targeted != null && targeted.equals(target)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public static double yawInDegree(Vec3 source, Vec3 target){
		Vec3 vec1 = target.subtract(source).normalize();
		double rad = Math.atan2(0.0,1.0) - Math.atan2(vec1.zCoord,vec1.xCoord);
		double degree = rad *(180/Math.PI);
		return degree;
	}

	public static double pitchInDegree(Vec3 source, Vec3 target){
		Vec3 vec1 = target.subtract(source).normalize();
		double axis = Math.sqrt(vec1.zCoord * vec1.zCoord + vec1.xCoord * vec1.xCoord);
		double pitch = Math.atan2(0.0,axis) - (Math.atan2(vec1.yCoord,axis));
		pitch = (-1.0) * pitch *(180/Math.PI);
		return pitch;
	}

	//--------------------------------------------------------------------------------------------------------------

	public static Vec3 pos2vec(BlockPos pos1){
		return new Vec3(pos1.getX(),pos1.getY(),pos1.getZ());
	}

	//--------------------------------------------------------------------------------------------------------------

	public static Vec3 pos2vec(BlockPos pos1, double offsetX, double offsetY, double offsetZ){
		return new Vec3(((double)pos1.getX()) + offsetX,((double)pos1.getY()) + offsetY,((double)pos1.getZ()) + offsetZ);
	}

	public static BlockPos vec2pos(Vec3 vec){
		return new BlockPos((int)vec.xCoord,(int)vec.yCoord,(int)vec.zCoord);		  
	}
}
