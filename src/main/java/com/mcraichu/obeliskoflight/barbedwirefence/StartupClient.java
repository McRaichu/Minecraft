package com.mcraichu.obeliskoflight.barbedwirefence;

import com.mcraichu.obeliskoflight.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StartupClient {
	
	public static Item itemBarbedFence;
	
	public static void preInitClient()
	{
	}

	public static void initClient()
	{
		itemBarbedFence = GameRegistry.findItem(Reference.MODID, BarbedWireFence.unl_name);
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(Reference.MODID + ":"+BarbedWireFence.unl_name, "inventory");
		final int DEFAULT_ITEM_SUBTYPE = 0;
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBarbedFence, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
	}

	public static void postInitClient()
	{
	}
}
