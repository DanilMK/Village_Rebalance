package net.smok.villagerebalance.utility;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.util.JsonHelper;
import org.jetbrains.annotations.NotNull;

public record FieldString(String value) implements JsonConvertible<FieldString> {

    @Override
    public FieldString childFromJson(@NotNull JsonObject json) throws JsonSyntaxException {
        String value = JsonHelper.getString(json, "value", this.value);
        return new FieldString(value);
    }

    @Override
    public void toJson(@NotNull JsonObject json) {
        json.addProperty("value", value);

    }
}
