package com.mcraichu.obeliskoflight.obelisk;

import com.mcraichu.obeliskoflight.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StartupClient {
	
	public static Item itemObelisk;
	
	public static void preInitClient()
	{
	}

	public static void initClient()
	{
		itemObelisk = GameRegistry.findItem(Reference.MODID, Obelisk.unl_name);
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(Reference.MODID + ":" + Obelisk.unl_name, "inventory");
		final int DEFAULT_ITEM_SUBTYPE = 0;
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemObelisk, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityObelisk.class, new TileEntitySpecialRendererObelisk());
	}

	public static void postInitClient()
	{
	}
}
