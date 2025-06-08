package net.smok.villagerebalance.trade;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.smok.villagerebalance.Debug;
import net.smok.villagerebalance.Values;
import net.smok.villagerebalance.utility.TradeRegistries;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class OffersLoader implements SimpleSynchronousResourceReloadListener {

    @Override
    public Identifier getFabricId() {
        return new Identifier(Values.MOD_ID, "content_loader");
    }


    @Override
    public void reload(ResourceManager manager) {

        ImmutableMap.Builder<Identifier, OfferFactory> builder = new ImmutableMap.Builder<>();
        for (Map.Entry<Identifier, JsonObject> entry : readResources(manager, "village").entrySet()) {
            try {
                OfferFactory factory = OfferFactory.DEFAULT_FACTORY.childFromJson(entry.getValue());

                if (!factory.isEmpty()) builder.put(entry.getKey(), factory);
                else Debug.warn("Offer " + entry.getKey() + " is empty\n" + factory);

            } catch (JsonParseException e) {
                Debug.warn("Error occurred while loading resource json " + entry.getKey());
                Debug.err(e.toString());
            }
        }
        TradeRegistries.tradeOffers = new TradeOffers(builder.build());
    }


    private HashMap<Identifier, JsonObject> readResources(ResourceManager manager, String startPath) {
        HashMap<Identifier, JsonObject> result = new HashMap<>();

        for(Map.Entry<Identifier, Resource> entry : manager.findResources(startPath,
                path -> path.getPath().endsWith(".json")).entrySet()) {

            try (InputStream stream = entry.getValue().getInputStream()) {
                JsonObject materialObject = JsonHelper.deserialize(new InputStreamReader(stream));

                result.put(entry.getKey(), materialObject);
            } catch (Exception e) {
                Debug.err("Error occurred while loading resource json {}\n", entry.getKey(), e);
            }
        }

        return result;
    }
}
