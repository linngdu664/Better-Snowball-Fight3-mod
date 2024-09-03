package com.linngdu664.bsf.network;

import com.linngdu664.bsf.item.tool.SnowGolemModeTweakerItem;
import com.linngdu664.bsf.item.weapon.SculkSnowballLauncherItem;
import com.linngdu664.bsf.registry.SoundRegister;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SwitchTweakerTargetModeToServer {
    private final boolean isIncrease;

    public SwitchTweakerTargetModeToServer(boolean isIncrease) {
        this.isIncrease = isIncrease;
    }

    public static void encoder(SwitchTweakerTargetModeToServer message, FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.isIncrease);
    }

    public static SwitchTweakerTargetModeToServer decoder(FriendlyByteBuf buffer) {
        return new SwitchTweakerTargetModeToServer(buffer.readBoolean());
    }

    public static void messageConsumer(SwitchTweakerTargetModeToServer message, Supplier<NetworkEvent.Context> ctxSupplier) {
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
                if (!tag.contains("Locator")) {
                    tag.putByte("Locator", (byte) 0);
                }
                int locator = tag.getByte("Locator");
                if (message.isIncrease) {
                    locator = locator + 1 == 4 ? 0 : locator + 1;
                } else {
                    locator = locator == 0 ? 3 : locator - 1;
                }
                tag.putByte("Locator", (byte) locator);
                level.playSound(null, sender.getX(), sender.getY(), sender.getZ(), SoundEvents.DISPENSER_DISPENSE, SoundSource.PLAYERS, 6.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            }
        });
        context.setPacketHandled(true);
    }
}
