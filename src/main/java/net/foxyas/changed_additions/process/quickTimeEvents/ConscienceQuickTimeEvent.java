package net.foxyas.changed_additions.process.quickTimeEvents;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class ConscienceQuickTimeEvent {

    private final Player player;
    private final ConscienceQuickTimeEventType type;
    private final List<InputKey> sequence;
    private int currentIndex = 0;
    private int ticksRemaining;
    private boolean finished = false;
    private float progress = 0.0f;

    private boolean isHolding = false;
    private InputKey lastKeyPressed = null;

    public ConscienceQuickTimeEvent(Player player, ConscienceQuickTimeEventType type, int durationTicks) {
        this.player = player;
        this.type = type;
        this.sequence = new ArrayList<>(type.getSequence());
        this.ticksRemaining = durationTicks;
    }

    public static ConscienceQuickTimeEvent loadFromTag(Player player, CompoundTag tag) {
        ConscienceQuickTimeEventType type = ConscienceQuickTimeEventType.valueOf(tag.getString("QTEType"));
        ConscienceQuickTimeEvent qte = new ConscienceQuickTimeEvent(player, type, tag.getInt("TicksRemaining"));
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
        this.progress -= 0.01f;
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
        handleFinal();
        finished = true;
    }

    public void handleFinal() {
        if (progress >= 0.95f) {
            handleSuccess();
        } else {
            handleFail();
        }
    }

    private void handleFail() {
        this.player.displayClientMessage(new TextComponent("Fail") , true);
    }

    private void handleSuccess() {
        this.player.displayClientMessage(new TextComponent("Success") , true);
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

    public ConscienceQuickTimeEventType getType() {
        return type;
    }

    public Player getPlayer() {
        return player;
    }

    public List<InputKey> getSequence() {
        return sequence;
    }

}
