package com.mcraichu.obeliskoflight.orca;

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
	
	public static ModelOrca orcaModel;
	
	public static void preInitClient()
	{	
	}

	public static void initClient()
	{
		orcaModel = new ModelOrca();
		RenderingRegistry.registerEntityRenderingHandler(Orca.class, new OrcaRenderer(Minecraft.getMinecraft().getRenderManager(),orcaModel,1.0F));

	}

	public static void postInitClient()
	{
	}
}
