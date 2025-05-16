package net.foxyas.changed_additions.process.quickTimeEvents;

import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

@Mod.EventBusSubscriber
public class InputKeyTracker {

    private static final Set<InputKey> heldKeys = EnumSet.noneOf(InputKey.class);
    private static final Set<InputKey> pressedKeys = EnumSet.noneOf(InputKey.class);
    private static final Set<InputKey> pressedThisTick = EnumSet.noneOf(InputKey.class);

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        Optional<InputKey> optional = InputKey.fromKeyCode(event.getKey());
        if (optional.isEmpty()) return;

        InputKey key = optional.get();

        if (event.getAction() == GLFW.GLFW_PRESS) {
            pressedThisTick.add(key);
            heldKeys.add(key);
        } else if (event.getAction() == GLFW.GLFW_RELEASE) {
            heldKeys.remove(key);
        } else if (event.getAction() == GLFW.GLFW_REPEAT) {
            heldKeys.add(key);
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            pressedKeys.clear();
            pressedKeys.addAll(pressedThisTick);
            pressedThisTick.clear();
        }
    }

    // 🔎 Consulta se a tecla foi pressionada neste tick
    public static boolean wasPressed(InputKey key) {
        return pressedKeys.contains(key);
    }

    // 🔁 Consulta se a tecla está sendo segurada
    public static boolean isHeld(InputKey key) {
        return heldKeys.contains(key);
    }

    // 🧹 (Opcional) Resetar tudo, útil em tela de pause ou debug
    public static void reset() {
        heldKeys.clear();
        pressedKeys.clear();
        pressedThisTick.clear();
    }
}
