package com.mcraichu.obeliskoflight.templeofnod;

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
	
	public static Item itemTempleOfNod;
	
	public static void preInitClient()
	{
		itemTempleOfNod = Item.getItemFromBlock(StartupCommon.templeOfNod);
		
		B3DLoader.instance.addDomain(Reference.MODID);
		
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(Reference.MODID + ":"+TempleOfNod.unl_name, "inventory");
		final int DEFAULT_ITEM_SUBTYPE = 0;
		ModelLoader.setCustomModelResourceLocation(itemTempleOfNod, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
		
	}

	public static void initClient()
	{
	}

	public static void postInitClient()
	{
	}
}
