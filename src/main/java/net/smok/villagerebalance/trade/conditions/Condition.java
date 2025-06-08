package net.smok.villagerebalance.trade.conditions;

import com.google.gson.JsonObject;
import net.minecraft.entity.passive.MerchantEntity;
import net.smok.villagerebalance.utility.JsonConvertible;
import net.smok.villagerebalance.utility.JsonMaker;
import net.smok.villagerebalance.utility.TradeRegistries;
import org.jetbrains.annotations.NotNull;

public interface Condition<T> {

    Condition<Boolean> DEFAULT_CONDITION = new Condition<>() {
        @Override
        public boolean match(MerchantEntity entity, Boolean data) {
            return true;
        }

        @Override
        public void toJson(JsonObject json, Boolean data) {
        }

        @Override
        public Boolean fromJson(JsonObject json) {
            return false;
        }
    };

    Data<Boolean> DEFAULT_DATA = new Data<>(DEFAULT_CONDITION, false);

    default Data<T> create(@NotNull T data) {
        return new Data<>(this, data);
    }

    default Data<T> create(JsonObject json) {
        return new Data<>(this, fromJson(json));
    }

    boolean match(MerchantEntity entity, T data);

    void toJson(JsonObject json, T data);

    T fromJson(JsonObject json);

    static Data<?> getFromJson(JsonObject json) {
        return JsonConvertible.getRegister(json, "condition_type", TradeRegistries.CONDITIONS, DEFAULT_CONDITION).create(json);
    }

    record Data<T> (@NotNull Condition<T> condition, @NotNull T data) implements JsonMaker {
        public boolean match(MerchantEntity entity) {
            return condition.match(entity, data);
        }

        @Override
        public void toJson(@NotNull JsonObject json) {
            json.addProperty("condition_type", TradeRegistries.CONDITIONS.getId(condition).toString());
            condition.toJson(json, data);
        }
    }


}
