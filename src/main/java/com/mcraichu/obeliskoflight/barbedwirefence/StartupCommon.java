package com.mcraichu.obeliskoflight.barbedwirefence;

import com.mcraichu.obeliskoflight.obelisk.Obelisk;
import com.mcraichu.obeliskoflight.obelisk.TileEntityObelisk;

import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StartupCommon {

	public static BarbedWireFence barbed_wire_fence;

	public static void preInitCommon()
	{
		// each instance of your block should have a name that is unique within your mod.  use lower case.
		barbed_wire_fence = (BarbedWireFence)(new BarbedWireFence(Material.iron)).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeMetal).setUnlocalizedName(BarbedWireFence.unl_name);
		GameRegistry.registerBlock(barbed_wire_fence, BarbedWireFence.unl_name);
		// you don't need to register an item corresponding to the block, GameRegistry.registerBlock does this automatically.
		//GameRegistry.registerTileEntity(TileEntityGag.class, BarbedWireFence.unl_name + "_te");
	}

	public static void initCommon()
	{
	}

	public static void postInitCommon()
	{
	}
}
