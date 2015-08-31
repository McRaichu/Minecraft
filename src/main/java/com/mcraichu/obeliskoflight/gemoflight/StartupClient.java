package com.mcraichu.obeliskoflight.gemoflight;

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
		Item itemGem_of_light = GameRegistry.findItem(Reference.MODID, GemOfLight.unl_name);
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(Reference.MODID + ":" + GemOfLight.unl_name, "inventory");
		final int DEFAULT_ITEM_SUBTYPE = 0;
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemGem_of_light, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGemOfLight.class, new TileEntitySpecialRendererGemOfLight());
	}

	public static void postInitClient()
	{
	}
}
