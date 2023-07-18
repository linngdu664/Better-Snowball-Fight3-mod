package com.linngdu664.bsf.event;

import com.linngdu664.bsf.item.weapon.AbstractBSFWeaponItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderOverlayEvent {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onRenderOverlay(RenderGuiOverlayEvent.Pre event) {
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
                GuiGraphics guiGraphics = event.getGuiGraphics();
                guiGraphics.renderItem(prev, 3, 93);
                guiGraphics.renderItem(current, 3, 113);
                guiGraphics.renderItem(next, 3, 133);
                guiGraphics.drawString(instance.font, String.valueOf(prev.getCount()), 24, 97, 0xffffffff);
                guiGraphics.drawString(instance.font, String.valueOf(current.getCount()), 24, 117, 0xffffffff);
                guiGraphics.drawString(instance.font, String.valueOf(next.getCount()), 24, 137, 0xffffffff);
                guiGraphics.blit(new ResourceLocation("bsf", "textures/gui/snowball_frame.png"), 0, 90, 0, 0, 23, 62,23,62);
            }
        }
    }
}
