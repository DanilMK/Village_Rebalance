package net.smok.villagerebalance.trade.data;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import net.smok.villagerebalance.Values;
import net.smok.villagerebalance.trade.ItemContainer;
import net.smok.villagerebalance.trade.OfferFactory;
import net.smok.villagerebalance.trade.fields.FieldVillagerData;
import net.smok.villagerebalance.trade.conditions.*;
import net.smok.villagerebalance.trade.functions.ItemFunction;
import net.smok.villagerebalance.trade.functions.ItemFunctions;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class VillagerTradeOffers {


    public static final String DIRECTORY = "village";


    protected static final Item[] DYES = new Item[]{ Items.WHITE_DYE, Items.LIGHT_GRAY_DYE, Items.GRAY_DYE, Items.BLACK_DYE, Items.BROWN_DYE, Items.RED_DYE, Items.ORANGE_DYE, Items.YELLOW_DYE, Items.LIME_DYE, Items.GREEN_DYE, Items.CYAN_DYE, Items.LIGHT_BLUE_DYE, Items.BLUE_DYE, Items.PURPLE_DYE, Items.MAGENTA_DYE, Items.PINK_DYE };
    protected static final Item[] WOOLS = new Item[]{ Items.WHITE_WOOL, Items.LIGHT_GRAY_WOOL, Items.GRAY_WOOL, Items.BLACK_WOOL, Items.BROWN_WOOL, Items.RED_WOOL, Items.ORANGE_WOOL, Items.YELLOW_WOOL, Items.LIME_WOOL, Items.GREEN_WOOL, Items.CYAN_WOOL, Items.LIGHT_BLUE_WOOL, Items.BLUE_WOOL, Items.PURPLE_WOOL, Items.MAGENTA_WOOL, Items.PINK_WOOL };
    protected static final Item[] CARPETS = new Item[]{ Items.WHITE_CARPET, Items.LIGHT_GRAY_CARPET, Items.GRAY_CARPET, Items.BLACK_CARPET, Items.BROWN_CARPET, Items.RED_CARPET, Items.ORANGE_CARPET, Items.YELLOW_CARPET, Items.LIME_CARPET, Items.GREEN_CARPET, Items.CYAN_CARPET, Items.LIGHT_BLUE_CARPET, Items.BLUE_CARPET, Items.PURPLE_CARPET, Items.MAGENTA_CARPET, Items.PINK_CARPET };
    protected static final Item[] TERRACOTTA = new Item[]{ Items.WHITE_TERRACOTTA, Items.LIGHT_GRAY_TERRACOTTA, Items.GRAY_TERRACOTTA, Items.BLACK_TERRACOTTA, Items.BROWN_TERRACOTTA, Items.RED_TERRACOTTA, Items.ORANGE_TERRACOTTA, Items.YELLOW_TERRACOTTA, Items.LIME_TERRACOTTA, Items.GREEN_TERRACOTTA, Items.CYAN_TERRACOTTA, Items.LIGHT_BLUE_TERRACOTTA, Items.BLUE_TERRACOTTA, Items.PURPLE_TERRACOTTA, Items.MAGENTA_TERRACOTTA, Items.PINK_TERRACOTTA };
    protected static final Item[] GLAZED_TERRACOTTA = new Item[]{ Items.WHITE_GLAZED_TERRACOTTA, Items.LIGHT_GRAY_GLAZED_TERRACOTTA, Items.GRAY_GLAZED_TERRACOTTA, Items.BLACK_GLAZED_TERRACOTTA, Items.BROWN_GLAZED_TERRACOTTA, Items.RED_GLAZED_TERRACOTTA, Items.ORANGE_GLAZED_TERRACOTTA, Items.YELLOW_GLAZED_TERRACOTTA, Items.LIME_GLAZED_TERRACOTTA, Items.GREEN_GLAZED_TERRACOTTA, Items.CYAN_GLAZED_TERRACOTTA, Items.LIGHT_BLUE_GLAZED_TERRACOTTA, Items.BLUE_GLAZED_TERRACOTTA, Items.PURPLE_GLAZED_TERRACOTTA, Items.MAGENTA_GLAZED_TERRACOTTA, Items.PINK_GLAZED_TERRACOTTA };
    protected static final Item[] CANDLES = new Item[]{ Items.WHITE_CANDLE, Items.LIGHT_GRAY_CANDLE, Items.GRAY_CANDLE, Items.BLACK_CANDLE, Items.BROWN_CANDLE, Items.RED_CANDLE, Items.ORANGE_CANDLE, Items.YELLOW_CANDLE, Items.LIME_CANDLE, Items.GREEN_CANDLE, Items.CYAN_CANDLE, Items.LIGHT_BLUE_CANDLE, Items.BLUE_CANDLE, Items.PURPLE_CANDLE, Items.MAGENTA_CANDLE, Items.PINK_CANDLE };
    protected static final Item[] BEDS = new Item[]{ Items.WHITE_BED, Items.LIGHT_GRAY_BED, Items.GRAY_BED, Items.BLACK_BED, Items.BROWN_BED, Items.RED_BED, Items.ORANGE_BED, Items.YELLOW_BED, Items.LIME_BED, Items.GREEN_BED, Items.CYAN_BED, Items.LIGHT_BLUE_BED, Items.BLUE_BED, Items.PURPLE_BED, Items.MAGENTA_BED, Items.PINK_BED };
    protected static final Item[] GLASS_PANES = new Item[] { Items.WHITE_STAINED_GLASS_PANE, Items.LIGHT_GRAY_STAINED_GLASS_PANE, Items.GRAY_STAINED_GLASS_PANE, Items.BLACK_STAINED_GLASS_PANE, Items.BROWN_STAINED_GLASS_PANE, Items.RED_STAINED_GLASS_PANE, Items.ORANGE_STAINED_GLASS_PANE, Items.YELLOW_STAINED_GLASS_PANE, Items.LIME_STAINED_GLASS_PANE, Items.GREEN_STAINED_GLASS_PANE, Items.CYAN_STAINED_GLASS_PANE, Items.LIGHT_BLUE_STAINED_GLASS_PANE, Items.BLUE_STAINED_GLASS_PANE, Items.PURPLE_STAINED_GLASS_PANE, Items.MAGENTA_STAINED_GLASS_PANE, Items.PINK_STAINED_GLASS_PANE };
    protected static final Item[] BANNERS = new Item[] { Items.WHITE_BANNER, Items.LIGHT_GRAY_BANNER, Items.GRAY_BANNER, Items.BLACK_BANNER, Items.BROWN_BANNER, Items.RED_BANNER, Items.ORANGE_BANNER, Items.YELLOW_BANNER, Items.LIME_BANNER, Items.GREEN_BANNER, Items.CYAN_BANNER, Items.LIGHT_BLUE_BANNER, Items.BLUE_BANNER, Items.PURPLE_BANNER, Items.MAGENTA_BANNER, Items.PINK_BANNER };

    protected static final Item[] TRAPDOORS = new Item[] { Items.OAK_TRAPDOOR, Items.SPRUCE_TRAPDOOR, Items.BIRCH_TRAPDOOR, Items.JUNGLE_TRAPDOOR, Items.ACACIA_TRAPDOOR, Items.DARK_OAK_TRAPDOOR, Items.MANGROVE_TRAPDOOR, Items.CHERRY_TRAPDOOR, Items.BAMBOO_TRAPDOOR, Items.CRIMSON_TRAPDOOR, Items.WARPED_TRAPDOOR };
    protected static final Item[] BOATS = new Item[] { Items.OAK_BOAT, Items.SPRUCE_BOAT, Items.BIRCH_BOAT, Items.JUNGLE_BOAT, Items.ACACIA_BOAT, Items.DARK_OAK_BOAT, Items.MANGROVE_BOAT, Items.CHERRY_BOAT };
    protected static final Item[] FENCES = new Item[] { Items.OAK_FENCE, Items.SPRUCE_FENCE, Items.BIRCH_FENCE, Items.JUNGLE_FENCE, Items.ACACIA_FENCE, Items.DARK_OAK_FENCE, Items.MANGROVE_FENCE, Items.CHERRY_FENCE, Items.BAMBOO_FENCE, Items.CRIMSON_FENCE, Items.WARPED_FENCE};
    protected static final Item[] GATES = new Item[] { Items.OAK_FENCE_GATE, Items.SPRUCE_FENCE_GATE, Items.BIRCH_FENCE_GATE, Items.JUNGLE_FENCE_GATE, Items.ACACIA_FENCE_GATE, Items.DARK_OAK_FENCE_GATE, Items.MANGROVE_FENCE_GATE, Items.CHERRY_FENCE_GATE, Items.BAMBOO_FENCE_GATE, Items.CRIMSON_FENCE_GATE, Items.WARPED_FENCE_GATE};





    protected static final int WHITE = 0;
    protected static final int LIGHT_GRAY = 1;
    protected static final int GRAY = 2;
    protected static final int BLACK = 3;
    protected static final int BROWN = 4;
    protected static final int RED = 5;
    protected static final int ORANGE = 6;
    protected static final int YELLOW = 7;
    protected static final int LIME = 8;
    protected static final int GREEN = 9;
    protected static final int CYAN = 10;
    protected static final int LIGHT_BLUE = 11;
    protected static final int BLUE = 12;
    protected static final int PURPLE = 13;
    protected static final int MAGENTA = 14;
    protected static final int PINK = 15;

    public static final int OAK = 0;
    public static final int SPRUCE = 1;
    public static final int BIRCH = 2;
    public static final int JUNGLE = 3;
    public static final int ACACIA = 4;
    public static final int DARK_OAK = 5;
    public static final int MANGROVE = 6;
    public static final int CHERRY = 7;
    public static final int BAMBOO = 8;
    public static final int CRIMSON = 9;
    public static final int WARPED = 10;


    public static final Map<VillagerType, int[]> colorsDistribute = Map.of(
            VillagerType.DESERT, new int[]{CYAN, GREEN, LIME},
            VillagerType.PLAINS, new int[]{WHITE, YELLOW, GREEN},
            VillagerType.SAVANNA, new int[]{RED, ORANGE, BROWN},
            VillagerType.SNOW, new int[]{WHITE, BLUE, BLACK},
            VillagerType.TAIGA, new int[]{PURPLE, BLUE, GRAY},
            VillagerType.SWAMP, new int[]{LIGHT_GRAY, MAGENTA, PINK},
            VillagerType.JUNGLE, new int[]{LIGHT_BLUE, LIGHT_GRAY, GREEN}
    );

    public static final Map<VillagerType, int[]> woodDistribute = Map.of(
            VillagerType.DESERT, new int[]{JUNGLE},
            VillagerType.JUNGLE, new int[]{JUNGLE, BAMBOO},
            VillagerType.PLAINS, new int[]{OAK, BIRCH},
            VillagerType.SAVANNA, new int[]{ACACIA, CHERRY},
            VillagerType.SNOW, new int[]{SPRUCE, DARK_OAK},
            VillagerType.SWAMP, new int[]{OAK, MANGROVE},
            VillagerType.TAIGA, new int[]{SPRUCE, DARK_OAK}
    );

    private final HashMap<Identifier, OfferFactory> objects = new HashMap<>();
    protected final VillagerProfession profession;

    protected VillagerTradeOffers(VillagerProfession profession) {
        this.profession = profession;
    }

    public HashMap<Identifier, OfferFactory> getObjects() {
        return objects;
    }

    public abstract void fill();


    protected void sellBook(String id, int level, Enchantment... enchantments) {
        sellBook(id, null, level, enchantments);
    }

    protected void sellBook(String id, VillagerType type, int level, Enchantment... enchantments) {
        FieldVillagerData data = new FieldVillagerData(type, profession, level);
        putTradeOffer(id,
                new ItemContainer[]{item(1)}, // todo add price
                new ItemContainer[]{ItemContainer.of(new ItemStack(Items.BOOK))},
                new ItemContainer[]{ItemContainer.of(new ItemStack(Items.ENCHANTED_BOOK), ItemFunctions.ofProgression(enchantments))},
                12, 0.05f, experienceByLevelSell(level), data);
    }

    protected void buyForEmerald(String id, int level, int maxUses, ItemContainer... container) {
        buyForEmerald(id, level, maxUses, 1, container);
    }

    protected void sellForEmerald(String id, int level, int maxUses, ItemContainer... container) {
        sellForEmerald(id, level, maxUses, 1, container);
    }

    protected void buyForEmerald(String id, int level, int maxUses, int emeralds, ItemContainer... container) {
        buyForEmerald(id, null, level, maxUses, emeralds, container);
    }

    protected void sellForEmerald(String id, int level, int maxUses, int emeralds, ItemContainer... container) {
        sellForEmerald(id, null, level, maxUses, emeralds, container);
    }

    protected void buyForEmerald(String id, VillagerType type, int level, int maxUses, ItemContainer... container) {
        buyForEmerald(id, type, level, maxUses, 1, container);
    }

    protected void sellForEmerald(String id, VillagerType type, int level, int maxUses, ItemContainer... container) {
        sellForEmerald(id, type, level, maxUses, 1, container);
    }

    protected void buyForEmerald(String id, VillagerType type, int level, int maxUses, int emeralds, ItemContainer... container) {
        FieldVillagerData data = new FieldVillagerData(type, profession, level);
        putTradeOffer(id, container, ItemContainer.EMPTY, new ItemContainer[]{item(emeralds)}, maxUses,
                0.05f, experienceByLevelBuy(level), data);
    }

    protected void sellForEmerald(String id, VillagerType type, int level, int maxUses, int emeralds, ItemContainer... container) {
        FieldVillagerData data = new FieldVillagerData(type, profession, level);
        putTradeOffer(id, new ItemContainer[]{item(emeralds)}, ItemContainer.EMPTY, container, maxUses,
                0.05f, experienceByLevelSell(level), data);
    }

    protected void buyForEmerald(String id, VillagerType type, int level, int maxUses, int emeralds, Condition.Data<?> condition, ItemContainer... container) {
        FieldVillagerData data = new FieldVillagerData(type, profession, level);
        putTradeOffer(id, container, ItemContainer.EMPTY, new ItemContainer[]{item(emeralds)}, maxUses,
                0.05f, experienceByLevelBuy(level), data, condition);
    }

    protected void sellForEmerald(String id, VillagerType type, int level, int maxUses, int emeralds, Condition.Data<?> condition, ItemContainer... container) {
        FieldVillagerData data = new FieldVillagerData(type, profession, level);
        putTradeOffer(id, new ItemContainer[]{item(emeralds)}, ItemContainer.EMPTY, container, maxUses,
                0.05f, experienceByLevelSell(level), data, condition);
    }

    protected void buyDistributeForBiomes(String id, int level, int emeralds, int price,
                                        int maxUses, Item[] items, Map<VillagerType, int[]> typeMap) {

        typeMap.forEach((type, color) ->
                putTradeOffer(id, Arrays.stream(color).mapToObj(i -> item(items[i], price)).toArray(ItemContainer[]::new),
                ItemContainer.EMPTY,
                new ItemContainer[]{item(emeralds)},
                maxUses, 0.05f, experienceByLevelBuy(level),
                new FieldVillagerData(type, profession, level)));
    }

    protected void buyDistributeForBiomes(String id, int level, int emeralds, int price,
                                          int maxUses, Item[] items, List<Pair<OfferCondition.SearchType, Item[]>> offerSearchItems, Map<VillagerType, int[]> typeMap) {

        typeMap.forEach((type, color) ->
                putTradeOffer(id, itemContainerFromOfferCondition(price, items, color, offerSearchItems),
                ItemContainer.EMPTY,
                new ItemContainer[]{item(emeralds)},
                maxUses, 0.05f, experienceByLevelBuy(level),
                new FieldVillagerData(type, profession, level)));
    }

    protected void sellDistributeForBiomes(String id, int level, int emeralds, int price,
                                         int maxUses, Item[] items, Map<VillagerType, int[]> typeMap) {
        typeMap.forEach((type, color) ->
                putTradeOffer(id, new ItemContainer[]{item(emeralds)},
                ItemContainer.EMPTY,
                Arrays.stream(color).mapToObj(i -> item(items[i], price)).toArray(ItemContainer[]::new),
                maxUses, 0.05f, experienceByLevelSell(level),
                new FieldVillagerData(type, profession, level)));
    }

    protected void sellDistributeForBiomes(String id, int level, int emeralds, int price,
                                           int maxUses, Item[] items, List<Pair<OfferCondition.SearchType, Item[]>> offerSearchItems, Map<VillagerType, int[]> typeMap) {
        typeMap.forEach((type, color) ->
                putTradeOffer(id, new ItemContainer[]{item(emeralds)},
                ItemContainer.EMPTY,
                        itemContainerFromOfferCondition(price, items, color, offerSearchItems),
                maxUses, 0.05f, experienceByLevelSell(level),
                new FieldVillagerData(type, profession, level))
        );
    }

    private static ItemContainer @NotNull [] itemContainerFromOfferCondition(int price, Item[] items, int[] color, List<Pair<OfferCondition.SearchType, Item[]>> offerSearchItems) {
        ItemContainer[] result = new ItemContainer[color.length];
        for (int i = 0; i < color.length; i++) {
            result[i] = item(items[color[i]], price, collapseOfferConditions(color[i], offerSearchItems));
        }
        return result;
    }

    private static Condition.Data<?> collapseOfferConditions(int color, List<Pair<OfferCondition.SearchType, Item[]>> offerSearchItems) {
        if (offerSearchItems.size() == 1) {
            Pair<OfferCondition.SearchType, Item[]> searchTypePair = offerSearchItems.get(0);
            return Conditions.of(Conditions.ITEM_OFFER_CONDITION, searchTypePair.getLeft(), searchTypePair.getRight()[color]);
        }
        return Conditions.or(offerSearchItems.stream().map(searchTypePair -> Conditions.of(Conditions.ITEM_OFFER_CONDITION, searchTypePair.getLeft(), searchTypePair.getRight()[color])).toArray(Condition.Data[]::new));
    }


    protected <T> Map<VillagerType, T[]> villagerTypeMap(T[] plains, T[] jungle, T[] taiga, T[] savanna, T[] desert, T[] swamp, T[] snow) {
        return Map.of(
                VillagerType.PLAINS, plains,
                VillagerType.JUNGLE, jungle,
                VillagerType.TAIGA, taiga,
                VillagerType.SAVANNA, savanna,
                VillagerType.DESERT, desert,
                VillagerType.SWAMP, swamp,
                VillagerType.SNOW, snow
        );
    }

    protected void buyDistributeForBiomes(String id, int level, int emeralds, int price,
                                        int maxUses, Map<VillagerType, Item[]> typeMap) {

        typeMap.forEach((type, items) ->
                putTradeOffer(id, Arrays.stream(items).map(i -> item(i, price)).toArray(ItemContainer[]::new),
                ItemContainer.EMPTY,
                new ItemContainer[]{item(emeralds)},
                maxUses, 0.05f, experienceByLevelBuy(level),
                new FieldVillagerData(type, profession, level)));
    }

    protected void sellDistributeForBiomes(String id, int level, int emeralds, int price,
                                         int maxUses, Map<VillagerType, Item[]> typeMap) {
        typeMap.forEach((type, items) ->
                putTradeOffer(id, new ItemContainer[]{item(emeralds)},
                ItemContainer.EMPTY,
                Arrays.stream(items).map(i -> item(i, price)).toArray(ItemContainer[]::new),
                maxUses, 0.05f, experienceByLevelBuy(level),
                new FieldVillagerData(type, profession, level)));
    }

    protected void putTradeOffer(String id, @NotNull ItemContainer[] item1, @NotNull ItemContainer[] item2, @NotNull ItemContainer[] sell,
                               int maxUses, float priceMultiplier, int experience, FieldVillagerData data) {
        if (data.type() != null) id = data.type() + "/" + id;
        if (data.profession() != null) id = data.profession() + "/" + id;
        OfferFactory factory = new OfferFactory(item1, item2, sell,
                maxUses, true, priceMultiplier, experience, Conditions.of(data));
        objects.put(new Identifier(Values.MOD_ID, id), factory);
    }

    protected void putTradeOffer(String id, @NotNull ItemContainer[] item1, @NotNull ItemContainer[] item2, @NotNull ItemContainer[] sell,
                                 int maxUses, float priceMultiplier, int experience, FieldVillagerData data, Condition.Data<?> additionalCondition) {
        if (data.type() != null) id = data.type() + "/" + id;
        if (data.profession() != null) id = data.profession() + "/" + id;
        OfferFactory factory = new OfferFactory(item1, item2, sell,
                maxUses, true, priceMultiplier, experience, Conditions.and(Conditions.of(data), additionalCondition));
        objects.put(new Identifier(Values.MOD_ID, id), factory);
    }






    protected static @NotNull ItemContainer[] item() {
        return ItemContainer.EMPTY;
    }

    protected static @NotNull ItemContainer item(Item item) {
        return ItemContainer.of(new ItemStack(item));
    }

    protected static @NotNull ItemContainer item(Item item, int count) {
        return ItemContainer.of(new ItemStack(item, count));
    }

    public static @NotNull ItemContainer @NotNull [] item(Item item0, int count0, Item item1, int count1) {
        return ItemContainer.of(new ItemStack(item0, count0), new ItemStack(item1, count1));
    }

    public static @NotNull ItemContainer @NotNull [] item(Item item0, int count0, Item item1, int count1, Item item2, int count2) {
        return ItemContainer.of(new ItemStack(item0, count0), new ItemStack(item1, count1), new ItemStack(item2, count2));
    }

    protected static @NotNull ItemContainer item(int price) {
        return ItemContainer.of(new ItemStack(Items.EMERALD, price));
    }

    protected static @NotNull ItemContainer item(Item item, ItemFunction.Data<?>... functions) {
        return ItemContainer.of(new ItemStack(item), functions);
    }

    protected static @NotNull ItemContainer item(Item item, int count, ItemFunction.Data<?>... functions) {
        return ItemContainer.of(new ItemStack(item, count), functions);
    }

    protected static @NotNull ItemContainer item(Item item, Condition.Data<?> condition) {
        return ItemContainer.of(new ItemStack(item), condition);
    }

    protected static @NotNull ItemContainer item(Item item, int count, Condition.Data<?> condition) {
        return ItemContainer.of(new ItemStack(item, count), condition);
    }

    public static int experienceByLevelBuy(int level) {
        if (level == 0) return 1;
        return MathHelper.clamp((level - 1) * 10, 2, 50);
    }

    public static int experienceByLevelSell(int level) {
        if (level == 0) return 1;
        return MathHelper.clamp((level - 1) * 5, 1, 50);
    }


}
