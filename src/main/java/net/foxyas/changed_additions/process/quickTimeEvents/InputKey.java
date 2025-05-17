package net.foxyas.changed_additions.process.quickTimeEvents;

import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.Optional;

public enum InputKey {
    W(GLFW.GLFW_KEY_W, 0, 0, -8, 72 + 20),
    A(GLFW.GLFW_KEY_A, 16, 0, -20-8, 72),
    S(GLFW.GLFW_KEY_S, 32, 0, -8, 72),
    D(GLFW.GLFW_KEY_D, 48, 0, 20-8, 72),
    UP(GLFW.GLFW_KEY_UP, 0, 16, -8, 72 + 20),
    DOWN(GLFW.GLFW_KEY_DOWN, 16, 16, -8, 72 - 20),
    LEFT(GLFW.GLFW_KEY_LEFT, 32, 16, -20-8, 72),
    RIGHT(GLFW.GLFW_KEY_RIGHT, 48, 16, 20-8, 72),
    SPACE(GLFW.GLFW_KEY_SPACE, 0, 32, -32, 72);

    public final int keyCode;
    public final int u;
    public final int v;
    public final int guiX;
    public final int guiY;

    InputKey(int keyCode, int u, int v, int guiX, int guiY) {
        this.keyCode = keyCode;
        this.u = u;
        this.v = v;
        this.guiX = guiX;
        this.guiY = guiY;
    }

    public static Optional<InputKey> fromKeyCode(int keyCode) {
        return Arrays.stream(values())
                .filter(k -> k.keyCode == keyCode)
                .findFirst();
    }

}
