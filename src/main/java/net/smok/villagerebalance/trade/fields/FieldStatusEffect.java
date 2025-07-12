package net.smok.villagerebalance.trade.fields;

import com.google.gson.JsonObject;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.smok.villagerebalance.utility.JsonConvertible;
import org.jetbrains.annotations.NotNull;

public record FieldStatusEffect(StatusEffect statusEffect, int duration) implements JsonConvertible<FieldStatusEffect> {
    @Override
    public FieldStatusEffect childFromJson(@NotNull JsonObject json) {
        StatusEffect statusId = JsonConvertible.getRegister(json, "status_id", Registries.STATUS_EFFECT, this.statusEffect);
        int duration = JsonHelper.getInt(json, "duration", this.duration);
        return new FieldStatusEffect(statusId, duration);
    }

    @Override
    public void toJson(@NotNull JsonObject json) {
        Identifier id = Registries.STATUS_EFFECT.getId(statusEffect);
        if (id != null) {
            json.addProperty("status_id", id.toString());
            json.addProperty("duration", duration);
        }
    }
}
