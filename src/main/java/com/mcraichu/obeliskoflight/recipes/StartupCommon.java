package com.mcraichu.obeliskoflight.recipes;

import com.mcraichu.obeliskoflight.Reference;
import com.mcraichu.obeliskoflight.barbedwirefence.BarbedWireFence;
import com.mcraichu.obeliskoflight.gemoflight.GemOfLight;
import com.mcraichu.obeliskoflight.harvester.ItemHarvester;
import com.mcraichu.obeliskoflight.hovercraft.ItemHovercraft;
import com.mcraichu.obeliskoflight.itembarrel.ItemBarrel;
import com.mcraichu.obeliskoflight.itemobelisk.ItemObelisk;
import com.mcraichu.obeliskoflight.nodarmor.NodArmor;
import com.mcraichu.obeliskoflight.nodturret.NodTurret;
import com.mcraichu.obeliskoflight.obeliskpart.ObeliskPart;
import com.mcraichu.obeliskoflight.tiberium.Tiberium;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

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
		GameRegistry.addShapedRecipe(new ItemStack(GameRegistry.findItem(Reference.MODID, ItemBarrel.unl_name)), new Object[]{
			"III",
			"...",
			"III",
			'I', Items.iron_ingot,
		});
		GameRegistry.addShapedRecipe(new ItemStack(GameRegistry.findItem(Reference.MODID, NodTurret.unl_name)), new Object[]{
			"IIB",
			".G.",
			"SSS",
			'I', Blocks.iron_block,
			'B', new ItemStack(GameRegistry.findItem(Reference.MODID, ItemBarrel.unl_name), 1, OreDictionary.WILDCARD_VALUE),
			'G', Items.gunpowder,
			'S', Blocks.brick_block // note carefully - 'E' not "E" !
		});
		
		GameRegistry.addShapedRecipe(new ItemStack(GameRegistry.findItem(Reference.MODID, BarbedWireFence.unl_name),16), new Object[]{
			"CCC",
			"III",
			"III",
			'I', Items.iron_ingot,
			'C', Blocks.cactus // note carefully - 'E' not "E" !
		});
		GameRegistry.addShapedRecipe(new ItemStack(GameRegistry.findItem(Reference.MODID, ItemHovercraft.unl_name),1), new Object[]{
			"...",
			"ISI",
			"LLL",
			'I', Items.iron_ingot,
			'S', Blocks.wooden_slab,
			'L', Items.leather
		});
		GameRegistry.addShapedRecipe(new ItemStack(GameRegistry.findItem(Reference.MODID, ItemHarvester.unl_name),1), new Object[]{
			"ITI",
			"ICI",
			"III",
			'I', Items.iron_ingot,
			'C', Blocks.chest,
			'T', GameRegistry.findItem(Reference.MODID, Tiberium.unl_name)
		});
		GameRegistry.addShapedRecipe(new ItemStack(GameRegistry.findItem(Reference.MODID, ItemHarvester.unl_name),1), new Object[]{
			"GTD",
			"TCT",
			"ITK",
			'I', Items.iron_ingot,
			'G', Items.gold_ingot,
			'D', Items.diamond,
			'K', Items.coal,
			'C', Blocks.chest,
			'T', GameRegistry.findItem(Reference.MODID, Tiberium.unl_name)
		});
		
		recipesNodArmor();
	}

	public static void postInitCommon()
	{
	}

	//-------------------------------------------------------------------------------------------------------------------------
	
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
