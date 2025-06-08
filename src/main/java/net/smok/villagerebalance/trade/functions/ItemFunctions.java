package net.smok.villagerebalance.trade.functions;

import com.google.gson.JsonObject;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Pair;
import net.minecraft.village.TradeOffer;
import net.smok.villagerebalance.Debug;
import net.smok.villagerebalance.Values;
import net.smok.villagerebalance.trade.EnchantData;
import net.smok.villagerebalance.utility.JsonConvertible;
import net.smok.villagerebalance.utility.TradeRegistries;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

public final class ItemFunctions {

    public static void init() {
        Debug.log("Initialize Village Item Functions...");
    }

    public static final ItemFunction<Pair<Integer, Boolean>> LEATHER_COLOR = register("leather_color", new ItemFunction<>() {
        private Optional<Integer> getColor(ItemStack itemStack1) {
            NbtCompound display = itemStack1.getOrCreateSubNbt("display");
            if (display.contains("color", NbtElement.INT_TYPE)) {
                return Optional.of(display.getInt("color"));
            }
            return Optional.empty();
        }

        @Override
        public void accept(ItemStack itemStack, MerchantEntity entity, Pair<Integer, Boolean> data) {
            int color = data.getRight() ? foreachOffer(entity, this::getColor).orElse(data.getLeft()) : data.getLeft();

            NbtCompound nbt = itemStack.getOrCreateNbt();
            NbtCompound display = nbt.contains("display") ? nbt.getCompound("display") : new NbtCompound();
            display.putInt("color", color);
            nbt.put("display", display);
            itemStack.setNbt(nbt);
        }

        @Override
        public void toJson(JsonObject json, Pair<Integer, Boolean>  data) {
            json.addProperty("color", data.getLeft());
            json.addProperty("use_same_color_if_possible", data.getRight());
        }

        @Override
        public Pair<Integer, Boolean>  fromJson(@NotNull JsonObject json) {
            return new Pair<>(
                    JsonHelper.getInt(json, "color", 0),
                    JsonHelper.getBoolean(json, "use_same_color_if_possible", true));
        }
    });


    public static final ItemFunction<EnchantData> ENCHANT = register("enchantment", new ItemFunction<EnchantData>() {
        @Override
        public void accept(ItemStack itemStack, MerchantEntity entity, EnchantData data) {

            Enchantment enchantment = data.useSameEnchant() ? foreachOffer(entity, ItemFunctions::getEnchant).orElse(data.enchantment()) : data.enchantment();
            int level = data.useVillagerLevel() && entity instanceof VillagerEntity villager ? villager.getVillagerData().getLevel() : data.level();

            if (itemStack.getItem() instanceof EnchantedBookItem) {
                NbtCompound nbt = itemStack.getOrCreateNbt();
                if (!nbt.contains("StoredEnchantments", NbtElement.LIST_TYPE)) {
                    nbt.put("StoredEnchantments", new NbtList());
                }

                NbtList nbtList = nbt.getList("StoredEnchantments", NbtElement.COMPOUND_TYPE);
                nbtList.add(EnchantmentHelper.createNbt(EnchantmentHelper.getEnchantmentId(enchantment), (byte)level));
            } else if (enchantment.isAcceptableItem(itemStack)) {
                itemStack.addEnchantment(enchantment, level);
            }

        }

        @Override
        public EnchantData fromJson(@NotNull JsonObject json) {
            Enchantment enchantment = JsonConvertible.getRegister(json, "enchantment", Registries.ENCHANTMENT, Enchantments.UNBREAKING);
            int level = JsonHelper.getInt(json, "level", 1);
            boolean useSameEnchant = JsonHelper.getBoolean(json, "use_same_enchantment", false);
            boolean useVillagerLevel = JsonHelper.getBoolean(json, "use_villager_level", false);
            return new EnchantData(enchantment, level, useSameEnchant, useVillagerLevel);
        }

        @Override
        public void toJson(JsonObject json, EnchantData data) {
            Identifier id = Registries.ENCHANTMENT.getId(data.enchantment());
            if (id != null) {
                json.addProperty("enchantment", id.toString());
                json.addProperty("level", data.level());
                json.addProperty("use_same_enchantment", data.useSameEnchant());
                json.addProperty("use_villager_level", data.useVillagerLevel());
            }
        }
    });


    private static <T> Optional<T> foreachOffer(MerchantEntity entity, Function<ItemStack, Optional<T>> function) {
        for (TradeOffer offer : entity.getOffers()) {
            if (offer.getSellItem().hasNbt()) {
                Optional<T> result = function.apply(offer.getSellItem());
                if (result.isPresent()) return result;
            }
            if (offer.getOriginalFirstBuyItem().hasNbt()) {
                Optional<T> result = function.apply(offer.getOriginalFirstBuyItem());
                if (result.isPresent()) return result;
            }
            if (offer.getSecondBuyItem().hasNbt()) {
                Optional<T> result = function.apply(offer.getSecondBuyItem());
                if (result.isPresent()) return result;
            }
        }
        return Optional.empty();
    }

    private static @Nullable Optional<Enchantment> getEnchant(@NotNull ItemStack itemStack) {
        NbtCompound nbt = itemStack.getNbt();
        if (nbt != null) {
            NbtList storedEnchantments = itemStack.getItem() instanceof EnchantedBookItem ?
                    nbt.getList("StoredEnchantments", NbtElement.COMPOUND_TYPE) :
                    nbt.getList("Enchantments", NbtElement.COMPOUND_TYPE);

            if (storedEnchantments != null) for (int i = 0; i < storedEnchantments.size(); i++) {

                NbtCompound enchantCompound = storedEnchantments.getCompound(i);
                if (enchantCompound != null && enchantCompound.getString("id") != null) {
                    Enchantment enchantment = Registries.ENCHANTMENT.get(new Identifier(enchantCompound.getString("id")));
                    if (enchantment != null && enchantment.getMaxLevel() > 1) return Optional.of(enchantment);
                }
            }
        }
        return Optional.empty();
    }


    public static ItemFunction.Data<EnchantData> ofSingle(Enchantment enchantment) {
        return of(ENCHANT, new EnchantData(enchantment, 1, true,false));
    }

    public static ItemFunction.Data<EnchantData> ofProgression(Enchantment enchantment) {
        return of(ENCHANT, new EnchantData(enchantment, 1, true, true));
    }

    public static ItemFunction.Data<EnchantData> of(Enchantment enchantment, int level, boolean useSameEnchantment, boolean useVillagerLevel) {
        return of(ENCHANT, new EnchantData(enchantment, level, useSameEnchantment, useVillagerLevel));
    }

    @Contract("_, _ -> new")
    public static <T> ItemFunction.Data<T> of(ItemFunction<T> function, T data) {
        return new ItemFunction.Data<>(function, data);
    }

    public static <V extends ItemFunction<T>, T> V register(String name, V function) {
        return Registry.register(TradeRegistries.ITEM_FUNCTIONS, new Identifier(Values.MOD_ID, name), function);
    }
}
