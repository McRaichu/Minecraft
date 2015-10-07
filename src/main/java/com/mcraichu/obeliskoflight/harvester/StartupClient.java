package com.mcraichu.obeliskoflight.harvester;

import com.mcraichu.obeliskoflight.Reference;
import com.mcraichu.obeliskoflight.nodturret.NodShell;
import com.mcraichu.obeliskoflight.nodturret.NodShellRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.b3d.B3DLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StartupClient {
	
	public static Item itemRefinery;
	public static ModelHarvester harvesterModel;
	
	public static void preInitClient()
	{
		itemRefinery = Item.getItemFromBlock(StartupCommon.refinery);
		
		B3DLoader.instance.addDomain(Reference.MODID);
		
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(Reference.MODID + ":"+Refinery.unl_name, "inventory");
		final int DEFAULT_ITEM_SUBTYPE = 0;
		ModelLoader.setCustomModelResourceLocation(itemRefinery, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
		
	}

	public static void initClient()
	{
		harvesterModel = new ModelHarvester();
		RenderingRegistry.registerEntityRenderingHandler(Harvester.class, new HarvesterRenderer(Minecraft.getMinecraft().getRenderManager(),harvesterModel,0.6F));
		
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(Reference.MODID + ":" + ItemHarvester.unl_name, "inventory");
		final int DEFAULT_ITEM_SUBTYPE = 0;
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(StartupCommon.item_harvester, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
	}

	public static void postInitClient()
	{
	}
}
