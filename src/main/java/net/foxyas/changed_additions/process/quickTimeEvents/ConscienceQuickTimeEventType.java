package net.foxyas.changed_additions.process.quickTimeEvents;

import com.mojang.datafixers.util.Pair;
import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public enum ConscienceQuickTimeEventType {
    SPACE(List.of(InputKey.SPACE), new Pair<>(64, 32)),
    WASD(List.of(InputKey.W, InputKey.A, InputKey.S, InputKey.D), new Pair<>(64, 32)),
    ARROWS(List.of(InputKey.UP, InputKey.RIGHT, InputKey.DOWN, InputKey.LEFT), new Pair<>(64, 32));

    private final List<InputKey> sequence;
    private final Pair<Integer,Integer> ImageDimensions;

    ConscienceQuickTimeEventType(List<InputKey> sequence, Pair<Integer,Integer> ImageSize) {
        this.sequence = sequence;
        this.ImageDimensions = ImageSize;
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

    public List<InputKey> getSequence() {
        return sequence;
    }
    public Pair<Integer, Integer> getImageDimensions() {
        return ImageDimensions;
    }
    public ResourceLocation getKeyTexture() {
        return ChangedAdditionsMod.modResource("textures/overlays/qte/" + name().toLowerCase() + ".png");
    }

}
