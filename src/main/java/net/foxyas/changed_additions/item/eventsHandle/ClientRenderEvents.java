package net.foxyas.changed_additions.item.eventsHandle;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChangedAdditionsMod.MODID, value = Dist.CLIENT)
public class ClientRenderEvents {
    /*
    @SubscribeEvent
    public static void onRenderLivingPre(RenderLivingEvent.Pre<?, ?> event) {
        LivingEntity entity = event.getEntity();
        ItemStack headStack = entity.getItemBySlot(EquipmentSlot.HEAD);

        // Verifica se o item é um acessório que você quer ocultar
        if (!headStack.isEmpty() && headStack.getItem() instanceof IAccessoryItem) {
            // Remove temporariamente o item para evitar renderização vanilla
            entity.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
        }
    }

    @SubscribeEvent
    public static void onRenderLivingPost(RenderLivingEvent.Post<?, ?> event) {
        LivingEntity entity = event.getEntity();

        // Aqui você pode restaurar o item se tiver salvo ele antes
        // Isso é mais necessário se o renderizador tentar usar o ItemStack após esse ponto (raro)
        // Geralmente, apenas remover no Pre já resolve.
    }*/
}
