package net.smok.villagerebalance.trade.data;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import net.smok.villagerebalance.trade.functions.ItemFunctions;

public class LeatherworkerProvider extends TradeOffersProvider{


    protected LeatherworkerProvider() {
        super(VillagerProfession.LEATHERWORKER);
    }

    @Override
    public void fill() {

        buyForEmerald("leather", 1, 16, item(Items.LEATHER, 6));
        buyForEmerald("flint", 2, 12, item(Items.FLINT, 26));
        buyForEmerald("rabbit_hide", 3, 12, item(Items.RABBIT_HIDE, 9));
        buyForEmerald("turtle_scute", 4, 12, item(Items.SCUTE, 4));

        sellForEmerald("leather_horse_armor", 4, 12, 6, item(Items.LEATHER_HORSE_ARMOR));
        sellForEmerald("saddle", 5, 12, 6, item(Items.SADDLE));

        fillGear(VillagerType.DESERT, 0x177366, 0x546f17, 0xfee77e);
        fillGear(VillagerType.JUNGLE, 0x77b41c, 0xa05f2c, 0x1947d5);
        fillGear(VillagerType.PLAINS, 0x604642, 0xdcc64c, 0x4a5a3d);
        fillGear(VillagerType.SAVANNA, 0xb12d2c, 0x45421e, 0x6b5d50);
        fillGear(VillagerType.SNOW, 0x619589, 0xdfd3b3, 0x55301d);
        fillGear(VillagerType.SWAMP, 0x523c66, 0x74954d, 0x1e1e1e);
        fillGear(VillagerType.TAIGA, 0x906549, 0xd3cba7, 0x262626);
    }

    private void fillGear(VillagerType type, int color1, int color2, int color3) {
        sellForEmerald("leather_gear_1", type, 1, 12, 5,
                item(Items.LEATHER_LEGGINGS, ItemFunctions.ofColor(color1, true)),
                item(Items.LEATHER_LEGGINGS, ItemFunctions.ofColor(color2, true)),
                item(Items.LEATHER_LEGGINGS, ItemFunctions.ofColor(color3, true)),
                item(Items.LEATHER_CHESTPLATE, ItemFunctions.ofColor(color1, true)),
                item(Items.LEATHER_CHESTPLATE, ItemFunctions.ofColor(color2, true)),
                item(Items.LEATHER_CHESTPLATE, ItemFunctions.ofColor(color3, true))
        );

        sellForEmerald("leather_gear_2", type, 2, 12, 3,
                item(Items.LEATHER_HELMET, ItemFunctions.ofColor(color1, true)),
                item(Items.LEATHER_HELMET, ItemFunctions.ofColor(color2, true)),
                item(Items.LEATHER_HELMET, ItemFunctions.ofColor(color3, true)),
                item(Items.LEATHER_BOOTS, ItemFunctions.ofColor(color1, true)),
                item(Items.LEATHER_BOOTS, ItemFunctions.ofColor(color2, true)),
                item(Items.LEATHER_BOOTS, ItemFunctions.ofColor(color3, true))
        );

        sellForEmerald("leather_gear_3", type, 3, 12, 4,
                item(Items.LEATHER_LEGGINGS, ItemFunctions.ofColor(color1, true), ItemFunctions.ofSingle(2, Enchantments.PROTECTION, Enchantments.UNBREAKING)),
                item(Items.LEATHER_LEGGINGS, ItemFunctions.ofColor(color2, true), ItemFunctions.ofSingle(2, Enchantments.PROTECTION, Enchantments.UNBREAKING)),
                item(Items.LEATHER_LEGGINGS, ItemFunctions.ofColor(color3, true), ItemFunctions.ofSingle(2, Enchantments.PROTECTION, Enchantments.UNBREAKING)),
                item(Items.LEATHER_CHESTPLATE, ItemFunctions.ofColor(color1, true), ItemFunctions.ofSingle(2, Enchantments.PROTECTION, Enchantments.UNBREAKING)),
                item(Items.LEATHER_CHESTPLATE, ItemFunctions.ofColor(color2, true), ItemFunctions.ofSingle(2, Enchantments.PROTECTION, Enchantments.UNBREAKING)),
                item(Items.LEATHER_CHESTPLATE, ItemFunctions.ofColor(color3, true), ItemFunctions.ofSingle(2, Enchantments.PROTECTION, Enchantments.UNBREAKING)),
                item(Items.LEATHER_HELMET, ItemFunctions.ofColor(color1, true), ItemFunctions.ofSingle(2, Enchantments.PROTECTION, Enchantments.UNBREAKING)),
                item(Items.LEATHER_HELMET, ItemFunctions.ofColor(color2, true), ItemFunctions.ofSingle(2, Enchantments.PROTECTION, Enchantments.UNBREAKING)),
                item(Items.LEATHER_HELMET, ItemFunctions.ofColor(color3, true), ItemFunctions.ofSingle(2, Enchantments.PROTECTION, Enchantments.UNBREAKING)),
                item(Items.LEATHER_BOOTS, ItemFunctions.ofColor(color1, true), ItemFunctions.ofSingle(2, Enchantments.PROTECTION, Enchantments.UNBREAKING)),
                item(Items.LEATHER_BOOTS, ItemFunctions.ofColor(color2, true), ItemFunctions.ofSingle(2, Enchantments.PROTECTION, Enchantments.UNBREAKING)),
                item(Items.LEATHER_BOOTS, ItemFunctions.ofColor(color3, true), ItemFunctions.ofSingle(2, Enchantments.PROTECTION, Enchantments.UNBREAKING))
        );
    }
}
