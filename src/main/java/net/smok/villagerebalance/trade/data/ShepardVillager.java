package net.smok.villagerebalance.trade.data;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.util.Pair;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import net.smok.villagerebalance.trade.conditions.OfferCondition;
import net.smok.villagerebalance.trade.functions.ItemFunctions;

import java.util.List;

public class ShepardVillager extends VillagerTradeOffers {


    protected ShepardVillager() {
        super(VillagerProfession.SHEPHERD);
    }

    @Override
    public void fill() {


        buyForEmerald("string", 1, 16, item(Items.STRING, 20));
        sellForEmerald("shears", 1, 12,
                item(Items.SHEARS), item(Items.SHEARS), item(Items.SHEARS), item(Items.SHEARS),
                item(Items.SHEARS, ItemFunctions.ofSingle(Enchantments.UNBREAKING))); // 1/5 - chance of enchanted Shears

        buyDistributeForBiomes("dyes", 2, 1, 12, 16, DYES,
                List.of(new Pair<>(OfferCondition.SearchType.SELL, WOOLS)), colorsDistribute);

        sellDistributeForBiomes("wool", 2, 3, 3, 16, WOOLS,
                List.of(new Pair<>(OfferCondition.SearchType.BUY, DYES)), colorsDistribute);

        sellDistributeForBiomes("carpets", 3, 3, 3, 16, CARPETS,
                List.of(new Pair<>(OfferCondition.SearchType.BUY, DYES), new Pair<>(OfferCondition.SearchType.SELL, WOOLS)), colorsDistribute);

        sellDistributeForBiomes("beds", 4, 3, 1, 16, BEDS,
                List.of(new Pair<>(OfferCondition.SearchType.BUY, DYES),
                        new Pair<>(OfferCondition.SearchType.SELL, WOOLS),
                        new Pair<>(OfferCondition.SearchType.SELL, CARPETS)
                ), colorsDistribute);

        buyDistributeForBiomes("fences", 3, 1, 40, 16, FENCES, woodDistribute);
        buyDistributeForBiomes("gates", 4, 1, 8, 16, GATES,
                List.of(new Pair<>(OfferCondition.SearchType.BUY, FENCES)), woodDistribute);
        //sellDistributeForBiomes("banners", 4, 3, 3, 16, BANNERS, colorsDistribute);


        sellForEmerald("painting", VillagerType.DESERT, 5, 12, 2, item(Items.PAINTING, 3));
    }
}
