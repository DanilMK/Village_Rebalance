package net.smok.villagerebalance.trade.fields;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.util.JsonHelper;
import net.smok.villagerebalance.utility.JsonConvertible;
import org.jetbrains.annotations.NotNull;

public record FieldInt(int value) implements JsonConvertible<FieldInt> {
    @Override
    public @NotNull FieldInt childFromJson(@NotNull JsonObject json) throws JsonSyntaxException {
        int value = JsonHelper.getInt(json, "value", this.value);
        return new FieldInt(value);
    }

    @Override
    public void toJson(@NotNull JsonObject json) {
        json.addProperty("value", value);
    }
}
