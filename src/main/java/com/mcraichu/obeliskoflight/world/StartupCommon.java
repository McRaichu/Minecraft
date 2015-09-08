package com.mcraichu.obeliskoflight.world;

import com.mcraichu.obeliskoflight.world.ObeliskWorldGenerator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StartupCommon {

	public static void preInitCommon()
	{
	}

	public static void initCommon()
	{
		GameRegistry.registerWorldGenerator(new ObeliskWorldGenerator(), 0);
	}

	public static void postInitCommon()
	{
	}
}
