package net.foxyas.changed_additions.process.variantsExtraStats;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TransfurExtraStatsRegistry {
    private static final Map<ResourceLocation, TransfurExtraStats> EXTRA_STATS_MAP = new HashMap<>();

    public static void clear() {
        EXTRA_STATS_MAP.clear();
    }

    public static void register(TransfurExtraStats stats) {
        EXTRA_STATS_MAP.put(stats.getForm(), stats);
    }

    public static @Nullable TransfurExtraStats get(ResourceLocation formId) {
        return EXTRA_STATS_MAP.get(formId);
    }

    public static boolean hasExtraStats(ResourceLocation formId) {
        return EXTRA_STATS_MAP.containsKey(formId);
    }

    public static Collection<TransfurExtraStats> getAll() {
        return EXTRA_STATS_MAP.values();
    }
}
