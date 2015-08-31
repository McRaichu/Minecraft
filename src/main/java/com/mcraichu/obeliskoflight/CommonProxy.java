package com.mcraichu.obeliskoflight;

public class CommonProxy {

	public void preInit(){
		com.mcraichu.obeliskoflight.obeliskpart.StartupCommon.preInitCommon();
		com.mcraichu.obeliskoflight.obelisk.StartupCommon.preInitCommon();
	}

	public void init(){
		com.mcraichu.obeliskoflight.obeliskpart.StartupCommon.initCommon();
		com.mcraichu.obeliskoflight.obelisk.StartupCommon.initCommon();
	}

	public void postInit(){
		com.mcraichu.obeliskoflight.obeliskpart.StartupCommon.postInitCommon();
		com.mcraichu.obeliskoflight.obelisk.StartupCommon.postInitCommon();
	}

}
