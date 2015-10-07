package com.mcraichu.obeliskoflight.harvester;

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

	public static Refinery refinery;
	public static ItemHarvester item_harvester;
	
	public static void preInitCommon()
	{
		refinery = (Refinery)(new Refinery(Material.rock).setUnlocalizedName(Refinery.unl_name));
		GameRegistry.registerBlock(refinery, Refinery.unl_name);
		GameRegistry.registerTileEntity(TileEntityRefinery.class, Refinery.unl_name + "_te");
		
		item_harvester = (ItemHarvester)(new ItemHarvester().setUnlocalizedName(ItemHarvester.unl_name));
		GameRegistry.registerItem(item_harvester, ItemHarvester.unl_name);
	}

	public static void initCommon()
	{
	}

	public static void postInitCommon()
	{
	}
}
