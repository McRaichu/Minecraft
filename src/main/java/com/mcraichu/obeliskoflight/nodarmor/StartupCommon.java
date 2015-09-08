package com.mcraichu.obeliskoflight.nodarmor;

import com.mcraichu.obeliskoflight.Reference;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StartupCommon {
	
	public static ArmorMaterial NODARMOR = EnumHelper.addArmorMaterial("NODARMOR", Reference.MODID + ":" + NodArmor.unl_name, 18, new int[]{3, 8, 6, 3}, 30);

	public static NodArmor nodarmor_helmet;
	public static NodArmor nodarmor_chestplate;
	public static NodArmor nodarmor_leggings;
	public static NodArmor nodarmor_boots;

	public static void preInitCommon()
	{
		// each instance of your block should have a name that is unique within your mod.  use lower case.
		// you don't need to register an item corresponding to the block, GameRegistry.registerBlock does this automatically.
		nodarmor_helmet = (NodArmor)(new NodArmor(NODARMOR, 1, 0).setUnlocalizedName(NodArmor.unl_name + "_helmet"));
		nodarmor_chestplate = (NodArmor)(new NodArmor(NODARMOR, 1, 1).setUnlocalizedName(NodArmor.unl_name + "_chestplate"));
		nodarmor_leggings = (NodArmor)(new NodArmor(NODARMOR, 2, 2).setUnlocalizedName(NodArmor.unl_name + "_leggings"));
		nodarmor_boots = (NodArmor)(new NodArmor(NODARMOR, 1, 3).setUnlocalizedName(NodArmor.unl_name + "_boots"));
		
		GameRegistry.registerItem(nodarmor_helmet, NodArmor.unl_name + "_helmet");
		GameRegistry.registerItem(nodarmor_chestplate, NodArmor.unl_name + "_chestplate");
		GameRegistry.registerItem(nodarmor_leggings, NodArmor.unl_name + "_leggings");
		GameRegistry.registerItem(nodarmor_boots, NodArmor.unl_name + "_boots");
	}

	public static void initCommon()
	{
	}

	public static void postInitCommon()
	{
	}
}
