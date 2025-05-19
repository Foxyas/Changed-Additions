package net.foxyas.changed_additions.process.quickTimeEvents.commonSide;

import net.foxyas.changed_additions.process.util.FoxyasUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ConscienceQTEDEBUG {

    @SubscribeEvent
    public static void DEBUG(ServerChatEvent event) {
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

            ConscienceQuickTimeEventType type = switch (typeStr) {
                case "arrows_right" -> ConscienceQuickTimeEventType.ARROWS_RIGHT;
                case "arrows_left" -> ConscienceQuickTimeEventType.ARROWS_LEFT;
                case "wasd" -> ConscienceQuickTimeEventType.WASD;
                case "wdsa" -> ConscienceQuickTimeEventType.WDSA;
                case "space" -> ConscienceQuickTimeEventType.SPACE;
                default -> null;
            };

            if (type == null) {
                player.displayClientMessage(new TextComponent("Tipo inválido! Use arrows, wasd ou space.").withStyle(ChatFormatting.RED), false);
                return;
            }

            ConscienceQTEManager.addQTE(player, new ConscienceQuickTimeEvent(player, type, FoxyasUtils.StringToInt(parts[2]))); // 100 ticks = 5 segundos

            player.displayClientMessage(new TextComponent("Quick Time Event iniciado: " + type.name()).withStyle(ChatFormatting.GREEN), false);
        }
    }
}

