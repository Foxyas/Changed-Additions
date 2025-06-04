package net.foxyas.changed_additions.client.models.accessories;

import java.awt.*;

public enum DefaultClothesColors {
    RED(new Color(255, 0, 0)),
    GREEN(new Color(0, 255, 0)),
    BLUE(new Color(0, 0, 255)),
    YELLOW(new Color(255, 255, 0)),
    CYAN(new Color(0, 255, 255)),
    MAGENTA(new Color(255, 0, 255)),
    ORANGE(new Color(255, 165, 0)),
    PINK(new Color(255, 105, 180)),
    WHITE(new Color(255, 255, 255)),
    BLACK(new Color(0, 0, 0)),
    GRAY(new Color(135, 135, 135));

    public final Color color;

    DefaultClothesColors(Color color) {
        this.color = color;
    }

    // Construtor sem argumentos, caso queira usar valores padrão depois
    DefaultClothesColors() {
        this.color = new Color(255, 255, 255); // fallback: branco
    }

    public Color getColor() {
        return color;
    }

    public int getColorToInt() {
        return color.getRGB();
    }
}