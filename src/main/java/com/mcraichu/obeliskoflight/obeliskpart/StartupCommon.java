package com.mcraichu.obeliskoflight.obeliskpart;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StartupCommon {

	public static ObeliskPart obelisk_part;

	public static void preInitCommon()
	{
		// each instance of your block should have a name that is unique within your mod.  use lower case.
		obelisk_part = (ObeliskPart)(new ObeliskPart().setUnlocalizedName(ObeliskPart.unl_name));
		GameRegistry.registerBlock(obelisk_part, ObeliskPart.unl_name);
		// you don't need to register an item corresponding to the block, GameRegistry.registerBlock does this automatically.
	}

	public static void initCommon()
	{
	}

	public static void postInitCommon()
	{
	}
}
