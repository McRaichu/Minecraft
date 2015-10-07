package com.mcraichu.obeliskoflight.tiberium;

import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class TileEntityTiberium extends TileEntity{

	private Random rand;
	
	public TileEntityTiberium(){
		rand = new Random();
	}

	public void grow(){
		if(!this.worldObj.isRemote)
		{
			Tiberium tib = (Tiberium)this.worldObj.getBlockState(this.pos).getBlock();
			int meta = tib.getMetaFromState(this.worldObj.getBlockState(this.pos));
			if(meta < 2 && rand.nextInt(10) == 0)
			{
				this.worldObj.setBlockState(this.pos, tib.getStateFromMeta(meta+1));
			}
		}
	}

	public void spread()
	{
		if(!this.worldObj.isRemote){
			Tiberium tib = (Tiberium)this.worldObj.getBlockState(this.pos).getBlock();

			BlockPos blockpos1 = this.pos.add(rand.nextInt(2) - rand.nextInt(2), 0, rand.nextInt(2) - rand.nextInt(2));
			int j = 0;
			while(j < 24 && blockpos1 == this.pos)
			{
				blockpos1 = this.pos.add(rand.nextInt(2) - rand.nextInt(2), 0, rand.nextInt(2) - rand.nextInt(2));
				j++;
			}

			BlockPos blockpos2 = this.worldObj.getTopSolidOrLiquidBlock(blockpos1);

			boolean isAir = this.worldObj.isAirBlock(blockpos2);
			Block temp = this.worldObj.getBlockState(blockpos2.down()).getBlock();
			if(Tiberium.class.isInstance(temp))
			{
				return;
			}

			boolean inHeightRange = (blockpos2.getY() >= (pos.getY()-1)) && (blockpos2.getY() <= (pos.getY()+1));


			if (canBlockStay(blockpos2) && inHeightRange)
			{
				this.worldObj.setBlockState(blockpos2, tib.getDefaultState());
				return;

			}

			return;
		}
	}

	public boolean canBlockStay(BlockPos pos)
	{
		Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

		while (iterator.hasNext())
		{
			EnumFacing enumfacing = (EnumFacing)iterator.next();

			Block temp = this.worldObj.getBlockState(pos.offset(enumfacing)).getBlock(); 
			if(TiberiumTree.class.isInstance(temp))
			{
				return false;
			}
		}

		boolean notOnTiberiumTree = !TiberiumTree.class.isInstance(this.worldObj.getBlockState(pos.down()).getBlock());
		boolean isSolid = this.worldObj.isSideSolid(pos.down(), EnumFacing.UP);
		boolean hasSky = (!this.worldObj.provider.getHasNoSky()); 

		return (isSolid && hasSky && notOnTiberiumTree);
	}


}
