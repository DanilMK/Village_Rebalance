package net.smok.villagerebalance.trade.data;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Pair;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import net.smok.villagerebalance.trade.ItemContainer;
import net.smok.villagerebalance.trade.conditions.OfferCondition;
import net.smok.villagerebalance.trade.fields.FieldVillagerData;
import net.smok.villagerebalance.trade.conditions.Conditions;

import java.util.List;
import java.util.Map;

public class MasonVillager extends VillagerTradeOffers {

    public MasonVillager() {
        super(VillagerProfession.MASON);
    }

    @Override
    public void fill() {

        buyForEmerald("clay_ball",
                1, 16, item(Items.CLAY_BALL, 10));

        sellForEmerald("brick",
                1, 16, item(Items.BRICK, 10));

        buyForEmerald("stones",
                2, 16,
                item(Items.ANDESITE, 16, Items.DIORITE, 16, Items.GRANITE, 16));

        sellForEmerald("polished_stones",
                2, 16,
                item(Items.POLISHED_ANDESITE, 4, Items.POLISHED_DIORITE, 4, Items.POLISHED_GRANITE, 4));

        buyForEmerald("any_stones", 2, 16,
                item(Items.SANDSTONE, 20, Conditions.of(FieldVillagerData.of(VillagerType.DESERT))),
                item(Items.MOSSY_COBBLESTONE, 20, Conditions.of(FieldVillagerData.of(VillagerType.JUNGLE))),
                item(Items.STONE, 20, Conditions.of(FieldVillagerData.of(VillagerType.PLAINS))),
                item(Items.RED_SANDSTONE, 20, Conditions.of(FieldVillagerData.of(VillagerType.SAVANNA))),
                item(Items.DEEPSLATE, 20, Conditions.of(FieldVillagerData.of(VillagerType.SNOW))),
                item(Items.MUD, 20, Conditions.of(FieldVillagerData.of(VillagerType.SWAMP))),
                item(Items.TUFF, 20, Conditions.of(FieldVillagerData.of(VillagerType.TAIGA)))
        );

        sellForEmerald("any_bricks", 2, 16,
                item(Items.CUT_SANDSTONE, 4, Conditions.of(FieldVillagerData.of(VillagerType.DESERT))),
                item(Items.MOSSY_STONE_BRICKS, 4, Conditions.of(FieldVillagerData.of(VillagerType.JUNGLE))),
                item(Items.STONE_BRICKS, 4, Conditions.of(FieldVillagerData.of(VillagerType.PLAINS))),
                item(Items.CUT_RED_SANDSTONE, 4, Conditions.of(FieldVillagerData.of(VillagerType.SAVANNA))),
                item(Items.DEEPSLATE_BRICKS, 4, Conditions.of(FieldVillagerData.of(VillagerType.SNOW))),
                item(Items.MUD_BRICKS, 4, Conditions.of(FieldVillagerData.of(VillagerType.SWAMP))),
                item(Items.STONE_BRICKS, 4, Conditions.of(FieldVillagerData.of(VillagerType.TAIGA)))
        );

        buyForEmerald("dripstone", 3, 16,
                item(Items.POINTED_DRIPSTONE, 16));

        sellForEmerald("dripstone_block", 3, 16,
                item(Items.DRIPSTONE_BLOCK, 4));

        Map<VillagerType, int[]> biomeDistribution = Map.of(
                VillagerType.DESERT, new int[]{ORANGE, BLUE},
                VillagerType.JUNGLE, new int[]{LIME, GREEN, CYAN, LIGHT_BLUE},
                VillagerType.PLAINS, new int[]{LIME, GREEN, PINK},
                VillagerType.SAVANNA, new int[]{RED, YELLOW, MAGENTA},
                VillagerType.SNOW, new int[]{WHITE, LIGHT_GRAY, GRAY, CYAN},
                VillagerType.SWAMP, new int[]{GRAY, BLUE, LIGHT_BLUE},
                VillagerType.TAIGA, new int[]{BLACK, BROWN, LIGHT_BLUE, PURPLE}
        );
        
        buyDistributeForBiomes("dyes", 3, 1, 12, 16, DYES, 
                List.of(new Pair<>(OfferCondition.SearchType.SELL, TERRACOTTA)), biomeDistribution);

        sellDistributeForBiomes("terracotta", 3, 1, 12, 16, TERRACOTTA, 
                List.of(new Pair<>(OfferCondition.SearchType.BUY, DYES)), biomeDistribution);

        sellDistributeForBiomes("glazed_terracotta", 4, 1, 12, 16, GLAZED_TERRACOTTA,
                List.of(new Pair<>(OfferCondition.SearchType.SELL, TERRACOTTA), new Pair<>(OfferCondition.SearchType.BUY, DYES)), biomeDistribution);
        
        
        buyForEmerald("nether_quartz",
                5, 16, item(Items.QUARTZ, 12));

        sellForEmerald("quartz_block",
                5, 16, item(Items.QUARTZ_BLOCK));
        sellForEmerald("quartz_pillar",
                5, 16, item(Items.QUARTZ_PILLAR));

    }
}
