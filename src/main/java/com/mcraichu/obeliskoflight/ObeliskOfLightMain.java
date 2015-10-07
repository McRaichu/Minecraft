package com.mcraichu.obeliskoflight;

import com.mcraichu.obeliskoflight.nodturret.NodShell;
import com.mcraichu.obeliskoflight.world.Entities;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;


@Mod(modid = Reference.MODID, version = Reference.VERSION, name = Reference.MODNAME)
public class ObeliskOfLightMain {

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide=Reference.CLIENT_PROXY_CLASS, serverSide=Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static final ObeliskOfLightTab tabobelisk = new ObeliskOfLightTab("tabObelisk");
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Entities.preinit(this);
		proxy.preInit();
	
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		
		proxy.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit();
	}

}
