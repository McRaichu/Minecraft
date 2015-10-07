package com.mcraichu.obeliskoflight.itembarrel;

import com.mcraichu.obeliskoflight.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StartupClient {
	public static void preInitClient()
	{
	}

	public static void initClient()
	{
	    // required in order for the renderer to know how to render your item.  Likely to change in the near future.
	    ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(Reference.MODID + ":" + ItemBarrel.unl_name, "inventory");
	    final int DEFAULT_ITEM_SUBTYPE = 0;
	    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(StartupCommon.item_barrel, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
	  
	}

	public static void postInitClient()
	{
	}
}
