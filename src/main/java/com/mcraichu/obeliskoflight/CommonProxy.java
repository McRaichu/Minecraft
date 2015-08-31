package com.mcraichu.obeliskoflight;

public class CommonProxy {

	public void preInit(){
		com.mcraichu.obeliskoflight.obeliskpart.StartupCommon.preInitCommon();
		com.mcraichu.obeliskoflight.obelisk.StartupCommon.preInitCommon();
		com.mcraichu.obeliskoflight.gemoflight.StartupCommon.preInitCommon();
	}

	public void init(){
		com.mcraichu.obeliskoflight.obeliskpart.StartupCommon.initCommon();
		com.mcraichu.obeliskoflight.obelisk.StartupCommon.initCommon();
		com.mcraichu.obeliskoflight.gemoflight.StartupCommon.initCommon();
	}

	public void postInit(){
		com.mcraichu.obeliskoflight.obeliskpart.StartupCommon.postInitCommon();
		com.mcraichu.obeliskoflight.obelisk.StartupCommon.postInitCommon();
		com.mcraichu.obeliskoflight.gemoflight.StartupCommon.postInitCommon();
	}

}
