package net.smok.villagerebalance.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.village.TradeOffer;
import net.smok.villagerebalance.VRTradeOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TradeOffer.class)
public class TradeOfferMixin implements VRTradeOffer {

    @Unique
    private boolean vanishable = false;

    @Override
    public boolean vRFabric$isVanishable() {
        return vanishable;
    }

    @Override
    public void vRFabric$setVanishable(boolean vanishable) {
        this.vanishable = vanishable;
    }

    @Inject(method = "<init>(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("TAIL"))
    void nbtConstructor(NbtCompound nbt, CallbackInfo ci) {

        if (nbt.contains("vanishable")) {
            vanishable = nbt.getBoolean("vanishable");
        }
    }

    @Inject(method = "toNbt", at = @At("RETURN"), cancellable = true)
    void toNbt(CallbackInfoReturnable<NbtCompound> cir) {
        if (vanishable) {
            NbtCompound returnValue = cir.getReturnValue();
            returnValue.putBoolean("vanishable", true);
            cir.setReturnValue(returnValue);
        }
    }
}
