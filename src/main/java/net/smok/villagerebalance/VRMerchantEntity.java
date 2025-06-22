package net.smok.villagerebalance;

import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.smok.villagerebalance.trade.OfferFactory;
import net.smok.villagerebalance.trade.TradeOffers;
import net.smok.villagerebalance.utility.TradeRegistries;

public interface VRMerchantEntity {

    void addTrade(TradeOffer trade);

    MerchantEntity asMerchant();

    default void addTrade(OfferFactory trade) {
        addTrade(trade.create(asMerchant(), asMerchant().getRandom()));
    }
}
