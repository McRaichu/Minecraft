package com.mcraichu.obeliskoflight.tiberium;

import com.mcraichu.obeliskoflight.ObeliskOfLightMain;
import com.mcraichu.obeliskoflight.nodturret.NodShell;
import com.mcraichu.obeliskoflight.obelisk.Obelisk;
import com.mcraichu.obeliskoflight.obelisk.TileEntityObelisk;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StartupCommon {

	public static Tiberium tiberium;
	public static TiberiumTree tiberiumtree;

	public static void preInitCommon()
	{
		// each instance of your block should have a name that is unique within your mod.  use lower case.
		tiberium = (Tiberium)(new Tiberium(Material.rock).setUnlocalizedName(Tiberium.unl_name));
		GameRegistry.registerBlock(tiberium, Tiberium.unl_name);
		GameRegistry.registerTileEntity(TileEntityTiberium.class, Tiberium.unl_name + "_te");
		
		tiberiumtree = (TiberiumTree)(new TiberiumTree(Material.wood).setUnlocalizedName(TiberiumTree.unl_name));
		GameRegistry.registerBlock(tiberiumtree, TiberiumTree.unl_name);
		GameRegistry.registerTileEntity(TileEntityTiberiumTree.class, TiberiumTree.unl_name + "_te");
		
	}

	public static void initCommon()
	{
	}

	public static void postInitCommon()
	{
	}
}
