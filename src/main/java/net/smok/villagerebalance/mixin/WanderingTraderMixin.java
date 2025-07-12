package net.smok.villagerebalance.mixin;

import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.village.TradeOfferList;
import net.smok.villagerebalance.Values;
import net.smok.villagerebalance.trade.OfferFactory;
import net.smok.villagerebalance.utility.TradeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(WanderingTraderEntity.class)
public class WanderingTraderMixin {

    @Inject(method = "fillRecipes", at = @At("HEAD"), cancellable = true)
    private void fillRecipes(CallbackInfo ci) {

        ci.cancel();
        MerchantEntity entity = (MerchantEntity) (Object) this;
        TradeOfferList offers = entity.getOffers();
        List<OfferFactory> purchasing = TradeRegistries.tradeOffers.get(entity, Values.WANDERING_TRADER_PURCHASING_OFFERS, Values.PURCHASING);
        List<OfferFactory> ordinary = TradeRegistries.tradeOffers.get(entity, Values.WANDERING_TRADER_ORDINARY_OFFERS, Values.ORDINARY);
        List<OfferFactory> special = TradeRegistries.tradeOffers.get(entity, Values.WANDERING_TRADER_SPECIAL_OFFERS, Values.SPECIAL);

        for (OfferFactory offerFactory : purchasing) offers.add(offerFactory.create(entity, entity.getRandom()));
        for (OfferFactory offerFactory : ordinary) offers.add(offerFactory.create(entity, entity.getRandom()));
        for (OfferFactory offerFactory : special) offers.add(offerFactory.create(entity, entity.getRandom()));
    }
}
