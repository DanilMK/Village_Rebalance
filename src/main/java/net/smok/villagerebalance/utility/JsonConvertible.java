package net.smok.villagerebalance.utility;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface JsonConvertible<T> extends JsonMaker {

    static Optional<String> getFromJsonString(@NotNull JsonObject json, String key) {
        if (json.has(key) && json.isJsonPrimitive() && json.getAsJsonPrimitive(key).isString())
            return Optional.of(json.getAsJsonPrimitive(key).getAsString());
        return Optional.empty();
    }

    static <T> T getRegister(@NotNull JsonObject json, String key, Registry<T> registry, T defaultValue) {
        if (json.has(key) && json.get(key).isJsonPrimitive() && json.getAsJsonPrimitive(key).isString()) {
            Identifier id = new Identifier(json.getAsJsonPrimitive(key).getAsString());
            Optional<T> entry = registry.getOrEmpty(id);
            if (entry.isPresent()) return entry.get();
            else throw new JsonSyntaxException("Unknown id " + id + " of type " + registry.getKey().toString());
        }
        return defaultValue;
    }

    static Identifier getId(@NotNull JsonObject json, String key, Identifier defaultId) {
        if (json.has(key) && json.get(key).isJsonPrimitive() && json.getAsJsonPrimitive(key).isString())
            return new Identifier(json.getAsJsonPrimitive(key).getAsString());
        return defaultId;
    }

    T childFromJson(@NotNull JsonObject json);

}
