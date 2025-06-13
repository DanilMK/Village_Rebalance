package net.smok.villagerebalance.utility;

import com.google.gson.JsonObject;
import net.minecraft.util.JsonHelper;
import org.jetbrains.annotations.NotNull;

public record ColorField(int color, boolean useSameColor) implements JsonConvertible<ColorField> {
    @Override
    public ColorField childFromJson(@NotNull JsonObject json) {
        boolean useSameColor = JsonHelper.getBoolean(json, "use_same_color", this.useSameColor);
        int color = JsonHelper.getInt(json, "color", this.color);
        return new ColorField(color, useSameColor);
    }

    @Override
    public void toJson(@NotNull JsonObject json) {
        json.addProperty("color", this.color);
        json.addProperty("use_same_color", this.useSameColor);
    }
}
