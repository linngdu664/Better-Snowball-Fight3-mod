package com.linngdu664.bsf.event;

import com.linngdu664.bsf.item.tank.AbstractSnowballTankItem;
import com.linngdu664.bsf.item.weapon.AbstractBSFWeaponItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;

public class RenderOverlayEvent {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
            Minecraft instance = Minecraft.getInstance();
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
                    GuiGraphics guiGraphics = event.getGuiGraphics();
                    guiGraphics.renderItem(itemStack1, 0, 90);
                    guiGraphics.renderItem(itemStack2, 0, 105);
                    guiGraphics.renderItem(itemStack3, 0, 120);
                    guiGraphics.drawString(instance.font, MutableComponent.create(new LiteralContents("123")).withStyle(ChatFormatting.WHITE), 15, 95, 0);
                    guiGraphics.drawString(instance.font, MutableComponent.create(new LiteralContents("456")).withStyle(ChatFormatting.WHITE), 15, 110, 0);
                    guiGraphics.drawString(instance.font, MutableComponent.create(new LiteralContents("789")).withStyle(ChatFormatting.WHITE), 15, 125, 0);
                }
            }
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
