package net.smok.villagerebalance.trade;

import net.minecraft.enchantment.Enchantment;

public record EnchantData(Enchantment enchantment, int level, boolean useSameEnchant, boolean useVillagerLevel) {
}
