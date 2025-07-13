package net.smok.villagerebalance.trade.conditions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.smok.villagerebalance.Debug;
import net.smok.villagerebalance.Values;
import net.smok.villagerebalance.utility.JsonConvertible;
import net.smok.villagerebalance.trade.fields.FieldVillagerData;
import net.smok.villagerebalance.utility.TradeRegistries;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public final class Conditions {

    public static void init() {
        Debug.log("Initialize Villager Offer Conditions...");
    }





    public static final Condition<FieldVillagerData> VILLAGER_DATA = register("villager_data", new Condition<>() {

        @Override
        public boolean match(MerchantEntity entity, FieldVillagerData data) {
            return entity instanceof VillagerEntity villager && data.match(villager.getVillagerData());
        }


        @Override
        public void toJson(JsonObject json, FieldVillagerData data) {
            data.toJson(json);
        }

        @Override
        public FieldVillagerData fromJson(JsonObject json) {
            return FieldVillagerData.EMPTY.childFromJson(json);
        }
    });

    public static final OfferCondition<Item> ITEM_OFFER_CONDITION = register("offers_contains_item", new OfferCondition<>() {
        @Override
        public boolean match(ItemStack itemStack, Item data) {
            return itemStack.isOf(data);
        }

        @Override
        public void toJsonLocal(JsonObject json, Item data) {
            json.addProperty("item", Registries.ITEM.getId(data).toString());
        }

        @Override
        public Item fromJsonLocal(JsonObject json) {
            return JsonConvertible.getRegister(json, "item", Registries.ITEM, Items.AIR);
        }
    });

    public static final OfferCondition<TagKey<Item>> TAG_KEY_OFFERS_CONDITION = register("offers_contains_tag", new OfferCondition<>() {
        @Override
        public void toJsonLocal(JsonObject json, TagKey<Item> data) {
            json.addProperty("tag", data.id().toString());
        }

        @Override
        public TagKey<Item> fromJsonLocal(JsonObject json) {
            String KEY_REF = "tag";
            return json.has(KEY_REF) && json.get(KEY_REF).isJsonPrimitive() && json.getAsJsonPrimitive(KEY_REF).isString() ?
                    TagKey.of(Registries.ITEM.getKey(), new Identifier(json.getAsJsonPrimitive(KEY_REF).getAsString())) : ItemTags.ARROWS;
        }

        @Override
        public boolean match(ItemStack itemStack, TagKey<Item> data) {
            return itemStack.isIn(data);
        }
    });
    
    public static final OfferCondition<Integer> OFFER_COLORED_CONDITION = register("offers_are_colored", new OfferCondition<Integer>() {
        @Override
        public void toJsonLocal(JsonObject json, Integer data) {
            json.addProperty("color", data);
        }

        @Override
        public Integer fromJsonLocal(JsonObject json) {
            return JsonHelper.getInt(json, "color", 0);
        }

        @Override
        public boolean match(ItemStack itemStack, Integer data) {
            NbtCompound display = itemStack.getOrCreateSubNbt("display");
            return display.contains("color") && display.getInt("color") == data;
        }
    });

    public static final Condition<Condition.Data<?>> NOT = register("not", new Condition<Condition.Data<?>>() {
        @Override
        public boolean match(MerchantEntity entity, Data<?> data) {
            return !data.match(entity);
        }

        @Override
        public void toJson(JsonObject json, Data<?> data) {
            json.add("condition", data.toJson());
        }

        @Override
        public Data<?> fromJson(JsonObject json) {
            if (json.has("condition") && json.get("condition").isJsonObject()) {
                return Condition.getFromJson(json.get("condition").getAsJsonObject());
            } else throw new JsonParseException("Not condition must 'condition' object");
        }
    });

    public static final Condition<Condition.Data<?>[]> OR = register("or", new Condition<Condition.Data<?>[]>() {
        @Override
        public boolean match(MerchantEntity entity, Data<?>[] data) {
            return Arrays.stream(data).anyMatch(data1 -> data1.match(entity));
        }

        @Override
        public void toJson(JsonObject json, Data<?>[] data) {
            convertArray(json, data);
        }

        @Override
        public Data<?>[] fromJson(JsonObject json) {
            return convertArray(json);
        }
    });

    public static final Condition<Condition.Data<?>[]> AND = register("and", new Condition<Condition.Data<?>[]>() {
        @Override
        public boolean match(MerchantEntity entity, Data<?>[] data) {
            return Arrays.stream(data).allMatch(data1 -> data1.match(entity));
        }

        @Override
        public void toJson(JsonObject json, Data<?>[] data) {
            convertArray(json, data);
        }

        @Override
        public Data<?>[] fromJson(JsonObject json) {
            return convertArray(json);
        }
    });


    public static final Condition<Boolean> WANDERING_TRADER_CONDITION = register("merchant_is_wandering_trader", new Condition<Boolean>() {

        @Override
        public boolean match(MerchantEntity entity, Boolean data) {
            return entity instanceof WanderingTraderEntity;
        }

        @Override
        public void toJson(JsonObject json, Boolean data) {

        }

        @Override
        public Boolean fromJson(JsonObject json) {
            return true;
        }
    });

    public static final Condition.Data<?> WANDERING_TRADER = of(WANDERING_TRADER_CONDITION, true);

    private static void convertArray(JsonObject json, Condition.Data<?>[] data) {
        JsonArray array = new JsonArray(data.length);
        for (Condition.Data<?> entry : data) array.add(entry.toJson());
        json.add("conditions", array);
    }

    private static Condition.Data<?> @NotNull [] convertArray(JsonObject json) {
        if (json.has("conditions") && json.get("conditions").isJsonArray()) {
            JsonArray jsonArray = json.getAsJsonArray("conditions");
            Condition.Data<?>[] dataArray = new Condition.Data[jsonArray.size()];

            for (int i = 0; i < dataArray.length; i++)
                dataArray[i] = Condition.getFromJson(jsonArray.get(i).getAsJsonObject());
            return dataArray;
        } else throw new JsonParseException("This condition must have array of other conditions");
    }


    private static <V extends Condition<T>, T> V register(String name, V condition) {
        return Registry.register(TradeRegistries.CONDITIONS, new Identifier(Values.MOD_ID, name), condition);
    }

    @Contract("_, _ -> new")
    public static <T> Condition.Data<T> of(@NotNull Condition<T> condition, T data) {
        return new Condition.Data<>(condition, data);
    }

    public static Condition.Data<FieldVillagerData> of(FieldVillagerData data) {
        return of(VILLAGER_DATA, data);
    }

    @Contract("_, _ -> new")
    public static <T> Condition.Data<OfferCondition.Searchable<T>> ofSell(OfferCondition<T> condition, T data) {
        return new Condition.Data<>(condition, new OfferCondition.Searchable<>(OfferCondition.SearchType.SELL, data));
    }

    @Contract("_, _ -> new")
    public static <T> Condition.Data<OfferCondition.Searchable<T>> ofBuy(OfferCondition<T> condition, T data) {
        return new Condition.Data<>(condition, new OfferCondition.Searchable<>(OfferCondition.SearchType.BUY, data));
    }

    public static Condition.Data<OfferCondition.Searchable<Integer>> ofBuy(int color) {
        return ofBuy(OFFER_COLORED_CONDITION, color);
    }

    public static Condition.Data<OfferCondition.Searchable<Integer>> ofSell(int color) {
        return ofSell(OFFER_COLORED_CONDITION, color);
    }

    public static Condition.Data<OfferCondition.Searchable<Item>> ofBuy(Item item) {
        return ofBuy(ITEM_OFFER_CONDITION, item);
    }

    public static Condition.Data<OfferCondition.Searchable<Item>> ofSell(Item item) {
        return ofSell(ITEM_OFFER_CONDITION, item);
    }

    public static Condition.Data<OfferCondition.Searchable<TagKey<Item>>> ofBuy(TagKey<Item> item) {
        return ofBuy(TAG_KEY_OFFERS_CONDITION, item);
    }

    public static Condition.Data<OfferCondition.Searchable<TagKey<Item>>> ofSell(TagKey<Item> item) {
        return ofSell(TAG_KEY_OFFERS_CONDITION, item);
    }

    public static Condition.Data<Condition.Data<?>> not(Condition.Data<?> other) {
        return of(NOT, other);
    }

    public static Condition.Data<Condition.Data<?>[]> and(Condition.Data<?>... others) {
        return of(AND, others);
    }

    public static Condition.Data<Condition.Data<?>[]> or(Condition.Data<?>... others) {
        return of(OR, others);
    }

}
