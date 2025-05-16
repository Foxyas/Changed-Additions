package net.foxyas.changed_additions.init;

import net.foxyas.changed_additions.advancements.critereon.PatEntityTrigger;
import net.minecraftforge.fml.common.Mod;

import static net.minecraft.advancements.CriteriaTriggers.register;

@Mod.EventBusSubscriber
public class ChangedAdditionsCriteriaTriggers {

    public static final PatEntityTrigger PAT_ENTITY_TRIGGER = register(new PatEntityTrigger());

}
