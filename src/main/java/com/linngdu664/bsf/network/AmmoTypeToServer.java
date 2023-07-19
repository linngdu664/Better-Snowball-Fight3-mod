package com.linngdu664.bsf.network;

import com.linngdu664.bsf.item.weapon.AbstractBSFWeaponItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AmmoTypeToServer {
    private final Item item;
    private final boolean isMainHand;

    public AmmoTypeToServer(Item item, boolean isMainHand) {
        this.item = item;
        this.isMainHand = isMainHand;
    }

    public static void encoder(AmmoTypeToServer message, FriendlyByteBuf buffer) {
        buffer.writeItem(message.item.getDefaultInstance());
        buffer.writeBoolean(message.isMainHand);
    }

    public static AmmoTypeToServer decoder(FriendlyByteBuf buffer) {
        return new AmmoTypeToServer(buffer.readItem().getItem(), buffer.readBoolean());
    }

    public static void messageConsumer(AmmoTypeToServer message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context context = ctxSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer sender = context.getSender();
            if (!sender.level().hasChunkAt(sender.blockPosition())) {
                return;
            }
            if (message.isMainHand && sender.getMainHandItem().getItem() instanceof AbstractBSFWeaponItem weapon) {
                weapon.setAmmoItem(message.item);
            } else if (sender.getOffhandItem().getItem() instanceof AbstractBSFWeaponItem weapon) {
                weapon.setAmmoItem(message.item);
            }
        });
        context.setPacketHandled(true);
    }
}
