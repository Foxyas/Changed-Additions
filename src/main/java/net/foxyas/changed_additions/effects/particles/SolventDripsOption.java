package net.foxyas.changed_additions.effects.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

public class SolventDripsOption implements ParticleOptions {
    public static final Deserializer<SolventDripsOption> DESERIALIZER = new Deserializer<>() {
        @Override
        public @NotNull SolventDripsOption fromNetwork(@NotNull ParticleType<SolventDripsOption> type, FriendlyByteBuf buffer) {
            int lifeTime = buffer.readInt();
            float Size = buffer.readFloat();
            return new SolventDripsOption(type, lifeTime, Size);
        }

        @Override
        public @NotNull SolventDripsOption fromCommand(@NotNull ParticleType<SolventDripsOption> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            if (!reader.canRead()) {
                throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerExpectedInt().create();
            }

            int lifeTime;
            try {
                lifeTime = reader.readInt();
            } catch (Exception e) {
                throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidInt().create(reader);
            }

            reader.expect(' '); // <- espera mais um espaço
            if (!reader.canRead()) {
                throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerExpectedInt().create();
            }

            float size;
            try {
                size = reader.readFloat();
            } catch (Exception e) {
                throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidFloat().create(reader);
            }

            try {
                return new SolventDripsOption(type, lifeTime, size);
            } catch (Exception e) {
                throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidInt().create(reader);
            }
        }
    };

    public static Codec<SolventDripsOption> codec(ParticleType<SolventDripsOption> type) {

        return RecordCodecBuilder.create(builder -> builder.group(
                Codec.INT.fieldOf("lifetime").forGetter(SolventDripsOption::getLifeTime),
                Codec.FLOAT.fieldOf("size").forGetter(SolventDripsOption::getSize)
        ).apply(builder, (lifetime, size) -> new SolventDripsOption(type, lifetime, size)));
    }

    private ParticleType<SolventDripsOption> Type;

    private final int lifeTime;
    private final float size;

    public SolventDripsOption(ParticleType<SolventDripsOption> type, int lifetime, float size) {
        super();
        this.Type = type;
        this.lifeTime = lifetime;
        this.size = size;
    }


    @Override
    public @NotNull ParticleType<SolventDripsOption> getType() {
        return Type;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeInt(lifeTime);
        buffer.writeFloat(size);
    }

    @Override
    public @NotNull String writeToString() {
        return "solvent_drips";
    }


    public float getSize() {
        return size;
    }

    public int getLifeTime() {
        return lifeTime;
    }
}
