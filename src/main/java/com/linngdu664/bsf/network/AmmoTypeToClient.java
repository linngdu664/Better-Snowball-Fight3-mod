package com.linngdu664.bsf.network;

import com.linngdu664.bsf.item.weapon.AbstractBSFWeaponItem;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AmmoTypeToClient {
    private final int slot;

    public AmmoTypeToClient(int slot) {
        this.slot = slot;
    }

    public static void encoder(AmmoTypeToClient message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.slot);
    }

    public static AmmoTypeToClient decoder(FriendlyByteBuf buffer) {
        return new AmmoTypeToClient(buffer.readInt());
    }

    public static void messageConsumer(AmmoTypeToClient message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context context = ctxSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> handlePacket(message.slot)));
        context.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean handlePacket(int slot) {
        if (Minecraft.getInstance().player.getInventory().getItem(slot).getItem() instanceof AbstractBSFWeaponItem weapon) {
            Network.PACKET_HANDLER.sendToServer(new AmmoTypeToServer(weapon.getCurrentAmmoItemStack().getItem(), slot));
        }
        return true;
    }
}
