package net.smok.villagerebalance.trade.data;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import net.smok.villagerebalance.trade.ItemContainer;
import net.smok.villagerebalance.trade.VillagerData;
import net.smok.villagerebalance.trade.conditions.Conditions;
import net.smok.villagerebalance.trade.functions.ItemFunctions;

import java.util.Map;

public class FletcherProvider extends TradeOffersProvider {


    public FletcherProvider() {
        super(VillagerProfession.FLETCHER);
    }

    @Override
    public void fill() {
        buyForEmerald("stick", 1, 12, item(Items.STICK, 32));
        sellForEmerald("arrow", 1, 12, item(Items.ARROW, 16));
        //sellForEmerald("flint", 1, 12, item(Items.FLINT, 10));

        buyForEmerald("flint", 2, 12, item(Items.FLINT, 26));

        buyForEmerald("string", 3, 12, item(Items.STRING, 14));

        buyForEmerald("feather", 4, 12, item(Items.FEATHER, 24));
        sellForEmerald("tipped_arrow", 4, 12, item(Items.TIPPED_ARROW, 5)); // todo add effect

        buyForEmerald("tripwire_hook", 5, 12, item(Items.TRIPWIRE_HOOK, 8));
        sellForEmerald("dispenser", 5, 12, item(Items.DISPENSER, 1));


        sellForEmerald("bow_1", 1, 3, 5,
                item(Items.BOW, ItemFunctions.ofProgression(Enchantments.FLAME, Enchantments.INFINITY)));

        sellForEmerald("bow_2", 2, 3, 13,
                item(Items.BOW, ItemFunctions.ofProgression(Enchantments.POWER, Enchantments.PUNCH)));

        sellForEmerald("bow_3", 3, 3, 18,
                item(Items.BOW, ItemFunctions.ofProgression(Enchantments.POWER, Enchantments.PUNCH)));

        sellForEmerald("bow_4", 4, 3, 22,
                item(Items.BOW, ItemFunctions.ofProgression(Enchantments.POWER, Enchantments.PUNCH)));

        sellForEmerald("crossbow_1", 1, 3, 8,
                item(Items.CROSSBOW, ItemFunctions.ofProgression(Enchantments.MULTISHOT, Enchantments.QUICK_CHARGE))
        );

        sellForEmerald("crossbow_2", 2, 3, 15,
                item(Items.CROSSBOW, ItemFunctions.ofProgression(Enchantments.PIERCING, Enchantments.QUICK_CHARGE))
        );

        sellForEmerald("crossbow_3", 3, 3, 20,
                item(Items.CROSSBOW, ItemFunctions.ofProgression(Enchantments.PIERCING, Enchantments.QUICK_CHARGE))
        );

        sellForEmerald("crossbow_3", 3, 3, 20,
                item(Items.CROSSBOW, ItemFunctions.ofProgression(Enchantments.PIERCING, Enchantments.QUICK_CHARGE))
        );

        sellBook("enchanted_book", 5, Enchantments.FLAME, Enchantments.INFINITY, Enchantments.POWER,
                Enchantments.PUNCH, Enchantments.MULTISHOT, Enchantments.QUICK_CHARGE, Enchantments.PIERCING);



    }
}
