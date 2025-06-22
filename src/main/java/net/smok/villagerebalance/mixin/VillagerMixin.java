package net.smok.villagerebalance.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.smok.villagerebalance.VRMerchantEntity;
import net.smok.villagerebalance.trade.OfferFactory;
import net.smok.villagerebalance.utility.TradeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public abstract class VillagerMixin  {

    @Unique
    private static final int ADD_RECIPE_COUNT = 2;

    @Inject(method = "fillRecipes", at = @At("HEAD"), cancellable = true)
    private void fillRecipesInject(CallbackInfo ci) {

        ci.cancel();
        MerchantEntity entity = (MerchantEntity) (Object) this;
        TradeOfferList offers = entity.getOffers();
        for (OfferFactory offerFactory : TradeRegistries.tradeOffers.get(entity, ADD_RECIPE_COUNT)) {
            offers.add(offerFactory.create(entity, entity.getRandom()));
        }
    }
}
