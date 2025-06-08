package net.smok.villagerebalance.trade;

import com.google.gson.*;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.smok.villagerebalance.trade.conditions.Condition;
import net.smok.villagerebalance.trade.functions.ItemFunction;
import net.smok.villagerebalance.utility.JsonMaker;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record ItemContainer(@NotNull ItemStack itemStack, @NotNull Condition.Data<?> condition, @NotNull List<ItemFunction.Data<?>> functions) implements JsonMaker {

    private static final String KEY_ITEM = "item";

    public static final ItemContainer[] EMPTY = new ItemContainer[0];
    private static final List<ItemFunction.Data<?>> EMPTY_FUNCTION = List.of();
    public static final ItemContainer EMPTY_CONTAINER = new ItemContainer(ItemStack.EMPTY, Condition.DEFAULT_DATA, EMPTY_FUNCTION);


    public static ItemContainer of(@NotNull ItemStack itemStack) {
        if (itemStack.isEmpty()) return EMPTY_CONTAINER;
        return new ItemContainer(itemStack, Condition.DEFAULT_DATA, EMPTY_FUNCTION);
    }

    public static ItemContainer of(@NotNull ItemStack itemStack, ItemFunction.Data<?>... functions) {
        if (itemStack.isEmpty()) return EMPTY_CONTAINER;
        return new ItemContainer(itemStack, Condition.DEFAULT_DATA, List.of(functions));
    }

    public static ItemContainer[] of(@NotNull ItemStack itemStack, Condition.Data<?> condition, ItemFunction.Data<?>... functions) {
        if (itemStack.isEmpty()) return EMPTY;
        return new ItemContainer[] {new ItemContainer(itemStack, condition, List.of(functions))};
    }

    public static ItemContainer of(@NotNull ItemStack itemStack, Condition.Data<?> condition) {
        if (itemStack.isEmpty()) return EMPTY_CONTAINER;
        return new ItemContainer(itemStack, condition, EMPTY_FUNCTION);
    }

    public static ItemContainer @NotNull [] of(ItemStack... itemStacks) {
        return Arrays.stream(itemStacks).filter(itemStack -> !itemStack.isEmpty())
                .map(itemStack -> new ItemContainer(itemStack, Condition.DEFAULT_DATA, EMPTY_FUNCTION))
                .toArray(ItemContainer[]::new);
    }

    public static void toJson(JsonObject json, String key, ItemContainer @NotNull [] containers) {
        if (containers.length == 0) return;
        if (containers.length == 1) {
            json.add(key, containers[0].toJson());
        } else {
            JsonArray array = new JsonArray(containers.length);
            for (ItemContainer container : containers) {
                array.add(container.toJson());
            }
            json.add(key, array);
        }
    }



    @Override
    public void toJson(@NotNull JsonObject json) {
        if (condition == Condition.DEFAULT_DATA && functions.isEmpty()) {
            itemToJson(itemStack, json);
            return;
        }

        JsonObject itemJson = new JsonObject();
        itemToJson(itemStack, itemJson);
        json.add(KEY_ITEM, itemJson);

        if (condition != Condition.DEFAULT_DATA) {
            json.add("condition", condition.toJson());
        }

        if (!functions.isEmpty()) {
            JsonArray array = new JsonArray(functions().size());
            for (ItemFunction.Data<?> function : functions) {
                array.add(function.toJson());
            }
            json.add("functions", array);
        }
    }

    @Contract("_, _ -> new")
    public static ItemContainer @NotNull [] fromJson(@NotNull JsonObject json, String key) {
        if (json.has(key)) {
            JsonElement element = json.get(key);
            if (element.isJsonObject()) {
                ItemContainer result = fromJson(element.getAsJsonObject());
                if (result.equals(EMPTY_CONTAINER)) return EMPTY;
                return new ItemContainer[] {result};
            } else if (element.isJsonArray()) {
                if (element.getAsJsonArray().isEmpty()) return EMPTY;
                return fromJson(element.getAsJsonArray());
            } else if (element.isJsonPrimitive()) {
                ItemContainer result = fromJson(element.getAsJsonPrimitive());
                if (result.equals(EMPTY_CONTAINER)) return EMPTY;
                return new ItemContainer[] {result};
            }
        } return EMPTY;
    }

    public static ItemContainer @NotNull [] fromJson(@NotNull JsonArray json) {
        ItemContainer[] result = new ItemContainer[json.size()];
        for (int i = 0; i < result.length; i++) {
            JsonElement element = json.get(i);
            if (element.isJsonPrimitive()) result[i] = fromJson(element.getAsJsonPrimitive());
            else if (element.isJsonObject()) result[i] = fromJson(element.getAsJsonObject());
            else throw new JsonParseException("Array must contain objects of container or id of item");
        }
        return result;
    }

    @Contract("_ -> new")
    public static @NotNull ItemContainer fromJson(@NotNull JsonObject json) {
        if (!json.has("item") && !json.has("id")) return EMPTY_CONTAINER;

        ItemStack item;
        if (json.has("item")) {
            if (json.get("item").isJsonPrimitive()) {
                item = itemFromJson(json);
            } else if (json.get("item").isJsonObject()) {
                item = itemFromJson(json.getAsJsonObject("item"));
            } else
                throw new JsonParseException("Item Container must have field 'id' or object containing them");

        } else if (json.has("id") && json.get("id").isJsonPrimitive()) {
            item = itemFromJson(json);
        } else
            throw new JsonParseException("Item Container must have field 'id' or object containing them");

        List <ItemFunction.Data<?>> functions = new ArrayList<>();
        if (json.has("functions")) {
            JsonElement element = json.get("functions");
            if (element.isJsonObject()) {
                ItemFunction.Data<?> functionData = ItemFunction.getFromJson(element.getAsJsonObject());
                if (functionData != null) functions.add(functionData);
            } else if (element.isJsonArray()) {

                for (JsonElement jsonElement : element.getAsJsonArray()) {
                    if (jsonElement.isJsonObject()) {
                        ItemFunction.Data<?> functionData = ItemFunction.getFromJson(jsonElement.getAsJsonObject());
                        if (functionData != null) functions.add(functionData);
                    }
                }
            }
        }

        Condition.Data<?> condition = Condition.DEFAULT_DATA;
        if (json.has("condition") && json.get("condition").isJsonObject()) {
            condition = Condition.getFromJson(json.getAsJsonObject("condition"));
        }

        return new ItemContainer(item, condition, functions);
    }


    @Contract("_ -> new")
    public static @NotNull ItemContainer fromJson(@NotNull JsonPrimitive json) {
        if (json.isString()) {
            Identifier id = new Identifier(json.getAsString());
            if (Registries.ITEM.containsId(id))
                return new ItemContainer(new ItemStack(Registries.ITEM.get(id)), Condition.DEFAULT_DATA, EMPTY_FUNCTION);
            else throw new JsonParseException("Unknown item id " + id);
        }
        throw new JsonParseException("Item Container Cannot be " + json + ". Only Object, Array or String Allowed.");
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


    private static void itemToJson(@NotNull ItemStack itemStack, @NotNull JsonObject json) {
        if (itemStack.isEmpty()) return;
        json.addProperty("id", Registries.ITEM.getId(itemStack.getItem()).toString());


        if (itemStack.getCount() != 1) json.addProperty("count", itemStack.getCount());
    }

    public ItemStack apply(MerchantEntity entity) {
        ItemStack result = itemStack.copy();
        for (ItemFunction.Data<?> function : functions) function.accept(result, entity);
        return result;
    }
}
