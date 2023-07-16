package com.linngdu664.bsf.event;

import com.linngdu664.bsf.item.tank.AbstractSnowballTankItem;
import com.linngdu664.bsf.item.weapon.AbstractBSFWeaponItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;

public class RenderOverlayEvent {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onRenderOverlay(RenderGuiOverlayEvent.Pre event) {
        if (event.getOverlay() == VanillaGuiOverlay.AIR_LEVEL.type()) {
            Minecraft instance = Minecraft.getInstance();
            GuiGraphics guiGraphics = event.getGuiGraphics();
            Player player = instance.player;
            AbstractBSFWeaponItem weaponItem = null;
            if (player.getMainHandItem().getItem() instanceof AbstractBSFWeaponItem item1) {
                weaponItem = item1;
            } else if (player.getOffhandItem().getItem() instanceof AbstractBSFWeaponItem item1) {
                weaponItem = item1;
            }
            if (weaponItem != null) {
                ArrayList<Item> order = weaponItem.getLaunchOrder();
                if (order != null && order.size() > 0) {
                    ItemStack itemStack1 = order.get(order.size() - 1).getDefaultInstance();
                    ItemStack itemStack2 = order.get(0).getDefaultInstance();
                    ItemStack itemStack3;
                    if (order.size() == 1) {
                        itemStack3 = order.get(0).getDefaultInstance();
                    } else {
                        itemStack3 = order.get(1).getDefaultInstance();
                    }
//                    int cnt1 = getAmmoCount(itemStack1.getItem(), player, weaponItem);
//                    int cnt2 = getAmmoCount(itemStack2.getItem(), player, weaponItem);
//                    int cnt3 = getAmmoCount(itemStack3.getItem(), player, weaponItem);
                    guiGraphics.renderItem(itemStack1, 3, 93);
                    guiGraphics.renderItem(itemStack2, 3, 113);
                    guiGraphics.renderItem(itemStack3, 3, 133);
//                    instance.font.drawInBatch("sfd", 15F, 95F, 0xffffffff, true, guiGraphics.pose().last().pose(), guiGraphics.bufferSource(), Font.DisplayMode.NORMAL, 0, 15728880);
                    guiGraphics.drawString(instance.font, MutableComponent.create(new LiteralContents("123")).withStyle(ChatFormatting.WHITE), 24, 97, 0);
                    guiGraphics.drawString(instance.font, MutableComponent.create(new LiteralContents("456")).withStyle(ChatFormatting.WHITE), 24, 117, 0);
                    guiGraphics.drawString(instance.font, MutableComponent.create(new LiteralContents("789")).withStyle(ChatFormatting.WHITE), 24, 137, 0);
                    guiGraphics.blit(new ResourceLocation("bsf", "textures/gui/snowball_frame.png"), 0, 90, 0, 0, 23, 62,23,62);
                }
            }
        }
        //15728880
/*
            Player player = instance.player;
            AbstractBSFWeaponItem weaponItem = null;
            if (player.getMainHandItem().getItem() instanceof AbstractBSFWeaponItem item1) {
                weaponItem = item1;
            } else if (player.getOffhandItem().getItem() instanceof AbstractBSFWeaponItem item1) {
                weaponItem = item1;
            }
            if (weaponItem != null) {
                ArrayList<Item> order = weaponItem.getLaunchOrder();
                if (order != null && order.size() > 0) {
                    ItemStack itemStack1 = order.get(order.size() - 1).getDefaultInstance();
                    ItemStack itemStack2 = order.get(0).getDefaultInstance();
                    ItemStack itemStack3;
                    if (order.size() == 1) {
                        itemStack3 = order.get(0).getDefaultInstance();
                    } else {
                        itemStack3 = order.get(1).getDefaultInstance();
                    }
//                    int cnt1 = getAmmoCount(itemStack1.getItem(), player, weaponItem);
//                    int cnt2 = getAmmoCount(itemStack2.getItem(), player, weaponItem);
//                    int cnt3 = getAmmoCount(itemStack3.getItem(), player, weaponItem);
                    guiGraphics.renderItem(itemStack1, 0, 90);
                    guiGraphics.renderItem(itemStack2, 0, 105);
                    guiGraphics.renderItem(itemStack3, 0, 120);
                    guiGraphics.drawString(instance.font, MutableComponent.create(new LiteralContents("123")).withStyle(ChatFormatting.WHITE), 15, 95, 0);
                    guiGraphics.drawString(instance.font, MutableComponent.create(new LiteralContents("456")).withStyle(ChatFormatting.WHITE), 15, 110, 0);
                    guiGraphics.drawString(instance.font, MutableComponent.create(new LiteralContents("789")).withStyle(ChatFormatting.WHITE), 15, 125, 0);
                }
            }*/
    }

    private int getAmmoCount(Item item, Player player, AbstractBSFWeaponItem weapon) {
        Inventory inventory = player.getInventory();
        int k = inventory.getContainerSize();
        int cnt = 0;
        for (int i = 0; i < k; i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack.getItem() instanceof AbstractSnowballTankItem tank && tank.getSnowball().equals(item)) {
                cnt += itemStack.getMaxDamage() - itemStack.getDamageValue();
            } else if (weapon.isAllowBulkedSnowball() && itemStack.getItem().equals(item)) {
                cnt += itemStack.getCount();
            }
        }
        return cnt;
    }
}
