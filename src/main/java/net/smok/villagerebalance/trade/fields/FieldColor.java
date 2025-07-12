package net.smok.villagerebalance.trade.fields;

import com.google.gson.JsonObject;
import net.minecraft.util.JsonHelper;
import net.smok.villagerebalance.utility.JsonConvertible;
import org.jetbrains.annotations.NotNull;

public record FieldColor(int color, boolean useSameColor) implements JsonConvertible<FieldColor> {
    @Override
    public FieldColor childFromJson(@NotNull JsonObject json) {
        boolean useSameColor = JsonHelper.getBoolean(json, "use_same_color", this.useSameColor);
        int color = JsonHelper.getInt(json, "color", this.color);
        return new FieldColor(color, useSameColor);
    }

    @Override
    public void toJson(@NotNull JsonObject json) {
        json.addProperty("color", this.color);
        json.addProperty("use_same_color", this.useSameColor);
    }
}
