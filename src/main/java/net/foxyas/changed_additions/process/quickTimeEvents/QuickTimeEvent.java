package net.foxyas.changed_additions.process.quickTimeEvents;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class QuickTimeEvent {

    private final Player player;
    private final QuickTimeEventType type;
    private final List<InputKey> sequence;
    private int currentIndex = 0;
    private int ticksRemaining;
    private boolean finished = false;
    private float progress = 0.0f;

    private boolean isHolding = false;
    private InputKey lastKeyPressed = null;

    public QuickTimeEvent(Player player, QuickTimeEventType type, int durationTicks) {
        this.player = player;
        this.type = type;
        this.sequence = new ArrayList<>(type.getSequence());
        this.ticksRemaining = durationTicks;
    }


    public static QuickTimeEvent loadFromTag(Player player, CompoundTag tag) {
        QuickTimeEventType type = QuickTimeEventType.valueOf(tag.getString("QTEType"));
        QuickTimeEvent qte = new QuickTimeEvent(player, type, tag.getInt("TicksRemaining"));
        qte.currentIndex = tag.getInt("CurrentIndex");
        qte.progress = tag.getFloat("Progress");
        qte.finished = tag.getBoolean("Finished");
        return qte;
    }

    public CompoundTag saveToTag() {
        CompoundTag tag = new CompoundTag();
        tag.putString("QTEType", type.name());
        tag.putInt("CurrentIndex", currentIndex);
        tag.putInt("TicksRemaining", ticksRemaining);
        tag.putFloat("Progress", progress);
        tag.putBoolean("Finished", finished);
        return tag;
    }

    public void tick() {
        if (finished) return;

        ticksRemaining--;
        if (ticksRemaining <= 0) {
            finish();
        }
    }

    public void applyKeyInput(InputKey key, int action) {
        if (finished) return;

        this.isHolding = (action == GLFW.GLFW_REPEAT);
        this.lastKeyPressed = key;

        if (key == getCurrentExpectedKey()) {
            progress = Math.min(1.0f, progress + 0.1f);
            currentIndex = (currentIndex + 1) % sequence.size();
        } else {
            progress = Math.max(0.0f, progress - 0.1f);
        }
    }

    private void finish() {
        finished = true;
        // Você pode enviar evento ou chamar callbacks aqui
    }

    public boolean isFinished() {
        return finished;
    }

    public float getProgress() {
        return progress;
    }

    public int getTicksRemaining() {
        return ticksRemaining;
    }

    public boolean isHoldingKey() {
        return isHolding;
    }

    public InputKey getLastKeyPressed() {
        return lastKeyPressed;
    }

    public InputKey getCurrentExpectedKey() {
        return sequence.get(currentIndex);
    }

    public QuickTimeEventType getType() {
        return type;
    }

    public Player getPlayer() {
        return player;
    }

    public List<InputKey> getSequence() {
        return sequence;
    }

    public boolean isHolding() {
        return isHolding;
    }


}
