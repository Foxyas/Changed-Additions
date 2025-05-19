package net.foxyas.changed_additions.process.quickTimeEvents.commonSide;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.init.ChangedAdditionsDamageSources;
import net.foxyas.changed_additions.network.packets.QTESyncPacket;
import net.foxyas.changed_additions.network.packets.utils.PacketsUtils;
import net.foxyas.changed_additions.process.quickTimeEvents.InputKey;
import net.foxyas.changed_additions.process.util.PlayerUtil;
import net.ltxprogrammer.changed.entity.variant.TransfurVariantInstance;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConscienceQuickTimeEvent {

    // --- Campos principais ---
    private Player player;
    private final ConscienceQuickTimeEventType type;
    private final List<InputKey> sequence;
    private int currentIndex = 0;
    private int ticksRemaining;
    private boolean finished = false;
    private float progress = 0.0f;
    private boolean isHolding = false;
    private InputKey lastKeyPressed = null;

    // --- Construtores ---
    public ConscienceQuickTimeEvent(@Nullable Player player, ConscienceQuickTimeEventType type, int durationTicks) {
        this.player = player;
        this.type = type;
        this.sequence = new ArrayList<>(type.getSequence());
        this.ticksRemaining = durationTicks;
    }

    public ConscienceQuickTimeEvent(@Nullable Player player, Random random, int durationTicks) {
        this(player, ConscienceQuickTimeEventType.getRandom(random), durationTicks);
    }

    // --- Salvamento/Carregamento NBT ---
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

    // --- Lógica principal ---
    public void tick() {
        if (player instanceof ServerPlayer serverPlayer) {
            PacketsUtils.sendToPlayer(ChangedAdditionsMod.PACKET_HANDLER, new QTESyncPacket(this), serverPlayer);
        }
        if (finished) return;

        TransfurVariantInstance<?> variant = ProcessTransfur.getPlayerTransfurVariant(player);
        if (variant != null && variant.getTransfurProgression(0) > 0.9f) {
            ticksRemaining -= 5;
        }

        ticksRemaining--;
        if (progress > 0.0f) {
            progress -= 0.01f;
        }

        if (ticksRemaining <= 0) {
            finish();
        }
    }

    public void applyKeyInput(InputKey key, int action) {
        if (finished) return;

        isHolding = (action == GLFW.GLFW_REPEAT);
        lastKeyPressed = key;

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

    private void handleSuccess() {
        if (player != null) {
            player.displayClientMessage(new TranslatableComponent("changed_additions.fight_conscience.success"), true);
        }
    }

    private void handleFail() {
        if (player != null) {
            player.displayClientMessage(new TranslatableComponent("changed_additions.fight_conscience.fail"), true);

            TransfurVariantInstance<?> variant = ProcessTransfur.getPlayerTransfurVariant(player);
            if (variant != null) {
                player.hurt(ChangedAdditionsDamageSources.CONSCIENCE_LOST, 10000);
                PlayerUtil.UnTransfurPlayer(player);
            }
        }
    }

    // --- Getters ---
    public Player getPlayer() {
        return player;
    }

    public ConscienceQuickTimeEventType getType() {
        return type;
    }

    public List<InputKey> getSequence() {
        return sequence;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getTicksRemaining() {
        return ticksRemaining;
    }

    public boolean isFinished() {
        return finished;
    }

    public float getProgress() {
        return progress;
    }

    public boolean isHoldingKey() {
        return isHolding;
    }

    public boolean isHolding() {
        return isHolding;
    }

    public InputKey getLastKeyPressed() {
        return lastKeyPressed;
    }

    public InputKey getCurrentExpectedKey() {
        return sequence.get(currentIndex);
    }

    // --- Setters ---
    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public void setHolding(boolean isHolding) {
        this.isHolding = isHolding;
    }

    public void setLastKeyPressed(InputKey lastKeyPressed) {
        this.lastKeyPressed = lastKeyPressed;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setTicksRemaining(int ticksRemaining) {
        this.ticksRemaining = ticksRemaining;
    }
}