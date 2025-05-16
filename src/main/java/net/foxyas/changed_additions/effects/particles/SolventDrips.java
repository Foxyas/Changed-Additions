package net.foxyas.changed_additions.effects.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import org.jetbrains.annotations.NotNull;

public class SolventDrips extends TextureSheetParticle {
    public static Provider provider(SpriteSet spriteSet) {
        return new Provider(spriteSet);
    }

    public SpriteSet getSpriteSet() {
        return spriteSet;
    }

    public static class Provider implements ParticleProvider<SolventDripsOption> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(@NotNull SolventDripsOption typeIn, @NotNull ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SolventDrips(worldIn, typeIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }

    private final SpriteSet spriteSet;

    protected SolventDrips(ClientLevel world, SolventDripsOption data, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        float size = data.getSize();
        this.setSize(size, size);
        this.quadSize = size;
        var lifetime = data.getLifeTime();
        this.lifetime = (int) Math.max(1, lifetime + (this.random.nextInt(lifetime * 2) - lifetime));
        this.gravity = 0.1f;
        this.hasPhysics = true;
        this.xd = vx * 0.05;
        this.yd = vy * 0.05;
        this.zd = vz * 0.05;
        this.pickSprite(spriteSet);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        // Diminui o tamanho suavemente com o tempo
        this.quadSize *= 0.98f;

        // Finaliza se estiver abaixo de um tamanho mínimo (opcional)
        if (this.quadSize < 0.02f) {
            this.remove();
        }
    }
}
