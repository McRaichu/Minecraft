package com.mcraichu.obeliskoflight;

public class ClientProxy extends CommonProxy{

	public void preInit(){
		super.preInit();
		com.mcraichu.obeliskoflight.obeliskpart.StartupClient.preInitClient();
		com.mcraichu.obeliskoflight.obelisk.StartupClient.preInitClient();
		com.mcraichu.obeliskoflight.gemoflight.StartupClient.preInitClient();
	}

	public void init(){
		super.init();
		com.mcraichu.obeliskoflight.obeliskpart.StartupClient.initClient();
		com.mcraichu.obeliskoflight.obelisk.StartupClient.initClient();
		com.mcraichu.obeliskoflight.gemoflight.StartupClient.initClient();
	}

	public void postInit(){
		super.postInit();
		com.mcraichu.obeliskoflight.obeliskpart.StartupClient.postInitClient();
		com.mcraichu.obeliskoflight.obelisk.StartupClient.postInitClient();
		com.mcraichu.obeliskoflight.gemoflight.StartupClient.postInitClient();
	}

}
