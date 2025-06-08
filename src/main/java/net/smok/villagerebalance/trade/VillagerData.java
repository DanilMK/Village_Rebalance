package net.smok.villagerebalance.trade;

import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import net.smok.villagerebalance.Debug;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record VillagerData(@Nullable VillagerType type, @Nullable VillagerProfession profession, int level)  {

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
}
