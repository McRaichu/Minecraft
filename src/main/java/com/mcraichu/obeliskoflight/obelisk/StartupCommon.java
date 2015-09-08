package com.mcraichu.obeliskoflight.obelisk;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StartupCommon {

	public static Obelisk obelisk;

	public static void preInitCommon()
	{
		// each instance of your block should have a name that is unique within your mod.  use lower case.
		obelisk = (Obelisk)(new Obelisk(Material.iron).setUnlocalizedName(Obelisk.unl_name));
		GameRegistry.registerBlock(obelisk, Obelisk.unl_name);
		// you don't need to register an item corresponding to the block, GameRegistry.registerBlock does this automatically.
		GameRegistry.registerTileEntity(TileEntityObelisk.class, Obelisk.unl_name + "_te");
	}

	public static void initCommon()
	{
	}

	public static void postInitCommon()
	{
	}
}
