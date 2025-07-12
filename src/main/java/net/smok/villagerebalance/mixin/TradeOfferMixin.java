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

import java.util.Objects;

@Mixin(TradeOffer.class)
public class TradeOfferMixin implements VRTradeOffer {

    @Unique
    private String rarity = "";

    @Override
    public String vRFabric$getRarity() {
        return rarity;
    }

    @Override
    public void vRFabric$setRarity(String rarity) {
        this.rarity = rarity;
    }

    @Inject(method = "<init>(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("TAIL"))
    void nbtConstructor(NbtCompound nbt, CallbackInfo ci) {

        if (nbt.contains("rarity", NbtElement.INT_TYPE)) {
            rarity = nbt.getString("rarity");
        }
    }

    @Inject(method = "toNbt", at = @At("RETURN"), cancellable = true)
    void toNbt(CallbackInfoReturnable<NbtCompound> cir) {
        if (rarity.isEmpty()) return;
        NbtCompound returnValue = cir.getReturnValue();
        returnValue.putString("rarity", rarity);
        cir.setReturnValue(returnValue);
    }
}
