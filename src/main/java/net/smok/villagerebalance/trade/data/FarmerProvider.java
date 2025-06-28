package net.smok.villagerebalance.trade.data;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import net.smok.villagerebalance.trade.ItemContainer;
import net.smok.villagerebalance.trade.VillagerData;
import net.smok.villagerebalance.trade.conditions.Conditions;
import net.smok.villagerebalance.trade.functions.ItemFunctions;

import java.util.Map;

public class FarmerProvider extends TradeOffersProvider {


    public FarmerProvider() {
        super(VillagerProfession.FARMER);
    }

    @Override
    public void fill() {
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

}
