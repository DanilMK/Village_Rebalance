package net.smok.villagerebalance.trade.data;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.village.VillagerProfession;
import net.smok.villagerebalance.trade.ItemContainer;

import java.util.Arrays;

public class LibrarianProvider extends TradeOffersProvider{
    protected LibrarianProvider() {
        super(VillagerProfession.LIBRARIAN);
    }

    @Override
    public void fill() {
        buyForEmerald("paper", 1, 16, item(Items.PAPER, 24));
        buyForEmerald("book", 2, 12, item(Items.BOOK, 4));
        buyForEmerald("ink_sac", 3, 12, item(Items.INK_SAC, 5));
        buyForEmerald("book_and_quill", 4, 12, item(Items.WRITABLE_BOOK));
        buyForEmerald("clock", 5, 12, item(Items.CLOCK));

        sellBook("enchanted_book", 0, Enchantments.UNBREAKING, Enchantments.MENDING, Enchantments.BINDING_CURSE, Enchantments.VANISHING_CURSE);

        buyForEmerald("bookshelf", 1, 12, 9, item(Items.BOOKSHELF));
        buyForEmerald("lantern", 2, 12, item(Items.LANTERN));
        buyForEmerald("glass", 3, 12, item(Items.GLASS, 4));
        buyForEmerald("candle", 4, 12, 2, Arrays.stream(CANDLES).map(item -> item(item, 4)).toArray(ItemContainer[]::new));
        buyForEmerald("name_tag", 5, 12, 20, item(Items.NAME_TAG));
    }
}
