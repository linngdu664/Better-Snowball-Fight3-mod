package com.linngdu664.bsf.network;

import com.linngdu664.bsf.client.resources.sounds.MovingSoundInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ToggleMovingSoundToClient {
    public static final byte PLAY_ONCE = 0;
    public static final byte PLAY_LOOP = 1;
    public static final byte STOP_LOOP = 2;
    private final Entity entity;
    private final SoundEvent soundEvent;
    private final byte flag;

    public ToggleMovingSoundToClient(Entity entity, SoundEvent soundEvent, byte flag) {
        this.entity = entity;
        this.soundEvent = soundEvent;
        this.flag = flag;
    }

    public static void encoder(ToggleMovingSoundToClient message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.entity.getId());
        message.soundEvent.writeToNetwork(buffer);
        buffer.writeByte(message.flag);
    }

    public static ToggleMovingSoundToClient decoder(FriendlyByteBuf buffer) {
        Entity entity = Minecraft.getInstance().level.getEntity(buffer.readInt());
        SoundEvent soundEvent = SoundEvent.readFromNetwork(buffer);
        byte flag = buffer.readByte();
        return new ToggleMovingSoundToClient(entity, soundEvent, flag);
    }

    public static void messageConsumer(ToggleMovingSoundToClient message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context context = ctxSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            SoundManager soundManager = Minecraft.getInstance().getSoundManager();
            if (message.flag == PLAY_LOOP) {
                MovingSoundInstance soundInstance = new MovingSoundInstance(message.entity, message.soundEvent, true);
                if (!soundManager.isActive(soundInstance)) {
                    soundManager.queueTickingSound(soundInstance);
                }
            } else if (message.flag == STOP_LOOP) {
                MovingSoundInstance soundInstance = new MovingSoundInstance(message.entity, message.soundEvent, true);
                soundManager.stop(soundInstance);
            } else {
                MovingSoundInstance soundInstance = new MovingSoundInstance(message.entity, message.soundEvent, false);
                soundManager.queueTickingSound(soundInstance);
            }
            return true;
        }));
        context.setPacketHandled(true);
    }
}
