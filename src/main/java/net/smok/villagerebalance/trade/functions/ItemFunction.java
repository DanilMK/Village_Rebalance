package net.smok.villagerebalance.trade.functions;

import com.google.gson.JsonObject;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.smok.villagerebalance.Debug;
import net.smok.villagerebalance.utility.JsonConvertible;
import net.smok.villagerebalance.utility.JsonMaker;
import net.smok.villagerebalance.utility.TradeRegistries;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record ItemFunction<T extends JsonConvertible<T>> (T defaultField, TriConsumer<ItemStack, MerchantEntity, T> consumer) {

    public Data<T> create(JsonObject json) {
        return ItemFunctions.of(this, fromJson(json));
    }

    public Data<T> create(T data) {
        return new Data<>(this, data);
    }

    public void accept(ItemStack itemStack, MerchantEntity entity, T data) {
        consumer.accept(itemStack, entity, data);
    }

    public T fromJson(@NotNull JsonObject json) {
        return defaultField.childFromJson(json);
    }


    @Nullable
    public static Data<?> getFromJson(@NotNull JsonObject json) {
        ItemFunction<?> jsonFactory = JsonConvertible.getRegister(json, "function_type", TradeRegistries.ITEM_FUNCTIONS, null);
        return jsonFactory == null ? null : jsonFactory.create(json);
    }


    public record Data<T extends JsonConvertible<T>> (@NotNull ItemFunction<T> function, @NotNull T data) implements JsonMaker {

        public void accept(ItemStack itemStack, MerchantEntity entity) {
            function.accept(itemStack, entity, data);
        }

        @Override
        public void toJson(@NotNull JsonObject json) {
            Identifier id = TradeRegistries.ITEM_FUNCTIONS.getId(function);
            if (id != null) {
                json.addProperty("function_type", id.toString());
                data.toJson(json);
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
