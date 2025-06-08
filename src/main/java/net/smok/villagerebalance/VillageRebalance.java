package net.smok.villagerebalance;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.smok.villagerebalance.trade.OffersLoader;
import net.smok.villagerebalance.trade.conditions.Conditions;
import net.smok.villagerebalance.trade.functions.ItemFunctions;

public class VillageRebalance implements ModInitializer {

	@Override
	public void onInitialize() {
		Values.init();
		Conditions.init();
		ItemFunctions.init();

		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new OffersLoader());
	}
}