package net.foxyas.changed_additions.effects.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.foxyas.changed_additions.configuration.ChangedAdditionsClientConfigs;
import net.foxyas.changed_additions.init.ChangedAdditionsParticles;
import net.foxyas.changed_additions.item.LaserPointer;
import net.foxyas.changed_additions.process.util.FoxyasUtils;
import net.foxyas.changed_additions.variants.TransfurVariantTags;
import net.ltxprogrammer.changed.init.ChangedTags;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.ltxprogrammer.changed.util.Color3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static net.foxyas.changed_additions.process.util.FoxyasUtils.manualRaycastIgnoringBlocks;
import static net.foxyas.changed_additions.process.util.PlayerUtil.getEntityHitLookingAt;

public class LaserPointParticle extends TextureSheetParticle {
    public static class Option implements ParticleOptions {
        public static final Deserializer<Option> DESERIALIZER = new Deserializer<>() {
            @Override
            public @NotNull LaserPointParticle.Option fromNetwork(@NotNull ParticleType<Option> type, FriendlyByteBuf buffer) {
                int entityId = buffer.readInt();
                int color = buffer.readInt(); // <- nova cor
                float alpha = buffer.readFloat();
                return new Option(entityId, color, alpha);
            }

            @Override
            public @NotNull LaserPointParticle.Option fromCommand(@NotNull ParticleType<Option> type, StringReader reader) throws CommandSyntaxException {
                reader.expect(' ');
                if (!reader.canRead()) {
                    throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerExpectedInt().create();
                }

                int entityId;
                try {
                    entityId = reader.readInt();
                } catch (Exception e) {
                    throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidInt().create(reader);
                }

                reader.expect(' '); // <- espera mais um espaço
                if (!reader.canRead()) {
                    throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerExpectedInt().create();
                }

                int color;
                try {
                    color = reader.readInt();
                } catch (Exception e) {
                    throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidInt().create(reader);
                }

                reader.expect(' '); // <- espera mais um espaço
                if (!reader.canRead()) {
                    throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerExpectedInt().create();
                }

                float alpha;
                try {
                    alpha = reader.readFloat();
                } catch (Exception e) {
                    throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidInt().create(reader);
                }

                return new Option(entityId, color, alpha);
            }
        };

        public static Codec<Option> codec(ParticleType<Option> type) {
            return RecordCodecBuilder.create(builder -> builder.group(
                    Codec.INT.fieldOf("entity").forGetter(Option::getEntityId),
                    Codec.INT.fieldOf("color").forGetter(Option::getColorAsInt),
                    Codec.FLOAT.fieldOf("alpha").forGetter(Option::getColorAlpha)
            ).apply(builder, Option::new));
        }

        private final int entityId, color;
        private float alpha;
        private final Entity entity;

        public Option(int entityId, int color, float alpha) {
            this.entityId = entityId;
            this.entity = null;
            this.color = color;
            this.alpha = alpha;
        }

        public Option(Entity entity, int color, float alpha) {
            this.entity = entity;
            this.entityId = entity.getId();
            this.color = color;
            this.alpha = alpha;
        }

        public int getEntityId() {
            return entityId;
        }

        public int getColorAsInt() {
            return color;
        }

        public Color3 getColorAsColor3() {
            return Color3.fromInt(color);
        }

        @Override
        public @NotNull ParticleType<?> getType() {
            return ChangedAdditionsParticles.LASER_POINT; // Substitua pelo seu ParticleType real
        }

        @Override
        public void writeToNetwork(FriendlyByteBuf buffer) {
            buffer.writeInt(entityId);
            buffer.writeInt(getColorAsInt());
            buffer.writeFloat(getColorAlpha());
        }

        @Override
        public @NotNull String writeToString() {
            return "EntityId:" + entityId + " ,Color:" + color;
        }

        public float getColorAlpha() {
            return alpha;
        }

        public void setColorAlpha(float alpha) {
            this.alpha = alpha;
        }
    }

    public static class Provider implements ParticleProvider<Option> {
        protected final SpriteSet sprite;

        public Provider(SpriteSet p_106394_) {
            this.sprite = p_106394_;
        }

        @Nullable
        @Override
        public Particle createParticle(Option type, ClientLevel level, double x, double y, double z,
                                       double xSpeed, double ySpeed, double zSpeed) {
            return new LaserPointParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, type, sprite);
        }
    }


    private final Entity entity;

    public final SpriteSet spriteSet;

    public LaserPointParticle(ClientLevel level, double x, double y, double z, double dx, double dy, double dz,
                              Option data, SpriteSet sprites) {
        super(level, x, y, z, dx, dy, dz);
        this.spriteSet = sprites;
        this.setSize(0.1f, 0.1f);
        this.quadSize = 0.1f;
        this.setAlpha(data.getColorAlpha());
        this.lifetime = 100; // você pode ajustar isso
        this.entity = level.getEntity(data.getEntityId());
        this.rCol = data.getColorAsColor3().red();
        this.bCol = data.getColorAsColor3().blue();
        this.gCol = data.getColorAsColor3().green();
        this.pickSprite(sprites);
    }

    @Override
    public int getLightColor(float p_105562_) {
        return 15728880;
    }

    @Override
    public void tick() {
        super.tick();

        if (!(entity instanceof LivingEntity owner) || !owner.isAlive()) {
            this.remove(); // Dono sumiu
            return;
        }

        if (level.isClientSide() && Minecraft.getInstance().player != null && ProcessTransfur.getPlayerTransfurVariantSafe(Minecraft.getInstance().player).map(
                transfurVariantInstance -> transfurVariantInstance.getParent().is(TransfurVariantTags.CAT_LIKE) || transfurVariantInstance.getParent().is(TransfurVariantTags.LEOPARD_LIKE)
        ).orElse(false)) {
            this.setSize(0.35f, 0.35f);
            this.quadSize = 0.35f;
        }


        ItemStack heldItem = owner.getUseItem();
        if (heldItem.isEmpty() || !(heldItem.getItem() instanceof LaserPointer) || !owner.isUsingItem()) {
            this.remove(); // Jogador parou de usar
            return;
        }

        HitResult result = owner.pick(LaserPointer.MAX_LASER_REACH, 0.0F, true);
        Vec3 hitPos;
        Direction face = null;

        EntityHitResult entityHitResult = getEntityHitLookingAt(owner, LaserPointer.MAX_LASER_REACH);

        boolean Subtract = false;
        if (entityHitResult != null) {
            hitPos = entityHitResult.getLocation();
            face = entityHitResult.getEntity().getDirection();
        } else if (result instanceof BlockHitResult blockResult) {
            BlockHitResult finalResult = blockResult;

            if (level.getBlockState(blockResult.getBlockPos()).is(ChangedTags.Blocks.LASER_TRANSLUCENT)) {
                Set<Block> blockSet = Objects.requireNonNull(ForgeRegistries.BLOCKS.tags())
                        .getTag(ChangedTags.Blocks.LASER_TRANSLUCENT).stream().collect(Collectors.toSet());
                finalResult = manualRaycastIgnoringBlocks(level, owner, LaserPointer.MAX_LASER_REACH, blockSet);
                Subtract = true;
            }

            hitPos = finalResult.getLocation();
            face = finalResult.getDirection();
            hitPos = FoxyasUtils.applyOffset(hitPos, face, !Subtract ? -0.025f : 0.025f);
        } else {
            hitPos = result.getLocation(); // fallback (geralmente miss)
        }

        /**/
        if (face != null) {
            // Aplica offset dinâmico baseado na direção
            double offset = !Subtract ? -0.05D : 0.05D;
            hitPos = hitPos.subtract(
                    face.getStepX() * offset,
                    face.getStepY() * offset,
                    face.getStepZ() * offset
            );
        }

        if (ChangedAdditionsClientConfigs.SMOOTH_LASER_MOVIMENT.get()) {
            moveToward(hitPos);
        } else {
            SetToward(hitPos);
        }
    }

    private void moveToward(Vec3 target) {
        double dx = target.x - this.x;
        double dy = target.y - this.y;
        double dz = target.z - this.z;

        double distanceSquared = dx * dx + dy * dy + dz * dz;

        if (distanceSquared >= 0.001) {
            double speed = 0.5;
            this.xd = dx * speed;
            this.yd = dy * speed;
            this.zd = dz * speed;

            this.x += this.xd;
            this.y += this.yd;
            this.z += this.zd;

            this.age = 0; // Reset idade para manter a partícula viva
        }
    }

    private void SetToward(Vec3 target) {
        this.x = target.x;
        this.y = target.y;
        this.z = target.z;

        this.age = 0; // Reset idade para manter a partícula viva
    }


    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }
}
