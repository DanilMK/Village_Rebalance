package net.smok.villagerebalance.trade;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import net.smok.villagerebalance.Values;
import net.smok.villagerebalance.trade.conditions.*;
import net.smok.villagerebalance.trade.functions.ItemFunction;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TradeOffersProvider {


    public static final String DIRECTORY = "village";
    private final HashMap<Identifier, OfferFactory> objects = new HashMap<>();



    private static final Item[] DYES = new Item[]{ Items.WHITE_DYE, Items.LIGHT_GRAY_DYE, Items.GRAY_DYE, Items.BLACK_DYE, Items.BROWN_DYE, Items.RED_DYE, Items.ORANGE_DYE, Items.YELLOW_DYE, Items.LIME_DYE, Items.GREEN_DYE, Items.CYAN_DYE, Items.LIGHT_BLUE_DYE, Items.BLUE_DYE, Items.PURPLE_DYE, Items.MAGENTA_DYE, Items.PINK_DYE };
    private static final Item[] WOOLS = new Item[]{ Items.WHITE_WOOL, Items.LIGHT_GRAY_WOOL, Items.GRAY_WOOL, Items.BLACK_WOOL, Items.BROWN_WOOL, Items.RED_WOOL, Items.ORANGE_WOOL, Items.YELLOW_WOOL, Items.LIME_WOOL, Items.GREEN_WOOL, Items.CYAN_WOOL, Items.LIGHT_BLUE_WOOL, Items.BLUE_WOOL, Items.PURPLE_WOOL, Items.MAGENTA_WOOL, Items.PINK_WOOL };
    private static final Item[] CARPETS = new Item[]{ Items.WHITE_CARPET, Items.LIGHT_GRAY_CARPET, Items.GRAY_CARPET, Items.BLACK_CARPET, Items.BROWN_CARPET, Items.RED_CARPET, Items.ORANGE_CARPET, Items.YELLOW_CARPET, Items.LIME_CARPET, Items.GREEN_CARPET, Items.CYAN_CARPET, Items.LIGHT_BLUE_CARPET, Items.BLUE_CARPET, Items.PURPLE_CARPET, Items.MAGENTA_CARPET, Items.PINK_CARPET };
    private static final Item[] TERRACOTTA = new Item[]{ Items.WHITE_TERRACOTTA, Items.LIGHT_GRAY_TERRACOTTA, Items.GRAY_TERRACOTTA, Items.BLACK_TERRACOTTA, Items.BROWN_TERRACOTTA, Items.RED_TERRACOTTA, Items.ORANGE_TERRACOTTA, Items.YELLOW_TERRACOTTA, Items.LIME_TERRACOTTA, Items.GREEN_TERRACOTTA, Items.CYAN_TERRACOTTA, Items.LIGHT_BLUE_TERRACOTTA, Items.BLUE_TERRACOTTA, Items.PURPLE_TERRACOTTA, Items.MAGENTA_TERRACOTTA, Items.PINK_TERRACOTTA };
    private static final Item[] GLAZED_TERRACOTTA = new Item[]{ Items.WHITE_GLAZED_TERRACOTTA, Items.LIGHT_GRAY_GLAZED_TERRACOTTA, Items.GRAY_GLAZED_TERRACOTTA, Items.BLACK_GLAZED_TERRACOTTA, Items.BROWN_GLAZED_TERRACOTTA, Items.RED_GLAZED_TERRACOTTA, Items.ORANGE_GLAZED_TERRACOTTA, Items.YELLOW_GLAZED_TERRACOTTA, Items.LIME_GLAZED_TERRACOTTA, Items.GREEN_GLAZED_TERRACOTTA, Items.CYAN_GLAZED_TERRACOTTA, Items.LIGHT_BLUE_GLAZED_TERRACOTTA, Items.BLUE_GLAZED_TERRACOTTA, Items.PURPLE_GLAZED_TERRACOTTA, Items.MAGENTA_GLAZED_TERRACOTTA, Items.PINK_GLAZED_TERRACOTTA };
    private static final Item[] CANDLES = new Item[]{ Items.WHITE_CANDLE, Items.LIGHT_GRAY_CANDLE, Items.GRAY_CANDLE, Items.BLACK_CANDLE, Items.BROWN_CANDLE, Items.RED_CANDLE, Items.ORANGE_CANDLE, Items.YELLOW_CANDLE, Items.LIME_CANDLE, Items.GREEN_CANDLE, Items.CYAN_CANDLE, Items.LIGHT_BLUE_CANDLE, Items.BLUE_CANDLE, Items.PURPLE_CANDLE, Items.MAGENTA_CANDLE, Items.PINK_CANDLE };
    private static final Item[] BEDS = new Item[]{ Items.WHITE_BED, Items.LIGHT_GRAY_BED, Items.GRAY_BED, Items.BLACK_BED, Items.BROWN_BED, Items.RED_BED, Items.ORANGE_BED, Items.YELLOW_BED, Items.LIME_BED, Items.GREEN_BED, Items.CYAN_BED, Items.LIGHT_BLUE_BED, Items.BLUE_BED, Items.PURPLE_BED, Items.MAGENTA_BED, Items.PINK_BED };

    private static final int WHITE = 0;
    private static final int LIGHT_GRAY = 1;
    private static final int GRAY = 2;
    private static final int BLACK = 3;
    private static final int BROWN = 4;
    private static final int RED = 5;
    private static final int ORANGE = 6;
    private static final int YELLOW = 7;
    private static final int LIME = 8;
    private static final int GREEN = 9;
    private static final int CYAN = 10;
    private static final int LIGHT_BLUE = 11;
    private static final int BLUE = 12;
    private static final int PURPLE = 13;
    private static final int MAGENTA = 14;
    private static final int PINK = 15;


    public HashMap<Identifier, OfferFactory> getObjects() {
        return objects;
    }

    public void fill() {
        fillMason();
    }


    private void fillMason() {
        VillagerProfession mason = VillagerProfession.MASON;

        buyForEmerald("clay_ball", VillagerData.of(mason, 1),
                1, 16, item(Items.CLAY_BALL, 10));

        sellForEmerald("brick", VillagerData.of(mason, 1),
                1, 16, item(Items.BRICK, 10));

        buyForEmerald("tri_stones", VillagerData.of(mason, 2),
                1, 16,
                item(Items.ANDESITE, 16, Items.DIORITE, 16, Items.GRANITE, 16));

        sellForEmerald("tri_polished_stones", VillagerData.of(mason, 2),
                1, 16,
                item(Items.POLISHED_ANDESITE, 4, Items.POLISHED_DIORITE, 4, Items.POLISHED_GRANITE, 4));

        buyForEmerald("any_stones", VillagerData.of(mason, 2), 1, 16,
                ItemContainer.of(new ItemStack(Items.SANDSTONE, 20), Conditions.of(VillagerData.of(VillagerType.DESERT))),
                ItemContainer.of(new ItemStack(Items.MOSSY_COBBLESTONE, 20), Conditions.of(VillagerData.of(VillagerType.JUNGLE))),
                ItemContainer.of(new ItemStack(Items.STONE, 20), Conditions.of(VillagerData.of(VillagerType.PLAINS))),
                ItemContainer.of(new ItemStack(Items.RED_SANDSTONE, 20), Conditions.of(VillagerData.of(VillagerType.SAVANNA))),
                ItemContainer.of(new ItemStack(Items.DEEPSLATE, 20), Conditions.of(VillagerData.of(VillagerType.SNOW))),
                ItemContainer.of(new ItemStack(Items.MUD, 20), Conditions.of(VillagerData.of(VillagerType.SWAMP))),
                ItemContainer.of(new ItemStack(Items.TUFF, 20), Conditions.of(VillagerData.of(VillagerType.TAIGA)))
        );

        sellForEmerald("any_bricks", VillagerData.of(mason, 2), 1, 16,
                ItemContainer.of(new ItemStack(Items.CUT_SANDSTONE, 4), Conditions.of(VillagerData.of(VillagerType.DESERT))),
                ItemContainer.of(new ItemStack(Items.MOSSY_STONE_BRICKS, 4), Conditions.of(VillagerData.of(VillagerType.JUNGLE))),
                ItemContainer.of(new ItemStack(Items.STONE_BRICKS, 4), Conditions.of(VillagerData.of(VillagerType.PLAINS))),
                ItemContainer.of(new ItemStack(Items.CUT_RED_SANDSTONE, 4), Conditions.of(VillagerData.of(VillagerType.SAVANNA))),
                ItemContainer.of(new ItemStack(Items.DEEPSLATE_BRICKS, 4), Conditions.of(VillagerData.of(VillagerType.SNOW))),
                ItemContainer.of(new ItemStack(Items.MUD_BRICKS, 4), Conditions.of(VillagerData.of(VillagerType.SWAMP))),
                ItemContainer.of(new ItemStack(Items.STONE_BRICKS, 4), Conditions.of(VillagerData.of(VillagerType.TAIGA)))
        );

        buyForEmerald("dripstone", VillagerData.of(mason, 3), 1, 16,
                item(Items.POINTED_DRIPSTONE, 16));

        sellForEmerald("dripstone_block", VillagerData.of(mason, 3), 1, 16,
                item(Items.DRIPSTONE_BLOCK, 4));

        buyDistributeForBiomes("dyes", mason, 3, 1, 12, 16, DYES, Map.of(
                VillagerType.DESERT, new int[] {ORANGE, BLUE},
                VillagerType.JUNGLE, new int[] {LIME, GREEN, CYAN, LIGHT_BLUE},
                VillagerType.PLAINS, new int[] {LIME, GREEN, PINK},
                VillagerType.SAVANNA, new int[] {RED, YELLOW, MAGENTA},
                VillagerType.SNOW, new int[] {WHITE, LIGHT_GRAY, GRAY, CYAN},
                VillagerType.SWAMP, new int[] {GRAY, BLUE, BLUE},
                VillagerType.TAIGA, new int[] {BLACK, BROWN, LIGHT_BLUE, PURPLE}
        ));

        sellDistributeForBiomes("terracotta", mason, 3, 1, 12, 16, TERRACOTTA, Map.of(
                VillagerType.DESERT, new int[] {ORANGE, BLUE},
                VillagerType.JUNGLE, new int[] {LIME, GREEN, CYAN, LIGHT_BLUE},
                VillagerType.PLAINS, new int[] {LIME, GREEN, PINK},
                VillagerType.SAVANNA, new int[] {RED, YELLOW, MAGENTA},
                VillagerType.SNOW, new int[] {WHITE, LIGHT_GRAY, GRAY, CYAN},
                VillagerType.SWAMP, new int[] {GRAY, BLUE, BLUE},
                VillagerType.TAIGA, new int[] {BLACK, BROWN, LIGHT_BLUE, PURPLE}
        ));

        sellDistributeForBiomes("glazed_terracotta", mason, 4, 1, 12, 16, GLAZED_TERRACOTTA, Map.of(
                VillagerType.DESERT, new int[] {ORANGE, BLUE},
                VillagerType.JUNGLE, new int[] {LIME, GREEN, CYAN, LIGHT_BLUE},
                VillagerType.PLAINS, new int[] {LIME, GREEN, PINK},
                VillagerType.SAVANNA, new int[] {RED, YELLOW, MAGENTA},
                VillagerType.SNOW, new int[] {WHITE, LIGHT_GRAY, GRAY, CYAN},
                VillagerType.SWAMP, new int[] {GRAY, BLUE, BLUE},
                VillagerType.TAIGA, new int[] {BLACK, BROWN, LIGHT_BLUE, PURPLE}
        ));

        buyForEmerald("nether_quartz", VillagerData.of(mason, 5),
                1, 16, item(Items.QUARTZ, 12));

        sellForEmerald("quartz_block", VillagerData.of(mason, 5),
                1, 16, item(Items.QUARTZ_BLOCK));
        sellForEmerald("quartz_pillar", VillagerData.of(mason, 5),
                1, 16, item(Items.QUARTZ_PILLAR));

    }


    private void buyForEmerald(String id, VillagerData data, int emeralds, int maxUses,
                               ItemContainer... container) {
        putByData(id, container, ItemContainer.EMPTY, new ItemContainer[]{item(emeralds)}, maxUses,
                true, 0.05f, experienceByLevel(data.level()), data);
    }

    private void sellForEmerald(String id, VillagerData data, int emeralds, int maxUses,
                                ItemContainer... container) {
        putByData(id, new ItemContainer[]{item(emeralds)}, ItemContainer.EMPTY, container, maxUses,
                true, 0.05f, experienceByLevel(data.level()), data);
    }

    private void buyDistributeForBiomes(String id, VillagerProfession profession, int level, int emeralds, int price,
                                        int maxUses, Item[] items, Map<VillagerType, int[]> colors) {
        colors.forEach((type, color) ->
                putByData(id, Arrays.stream(color).mapToObj(i -> item(items[i], price)).toArray(ItemContainer[]::new),
                ItemContainer.EMPTY,
                new ItemContainer[]{item(emeralds)},
                maxUses, true, 0.05f, experienceByLevel(level),
                new VillagerData(type, profession, level)));
    }
    private void sellDistributeForBiomes(String id, VillagerProfession profession, int level, int emeralds, int price,
                                         int maxUses, Item[] items, Map<VillagerType, int[]> colors) {
        colors.forEach((type, color) ->
                putByData(id, new ItemContainer[]{item(emeralds)},
                ItemContainer.EMPTY,
                Arrays.stream(color).mapToObj(i -> item(items[i], price)).toArray(ItemContainer[]::new),
                maxUses, true, 0.05f, experienceByLevel(level),
                new VillagerData(type, profession, level)));
    }

    private void putByData(String id, @NotNull ItemContainer[] item1, @NotNull ItemContainer[] item2, @NotNull ItemContainer[] sell,
                           int maxUses, boolean rewardPlayer, float priceMultiplier, int experience, VillagerData data, Condition.Data<?> condition) {
        if (data.type() != null) id = data.type() + "/" + id;
        if (data.profession() != null) id = data.profession() + "/" + id;
        OfferFactory factory = new OfferFactory(item1, item2, sell,
                maxUses, rewardPlayer, priceMultiplier, experience, Conditions.and(Conditions.of(data), condition));
        objects.put(new Identifier(Values.MOD_ID, id), factory);
    }

    private void putByData(String id, @NotNull ItemContainer[] item1, @NotNull ItemContainer[] item2, @NotNull ItemContainer[] sell,
                           int maxUses, boolean rewardPlayer, float priceMultiplier, int experience, VillagerData data) {
        if (data.type() != null) id = data.type() + "/" + id;
        if (data.profession() != null) id = data.profession() + "/" + id;
        OfferFactory factory = new OfferFactory(item1, item2, sell,
                maxUses, rewardPlayer, priceMultiplier, experience, Conditions.of(data));
        objects.put(new Identifier(Values.MOD_ID, id), factory);
    }






    private static @NotNull ItemContainer[] item() {
        return ItemContainer.EMPTY;
    }

    private static @NotNull ItemContainer item(Item item) {
        return ItemContainer.of(new ItemStack(item));
    }

    private static @NotNull ItemContainer item(Item item, int count) {
        return ItemContainer.of(new ItemStack(item, count));
    }

    public static @NotNull ItemContainer @NotNull [] item(Item item0, int count0, Item item1, int count1) {
        return ItemContainer.of(new ItemStack(item0, count0), new ItemStack(item1, count1));
    }

    public static @NotNull ItemContainer @NotNull [] item(Item item0, int count0, Item item1, int count1, Item item2, int count2) {
        return ItemContainer.of(new ItemStack(item0, count0), new ItemStack(item1, count1), new ItemStack(item2, count2));
    }

    private static @NotNull ItemContainer item(int price) {
        return ItemContainer.of(new ItemStack(Items.EMERALD, price));
    }

    private static @NotNull ItemContainer item(Item item, ItemFunction.Data<?>... functions) {
        return ItemContainer.of(new ItemStack(item), functions);
    }

    private static @NotNull ItemContainer item(Item item, int count, ItemFunction.Data<?>... functions) {
        return ItemContainer.of(new ItemStack(item, count), functions);
    }

    public static int experienceByLevel(int level) {
        if (level == 0) return 1;
        return MathHelper.clamp((level - 1) * 10, 2, 50);
    }



    private void putSimple(String id, OfferFactory factory) {
        objects.put(new Identifier(Values.MOD_ID, id), factory);
    }


}
