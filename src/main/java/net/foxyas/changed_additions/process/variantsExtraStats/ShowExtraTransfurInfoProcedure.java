package net.foxyas.changed_additions.process.variantsExtraStats;

import net.foxyas.changed_additions.process.util.TransfurVariantUtils;
import net.foxyas.changed_additions.variants.ChangedAdditionsTransfurVariants;
import net.ltxprogrammer.changed.init.ChangedItems;
import net.ltxprogrammer.changed.item.Syringe;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber
public class ShowExtraTransfurInfoProcedure {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        execute(event.getEntity(), event.getItemStack(), event.getToolTip());
    }

    public static void execute(@Nullable Player entity, ItemStack itemstack, List<Component> tooltip) {
        if (entity == null || itemstack == null || tooltip == null) return;

        boolean isSyringe = itemstack.is(ChangedItems.LATEX_SYRINGE.get());
        boolean isFlask = itemstack.is(ChangedItems.LATEX_FLASK.get());
        boolean isArrow = itemstack.is(ChangedItems.LATEX_TIPPED_ARROW.get());

        if (!(isSyringe || isFlask || isArrow)) return;

        String form = itemstack.getOrCreateTag().getString("form");
        boolean isCreative = entity.isCreative();
        
        if (isCreative) {
            if (!Screen.hasShiftDown()) {
                String variantName = Component.translatable(Syringe.getVariantDescriptionId(itemstack)).getString();
                tooltip.add(Component.literal("Hold ").append(Component.literal("<Shift>").withStyle(style -> style.withColor(0xFFD700)))
                        .append(" to show the stats of the " + variantName + " Transfur"));
            }

            if (Screen.hasShiftDown()) {
                double hp = TransfurVariantUtils.GetExtraHp(form, entity);
                double swimSpeed = TransfurVariantUtils.GetSwimSpeed(form, entity);
                double landSpeed = TransfurVariantUtils.GetLandSpeed(form, entity);
                double jumpStrength = TransfurVariantUtils.GetJumpStrength(form);
                boolean canFlyOrGlide = TransfurVariantUtils.CanGlideAndFly(form);
                String miningStrength = TransfurVariantUtils.getMiningStrength(form);
                int index = Math.min(tooltip.size(), 3);

                double extraHp = (hp) / 2.0;
                tooltip.add(index, Component.translatable("text.changed_additions.additionalHealth")
                        .append("")
                        .append(extraHp == 0
                                ? Component.literal("§7None§r")
                                : Component.literal((extraHp > 0 ? "§a+" : "§c") + extraHp + "§r"))
                        .append(Component.translatable("text.changed_additions.additionalHealth.Hearts")));

                index++;
                tooltip.add(index, Component.translatable("text.changed_additions.miningStrength", miningStrength));

                index++;
                double landSpeedPct = (landSpeed - 1) * 100;
                tooltip.add(index, Component.translatable("text.changed_additions.land_speed")
                        .append("")
                        .append(landSpeedPct == 0
                                ? Component.literal("§7None§r")
                                : Component.literal((landSpeedPct > 0 ? "§a+" : "§c") + (int) landSpeedPct + "%")));

                index++;
                double swimSpeedPct = (swimSpeed - 1) * 100;
                tooltip.add(index, Component.translatable("text.changed_additions.swim_speed")
                        .append("")
                        .append(swimSpeedPct == 0
                                ? Component.literal("§7None§r")
                                : Component.literal((swimSpeedPct > 0 ? "§a+" : "§c") + (int) swimSpeedPct + "%")));

                index++;
                double jumpStrengthPct = (jumpStrength - 1) * 100;
                tooltip.add(index, Component.translatable("text.changed_additions.jumpStrength")
                        .append("")
                        .append(jumpStrengthPct == 0
                                ? Component.literal("§7None§r")
                                : Component.literal((jumpStrengthPct > 0 ? "§a+" : "§c") + (int) jumpStrengthPct + "%")));

                index++;
                tooltip.add(index, Component.translatable("text.changed_additions.canGlide/Fly")
                        .append("")
                        .append(canFlyOrGlide
                                ? Component.literal("§aTrue§r")
                                : Component.literal("§cFalse§r")));
            }

            if (ChangedAdditionsTransfurVariants.isVariantOC(form, entity.level())) {
                tooltip.add(Component.literal("§8OC Transfur"));
            }
        }

        if (ChangedAdditionsTransfurVariants.getBossesVariantsList().stream().anyMatch(variant -> {
            String variantID = variant.getFormId().toString();
            return variantID.equals(form) || variantID.startsWith(form);
        })) {
            tooltip.add(Component.literal("§8Boss Version"));
        }
    }

}
