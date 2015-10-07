package com.mcraichu.obeliskoflight.itembarrel;

import com.mcraichu.obeliskoflight.ObeliskOfLightMain;
import com.mcraichu.obeliskoflight.ObeliskOfLightTab;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentKnockback;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;

public class ItemBarrel extends ItemSword{

	public static String unl_name = "item_barrel";
		
	public ItemBarrel(ToolMaterial material) {
		super(material);
		// TODO Auto-generated constructor stub
		this.setCreativeTab(ObeliskOfLightMain.tabobelisk);
	}

}
