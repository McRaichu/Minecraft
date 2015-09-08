package com.mcraichu.obeliskoflight.nodarmor;

import com.mcraichu.obeliskoflight.ObeliskOfLightMain;
import com.mcraichu.obeliskoflight.ObeliskOfLightTab;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;

public class NodArmor extends ItemArmor{

	public static String unl_name = "nodarmor";
	private EntityLivingBase ent;
	private int duration = 400;
	private int ticksCooldown = 0;
	private int cooldown = 3600;

	public NodArmor(ArmorMaterial material, int renderIndex, int armorType) {
		super(material, renderIndex, armorType);
		ent = null;
		this.setCreativeTab(ObeliskOfLightMain.tabobelisk);
	}


	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if(!world.isRemote){
			if(checkSet(player,false,false,true,true)){
				if(player.isBurning()){
				effectPlayer(player, Potion.moveSpeed, 4);
				}
			}
			if(checkSet(player,true,true,true,true)){

				//if on cooldown dont reg
				if(ticksCooldown > 0 ){
					ticksCooldown--;	
					if(ticksCooldown<=0){
						ticksCooldown = 0;
					}
					return;
				}

				EntityLivingBase ent2 =  player.getLastAttacker();
				//ent2 = entity || ent2 = null
				if((ent==null)&&(ent2==null)){
					return;
				}else if((ent!=null)&&(ent2==null)){
					if(!ent.isDead){
						ent = null;
						return;
					}	
				}else if((ent==null)&&(ent2!=null)){
					//attacked new target
					effectPlayer(player, Potion.regeneration, 2);
					ticksCooldown = cooldown;
				}
			}	
		}

	}   

	public boolean checkSet(EntityPlayer player,boolean hasHelmet, boolean hasChestPlate, boolean hasLeggings, boolean hasBoots){
		boolean set = true;
		if(hasHelmet){
			set = set && ((player.inventory.armorItemInSlot(3) != null) && (player.inventory.armorItemInSlot(3).getItem() == StartupCommon.nodarmor_helmet));
		}
		if(hasChestPlate){
			set = set && ((player.inventory.armorItemInSlot(2) != null) && (player.inventory.armorItemInSlot(2).getItem() == StartupCommon.nodarmor_chestplate));
		}
		if(hasLeggings){
			set = set && ((player.inventory.armorItemInSlot(1) != null) && (player.inventory.armorItemInSlot(1).getItem() == StartupCommon.nodarmor_leggings));
		}
		if(hasBoots){
			set = set && ((player.inventory.armorItemInSlot(0) != null) && (player.inventory.armorItemInSlot(0).getItem() == StartupCommon.nodarmor_boots)); 
		}
		return set;
	}

	private void stopEffectPlayer(EntityPlayer player, Potion potion) {
		//Always effect for 8 seconds, then refresh
		if (player.getActivePotionEffect(potion) != null)
			player.removePotionEffect(potion.id);
	}

	private void effectPlayer(EntityPlayer player, Potion potion, int amplifier) {
		//Always effect for 8 seconds, then refresh
		if (player.getActivePotionEffect(potion) == null || player.getActivePotionEffect(potion).getDuration() <= 1)
			player.addPotionEffect(new PotionEffect(potion.id, duration, amplifier, true, true));
	}

}
