package com.linngdu664.bsf.network;

import com.linngdu664.bsf.item.weapon.AbstractBSFWeaponItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AmmoTypeToServer {
    private final Item item;

    public AmmoTypeToServer(Item item) {
        this.item = item;
    }

    public static void encoder(AmmoTypeToServer message, FriendlyByteBuf buffer) {
        buffer.writeItem(message.item.getDefaultInstance());
    }

    public static AmmoTypeToServer decoder(FriendlyByteBuf buffer) {
        return new AmmoTypeToServer(buffer.readItem().getItem());
    }

    public static void messageConsumer(AmmoTypeToServer message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context context = ctxSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer sender = context.getSender();
            if (!sender.level().hasChunkAt(sender.blockPosition())) {
                return;
            }
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
