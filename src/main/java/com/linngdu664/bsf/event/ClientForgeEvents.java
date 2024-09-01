package com.linngdu664.bsf.event;

import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.linngdu664.bsf.item.tool.ColdCompressionJetEngineItem;
import com.linngdu664.bsf.item.tool.SnowGolemModeTweakerItem;
import com.linngdu664.bsf.item.tool.TeamLinkerItem;
import com.linngdu664.bsf.item.weapon.AbstractBSFWeaponItem;
import com.linngdu664.bsf.item.weapon.SnowballCannonItem;
import com.linngdu664.bsf.network.SwitchSoundToServer;
import com.linngdu664.bsf.registry.ItemRegister;
import com.linngdu664.bsf.registry.NetworkRegister;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.util.LinkedList;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEvents {
    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.is(ItemRegister.SCULK_SNOWBALL_LAUNCHER.get()) && player.isShiftKeyDown()) {
            NetworkRegister.PACKET_HANDLER.sendToServer(new SwitchSoundToServer(event.getScrollDelta() > 0));
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft minecraft = Minecraft.getInstance();
        int key = event.getKey();
        if (minecraft.screen == null && (key == ClientModEvents.CYCLE_MOVE_AMMO_NEXT.getKey().getValue() || key == ClientModEvents.CYCLE_MOVE_AMMO_PREV.getKey().getValue()) && event.getAction() == GLFW.GLFW_PRESS) {
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
                    if (key == ClientModEvents.CYCLE_MOVE_AMMO_NEXT.getKey().getValue()) {
                        Item item = launchOrder.getFirst();
                        launchOrder.removeFirst();
                        launchOrder.addLast(item);
                    } else {
                        Item item = launchOrder.getLast();
                        launchOrder.removeLast();
                        launchOrder.addFirst(item);
                    }
                    player.playSound(SoundEvents.DISPENSER_DISPENSE, 1.0F, 1.0F / (player.level().getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onClientLoggingIn(ClientPlayerNetworkEvent.LoggingIn event) {
        TeamLinkerItem.shouldShowHighlight = false;
    }

    @SubscribeEvent
    public static void onComputeFovModifier(ComputeFovModifierEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getUseItem();
        if (player.isUsingItem()){
            if (itemStack.getItem() instanceof SnowballCannonItem) {
                int i = player.getTicksUsingItem();
                float f = event.getFovModifier();
                float f1 = (float) i / 20.0F;
                if (f1 > 1.0F) {
                    f1 = 1.0F;
                } else {
                    f1 *= f1;
                }
                if (itemStack.is(ItemRegister.POWERFUL_SNOWBALL_CANNON.get())) {
                    f *= 1.0F - f1 * 0.5F;
                } else {
                    f *= 1.0F - f1 * 0.3F;
                }
                event.setNewFovModifier(f);
            } else if (itemStack.getItem() instanceof ColdCompressionJetEngineItem) {
                float f = event.getFovModifier();
                if (player.getTicksUsingItem() >= ColdCompressionJetEngineItem.STARTUP_DURATION) {
                    f *= 1.4F;
                }
                event.setNewFovModifier(f);
            }
        }
        
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Pre event) {
        if (event.getOverlay().equals(VanillaGuiOverlay.HOTBAR.type())) {
            Minecraft instance = Minecraft.getInstance();
            Player player = instance.player;
            AbstractBSFWeaponItem weaponItem = null;
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offHandItem = player.getOffhandItem();
            if (mainHandItem.getItem() instanceof AbstractBSFWeaponItem item) {
                weaponItem = item;
            } else if (offHandItem.getItem() instanceof AbstractBSFWeaponItem item) {
                weaponItem = item;
            }
            if (weaponItem != null) {
                ItemStack current = weaponItem.getCurrentAmmoItemStack();
                ItemStack prev = weaponItem.getPrevAmmoItemStack();
                ItemStack next = weaponItem.getNextAmmoItemStack();
                GuiGraphics guiGraphics = event.getGuiGraphics();
                Window window = event.getWindow();
                BSFGui.V2I v2I = BSFGui.SNOWBALL_GUI.renderCenterVertically(guiGraphics, window, 0);
                int startPos = v2I.y;
                guiGraphics.renderItem(prev, 3, startPos + 3);
                guiGraphics.renderItem(current, 3, startPos + 23);
                guiGraphics.renderItem(next, 3, startPos + 43);
                guiGraphics.drawString(instance.font, String.valueOf(prev.getCount()), 24, startPos + 7, 0xffffffff);
                guiGraphics.drawString(instance.font, String.valueOf(current.getCount()), 24, startPos + 27, 0xffffffff);
                guiGraphics.drawString(instance.font, String.valueOf(next.getCount()), 24, startPos + 47, 0xffffffff);
            }
            ItemStack tweaker = null;
            if (mainHandItem.getItem() instanceof SnowGolemModeTweakerItem) {
                tweaker = mainHandItem;
            } else if (offHandItem.getItem() instanceof SnowGolemModeTweakerItem) {
                tweaker = offHandItem;
            }
            if (tweaker != null) {
                CompoundTag tag = tweaker.getOrCreateTag();
                byte locator = tag.getByte("Locator");
                byte status = tag.getByte("Status");
                GuiGraphics guiGraphics = event.getGuiGraphics();
                Window window = event.getWindow();
                BSFGui.V2I v2I = BSFGui.TWEAKER_LOCATOR_GUI.renderRatio(guiGraphics, window, 0.7, 0.5,30,0);
                BSFGui.TWEAKER_SELECTOR_GUI.render(guiGraphics, v2I.x-1, v2I.y-1+locator*20);
                v2I = BSFGui.TWEAKER_STATUS_GUI.renderRatio(guiGraphics,window,0.7,0.5,80,0);
                BSFGui.TWEAKER_SELECTOR_GUI.render(guiGraphics, v2I.x-1, v2I.y-1+status*20);
            }
//            HitResult pick = instance.hitResult;
//            System.out.println(pick);
//            if (pick.getType() == HitResult.Type.ENTITY && ((EntityHitResult) pick).getEntity() instanceof BSFSnowGolemEntity entity) {
//                GuiGraphics guiGraphics = event.getGuiGraphics();
//                Window window = event.getWindow();
//                byte locator = entity.getLocator();
//                byte status = entity.getStatus();
//                BSFGui.V2I v2I = BSFGui.GOLEM_LOCATOR_GUI.renderRatio(guiGraphics, window, 0.7, 0.5);
//                BSFGui.GOLEM_SELECTOR_GUI.render(guiGraphics, v2I.x-1, v2I.y-1+locator*20);
//                v2I = BSFGui.GOLEM_STATUS_GUI.renderRatio(guiGraphics,window,0.7,0.5,50,0);
//                BSFGui.GOLEM_SELECTOR_GUI.render(guiGraphics, v2I.x-1, v2I.y-1+status*20);
//            }
        }
    }

}
