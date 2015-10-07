package com.mcraichu.obeliskoflight.tiberium;

import com.mcraichu.obeliskoflight.Reference;
import com.mcraichu.obeliskoflight.obeliskpart.ObeliskPart;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.b3d.B3DLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StartupClient {
	
	public static Item itemTiberium;
	public static Item itemTiberiumTree;
	
	public static void preInitClient()
	{
//		itemTiberium = Item.getItemFromBlock(StartupCommon.tiberium);
//		
//		B3DLoader.instance.addDomain(Reference.MODID);
//		
//		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(Reference.MODID + ":"+Tiberium.unl_name, "inventory");
//		final int DEFAULT_ITEM_SUBTYPE = 0;
//		ModelLoader.setCustomModelResourceLocation(itemTiberium, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
		
	}

	public static void initClient()
	{
		itemTiberium = GameRegistry.findItem(Reference.MODID, Tiberium.unl_name);
		ModelResourceLocation itemModelResourceLocation2 = new ModelResourceLocation(Reference.MODID + ":"+Tiberium.unl_name, "inventory");
		final int DEFAULT_ITEM_SUBTYPE = 0;
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemTiberium, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation2);

		itemTiberiumTree = GameRegistry.findItem(Reference.MODID, TiberiumTree.unl_name);
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(Reference.MODID + ":"+TiberiumTree.unl_name, "inventory");
//		final int DEFAULT_ITEM_SUBTYPE = 0;
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemTiberiumTree, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
		
	}

	public static void postInitClient()
	{
	}
}
