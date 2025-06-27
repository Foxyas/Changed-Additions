package net.foxyas.changed_additions.init;

import com.mojang.serialization.Codec;
import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.effects.particles.*;
import net.ltxprogrammer.changed.Changed;
import net.ltxprogrammer.changed.util.Color3;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChangedAdditionsParticles {
    public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ChangedAdditionsMod.MODID);
    public static final RegistryObject<ParticleType<ThunderSparkOption>> THUNDER_SPARK = register("thunder_spark", ThunderSparkOption.DESERIALIZER, ThunderSparkOption::codec);
    public static final RegistryObject<ParticleType<LaserPointParticle.Option>> LAZER_POINT = register("laser_point", LaserPointParticle.Option.DESERIALIZER, LaserPointParticle.Option::codec);
    public static final RegistryObject<ParticleType<SolventDripsOption>> SOLVENT_PARTICLE = register("solvent_drips", SolventDripsOption.DESERIALIZER, SolventDripsOption::codec);

    public static ThunderSparkOption thunderSpark(int lifeSpam) {
        return new ThunderSparkOption(THUNDER_SPARK.get(), lifeSpam);
    }

    public static LaserPointParticle.Option laserPoint(Entity entity, Color3 color, float alpha) {
        return new LaserPointParticle.Option(entity, color.toInt(), alpha);
    }

    public static LaserPointParticle.Option laserPoint(Entity entity, Color color) {
        return new LaserPointParticle.Option(entity, color.getRGB(), color.getAlpha());
    }

    public static SolventDripsOption solventDrips(int lifeTime, float size) {
        return new SolventDripsOption(SOLVENT_PARTICLE.get(), lifeTime, size);
    }

    private static <T extends ParticleOptions> RegistryObject<ParticleType<T>> register(String name, ParticleOptions.Deserializer<T> dec, final Function<ParticleType<T>, Codec<T>> fn) {
        var type = new ParticleType<T>(false, dec) {
            public Codec<T> codec() {
                return fn.apply(this);
            }
        };

        return REGISTRY.register(name, () -> new ParticleType<T>(false, dec) {
            public Codec<T> codec() {
                return fn.apply(this);
            }
        });
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(THUNDER_SPARK.get(), ThunderSparkParticle.Provider::new);
        event.registerSpriteSet(LAZER_POINT.get(), LaserPointParticle.Provider::new);
        event.registerSpriteSet(SOLVENT_PARTICLE.get(), SolventDrips.Provider::new);
    }

}

