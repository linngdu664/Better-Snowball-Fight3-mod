package com.linngdu664.bsf.network;

import com.linngdu664.bsf.item.weapon.SculkSnowballLauncherItem;
import com.linngdu664.bsf.registry.SoundRegister;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SwitchSoundToServer {
    private final boolean isIncrease;

    public SwitchSoundToServer(boolean isIncrease) {
        this.isIncrease = isIncrease;
    }

    public static void encoder(SwitchSoundToServer message, FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.isIncrease);
    }

    public static SwitchSoundToServer decoder(FriendlyByteBuf buffer) {
        return new SwitchSoundToServer(buffer.readBoolean());
    }

    public static void messageConsumer(SwitchSoundToServer message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context context = ctxSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer sender = context.getSender();
            Level level = sender.level();
            if (!level.hasChunkAt(sender.blockPosition())) {
                return;
            }
            ItemStack itemStack = sender.getItemInHand(InteractionHand.MAIN_HAND);
            if (itemStack.getItem() instanceof SculkSnowballLauncherItem) {
                CompoundTag tag = itemStack.getOrCreateTag();
                if (!tag.contains("SoundId")) {
                    tag.putInt("SoundId", -1);
                }
                int soundId = tag.getInt("SoundId");
                if (message.isIncrease) {
                    soundId = soundId + 1 == SoundRegister.MEME_SOUND_AMOUNT ? -1 : soundId + 1;
                } else {
                    soundId = soundId == -1 ? SoundRegister.MEME_SOUND_AMOUNT - 1 : soundId - 1;
                }
                tag.putInt("SoundId", soundId);
                if (soundId == -1) {
                    itemStack.setHoverName(MutableComponent.create(new TranslatableContents("item.bsf.sculk_snowball_launcher", null, new Object[]{}))
                            .append(": ").append(MutableComponent.create(new TranslatableContents("random_sound.tip", null, new Object[]{}))));
                } else {
                    itemStack.setHoverName(MutableComponent.create(new TranslatableContents("item.bsf.sculk_snowball_launcher", null, new Object[]{}))
                            .append(": ").append(MutableComponent.create(new TranslatableContents("sound_id.tip", null, new Object[]{String.valueOf(soundId)}))));
                }
            }
        });
        context.setPacketHandled(true);
    }
}
