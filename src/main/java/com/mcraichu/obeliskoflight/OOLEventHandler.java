package com.mcraichu.obeliskoflight;

import com.mcraichu.obeliskoflight.orca.Orca;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;


public class OOLEventHandler {

	@SubscribeEvent
	public void flyUp(InputEvent.KeyInputEvent event)
	{
		if(!FMLClientHandler.instance().isGUIOpen(GuiChat.class))
		{
			if(Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown())
			{
				EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
				if((player.ridingEntity != null) && (player.ridingEntity instanceof Orca)){
//					event.g
					player.ridingEntity.motionY = +0.25;
				}
			}
			if(Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown())
			{
				EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
				if((player.ridingEntity != null) && (player.ridingEntity instanceof Orca)){
					//event.setCanceled(true);
					player.ridingEntity.motionY = -0.25;
				}
			}
		}
	}
	
}
