package net.smok.villagerebalance.trade;

import com.google.gson.JsonObject;
import net.minecraft.registry.Registries;
import net.minecraft.util.JsonHelper;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import net.smok.villagerebalance.Debug;
import net.smok.villagerebalance.utility.JsonConvertible;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record VillagerData(@Nullable VillagerType type, @Nullable VillagerProfession profession, int level) implements JsonConvertible<VillagerData> {

    public static final VillagerData EMPTY = new VillagerData(null, null, 0);

    public boolean match(net.minecraft.village.@NotNull VillagerData villagerData) {

        /*
        Debug.log("Data Match " + villagerData.getLevel() + '/' + level +
                ' ' + villagerData.getType() + '/' + type
                + ' ' + villagerData.getProfession() + '/' + profession);*/
        return (villagerData.getLevel() == level || villagerData.getLevel() == 0 || level == 0) &&
                (villagerData.getType() == type || villagerData.getType() == null || type == null) &&
                (villagerData.getProfession() == profession || villagerData.getProfession() == null || profession == null);
    }

    public static @NotNull VillagerData of (VillagerType type, VillagerProfession profession, int level) {
        return new VillagerData(type, profession, level);
    }

    @Contract("_, _ -> new")
    public static @NotNull VillagerData of(VillagerProfession profession, int level) {
        return new VillagerData(null, profession, level);
    }

    public static @NotNull VillagerData of(VillagerType type) {
        return new VillagerData(type, null, 0);
    }

    @Override
    public VillagerData childFromJson(@NotNull JsonObject json) {
        VillagerType type = JsonConvertible.getRegister(json, "villager_type", Registries.VILLAGER_TYPE, type());
        VillagerProfession profession = JsonConvertible.getRegister(json, "villager_profession", Registries.VILLAGER_PROFESSION, profession());
        int level = JsonHelper.getInt(json, "villager_level", level());

        if (type == type() && profession == profession() && level == level()) return this;
        return new VillagerData(type, profession, level);
    }

    @Override
    public void toJson(@NotNull JsonObject json) {
        if (type() != null) json.addProperty("villager_type", Registries.VILLAGER_TYPE.getId(type()).toString());
        if (profession() != null)
            json.addProperty("villager_profession", Registries.VILLAGER_PROFESSION.getId(profession()).toString());
        if (level() != 0) json.addProperty("villager_level", level());

    }
}
