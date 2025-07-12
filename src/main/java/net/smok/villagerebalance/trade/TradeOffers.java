package net.smok.villagerebalance.trade;

import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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


    public @NotNull List<OfferFactory> get(MerchantEntity entity, int addRecipeCount, String rarity) {
        List<OfferFactory> offerPool = new ArrayList<>(offers.values().stream()
                .filter(offer -> offer.match(entity) && Objects.equals(offer.rarity(), rarity))
                .toList());


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
