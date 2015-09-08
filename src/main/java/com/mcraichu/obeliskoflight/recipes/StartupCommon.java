package com.mcraichu.obeliskoflight.recipes;

import com.mcraichu.obeliskoflight.Reference;
import com.mcraichu.obeliskoflight.gemoflight.GemOfLight;
import com.mcraichu.obeliskoflight.itemobelisk.ItemObelisk;
import com.mcraichu.obeliskoflight.nodarmor.NodArmor;
import com.mcraichu.obeliskoflight.obeliskpart.ObeliskPart;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StartupCommon {

	public static void preInitCommon()
	{
	}

	public static void initCommon()
	{
		GameRegistry.addShapedRecipe(new ItemStack(GameRegistry.findItem(Reference.MODID, ObeliskPart.unl_name)), new Object[]{
			"ORO",
			"ODO",
			"ORO",
			'D', Blocks.diamond_block,
			'R', Blocks.redstone_block,
			'O', Blocks.obsidian     // note carefully - 'E' not "E" !
		});
		GameRegistry.addRecipe(new ItemStack(GameRegistry.findItem(Reference.MODID, ItemObelisk.unl_name)), new Object[]{
			"G",
			"O",
			"O",
			'G', GameRegistry.findItem(Reference.MODID, GemOfLight.unl_name),
			'O', GameRegistry.findItem(Reference.MODID, ObeliskPart.unl_name)     // note carefully - 'E' not "E" !
		});
		recipesNodArmor();
	}

	public static void postInitCommon()
	{
	}

	private static void recipesNodArmor(){
		Item item = GameRegistry.findItem(Reference.MODID, NodArmor.unl_name + "_chestplate");
		GameRegistry.addShapedRecipe(new ItemStack(GameRegistry.findItem(Reference.MODID, NodArmor.unl_name + "_chestplate")), new Object[]{
			"I.I",
			"IOI",
			"III",
			'I', Items.iron_ingot,
			'O', GameRegistry.findItem(Reference.MODID, GemOfLight.unl_name)     // note carefully - 'E' not "E" !
		});
		GameRegistry.addShapedRecipe(new ItemStack(GameRegistry.findItem(Reference.MODID, NodArmor.unl_name + "_leggings")), new Object[]{
			"III",
			"IOI",
			"I.I",
			'I', Items.iron_ingot,
			'O', GameRegistry.findItem(Reference.MODID, GemOfLight.unl_name)     // note carefully - 'E' not "E" !
		});
		GameRegistry.addShapedRecipe(new ItemStack(GameRegistry.findItem(Reference.MODID, NodArmor.unl_name + "_helmet")), new Object[]{
			"III",
			"IOI",
			"...",
			'I', Items.iron_ingot,
			'O', GameRegistry.findItem(Reference.MODID, GemOfLight.unl_name)     // note carefully - 'E' not "E" !
		});
		GameRegistry.addShapedRecipe(new ItemStack(GameRegistry.findItem(Reference.MODID, NodArmor.unl_name + "_boots")), new Object[]{
			"...",
			"IOI",
			"I.I",
			'I', Items.iron_ingot,
			'O', GameRegistry.findItem(Reference.MODID, GemOfLight.unl_name)     // note carefully - 'E' not "E" !
		});
	}
}
