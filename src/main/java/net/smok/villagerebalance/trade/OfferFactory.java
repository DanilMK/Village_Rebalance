package net.smok.villagerebalance.trade;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.smok.villagerebalance.trade.conditions.Condition;
import net.smok.villagerebalance.utility.JsonConvertible;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public record OfferFactory(ItemContainer[] firstBuy, ItemContainer[] secondBuy, ItemContainer[] sell, int maxUses,
                           boolean rewardPlayer, float priceMultiplier, int experience, Condition.Data<?> condition)
        implements TradeOffers.Factory, JsonConvertible<OfferFactory> {

    private static final String KEY_FIRST_BUY = "first_buy";
    private static final String KEY_SECOND_BUY = "second_buy";
    private static final String KEY_SELL = "sell";
    private static final String KEY_ITEM = "item";
    private static final String KEY_MAX_USES = "max_uses";
    private static final String KEY_REWARD_PLAYER = "rewarding_player_experience";
    private static final String KEY_PRICE_MULTIPLIER = "price_multiplier";
    private static final String KEY_EXPERIENCE = "merchant_experience";
    private static final String KEY_CONDITION = "condition";


    public static final OfferFactory DEFAULT_FACTORY =
            new OfferFactory(ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY,
                    12, true, 0.05f, 1, Condition.DEFAULT_DATA);

    public OfferFactory(ItemStack firstBuy, ItemStack secondBuy, ItemStack sell, int maxUses, boolean rewardPlayer,
                        float priceMultiplier, int experience, Condition.Data<?> condition) {
        this(new ItemContainer[]{ItemContainer.of(firstBuy)},
                new ItemContainer[]{ItemContainer.of(secondBuy)},
                new ItemContainer[]{ItemContainer.of(sell)},
                maxUses, rewardPlayer, priceMultiplier, experience, condition);
    }

    public @NotNull OfferFactory childFromJson(@NotNull JsonObject json) {

        ItemContainer @NotNull [] firstBuy = ItemContainer.fromJson(json, KEY_FIRST_BUY);
        ItemContainer @NotNull [] secondBuy = ItemContainer.fromJson(json, KEY_SECOND_BUY);
        ItemContainer @NotNull [] sell = ItemContainer.fromJson(json, KEY_SELL);

        int maxUses = Math.max(JsonHelper.getInt(json, KEY_MAX_USES, this.maxUses), 1);
        boolean rewardPlayer = JsonHelper.getBoolean(json, KEY_REWARD_PLAYER, this.rewardPlayer);
        float priceMultiplier = MathHelper.clamp(JsonHelper.getFloat(json, KEY_PRICE_MULTIPLIER, this.priceMultiplier), 0, 1);
        int experience = MathHelper.clamp(JsonHelper.getInt(json, KEY_EXPERIENCE, this.experience), 0, 256);
        Condition.Data<?> offerCondition = Condition.getFromJson(json.getAsJsonObject(KEY_CONDITION));

        return new OfferFactory(firstBuy, secondBuy, sell, maxUses, rewardPlayer, priceMultiplier, experience, offerCondition);
    }



    public void toJson(@NotNull JsonObject result) {
        result.addProperty(KEY_EXPERIENCE, this.experience());
        result.addProperty(KEY_MAX_USES, this.maxUses());
        result.addProperty(KEY_PRICE_MULTIPLIER, this.priceMultiplier());
        result.addProperty(KEY_REWARD_PLAYER, this.rewardPlayer());
        ItemContainer.toJson(result, KEY_FIRST_BUY, firstBuy);
        ItemContainer.toJson(result, KEY_SECOND_BUY, secondBuy);
        ItemContainer.toJson(result, KEY_SELL, sell);
        result.add(KEY_CONDITION, condition.toJson());
    }

    private static @NotNull ItemStack itemFromJson(@NotNull JsonObject parent, String key) {
        if (!parent.has(key)) return ItemStack.EMPTY;
        JsonElement element = parent.get(key);

        if (element.isJsonPrimitive()) return new ItemStack(JsonHelper.asItem(element, key));
        if (element.isJsonObject()) {
            JsonObject jsonObject = element.getAsJsonObject();
            if (jsonObject.has(KEY_ITEM) && jsonObject.get(KEY_ITEM).isJsonObject())
                return itemFromJson(jsonObject.getAsJsonObject(KEY_ITEM));
            else return itemFromJson(jsonObject);
        }

        throw new JsonSyntaxException("Expected " + key + " to be an Item, Item Stack or Object with Item or Item Stack. " +
                "Was " + JsonHelper.getType(element));
    }


    @Contract("_ -> new")
    private static @NotNull ItemStack itemFromJson(@NotNull JsonObject json) {

        Item id = JsonHelper.getItem(json, KEY_ITEM, Items.AIR);
        id = JsonHelper.getItem(json, "id", id);
        if (id == Items.AIR) return ItemStack.EMPTY;

        int amount = JsonHelper.getInt(json, "count", 1);
        amount = JsonHelper.getInt(json, "amount", amount);
        return new ItemStack(id, amount);
    }


    @Nullable
    private static JsonObject itemToJson(@NotNull ItemStack itemStack) {
        if (itemStack.isEmpty()) return null;
        JsonObject result = new JsonObject();
        result.addProperty("id", Registries.ITEM.getId(itemStack.getItem()).toString());


        if (itemStack.getCount() != 1) result.addProperty("count", itemStack.getCount());
        return result;
    }


    @Override
    public @NotNull TradeOffer create(Entity entity, Random random) {
        return new TradeOffer(
                randomContainer(firstBuy, (MerchantEntity) entity),
                randomContainer(secondBuy, (MerchantEntity) entity),
                randomContainer(sell, (MerchantEntity) entity), maxUses, experience, priceMultiplier);
    }

    private static ItemStack randomContainer(ItemContainer[] containers, MerchantEntity entity) {
        if (containers.length == 0) return ItemStack.EMPTY;
        List<ItemContainer> list = Arrays.stream(containers)
                .filter(itemContainer -> itemContainer.condition().match(entity)).toList();
        if (list.isEmpty())
            return containers[entity.getRandom().nextInt(containers.length)].apply(entity);
        return list.get(entity.getRandom().nextInt(list.size())).apply(entity);
    }

    @Override
    public String toString() {
        return "BaseOfferFactory{" +
                " firstBuy={" + Arrays.toString(firstBuy) +
                "}, secondBuy={" + Arrays.toString(secondBuy) +
                "}, sell={" + Arrays.toString(sell) +
                "}, maxUses=" + maxUses +
                ", rewardPlayer=" + rewardPlayer +
                ", priceMultiplier=" + priceMultiplier +
                ", experience=" + experience +
                ", condition=" + condition +
                '}';
    }

    public boolean match(MerchantEntity villager) {
        return condition.match(villager);
    }

    public boolean isEmpty() {
        return sell.length == 0 || (firstBuy.length == 0 && secondBuy.length == 0);
    }
}
