package net.smok.villagerebalance.trade.data;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;
import net.smok.villagerebalance.Values;
import net.smok.villagerebalance.trade.ItemContainer;
import net.smok.villagerebalance.trade.OfferFactory;
import net.smok.villagerebalance.trade.conditions.Condition;
import net.smok.villagerebalance.trade.conditions.Conditions;
import net.smok.villagerebalance.trade.functions.ItemFunction;
import net.smok.villagerebalance.trade.functions.ItemFunctions;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class WanderingTraderProvider {

    private final HashMap<Identifier, OfferFactory> objects = new HashMap<>();
    private String rarity;

    public HashMap<Identifier, OfferFactory> getObjects() {
        return objects;
    }


    public void fill() {
        rarity = Values.PURCHASING;

        buyForEmerald("hay_bale", 2, item(Items.HAY_BLOCK));
        buyForEmerald("water_bottle", 2, item(Items.POTION, ItemFunctions.of(Potions.WATER)));
        buyForEmerald("baked_potato", 2, item(Items.BAKED_POTATO, 4));
        buyForEmerald("milk_bucket", 2, 2, item(Items.MILK_BUCKET));
        buyForEmerald("water_bucket", 2, 2, item(Items.WATER_BUCKET));
        buyForEmerald("fermented_spider_eye", 2, 3, item(Items.FERMENTED_SPIDER_EYE));

        rarity = Values.SPECIAL;

        sellForEmerald("packed_ice", 6, 1, item(Items.PACKED_ICE));
        sellForEmerald("gunpowder", 2, 1, item(Items.GUNPOWDER, 4));
        sellForEmerald("logs", 4, 1,
                item(Items.OAK_LOG, 8),
                item(Items.SPRUCE_LOG, 8),
                item(Items.BIRCH_LOG, 8),
                item(Items.JUNGLE_LOG, 8),
                item(Items.ACACIA_LOG, 8),
                item(Items.DARK_OAK_LOG, 8),
                item(Items.MANGROVE_LOG, 8),
                item(Items.CHERRY_LOG, 8)
        );

        sellForEmerald("podzol", 6, 3, item(Items.PODZOL, 3));
        sellForEmerald("potion_of_invisibility", 1, 5, item(Items.POTION, ItemFunctions.of(Potions.INVISIBILITY)));
        sellForEmerald("blue_ice", 6, 6, item(Items.BLUE_ICE));
        sellForEmerald("iron_pickaxe", 1, 6, item(Items.IRON_PICKAXE));

        rarity = Values.ORDINARY;

        sellForEmerald("fern", 12, item(Items.FERN));
        sellForEmerald("sugarcane", 8, item(Items.SUGAR_CANE));
        sellForEmerald("pumpkin", 4, item(Items.PUMPKIN));
        sellForEmerald("ordinary_flowers", 12,
                item(Items.DANDELION),
                item(Items.POPPY),
                item(Items.ALLIUM),
                item(Items.AZURE_BLUET),
                item(Items.RED_TULIP),
                item(Items.ORANGE_TULIP),
                item(Items.WHITE_TULIP),
                item(Items.PINK_TULIP),
                item(Items.OXEYE_DAISY),
                item(Items.CORNFLOWER)
        );

        sellForEmerald("rare_flowers", 8,
                item(Items.BLUE_ORCHID),
                item(Items.LILY_OF_THE_VALLEY)
        );

        sellForEmerald("seeds", 12,
                item(Items.WHEAT_SEEDS),
                item(Items.BEETROOT_SEEDS),
                item(Items.PUMPKIN_SEEDS),
                item(Items.MELON_SEEDS)
        );

        sellForEmerald("dyes", 12,
                item(Items.RED_DYE, 3),
                item(Items.WHITE_DYE, 3),
                item(Items.BLUE_DYE, 3),
                item(Items.PINK_DYE, 3),
                item(Items.BLACK_DYE, 3),
                item(Items.GREEN_DYE, 3),
                item(Items.LIGHT_GRAY_DYE, 3),
                item(Items.MAGENTA_DYE, 3),
                item(Items.YELLOW_DYE, 3),
                item(Items.GRAY_DYE, 3),
                item(Items.PURPLE_DYE, 3),
                item(Items.LIGHT_BLUE_DYE, 3),
                item(Items.ORANGE_DYE, 3),
                item(Items.BROWN_DYE, 3),
                item(Items.CYAN_DYE, 3)
        );

        sellForEmerald("vegetation", 4,
                item(Items.VINE, 3),
                item(Items.RED_MUSHROOM, 3),
                item(Items.BROWN_MUSHROOM, 3),
                item(Items.LILY_PAD, 5),
                item(Items.SMALL_DRIPLEAF, 2)
        );
        
        sellForEmerald("sands", 8, item(Items.SAND, 8), item(Items.RED_SAND, 6));
        sellForEmerald("blocks", 1, 5, 
                item(Items.POINTED_DRIPSTONE, 2), 
                item(Items.ROOTED_DIRT, 2),
                item(Items.MOSS_BLOCK, 2)
        );
        
        sellForEmerald("glowstone", 5, 2, item(Items.GLOWSTONE));
        sellForEmerald("sea_pickle", 5, 2, item(Items.SEA_PICKLE));
        sellForEmerald("fish_in_bucket", 4, 3, item(Items.TROPICAL_FISH_BUCKET), item(Items.PUFFERFISH_BUCKET));
        sellForEmerald("kelp", 12, 3, item(Items.KELP));
        sellForEmerald("cactus", 8, 3, item(Items.CACTUS));
        sellForEmerald("corals", 8, 3,
                item(Items.BRAIN_CORAL_BLOCK),
                item(Items.BUBBLE_CORAL_BLOCK),
                item(Items.FIRE_CORAL_BLOCK),
                item(Items.HORN_CORAL_BLOCK),
                item(Items.TUBE_CORAL_BLOCK)
        );
        
        sellForEmerald("slimeball", 5, 4, item(Items.SLIME_BALL));
        sellForEmerald("sapplings", 8, 5,
                item(Items.OAK_SAPLING),
                item(Items.SPRUCE_SAPLING),
                item(Items.BIRCH_SAPLING),
                item(Items.JUNGLE_SAPLING),
                item(Items.ACACIA_SAPLING),
                item(Items.DARK_OAK_SAPLING),
                item(Items.MANGROVE_PROPAGULE),
                item(Items.CHERRY_SAPLING)
        );

        sellForEmerald("nautilus_shell", 5, 5, item(Items.NAUTILUS_SHELL));
    }


    protected void buyForEmerald(String id, int maxUses, ItemContainer... container) {
        buyForEmerald(id, maxUses, 1, container);
    }

    protected void sellForEmerald(String id, int maxUses, ItemContainer... container) {
        sellForEmerald(id, maxUses, 1, container);
    }

    protected void buyForEmerald(String id, int maxUses, int emeralds, ItemContainer... container) {
        putTradeOffer(id, container, item(), new ItemContainer[]{item(emeralds)}, maxUses, 0.05f, rarity);
    }

    protected void sellForEmerald(String id, int maxUses, int emeralds, ItemContainer... container) {
        putTradeOffer(id, new ItemContainer[]{item(emeralds)}, item(), container, maxUses, 0.05f, rarity);
    }

    protected void buyForEmerald(String id, int maxUses, int emeralds, Condition.Data<?> condition, ItemContainer... container) {
        putTradeOffer(id, container, ItemContainer.EMPTY, new ItemContainer[]{item(emeralds)}, maxUses,
                0.05f, rarity, condition);
    }

    protected void sellForEmerald(String id, int maxUses, int emeralds, Condition.Data<?> condition, ItemContainer... container) {

        putTradeOffer(id, new ItemContainer[]{item(emeralds)}, ItemContainer.EMPTY, container, maxUses,
                0.05f, rarity, condition);
    }

    protected void putTradeOffer(String id, @NotNull ItemContainer[] item1, @NotNull ItemContainer[] item2, @NotNull ItemContainer[] sell,
                                 int maxUses, float priceMultiplier, String rarity) {
        id = "wandering_trader/" + rarity + "/" + id;
        OfferFactory factory = new OfferFactory(item1, item2, sell,
                maxUses, true, priceMultiplier, 1, rarity, Conditions.WANDERING_TRADER);
        objects.put(new Identifier(Values.MOD_ID, id), factory);
    }

    protected void putTradeOffer(String id, @NotNull ItemContainer[] item1, @NotNull ItemContainer[] item2, @NotNull ItemContainer[] sell,
                                 int maxUses, float priceMultiplier, String rarity, Condition.Data<?> additionalCondition) {
        id = "wandering_trader/" + rarity + "/" + id;
        OfferFactory factory = new OfferFactory(item1, item2, sell,
                maxUses, true, priceMultiplier, 1, rarity,
                Conditions.and(Conditions.WANDERING_TRADER, additionalCondition));

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

}
