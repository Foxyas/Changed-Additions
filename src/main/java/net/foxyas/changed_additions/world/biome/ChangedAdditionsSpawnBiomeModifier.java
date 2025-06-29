package net.foxyas.changed_additions.world.biome;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public record ChangedAdditionsSpawnBiomeModifier(
        List<ResourceKey<Biome>> biomes,
        List<ResourceKey<EntityType<?>>> entityTypeKey,
        int weight,
        int minCount,
        int maxCount
) implements BiomeModifier {

    public static final Codec<ChangedAdditionsSpawnBiomeModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceKey.codec(Registries.BIOME).listOf().fieldOf("biomes").forGetter(ChangedAdditionsSpawnBiomeModifier::biomes),
            ResourceKey.codec(Registries.ENTITY_TYPE).listOf().fieldOf("entity_type").forGetter(ChangedAdditionsSpawnBiomeModifier::entityTypeKey),
            Codec.INT.fieldOf("weight").forGetter(ChangedAdditionsSpawnBiomeModifier::weight),
            Codec.INT.fieldOf("min_count").forGetter(ChangedAdditionsSpawnBiomeModifier::minCount),
            Codec.INT.fieldOf("max_count").forGetter(ChangedAdditionsSpawnBiomeModifier::maxCount)
    ).apply(instance, ChangedAdditionsSpawnBiomeModifier::new));

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.ADD && biomes.contains(biome.unwrapKey().orElse(null))) {
            for (ResourceKey<EntityType<?>> typeResourceKey : entityTypeKey){
                builder.getMobSpawnSettings().addSpawn(
                        net.minecraft.world.entity.MobCategory.MONSTER,
                        new MobSpawnSettings.SpawnerData(
                                ForgeRegistries.ENTITY_TYPES.getDelegateOrThrow(typeResourceKey).get(),
                                weight,
                                minCount,
                                maxCount
                        )
                );
            }
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return CODEC;
    }
}
