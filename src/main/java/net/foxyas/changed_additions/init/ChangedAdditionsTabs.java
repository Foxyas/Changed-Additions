package net.foxyas.changed_additions.init;

import net.ltxprogrammer.changed.Changed;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class ChangedAdditionsTabs {
    public static DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Changed.MODID);

    public static RegistryObject<CreativeModeTab> CHANGED_ADDITIONS_TAB = register("misc", builder ->
            builder.icon(() -> new ItemStack(ChangedAdditionsBlocks.NEOFUSER.get()))
                    .displayItems((params, output) -> {
                        ChangedAdditionsItems.REGISTRY.getEntries().forEach((itemRegistryObject -> {
                            output.accept(itemRegistryObject.get());
                        }));
                    }).build());

    private static RegistryObject<CreativeModeTab> register(String id, Function<CreativeModeTab.Builder, CreativeModeTab> finalizer) {
        return REGISTRY.register(id, () -> finalizer.apply(
                CreativeModeTab.builder().title(Component.translatable("itemGroup.changed_additions." + id))
        ));
    }
}
