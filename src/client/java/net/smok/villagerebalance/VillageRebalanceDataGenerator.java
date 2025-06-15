package net.smok.villagerebalance;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.smok.villagerebalance.trade.OfferFactory;
import net.smok.villagerebalance.trade.data.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class VillageRebalanceDataGenerator implements DataGeneratorEntrypoint {



	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {

		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(TradeOffersDataProvider::new);

	}

	private static class TradeOffersDataProvider implements DataProvider {
		private final DataOutput.PathResolver pathResolver;

		private TradeOffersDataProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryFuture) {
			this.pathResolver = dataOutput.getResolver(DataOutput.OutputType.DATA_PACK, TradeOffersProvider.DIRECTORY);
		}


		@Override
		public CompletableFuture<?> run(DataWriter writer) {
			final List<CompletableFuture<?>> futures = new ArrayList<>();

			for (TradeOffersProvider provider : TradeOffersProviders.providers) {
				provider.fill();
				for (Map.Entry<Identifier, OfferFactory> entry : provider.getObjects().entrySet()) {
					futures.add(DataProvider.writeToPath(writer, entry.getValue().toJson(), pathResolver.resolveJson(entry.getKey())));
				}
			}

			return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
		}

		@Override
		public String getName() {
			return "Offer Factories";
		}
	}

}
