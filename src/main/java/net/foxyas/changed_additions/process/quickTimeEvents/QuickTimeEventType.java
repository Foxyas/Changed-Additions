package net.foxyas.changed_additions.process.quickTimeEvents;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public enum QuickTimeEventType {
    SPACE(List.of(InputKey.SPACE)),
    WASD(List.of(InputKey.W, InputKey.A, InputKey.S, InputKey.D)),
    ARROWS(List.of(InputKey.UP, InputKey.RIGHT, InputKey.DOWN, InputKey.LEFT));

    private final List<InputKey> sequence;

    QuickTimeEventType(List<InputKey> sequence) {
        this.sequence = sequence;
    }

    public List<InputKey> getSequence() {
        return sequence;
    }

    @Nullable
    public static QuickTimeEventType getFromSequence(List<InputKey> sequence) {
        for (QuickTimeEventType type : values()) {
            if (type.getSequence().equals(sequence)) {
                return type;
            }
        }
        return null;
    }

    public ResourceLocation getKeyTexture(){
        return ChangedAdditionsMod.modResource("textures/overlays/qte/" + name().toLowerCase() + ".png");
    }
}
