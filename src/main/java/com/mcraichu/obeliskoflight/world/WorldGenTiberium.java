package com.mcraichu.obeliskoflight.world;

import java.util.Random;

import com.mcraichu.obeliskoflight.gemoflight.GemOfLight;
import com.mcraichu.obeliskoflight.tiberium.Tiberium;
import com.mcraichu.obeliskoflight.tiberium.TiberiumTree;

import net.minecraft.block.BlockGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenTiberium extends WorldGenerator{

	private Tiberium tiberium;
	private TiberiumTree tiberium_tree;
	private IBlockState blockstate_tib;
	private IBlockState blockstate_tib_tree;

	public WorldGenTiberium(Tiberium tiberium, TiberiumTree tiberium_tree){
		this.tiberium = tiberium;
		this.tiberium_tree = tiberium_tree;
		this.blockstate_tib = tiberium.getDefaultState();
		this.blockstate_tib_tree = tiberium_tree.getDefaultState();
	}

	@Override
	public boolean generate(World worldIn, Random p_180709_2_, BlockPos p_180709_3_) {

		BiomeGenBase bgb = worldIn.getBiomeGenForCoords(p_180709_3_);
		if((bgb.biomeName != "Plains")&&(bgb.biomeName != "Savanna")&&(bgb.biomeName != "Desert")){
			return false;
		}

		BlockPos blockpos1 = p_180709_3_.add(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8), 0, p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));

		BlockPos blockpos2 = worldIn.getTopSolidOrLiquidBlock(blockpos1);

		boolean isAir = worldIn.isAirBlock(blockpos2);
		boolean isSolid = worldIn.isSideSolid(blockpos2.down(), EnumFacing.UP);
		boolean overMinHeight = (blockpos2.getY() >= blockpos1.getY());
		boolean isGrass = BlockGrass.class.isInstance(worldIn.getBlockState(blockpos2.down()).getBlock());
		boolean hasSky = (!worldIn.provider.getHasNoSky()); 

		if (isSolid && overMinHeight && hasSky && isGrass)
		{
			int amount = 16 + p_180709_2_.nextInt(16);
			boolean hasSeeder = false;
			if(tiberium_tree.canPlaceBlockAt(worldIn, blockpos2))
			{
				hasSeeder = true;
				worldIn.setBlockState(blockpos2, this.tiberium_tree.getStateFromMeta(2));
				worldIn.setBlockState(blockpos2.up(1), this.tiberium_tree.getStateFromMeta(1));
				worldIn.setBlockState(blockpos2.up(2), this.tiberium_tree.getStateFromMeta(0));
//				System.out.println("Generated seeder at: " + blockpos2.getX() + " , " + blockpos2.getZ() );
			}
			for(int i=0; i<amount;i++)
			{
				
				if(generateTiberium(worldIn, p_180709_2_, p_180709_3_, hasSeeder))
				{
//					System.out.println("Generated Tiberium: " + i);
				}
			}

			return true;

		}

		return false;
	}
	
	public boolean generateTiberium(World worldIn, Random p_180709_2_, BlockPos p_180709_3_, boolean hasSeeder) {

		BiomeGenBase bgb = worldIn.getBiomeGenForCoords(p_180709_3_);
		if(bgb.biomeName != "Plains"){
			return false;
		}

		int radius = 5;
		
		BlockPos blockpos1 = p_180709_3_.add(p_180709_2_.nextInt(radius) - p_180709_2_.nextInt(radius), 0, p_180709_2_.nextInt(radius) - p_180709_2_.nextInt(radius));

		if(hasSeeder)
		{
			while(p_180709_3_.distanceSq(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ()) < 1.75 )
			{
				blockpos1 = p_180709_3_.add(p_180709_2_.nextInt(radius) - p_180709_2_.nextInt(radius), 0, p_180709_2_.nextInt(radius) - p_180709_2_.nextInt(radius));
			}
		}


		BlockPos blockpos2 = worldIn.getTopSolidOrLiquidBlock(blockpos1);

		boolean isAir = worldIn.isAirBlock(blockpos2);
		boolean isSolid = worldIn.isSideSolid(blockpos2.down(), EnumFacing.UP);
		boolean overMinHeight = (blockpos2.getY() >= blockpos1.getY());
		boolean isGrass = BlockGrass.class.isInstance(worldIn.getBlockState(blockpos2.down()).getBlock());
		boolean hasSky = (!worldIn.provider.getHasNoSky()); 

		if (isSolid && overMinHeight && hasSky && isGrass)
		{

			worldIn.setBlockState(blockpos2, this.blockstate_tib);

			return true;

		}

		return false;
	}

}
