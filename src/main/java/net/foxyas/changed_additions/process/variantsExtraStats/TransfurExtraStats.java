package net.foxyas.changed_additions.process.variantsExtraStats;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class TransfurExtraStats {
    private final float MiningSpeedMultiplier;
    private final float RegenSpeedMultiplier;
    private final float FlySpeedMultiplier;
    private final float FallDmgMultiplier;
    private final ResourceLocation form;

    public TransfurExtraStats(ResourceLocation form, float... multipliers) {
        super();
        this.form = form;
        this.MiningSpeedMultiplier = multipliers[0];
        this.RegenSpeedMultiplier = multipliers[1];
        this.FlySpeedMultiplier = multipliers[2];
        this.FallDmgMultiplier = multipliers[3];
    }

    public TransfurExtraStats(ResourceLocation form, float miningSpeedMultiplier, float regenSpeedMultiplier, float flySpeedMultiplier, float fallDmgMultiplier) {
        super();
        this.form = form;
        this.MiningSpeedMultiplier = miningSpeedMultiplier;
        this.RegenSpeedMultiplier = regenSpeedMultiplier;
        this.FlySpeedMultiplier = flySpeedMultiplier;
        this.FallDmgMultiplier = fallDmgMultiplier;
    }

    private static ResourceLocation fixJsonExtension(ResourceLocation input) {
        String path = input.getPath();
        if (!path.endsWith(".json")) {
            path += ".json";
        }
        return new ResourceLocation(input.getNamespace(), path);
    }

    public static TransfurExtraStats fromJson(ResourceLocation id, JsonObject json) {
        float miningSpeedMultiplier = GsonHelper.getAsFloat(json, "miningSpeedMultiplier", 1f);
        float regenSpeedMultiplier = GsonHelper.getAsFloat(json, "regenSpeedMultiplier", 0f);
        float flySpeedMultiplier = GsonHelper.getAsFloat(json, "flySpeedMultiplier", 1f);
        float fallDmgMultiplier = GsonHelper.getAsFloat(json, "fallDmgMultiplier", 1f);
        String formStr = GsonHelper.getAsString(json, "form");
        return new TransfurExtraStats(
                new ResourceLocation(formStr),
                miningSpeedMultiplier,
                regenSpeedMultiplier,
                flySpeedMultiplier,
                fallDmgMultiplier);
    }

    public float getMiningSpeedMultiplier() {
        return MiningSpeedMultiplier;
    }

    public float getRegenSpeedMultiplier() {
        return RegenSpeedMultiplier;
    }

    public float getFlySpeedMultiplier() {
        return FlySpeedMultiplier;
    }

    public float getFallDmgMultiplier() {
        return FallDmgMultiplier;
    }


    public ResourceLocation getForm() {
        return form;
    }
}
