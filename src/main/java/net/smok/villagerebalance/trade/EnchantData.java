package net.smok.villagerebalance.trade;

import com.google.gson.JsonObject;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.smok.villagerebalance.utility.JsonConvertible;
import org.jetbrains.annotations.NotNull;

public record EnchantData(Enchantment enchantment, int minLevel, int maxLevel, boolean useSameEnchant, boolean useVillagerLevel) implements JsonConvertible<EnchantData> {
    @Override
    public EnchantData childFromJson(@NotNull JsonObject json) {
        Enchantment enchantment = JsonConvertible.getRegister(json, "enchantment", Registries.ENCHANTMENT);
        int minLevel = JsonHelper.getInt(json, "min_level", this.minLevel);
        int maxLevel = JsonHelper.getInt(json, "max_level", this.maxLevel);
        boolean useSameEnchant = JsonHelper.getBoolean(json, "use_same_enchantment", this.useSameEnchant);
        boolean useVillagerLevel = JsonHelper.getBoolean(json, "use_villager_level", this.useVillagerLevel);
        return new EnchantData(enchantment, minLevel, maxLevel, useSameEnchant, useVillagerLevel);
    }

    @Override
    public void toJson(@NotNull JsonObject json) {
        Identifier id = Registries.ENCHANTMENT.getId(enchantment);
        if (id != null) {
            json.addProperty("enchantment", id.toString());
            json.addProperty("min_level", minLevel);
            json.addProperty("max_level", maxLevel);
            json.addProperty("use_same_enchantment", useSameEnchant);
            json.addProperty("use_villager_level", useVillagerLevel);
        }
    }
}
