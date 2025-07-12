package net.smok.villagerebalance.trade.fields;

import com.google.gson.JsonObject;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.smok.villagerebalance.utility.JsonConvertible;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record FieldRegistered<T>(Registry<T> registry, T value) implements JsonConvertible<FieldRegistered<T>> {
    @Override
    public FieldRegistered<T> childFromJson(@NotNull JsonObject json) {
        T value = JsonConvertible.getRegister(json, "value", registry, this.value);
        return new FieldRegistered<>(registry, value);
    }

    @Override
    public void toJson(@NotNull JsonObject json) {
        Identifier id = registry.getId(value);
        if (id != null) json.addProperty("value", id.toString());
    }

    public Optional<String> valueToString() {
        Identifier id = registry.getId(value);
        if (id == null) return Optional.empty();
        return Optional.of(id.toString());
    }
}
