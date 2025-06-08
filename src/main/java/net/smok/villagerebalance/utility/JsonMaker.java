package net.smok.villagerebalance.utility;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public interface JsonMaker {


    void toJson(@NotNull JsonObject json);

    default JsonObject toJson() {
        JsonObject json = new JsonObject();
        toJson(json);
        return json;
    }
}
