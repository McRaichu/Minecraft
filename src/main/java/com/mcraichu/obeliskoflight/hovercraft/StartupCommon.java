package com.mcraichu.obeliskoflight.hovercraft;

import com.mcraichu.obeliskoflight.itemobelisk.ItemObelisk;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StartupCommon {

	public static ItemHovercraft item_hovercraft;

	public static void preInitCommon()
	{
		item_hovercraft = (ItemHovercraft)(new ItemHovercraft().setUnlocalizedName(ItemHovercraft.unl_name));
		GameRegistry.registerItem(item_hovercraft, ItemHovercraft.unl_name);
		
	}

	public static void initCommon()
	{
	}

	public static void postInitCommon()
	{
	}
}
