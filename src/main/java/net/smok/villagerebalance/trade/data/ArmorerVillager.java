package net.smok.villagerebalance.trade.data;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import net.smok.villagerebalance.trade.ItemContainer;
import net.smok.villagerebalance.trade.fields.FieldVillagerData;
import net.smok.villagerebalance.trade.conditions.Conditions;
import net.smok.villagerebalance.trade.functions.ItemFunctions;

import java.util.Map;

public class ArmorerVillager extends VillagerTradeOffers {


    public ArmorerVillager() {
        super(VillagerProfession.ARMORER);
    }

    @Override
    public void fill() {
        buyForEmerald("coal", 1, 12, item(Items.COAL, 15));
        buyForEmerald("iron_ingot", 2, 12, item(Items.IRON_INGOT, 4));
        buyForEmerald("lava_bucket", 3, 12, item(Items.LAVA_BUCKET, 1));
        buyForEmerald("diamond", 4, 12, item(Items.DIAMOND, 1));

        sellDistributeForBiomes(3, new Item[]{Items.CHAINMAIL_BOOTS, Items.IRON_BOOTS, Items.IRON_BOOTS, Items.DIAMOND_BOOTS},
                villagerTypeMap(
                        new Enchantment[]{Enchantments.PROTECTION, Enchantments.FEATHER_FALLING, Enchantments.DEPTH_STRIDER, Enchantments.FROST_WALKER},
                        new Enchantment[]{Enchantments.PROJECTILE_PROTECTION, Enchantments.FEATHER_FALLING, Enchantments.DEPTH_STRIDER, Enchantments.FROST_WALKER},
                        new Enchantment[]{Enchantments.PROJECTILE_PROTECTION, Enchantments.FEATHER_FALLING, Enchantments.DEPTH_STRIDER, Enchantments.FROST_WALKER},
                        new Enchantment[]{Enchantments.FIRE_PROTECTION, Enchantments.FEATHER_FALLING, Enchantments.DEPTH_STRIDER, Enchantments.FROST_WALKER},
                        new Enchantment[]{Enchantments.THORNS, Enchantments.FEATHER_FALLING, Enchantments.DEPTH_STRIDER, Enchantments.FROST_WALKER},
                        new Enchantment[]{Enchantments.THORNS, Enchantments.FEATHER_FALLING, Enchantments.DEPTH_STRIDER, Enchantments.FROST_WALKER},
                        new Enchantment[]{Enchantments.BLAST_PROTECTION, Enchantments.FEATHER_FALLING, Enchantments.DEPTH_STRIDER, Enchantments.FROST_WALKER}
                )
        );

        sellDistributeForBiomes(5, new Item[]{Items.CHAINMAIL_LEGGINGS, Items.IRON_LEGGINGS, Items.IRON_LEGGINGS, Items.DIAMOND_LEGGINGS},
                villagerTypeMap(
                        new Enchantment[]{Enchantments.PROTECTION},
                        new Enchantment[]{Enchantments.PROJECTILE_PROTECTION},
                        new Enchantment[]{Enchantments.PROJECTILE_PROTECTION},
                        new Enchantment[]{Enchantments.FIRE_PROTECTION},
                        new Enchantment[]{Enchantments.THORNS},
                        new Enchantment[]{Enchantments.THORNS},
                        new Enchantment[]{Enchantments.BLAST_PROTECTION}
                )
        );

        sellDistributeForBiomes(6, new Item[]{Items.CHAINMAIL_CHESTPLATE, Items.IRON_CHESTPLATE, Items.IRON_CHESTPLATE, Items.DIAMOND_CHESTPLATE},
                villagerTypeMap(
                        new Enchantment[]{Enchantments.PROTECTION},
                        new Enchantment[]{Enchantments.PROJECTILE_PROTECTION},
                        new Enchantment[]{Enchantments.PROJECTILE_PROTECTION},
                        new Enchantment[]{Enchantments.FIRE_PROTECTION},
                        new Enchantment[]{Enchantments.THORNS},
                        new Enchantment[]{Enchantments.THORNS},
                        new Enchantment[]{Enchantments.BLAST_PROTECTION}
                )
        );

        sellDistributeForBiomes(3, new Item[]{Items.CHAINMAIL_HELMET, Items.IRON_HELMET, Items.IRON_HELMET, Items.DIAMOND_HELMET},
                villagerTypeMap(
                        new Enchantment[]{Enchantments.PROTECTION, Enchantments.RESPIRATION, Enchantments.AQUA_AFFINITY},
                        new Enchantment[]{Enchantments.PROJECTILE_PROTECTION, Enchantments.RESPIRATION, Enchantments.AQUA_AFFINITY},
                        new Enchantment[]{Enchantments.PROJECTILE_PROTECTION, Enchantments.RESPIRATION, Enchantments.AQUA_AFFINITY},
                        new Enchantment[]{Enchantments.FIRE_PROTECTION, Enchantments.RESPIRATION, Enchantments.AQUA_AFFINITY},
                        new Enchantment[]{Enchantments.THORNS, Enchantments.RESPIRATION, Enchantments.AQUA_AFFINITY},
                        new Enchantment[]{Enchantments.THORNS, Enchantments.RESPIRATION, Enchantments.AQUA_AFFINITY},
                        new Enchantment[]{Enchantments.BLAST_PROTECTION, Enchantments.RESPIRATION, Enchantments.AQUA_AFFINITY}
                )
        );

        sellBook("enchanted_book", 5,
                Enchantments.PROTECTION, Enchantments.PROJECTILE_PROTECTION, Enchantments.BLAST_PROTECTION,
                Enchantments.FIRE_PROTECTION, Enchantments.THORNS, Enchantments.RESPIRATION, Enchantments.AQUA_AFFINITY,
                Enchantments.FEATHER_FALLING, Enchantments.DEPTH_STRIDER, Enchantments.FROST_WALKER
        );
    }

    protected void sellDistributeForBiomes(int emeralds, Item[] items, Map<VillagerType, Enchantment[]> enchantmentsForBiome) {
        for (int i = 1; i < items.length + 1; i++) {
            for (Map.Entry<VillagerType, Enchantment[]> entry : enchantmentsForBiome.entrySet()) {
                Item item = items[i-1];
                String id = item.toString() + "_" + i;
                ItemContainer[] container = new ItemContainer[]{item(item, ItemFunctions.ofProgression(entry.getValue()))};
                FieldVillagerData data = new FieldVillagerData(entry.getKey(), profession, i);
                if (i == 1) putTradeOffer(id, new ItemContainer[]{item(emeralds)}, ItemContainer.EMPTY, container, 12,
                        0.05f, experienceByLevelSell(i), data);
                else putTradeOffer(id, new ItemContainer[]{item(i * emeralds)}, ItemContainer.EMPTY, container, 12,
                        0.05f, experienceByLevelSell(i), data, Conditions.ofSell(items[0]));
            }
        }
    }
}
