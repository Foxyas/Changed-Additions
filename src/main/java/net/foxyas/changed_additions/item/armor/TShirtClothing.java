package net.foxyas.changed_additions.item.armor;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.client.models.armors.DarkLatexCoatModel;
import net.foxyas.changed_additions.client.models.armors.SkinLayerModel;
import net.foxyas.changed_additions.client.renderer.LatexSnowFoxMaleRenderer;
import net.foxyas.changed_additions.entities.LatexSnowFoxMale;
import net.foxyas.changed_additions.init.ChangedAdditionsItems;
import net.foxyas.changed_additions.init.ChangedAdditionsTabs;
import net.foxyas.changed_additions.process.util.DelayedTask;
import net.foxyas.changed_additions.process.util.FoxyasUtils;
import net.foxyas.changed_additions.process.util.GlobalEntityUtil;
import net.foxyas.changed_additions.process.util.PlayerUtil;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.init.ChangedSounds;
import net.ltxprogrammer.changed.init.ChangedTransfurVariants;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.ltxprogrammer.changed.util.Color3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.ModelUtils;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

public class TShirtClothing extends SimpleClothingItem {

    public enum DefaultColors {
        RED(new Color(255, 0, 0)),
        GREEN(new Color(0, 255, 0)),
        BLUE(new Color(0, 0, 255)),
        YELLOW(new Color(255, 255, 0)),
        CYAN(new Color(0, 255, 255)),
        MAGENTA(new Color(255, 0, 255)),
        ORANGE(new Color(255, 165, 0)),
        PINK(new Color(255, 105, 180)),
        WHITE(new Color(255, 255, 255));

        public final Color color;

        DefaultColors(Color color) {
            this.color = color;
        }

        // Construtor sem argumentos, caso queira usar valores padrão depois
        DefaultColors() {
            this.color = new Color(255, 255, 255); // fallback: branco
        }

        public Color getColor() {
            return color;
        }

        public int getColorToInt() {
            return color.getRGB();
        }
    }

    public enum ShirtType {
        TYPE1("type1"),
        TYPE2("type2");

        private final String id;

        ShirtType(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public static ShirtType fromString(String name) {
            for (ShirtType type : values()) {
                if (type.id.equalsIgnoreCase(name)) return type;
            }
            return TYPE1; // default
        }
    }

    public TShirtClothing() {
        super(EquipmentSlot.CHEST, new Properties().tab(ChangedAdditionsTabs.CHANGED_ADDITIONS_TAB));
    }

    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot armorType, Entity entity) {
        return super.canEquip(stack, armorType, entity);
    }

    public SoundEvent getEquipSound() {
        return ChangedSounds.EQUIP3;
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        this.setColor(stack, Color3.WHITE.toInt());
        return stack;
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
        if (this.allowdedIn(tab)) {
            for (ShirtType type : ShirtType.values()) {
                for (DefaultColors color : DefaultColors.values()) {
                    ItemStack stack = new ItemStack(this);
                    this.setColor(stack, color.getColorToInt());
                    TShirtClothing.setShirtType(stack, type);
                    items.add(stack);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientInitializer {
        @SubscribeEvent
        public static void onItemColorsInit(ColorHandlerEvent.Item event) {
            event.getItemColors().register(
                    (stack, layer) -> ((DyeableLeatherItem) stack.getItem()).getColor(stack),
                    ChangedAdditionsItems.DYEABLE_SHIRT.get());
        }
    }

    @Override
    public int getColor(ItemStack p_41122_) {
        CompoundTag compoundtag = p_41122_.getTagElement("display");
        return compoundtag != null && compoundtag.contains("color", 99) ? compoundtag.getInt("color") : Color3.WHITE.toInt();
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    public static ShirtType getShirtType(ItemStack stack) {
        String tag = stack.getOrCreateTag().getString("Style");
        return ShirtType.fromString(tag);
    }

    public static void setShirtType(ItemStack stack, ShirtType type) {
        stack.getOrCreateTag().putString("Style", type.getId());
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        if (entity instanceof ChangedEntity changedEntity && this.getClothTexture(changedEntity, slot, stack) != null) {
            return "changed_additions:textures/models/armor/nothing_layer_1.png";
        }

        if (entity instanceof Player player) {
            if (ProcessTransfur.getPlayerTransfurVariant(player) == null
                    || ProcessTransfur.getPlayerTransfurVariant(player).getParent().is(ChangedTransfurVariants.LATEX_HUMAN)) {
                ShirtType shirtType = getShirtType(stack);

                if (shirtType == ShirtType.TYPE2) {
                    if ("overlay".equals(type)) {
                        return "changed_additions:textures/models/armor/player_t_shirt_type_2_layer_1_overlay.png";
                    }
                    return "changed_additions:textures/models/armor/player_t_shirt_type_2_layer_1.png";
                }

                // Default TYPE1
                if ("overlay".equals(type)) {
                    return "changed_additions:textures/models/armor/player_t_shirt_layer_1_overlay.png";
                }
                return "changed_additions:textures/models/armor/player_t_shirt_layer_1.png";
            }
        } else if (entity instanceof ChangedEntity changedEntity) {
            Player player = changedEntity.getUnderlyingPlayer();
            if (player != null) {
                if (ProcessTransfur.getPlayerTransfurVariant(player) == null
                        || ProcessTransfur.getPlayerTransfurVariant(player).getParent().is(ChangedTransfurVariants.LATEX_HUMAN)) {
                    ShirtType shirtType = getShirtType(stack);

                    if (shirtType == ShirtType.TYPE2) {
                        if ("overlay".equals(type)) {
                            return "changed_additions:textures/models/armor/player_t_shirt_type_2_layer_1_overlay.png";
                        }
                        return "changed_additions:textures/models/armor/player_t_shirt_type_2_layer_1.png";
                    }

                    // Default TYPE1
                    if ("overlay".equals(type)) {
                        return "changed_additions:textures/models/armor/player_t_shirt_layer_1_overlay.png";
                    }
                    return "changed_additions:textures/models/armor/player_t_shirt_layer_1.png";
                }
            }
        }

        ShirtType shirtType = getShirtType(stack);

        if (shirtType == ShirtType.TYPE2) {
            if ("overlay".equals(type)) {
                return "changed_additions:textures/models/armor/t_shirt_type_2_layer_1_overlay.png";
            }
            return "changed_additions:textures/models/armor/t_shirt_type_2_layer_1.png";
        }

        // Default TYPE1
        if ("overlay".equals(type)) {
            return "changed_additions:textures/models/armor/t_shirt_layer_1_overlay.png";
        }
        return "changed_additions:textures/models/armor/t_shirt_layer_1.png";
    }

    @Override
    public void initializeClient(@NotNull Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @OnlyIn(Dist.CLIENT)
            @Override
            public HumanoidModel<?> getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
                if (!(living instanceof Player) && !(living instanceof ChangedEntity)) {
                    return defaultModel;
                }

                if (living instanceof Player player) {
                    if (ProcessTransfur.getPlayerTransfurVariant(player) != null
                            && !ProcessTransfur.getPlayerTransfurVariant(player).getParent().is(ChangedTransfurVariants.LATEX_HUMAN)) {
                        return null;
                    }
                } else if (living instanceof ChangedEntity changedEntity) {
                    Player player = changedEntity.getUnderlyingPlayer();
                    if (player != null) {
                        if (ProcessTransfur.getPlayerTransfurVariant(player) != null
                                && !ProcessTransfur.getPlayerTransfurVariant(player).getParent().is(ChangedTransfurVariants.LATEX_HUMAN)) {
                            return null;
                        }
                    } else {
                        return null;
                    }
                }

                SkinLayerModel<LivingEntity> model = new SkinLayerModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(SkinLayerModel.LAYER_LOCATION));
                /*SkinLayerModel<LivingEntity> LayerArmor = new SkinLayerModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(SkinLayerModel.LAYER_LOCATION));
                // Criar o modelo de armadura com base na classe DarkLatexCoat
                HumanoidModel<?> model = new HumanoidModel<>(new ModelPart(Collections.emptyList(),
                        Map.of("head", LayerArmor.head,  // Para a parte da cabeça
                                "hat", LayerArmor.hat,
                                "body", LayerArmor.body,
                                "left_arm", LayerArmor.leftArm,
                                "right_arm", LayerArmor.rightArm,
                                "right_leg", LayerArmor.rightLeg,
                                "left_leg", LayerArmor.leftLeg)));*/

                model.crouching = living.isShiftKeyDown();
                model.riding = defaultModel.riding;
                model.young = living.isBaby();
                defaultModel.copyPropertiesTo(model);

                return model;
            }
        });
    }

    @Override
    public @Nullable Color3 getClothColor(ChangedEntity entity, EquipmentSlot slot, ItemStack itemBySlot) {
        return super.getClothColor(entity, slot, itemBySlot);
    }

    @Override
    public @Nullable ResourceLocation getClothTexture(ChangedEntity entity, EquipmentSlot slot, ItemStack itemBySlot) {
        return super.getClothTexture(entity, slot, itemBySlot);
    }
}
