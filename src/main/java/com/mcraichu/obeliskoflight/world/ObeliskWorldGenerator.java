package com.mcraichu.obeliskoflight.world;

import java.util.Random;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class ObeliskWorldGenerator implements IWorldGenerator{

	private WorldGenerator gen_gem_of_light;
	private int countChunks = 0;
	private int countGems = 0;
	
	public ObeliskWorldGenerator(){
		this.gen_gem_of_light = new WorldGenGemOfLight(com.mcraichu.obeliskoflight.gemoflight.StartupCommon.gem_of_light);
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(world.getWorldType() == WorldType.FLAT){
			return;
		}
		switch (world.provider.getDimensionId()) {
		case 0: // Overworld
			//WorldGenGemOfLight ignores maxheight aka takes the heighest solid block
			this.runGenerator(this.gen_gem_of_light, world, random, chunkX, chunkZ, 0.03f, 1, 84, 84);
			break;
		case -1: // Nether

			break;
		case 1: // End
			break;
		}	
	}

	private void runGenerator(WorldGenerator generator, World world, Random rand, int chunk_X, int chunk_Z, float chancesToSpawn, int numberOfBlocks, int minHeight, int maxHeight) {
		if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
			throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");

		int temp = 0;
		int heightDiff = maxHeight - minHeight + 1;
		if(chancesToSpawn > rand.nextFloat()){
			for (int i = 0; i < numberOfBlocks; i++) {
				//middle of chunk
				int x = chunk_X * 16 + 8;
				int y = minHeight + rand.nextInt(heightDiff);
				int z = chunk_Z * 16 + 8;
				if(generator.generate(world, rand, new BlockPos(x, y, z))){
					countGems++;
					temp++;
				}
			}
			System.out.println("In chunk number " + countChunks + " with (" + chunk_X + "," + chunk_Z + ") generated " + temp + " gems (Total: " + countGems + ").");
		}
		countChunks++;
	}
	
}
