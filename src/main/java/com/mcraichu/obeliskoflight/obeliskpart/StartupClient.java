package com.mcraichu.obeliskoflight.obeliskpart;

import com.mcraichu.obeliskoflight.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StartupClient {
	
	public static Item itemObeliskPart;
	
	public static void preInitClient()
	{
	}

	public static void initClient()
	{
		itemObeliskPart = GameRegistry.findItem(Reference.MODID, ObeliskPart.unl_name);
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(Reference.MODID + ":"+ObeliskPart.unl_name, "inventory");
		final int DEFAULT_ITEM_SUBTYPE = 0;
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemObeliskPart, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
	}

	public static void postInitClient()
	{
	}
}
