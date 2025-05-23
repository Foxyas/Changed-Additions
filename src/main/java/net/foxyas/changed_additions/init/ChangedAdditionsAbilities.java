
package net.foxyas.changed_additions.init;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.abilities.*;
import net.ltxprogrammer.changed.ability.AbstractAbility;
import net.ltxprogrammer.changed.entity.variant.TransfurVariant;
import net.ltxprogrammer.changed.init.ChangedEntities;
import net.ltxprogrammer.changed.init.ChangedTags;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

import static net.ltxprogrammer.changed.init.ChangedRegistry.ABILITY;

@Mod.EventBusSubscriber(modid = ChangedAdditionsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChangedAdditionsAbilities /*extends ChangedAbilities*/ {

    public static final DeferredRegister<AbstractAbility<?>> REGISTRY = ABILITY.createDeferred(ChangedAdditionsMod.MODID);
    public static final RegistryObject<LeapAbility> LEAP = REGISTRY.register("leap", LeapAbility::new);
    public static final RegistryObject<CarryAbility> CARRY = REGISTRY.register("carry", CarryAbility::new);
    public static final RegistryObject<DazedPuddleAbility> DAZED_PUDDLE_ABILITY = REGISTRY.register("dazed_puddle", DazedPuddleAbility::new);
    public static final RegistryObject<SoftenAbility> SOFTEN_ABILITY = REGISTRY.register("soften", SoftenAbility::new);
    public static final RegistryObject<CustomInteraction> CUSTOM_INTERACTION = REGISTRY.register("custom_interaction", CustomInteraction::new);
    public static final RegistryObject<WingFlapAbility> WING_FLAP_ABILITY = REGISTRY.register("wing_flap", WingFlapAbility::new);
    public static final RegistryObject<ClawsAbility> CLAWS_ABILITY = REGISTRY.register("claws", ClawsAbility::new);

    public static List<EntityType<?>> getCanGlideEntites() {
        //["form_dark_dragon", "form_dark_latex_yufeng", "form_latex_pink_yuin_dragon", "form_latex_red_dragon"]
        return List.of(ChangedEntities.DARK_LATEX_YUFENG.get(), ChangedEntities.LATEX_PINK_YUIN_DRAGON.get(), ChangedEntities.DARK_DRAGON.get(), ChangedEntities.LATEX_RED_DRAGON.get());
    }

    public static void addUniversalAbilities(TransfurVariant.UniversalAbilitiesEvent event) {
        event.addAbility(event.isOfTag(ChangedTags.EntityTypes.LATEX).and(event.isNotOfTag(ChangedTags.EntityTypes.PARTIAL_LATEX)), SOFTEN_ABILITY);
        event.addAbility(event.isOfTag(ChangedAdditionsTags.EntityTypes.HAVE_CLAWS).and(event.isNotOfTag(ChangedAdditionsTags.EntityTypes.NO_CLAWS)), CLAWS_ABILITY);
        event.addAbility(event.isOfTag(ChangedAdditionsTags.EntityTypes.FELINES_LIKE), LEAP);
        event.addAbility(entityType -> getCanGlideEntites().contains(entityType), WING_FLAP_ABILITY);
    }

}