package net.foxyas.changed_additions.process.variantsExtraStats;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.ltxprogrammer.changed.util.UniversalDist;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class TransfurExtraStatsRegistry {
    private static final Map<ResourceLocation, TransfurExtraStats> EXTRA_STATS_MAP = new HashMap<>();

    @OnlyIn(Dist.CLIENT)
    public static class CLIENT {
        private static final Map<ResourceLocation, TransfurExtraStats> EXTRA_STATS_MAP_CLIENT = new HashMap<>();

        public static void clear() {
            EXTRA_STATS_MAP_CLIENT.clear();
        }

        public static void register(TransfurExtraStats stats) {
            EXTRA_STATS_MAP_CLIENT.put(stats.getForm(), stats);
        }

        public static @Nullable TransfurExtraStats get(ResourceLocation formId) {
            return EXTRA_STATS_MAP_CLIENT.get(formId);
        }

        public static boolean hasExtraStats(ResourceLocation formId) {
            return EXTRA_STATS_MAP_CLIENT.containsKey(formId);
        }

        public static Collection<TransfurExtraStats> getAll() {
            return EXTRA_STATS_MAP_CLIENT.values();
        }
    }

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
