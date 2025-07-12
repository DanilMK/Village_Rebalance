package net.smok.villagerebalance.trade.data;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import net.smok.villagerebalance.trade.ItemContainer;
import net.smok.villagerebalance.trade.conditions.OfferCondition;
import net.smok.villagerebalance.trade.fields.FieldVillagerData;
import net.smok.villagerebalance.trade.conditions.Conditions;

import java.util.Map;

public class MasonProvider extends TradeOffersProvider {

    public MasonProvider() {
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
                ItemContainer.of(new ItemStack(Items.SANDSTONE, 20), Conditions.of(FieldVillagerData.of(VillagerType.DESERT))),
                ItemContainer.of(new ItemStack(Items.MOSSY_COBBLESTONE, 20), Conditions.of(FieldVillagerData.of(VillagerType.JUNGLE))),
                ItemContainer.of(new ItemStack(Items.STONE, 20), Conditions.of(FieldVillagerData.of(VillagerType.PLAINS))),
                ItemContainer.of(new ItemStack(Items.RED_SANDSTONE, 20), Conditions.of(FieldVillagerData.of(VillagerType.SAVANNA))),
                ItemContainer.of(new ItemStack(Items.DEEPSLATE, 20), Conditions.of(FieldVillagerData.of(VillagerType.SNOW))),
                ItemContainer.of(new ItemStack(Items.MUD, 20), Conditions.of(FieldVillagerData.of(VillagerType.SWAMP))),
                ItemContainer.of(new ItemStack(Items.TUFF, 20), Conditions.of(FieldVillagerData.of(VillagerType.TAIGA)))
        );

        sellForEmerald("any_bricks", 2, 16,
                ItemContainer.of(new ItemStack(Items.CUT_SANDSTONE, 4), Conditions.of(FieldVillagerData.of(VillagerType.DESERT))),
                ItemContainer.of(new ItemStack(Items.MOSSY_STONE_BRICKS, 4), Conditions.of(FieldVillagerData.of(VillagerType.JUNGLE))),
                ItemContainer.of(new ItemStack(Items.STONE_BRICKS, 4), Conditions.of(FieldVillagerData.of(VillagerType.PLAINS))),
                ItemContainer.of(new ItemStack(Items.CUT_RED_SANDSTONE, 4), Conditions.of(FieldVillagerData.of(VillagerType.SAVANNA))),
                ItemContainer.of(new ItemStack(Items.DEEPSLATE_BRICKS, 4), Conditions.of(FieldVillagerData.of(VillagerType.SNOW))),
                ItemContainer.of(new ItemStack(Items.MUD_BRICKS, 4), Conditions.of(FieldVillagerData.of(VillagerType.SWAMP))),
                ItemContainer.of(new ItemStack(Items.STONE_BRICKS, 4), Conditions.of(FieldVillagerData.of(VillagerType.TAIGA)))
        );

        buyForEmerald("dripstone", 3, 16,
                item(Items.POINTED_DRIPSTONE, 16));

        sellForEmerald("dripstone_block", 3, 16,
                item(Items.DRIPSTONE_BLOCK, 4));

        buyDistributeForBiomes("dyes", 3, 1, 12, 16, DYES, TERRACOTTA, OfferCondition.SearchType.SELL, Map.of(
                VillagerType.DESERT, new int[] {ORANGE, BLUE},
                VillagerType.JUNGLE, new int[] {LIME, GREEN, CYAN, LIGHT_BLUE},
                VillagerType.PLAINS, new int[] {LIME, GREEN, PINK},
                VillagerType.SAVANNA, new int[] {RED, YELLOW, MAGENTA},
                VillagerType.SNOW, new int[] {WHITE, LIGHT_GRAY, GRAY, CYAN},
                VillagerType.SWAMP, new int[] {GRAY, BLUE, LIGHT_BLUE},
                VillagerType.TAIGA, new int[] {BLACK, BROWN, LIGHT_BLUE, PURPLE}
        ));

        sellDistributeForBiomes("terracotta", 3, 1, 12, 16, TERRACOTTA, DYES, OfferCondition.SearchType.BUY, Map.of(
                VillagerType.DESERT, new int[] {ORANGE, BLUE},
                VillagerType.JUNGLE, new int[] {LIME, GREEN, CYAN, LIGHT_BLUE},
                VillagerType.PLAINS, new int[] {LIME, GREEN, PINK},
                VillagerType.SAVANNA, new int[] {RED, YELLOW, MAGENTA},
                VillagerType.SNOW, new int[] {WHITE, LIGHT_GRAY, GRAY, CYAN},
                VillagerType.SWAMP, new int[] {GRAY, BLUE, LIGHT_BLUE},
                VillagerType.TAIGA, new int[] {BLACK, BROWN, LIGHT_BLUE, PURPLE}
        ));

        sellDistributeForBiomes("glazed_terracotta", 4, 1, 12, 16, GLAZED_TERRACOTTA, DYES, OfferCondition.SearchType.BUY, Map.of(
                VillagerType.DESERT, new int[] {ORANGE, BLUE},
                VillagerType.JUNGLE, new int[] {LIME, GREEN, CYAN, LIGHT_BLUE},
                VillagerType.PLAINS, new int[] {LIME, GREEN, PINK},
                VillagerType.SAVANNA, new int[] {RED, YELLOW, MAGENTA},
                VillagerType.SNOW, new int[] {WHITE, LIGHT_GRAY, GRAY, CYAN},
                VillagerType.SWAMP, new int[] {GRAY, BLUE, LIGHT_BLUE},
                VillagerType.TAIGA, new int[] {BLACK, BROWN, LIGHT_BLUE, PURPLE}
        ));

        buyForEmerald("nether_quartz",
                5, 16, item(Items.QUARTZ, 12));

        sellForEmerald("quartz_block",
                5, 16, item(Items.QUARTZ_BLOCK));
        sellForEmerald("quartz_pillar",
                5, 16, item(Items.QUARTZ_PILLAR));

    }
}
