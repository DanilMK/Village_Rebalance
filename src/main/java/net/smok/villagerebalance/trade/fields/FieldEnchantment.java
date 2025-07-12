package net.smok.villagerebalance.trade.fields;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.smok.villagerebalance.utility.JsonConvertible;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record FieldEnchantment(boolean useSameEnchant, boolean useVillagerLevel, int fixedLevel, List<Enchantment> enchantments) implements JsonConvertible<FieldEnchantment> {

    @Contract("_, _, _, _ -> new")
    public static @NotNull FieldEnchantment of(boolean useSameEnchant, boolean useVillagerLevel, int fixedLevel, Enchantment... enchantments) {
        return new FieldEnchantment(useSameEnchant, useVillagerLevel, fixedLevel, List.of(enchantments));
    }

    public int getLevel(Enchantment enchantment, MerchantEntity entity) {
        if (useVillagerLevel && entity instanceof VillagerEntity villager)
            return MathHelper.clamp(villager.getVillagerData().getLevel(), enchantment.getMinLevel(), enchantment.getMaxLevel());

        if (fixedLevel > 0) return fixedLevel;
        return entity.getRandom().nextBetween(enchantment.getMinLevel(), enchantment.getMaxLevel());
    }


    public Optional<Enchantment> getSame(@NotNull ItemStack itemStack) {
        NbtCompound nbt = itemStack.getNbt();

        if (nbt != null) {
            NbtList storedEnchantments = itemStack.getItem() instanceof EnchantedBookItem ?
                    nbt.getList("StoredEnchantments", NbtElement.COMPOUND_TYPE) :
                    nbt.getList("Enchantments", NbtElement.COMPOUND_TYPE);

            if (storedEnchantments != null) for (int i = 0; i < storedEnchantments.size(); i++) {

                NbtCompound enchantCompound = storedEnchantments.getCompound(i);
                if (enchantCompound != null && enchantCompound.getString("id") != null) {
                    Enchantment enchantment = Registries.ENCHANTMENT.get(new Identifier(enchantCompound.getString("id")));

                    if (enchantments.contains(enchantment)) return Optional.ofNullable(enchantment);
                }
            }
        }
        return Optional.empty();
    }

    @NotNull
    public Enchantment getRandom(Random random) {
        return enchantments.get(random.nextInt(enchantments.size()));
    }

    @Override
    public FieldEnchantment childFromJson(@NotNull JsonObject json) {
        List<Enchantment> enchantments = new ArrayList<>();
        if (json.has("enchantments") && json.get("enchantments").isJsonPrimitive()) {
            enchantments.add(JsonConvertible.getRegister(json, "enchantments", Registries.ENCHANTMENT));
        }
        else if (json.has("enchantments") && json.get("enchantments").isJsonArray()) {
            JsonArray jsonArray = json.get("enchantments").getAsJsonArray();
            for (JsonElement element : jsonArray)
                if (element.isJsonPrimitive())
                    enchantments.add(JsonConvertible.asRegister(Registries.ENCHANTMENT, element.getAsJsonPrimitive()));
        }
        if (enchantments.isEmpty()) throw new JsonParseException("Empty enchantments array");

        boolean useSameEnchant = JsonHelper.getBoolean(json, "use_same_enchantment", this.useSameEnchant);
        boolean useVillagerLevel = JsonHelper.getBoolean(json, "use_villager_level", this.useVillagerLevel);
        int fixedLevel = JsonHelper.getInt(json, "fixed_level", this.fixedLevel);

        return new FieldEnchantment(useSameEnchant, useVillagerLevel, fixedLevel, enchantments);
    }


    @Override
    public void toJson(@NotNull JsonObject json) {
        if (enchantments.isEmpty()) return;
        else if (enchantments.size() == 1) {
            Identifier id = Registries.ENCHANTMENT.getId(enchantments.get(0));
            if (id != null) json.addProperty("enchantments", id.toString());
        } else {
            JsonArray array = new JsonArray(enchantments().size());
            enchantments.forEach(enchantment -> {
                Identifier id = Registries.ENCHANTMENT.getId(enchantment);
                if (id != null) array.add(id.toString());
            });
            json.add("enchantments", array);
        }
        json.addProperty("use_same_enchantment", useSameEnchant);
        json.addProperty("use_villager_level", useVillagerLevel);
        json.addProperty("fixed_level", fixedLevel);

    }
}
