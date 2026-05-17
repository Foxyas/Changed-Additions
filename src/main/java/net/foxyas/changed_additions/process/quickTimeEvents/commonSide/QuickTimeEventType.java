package net.foxyas.changed_additions.process.quickTimeEvents.commonSide;

import java.util.Random;
import net.minecraft.util.RandomSource;

public enum QuickTimeEventType {
    FIGHT_TO_KEEP_CONSCIENCE,
    STRUGGLE,
    GENERIC;

    QuickTimeEventType() {
    }

    public static QuickTimeEventType getRandom(RandomSource random) {
        QuickTimeEventType[] values = values();
        return values[random.nextInt(values.length)];
    }
}
