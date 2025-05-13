package net.foxyas.changed_additions.configuration;

import net.minecraftforge.common.ForgeConfigSpec;

public class ChangedAdditionsServerConfigs {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;
	public static final ForgeConfigSpec.ConfigValue<Boolean> DEBUFFS;
	public static final ForgeConfigSpec.ConfigValue<Double> AGE_NEED;

	static {
		BUILDER.push("Creatures Diets");
		DEBUFFS = BUILDER.comment("Add Debuffs for Player when they ate a non good food for they kind").worldRestart().define("When Eat Food Debuffs", false);
		AGE_NEED = BUILDER.comment("Set Amount of Transfur Age is need to not get debuffs when eat a food that is not of your diet").worldRestart().define("Age Need", (double) 15000);
		BUILDER.pop();

		SPEC = BUILDER.build();
	}

}
