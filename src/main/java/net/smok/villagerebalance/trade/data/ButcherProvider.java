package net.smok.villagerebalance.trade.data;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import net.smok.villagerebalance.trade.ItemContainer;
import net.smok.villagerebalance.trade.VillagerData;
import net.smok.villagerebalance.trade.conditions.Conditions;
import net.smok.villagerebalance.trade.functions.ItemFunctions;

import java.util.Map;

public class ButcherProvider extends TradeOffersProvider {

    public ButcherProvider() {
        super(VillagerProfession.BUTCHER);
    }

    @Override
    public void fill() {

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

        sellDistributeForBiomes("trapdoors", 4, 3, 3, 16, TRAPDOORS, woodDistribute);

        sellForEmerald("potions", 5, 12, 3,
                item(Items.POTION, ItemFunctions.of(Potions.LEAPING)),
                item(Items.POTION, ItemFunctions.of(Potions.SWIFTNESS))
        );

    }

}
