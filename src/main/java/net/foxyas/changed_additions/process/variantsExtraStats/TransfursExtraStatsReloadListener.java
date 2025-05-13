package net.foxyas.changed_additions.process.variantsExtraStats;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class TransfursExtraStatsReloadListener extends SimpleJsonResourceReloadListener {
    public static final Gson GSON = new GsonBuilder().create();

    public TransfursExtraStatsReloadListener() {
        super(GSON, "transfur_stats"); // data/*/transfur_visions/*.json
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> entries, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profiler) {
        TransfurExtraStatsRegistry.clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : entries.entrySet()) {
            try {
                ResourceLocation id = entry.getKey();
                JsonObject json = GsonHelper.convertToJsonObject(entry.getValue(), "Custom_Stats");

                TransfurExtraStats vision = TransfurExtraStats.fromJson(id, json);
                TransfurExtraStatsRegistry.register(vision);

            } catch (Exception e) {
                ChangedAdditionsMod.LOGGER.error("Failed to parse transfur stats {}: {}", entry.getKey(), e.getMessage());
            }
        }

        if (!entries.isEmpty()) {
            ChangedAdditionsMod.LOGGER.info("Loaded transfur stats successfully.");
        }
        ChangedAdditionsMod.LOGGER.info("Loaded {} transfur stats", entries.size());
    }

}
