package com.mcraichu.obeliskoflight.hovercraft;

import com.mcraichu.obeliskoflight.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderFireball;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StartupClient {
		
	public static void preInitClient()
	{
	}

	public static void initClient()
	{
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(Reference.MODID + ":" + ItemHovercraft.unl_name, "inventory");
		final int DEFAULT_ITEM_SUBTYPE = 0;
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(StartupCommon.item_hovercraft, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
	
		//RenderingRegistry.registerEntityRenderingHandler(NodRocket.class, new RenderFireball(Minecraft.getMinecraft().getRenderManager(), 1.0f));
		RenderingRegistry.registerEntityRenderingHandler(Hovercraft.class, new HovercraftRenderer(Minecraft.getMinecraft().getRenderManager()));
	}

	public static void postInitClient()
	{
	}
}
