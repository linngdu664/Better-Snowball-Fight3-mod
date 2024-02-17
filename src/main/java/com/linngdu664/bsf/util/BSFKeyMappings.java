package com.linngdu664.bsf.util;

import com.linngdu664.bsf.item.weapon.AbstractBSFWeaponItem;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.util.LinkedList;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BSFKeyMappings {
    public static final KeyMapping CYCLE_MOVE_AMMO_NEXT = new KeyMapping("key.bsf.ammo_switch_next", GLFW.GLFW_KEY_H, "key.categories.misc");
    public static final KeyMapping CYCLE_MOVE_AMMO_PREV = new KeyMapping("key.bsf.ammo_switch_prev", GLFW.GLFW_KEY_G, "key.categories.misc");

    @SubscribeEvent
    public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
        event.register(CYCLE_MOVE_AMMO_NEXT);
        event.register(CYCLE_MOVE_AMMO_PREV);
    }

    @Mod.EventBusSubscriber(Dist.CLIENT)
    public static class KeyEventListener {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            Minecraft minecraft = Minecraft.getInstance();
            int key = event.getKey();
            if (minecraft.screen == null && (key == CYCLE_MOVE_AMMO_NEXT.getKey().getValue() || key == CYCLE_MOVE_AMMO_PREV.getKey().getValue()) && event.getAction() == GLFW.GLFW_PRESS) {
                Player player = minecraft.player;
                AbstractBSFWeaponItem weaponItem = null;
                if (player.getMainHandItem().getItem() instanceof AbstractBSFWeaponItem item) {
                    weaponItem = item;
                } else if (player.getOffhandItem().getItem() instanceof AbstractBSFWeaponItem item) {
                    weaponItem = item;
                }
                if (weaponItem != null) {
                    LinkedList<Item> launchOrder = weaponItem.getLaunchOrder();
                    if (!launchOrder.isEmpty()) {
                        if (key == CYCLE_MOVE_AMMO_NEXT.getKey().getValue()) {
                            Item item = launchOrder.getFirst();
                            launchOrder.removeFirst();
                            launchOrder.addLast(item);
                        } else {
                            Item item = launchOrder.getLast();
                            launchOrder.removeLast();
                            launchOrder.addFirst(item);
                        }
                        player.playSound(SoundEvents.DISPENSER_DISPENSE, 1.0F, 1.0F / (player.level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                    }
                }
            }
        }
    }
}
