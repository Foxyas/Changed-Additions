package net.foxyas.changed_additions.process.quickTimeEvents;

import com.mojang.datafixers.util.Pair;
import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public enum ConscienceQuickTimeEventType {
    SPACE(List.of(InputKey.SPACE), new Pair<>(64, 32), new Pair<>(64, 16)),
    WASD(List.of(InputKey.W, InputKey.A, InputKey.S, InputKey.D), new Pair<>(64, 32), new Pair<>(16, 16)),
    ARROWS(List.of(InputKey.UP, InputKey.RIGHT, InputKey.DOWN, InputKey.LEFT), new Pair<>(64, 32), new Pair<>(16, 16));

    private final List<InputKey> sequence;
    private final Pair<Integer, Integer> ImageDimensions;
    private final Pair<Integer, Integer> KeyTypeSize;

    ConscienceQuickTimeEventType(List<InputKey> sequence, Pair<Integer, Integer> ImageMaxSheetSize, Pair<Integer, Integer> KeySize) {
        this.sequence = sequence;
        this.ImageDimensions = ImageMaxSheetSize;
        this.KeyTypeSize = KeySize;
    }

    @Nullable
    public static ConscienceQuickTimeEventType getFromSequence(List<InputKey> sequence) {
        for (ConscienceQuickTimeEventType type : values()) {
            if (type.getSequence().equals(sequence)) {
                return type;
            }
        }
        return null;
    }

    public static ConscienceQuickTimeEventType getRandom(Random random) {
        ConscienceQuickTimeEventType[] values = values();
        return values[random.nextInt(values.length)];
    }

    public List<InputKey> getSequence() {
        return sequence;
    }

    public Pair<Integer, Integer> getImageDimensions() {
        return ImageDimensions;
    }

    public Pair<Integer, Integer> getKeyTypeSize() {
        return KeyTypeSize;
    }

    public ResourceLocation getKeyTexture() {
        return ChangedAdditionsMod.modResource("textures/overlays/qte/" + name().toLowerCase() + ".png");
    }

}
