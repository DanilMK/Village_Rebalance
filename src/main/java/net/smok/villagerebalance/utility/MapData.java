package net.smok.villagerebalance.utility;

import com.google.gson.JsonObject;
import net.minecraft.item.map.MapIcon;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.StructureTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureKeys;
import org.jetbrains.annotations.NotNull;

public record MapData(TagKey<Structure> structure, String nameKey, MapIcon.Type icon) implements JsonConvertible<MapData>{
    @Override
    public MapData childFromJson(@NotNull JsonObject json) {

        String name = JsonHelper.getString(json, "map_name", nameKey);
        String icon = JsonHelper.getString(json, "map_icon", icon().name().toLowerCase());
        TagKey<Structure> mapStructure = JsonConvertible.getTag(json, "map_structure", RegistryKeys.STRUCTURE, structure);

        return new MapData(mapStructure, name, MapIcon.Type.valueOf(icon.toUpperCase()));
    }

    @Override
    public void toJson(@NotNull JsonObject json) {

        json.addProperty("map_name", nameKey);
        json.addProperty("map_icon", icon.name().toLowerCase());
        json.addProperty("map_structure", structure.id().toString());
    }

    public static final MapData OCEAN = new MapData(StructureTags.ON_OCEAN_EXPLORER_MAPS, "filled_map.monument", MapIcon.Type.MONUMENT);
    public static final MapData WOODLAND = new MapData(StructureTags.ON_WOODLAND_EXPLORER_MAPS, "filled_map.mansion", MapIcon.Type.MANSION);
    public static final MapData VILLAGE = new MapData(StructureTags.VILLAGE, "filled_map.unknown", MapIcon.Type.BANNER_WHITE);
}
