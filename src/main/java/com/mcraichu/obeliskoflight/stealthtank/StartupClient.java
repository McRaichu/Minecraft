package com.mcraichu.obeliskoflight.stealthtank;

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
	
	public static ModelStealthTank stealthTankModel;
	
	public static void preInitClient()
	{	
	}

	public static void initClient()
	{
		stealthTankModel = new ModelStealthTank();
		RenderingRegistry.registerEntityRenderingHandler(StealthTank.class, new StealthTankRenderer(Minecraft.getMinecraft().getRenderManager(),stealthTankModel,1.0F));

		RenderingRegistry.registerEntityRenderingHandler(NodRocket.class, new NodRocketRenderer(Minecraft.getMinecraft().getRenderManager()));
		
	}

	public static void postInitClient()
	{
	}
}
