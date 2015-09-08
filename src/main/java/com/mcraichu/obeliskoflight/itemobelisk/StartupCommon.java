package com.mcraichu.obeliskoflight.itemobelisk;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StartupCommon {

	public static ItemObelisk item_obelisk;

	public static void preInitCommon()
	{
		// each instance of your block should have a name that is unique within your mod.  use lower case.
		item_obelisk = (ItemObelisk)(new ItemObelisk().setUnlocalizedName(ItemObelisk.unl_name));
		GameRegistry.registerItem(item_obelisk, ItemObelisk.unl_name);
	}

	public static void initCommon()
	{
	}

	public static void postInitCommon()
	{
	}
}
