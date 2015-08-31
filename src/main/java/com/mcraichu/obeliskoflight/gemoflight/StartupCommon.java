package com.mcraichu.obeliskoflight.gemoflight;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StartupCommon {

	public static GemOfLight gem_of_light;

	public static void preInitCommon()
	{
		// each instance of your block should have a name that is unique within your mod.  use lower case.
		gem_of_light = (GemOfLight)(new GemOfLight(Material.iron).setUnlocalizedName(GemOfLight.unl_name));
		GameRegistry.registerBlock(gem_of_light, GemOfLight.unl_name);
		// you don't need to register an item corresponding to the block, GameRegistry.registerBlock does this automatically.
		GameRegistry.registerTileEntity(TileEntityGemOfLight.class, GemOfLight.unl_name + "_te");
	}

	public static void initCommon()
	{
	}

	public static void postInitCommon()
	{
	}
}
