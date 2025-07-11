package net.foxyas.changed_additions.process.variantsExtraStats;

import net.foxyas.changed_additions.configuration.ChangedAdditionsServerConfigs;
import net.foxyas.changed_additions.init.ChangedAdditionsGameRules;
import net.foxyas.changed_additions.init.ChangedAdditionsItems;
import net.foxyas.changed_additions.variants.TransfurVariantTags;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.beast.AbstractLatexWolf;
import net.ltxprogrammer.changed.entity.beast.AquaticEntity;
import net.ltxprogrammer.changed.entity.variant.TransfurVariant;
import net.ltxprogrammer.changed.entity.variant.TransfurVariantInstance;
import net.ltxprogrammer.changed.init.ChangedItems;
import net.ltxprogrammer.changed.init.ChangedRegistry;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber
public class CreatureDietsHandleProcedure {

    @SubscribeEvent
    public static void onUseItemFinish(LivingEntityUseItemEvent.Finish event) {
        if (event == null) return;

        LivingEntity livingEntity = event.getEntityLiving();
        ItemStack item = event.getItem();

        if (!(livingEntity instanceof Player player)) {
            return;
        }

        TransfurVariantInstance<?> latexInstance = ProcessTransfur.getPlayerTransfurVariant(player);
        if (latexInstance == null) return;


        Level world = player.getLevel();

        if (world.isClientSide) {
            return;
        }

        boolean isForWork = world.getGameRules().getBoolean(ChangedAdditionsGameRules.CREATURE_DIETS);
        boolean Debuffs;
        Debuffs = ChangedAdditionsServerConfigs.DEBUFFS.get();

        ChangedEntity ChangedEntity = latexInstance.getChangedEntity();
        TransfurVariant<?> variant = ChangedEntity.getSelfVariant();

        if (!item.isEdible()) return;

        List<DietType> dietType = determineDietType(ChangedEntity, variant);
        if (dietType.isEmpty()) return;

        boolean ShouldGiveDebuffs = (latexInstance.ageAsVariant < ChangedAdditionsServerConfigs.AGE_NEED.get());

        if (isForWork) {
            if (dietType.stream().anyMatch((diet -> diet.isDietItem(item)))) {
                applyFoodEffects(player, item, true);
                if (!world.isClientSide()) {
                    world.playSound(null, player, SoundEvents.GENERIC_EAT, SoundSource.MASTER, 1, 1.5f);
                }
            } else if (Debuffs) {
                if (dietType.stream().noneMatch((diet -> diet.isDietItem(item))) && !DietType.isNotFoodItem(item) && ShouldGiveDebuffs) {
                    applyFoodEffects(player, item, false);
                    applyMobEffects(player);
                    if (!world.isClientSide()) {
                        world.playSound(null, player, SoundEvents.GENERIC_EAT, SoundSource.MASTER, 1, 0f);
                    }
                }
            }
        }
    }

    private static void applyFoodEffects(Player player, ItemStack item) {
        int additionalFood = 4;
        float additionalSaturation = 3;

        player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() + additionalFood);
        player.getFoodData().setSaturation(player.getFoodData().getSaturationLevel() + additionalSaturation);
    }

    private static void applyMobEffects(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 60, 3, false, true, true));
        player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 160, 0, false, true, true,
                new MobEffectInstance(MobEffects.WEAKNESS, 5 * 20, 2)));
    }

    private static void applyFoodEffects(Player player, ItemStack item, boolean isGoodFood) {
        int additionalFood;
        float additionalSaturation;
        if (isGoodFood) {
            additionalFood = Objects.requireNonNull(item.getFoodProperties(player)).getNutrition() / 2;
            additionalSaturation = Objects.requireNonNull(item.getFoodProperties(player)).getSaturationModifier() / 2;
        } else {
            additionalFood = -Objects.requireNonNull(item.getFoodProperties(player)).getNutrition() / 4;
            additionalSaturation = -Objects.requireNonNull(item.getFoodProperties(player)).getSaturationModifier() / 4;
        }
        if (!(player.getFoodData().getFoodLevel() >= 20)) {
            player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() + additionalFood);
        } else if (!(player.getFoodData().getSaturationLevel() >= 20)) {
            player.getFoodData().setSaturation(player.getFoodData().getSaturationLevel() + additionalSaturation);
        }
    }

    private static List<DietType> determineDietType(ChangedEntity ChangedEntity, TransfurVariant<?> variant) {
        List<TransfurVariant<?>> NoDietList = List.of();
        List<DietType> dietTypeList = new ArrayList<>();
        if (NoDietList.contains(variant)) {
            return List.of();
        }
        if (isCatDiet(ChangedEntity, variant)) dietTypeList.add(DietType.CAT);
        if (isWolfDiet(ChangedEntity, variant)) dietTypeList.add(DietType.WOLF);
        if (isSpecialDiet(variant)) dietTypeList.add(DietType.SPECIAL);
        if (isFoxDiet(ChangedEntity, variant)) dietTypeList.add(DietType.FOX);
        if (isAquaticDiet(ChangedEntity, variant)) dietTypeList.add(DietType.AQUATIC);
        if (isDragonDiet(ChangedEntity, variant)) dietTypeList.add(DietType.DRAGON);

        return dietTypeList;
    }

    private static boolean isCatDiet(ChangedEntity entity, TransfurVariant<?> variant) {
        return entity.getType().getRegistryName().toString().contains("cat") || entity.getType().getRegistryName().toString().contains("tiger") ||
                variant.is(TransfurVariantTags.CAT_LIKE) ||
                variant.is(TransfurVariantTags.LEOPARD_LIKE) ||
                variant.is(TagKey.create(ChangedRegistry.TRANSFUR_VARIANT.get().getRegistryKey(),
                        new ResourceLocation("changed_additions:cat_diet")));
    }

    private static boolean isWolfDiet(ChangedEntity entity, TransfurVariant<?> variant) {
        return entity.getType().getRegistryName().toString().contains("dog") ||
                entity.getType().getRegistryName().toString().contains("wolf") ||
                entity instanceof AbstractLatexWolf ||
                variant.is(TransfurVariantTags.WOLF_LIKE) ||
                variant.is(TagKey.create(ChangedRegistry.TRANSFUR_VARIANT.get().getRegistryKey(),
                        new ResourceLocation("changed_additions:wolf_diet")));
    }

    private static boolean isSpecialDiet(TransfurVariant<?> variant) {
        return variant.is(TagKey.create(ChangedRegistry.TRANSFUR_VARIANT.get().getRegistryKey(),
                new ResourceLocation("changed_additions:special_diet")));
    }

    private static boolean isFoxDiet(ChangedEntity entity, TransfurVariant<?> variant) {
        return entity.getType().getRegistryName().toString().contains("fox") || variant.is(TransfurVariantTags.FOX_LIKE)
                || variant.is(TagKey.create(ChangedRegistry.TRANSFUR_VARIANT.get().getRegistryKey(),
                        new ResourceLocation("changed_additions:fox_diet")));
    }

    private static boolean isAquaticDiet(ChangedEntity entity, TransfurVariant<?> variant) {
        return entity instanceof AquaticEntity || entity.getType().getRegistryName().toString().contains("shark") ||
                variant.is(TransfurVariantTags.SHARK_LIKE) ||
                variant.is(TagKey.create(ChangedRegistry.TRANSFUR_VARIANT.get().getRegistryKey(),
                        new ResourceLocation("changed_additions:aquatic_like"))) ||
                variant.is(TagKey.create(ChangedRegistry.TRANSFUR_VARIANT.get().getRegistryKey(),
                        new ResourceLocation("changed_additions:aquatic_diet")));
    }

    private static boolean isDragonDiet(ChangedEntity entity, TransfurVariant<?> variant) {
        return entity.getType().getRegistryName().toString().contains("dragon") ||
                variant.is(TagKey.create(ChangedRegistry.TRANSFUR_VARIANT.get().getRegistryKey(),
                        new ResourceLocation("changed:dragon_like"))) ||
                variant.is(TagKey.create(ChangedRegistry.TRANSFUR_VARIANT.get().getRegistryKey(),
                        new ResourceLocation("changed_additions:dragon_diet")));
    }

    private enum DietType {
        CAT(List.of(
                Items.COD, Items.COOKED_COD, Items.SALMON, Items.COOKED_SALMON,
                Items.PUFFERFISH, Items.TROPICAL_FISH, Items.RABBIT, Items.COOKED_RABBIT, Items.BEEF, Items.COOKED_BEEF,
                Items.CHICKEN, Items.COOKED_CHICKEN, Items.PORKCHOP, Items.COOKED_PORKCHOP
        ), "changed_additions:cat_diet_list"),
        DRAGON(List.of(
                Items.COD, Items.COOKED_COD, Items.SALMON, Items.COOKED_SALMON,
                Items.PUFFERFISH, Items.TROPICAL_FISH, Items.RABBIT, Items.COOKED_RABBIT,
                Items.BEEF, Items.COOKED_BEEF, Items.CHICKEN, Items.COOKED_CHICKEN,
                Items.PORKCHOP, Items.COOKED_PORKCHOP, Items.MUTTON, Items.COOKED_MUTTON
        ), "changed_additions:dragon_diet_list"),
        WOLF(List.of(
                Items.RABBIT, Items.COOKED_RABBIT, Items.BEEF, Items.COOKED_BEEF,
                Items.CHICKEN, Items.COOKED_CHICKEN, Items.PORKCHOP, Items.COOKED_PORKCHOP,
                Items.MUTTON, Items.COOKED_MUTTON
        ), "changed_additions:wolf_diet_list"),
        FOX(List.of(
                Items.SWEET_BERRIES, Items.GLOW_BERRIES, Items.RABBIT, Items.COOKED_RABBIT,
                Items.BEEF, Items.COOKED_BEEF, Items.CHICKEN, Items.COOKED_CHICKEN,
                Items.PORKCHOP, Items.COOKED_PORKCHOP, Items.MUTTON, Items.COOKED_MUTTON
        ), "changed_additions:fox_diet_list"),
        AQUATIC(List.of(
                Items.DRIED_KELP, Items.COD, Items.COOKED_COD, Items.SALMON,
                Items.COOKED_SALMON, Items.PUFFERFISH, Items.TROPICAL_FISH
        ), "changed_additions:aquatic_diet_list"),
        SPECIAL(List.of(
                ChangedItems.ORANGE.get()
        ), "changed_additions:special_diet_list");

        private final List<Item> dietItems;
        private final String dietTag;

        DietType(List<Item> dietItems, String dietTag) {
            this.dietItems = dietItems;
            this.dietTag = dietTag;
        }

        public boolean isDietItem(ItemStack item) {
            return dietItems.contains(item.getItem()) ||
                    item.is(ItemTags.create(new ResourceLocation(dietTag)));
        }

        public static boolean isNotFoodItem(ItemStack item) {
            List<Item> NonFoodItemslist = List.of(
                    ChangedAdditionsItems.LAETHIN_SYRINGE.get());
            if (item.is(Items.ENCHANTED_GOLDEN_APPLE)) {
                return true;
            } else if (item.is(Items.POTION)) {
                return true;
            } else if (NonFoodItemslist.contains(item.getItem())
                    || item.is(ItemTags.create(new ResourceLocation("changed_additions:is_not_food")))) {
                return true;
            } else return item.is(Items.GOLDEN_APPLE);
        }
    }
}

