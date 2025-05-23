package net.foxyas.changed_additions.network.packets;

import net.foxyas.changed_additions.process.quickTimeEvents.InputKey;
import net.foxyas.changed_additions.process.quickTimeEvents.commonSide.QTEManager;
import net.foxyas.changed_additions.process.quickTimeEvents.commonSide.QuickTimeEvent;
import net.foxyas.changed_additions.process.quickTimeEvents.commonSide.QuickTimeEventSequenceType;
import net.foxyas.changed_additions.process.quickTimeEvents.commonSide.QuickTimeEventType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class QTESyncPacket {
    private final String SequenceTypeName;
    private final List<Integer> sequence; // Enviar InputKey.ordinal()
    private final String Type;
    private final int currentIndex;
    private final int ticksRemaining;
    private final boolean finished;
    private final float progress;
    private final boolean isHolding;
    private final int lastKeyPressed; // Enviar ordinal ou -1 se null
    private final int PlayerId;

    public QTESyncPacket(QuickTimeEvent event) {
        this.PlayerId = event.getPlayer().getId();
        this.Type = event.getType().name();
        this.SequenceTypeName = event.getSequenceType().name();
        this.sequence = event.getSequence().stream().map(Enum::ordinal).toList();
        this.currentIndex = event.getCurrentIndex();
        this.ticksRemaining = event.getTicksRemaining();
        this.finished = event.isFinished();
        this.progress = event.getProgress();
        this.isHolding = event.isHoldingKey();
        this.lastKeyPressed = event.getLastKeyPressed() != null ? event.getLastKeyPressed().ordinal() : -1;
    }

    public QTESyncPacket(int PlayerID, String SequenceTypeName, List<Integer> sequence, String type, int currentIndex, int ticksRemaining, boolean finished, float progress, boolean isHolding, int lastKeyPressed) {
        this.PlayerId = PlayerID;
        this.SequenceTypeName = SequenceTypeName;
        this.sequence = sequence;
        this.Type = type;
        this.currentIndex = currentIndex;
        this.ticksRemaining = ticksRemaining;
        this.finished = finished;
        this.progress = progress;
        this.isHolding = isHolding;
        this.lastKeyPressed = lastKeyPressed;
    }


    public static void encode(QTESyncPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.PlayerId);
        buf.writeUtf(msg.SequenceTypeName);
        buf.writeVarInt(msg.sequence.size());
        for (int keyOrdinal : msg.sequence) {
            buf.writeVarInt(keyOrdinal);
        }
        buf.writeUtf(msg.Type);
        buf.writeInt(msg.currentIndex);
        buf.writeInt(msg.ticksRemaining);
        buf.writeBoolean(msg.finished);
        buf.writeFloat(msg.progress);
        buf.writeBoolean(msg.isHolding);
        buf.writeInt(msg.lastKeyPressed);
    }

    public static QTESyncPacket decode(FriendlyByteBuf buf) {
        int PlayerId = buf.readInt();
        String SequenceTypeName = buf.readUtf();
        int size = buf.readVarInt();
        List<Integer> sequence = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            sequence.add(buf.readVarInt());
        }
        String Type = buf.readUtf();
        int currentIndex = buf.readInt();
        int ticksRemaining = buf.readInt();
        boolean finished = buf.readBoolean();
        float progress = buf.readFloat();
        boolean isHolding = buf.readBoolean();
        int lastKeyPressed = buf.readInt();

        return new QTESyncPacket(PlayerId, SequenceTypeName, sequence, Type, currentIndex, ticksRemaining, finished, progress, isHolding, lastKeyPressed);
    }


    public void handle(Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context context = ctxSupplier.get();
        context.enqueueWork(() -> {
            if (context.getDirection().getReceptionSide().isClient()) {
                // Lado CLIENTE: atualiza o QTE do player local

                QuickTimeEventSequenceType sequenceType = QuickTimeEventSequenceType.valueOf(SequenceTypeName);
                QuickTimeEventType type = QuickTimeEventType.valueOf(Type);

                InputKey lastKey = lastKeyPressed >= 0 ? InputKey.values()[lastKeyPressed] : null;

                QuickTimeEvent qte = new QuickTimeEvent(null, sequenceType, type, ticksRemaining);
                qte.setCurrentIndex(currentIndex);
                qte.setFinished(finished);
                qte.setProgress(progress);
                qte.setHolding(isHolding);
                qte.setLastKeyPressed(lastKey);

                QTEManager.Client.addQTE(qte);
                QTEManager.Client.WhenSync();
            } else {
                // Pacote recebido no servidor, se for o caso (não deve acontecer aqui)
                // Pode ignorar ou lançar um log de erro
            }
        });

        context.setPacketHandled(true);
    }


}
