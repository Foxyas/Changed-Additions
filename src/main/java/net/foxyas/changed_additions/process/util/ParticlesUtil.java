package net.foxyas.changed_additions.process.util;

import net.ltxprogrammer.changed.effect.particle.ColoredParticleOption;
import net.ltxprogrammer.changed.init.ChangedParticles;
import net.ltxprogrammer.changed.util.Color3;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.awt.*;

public class ParticlesUtil {

    public static void sendColorTransitionParticles(Level level, double x, double y, double z,
                                                    float redStart, float greenStart, float blueStart,
                                                    float redEnd, float greenEnd, float blueEnd,
                                                    float size, float XV, float YV, float ZV, int count, float speed) {

        // Criar a opção de partícula para transição de cor usando Vector3f
        Vector3f startColor = new Vector3f(redStart, greenStart, blueStart);
        Vector3f endColor = new Vector3f(redEnd, greenEnd, blueEnd);
        DustColorTransitionOptions particleOptions = new DustColorTransitionOptions(startColor, endColor, size);

        // Enviar as partículas
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(particleOptions,
                    x, y + 1, z, count, XV, YV, ZV, speed);
        }
    }

    public static void sendColorTransitionParticles(Level level, Player player,
                                                    float redStart, float greenStart, float blueStart,
                                                    float redEnd, float greenEnd, float blueEnd,
                                                    float size, float XV, float YV, float ZV, int count, float speed) {
        sendColorTransitionParticles(level, player.getX(), player.getY(), player.getZ(), redStart, greenStart, blueStart, redEnd, greenEnd, blueEnd, size, XV, YV, ZV, count, speed);
    }

    public static void sendColorTransitionParticles(Level level, Player player,
                                                    Color startColor, Color endColor,
                                                    float size, float XV, float YV, float ZV, int count, float speed) {
        sendColorTransitionParticles(level, player.getX(), player.getY(), player.getZ(), startColor.getRed() / 255f, startColor.getGreen() / 255f, startColor.getBlue() / 255f, endColor.getRed() / 255f, endColor.getGreen() / 255f, endColor.getBlue() / 255f, size, XV, YV, ZV, count, speed);
    }

    public static void sendDripParticles(Level level, Entity entity, float middle,
                                         float red, float green, float blue, float XV, float YV, float ZV, int count, float speed) {

        // Criar a opção de partícula para transição de cor usando Vector3f
        ColoredParticleOption particleOptions = ChangedParticles.drippingLatex(new Color3(red, green, blue));

        // Enviar as partículas
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(particleOptions,
                    entity.getX(), entity.getY() + middle, entity.getZ(), count, XV, YV, ZV, speed);
        }
    }


    public static void sendDripParticles(Level level, Entity entity, float middle, String color, float XV, float YV, float ZV, int count, float speed) {

        // Criar a opção de partícula para transição de cor usando Vector3f
        ColoredParticleOption particleOptions = ChangedParticles.drippingLatex(Color3.getColor(color));

        // Enviar as partículas
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(particleOptions,
                    entity.getX(), entity.getY() + middle, entity.getZ(), count, XV, YV, ZV, speed);
        }
    }

    public static void sendParticles(Level level, ParticleOptions particleOptions, BlockPos entity, float XV, float YV, float ZV, int count, float speed) {
        // Enviar as partículas
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(particleOptions,
                    entity.getX(), entity.getY(), entity.getZ(), count, XV, YV, ZV, speed);
        }
    }

    public static void sendParticles(Level level, ParticleOptions particleOptions, Vec3 entity, float XV, float YV, float ZV, int count, float speed) {
        // Enviar as partículas
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(particleOptions,
                    entity.x(), entity.y(), entity.z(), count, XV, YV, ZV, speed);
        }
    }

    public static void sendParticles(Level level, ParticleOptions particleOptions, double x, double y, double z, double XV, double YV, double ZV, int count, float speed) {
        // Enviar as partículas
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(particleOptions,
                    x, y, z, count, XV, YV, ZV, speed);
        }
    }

    public static void sendParticlesinClient(Level level, ParticleOptions particleOptions, double x, double y, double z, double XV, double YV, double ZV, int count) {
        // Enviar as partículas
        if (level instanceof ClientLevel clientLevel) {
            for (int i = 0; i < count; i++) {
                clientLevel.addParticle(particleOptions,
                        x, y, z, XV, YV, ZV);
            }
        }
    }

}