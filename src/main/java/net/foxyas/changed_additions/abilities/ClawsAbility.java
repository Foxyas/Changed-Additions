package net.foxyas.changed_additions.abilities;

import net.foxyas.changed_additions.variants.TransfurVariantTags;
import net.ltxprogrammer.changed.ability.IAbstractChangedEntity;
import net.ltxprogrammer.changed.ability.SimpleAbility;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collection;

public class ClawsAbility extends SimpleAbility {
	public boolean isActive = false;

	public ClawsAbility() {
		super();
	}

	@Override
	public void saveData(CompoundTag tag, IAbstractChangedEntity entity) {
		super.saveData(tag, entity);
		tag.putBoolean("isActive", isActive);
	}

	@Override
	public void readData(CompoundTag tag, IAbstractChangedEntity entity) {
		super.readData(tag, entity);
		if (tag.contains("isActive")) {
			this.isActive = tag.getBoolean("isActive");
		}
	}

	@Override
	public ResourceLocation getTexture(IAbstractChangedEntity entity) {
		return new ResourceLocation("changed_additions:textures/abilities/claw.png");
	}

	@Override
	public Component getAbilityName(IAbstractChangedEntity entity) {
		return new TranslatableComponent("changed_additions.ability.claws");
	}

	@Override
	public Collection<Component> getAbilityDescription(IAbstractChangedEntity entity) {
		Collection<Component> list = new ArrayList<>();
		list.add(new TranslatableComponent("changed_additions.ability.claws.desc"));
		return list;
	}

	@Override
	public UseType getUseType(IAbstractChangedEntity entity) {
		return UseType.INSTANT;
	}

	@Override
	public int getCoolDown(IAbstractChangedEntity entity) {
		return 2;
	}

	@Override
	public boolean canUse(IAbstractChangedEntity entity) {
		if (entity.getTransfurVariantInstance() == null) {
			return false;
		}
		return entity.getTransfurVariantInstance().getParent().is(TransfurVariantTags.HAS_CLAWS) || entity.getTransfurVariantInstance().getParent().is(TransfurVariantTags.CAT_LIKE) || entity.getTransfurVariantInstance().getParent().is(TransfurVariantTags.LEOPARD_LIKE);
	}

	@Override
	public boolean canKeepUsing(IAbstractChangedEntity entity) {
		if (entity.getTransfurVariantInstance() == null) {
			return false;
		}
		return entity.getTransfurVariantInstance().getParent().is(TransfurVariantTags.HAS_CLAWS) || entity.getTransfurVariantInstance().getParent().is(TransfurVariantTags.CAT_LIKE) || entity.getTransfurVariantInstance().getParent().is(TransfurVariantTags.LEOPARD_LIKE);
	}

	@Override
	public void startUsing(IAbstractChangedEntity entity) {
		if (!entity.getLevel().isClientSide()) {
			TurnOnClaws();
			this.setDirty(entity);
		}
	}

	public void TurnOnClaws() {
		this.isActive = !this.isActive;
	}
}
