package net.smok.villagerebalance.trade;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.argument.ItemStringReader;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public record TradeOffers(Map<Identifier, OfferFactory> offers) {

    public OfferFactory get(Identifier key) {
        return offers.get(key);
    }

    public @NotNull List<OfferFactory> get(MerchantEntity entity, int addRecipeCount) {
        List<OfferFactory> offerPool = new ArrayList<>(offers.values().stream().filter(offer -> offer
                .match(entity)).toList());


        if (offerPool.size() > addRecipeCount) {
            List<OfferFactory> offers = new ArrayList<>();
            while (offers.size() < addRecipeCount) {
                int randomIndex = entity.getRandom().nextInt(offerPool.size());
                offers.add(offerPool.get(randomIndex));
                offerPool.remove(randomIndex);
            }
            return offers;
        } else {
            return offerPool;
        }
    }


}
