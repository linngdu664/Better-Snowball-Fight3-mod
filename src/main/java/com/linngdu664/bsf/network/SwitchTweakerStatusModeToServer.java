package com.linngdu664.bsf.network;

import com.linngdu664.bsf.item.tool.SnowGolemModeTweakerItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SwitchTweakerStatusModeToServer {
    private final boolean isIncrease;

    public SwitchTweakerStatusModeToServer(boolean isIncrease) {
        this.isIncrease = isIncrease;
    }

    public static void encoder(SwitchTweakerStatusModeToServer message, FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.isIncrease);
    }

    public static SwitchTweakerStatusModeToServer decoder(FriendlyByteBuf buffer) {
        return new SwitchTweakerStatusModeToServer(buffer.readBoolean());
    }

    public static void messageConsumer(SwitchTweakerStatusModeToServer message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context context = ctxSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer sender = context.getSender();
            Level level = sender.level();
            if (!level.hasChunkAt(sender.blockPosition())) {
                return;
            }
            ItemStack itemStack = sender.getItemInHand(InteractionHand.MAIN_HAND);
            if (itemStack.getItem() instanceof SnowGolemModeTweakerItem) {
                CompoundTag tag = itemStack.getOrCreateTag();
                if (!tag.contains("Status")) {
                    tag.putByte("Status", (byte) 0);
                }
                int status = tag.getByte("Status");
                if (message.isIncrease) {
                    status = status + 1 == 5 ? 0 : status + 1;
                } else {
                    status = status == 0 ? 4 : status - 1;
                }
                tag.putByte("Status", (byte) status);
                level.playSound(null, sender.getX(), sender.getY(), sender.getZ(), SoundEvents.DISPENSER_DISPENSE, SoundSource.PLAYERS, 6.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            }
        });
        context.setPacketHandled(true);
    }
}
