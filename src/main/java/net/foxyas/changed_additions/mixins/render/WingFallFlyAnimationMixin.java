package net.foxyas.changed_additions.mixins.render;

import net.foxyas.changed_additions.abilities.WingFlapAbility;
import net.ltxprogrammer.changed.client.renderer.animate.wing.DragonWingFallFlyAnimator;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.variant.TransfurVariantInstance;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.foxyas.changed_additions.init.ChangedAdditionsAbilities.WING_FLAP_ABILITY;

@Mixin(value = DragonWingFallFlyAnimator.class, remap = false)
public class WingFallFlyAnimationMixin {

	@Inject(method = "setupAnim", at = @At("TAIL"))
	private void WingAnimation(@NotNull ChangedEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
		if (entity.getUnderlyingPlayer() != null && ProcessTransfur.getPlayerTransfurVariant(entity.getUnderlyingPlayer()) != null) {
			TransfurVariantInstance<?> variantInstance = ProcessTransfur.getPlayerTransfurVariant(entity.getUnderlyingPlayer());
			if (variantInstance.hasAbility(WING_FLAP_ABILITY.get()) && variantInstance.getAbilityInstance(WING_FLAP_ABILITY.get()).canUse()
					&& variantInstance.getSelectedAbility() instanceof WingFlapAbility.AbilityInstance WingFlapAbilityInstance) {
				if (entity.getUnderlyingPlayer().getAbilities().flying){
					return;
				}

				// Aplicação no cálculo da rotação
				float progress = WingFlapAbilityInstance.getController().getHoldTicks() / (float) WingFlapAbility.MAX_TICK_HOLD;
				float easedProgress = changed_Additions$easeOutCubic(progress); // Aplica suavização
				float maxRotation = changed_Additions$capLevel(35 * easedProgress, 0, 35); // Aplica o level cap

				// Interpolação suave
				((DragonWingFallFlyAnimator<?, ?>) (Object) this).leftWingRoot.zRot = -maxRotation * Mth.DEG_TO_RAD;
				((DragonWingFallFlyAnimator<?, ?>) (Object) this).rightWingRoot.zRot = maxRotation * Mth.DEG_TO_RAD;
			}
		}
	}

	// Função de suavização
	@Unique
	private static float changed_Additions$easeInOut(float t) {
		return t * t * (3 - 2 * t);
	}

	@Unique
	private static float changed_Additions$easeOutCubic(float t) {
    return 1 - (float) Math.pow(1 - t, 3);
	}


	// Método para limitar o valor entre min e max
	@Unique
	private static float changed_Additions$capLevel(float value, float min, float max) {
		if (value < min) {
			return min;
		} else if (value > max) {
			return max;
		}
		return value;
	}
}