package com.mcraichu.obeliskoflight.itembarrel;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StartupCommon {

	public static ToolMaterial NODSTEEL = EnumHelper.addToolMaterial("NODSTEEL", 3, 1000, 10.0F, 4.0F, 15);
	
	public static ItemBarrel item_barrel;

	public static void preInitCommon()
	{
		// each instance of your block should have a name that is unique within your mod.  use lower case.
		item_barrel = (ItemBarrel)(new ItemBarrel(NODSTEEL).setUnlocalizedName(ItemBarrel.unl_name));
		GameRegistry.registerItem(item_barrel, ItemBarrel.unl_name);
	}

	public static void initCommon()
	{
	}

	public static void postInitCommon()
	{
	}
}
