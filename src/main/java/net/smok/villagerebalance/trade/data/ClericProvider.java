package net.smok.villagerebalance.trade.data;

import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.village.VillagerProfession;
import net.smok.villagerebalance.trade.functions.ItemFunctions;

public class ClericProvider extends TradeOffersProvider {


    public ClericProvider() {
        super(VillagerProfession.CLERIC);
    }

    @Override
    public void fill() {

        buyForEmerald("rotten_flesh", 1, 16, item(Items.ROTTEN_FLESH, 32));
        sellForEmerald("redstone_dust", 1, 16, item(Items.REDSTONE, 2));
        buyForEmerald("gold_ingot", 2, 12, item(Items.GOLD_INGOT, 3));
        sellForEmerald("lapis_lazuli", 2, 12, item(Items.LAPIS_LAZULI));
        buyForEmerald("phantom_membrane", 3, 12, item(Items.PHANTOM_MEMBRANE, 5));
        sellForEmerald("glowstone", 3, 12, item(Items.GLOWSTONE));
        buyForEmerald("glass_bottle", 4, 12, item(Items.GLASS_BOTTLE, 9));
        sellForEmerald("potions", 4, 12, 3,
                item(Items.POTION, ItemFunctions.of(Potions.REGENERATION)),
                item(Items.POTION, ItemFunctions.of(Potions.HEALING))
        );

        sellForEmerald("ender_pearl", 4, 12, 5, item(Items.ENDER_PEARL));
        buyForEmerald("nether_wart", 5, 12, item(Items.NETHER_WART, 22));


    }
}
