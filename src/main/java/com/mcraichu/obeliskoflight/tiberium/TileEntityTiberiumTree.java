package com.mcraichu.obeliskoflight.tiberium;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.mcraichu.obeliskoflight.utilities.Utilities;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class TileEntityTiberiumTree extends TileEntity implements IUpdatePlayerListBox{

	private int ticks;
	private int untilPosion = 100;
	private int growTiberium = 2400;

	public TileEntityTiberiumTree(){
		ticks = 0;
	}

	@Override
	public void update() {
		ticks++;
		if(ticks > untilPosion)
		{

		}
		if(ticks > growTiberium)
		{
			if(!this.worldObj.isRemote)
			{
				growTiberium();
			}
			for (int x = 0; x <= 50; x++) {
				Random random = new Random();
				this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.pos.getX() + 0.5 + (random.nextGaussian() / 10), this.pos.getY() + 1.1 + (random.nextGaussian() / 10),
						this.pos.getZ() + 0.5 + (random.nextGaussian() / 10), (0), (0), (0));
			}
			ticks = 0;
		}
	}

	public void growTiberium(){
		/*
		 * HERE WE SPREAD
		 * */
		IBlockState state = (IBlockState)this.worldObj.getBlockState(this.pos);
		int j = ((Integer)state.getValue(TiberiumTree.AGE)).intValue();
		if (j == 0)
		{
			int distance = 10;
			AxisAlignedBB bb = new AxisAlignedBB(this.pos, this.pos.add(1, 1, 1));
			//List list = worldIn.getEntitiesWithinAABB(TileEntityTiberium.class, bb.expand((double)distance, (double)(distance/2), (double)distance));
			List list = Utilities.getTileEntitiesWithinAABB(this.worldObj, TileEntityTiberium.class, bb.expand((double)distance, (double)(distance/2), (double)distance));

			Iterator iterator = list.iterator();

			while (iterator.hasNext())
			{
				TileEntityTiberium tib = (TileEntityTiberium)iterator.next();
				tib.spread();
				tib.grow();
			}

			if(list.isEmpty())
			{
				placeTiberium(this.pos.add(-2.0, 0.0, 0.0));
				placeTiberium(this.pos.add( 2.0, 0.0, 0.0));
				placeTiberium(this.pos.add( 0.0, 0.0,-2.0));
				placeTiberium(this.pos.add( 0.0, 0.0, 2.0));
			}

		}
	}

	public void placeTiberium(BlockPos pos)
	{

		BlockPos blockpos1 = this.worldObj.getTopSolidOrLiquidBlock(pos);

		boolean isAir = this.worldObj.isAirBlock(blockpos1);
		Block temp = this.worldObj.getBlockState(blockpos1.down()).getBlock();
		if(Tiberium.class.isInstance(temp))
		{
			return;
		}

		boolean inHeightRange = (blockpos1.getY() >= (pos.getY()-2)) && (blockpos1.getY() <= (pos.getY()+2));


		if (canBlockStay(blockpos1) && inHeightRange)
		{
			this.worldObj.setBlockState(blockpos1, StartupCommon.tiberium.getDefaultState());
			return;

		}

		return;

	}

	public boolean canBlockStay(BlockPos pos)
	{
		Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

		while (iterator.hasNext())
		{
			EnumFacing enumfacing = (EnumFacing)iterator.next();

			Block temp = worldObj.getBlockState(pos.offset(enumfacing)).getBlock(); 
			if(TiberiumTree.class.isInstance(temp))
			{
				return false;
			}
		}

		boolean notOnTiberiumTree = !TiberiumTree.class.isInstance(this.worldObj.getBlockState(pos.down()).getBlock());
		boolean isSolid = worldObj.isSideSolid(pos.down(), EnumFacing.UP);
		boolean hasSky = (!worldObj.provider.getHasNoSky()); 

		return (isSolid && hasSky && notOnTiberiumTree);
	}


}
