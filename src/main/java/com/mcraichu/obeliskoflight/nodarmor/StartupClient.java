package com.mcraichu.obeliskoflight.nodarmor;

import com.mcraichu.obeliskoflight.Reference;
import com.mcraichu.obeliskoflight.nodarmor.NodArmor;

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
		final int DEFAULT_ITEM_SUBTYPE = 0;
	    ModelResourceLocation itemModelResourceLocation_h = new ModelResourceLocation(Reference.MODID + ":" + NodArmor.unl_name + "_helmet", "inventory");  
	    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(StartupCommon.nodarmor_helmet, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation_h);
	    
	    ModelResourceLocation itemModelResourceLocation_c = new ModelResourceLocation(Reference.MODID + ":" + NodArmor.unl_name + "_chestplate", "inventory");  
	    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(StartupCommon.nodarmor_chestplate, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation_c);
	    
	    ModelResourceLocation itemModelResourceLocation_l = new ModelResourceLocation(Reference.MODID + ":" + NodArmor.unl_name + "_leggings", "inventory");  
	    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(StartupCommon.nodarmor_leggings, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation_l);
	  
	    ModelResourceLocation itemModelResourceLocation_b = new ModelResourceLocation(Reference.MODID + ":" + NodArmor.unl_name + "_boots", "inventory");  
	    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(StartupCommon.nodarmor_boots, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation_b);
	  
	}

	public static void postInitClient()
	{
	}
}
