package com.linngdu664.bsf.event;

import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.item.tool.TeamLinkerItem;
import com.linngdu664.bsf.item.weapon.AbstractBSFWeaponItem;
import com.linngdu664.bsf.item.weapon.SnowballCannonItem;
import com.linngdu664.bsf.registry.ItemRegister;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.gui.GuiComponent;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEvents {
    @SubscribeEvent
    public static void onClientLoggingIn(ClientPlayerNetworkEvent.LoggingIn event) {
        TeamLinkerItem.shouldShowHighlight = false;
    }

    @SubscribeEvent
    public static void onComputeFovModifier(ComputeFovModifierEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getUseItem();
        if (player.isUsingItem() && itemStack.getItem() instanceof SnowballCannonItem) {
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
        }
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Pre event) {
        if (event.getOverlay() == VanillaGuiOverlay.HOTBAR.type()) {
            Minecraft instance = Minecraft.getInstance();
            Player player = instance.player;
            AbstractBSFWeaponItem weaponItem = null;
            if (player.getMainHandItem().getItem() instanceof AbstractBSFWeaponItem item) {
                weaponItem = item;
            } else if (player.getOffhandItem().getItem() instanceof AbstractBSFWeaponItem item) {
                weaponItem = item;
            }
            if (weaponItem != null) {
                ItemStack current = weaponItem.getCurrentAmmoItemStack();
                ItemStack prev = weaponItem.getPrevAmmoItemStack();
                ItemStack next = weaponItem.getNextAmmoItemStack();
                PoseStack poseStack = event.getPoseStack();
                ItemRenderer itemRenderer = instance.getItemRenderer();
                Window window = event.getWindow();
                int startPos = window.getHeight() * 3 / 8 / (int) window.getGuiScale();
                RenderSystem.setShaderTexture(0, new ResourceLocation("bsf", "textures/gui/snowball_frame.png"));
                GuiComponent.blit(poseStack, 0, startPos, 0, 0, 23, 62, 23, 62);
                itemRenderer.renderGuiItem(prev, 3, startPos + 3);
                itemRenderer.renderGuiItem(current, 3, startPos + 23);
                itemRenderer.renderGuiItem(next, 3, startPos + 43);
                GuiComponent.drawString(poseStack, instance.font, String.valueOf(prev.getCount()), 24, startPos + 7, 0xffffffff);
                GuiComponent.drawString(poseStack, instance.font, String.valueOf(current.getCount()), 24, startPos + 27, 0xffffffff);
                GuiComponent.drawString(poseStack, instance.font, String.valueOf(next.getCount()), 24, startPos + 47, 0xffffffff);
            }
        }
    }
}
