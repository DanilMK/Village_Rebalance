package net.smok.villagerebalance.trade.functions;

import com.google.gson.JsonObject;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.item.map.MapIcon;
import net.minecraft.item.map.MapState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.StructureTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.TradeOffer;
import net.minecraft.world.gen.structure.Structure;
import net.smok.villagerebalance.Debug;
import net.smok.villagerebalance.Values;
import net.smok.villagerebalance.trade.EnchantData;
import net.smok.villagerebalance.utility.JsonConvertible;
import net.smok.villagerebalance.utility.MapData;
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
            int level = data.useVillagerLevel() && entity instanceof VillagerEntity villager ? villager.getVillagerData().getLevel() : data.minLevel(); //todo add level

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
            return new EnchantData(Enchantments.UNBREAKING, 1, 1, false, false).childFromJson(json);
        }

        @Override
        public void toJson(JsonObject json, EnchantData data) {
            data.toJson(json);
        }
    });

    public static final ItemFunction<Pair<StatusEffect, Integer>> EFFECT_ITEM = register("effect_item", new ItemFunction<Pair<StatusEffect, Integer>>() {
        @Override
        public void accept(ItemStack itemStack, MerchantEntity entity, Pair<StatusEffect, Integer> data) {
            if (itemStack.getItem() instanceof SuspiciousStewItem) {
                SuspiciousStewItem.addEffectToStew(itemStack, data.getLeft(), data.getRight());
            }
        }

        @Override
        public Pair<StatusEffect, Integer> fromJson(@NotNull JsonObject json) {
            StatusEffect statusId = JsonConvertible.getRegister(json, "status_id", Registries.STATUS_EFFECT);
            int level = JsonHelper.getInt(json, "duration", 0);
            return new Pair<>(statusId, level);
        }

        @Override
        public void toJson(JsonObject json, Pair<StatusEffect, Integer> data) {
            json.addProperty("status_id", Registries.STATUS_EFFECT.getId(data.getLeft()).toString());
            json.addProperty("duration", data.getRight());
        }
    });


    public static final ItemFunction<MapData> FILL_MAP = register("fill_map", new ItemFunction<MapData>() {
        @Override
        public void accept(ItemStack itemStack, MerchantEntity entity, MapData data) {
            ServerWorld serverWorld = (ServerWorld)entity.getWorld();
            BlockPos blockPos = serverWorld.locateStructure(data.structure(), entity.getBlockPos(), 100, true);

            if (blockPos != null) {
                ItemStack filledMap = FilledMapItem.createMap(serverWorld, blockPos.getX(), blockPos.getZ(), (byte) 2, true, true);
                NbtCompound filledMapId = filledMap.getOrCreateNbt();
                int map = filledMapId.getInt("map");
                NbtCompound nbt = itemStack.getOrCreateNbt();
                nbt.putInt("map", map);
                itemStack.setNbt(nbt);

                FilledMapItem.fillExplorationMap(serverWorld, itemStack);
                MapState.addDecorationsNbt(itemStack, blockPos, "+", data.icon());
                itemStack.setCustomName(Text.translatable(data.nameKey()));
            }
        }

        @Override
        public MapData fromJson(@NotNull JsonObject json) {
            return new MapData(StructureTags.ON_OCEAN_EXPLORER_MAPS, "filled_map.monument", MapIcon.Type.MONUMENT)
                    .childFromJson(json);
        }

        @Override
        public void toJson(JsonObject json, MapData data) {
            data.toJson(json);
        }
    });

    public static final ItemFunction<Potion> POTION = register("potion", new ItemFunction<Potion>() {
        @Override
        public void accept(ItemStack itemStack, MerchantEntity entity, Potion data) {
            NbtCompound nbt = itemStack.getOrCreateNbt();
            nbt.putString("Potion", Registries.POTION.getId(data).toString());
        }

        @Override
        public Potion fromJson(@NotNull JsonObject json) {
            return JsonConvertible.getRegister(json, "potion", Registries.POTION);
        }

        @Override
        public void toJson(JsonObject json, Potion data) {
            json.addProperty("potion", Registries.POTION.getId(data).toString());
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
        return of(ENCHANT, new EnchantData(enchantment, 1, 1,true,false));
    }

    public static ItemFunction.Data<EnchantData> ofProgression(Enchantment enchantment) {
        return of(ENCHANT, new EnchantData(enchantment, 1, 1,true, true));
    }

    public static ItemFunction.Data<EnchantData> of(Enchantment enchantment, int minLevel, int maxLevel, boolean useSameEnchantment, boolean useVillagerLevel) {
        return of(ENCHANT, new EnchantData(enchantment, minLevel, maxLevel, useSameEnchantment, useVillagerLevel));
    }

    @Contract("_, _ -> new")
    public static <T> ItemFunction.Data<T> of(ItemFunction<T> function, T data) {
        return new ItemFunction.Data<>(function, data);
    }

    public static <V extends ItemFunction<T>, T> V register(String name, V function) {
        return Registry.register(TradeRegistries.ITEM_FUNCTIONS, new Identifier(Values.MOD_ID, name), function);
    }

    public static ItemFunction.Data<?> of(StatusEffect effect, int duration) {
        return of(EFFECT_ITEM, new Pair<>(effect, duration));
    }

    public static ItemFunction.Data<?> of(TagKey<Structure> structure, String nameKey, MapIcon.Type type) {
        return of(FILL_MAP, new MapData(structure, nameKey, type));
    }

    public static ItemFunction.Data<?> of(MapData map) {
        return of(FILL_MAP, map);
    }

    public static ItemFunction.Data<?> of(Potion potion) {
        return of(POTION, potion);
    }
}
