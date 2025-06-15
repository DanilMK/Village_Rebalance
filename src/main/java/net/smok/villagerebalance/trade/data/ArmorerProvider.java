package net.smok.villagerebalance.trade.data;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import net.smok.villagerebalance.trade.functions.ItemFunctions;

import java.util.Map;

public class ArmorerProvider extends TradeOffersProvider {


    public ArmorerProvider() {
        super(VillagerProfession.ARMORER);
    }

    @Override
    public void fill() {
        buyForEmerald("coal", 1, 12, item(Items.COAL, 15));
        buyForEmerald("iron_ingot", 2, 12, item(Items.IRON_INGOT, 4));
        buyForEmerald("lava_bucket", 3, 12, item(Items.LAVA_BUCKET, 1));
        buyForEmerald("diamond", 4, 12, item(Items.DIAMOND, 1));

        sellDistributeForBiomes(12, 3, new Item[]{Items.CHAINMAIL_BOOTS, Items.IRON_BOOTS, Items.IRON_BOOTS, Items.DIAMOND_BOOTS},
                Map.of(
                        VillagerType.PLAINS, new Enchantment[]{Enchantments.PROTECTION, Enchantments.FEATHER_FALLING, Enchantments.DEPTH_STRIDER, Enchantments.FROST_WALKER},
                        VillagerType.JUNGLE, new Enchantment[]{Enchantments.PROJECTILE_PROTECTION, Enchantments.FEATHER_FALLING, Enchantments.DEPTH_STRIDER, Enchantments.FROST_WALKER},
                        VillagerType.TAIGA, new Enchantment[]{Enchantments.PROJECTILE_PROTECTION, Enchantments.FEATHER_FALLING, Enchantments.DEPTH_STRIDER, Enchantments.FROST_WALKER},
                        VillagerType.SAVANNA, new Enchantment[]{Enchantments.FIRE_PROTECTION, Enchantments.FEATHER_FALLING, Enchantments.DEPTH_STRIDER, Enchantments.FROST_WALKER},
                        VillagerType.DESERT, new Enchantment[]{Enchantments.THORNS, Enchantments.FEATHER_FALLING, Enchantments.DEPTH_STRIDER, Enchantments.FROST_WALKER},
                        VillagerType.SWAMP, new Enchantment[]{Enchantments.THORNS, Enchantments.FEATHER_FALLING, Enchantments.DEPTH_STRIDER, Enchantments.FROST_WALKER}
                )
        );

        sellDistributeForBiomes(12, 5, new Item[]{Items.CHAINMAIL_LEGGINGS, Items.IRON_LEGGINGS, Items.IRON_LEGGINGS, Items.DIAMOND_LEGGINGS},
                Map.of(
                        VillagerType.PLAINS, new Enchantment[]{Enchantments.PROTECTION},
                        VillagerType.JUNGLE, new Enchantment[]{Enchantments.PROJECTILE_PROTECTION},
                        VillagerType.TAIGA, new Enchantment[]{Enchantments.PROJECTILE_PROTECTION},
                        VillagerType.SAVANNA, new Enchantment[]{Enchantments.FIRE_PROTECTION},
                        VillagerType.DESERT, new Enchantment[]{Enchantments.THORNS},
                        VillagerType.SWAMP, new Enchantment[]{Enchantments.THORNS}
                )
        );

        sellDistributeForBiomes(12, 6, new Item[]{Items.CHAINMAIL_CHESTPLATE, Items.IRON_CHESTPLATE, Items.IRON_CHESTPLATE, Items.DIAMOND_CHESTPLATE},
                Map.of(
                        VillagerType.PLAINS, new Enchantment[]{Enchantments.PROTECTION},
                        VillagerType.JUNGLE, new Enchantment[]{Enchantments.PROJECTILE_PROTECTION},
                        VillagerType.TAIGA, new Enchantment[]{Enchantments.PROJECTILE_PROTECTION},
                        VillagerType.SAVANNA, new Enchantment[]{Enchantments.FIRE_PROTECTION},
                        VillagerType.DESERT, new Enchantment[]{Enchantments.THORNS},
                        VillagerType.SWAMP, new Enchantment[]{Enchantments.THORNS}
                )
        );

        sellDistributeForBiomes(12, 3, new Item[]{Items.CHAINMAIL_HELMET, Items.IRON_HELMET, Items.IRON_HELMET, Items.DIAMOND_HELMET},
                Map.of(
                        VillagerType.PLAINS, new Enchantment[]{Enchantments.PROTECTION, Enchantments.RESPIRATION, Enchantments.AQUA_AFFINITY},
                        VillagerType.JUNGLE, new Enchantment[]{Enchantments.PROJECTILE_PROTECTION, Enchantments.RESPIRATION, Enchantments.AQUA_AFFINITY},
                        VillagerType.TAIGA, new Enchantment[]{Enchantments.PROJECTILE_PROTECTION, Enchantments.RESPIRATION, Enchantments.AQUA_AFFINITY},
                        VillagerType.SAVANNA, new Enchantment[]{Enchantments.FIRE_PROTECTION, Enchantments.RESPIRATION, Enchantments.AQUA_AFFINITY},
                        VillagerType.DESERT, new Enchantment[]{Enchantments.THORNS, Enchantments.RESPIRATION, Enchantments.AQUA_AFFINITY},
                        VillagerType.SWAMP, new Enchantment[]{Enchantments.THORNS, Enchantments.RESPIRATION, Enchantments.AQUA_AFFINITY}
                )
        );

        sellBook("enchanted_book", 5,
                Enchantments.PROTECTION, Enchantments.PROJECTILE_PROTECTION, Enchantments.BLAST_PROTECTION,
                Enchantments.FIRE_PROTECTION, Enchantments.THORNS, Enchantments.RESPIRATION, Enchantments.AQUA_AFFINITY,
                Enchantments.FEATHER_FALLING, Enchantments.DEPTH_STRIDER, Enchantments.FROST_WALKER
        );
    }
}
