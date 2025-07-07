
package net.foxyas.changed_additions.init;

import net.minecraft.world.level.GameRules;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChangedAdditionsGameRules {
	public static final GameRules.Key<GameRules.BooleanValue> CREATURE_DIETS = GameRules.register("changed_additions:doCreatureDiets", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
	public static final GameRules.Key<GameRules.BooleanValue> FIGHT_TO_KEEP_CONSCIENCE = GameRules.register("changed_additions:fight_to_keep_consciousness", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
	public static final GameRules.Key<GameRules.BooleanValue> NEED_FULL_SOURCE_TO_SPREAD = GameRules.register("changed_additions:blocksNeedFullSourceToSpread", GameRules.Category.MISC, GameRules.BooleanValue.create(false));

}
