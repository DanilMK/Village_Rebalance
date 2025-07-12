package net.smok.villagerebalance.trade.functions;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.decoration.painting.PaintingVariant;
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
import net.smok.villagerebalance.trade.fields.*;
import net.smok.villagerebalance.utility.*;
import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public final class ItemFunctions {

    public static void init() {
        Debug.log("Initialize Village Item Functions...");
    }



    public static final ItemFunction<FieldColor> LEATHER_COLOR = register("leather_color", new ItemFunction<>(
            new FieldColor(0, false), (itemStack, entity, fieldColor) -> {
                int color = fieldColor.useSameColor() ? foreachOffer(entity, ItemFunctions::getColor).orElse(fieldColor.color()) : fieldColor.color();

                NbtCompound nbt = itemStack.getOrCreateNbt();
                NbtCompound display = nbt.contains("display") ? nbt.getCompound("display") : new NbtCompound();
                display.putInt("color", color);
                nbt.put("display", display);
                itemStack.setNbt(nbt);
    }));


    public static final ItemFunction<FieldEnchantment> ENCHANT = register("enchantment", new ItemFunction<>(
            new FieldEnchantment(false, false, 0, List.of(Enchantments.UNBREAKING)),
            (itemStack, entity, fieldEnchantment) -> {
                Enchantment enchantment = fieldEnchantment.useSameEnchant() ? foreachOffer(entity, fieldEnchantment::getSame).orElse(fieldEnchantment.getRandom(entity.getRandom())) : fieldEnchantment.getRandom(entity.getRandom());
                int level = fieldEnchantment.getLevel(enchantment, entity);

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

    public static final ItemFunction<FieldStatusEffect> EFFECT_ITEM = register("effect_item", new ItemFunction<>(
            new FieldStatusEffect(StatusEffects.ABSORPTION, 1), (itemStack, entity, fieldStatusEffect) -> {
                if (itemStack.getItem() instanceof SuspiciousStewItem) {
                    SuspiciousStewItem.addEffectToStew(itemStack, fieldStatusEffect.statusEffect(), fieldStatusEffect.duration());
                }
    }));


    public static final ItemFunction<FieldMap> FILL_MAP = register("fill_map", new ItemFunction<>(
            FieldMap.VILLAGE, (itemStack, entity, fieldMap) -> {
                ServerWorld serverWorld = (ServerWorld) entity.getWorld();
                BlockPos blockPos = serverWorld.locateStructure(fieldMap.structure(), entity.getBlockPos(), 100, true);

                if (blockPos != null) {
                    ItemStack filledMap = FilledMapItem.createMap(serverWorld, blockPos.getX(), blockPos.getZ(), (byte) 2, true, true);
                    NbtCompound filledMapId = filledMap.getOrCreateNbt();
                    int map = filledMapId.getInt("map");
                    NbtCompound nbt = itemStack.getOrCreateNbt();
                    nbt.putInt("map", map);
                    itemStack.setNbt(nbt);

                    FilledMapItem.fillExplorationMap(serverWorld, itemStack);
                    MapState.addDecorationsNbt(itemStack, blockPos, "+", fieldMap.icon());
                    itemStack.setCustomName(Text.translatable(fieldMap.nameKey()));
                }
    }));

    public static final ItemFunction<FieldRegistered<Potion>> POTION = register("potion", new ItemFunction<>(new FieldRegistered<>(Registries.POTION, Potions.EMPTY),
            (itemStack, entity, potionFieldRegistered) -> {
                NbtCompound nbt = itemStack.getOrCreateNbt();
                nbt.putString("Potion", Registries.POTION.getId(potionFieldRegistered.value()).toString());
            }));


    public static final ItemFunction<FieldRegistered<PaintingVariant>> PAINTING = register("painting", new ItemFunction<>(
            new FieldRegistered<>(Registries.PAINTING_VARIANT, null),
            (itemStack, entity, variant) -> {
                Optional<String> value = variant.valueToString();

                if (value.isPresent()) {
                    NbtCompound nbt = itemStack.getOrCreateNbt();
                    NbtCompound entityTag = nbt.getCompound("EntityTag");
                    if (entityTag == null) entityTag = new NbtCompound();
                    entityTag.putString("variant", value.get());
                    nbt.put("EntityTag", entityTag);
                }
            })
    );


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


    public static ItemFunction.Data<FieldEnchantment> ofProgression(Enchantment... enchantments) {
        return new ItemFunction.Data<>(ENCHANT, FieldEnchantment.of(true, true, 0, enchantments));
    }

    public static ItemFunction.Data<FieldEnchantment> ofSingle(Enchantment... enchantments) {
        return new ItemFunction.Data<>(ENCHANT, FieldEnchantment.of(false, false, 0, enchantments));
    }
    public static ItemFunction.Data<FieldEnchantment> ofSingle(int fixedLevel, Enchantment... enchantments) {
        return new ItemFunction.Data<>(ENCHANT, FieldEnchantment.of(false, false, fixedLevel, enchantments));
    }

    public static ItemFunction.Data<?> ofColor(int color, boolean useSameColor) {
        return new ItemFunction.Data<>(LEATHER_COLOR, new FieldColor(color, true));
    }

    @Contract("_, _ -> new")
    public static <T extends JsonConvertible<T>> ItemFunction.Data<T> of(ItemFunction<T> function, T data) {
        return new ItemFunction.Data<>(function, data);
    }

    public static <V extends ItemFunction<T>, T extends JsonConvertible<T>> V register(String name, V function) {
        return Registry.register(TradeRegistries.ITEM_FUNCTIONS, new Identifier(Values.MOD_ID, name), function);
    }

    public static ItemFunction.Data<?> of(StatusEffect effect, int duration) {
        return of(EFFECT_ITEM, new FieldStatusEffect(effect, duration));
    }

    public static ItemFunction.Data<?> of(TagKey<Structure> structure, String nameKey, MapIcon.Type type) {
        return of(FILL_MAP, new FieldMap(structure, nameKey, type));
    }

    public static ItemFunction.Data<?> of(FieldMap map) {
        return of(FILL_MAP, map);
    }

    public static ItemFunction.Data<?> of(Potion potion) {
        return of(POTION, new FieldRegistered<>(Registries.POTION, potion));
    }

    public static ItemFunction.Data<?> of(PaintingVariant variant) {
        return of(PAINTING, new FieldRegistered<>(Registries.PAINTING_VARIANT, variant));
    }

    private static Optional<Integer> getColor(ItemStack itemStack1) {
        NbtCompound display = itemStack1.getOrCreateSubNbt("display");
        if (display.contains("color", NbtElement.INT_TYPE)) {
            return Optional.of(display.getInt("color"));
        }
        return Optional.empty();
    }
}
