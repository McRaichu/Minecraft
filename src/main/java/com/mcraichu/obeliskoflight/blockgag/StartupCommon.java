package com.mcraichu.obeliskoflight.blockgag;

import com.mcraichu.obeliskoflight.obelisk.Obelisk;
import com.mcraichu.obeliskoflight.obelisk.TileEntityObelisk;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StartupCommon {

	public static BlockGag obelisk_part;

	public static void preInitCommon()
	{
		// each instance of your block should have a name that is unique within your mod.  use lower case.
		obelisk_part = (BlockGag)(new BlockGag().setUnlocalizedName(BlockGag.unl_name));
		GameRegistry.registerBlock(obelisk_part, BlockGag.unl_name);
		// you don't need to register an item corresponding to the block, GameRegistry.registerBlock does this automatically.
		GameRegistry.registerTileEntity(TileEntityGag.class, BlockGag.unl_name + "_te");
	}

	public static void initCommon()
	{
	}

	public static void postInitCommon()
	{
	}
}
