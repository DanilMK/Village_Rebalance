package net.smok.villagerebalance.trade.data;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import net.smok.villagerebalance.trade.conditions.Conditions;
import net.smok.villagerebalance.trade.fields.FieldVillagerData;
import net.smok.villagerebalance.trade.functions.ItemFunctions;

public class FishermanVillager extends VillagerTradeOffers {


    public FishermanVillager() {
        super(VillagerProfession.FISHERMAN);
    }

    @Override
    public void fill() {

        buyForEmerald("lily_pad", 1, 16, item(Items.LILY_PAD, 7));

        sellForEmerald("cod_bucket", 1, 12, 3, item(Items.COD_BUCKET));

        buyForEmerald("raw_cod", 2, 12, item(Items.COD, 15));
        sellForEmerald("cooked_cod", 2, 12, item(Items.COOKED_COD, 6));

        buyForEmerald("raw_salmon", 3, 12, item(Items.SALMON, 13));
        sellForEmerald("cooked_salmon", 3, 12, item(Items.COOKED_SALMON, 6));

        buyForEmerald("tropical_fish", 4, 12, item(Items.TROPICAL_FISH, 6));
        sellForEmerald("campfire", 4, 12,2, item(Items.CAMPFIRE));

        buyForEmerald("pufferfish", 5, 12, item(Items.PUFFERFISH, 4));


        sellForEmerald("boats", 5, 12, 1,
                item(BOATS[DARK_OAK], Conditions.of(FieldVillagerData.of(VillagerType.SWAMP))),
                item(BOATS[ACACIA], Conditions.of(FieldVillagerData.of(VillagerType.SAVANNA))),
                item(BOATS[JUNGLE], Conditions.of(FieldVillagerData.of(VillagerType.JUNGLE))),
                item(BOATS[DARK_OAK], Conditions.of(FieldVillagerData.of(VillagerType.JUNGLE))),
                item(BOATS[SPRUCE], Conditions.of(FieldVillagerData.of(VillagerType.SNOW))),
                item(BOATS[SPRUCE], Conditions.of(FieldVillagerData.of(VillagerType.TAIGA))),
                item(BOATS[OAK], Conditions.of(FieldVillagerData.of(VillagerType.PLAINS))),
                item(BOATS[BIRCH], Conditions.of(FieldVillagerData.of(VillagerType.PLAINS)))
        );



        sellForEmerald("fishing_rod_1", 1, 3, 8,
                item(Items.FISHING_ROD, ItemFunctions.ofProgression(Enchantments.LURE, Enchantments.LUCK_OF_THE_SEA))
        );

        sellForEmerald("fishing_rod_2", 2, 3, 18,
                item(Items.FISHING_ROD, ItemFunctions.ofProgression(Enchantments.LURE, Enchantments.LUCK_OF_THE_SEA))
        );

        sellForEmerald("fishing_rod_3", 3, 3, 25,
                item(Items.FISHING_ROD, ItemFunctions.ofProgression(Enchantments.LURE, Enchantments.LUCK_OF_THE_SEA))
        );

        sellBook("enchanted_book", 5, Enchantments.LURE, Enchantments.LUCK_OF_THE_SEA);

    }

}
