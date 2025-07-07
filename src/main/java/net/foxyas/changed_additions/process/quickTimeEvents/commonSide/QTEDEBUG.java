package net.foxyas.changed_additions.process.quickTimeEvents.commonSide;

import net.foxyas.changed_additions.process.util.FoxyasUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class QTEDEBUG {

    @SubscribeEvent
    public static void DEBUG(ServerChatEvent event) {
        if (true){return;} //disabled

        ServerPlayer player = event.getPlayer();
        String message = event.getMessage().toLowerCase();

        if (message.startsWith("_/qte")) {
            event.setCanceled(true); // impede que o comando apareça no chat normal

            String[] parts = message.split(" ");
            if (parts.length < 2) {
                player.displayClientMessage(new TextComponent("Uso: /qte <arrows|wasd|space>").withStyle(ChatFormatting.RED), false);
                return;
            }

            String typeStr = parts[1];

            QuickTimeEventSequenceType type = switch (typeStr) {
                case "arrows_right" -> QuickTimeEventSequenceType.ARROWS_RIGHT;
                case "arrows_left" -> QuickTimeEventSequenceType.ARROWS_LEFT;
                case "wasd" -> QuickTimeEventSequenceType.WASD;
                case "wdsa" -> QuickTimeEventSequenceType.WDSA;
                case "space" -> QuickTimeEventSequenceType.SPACE;
                default -> null;
            };

            if (type == null) {
                player.displayClientMessage(new TextComponent("Tipo inválido! Use arrows_[left or right], wasd, wdsa ou space.").withStyle(ChatFormatting.RED), false);
                return;
            }

            String typeStr2 = parts[2];

            QuickTimeEventType type2 = switch (typeStr2) {
                case "ftkc" -> QuickTimeEventType.FIGHT_TO_KEEP_CONSCIENCE;
                case "s" -> QuickTimeEventType.STRUGGLE;
                case "g" -> QuickTimeEventType.GENERIC;
                default -> null;
            };

            if (type2 == null) {
                player.displayClientMessage(new TextComponent("Tipo inválido! Use ftkc, s ou g.").withStyle(ChatFormatting.RED), false);
                return;
            }

            QTEManager.addQTE(player, new QuickTimeEvent(player, type, type2, FoxyasUtils.StringToInt(parts[3]))); // 100 ticks = 5 segundos

            player.displayClientMessage(new TextComponent("Quick Time Event iniciado: " + type.name()).withStyle(ChatFormatting.GREEN), false);
        }
    }
}

