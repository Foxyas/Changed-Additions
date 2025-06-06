
package net.foxyas.changed_additions.init;

import net.minecraft.world.level.GameRules;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChangedAdditionsGameRules {
	public static final GameRules.Key<GameRules.BooleanValue> CHANGED_ADDITIONS_CREATURE_DIETS = GameRules.register("changed_additions:CreatureDiets", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
	public static final GameRules.Key<GameRules.BooleanValue> CHANGED_ADDITIONS_FIGHT_TO_KEEP_CONSCIENCE = GameRules.register("changed_additions:fight_to_keep_consciousness", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
}
