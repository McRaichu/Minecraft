package com.mcraichu.obeliskoflight.world;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import com.mcraichu.obeliskoflight.ObeliskOfLightMain;
import com.mcraichu.obeliskoflight.Reference;
import com.mcraichu.obeliskoflight.harvester.Harvester;
import com.mcraichu.obeliskoflight.hovercraft.Hovercraft;
import com.mcraichu.obeliskoflight.nodturret.NodShell;
import com.mcraichu.obeliskoflight.stealthtank.NodRocket;
import com.mcraichu.obeliskoflight.stealthtank.StealthTank;

public class Entities {

	public static void preinit(ObeliskOfLightMain mod){
		int entityID = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(NodShell.class, Reference.MODID+".NodShell", entityID);
		EntityRegistry.registerModEntity(NodShell.class, Reference.MODID+".NodShell", entityID, mod , 64, 10, true);
		
		entityID = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(Hovercraft.class, Reference.MODID+".Hovercraft", entityID);
		EntityRegistry.registerModEntity(Hovercraft.class, Reference.MODID+".Hovercraft", entityID, mod , 64, 10, true);
		
		entityID = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(Harvester.class, Reference.MODID+".Harvester", entityID, 0x3F5505, 0x4E6414);
		EntityRegistry.registerModEntity(Harvester.class, Reference.MODID+".Harvester", entityID, mod , 64, 3, false);
		
		entityID = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(StealthTank.class, Reference.MODID+".StealthTank", entityID, 0x3F9905, 0x4E8814);
		EntityRegistry.registerModEntity(StealthTank.class, Reference.MODID+".StealthTank", entityID, mod , 64, 3, false);
		EntityRegistry.addSpawn(StealthTank.class, 20, 1, 1, EnumCreatureType.MONSTER, BiomeGenBase.forest, BiomeGenBase.forestHills,
				BiomeGenBase.taiga, BiomeGenBase.taigaHills, BiomeGenBase.birchForest,BiomeGenBase.birchForestHills, BiomeGenBase.plains, 
				BiomeGenBase.savanna, BiomeGenBase.savannaPlateau, BiomeGenBase.roofedForest, BiomeGenBase.megaTaiga);
		
		entityID = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(NodRocket.class, Reference.MODID+".NodRocket", entityID);
		EntityRegistry.registerModEntity(NodRocket.class, Reference.MODID+".NodRocket", entityID, mod , 64, 10, true);
		
		
	}
}
