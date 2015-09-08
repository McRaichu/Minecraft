package com.mcraichu.obeliskoflight;

public class CommonProxy {

	public void preInit(){
		com.mcraichu.obeliskoflight.obeliskpart.StartupCommon.preInitCommon();
		com.mcraichu.obeliskoflight.obelisk.StartupCommon.preInitCommon();
		com.mcraichu.obeliskoflight.gemoflight.StartupCommon.preInitCommon();
		com.mcraichu.obeliskoflight.itemobelisk.StartupCommon.preInitCommon();
		com.mcraichu.obeliskoflight.blockgag.StartupCommon.preInitCommon();
		com.mcraichu.obeliskoflight.nodarmor.StartupCommon.preInitCommon();
	}

	public void init(){
		com.mcraichu.obeliskoflight.obeliskpart.StartupCommon.initCommon();
		com.mcraichu.obeliskoflight.obelisk.StartupCommon.initCommon();
		com.mcraichu.obeliskoflight.gemoflight.StartupCommon.initCommon();
		com.mcraichu.obeliskoflight.itemobelisk.StartupCommon.initCommon();
		com.mcraichu.obeliskoflight.blockgag.StartupCommon.initCommon();
		com.mcraichu.obeliskoflight.nodarmor.StartupCommon.initCommon();
		
		//only here init recipes
		com.mcraichu.obeliskoflight.recipes.StartupCommon.initCommon();
		
		//only here init worldgenerator
		com.mcraichu.obeliskoflight.world.StartupCommon.initCommon();
	}

	public void postInit(){
		com.mcraichu.obeliskoflight.obeliskpart.StartupCommon.postInitCommon();
		com.mcraichu.obeliskoflight.obelisk.StartupCommon.postInitCommon();
		com.mcraichu.obeliskoflight.gemoflight.StartupCommon.postInitCommon();
		com.mcraichu.obeliskoflight.itemobelisk.StartupCommon.postInitCommon();
		com.mcraichu.obeliskoflight.blockgag.StartupCommon.postInitCommon();
		com.mcraichu.obeliskoflight.nodarmor.StartupCommon.postInitCommon();
	}

}
