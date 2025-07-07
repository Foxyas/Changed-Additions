package net.foxyas.changed_additions.variants;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.entities.*;
import net.foxyas.changed_additions.entities.simple.*;
import net.foxyas.changed_additions.init.ChangedAdditionsAbilities;
import net.foxyas.changed_additions.init.ChangedAdditionsEntities;
import net.foxyas.changed_additions.init.ChangedAdditionsTags;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.PatronOC;
import net.ltxprogrammer.changed.entity.variant.GenderedPair;
import net.ltxprogrammer.changed.entity.variant.TransfurVariant;
import net.ltxprogrammer.changed.init.ChangedRegistry;
import net.ltxprogrammer.changed.init.ChangedTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChangedAdditionsTransfurVariants {
    //public static UseItemMode ABO = UseItemMode.create("ABO",false,true,true,true,true);
    //this is For Not Show The Hot Bar
    //.itemUseMode(ABO)

    public static final DeferredRegister<TransfurVariant<?>> REGISTRY = ChangedRegistry.TRANSFUR_VARIANT.createDeferred(ChangedAdditionsMod.MODID);

    public static List<TransfurVariant<?>> getRemovedVariantsList() {
        return List.of(FENG_QI_WOLF.get());
    }


    public static List<TransfurVariant<?>> getBossesVariantsList() {
        return List.of();
    }

    protected static List<TransfurVariant<?>> getOcVariantList() {
        return List.of(FENG_QI_WOLF.get());
    }

    public static boolean isVariantOC(TransfurVariant<?> transfurVariant, @Nullable Level level) {
        if (level != null && transfurVariant.getEntityType().create(level) instanceof PatronOC) {
            return true;
        } else return getOcVariantList().contains(transfurVariant);
    }

    public static boolean isVariantOC(ResourceLocation transfurVariantID, @Nullable Level level) {
        TransfurVariant<?> variantFromID = ChangedRegistry.TRANSFUR_VARIANT.get().getValue(transfurVariantID);
        if (variantFromID != null) {
            return isVariantOC(variantFromID, level);
        }
        return false;
    }

    public static boolean isVariantOC(String transfurVariantString, @Nullable Level level) {
        ResourceLocation transfurVariantID = ResourceLocation.parse(transfurVariantString);
        TransfurVariant<?> variantFromID = ChangedRegistry.TRANSFUR_VARIANT.get().getValue(transfurVariantID);
        if (variantFromID != null) {
            return isVariantOC(variantFromID, level);
        }
        return false;
    }


    public static final RegistryObject<TransfurVariant<LatexSnowFoxMale>> LATEX_SNOW_FOX_MALE = register("form_latex_snow_fox/male",
            () -> TransfurVariant.Builder.of(/*WHITE_LATEX_WOLF_MALE.get(),*/ChangedAdditionsEntities.LATEX_SNOW_FOX_MALE)
                    .nightVision().scares(Rabbit.class));

    public static final RegistryObject<TransfurVariant<LatexSnowFoxFemale>> LATEX_SNOW_FOX_FEMALE = register("form_latex_snow_fox/female",
            () -> TransfurVariant.Builder.of(/*WHITE_LATEX_WOLF_FEMALE.get(),*/ChangedAdditionsEntities.LATEX_SNOW_FOX_FEMALE)
                    .nightVision().scares(Rabbit.class));

    public static final RegistryObject<TransfurVariant<FengQIWolf>> FENG_QI_WOLF = register("form_feng_qi_wolf", TransfurVariant.Builder.of(ChangedAdditionsEntities.FENG_QI_WOLF)
            .stepSize(0.7F).breatheMode(TransfurVariant.BreatheMode.NORMAL).scares(List.of(Rabbit.class)).nightVision());

    public static final RegistryObject<TransfurVariant<LatexKitsuneMaleEntity>> LATEX_KITSUNE_MALE = register("form_latex_kitsune/male", () -> TransfurVariant.Builder.of(ChangedAdditionsEntities.LATEX_KITSUNE_MALE).addAbility(ChangedAdditionsAbilities.TELEPORT).stepSize(0.7F).breatheMode(TransfurVariant.BreatheMode.NORMAL).addAbility(ChangedAdditionsAbilities.CLAWS_ABILITY).scares(List.of(Rabbit.class)).nightVision());

    public static final RegistryObject<TransfurVariant<LatexKitsuneFemaleEntity>> LATEX_KITSUNE_FEMALE = register("form_latex_kitsune/female", () -> TransfurVariant.Builder.of(ChangedAdditionsEntities.LATEX_KITSUNE_FEMALE).addAbility(ChangedAdditionsAbilities.TELEPORT).stepSize(0.7F).breatheMode(TransfurVariant.BreatheMode.NORMAL).addAbility(ChangedAdditionsAbilities.CLAWS_ABILITY).scares(List.of(Rabbit.class)).nightVision());

    public static final RegistryObject<TransfurVariant<LatexCalicoCat>> LATEX_CALICO_CAT = register("form_latex_calico_cat", () -> TransfurVariant.Builder.of(ChangedAdditionsEntities.LATEX_CALICO_CAT).stepSize(0.7F).breatheMode(TransfurVariant.BreatheMode.NORMAL).addAbility(ChangedAdditionsAbilities.CLAWS_ABILITY).scares(List.of(Creeper.class)).nightVision());

    public static class Gendered {
        public static final GenderedPair<LatexSnowFoxMale, LatexSnowFoxFemale> LATEX_SNOW_FOXES = new GenderedPair<>(LATEX_SNOW_FOX_MALE, LATEX_SNOW_FOX_FEMALE);
        public static final GenderedPair<LatexKitsuneMaleEntity, LatexKitsuneFemaleEntity> KITSUNES = new GenderedPair<>(LATEX_KITSUNE_MALE, LATEX_KITSUNE_FEMALE);
    }

    private static <T extends ChangedEntity> RegistryObject<TransfurVariant<T>> register(String name, TransfurVariant.Builder<T> builder) {
        Objects.requireNonNull(builder);
        builder.addAbility(entityType -> {
            if (entityType.is(ChangedTags.EntityTypes.LATEX)
                    && !entityType.is(ChangedTags.EntityTypes.PARTIAL_LATEX)) {
                return ChangedAdditionsAbilities.SOFTEN_ABILITY.get();
            }
            return null;
        });
        builder.addAbility(entityType -> {
            if (entityType.is(ChangedAdditionsTags.EntityTypes.DRAGONS_ENTITIES)) {
                return ChangedAdditionsAbilities.WING_FLAP_ABILITY.get();
            }
            return null;
        });

        return REGISTRY.register(name, builder::build);
    }

    private static <T extends ChangedEntity> RegistryObject<TransfurVariant<T>> register(String name, Supplier<TransfurVariant.Builder<T>> builder) {
        return REGISTRY.register(name, () -> builder.get().addAbility(entityType -> {
                    if (entityType.is(ChangedTags.EntityTypes.LATEX)
                            && !entityType.is(ChangedTags.EntityTypes.PARTIAL_LATEX)) {
                        return ChangedAdditionsAbilities.SOFTEN_ABILITY.get();
                    }
                    return null;
                }).addAbility(entityType -> {
                    if (entityType.is(ChangedAdditionsTags.EntityTypes.DRAGONS_ENTITIES)) {
                        return ChangedAdditionsAbilities.SOFTEN_ABILITY.get();
                    }
                    return null;
                })
                .build());
    }
}