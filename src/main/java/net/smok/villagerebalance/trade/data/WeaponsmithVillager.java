package net.smok.villagerebalance.trade.data;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;

public class WeaponsmithVillager extends VillagerTradeOffers {

    public WeaponsmithVillager() {
        super(VillagerProfession.WEAPONSMITH);
    }

    @Override
    public void fill() {

        buyForEmerald("coal", 1, 12, item(Items.COAL, 15));
        buyForEmerald("lava_bucket", 3, 12, item(Items.LAVA_BUCKET, 1));
        buyForEmerald("diamond", 4, 12, item(Items.DIAMOND, 1));

        sellForEmerald("bell", 2, 12, 36, item(Items.BELL));


        trident();
        sword();
        exSword();
        axe();
    }

    private void trident() {
        sellForEmerald("iron_sword", VillagerType.SNOW, 1, 3, 3, item(Items.IRON_SWORD));
        sellForEmerald("diamond_sword", VillagerType.SNOW, 4, 3, 7, item(Items.DIAMOND_SWORD));
        sellForEmerald("iron_sword", VillagerType.DESERT, 1, 3, 3, item(Items.IRON_SWORD));
        sellForEmerald("diamond_sword", VillagerType.DESERT, 4, 3, 7, item(Items.DIAMOND_SWORD));

        sellBook("trident_books_2", VillagerType.SNOW, 2, Enchantments.IMPALING, Enchantments.LOYALTY, Enchantments.CHANNELING, Enchantments.RIPTIDE);
        sellBook("trident_books_2", VillagerType.DESERT, 2, Enchantments.IMPALING, Enchantments.LOYALTY, Enchantments.CHANNELING, Enchantments.RIPTIDE);

        sellBook("trident_books_3", VillagerType.SNOW, 3, Enchantments.IMPALING, Enchantments.LOYALTY, Enchantments.CHANNELING, Enchantments.RIPTIDE);
        sellBook("trident_books_3", VillagerType.DESERT, 3, Enchantments.IMPALING, Enchantments.LOYALTY, Enchantments.CHANNELING, Enchantments.RIPTIDE);

        sellBook("trident_books_5", VillagerType.SNOW, 5, Enchantments.IMPALING, Enchantments.LOYALTY, Enchantments.CHANNELING, Enchantments.RIPTIDE);
        sellBook("trident_books_5", VillagerType.DESERT, 5, Enchantments.IMPALING, Enchantments.LOYALTY, Enchantments.CHANNELING, Enchantments.RIPTIDE);

    }

    private void sword() {
        sellForEmerald("iron_sword", VillagerType.PLAINS, 1, 3, 3, item(Items.IRON_SWORD));
        sellForEmerald("diamond_sword", VillagerType.PLAINS, 4, 3, 7, item(Items.DIAMOND_SWORD));

        sellBook("sword_books_2", VillagerType.PLAINS, 2, 
                Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS, Enchantments.LOOTING,
                Enchantments.KNOCKBACK, Enchantments.FIRE_ASPECT, Enchantments.SWEEPING);

        sellBook("sword_books_3", VillagerType.PLAINS, 3, 
                Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS, Enchantments.LOOTING,
                Enchantments.KNOCKBACK, Enchantments.FIRE_ASPECT, Enchantments.SWEEPING);

        sellBook("sword_books_5", VillagerType.PLAINS, 5, 
                Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS, Enchantments.LOOTING,
                Enchantments.KNOCKBACK, Enchantments.FIRE_ASPECT, Enchantments.SWEEPING);
        
    }
    private void exSword() {
        sellForEmerald("iron_sword", VillagerType.SWAMP, 1, 3, 3, item(Items.IRON_SWORD));
        sellForEmerald("diamond_sword", VillagerType.SWAMP, 4, 3, 7, item(Items.DIAMOND_SWORD));
        sellForEmerald("iron_sword", VillagerType.SAVANNA, 1, 3, 3, item(Items.IRON_SWORD));
        sellForEmerald("diamond_sword", VillagerType.SAVANNA, 4, 3, 7, item(Items.DIAMOND_SWORD));

        sellBook("sword_books_2", VillagerType.SWAMP, 2,
                Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS, Enchantments.LOOTING,
                Enchantments.KNOCKBACK, Enchantments.FIRE_ASPECT, Enchantments.SWEEPING);

        sellBook("sword_books_3", VillagerType.SWAMP, 3,
                Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS, Enchantments.LOOTING,
                Enchantments.KNOCKBACK, Enchantments.FIRE_ASPECT, Enchantments.SWEEPING);

        sellBook("sword_books_5", VillagerType.SWAMP, 5,
                Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS, Enchantments.LOOTING,
                Enchantments.KNOCKBACK, Enchantments.FIRE_ASPECT, Enchantments.SWEEPING);

        sellBook("sword_books_2", VillagerType.SAVANNA, 2,
                Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS, Enchantments.LOOTING,
                Enchantments.KNOCKBACK, Enchantments.FIRE_ASPECT, Enchantments.SWEEPING);

        sellBook("sword_books_3", VillagerType.SAVANNA, 3,
                Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS, Enchantments.LOOTING,
                Enchantments.KNOCKBACK, Enchantments.FIRE_ASPECT, Enchantments.SWEEPING);

        sellBook("sword_books_5", VillagerType.SAVANNA, 5,
                Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS, Enchantments.LOOTING,
                Enchantments.KNOCKBACK, Enchantments.FIRE_ASPECT, Enchantments.SWEEPING);
    }

    private void axe() {
        sellForEmerald("iron_axe", VillagerType.TAIGA, 1, 3, 3, item(Items.IRON_AXE));
        sellForEmerald("diamond_axe", VillagerType.TAIGA, 4, 3, 7, item(Items.DIAMOND_AXE));
        sellForEmerald("iron_axe", VillagerType.JUNGLE, 1, 3, 3, item(Items.IRON_AXE));
        sellForEmerald("diamond_axe", VillagerType.JUNGLE, 4, 3, 7, item(Items.DIAMOND_AXE));

        sellBook("axe_books_2", VillagerType.TAIGA, 2,
                Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS);
        sellBook("axe_books_3", VillagerType.TAIGA, 3,
                Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS);
        sellBook("axe_books_5", VillagerType.TAIGA, 5,
                Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS);

        sellBook("axe_books_2", VillagerType.JUNGLE, 2,
                Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS);
        sellBook("axe_books_3", VillagerType.JUNGLE, 3,
                Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS);
        sellBook("axe_books_5", VillagerType.JUNGLE, 5,
                Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS);

    }
}
