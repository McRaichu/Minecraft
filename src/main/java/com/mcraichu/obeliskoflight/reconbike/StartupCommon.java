package com.mcraichu.obeliskoflight.reconbike;

import com.mcraichu.obeliskoflight.gemoflight.GemOfLight;
import com.mcraichu.obeliskoflight.gemoflight.TileEntityGemOfLight;
import com.mcraichu.obeliskoflight.hovercraft.ItemHovercraft;
import com.mcraichu.obeliskoflight.obelisk.Obelisk;
import com.mcraichu.obeliskoflight.obelisk.TileEntityObelisk;
import com.mcraichu.obeliskoflight.tiberium.Tiberium;

import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StartupCommon {
	
	public static ItemReconBike item_reconbike;
	
	public static void preInitCommon()
	{
		item_reconbike = (ItemReconBike)(new ItemReconBike().setUnlocalizedName(ItemReconBike.unl_name));
		GameRegistry.registerItem(item_reconbike, ItemReconBike.unl_name);
	}

	public static void initCommon()
	{
	}

	public static void postInitCommon()
	{
	}
}
