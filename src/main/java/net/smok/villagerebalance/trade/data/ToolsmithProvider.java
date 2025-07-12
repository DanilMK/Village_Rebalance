package net.smok.villagerebalance.trade.data;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.village.VillagerProfession;
import net.smok.villagerebalance.trade.functions.ItemFunctions;

public class ToolsmithProvider extends TradeOffersProvider {

    public ToolsmithProvider() {
        super(VillagerProfession.TOOLSMITH);
    }

    @Override
    public void fill() {

        buyForEmerald("coal", 1, 12, item(Items.COAL, 15));
        buyForEmerald("iron_ingot", 2, 12, item(Items.IRON_INGOT, 4));
        buyForEmerald("lava_bucket", 3, 12, item(Items.LAVA_BUCKET, 1));
        buyForEmerald("diamond", 4, 12, item(Items.DIAMOND, 1));
        sellForEmerald("bell", 2, 12, 36, item(Items.BELL));

        sellForEmerald("stone_tools_1", 1, 12, 5,
                item(Items.STONE_AXE, ItemFunctions.ofProgression(Enchantments.SILK_TOUCH, Enchantments.FORTUNE)),
                item(Items.STONE_SHOVEL, ItemFunctions.ofProgression(Enchantments.SILK_TOUCH, Enchantments.FORTUNE)),
                item(Items.STONE_PICKAXE, ItemFunctions.ofProgression(Enchantments.SILK_TOUCH, Enchantments.FORTUNE)),
                item(Items.STONE_HOE, ItemFunctions.ofProgression(Enchantments.SILK_TOUCH, Enchantments.FORTUNE))
        );

        sellForEmerald("iron_tools_2", 2, 12, 10,
                item(Items.IRON_AXE, ItemFunctions.ofProgression(Enchantments.SILK_TOUCH, Enchantments.FORTUNE)),
                item(Items.IRON_SHOVEL, ItemFunctions.ofProgression(Enchantments.SILK_TOUCH, Enchantments.FORTUNE)),
                item(Items.IRON_PICKAXE, ItemFunctions.ofProgression(Enchantments.SILK_TOUCH, Enchantments.FORTUNE)),
                item(Items.IRON_HOE, ItemFunctions.ofProgression(Enchantments.SILK_TOUCH, Enchantments.FORTUNE))
        );

        sellForEmerald("iron_tools_3", 3, 12, 18,
                item(Items.IRON_AXE, ItemFunctions.ofProgression(Enchantments.SILK_TOUCH, Enchantments.FORTUNE)),
                item(Items.IRON_SHOVEL, ItemFunctions.ofProgression(Enchantments.SILK_TOUCH, Enchantments.FORTUNE)),
                item(Items.IRON_PICKAXE, ItemFunctions.ofProgression(Enchantments.SILK_TOUCH, Enchantments.FORTUNE)),
                item(Items.IRON_HOE, ItemFunctions.ofProgression(Enchantments.SILK_TOUCH, Enchantments.FORTUNE))
        );

        sellForEmerald("diamond_tools_4", 4, 12, 25,
                item(Items.DIAMOND_AXE, ItemFunctions.ofProgression(Enchantments.SILK_TOUCH, Enchantments.FORTUNE)),
                item(Items.DIAMOND_SHOVEL, ItemFunctions.ofProgression(Enchantments.SILK_TOUCH, Enchantments.FORTUNE)),
                item(Items.DIAMOND_PICKAXE, ItemFunctions.ofProgression(Enchantments.SILK_TOUCH, Enchantments.FORTUNE)),
                item(Items.DIAMOND_HOE, ItemFunctions.ofProgression(Enchantments.SILK_TOUCH, Enchantments.FORTUNE))
        );

        sellBook("enchanted_book", 5, Enchantments.SILK_TOUCH, Enchantments.FORTUNE);

    }
}
