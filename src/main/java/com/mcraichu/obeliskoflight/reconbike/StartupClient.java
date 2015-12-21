package com.mcraichu.obeliskoflight.reconbike;

import com.mcraichu.obeliskoflight.Reference;
import com.mcraichu.obeliskoflight.reconbike.StartupCommon;
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
	
	public static ModelReconBike reconBikeModel;
	
	public static void preInitClient()
	{	
	}

	public static void initClient()
	{
		reconBikeModel = new ModelReconBike();
		RenderingRegistry.registerEntityRenderingHandler(ReconBike.class, new ReconBikeRenderer(Minecraft.getMinecraft().getRenderManager(),reconBikeModel,1.0F));
		
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(Reference.MODID + ":" + ItemReconBike.unl_name, "inventory");
		final int DEFAULT_ITEM_SUBTYPE = 0;
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(StartupCommon.item_reconbike, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
	
	}

	public static void postInitClient()
	{
	}
}
