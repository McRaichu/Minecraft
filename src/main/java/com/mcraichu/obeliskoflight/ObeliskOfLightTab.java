package com.mcraichu.obeliskoflight;

import com.mcraichu.obeliskoflight.itemobelisk.ItemObelisk;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ObeliskOfLightTab extends CreativeTabs{

	public ObeliskOfLightTab(String label) {
		super(label);
		this.setBackgroundImageName("obelisk.png");
	}

	@Override
	public Item getTabIconItem() {
		return com.mcraichu.obeliskoflight.itemobelisk.StartupCommon.item_obelisk;
	}

}
