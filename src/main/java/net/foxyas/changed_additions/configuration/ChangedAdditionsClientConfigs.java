package net.foxyas.changed_additions.configuration;

import net.minecraftforge.common.ForgeConfigSpec;

public class ChangedAdditionsClientConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> SMOOTH_LASER_MOVIMENT;
    public static final ForgeConfigSpec.ConfigValue<Boolean> PLANTOIDS_VARIABLE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> PAT_OVERLAY;
    public static final ForgeConfigSpec.ConfigValue<Integer> PAT_OVERLAY_X;
    public static final ForgeConfigSpec.ConfigValue<Integer> PAT_OVERLAY_Y;
    public static final ForgeConfigSpec.ConfigValue<Boolean> PAW_STYLE_PAT_OVERLAY;
    public static final ForgeConfigSpec.ConfigValue<Boolean> WING_FLAP_INFO;
    public static final ForgeConfigSpec.ConfigValue<Boolean> USE_BLOW1_SOUND_INSTEAD_OF_CLICK;

    // Subcategoria: QuickTimeEvents.Info
    public static final ForgeConfigSpec.ConfigValue<Boolean> QTE_SHOW_INFO;
    public static final ForgeConfigSpec.ConfigValue<Boolean> QTE_SHOW_PROGRESS;
    public static final ForgeConfigSpec.ConfigValue<Boolean> QTE_SHOW_TICKS_LEFT;

    static {
        BUILDER.push("Animations / Movement");
        SMOOTH_LASER_MOVIMENT = BUILDER.comment("Make the Laser Moviment be smooth, it may cause the particule to be slower").define("Laser Smooth Moviment", false);
        BUILDER.pop();

        BUILDER.push("ModelsHandle");
        PLANTOIDS_VARIABLE = BUILDER.comment("Turn off the Plantoids [Female Chest Features]").define("Turn Off the Plantoids", false);
        BUILDER.pop();

        BUILDER.push("Overlays");
        WING_FLAP_INFO = BUILDER.comment("Display How much Ticks You have Hold the Wing Flap Ability").define("Wing Flap Ability Ticks Info", false);
        PAT_OVERLAY = BUILDER.comment("Set the Pat Overlay On or Off").define("Pat Overlay", true);
        PAT_OVERLAY_X = BUILDER.comment("Set the X pos of the pat overlay.").define("Pat Overlay X pos", 25);
        PAT_OVERLAY_Y = BUILDER.comment("Set the Y pos of the pat overlay.").define("Pat Overlay Y pos", 72);
        PAW_STYLE_PAT_OVERLAY = BUILDER.comment("Make the pat overlay use a paw icon instead of text").define("Paw Style Pat Overlay", true);
        BUILDER.pop();

        BUILDER.push("QuickTimeEvents");
        USE_BLOW1_SOUND_INSTEAD_OF_CLICK = BUILDER.comment("Use the Blow1 sound instead of the Default Minecraft click one").define("Use blow1 sound instead of click", false);

        BUILDER.push("Info");
        QTE_SHOW_INFO = BUILDER.comment("Enable QuickTimeEvent information display").define("Show Info", true);
        QTE_SHOW_PROGRESS = BUILDER.comment("Show the progress percentage of the QuickTimeEvent").define("Show Progress", true);
        QTE_SHOW_TICKS_LEFT = BUILDER.comment("Show how many ticks are left in the QTE").define("Show Ticks Left", true);
        BUILDER.pop(); // Info

        BUILDER.pop(); // QuickTimeEvents

        SPEC = BUILDER.build();
    }
}
