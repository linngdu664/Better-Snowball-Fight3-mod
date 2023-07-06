package com.linngdu664.bsf.event;

import com.linngdu664.bsf.network.AmmoRotateRight;
import com.linngdu664.bsf.network.Network;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BSFKeyMappings {
    public static final KeyMapping CYCLE_MOVE_AMMO_RIGHT = new KeyMapping("key.bsf.ammo_rotate_right", GLFW.GLFW_KEY_G, "key.categories.misc");

    @SubscribeEvent
    public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
        event.register(CYCLE_MOVE_AMMO_RIGHT);
    }

    @Mod.EventBusSubscriber(Dist.CLIENT)
    public static class KeyEventListener {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (Minecraft.getInstance().screen == null) {
                if (event.getKey() == CYCLE_MOVE_AMMO_RIGHT.getKey().getValue()) {
                    if (event.getAction() == GLFW.GLFW_PRESS) {
                        Network.PACKET_HANDLER.sendToServer(new AmmoRotateRight(0, 0));
                        AmmoRotateRight.pressAction(Minecraft.getInstance().player, 0, 0);
                    }
                }
            }
        }
    }
}
