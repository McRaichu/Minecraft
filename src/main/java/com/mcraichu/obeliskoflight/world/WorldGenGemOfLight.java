package com.mcraichu.obeliskoflight.world;

import java.util.Random;

import com.mcraichu.obeliskoflight.gemoflight.GemOfLight;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenGemOfLight extends WorldGenerator{

	private GemOfLight gem;
    private IBlockState blockstate;
	
	public WorldGenGemOfLight(GemOfLight gem){
		this.gem = gem;
		this.blockstate = gem.getDefaultState();
	}
	
	@Override
	public boolean generate(World worldIn, Random p_180709_2_, BlockPos p_180709_3_) {
		
		BlockPos blockpos1 = p_180709_3_.add(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8), 0, p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));
		
		BlockPos blockpos2 = worldIn.getTopSolidOrLiquidBlock(blockpos1);
		
		boolean isAir = worldIn.isAirBlock(blockpos2);
		boolean isSolid = worldIn.isSideSolid(blockpos2.down(), EnumFacing.UP);
		boolean overMinHeight = (blockpos2.getY() >= blockpos1.getY());
		boolean hasSky = (!worldIn.provider.getHasNoSky()); 
		
		if (isSolid && overMinHeight && hasSky)
        {
            worldIn.setBlockState(blockpos2, this.blockstate);
            return true;
            
        }
		
		return false;
	}

}
