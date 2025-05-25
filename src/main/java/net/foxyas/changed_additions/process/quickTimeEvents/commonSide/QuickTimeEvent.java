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
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuickTimeEvent {

    private final QuickTimeEventSequenceType SequenceType;
    private final QuickTimeEventType Type;
    private final List<InputKey> sequence;
    // --- Campos principais ---
    private Player player;
    private int currentIndex = 0;
    private int ticksRemaining;
    private boolean finished = false;
    private float progress = 0.0f;
    private boolean isHolding = false;
    private InputKey lastKeyPressed = null;

    // --- Construtores ---
    public QuickTimeEvent(@Nullable Player player, QuickTimeEventSequenceType SequenceType, QuickTimeEventType type, int durationTicks) {
        this.player = player;
        this.SequenceType = SequenceType;
        this.Type = type;
        this.sequence = new ArrayList<>(SequenceType.getSequence());
        this.ticksRemaining = durationTicks;
    }

    public QuickTimeEvent(@Nullable Player player, Random random, QuickTimeEventType type, int durationTicks) {
        this(player, QuickTimeEventSequenceType.getRandom(random), type, durationTicks);
    }

    public QuickTimeEvent(@Nullable Player player, Random random, Random randomType, int durationTicks) {
        this(player, QuickTimeEventSequenceType.getRandom(random), QuickTimeEventType.getRandom(randomType), durationTicks);
    }

    public static QuickTimeEvent loadFromTag(Player player, CompoundTag tag) {
        QuickTimeEventSequenceType sequenceType;
        QuickTimeEventType type;

        try {
            sequenceType = QuickTimeEventSequenceType.valueOf(tag.getString("QTESequenceType"));
        } catch (IllegalArgumentException e) {
            ChangedAdditionsMod.LOGGER.info("Something goes wrong when loading the tag from{}\n QTESequenceTypeTag:{}", player.getName().getString(), tag.getString("QTESequenceType"));
            ChangedAdditionsMod.LOGGER.error("QTESequenceTypeTag Erro:{}", e.getMessage());
            sequenceType = QuickTimeEventSequenceType.WASD;
        }

        try {
            type = QuickTimeEventType.valueOf(tag.getString("QTEType"));
        } catch (IllegalArgumentException e) {
            ChangedAdditionsMod.LOGGER.info("Something goes wrong when loading the tag from{}\n QTETypeTag:{}", player.getName().getString(), tag.getString("QTEType"));
            ChangedAdditionsMod.LOGGER.error("QTETypeTag Erro:{}", e.getMessage());
            type = QuickTimeEventType.GENERIC;
        }

        QuickTimeEvent qte = new QuickTimeEvent(player, sequenceType, type, tag.getInt("TicksRemaining"));
        qte.currentIndex = tag.getInt("CurrentIndex");
        qte.progress = tag.getFloat("Progress");
        qte.finished = tag.getBoolean("Finished");
        return qte;
    }


    public CompoundTag saveToTag() {
        CompoundTag tag = new CompoundTag();
        tag.putString("QTESequenceType", SequenceType.name());
        tag.putString("QTEType", Type.name());
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

        if (this.getType() == QuickTimeEventType.FIGHT_TO_KEEP_CONSCIENCE) {
            TransfurVariantInstance<?> variant = ProcessTransfur.getPlayerTransfurVariant(player);
            if (variant != null && variant.getTransfurProgression(0) > 0.9f) {
                ticksRemaining -= 5;
            }
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

        if (key == getCurrentExpectedKey() && !isHolding) {
            progress = Math.min(2.0f, progress + 0.1f);
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
            if (this.getType() == QuickTimeEventType.FIGHT_TO_KEEP_CONSCIENCE) {
                player.displayClientMessage(new TranslatableComponent("changed_additions.fight_conscience.success"), true);
            } else if (this.getType() == QuickTimeEventType.STRUGGLE) {
                player.displayClientMessage(new TranslatableComponent("changed_additions.struggle.success"), true);
            } else if (this.getType() == QuickTimeEventType.GENERIC) {
                player.displayClientMessage(new TranslatableComponent("changed_additions.generic.success"), true);
            }
        }
    }

    private void handleFail() {
        if (player != null) {
            if (this.getType() == QuickTimeEventType.FIGHT_TO_KEEP_CONSCIENCE) {
                player.displayClientMessage(new TranslatableComponent("changed_additions.fight_conscience.fail"), true);

                TransfurVariantInstance<?> variant = ProcessTransfur.getPlayerTransfurVariant(player);
                if (variant != null) {
                    player.hurt(ChangedAdditionsDamageSources.CONSCIENCE_LOST, 10000);
                    PlayerUtil.UnTransfurPlayer(player);
                }
            } else if (this.getType() == QuickTimeEventType.STRUGGLE) {
                player.displayClientMessage(new TranslatableComponent("changed_additions.struggle.fail"), true);
            } else if (this.getType() == QuickTimeEventType.GENERIC) {
                player.displayClientMessage(new TranslatableComponent("changed_additions.generic.fail"), true);
            }
        }
    }

    // --- Getters ---
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public QuickTimeEventSequenceType getSequenceType() {
        return SequenceType;
    }

    public List<InputKey> getSequence() {
        return sequence;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    // --- Setters ---
    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public int getTicksRemaining() {
        return ticksRemaining;
    }

    public void setTicksRemaining(int ticksRemaining) {
        this.ticksRemaining = ticksRemaining;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public boolean isHoldingKey() {
        return isHolding;
    }

    public boolean isHolding() {
        return isHolding;
    }

    public void setHolding(boolean isHolding) {
        this.isHolding = isHolding;
    }

    public InputKey getLastKeyPressed() {
        return lastKeyPressed;
    }

    public void setLastKeyPressed(InputKey lastKeyPressed) {
        this.lastKeyPressed = lastKeyPressed;
    }

    public InputKey getCurrentExpectedKey() {
        return sequence.get(currentIndex);
    }

    public QuickTimeEventType getType() {
        return this.Type;
    }
}