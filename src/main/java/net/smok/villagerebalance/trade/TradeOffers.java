package net.smok.villagerebalance.trade;

import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record TradeOffers(Map<Identifier, OfferFactory> offers) {

    public @NotNull List<OfferFactory> get(MerchantEntity entity, int addRecipeCount) {
        List<OfferFactory> offerPool = offers.values().stream().filter(offer -> offer
                .match(entity)).toList();


        if (offerPool.size() > addRecipeCount) {
            List<OfferFactory> offers = new ArrayList<>();
            while (offers.size() < addRecipeCount) {
                offers.add(offerPool.get(entity.getRandom().nextInt(offerPool.size())));
            }
            return offers;
        } else {
            return offerPool;
        }
    }
}
