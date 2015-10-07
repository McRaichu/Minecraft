package com.mcraichu.obeliskoflight.tiberium;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.mcraichu.obeliskoflight.ObeliskOfLightMain;
import com.mcraichu.obeliskoflight.utilities.Utilities;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TiberiumTree extends Block implements net.minecraftforge.common.IPlantable,ITileEntityProvider{

	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 2);

	public static String unl_name = "tiberium_tree";

	protected TiberiumTree(Material materialIn) {
		super(materialIn);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)));
		this.setTickRandomly(true);
		this.setCreativeTab(ObeliskOfLightMain.tabobelisk);
	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		BlockPos blockpos1 = pos.up();

		if (worldIn.isAirBlock(blockpos1))
		{
			/*
			 * HERE WE GROW
			 * */
			int i = 0;

			for (i = 1; (worldIn.getBlockState(pos.down(i)) == state.withProperty(AGE, Integer.valueOf(1))) || (worldIn.getBlockState(pos.down(i)) == state.withProperty(AGE, Integer.valueOf(2))); ++i)
			{
				;
			}

			if (i < 3)
			{
				if (rand.nextInt(2) == 0)
				{             
					int j = ((Integer)state.getValue(AGE)).intValue();
					worldIn.setBlockState(blockpos1, this.getDefaultState());
					IBlockState iblockstate1 = state.withProperty(AGE, Integer.valueOf(j + 1));
					worldIn.setBlockState(pos, iblockstate1, 2);
					this.onNeighborBlockChange(worldIn, blockpos1, iblockstate1, this);
					IBlockState temp = worldIn.getBlockState(pos);
				}
			}            
		}

		super.updateTick(worldIn, pos, state, rand);
	}

	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
	{
		float f = 0.0F;// 0.0625F;
		return new AxisAlignedBB((double)((float)pos.getX() + f), (double)pos.getY(), (double)((float)pos.getZ() + f), (double)((float)(pos.getX() + 1) - f), (double)((float)(pos.getY() + 1) - f), (double)((float)(pos.getZ() + 1) - f));
	}

	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos)
	{
		float f = 0.0F;// 0.0625F;
		return new AxisAlignedBB((double)((float)pos.getX() + f), (double)pos.getY(), (double)((float)pos.getZ() + f), (double)((float)(pos.getX() + 1) - f), (double)(pos.getY() + 1), (double)((float)(pos.getZ() + 1) - f));
	}

	//    public boolean isFullCube()
	//    {
	//        return false;
	//    }
	//
	//    public boolean isOpaqueCube()
	//    {
	//        return false;
	//    }

	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return super.canPlaceBlockAt(worldIn, pos) ? this.canBlockStay(worldIn, pos) : false;
	}

	/**
	 * Called when a neighboring block changes.
	 */
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		if (!this.canBlockStay(worldIn, pos))
		{
			worldIn.destroyBlock(pos, true);
		}
	}

	public boolean canBlockStay(World worldIn, BlockPos pos)
	{
		Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

		while (iterator.hasNext())
		{
			EnumFacing enumfacing = (EnumFacing)iterator.next();

			if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock().getMaterial().isSolid())
			{
				return false;
			}
		}

		Block block = worldIn.getBlockState(pos.down()).getBlock();
		boolean inPlains = block.canSustainPlant(worldIn, pos.down(), EnumFacing.UP, this);
		if(!inPlains){
			if (block == StartupCommon.tiberiumtree)
			{
				return true;
			}
		}
		return inPlains;
	}

	/**
	 * Called When an Entity Collided with the Block
	 */
	 public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	 {
		 entityIn.attackEntityFrom(DamageSource.cactus, 1.0F);
	 }

	 /**
	  * Convert the given metadata into a BlockState for this Block
	  */
	 public IBlockState getStateFromMeta(int meta)
	 {
		 return this.getDefaultState().withProperty(AGE, Integer.valueOf(meta));
	 }

	 @SideOnly(Side.CLIENT)
	 public EnumWorldBlockLayer getBlockLayer()
	 {
		 return EnumWorldBlockLayer.CUTOUT;
	 }

	 /**
	  * Convert the BlockState into the correct metadata value
	  */
	 public int getMetaFromState(IBlockState state)
	 {
		 return ((Integer)state.getValue(AGE)).intValue();
	 }

	 protected BlockState createBlockState()
	 {
		 return new BlockState(this, new IProperty[] {AGE});
	 }

	 @Override
	 public net.minecraftforge.common.EnumPlantType getPlantType(net.minecraft.world.IBlockAccess world, BlockPos pos)
	 {
		 return net.minecraftforge.common.EnumPlantType.Plains;
	 }

	 @Override
	 public IBlockState getPlant(net.minecraft.world.IBlockAccess world, BlockPos pos)
	 {
		 return world.getBlockState(pos);
	 }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityTiberiumTree();
	}

}
