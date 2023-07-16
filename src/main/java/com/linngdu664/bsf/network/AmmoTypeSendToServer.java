package com.linngdu664.bsf.network;

import com.linngdu664.bsf.item.weapon.AbstractBSFWeaponItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AmmoTypeSendToServer {
    private final Item item;

    public AmmoTypeSendToServer(Item item) {
        this.item = item;
    }

    public static void encoder(AmmoTypeSendToServer message, FriendlyByteBuf buffer) {
        buffer.writeItem(message.item.getDefaultInstance());
    }

    public static AmmoTypeSendToServer decoder(FriendlyByteBuf buffer) {
        return new AmmoTypeSendToServer(buffer.readItem().getItem());
    }

    public static void messageConsumer(AmmoTypeSendToServer message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context context = ctxSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer sender = context.getSender();
            AbstractBSFWeaponItem weapon = null;
            if (sender.getMainHandItem().getItem() instanceof AbstractBSFWeaponItem item) {
                weapon = item;
            } else if (sender.getOffhandItem().getItem() instanceof AbstractBSFWeaponItem item) {
                weapon = item;
            }
            if (weapon != null) {
                weapon.setAmmoItem(message.item);
            }
        });
        context.setPacketHandled(true);
    }
}
