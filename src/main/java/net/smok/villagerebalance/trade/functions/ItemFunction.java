package net.smok.villagerebalance.trade.functions;

import com.google.gson.JsonObject;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.smok.villagerebalance.Debug;
import net.smok.villagerebalance.utility.JsonConvertible;
import net.smok.villagerebalance.utility.JsonMaker;
import net.smok.villagerebalance.utility.TradeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ItemFunction<T> {

    default Data<T> create(JsonObject json) {
        return ItemFunctions.of(this, fromJson(json));
    }

    default Data<T> create(T data) {
        return new Data<>(this, data);
    }

    void accept(ItemStack itemStack, MerchantEntity entity, T data);

    T fromJson(@NotNull JsonObject json);

    void toJson(JsonObject json, T data);

    @Nullable
    static Data<?> getFromJson(@NotNull JsonObject json) {
        ItemFunction<?> jsonFactory = JsonConvertible.getRegister(json, "function_type", TradeRegistries.ITEM_FUNCTIONS, null);
        return jsonFactory == null ? null : jsonFactory.create(json);
    }


    record Data<T> (@NotNull ItemFunction<T> function, @NotNull T data) implements JsonMaker {

        public void accept(ItemStack itemStack, MerchantEntity entity) {
            function.accept(itemStack, entity, data);
        }

        @Override
        public void toJson(@NotNull JsonObject json) {
            Identifier id = TradeRegistries.ITEM_FUNCTIONS.getId(function);
            if (id != null) {
                json.addProperty("function_type", id.toString());
                function.toJson(json, data);
            } else Debug.err("Unknown function");
        }

        @Override
        public String toString() {
            return "Data{" +
                    "function=" + TradeRegistries.ITEM_FUNCTIONS.getId(function) +
                    ", data=" + data +
                    '}';
        }
    }

}
