package net.foxyas.changed_additions.init;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.item.LaserPointer;
import net.foxyas.changed_additions.item.armor.DyeableShorts;
import net.foxyas.changed_additions.item.armor.TShirtClothing;
import net.ltxprogrammer.changed.Changed;
import net.ltxprogrammer.changed.entity.variant.TransfurVariant;
import net.ltxprogrammer.changed.init.ChangedItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Predicate;

public class ChangedAdditionsTabs {
    public static DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Changed.MODID);

    private static final Predicate<TransfurVariant<?>> CHANGED_ADDON_ONLY = variant -> variant.getFormId().getNamespace().equals(ChangedAdditionsMod.MODID);


    public static RegistryObject<CreativeModeTab> CHANGED_ADDITIONS_TAB = register("misc", builder ->
            builder.icon(() -> new ItemStack(ChangedAdditionsBlocks.NEOFUSER.get()))
                    .displayItems((params, output) -> {
                        ChangedAdditionsItems.REGISTRY.getEntries().forEach((itemRegistryObject -> {
                            if (itemRegistryObject.get() instanceof LaserPointer laserPointer) {
                                for (LaserPointer.DefaultColors color : LaserPointer.DefaultColors.values()) {
                                    ItemStack stack = new ItemStack(laserPointer);
                                    LaserPointer.setLaserColor(stack, color.getColorToInt());
                                    output.accept(stack);
                                }
                            } else if (itemRegistryObject.get() instanceof DyeableShorts dyeableShorts) {
                                for (DyeableShorts.DefaultColors color : DyeableShorts.DefaultColors.values()) {
                                    ItemStack stack = new ItemStack(dyeableShorts);
                                    dyeableShorts.setColor(stack, color.getColorToInt());
                                    output.accept(stack);
                                }
                            } else if (itemRegistryObject.get() instanceof TShirtClothing shirtClothing) {
                                for (TShirtClothing.DefaultColors color : TShirtClothing.DefaultColors.values()) {
                                    ItemStack stack = new ItemStack(shirtClothing);
                                    shirtClothing.setColor(stack, color.getColorToInt());
                                    output.accept(stack);
                                }
                            } else {
                                output.accept(itemRegistryObject.get());
                            }

                            ChangedItems.DARK_LATEX_MASK.get().fillItemList(CHANGED_ADDON_ONLY, params, output);
                            ChangedItems.LATEX_SYRINGE.get().fillItemList(CHANGED_ADDON_ONLY, params, output);
                            ChangedItems.LATEX_FLASK.get().fillItemList(CHANGED_ADDON_ONLY, params, output);
                            ChangedItems.LATEX_TIPPED_ARROW.get().fillItemList(CHANGED_ADDON_ONLY, params, output);
                        }));
                    }).build());

    private static RegistryObject<CreativeModeTab> register(String id, Function<CreativeModeTab.Builder, CreativeModeTab> finalizer) {
        return REGISTRY.register(id, () -> finalizer.apply(
                CreativeModeTab.builder().title(Component.translatable("itemGroup.changed_additions." + id))
        ));
    }
}
