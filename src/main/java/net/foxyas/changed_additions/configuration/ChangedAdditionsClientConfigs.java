package net.foxyas.changed_additions.configuration;

import net.minecraftforge.common.ForgeConfigSpec;

public class ChangedAdditionsClientConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SMOOTH_LASER_MOVIMENT;
    public static final ForgeConfigSpec.ConfigValue<Boolean> PLANTOIDS_VARIABLE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> WING_FLAP_INFO;


    static {
        BUILDER.push("Animations / Movement");
        SMOOTH_LASER_MOVIMENT = BUILDER.comment("Make the Laser Moviment be smooth, it may cause the particule to be slower").define("Laser Smooth Moviment", false);
        BUILDER.pop();
        BUILDER.push("ModelsHandle");
        PLANTOIDS_VARIABLE = BUILDER.comment("Turn off the Plantoids [Female Chest Features]").define("Turn Off the Plantoids", false);
        BUILDER.pop();
        BUILDER.push("Overlays");
        WING_FLAP_INFO = BUILDER.comment("Display How much Ticks You have Hold the Wing Flap Ability").define("Wing Flap Ability Ticks Info", false);
        BUILDER.pop();


        SPEC = BUILDER.build();
    }

}
