package net.smok.villagerebalance.utility;

import com.mojang.serialization.Lifecycle;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
import net.smok.villagerebalance.Values;
import net.smok.villagerebalance.trade.TradeOffers;
import net.smok.villagerebalance.trade.conditions.Condition;
import net.smok.villagerebalance.trade.functions.ItemFunction;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class TradeRegistries {


    public static final RegistryKey<Registry<Condition<?>>> CONDITION_KEY = RegistryKey.ofRegistry(new Identifier(Values.MOD_ID, "condition"));
    public static final RegistryKey<Registry<ItemFunction<?>>> ITEM_FUNCTION_KEY = RegistryKey.ofRegistry(new Identifier(Values.MOD_ID, "function"));

    public static final DefaultedRegistry<Condition<?>> CONDITIONS = createDefaultedRegistry(CONDITION_KEY, Condition.DEFAULT_CONDITION);
    public static final Registry<ItemFunction<?>> ITEM_FUNCTIONS = new SimpleRegistry<>(ITEM_FUNCTION_KEY, Lifecycle.stable());


    @NotNull public static TradeOffers tradeOffers = new TradeOffers(Map.of());


    private static <T> DefaultedRegistry<T> createDefaultedRegistry(RegistryKey<Registry<T>> key, T defaultValue) {
        SimpleDefaultedRegistry<T> r = new SimpleDefaultedRegistry<>("base", key, Lifecycle.stable(), false);
        r.set(0, RegistryKey.of(key, new Identifier("base")), defaultValue, Lifecycle.stable());
        return r;
    }


}
