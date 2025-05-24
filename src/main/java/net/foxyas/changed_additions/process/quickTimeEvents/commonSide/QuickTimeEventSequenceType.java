package net.foxyas.changed_additions.process.quickTimeEvents.commonSide;

import com.mojang.datafixers.util.Pair;
import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.process.quickTimeEvents.InputKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public enum QuickTimeEventSequenceType {
    SPACE(List.of(InputKey.SPACE), new Pair<>(64, 64), new Pair<>(64, 32)),
    WASD(List.of(InputKey.W, InputKey.A, InputKey.S, InputKey.D), new Pair<>(64, 32), new Pair<>(16, 16)),
    WDSA(List.of(InputKey.W, InputKey.D, InputKey.S, InputKey.A), new Pair<>(64, 32), new Pair<>(16, 16)),
    ARROWS_RIGHT(List.of(InputKey.UP, InputKey.RIGHT, InputKey.DOWN, InputKey.LEFT), new Pair<>(64, 32), new Pair<>(16, 16)),
    ARROWS_LEFT(List.of(InputKey.UP, InputKey.LEFT, InputKey.DOWN, InputKey.RIGHT), new Pair<>(64, 32), new Pair<>(16, 16));

    private final List<InputKey> sequence;
    private final Pair<Integer, Integer> ImageDimensions;
    private final Pair<Integer, Integer> KeyTypeSize;

    QuickTimeEventSequenceType(List<InputKey> sequence, Pair<Integer, Integer> ImageMaxSheetSize, Pair<Integer, Integer> KeySize) {
        this.sequence = sequence;
        this.ImageDimensions = ImageMaxSheetSize;
        this.KeyTypeSize = KeySize;
    }

    @Nullable
    public static QuickTimeEventSequenceType getFromSequence(List<InputKey> sequence) {
        for (QuickTimeEventSequenceType type : values()) {
            if (type.getSequence().equals(sequence)) {
                return type;
            }
        }
        return null;
    }

    public static QuickTimeEventSequenceType getRandom(Random random) {
        QuickTimeEventSequenceType[] values = values();
        return values[random.nextInt(values.length)];
    }

    public List<InputKey> getSequence() {
        return sequence;
    }

    public String getQteType() {
        String type = this.name().split("_", 2)[0];
        return type.toLowerCase();
    }

    private String capitalize(String str) {
        if (str.isEmpty()) return str;
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }


    public Pair<Integer, Integer> getImageDimensions() {
        return ImageDimensions;
    }

    public Pair<Integer, Integer> getKeyTypeSize() {
        return KeyTypeSize;
    }

    public ResourceLocation getKeyTexture() {
        return ChangedAdditionsMod.modResource("textures/overlays/qte/" + getQteType().toLowerCase() + ".png");
    }

}
