package net.foxyas.changed_additions.process.quickTimeEvents;

import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.Optional;

public enum InputKey {
    W(GLFW.GLFW_KEY_W, 0, 0, 100, 180),
    A(GLFW.GLFW_KEY_A, 16, 0, 80, 200),
    S(GLFW.GLFW_KEY_S, 32, 0, 100, 200),
    D(GLFW.GLFW_KEY_D, 48, 0, 120, 200),
    UP(GLFW.GLFW_KEY_UP, 0, 16, 100, 140),
    DOWN(GLFW.GLFW_KEY_DOWN, 16, 16, 100, 180),
    LEFT(GLFW.GLFW_KEY_LEFT, 32, 16, 80, 160),
    RIGHT(GLFW.GLFW_KEY_RIGHT, 48, 16, 120, 160),
    SPACE(GLFW.GLFW_KEY_SPACE, 0, 32, 100, 220);

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
