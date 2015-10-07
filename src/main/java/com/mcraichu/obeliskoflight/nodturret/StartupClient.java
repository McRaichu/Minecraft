package com.mcraichu.obeliskoflight.nodturret;

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
	
	public static Item itemNodTurret;
	
	public static void preInitClient()
	{
	}

	public static void initClient()
	{
		itemNodTurret = GameRegistry.findItem(Reference.MODID, NodTurret.unl_name);
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(Reference.MODID + ":" + NodTurret.unl_name, "inventory");
		final int DEFAULT_ITEM_SUBTYPE = 0;
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemNodTurret, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNodTurret.class, new TileEntitySpecialRendererNodTurret());
		
		//RenderingRegistry.registerEntityRenderingHandler(NodRocket.class, new RenderFireball(Minecraft.getMinecraft().getRenderManager(), 1.0f));
		RenderingRegistry.registerEntityRenderingHandler(NodShell.class, new NodShellRenderer(Minecraft.getMinecraft().getRenderManager()));
	}

	public static void postInitClient()
	{
	}
}
