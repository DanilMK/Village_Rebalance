package net.smok.villagerebalance;

import net.fabricmc.api.ModInitializer;

public class VillageRebalance implements ModInitializer {

	@Override
	public void onInitialize() {
		Values.init();
	}
}