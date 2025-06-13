package net.smok.villagerebalance.utility;

import com.google.gson.JsonObject;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public record RegisteredField<T>(Registry<T> registry, T value) implements JsonConvertible<RegisteredField<T>> {
    @Override
    public RegisteredField<T> childFromJson(@NotNull JsonObject json) {
        T value = JsonConvertible.getRegister(json, "value", registry, this.value);
        return new RegisteredField<>(registry, value);
    }

    @Override
    public void toJson(@NotNull JsonObject json) {
        Identifier id = registry.getId(value);
        if (id != null) json.addProperty("value", value.toString());
    }
}
