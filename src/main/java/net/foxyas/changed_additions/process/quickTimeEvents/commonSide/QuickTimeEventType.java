package net.foxyas.changed_additions.process.quickTimeEvents.commonSide;

import java.util.Random;

public enum QuickTimeEventType {
    FIGHT_TO_KEEP_CONSCIENCE,
    STRUGGLE,
    GENERIC;

    QuickTimeEventType() {
    }

    public static QuickTimeEventType getRandom(Random random) {
        QuickTimeEventType[] values = values();
        return values[random.nextInt(values.length)];
    }
}
