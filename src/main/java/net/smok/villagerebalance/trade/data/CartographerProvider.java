package net.smok.villagerebalance.trade.data;

import net.minecraft.item.Items;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import net.smok.villagerebalance.trade.ItemContainer;
import net.smok.villagerebalance.trade.fields.FieldVillagerData;
import net.smok.villagerebalance.trade.functions.ItemFunctions;
import net.smok.villagerebalance.trade.fields.FieldMap;

import java.util.Arrays;
import java.util.Map;

public class CartographerProvider extends TradeOffersProvider {


    public CartographerProvider() {
        super(VillagerProfession.CARTOGRAPHER);
    }

    @Override
    public void fill() {

        buyForEmerald("paper", 1, 12, item(Items.PAPER));
        sellForEmerald("empty_map", 1, 12, item(Items.MAP));

        buyForEmerald("colored_glass_pane", 2, 12,
                Arrays.stream(GLASS_PANES).map(TradeOffersProvider::item).toArray(ItemContainer[]::new)
        );


        putTradeOffer("ocean_explorer_map",
                new ItemContainer[]{item(Items.EMERALD, 13)},
                new ItemContainer[]{item(Items.COMPASS)},
                new ItemContainer[]{item(Items.FILLED_MAP, ItemFunctions.of(FieldMap.OCEAN))},
                4, 0.05f, experienceByLevelSell(2), FieldVillagerData.of(null, VillagerProfession.CARTOGRAPHER, 2));

        buyForEmerald("compass", 3, 12, item(Items.COMPASS));

        putTradeOffer("woodland_explorer_map",
                new ItemContainer[]{item(Items.EMERALD, 14)},
                new ItemContainer[]{item(Items.COMPASS)},
                new ItemContainer[]{item(Items.FILLED_MAP, ItemFunctions.of(FieldMap.WOODLAND))},
                4, 0.05f, experienceByLevelSell(2), FieldVillagerData.of(null, VillagerProfession.CARTOGRAPHER, 2));


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

}
