package com.linngdu664.bsf.network;

import com.linngdu664.bsf.item.weapon.AbstractBSFWeaponItem;
import com.linngdu664.bsf.registry.NetworkRegister;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class AmmoTypeToServer {
    private final Item item;
    private final int slot;

    public AmmoTypeToServer(Item item, int slot) {
        this.item = item;
        this.slot = slot;
    }

    public static void encoder(AmmoTypeToServer message, FriendlyByteBuf buffer) {
        buffer.writeItem(message.item.getDefaultInstance());
        buffer.writeInt(message.slot);
    }

    public static AmmoTypeToServer decoder(FriendlyByteBuf buffer) {
        return new AmmoTypeToServer(buffer.readItem().getItem(), buffer.readInt());
    }

    public static void messageConsumer(AmmoTypeToServer message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context context = ctxSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer sender = context.getSender();
            if (!sender.level().hasChunkAt(sender.blockPosition())) {
                return;
            }
            ItemStack itemStack = sender.getInventory().getItem(message.slot);
            if (itemStack.getItem() instanceof AbstractBSFWeaponItem) {
                CompoundTag compoundTag = itemStack.getOrCreateTag();
                ResourceLocation resourceLocation = ForgeRegistries.ITEMS.getKey(message.item);
                compoundTag.putString("ammo_item", resourceLocation.getPath());
            } else {
                NetworkRegister.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> sender), new AmmoTypeToClient(message.slot));
            }
        });
        context.setPacketHandled(true);
    }
}
