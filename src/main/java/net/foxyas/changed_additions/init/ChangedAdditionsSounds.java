package net.foxyas.changed_additions.init;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ChangedAdditionsSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(
            ForgeRegistries.SOUND_EVENTS,
            ChangedAdditionsMod.MODID
    );

    // registros bonitos, automáticos
    public static final RegistryObject<SoundEvent> UNTRANSFUR = register("player.untransfur");
    public static final RegistryObject<SoundEvent> ARMOR_EQUIP = register("armor.equip");

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(ChangedAdditionsMod.modResource(name)));
    }

    public static void init(IEventBus bus) {
        SOUNDS.register(bus);
    }
}
