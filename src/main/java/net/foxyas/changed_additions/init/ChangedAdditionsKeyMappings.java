package net.foxyas.changed_additions.init;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.network.PatKeyMessage;
import net.foxyas.changed_additions.network.TurnOffTransfurMessage;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class ChangedAdditionsKeyMappings {
    public static final KeyMapping TURN_OFF_TRANSFUR = new KeyMapping("key.changed_additions.turn_off_transfur", GLFW.GLFW_KEY_UNKNOWN, "key.categories.changed_additions") {
        private boolean isDownOld = false;

        @Override
        public void setDown(boolean isDown) {
            if (Minecraft.getInstance().player != null) {
                super.setDown(isDown);
                if (isDownOld != isDown && isDown) {
                    ChangedAdditionsMod.PACKET_HANDLER.sendToServer(new TurnOffTransfurMessage(0, 0));
                    TurnOffTransfurMessage.pressAction(Minecraft.getInstance().player, 0, 0);
                }
                isDownOld = isDown;
            }
        }
    };
    public static final KeyMapping PAT_KEY = new KeyMapping("key.changed_additions.pat_key", GLFW.GLFW_KEY_C, "key.categories.changed_additions") {

        @Override
        public void setDown(boolean isDown) {
            if (Minecraft.getInstance().player != null) {
                super.setDown(isDown);
                if (isDown) {
                    ChangedAdditionsMod.PACKET_HANDLER.sendToServer(new PatKeyMessage(0, 0));
                    PatKeyMessage.pressAction(Minecraft.getInstance().player, 0, 0);
                }
            }
        }

        @Override
        public boolean consumeClick() {
            return super.consumeClick() && Minecraft.getInstance().player != null;
        }
    };
	/*public static final KeyMapping OPEN_STRUGGLE_MENU = new KeyMapping("key.changed_additions.open_struggle_menu", GLFW.GLFW_KEY_B, "key.categories.changed_additions") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				ChangedAdditionsMod.PACKET_HANDLER.sendToServer(new OpenStruggleMenuMessage(0, 0));
				OpenStruggleMenuMessage.pressAction(Minecraft.getInstance().player, 0, 0);
			}
			isDownOld = isDown;
		}
	};*/

    @SubscribeEvent
    public static void registerKeyBindings(FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(TURN_OFF_TRANSFUR);
        ClientRegistry.registerKeyBinding(PAT_KEY);
        //ClientRegistry.registerKeyBinding(OPEN_STRUGGLE_MENU);
    }

    @Mod.EventBusSubscriber({Dist.CLIENT})
    public static class KeyEventListener {
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (Minecraft.getInstance().screen == null) {
                TURN_OFF_TRANSFUR.consumeClick();
                PAT_KEY.consumeClick();
                //OPEN_STRUGGLE_MENU.consumeClick();
            }
        }

        @SubscribeEvent
        public static void onKeyInput(InputEvent.KeyInputEvent event) {
            if (Minecraft.getInstance().screen == null && event.getKey() == PAT_KEY.getKey().getValue()) {
                //PAT_KEY.consumeClick();
            }
        }
    }
}
