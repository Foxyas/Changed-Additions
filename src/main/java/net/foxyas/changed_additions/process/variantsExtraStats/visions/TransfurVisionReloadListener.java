package net.foxyas.changed_additions.process.variantsExtraStats.visions;

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

import java.util.Map;

public class TransfurVisionReloadListener extends SimpleJsonResourceReloadListener {
    public static final Gson GSON = new GsonBuilder().create();

    public TransfurVisionReloadListener() {
        super(GSON, "transfur_visions"); // data/*/transfur_visions/*.json
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> entries, ResourceManager resourceManager, ProfilerFiller profiler) {
        TransfurVisionRegistry.clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : entries.entrySet()) {
            try {
                ResourceLocation id = entry.getKey();
                JsonObject json = GsonHelper.convertToJsonObject(entry.getValue(), "Vision");

                TransfurVariantVision vision = TransfurVariantVision.fromJson(id, json);
                TransfurVisionRegistry.register(vision);

            } catch (Exception e) {
                ChangedAdditionsMod.LOGGER.error("Failed to parse vision {}: {}", entry.getKey(), e.getMessage());
            }
        }

        if (!entries.isEmpty()) {
            ChangedAdditionsMod.LOGGER.info("Loaded transfur visions successfully. Note: Ensure your vision post-effect shaders are valid – malformed shaders may cause rendering issues or crashes.");
        }
        ChangedAdditionsMod.LOGGER.info("Loaded {} transfur visions", entries.size());
    }

}
