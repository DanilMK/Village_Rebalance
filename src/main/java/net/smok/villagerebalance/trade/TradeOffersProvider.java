package net.smok.villagerebalance.trade;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import net.smok.villagerebalance.Values;
import net.smok.villagerebalance.trade.conditions.*;
import net.smok.villagerebalance.trade.functions.ItemFunction;
import net.smok.villagerebalance.trade.functions.ItemFunctions;
import net.smok.villagerebalance.utility.MapData;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TradeOffersProvider {


    public static final String DIRECTORY = "village";



    private static final Item[] DYES = new Item[]{ Items.WHITE_DYE, Items.LIGHT_GRAY_DYE, Items.GRAY_DYE, Items.BLACK_DYE, Items.BROWN_DYE, Items.RED_DYE, Items.ORANGE_DYE, Items.YELLOW_DYE, Items.LIME_DYE, Items.GREEN_DYE, Items.CYAN_DYE, Items.LIGHT_BLUE_DYE, Items.BLUE_DYE, Items.PURPLE_DYE, Items.MAGENTA_DYE, Items.PINK_DYE };
    private static final Item[] WOOLS = new Item[]{ Items.WHITE_WOOL, Items.LIGHT_GRAY_WOOL, Items.GRAY_WOOL, Items.BLACK_WOOL, Items.BROWN_WOOL, Items.RED_WOOL, Items.ORANGE_WOOL, Items.YELLOW_WOOL, Items.LIME_WOOL, Items.GREEN_WOOL, Items.CYAN_WOOL, Items.LIGHT_BLUE_WOOL, Items.BLUE_WOOL, Items.PURPLE_WOOL, Items.MAGENTA_WOOL, Items.PINK_WOOL };
    private static final Item[] CARPETS = new Item[]{ Items.WHITE_CARPET, Items.LIGHT_GRAY_CARPET, Items.GRAY_CARPET, Items.BLACK_CARPET, Items.BROWN_CARPET, Items.RED_CARPET, Items.ORANGE_CARPET, Items.YELLOW_CARPET, Items.LIME_CARPET, Items.GREEN_CARPET, Items.CYAN_CARPET, Items.LIGHT_BLUE_CARPET, Items.BLUE_CARPET, Items.PURPLE_CARPET, Items.MAGENTA_CARPET, Items.PINK_CARPET };
    private static final Item[] TERRACOTTA = new Item[]{ Items.WHITE_TERRACOTTA, Items.LIGHT_GRAY_TERRACOTTA, Items.GRAY_TERRACOTTA, Items.BLACK_TERRACOTTA, Items.BROWN_TERRACOTTA, Items.RED_TERRACOTTA, Items.ORANGE_TERRACOTTA, Items.YELLOW_TERRACOTTA, Items.LIME_TERRACOTTA, Items.GREEN_TERRACOTTA, Items.CYAN_TERRACOTTA, Items.LIGHT_BLUE_TERRACOTTA, Items.BLUE_TERRACOTTA, Items.PURPLE_TERRACOTTA, Items.MAGENTA_TERRACOTTA, Items.PINK_TERRACOTTA };
    private static final Item[] GLAZED_TERRACOTTA = new Item[]{ Items.WHITE_GLAZED_TERRACOTTA, Items.LIGHT_GRAY_GLAZED_TERRACOTTA, Items.GRAY_GLAZED_TERRACOTTA, Items.BLACK_GLAZED_TERRACOTTA, Items.BROWN_GLAZED_TERRACOTTA, Items.RED_GLAZED_TERRACOTTA, Items.ORANGE_GLAZED_TERRACOTTA, Items.YELLOW_GLAZED_TERRACOTTA, Items.LIME_GLAZED_TERRACOTTA, Items.GREEN_GLAZED_TERRACOTTA, Items.CYAN_GLAZED_TERRACOTTA, Items.LIGHT_BLUE_GLAZED_TERRACOTTA, Items.BLUE_GLAZED_TERRACOTTA, Items.PURPLE_GLAZED_TERRACOTTA, Items.MAGENTA_GLAZED_TERRACOTTA, Items.PINK_GLAZED_TERRACOTTA };
    private static final Item[] CANDLES = new Item[]{ Items.WHITE_CANDLE, Items.LIGHT_GRAY_CANDLE, Items.GRAY_CANDLE, Items.BLACK_CANDLE, Items.BROWN_CANDLE, Items.RED_CANDLE, Items.ORANGE_CANDLE, Items.YELLOW_CANDLE, Items.LIME_CANDLE, Items.GREEN_CANDLE, Items.CYAN_CANDLE, Items.LIGHT_BLUE_CANDLE, Items.BLUE_CANDLE, Items.PURPLE_CANDLE, Items.MAGENTA_CANDLE, Items.PINK_CANDLE };
    private static final Item[] BEDS = new Item[]{ Items.WHITE_BED, Items.LIGHT_GRAY_BED, Items.GRAY_BED, Items.BLACK_BED, Items.BROWN_BED, Items.RED_BED, Items.ORANGE_BED, Items.YELLOW_BED, Items.LIME_BED, Items.GREEN_BED, Items.CYAN_BED, Items.LIGHT_BLUE_BED, Items.BLUE_BED, Items.PURPLE_BED, Items.MAGENTA_BED, Items.PINK_BED };
    private static final Item[] GLASS_PANES = new Item[] { Items.WHITE_STAINED_GLASS_PANE, Items.LIGHT_GRAY_STAINED_GLASS_PANE, Items.GRAY_STAINED_GLASS_PANE, Items.BLACK_STAINED_GLASS_PANE, Items.BROWN_STAINED_GLASS_PANE, Items.RED_STAINED_GLASS_PANE, Items.ORANGE_STAINED_GLASS_PANE, Items.YELLOW_STAINED_GLASS_PANE, Items.LIME_STAINED_GLASS_PANE, Items.GREEN_STAINED_GLASS_PANE, Items.CYAN_STAINED_GLASS_PANE, Items.LIGHT_BLUE_STAINED_GLASS_PANE, Items.BLUE_STAINED_GLASS_PANE, Items.PURPLE_STAINED_GLASS_PANE, Items.MAGENTA_STAINED_GLASS_PANE, Items.PINK_STAINED_GLASS_PANE };
    private static final Item[] BANNERS = new Item[] { Items.WHITE_BANNER, Items.LIGHT_GRAY_BANNER, Items.GRAY_BANNER, Items.BLACK_BANNER, Items.BROWN_BANNER, Items.RED_BANNER, Items.ORANGE_BANNER, Items.YELLOW_BANNER, Items.LIME_BANNER, Items.GREEN_BANNER, Items.CYAN_BANNER, Items.LIGHT_BLUE_BANNER, Items.BLUE_BANNER, Items.PURPLE_BANNER, Items.MAGENTA_BANNER, Items.PINK_BANNER };

    private static final Item[] TRAPDOORS = new Item[] { Items.OAK_TRAPDOOR, Items.SPRUCE_TRAPDOOR, Items.BIRCH_TRAPDOOR, Items.JUNGLE_TRAPDOOR, Items.ACACIA_TRAPDOOR, Items.DARK_OAK_TRAPDOOR, Items.MANGROVE_TRAPDOOR, Items.CHERRY_TRAPDOOR, Items.BAMBOO_TRAPDOOR, Items.CRIMSON_TRAPDOOR, Items.WARPED_TRAPDOOR, Items.IRON_TRAPDOOR };
    private static final Item[] BOATS = new Item[] { Items.OAK_BOAT, Items.SPRUCE_BOAT, Items.BIRCH_BOAT, Items.JUNGLE_BOAT, Items.ACACIA_BOAT, Items.DARK_OAK_BOAT, Items.MANGROVE_BOAT, Items.CHERRY_BOAT };




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
    public static final int IRON = 11;



    private final HashMap<Identifier, OfferFactory> objects = new HashMap<>();
    private VillagerProfession profession;

    public HashMap<Identifier, OfferFactory> getObjects() {
        return objects;
    }

    public void fill() {
        fillMason();
        fillFarmer();
        fillCartographer();
        fillButcher();
        fillCleric();
        fillFisherman();


    }


    private void fillFletcher() {

    }

    private void fillFisherman() {
        profession = VillagerProfession.FISHERMAN;

        buyForEmerald("string", 1, 16, item(Items.STRING, 20));
        buyForEmerald("lily_pad", 1, 16, item(Items.LILY_PAD, 7));

        sellForEmerald("cod_bucket", 1, 12, 3, item(Items.COD_BUCKET));
        
        buyForEmerald("raw_cod", 2, 12, item(Items.COD, 15));
        sellForEmerald("cooked_cod", 2, 12, item(Items.COOKED_COD, 6));
        
        buyForEmerald("raw_salmon", 3, 12, item(Items.SALMON, 13));
        sellForEmerald("cooked_salmon", 3, 12, item(Items.COOKED_SALMON, 6));
        
        buyForEmerald("tropical_fish", 4, 12, item(Items.TROPICAL_FISH, 6));
        sellForEmerald("campfire", 4, 12,2, item(Items.CAMPFIRE));

        buyForEmerald("pufferfish", 5, 12, item(Items.PUFFERFISH, 4));
        sellDistributeForBiomes("boats", 5, 1, 1, 12, BOATS, Map.of(
                VillagerType.SWAMP, new int[] {DARK_OAK},
                VillagerType.SAVANNA, new int[] {ACACIA},
                VillagerType.JUNGLE, new int[] {JUNGLE},
                VillagerType.DESERT, new int[] {JUNGLE},
                VillagerType.SNOW, new int[] {SPRUCE},
                VillagerType.TAIGA, new int[] {SPRUCE},
                VillagerType.PLAINS, new int[] {OAK, BIRCH}
        ));


        
        sellForEmerald("fishing_rod_1", 1, 3, 8,
                item(Items.FISHING_ROD, ItemFunctions.of(Enchantments.LURE, 1, 3, true, true)),
                item(Items.FISHING_ROD, ItemFunctions.of(Enchantments.LUCK_OF_THE_SEA, 1, 3, true, true))
        );

        sellForEmerald("fishing_rod_2", 2, 3, 18,
                item(Items.FISHING_ROD, ItemFunctions.of(Enchantments.LURE, 2, 3, true, true)),
                item(Items.FISHING_ROD, ItemFunctions.of(Enchantments.LUCK_OF_THE_SEA, 2, 3, true, true))
        );

        sellForEmerald("fishing_rod_3", 3, 3, 25,
                item(Items.FISHING_ROD, ItemFunctions.of(Enchantments.LURE, 3, 3, true, true)),
                item(Items.FISHING_ROD, ItemFunctions.of(Enchantments.LUCK_OF_THE_SEA, 3, 3, true, true))
        );

        sellForEmerald("fishing_book_3", 5, 12, 25,
                item(Items.FISHING_ROD, ItemFunctions.of(Enchantments.LURE, 3, 3, true, true)),
                item(Items.FISHING_ROD, ItemFunctions.of(Enchantments.LUCK_OF_THE_SEA, 3, 3, true, true))
        );


    }

    private void fillCleric() {
        profession = VillagerProfession.CLERIC;

        buyForEmerald("rotten_flesh", 1, 16, item(Items.ROTTEN_FLESH, 32));
        sellForEmerald("redstone_dust", 1, 16, item(Items.REDSTONE, 2));
        buyForEmerald("gold_ingot", 2, 12, item(Items.GOLD_INGOT, 3));
        sellForEmerald("lapis_lazuli", 2, 12, item(Items.LAPIS_LAZULI));
        buyForEmerald("rabbit_foot", 3, 12, item(Items.RABBIT_FOOT, 2));
        sellForEmerald("glowstone", 3, 12, item(Items.GLOWSTONE));
        buyForEmerald("glass_bottle", 4, 12, item(Items.GLASS_BOTTLE, 9));
        sellForEmerald("potions", 4, 12, 3,
                item(Items.POTION, ItemFunctions.of(Potions.REGENERATION)),
                item(Items.POTION, ItemFunctions.of(Potions.HEALING))
        );

        sellForEmerald("ender_pearl", 4, 12, 5, item(Items.ENDER_PEARL));
        buyForEmerald("nether_wart", 5, 12, item(Items.NETHER_WART, 22));


    }

    private void fillButcher() {
        profession = VillagerProfession.BUTCHER;

        buyForEmerald("raw_soft_meat", 1, 16,
                item(Items.CHICKEN, 14, Conditions.ofBuy(Items.COOKED_CHICKEN)),
                item(Items.PORKCHOP, 7, Conditions.ofBuy(Items.COOKED_PORKCHOP)),
                item(Items.RABBIT, 4, Conditions.ofBuy(Items.COOKED_RABBIT)));

        sellForEmerald("cooked_soft_meat", 1, 16,
                item(Items.COOKED_CHICKEN, 8, Conditions.ofBuy(Items.CHICKEN)),
                item(Items.COOKED_PORKCHOP, 5, Conditions.ofBuy(Items.PORKCHOP)),
                item(Items.RABBIT_STEW, 1, Conditions.ofBuy(Items.RABBIT)));

        buyForEmerald("charcoal", 2, 16, item(Items.CHARCOAL, 20));

        buyForEmerald("raw_hard_meat", 3, 16,
                item(Items.BEEF, 10, Conditions.ofBuy(Items.COOKED_BEEF)),
                item(Items.MUTTON, 7, Conditions.ofBuy(Items.COOKED_MUTTON))
        );

        sellForEmerald("cooked_hard_meat", 3, 16,
                item(Items.COOKED_BEEF, 10, Conditions.ofBuy(Items.BEEF)),
                item(Items.COOKED_MUTTON, 7, Conditions.ofBuy(Items.MUTTON))
        );

        buyForEmerald("dried_kelp", 4, 16, item(Items.DRIED_KELP_BLOCK, 10));

        sellDistributeForBiomes("trapdoors", 4, 3, 3, 16, TRAPDOORS, Map.of(
                VillagerType.DESERT, new int[] {IRON, JUNGLE},
                VillagerType.JUNGLE, new int[] {JUNGLE, BAMBOO},
                VillagerType.PLAINS, new int[] {OAK, BIRCH},
                VillagerType.SAVANNA, new int[] {ACACIA, CHERRY},
                VillagerType.SNOW, new int[] {SPRUCE, DARK_OAK},
                VillagerType.SWAMP, new int[] {OAK, MANGROVE},
                VillagerType.TAIGA, new int[] {SPRUCE, DARK_OAK}
        ));

        sellForEmerald("potions", 5, 12, 3,
                item(Items.POTION, ItemFunctions.of(Potions.LEAPING)),
                item(Items.POTION, ItemFunctions.of(Potions.SWIFTNESS))
        );

    }

    private void fillCartographer() {
        profession = VillagerProfession.CARTOGRAPHER;

        buyForEmerald("paper", 1, 12, item(Items.PAPER));
        sellForEmerald("empty_map", 1, 12, item(Items.MAP));

        buyForEmerald("colored_glass_pane", 2, 12,
                Arrays.stream(GLASS_PANES).map(TradeOffersProvider::item).toArray(ItemContainer[]::new)
        );


        putByData("ocean_explorer_map",
                new ItemContainer[]{item(Items.EMERALD, 13)},
                new ItemContainer[]{item(Items.COMPASS)},
                new ItemContainer[]{item(Items.FILLED_MAP, ItemFunctions.of(MapData.OCEAN))},
                4, 0.05f, experienceByLevel(2), VillagerData.of(null, VillagerProfession.CARTOGRAPHER, 2));

        buyForEmerald("compass", 3, 12, item(Items.COMPASS));

        putByData("woodland_explorer_map",
                new ItemContainer[]{item(Items.EMERALD, 14)},
                new ItemContainer[]{item(Items.COMPASS)},
                new ItemContainer[]{item(Items.FILLED_MAP, ItemFunctions.of(MapData.WOODLAND))},
                4, 0.05f, experienceByLevel(2), VillagerData.of(null, VillagerProfession.CARTOGRAPHER, 2));


        buyForEmerald("ink_sac", 4, 12, item(Items.INK_SAC, 5));


        sellDistributeForBiomes("banners", 4, 3, 1, 12, BANNERS, Map.of(
                VillagerType.DESERT, new int[] {CYAN, GREEN, LIME},
                VillagerType.PLAINS, new int[] {WHITE, YELLOW, GREEN},
                VillagerType.SAVANNA, new int[] {RED, ORANGE, BROWN},
                VillagerType.SNOW, new int[] {WHITE, BLUE, BLACK},
                VillagerType.TAIGA, new int[] {PURPLE, BLUE, GRAY},
                VillagerType.SWAMP, new int[] {LIGHT_GRAY, MAGENTA, PINK},
                VillagerType.JUNGLE, new int[] {LIGHT_BLUE, LIGHT_GRAY, GREEN}
        ));

        buyForEmerald("glow_ink_sac", 5, 12, 2, item(Items.GLOW_INK_SAC, 5));

        sellForEmerald("globe_banner_pattern", 5, 12, 8, item(Items.GLOBE_BANNER_PATTERN));


    }


    private void fillFarmer() {
        profession = VillagerProfession.FARMER;
        buyForEmerald("wheat",
                1, 12, item(Items.WHEAT, 20));
        buyForEmerald("potato",
                1, 12, item(Items.POTATO, 26));
        buyForEmerald("carrot",
                1, 12, item(Items.CARROT, 22));
        buyForEmerald("beetroot",
                1, 12, item(Items.BEETROOT, 15));

        sellForEmerald("bread",
                1, 12, item(Items.BREAD, 6));
        sellForEmerald("baked_potato",
                1, 12, item(Items.BAKED_POTATO, 12));


        buyForEmerald("pumpkin",
                2, 12, item(Items.PUMPKIN, 6));

        buyForEmerald("any_food",
                2, 12,
                item(Items.CACTUS, 26, Conditions.of(VillagerData.of(VillagerType.DESERT))),
                item(Items.COCOA_BEANS, 12, Conditions.of(VillagerData.of(VillagerType.JUNGLE))),
                item(Items.GLOW_BERRIES, 12, Conditions.of(VillagerData.of(VillagerType.SNOW))),
                item(Items.RED_MUSHROOM, 16, Conditions.of(VillagerData.of(VillagerType.SWAMP))),
                item(Items.BROWN_MUSHROOM, 16, Conditions.of(VillagerData.of(VillagerType.SWAMP))),
                item(Items.SWEET_BERRIES, 12, Conditions.of(VillagerData.of(VillagerType.TAIGA)))
        );

        sellForEmerald("pumpkin_pie",
                2, 12, item(Items.PUMPKIN_PIE, 4));
        sellForEmerald("apple",
                2, 12, item(Items.APPLE, 4));

        buyForEmerald("melon",
                3, 12, item(Items.MELON, 4));
        buyForEmerald("honey",
                3, 12, item(Items.HONEY_BOTTLE, 12));

        sellForEmerald("wax",
                3, 1, item(Items.HONEYCOMB, 8));
        sellForEmerald("cookie", 3, 1,
                item(Items.COOKIE, 18));

        buyForEmerald("bowl",
                4, 1, item(Items.BOWL, 25));

        sellForEmerald("suspicious_stew", 4, 12,
                item(Items.SUSPICIOUS_STEW, ItemFunctions.of(StatusEffects.NIGHT_VISION, 100)),
                item(Items.SUSPICIOUS_STEW, ItemFunctions.of(StatusEffects.JUMP_BOOST, 160)),
                item(Items.SUSPICIOUS_STEW, ItemFunctions.of(StatusEffects.WEAKNESS, 140)),
                item(Items.SUSPICIOUS_STEW, ItemFunctions.of(StatusEffects.BLINDNESS, 120)),
                item(Items.SUSPICIOUS_STEW, ItemFunctions.of(StatusEffects.POISON, 280)),
                item(Items.SUSPICIOUS_STEW, ItemFunctions.of(StatusEffects.SATURATION, 7))
                );

        sellForEmerald("cake", 4, 12, item(Items.CAKE));

        sellForEmerald("golden_carrot", 5, 12, 3, item(Items.GOLDEN_CARROT, 3));
        sellForEmerald("melon_slice", 5, 12, 4, item(Items.GLISTERING_MELON_SLICE, 3));
    }


    private void fillMason() {

        profession = VillagerProfession.MASON;

        buyForEmerald("clay_ball",
                1, 16, item(Items.CLAY_BALL, 10));

        sellForEmerald("brick",
                1, 16, item(Items.BRICK, 10));

        buyForEmerald("tri_stones",
                2, 16,
                item(Items.ANDESITE, 16, Items.DIORITE, 16, Items.GRANITE, 16));

        sellForEmerald("tri_polished_stones",
                2, 16,
                item(Items.POLISHED_ANDESITE, 4, Items.POLISHED_DIORITE, 4, Items.POLISHED_GRANITE, 4));

        buyForEmerald("any_stones", 2, 16,
                ItemContainer.of(new ItemStack(Items.SANDSTONE, 20), Conditions.of(VillagerData.of(VillagerType.DESERT))),
                ItemContainer.of(new ItemStack(Items.MOSSY_COBBLESTONE, 20), Conditions.of(VillagerData.of(VillagerType.JUNGLE))),
                ItemContainer.of(new ItemStack(Items.STONE, 20), Conditions.of(VillagerData.of(VillagerType.PLAINS))),
                ItemContainer.of(new ItemStack(Items.RED_SANDSTONE, 20), Conditions.of(VillagerData.of(VillagerType.SAVANNA))),
                ItemContainer.of(new ItemStack(Items.DEEPSLATE, 20), Conditions.of(VillagerData.of(VillagerType.SNOW))),
                ItemContainer.of(new ItemStack(Items.MUD, 20), Conditions.of(VillagerData.of(VillagerType.SWAMP))),
                ItemContainer.of(new ItemStack(Items.TUFF, 20), Conditions.of(VillagerData.of(VillagerType.TAIGA)))
        );

        sellForEmerald("any_bricks", 2, 16,
                ItemContainer.of(new ItemStack(Items.CUT_SANDSTONE, 4), Conditions.of(VillagerData.of(VillagerType.DESERT))),
                ItemContainer.of(new ItemStack(Items.MOSSY_STONE_BRICKS, 4), Conditions.of(VillagerData.of(VillagerType.JUNGLE))),
                ItemContainer.of(new ItemStack(Items.STONE_BRICKS, 4), Conditions.of(VillagerData.of(VillagerType.PLAINS))),
                ItemContainer.of(new ItemStack(Items.CUT_RED_SANDSTONE, 4), Conditions.of(VillagerData.of(VillagerType.SAVANNA))),
                ItemContainer.of(new ItemStack(Items.DEEPSLATE_BRICKS, 4), Conditions.of(VillagerData.of(VillagerType.SNOW))),
                ItemContainer.of(new ItemStack(Items.MUD_BRICKS, 4), Conditions.of(VillagerData.of(VillagerType.SWAMP))),
                ItemContainer.of(new ItemStack(Items.STONE_BRICKS, 4), Conditions.of(VillagerData.of(VillagerType.TAIGA)))
        );

        buyForEmerald("dripstone", 3, 16,
                item(Items.POINTED_DRIPSTONE, 16));

        sellForEmerald("dripstone_block", 3, 16,
                item(Items.DRIPSTONE_BLOCK, 4));

        buyDistributeForBiomes("dyes", 3, 1, 12, 16, DYES, Map.of(
                VillagerType.DESERT, new int[] {ORANGE, BLUE},
                VillagerType.JUNGLE, new int[] {LIME, GREEN, CYAN, LIGHT_BLUE},
                VillagerType.PLAINS, new int[] {LIME, GREEN, PINK},
                VillagerType.SAVANNA, new int[] {RED, YELLOW, MAGENTA},
                VillagerType.SNOW, new int[] {WHITE, LIGHT_GRAY, GRAY, CYAN},
                VillagerType.SWAMP, new int[] {GRAY, BLUE, BLUE},
                VillagerType.TAIGA, new int[] {BLACK, BROWN, LIGHT_BLUE, PURPLE}
        ));

        sellDistributeForBiomes("terracotta", 3, 1, 12, 16, TERRACOTTA, Map.of(
                VillagerType.DESERT, new int[] {ORANGE, BLUE},
                VillagerType.JUNGLE, new int[] {LIME, GREEN, CYAN, LIGHT_BLUE},
                VillagerType.PLAINS, new int[] {LIME, GREEN, PINK},
                VillagerType.SAVANNA, new int[] {RED, YELLOW, MAGENTA},
                VillagerType.SNOW, new int[] {WHITE, LIGHT_GRAY, GRAY, CYAN},
                VillagerType.SWAMP, new int[] {GRAY, BLUE, BLUE},
                VillagerType.TAIGA, new int[] {BLACK, BROWN, LIGHT_BLUE, PURPLE}
        ));

        sellDistributeForBiomes("glazed_terracotta", 4, 1, 12, 16, GLAZED_TERRACOTTA, Map.of(
                VillagerType.DESERT, new int[] {ORANGE, BLUE},
                VillagerType.JUNGLE, new int[] {LIME, GREEN, CYAN, LIGHT_BLUE},
                VillagerType.PLAINS, new int[] {LIME, GREEN, PINK},
                VillagerType.SAVANNA, new int[] {RED, YELLOW, MAGENTA},
                VillagerType.SNOW, new int[] {WHITE, LIGHT_GRAY, GRAY, CYAN},
                VillagerType.SWAMP, new int[] {GRAY, BLUE, BLUE},
                VillagerType.TAIGA, new int[] {BLACK, BROWN, LIGHT_BLUE, PURPLE}
        ));

        buyForEmerald("nether_quartz",
                1, 16, item(Items.QUARTZ, 12));

        sellForEmerald("quartz_block",
                1, 16, item(Items.QUARTZ_BLOCK));
        sellForEmerald("quartz_pillar",
                1, 16, item(Items.QUARTZ_PILLAR));

    }


    private void buyForEmerald(String id, int level, int maxUses,
                               ItemContainer... container) {
        VillagerData data = new VillagerData(null, profession, level);
        putByData(id, container, ItemContainer.EMPTY, new ItemContainer[]{item(1)}, maxUses,
                0.05f, experienceByLevel(level), data);
    }

    private void sellForEmerald(String id, int level, int maxUses,
                                ItemContainer... container) {
        VillagerData data = new VillagerData(null, profession, level);
        putByData(id, new ItemContainer[]{item(1)}, ItemContainer.EMPTY, container, maxUses,
                0.05f, experienceByLevel(level), data);
    }

    private void buyForEmerald(String id, int level, int maxUses, int price,
                               ItemContainer... container) {
        VillagerData data = new VillagerData(null, profession, level);
        putByData(id, container, ItemContainer.EMPTY, new ItemContainer[]{item(price)}, maxUses,
                0.05f, experienceByLevel(level), data);
    }

    private void sellForEmerald(String id, int level, int maxUses, int price,
                                ItemContainer... container) {
        VillagerData data = new VillagerData(null, profession, level);
        putByData(id, new ItemContainer[]{item(price)}, ItemContainer.EMPTY, container, maxUses,
                0.05f, experienceByLevel(level), data);
    }

    private void buyDistributeForBiomes(String id, int level, int emeralds, int price,
                                        int maxUses, Item[] items, Map<VillagerType, int[]> colors) {

        colors.forEach((type, color) ->
                putByData(id, Arrays.stream(color).mapToObj(i -> item(items[i], price)).toArray(ItemContainer[]::new),
                ItemContainer.EMPTY,
                new ItemContainer[]{item(emeralds)},
                maxUses, 0.05f, experienceByLevel(level),
                new VillagerData(type, profession, level)));
    }
    private void sellDistributeForBiomes(String id, int level, int emeralds, int price,
                                         int maxUses, Item[] items, Map<VillagerType, int[]> colors) {
        colors.forEach((type, color) ->
                putByData(id, new ItemContainer[]{item(emeralds)},
                ItemContainer.EMPTY,
                Arrays.stream(color).mapToObj(i -> item(items[i], price)).toArray(ItemContainer[]::new),
                maxUses, 0.05f, experienceByLevel(level),
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
                           int maxUses, float priceMultiplier, int experience, VillagerData data) {
        if (data.type() != null) id = data.type() + "/" + id;
        if (data.profession() != null) id = data.profession() + "/" + id;
        OfferFactory factory = new OfferFactory(item1, item2, sell,
                maxUses, true, priceMultiplier, experience, Conditions.of(data));
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

    private static @NotNull ItemContainer item(Item item, Condition.Data<?> condition) {
        return ItemContainer.of(new ItemStack(item), condition);
    }

    private static @NotNull ItemContainer item(Item item, int count, Condition.Data<?> condition) {
        return ItemContainer.of(new ItemStack(item, count), condition);
    }

    public static int experienceByLevel(int level) {
        if (level == 0) return 1;
        return MathHelper.clamp((level - 1) * 10, 2, 50);
    }



    private void putSimple(String id, OfferFactory factory) {
        objects.put(new Identifier(Values.MOD_ID, id), factory);
    }


}
