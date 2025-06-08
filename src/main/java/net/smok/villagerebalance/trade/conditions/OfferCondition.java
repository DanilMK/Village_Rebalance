package net.smok.villagerebalance.trade.conditions;

import com.google.gson.JsonObject;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonHelper;

public interface OfferCondition<T> extends Condition<OfferCondition.Searchable<T>> {

    @Override
    default boolean match(MerchantEntity entity, Searchable<T> searchableData) {
        return entity.getOffers().stream().anyMatch(tradeOffer -> switch (searchableData.searchType()) {
            case BUY -> match(tradeOffer.getOriginalFirstBuyItem(), searchableData.data) || match(tradeOffer.getSecondBuyItem(), searchableData.data);
            case SELL -> match(tradeOffer.getSellItem(), searchableData.data);
        });
    }

    @Override
    default void toJson(JsonObject json, Searchable<T> data) {
        json.addProperty("search_type", data.searchType.name().toLowerCase());
        toJsonLocal(json, data.data);
    }

    @Override
    default Searchable<T> fromJson(JsonObject json) {
        SearchType searchType = SearchType.valueOf(JsonHelper.getString(json, "search_type", "sell").toUpperCase());
        T data = fromJsonLocal(json);
        return new Searchable<>(searchType, data);
    }

    void toJsonLocal(JsonObject json, T data);

    T fromJsonLocal(JsonObject json);

    boolean match(ItemStack itemStack, T data);

    enum SearchType {
        BUY, SELL
    }

    record Searchable<T>(SearchType searchType, T data) {}
}
