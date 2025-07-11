package net.foxyas.changed_additions.init;

import com.mojang.serialization.Codec;
import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.effects.particles.*;
import net.ltxprogrammer.changed.util.Color3;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChangedAdditionsParticles {
    private static final Map<ResourceLocation, ParticleType<?>> REGISTRY = new HashMap<>();
    public static final ParticleType<ThunderSparkOption> THUNDER_SPARK = register(new ResourceLocation(ChangedAdditionsMod.MODID, "thunder_spark"), ThunderSparkOption.DESERIALIZER, ThunderSparkOption::codec);
    public static final ParticleType<LaserPointParticle.Option> LASER_POINT = register(new ResourceLocation(ChangedAdditionsMod.MODID, "laser_point"), LaserPointParticle.Option.DESERIALIZER, LaserPointParticle.Option::codec);
    public static final ParticleType<SolventDripsOption> SOLVENT_PARTICLE = register(new ResourceLocation(ChangedAdditionsMod.MODID, "solvent_drips"), SolventDripsOption.DESERIALIZER, SolventDripsOption::codec);

    public static ThunderSparkOption thunderSpark(int lifeSpam) {
        return new ThunderSparkOption(THUNDER_SPARK, lifeSpam);
    }

    public static LaserPointParticle.Option laserPoint(Entity entity, Color3 color, float alpha) {
        return new LaserPointParticle.Option(entity, color.toInt(), alpha);
    }

    public static LaserPointParticle.Option laserPoint(Entity entity, Color color) {
        return new LaserPointParticle.Option(entity, color.getRGB(), color.getAlpha());
    }

    public static SolventDripsOption solventDrips(int lifeTime, float size) {
        return new SolventDripsOption(SOLVENT_PARTICLE, lifeTime, size);
    }

    private static <T extends ParticleOptions> ParticleType<T> register(ResourceLocation name, ParticleType<T> type) {
        type.setRegistryName(name);
        REGISTRY.put(name, type);
        return type;
    }

    private static <T extends ParticleOptions> ParticleType<T> register(ResourceLocation name, ParticleOptions.Deserializer<T> dec, final Function<ParticleType<T>, Codec<T>> fn) {
        var type = new ParticleType<T>(false, dec) {
            @Override
            public @NotNull Codec<T> codec() {
                return fn.apply(this);
            }
        };

        return register(name, type);
    }

    @SubscribeEvent
    public static void registerParticleTypes(RegistryEvent.Register<ParticleType<?>> event) {
        REGISTRY.forEach((name, entry) -> {
            event.getRegistry().register(entry);
        });
    }

    @SubscribeEvent
    public static void registerParticles(ParticleFactoryRegisterEvent event) {
        var engine = Minecraft.getInstance().particleEngine;
        engine.register(THUNDER_SPARK, ThunderSparkParticle.Provider::new);
        engine.register(LASER_POINT, LaserPointParticle.Provider::new);
        engine.register(SOLVENT_PARTICLE, SolventDrips.Provider::new);
    }

}

