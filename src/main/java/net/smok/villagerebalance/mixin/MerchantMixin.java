package net.smok.villagerebalance.mixin;

import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.smok.villagerebalance.VRMerchantEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MerchantEntity.class)
public abstract class MerchantMixin implements VRMerchantEntity {


    @Shadow @Nullable protected TradeOfferList offers;

    @Shadow public abstract TradeOfferList getOffers();

    @Override
    public void addTrade(TradeOffer trade) {
        getOffers().add(trade);
    }

    @Override
    public MerchantEntity asMerchant() {
        return (MerchantEntity) (Object) this;
    }
}
