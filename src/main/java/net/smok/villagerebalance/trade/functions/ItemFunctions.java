package net.smok.villagerebalance.trade.functions;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.MerchantEntity;
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
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.TradeOffer;
import net.minecraft.world.gen.structure.Structure;
import net.smok.villagerebalance.Debug;
import net.smok.villagerebalance.Values;
import net.smok.villagerebalance.trade.EnchantData;
import net.smok.villagerebalance.utility.*;
import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public final class ItemFunctions {

    public static void init() {
        Debug.log("Initialize Village Item Functions...");
    }



    public static final ItemFunction<ColorField> LEATHER_COLOR = register("leather_color", new ItemFunction<>(
            new ColorField(0, false), (itemStack, entity, colorField) -> {
                int color = colorField.useSameColor() ? foreachOffer(entity, ItemFunctions::getColor).orElse(colorField.color()) : colorField.color();

                NbtCompound nbt = itemStack.getOrCreateNbt();
                NbtCompound display = nbt.contains("display") ? nbt.getCompound("display") : new NbtCompound();
                display.putInt("color", color);
                nbt.put("display", display);
                itemStack.setNbt(nbt);
    }));


    public static final ItemFunction<EnchantData> ENCHANT = register("enchantment", new ItemFunction<>(
            new EnchantData(false, false, 0, List.of(Enchantments.UNBREAKING)),
            (itemStack, entity, enchantData) -> {
                Enchantment enchantment = enchantData.useSameEnchant() ? foreachOffer(entity, enchantData::getSame).orElse(enchantData.getRandom(entity.getRandom())) : enchantData.getRandom(entity.getRandom());
                int level = enchantData.getLevel(enchantment, entity);

                if (itemStack.getItem() instanceof EnchantedBookItem) {
                    NbtCompound nbt = itemStack.getOrCreateNbt();
                    if (!nbt.contains("StoredEnchantments", NbtElement.LIST_TYPE)) {
                        nbt.put("StoredEnchantments", new NbtList());
                    }

                    NbtList nbtList = nbt.getList("StoredEnchantments", NbtElement.COMPOUND_TYPE);
                    nbtList.add(EnchantmentHelper.createNbt(EnchantmentHelper.getEnchantmentId(enchantment), (byte) level));
                } else if (enchantment.isAcceptableItem(itemStack)) {
                    itemStack.addEnchantment(enchantment, level);
                }

            }));

    public static final ItemFunction<StatusEffectField> EFFECT_ITEM = register("effect_item", new ItemFunction<>(
            new StatusEffectField(StatusEffects.ABSORPTION, 1), (itemStack, entity, statusEffectField) -> {
                if (itemStack.getItem() instanceof SuspiciousStewItem) {
                    SuspiciousStewItem.addEffectToStew(itemStack, statusEffectField.statusEffect(), statusEffectField.duration());
                }
    }));


    public static final ItemFunction<MapData> FILL_MAP = register("fill_map", new ItemFunction<>(
            MapData.VILLAGE, (itemStack, entity, mapData) -> {
                ServerWorld serverWorld = (ServerWorld) entity.getWorld();
                BlockPos blockPos = serverWorld.locateStructure(mapData.structure(), entity.getBlockPos(), 100, true);

                if (blockPos != null) {
                    ItemStack filledMap = FilledMapItem.createMap(serverWorld, blockPos.getX(), blockPos.getZ(), (byte) 2, true, true);
                    NbtCompound filledMapId = filledMap.getOrCreateNbt();
                    int map = filledMapId.getInt("map");
                    NbtCompound nbt = itemStack.getOrCreateNbt();
                    nbt.putInt("map", map);
                    itemStack.setNbt(nbt);

                    FilledMapItem.fillExplorationMap(serverWorld, itemStack);
                    MapState.addDecorationsNbt(itemStack, blockPos, "+", mapData.icon());
                    itemStack.setCustomName(Text.translatable(mapData.nameKey()));
                }
    }));

    public static final ItemFunction<RegisteredField<Potion>> POTION = register("potion", new ItemFunction<>(new RegisteredField<>(Registries.POTION, Potions.EMPTY),
            (itemStack, entity, potionRegisteredField) -> {
                NbtCompound nbt = itemStack.getOrCreateNbt();
                nbt.putString("Potion", Registries.POTION.getId(potionRegisteredField.value()).toString());
            }));


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


    public static ItemFunction.Data<EnchantData> ofProgression(Enchantment... enchantments) {
        return new ItemFunction.Data<>(ENCHANT, EnchantData.of(true, true, 0, enchantments));
    }

    public static ItemFunction.Data<EnchantData> ofSingle(Enchantment... enchantments) {
        return new ItemFunction.Data<>(ENCHANT, EnchantData.of(false, false, 0, enchantments));
    }

    public static ItemFunction.Data<EnchantData> ofProgression(int fixedLevel, Enchantment... enchantments) {
        return new ItemFunction.Data<>(ENCHANT, EnchantData.of(true, false, fixedLevel, enchantments));
    }

    public static ItemFunction.Data<EnchantData> ofSingle(int fixedLevel, Enchantment... enchantments) {
        return new ItemFunction.Data<>(ENCHANT, EnchantData.of(false, false, fixedLevel, enchantments));
    }

    @Contract("_, _ -> new")
    public static <T extends JsonConvertible<T>> ItemFunction.Data<T> of(ItemFunction<T> function, T data) {
        return new ItemFunction.Data<>(function, data);
    }

    public static <V extends ItemFunction<T>, T extends JsonConvertible<T>> V register(String name, V function) {
        return Registry.register(TradeRegistries.ITEM_FUNCTIONS, new Identifier(Values.MOD_ID, name), function);
    }

    public static ItemFunction.Data<?> of(StatusEffect effect, int duration) {
        return of(EFFECT_ITEM, new StatusEffectField(effect, duration));
    }

    public static ItemFunction.Data<?> of(TagKey<Structure> structure, String nameKey, MapIcon.Type type) {
        return of(FILL_MAP, new MapData(structure, nameKey, type));
    }

    public static ItemFunction.Data<?> of(MapData map) {
        return of(FILL_MAP, map);
    }

    public static ItemFunction.Data<?> of(Potion potion) {
        return of(POTION, new RegisteredField<>(Registries.POTION, potion));
    }

    private static Optional<Integer> getColor(ItemStack itemStack1) {
        NbtCompound display = itemStack1.getOrCreateSubNbt("display");
        if (display.contains("color", NbtElement.INT_TYPE)) {
            return Optional.of(display.getInt("color"));
        }
        return Optional.empty();
    }
}
