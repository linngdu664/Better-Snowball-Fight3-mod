package com.linngdu664.bsf.network;

import com.linngdu664.bsf.client.screenshake.Easing;
import com.linngdu664.bsf.client.screenshake.ScreenshakeHandler;
import com.linngdu664.bsf.client.screenshake.ScreenshakeInstance;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ScreenshakeToClient {
    public final int duration;
    public float intensity1;
    public float intensity2;
    public float intensity3;
    public Easing intensityCurveStartEasing;
    public Easing intensityCurveEndEasing;

    public ScreenshakeToClient(int duration) {
        this.intensityCurveStartEasing = Easing.LINEAR;
        this.intensityCurveEndEasing = Easing.LINEAR;
        this.duration = duration;
    }

    public ScreenshakeToClient setIntensity(float intensity) {
        return this.setIntensity(intensity, intensity);
    }

    public ScreenshakeToClient setIntensity(float intensity1, float intensity2) {
        return this.setIntensity(intensity1, intensity2, intensity2);
    }

    public ScreenshakeToClient setIntensity(float intensity1, float intensity2, float intensity3) {
        this.intensity1 = intensity1;
        this.intensity2 = intensity2;
        this.intensity3 = intensity3;
        return this;
    }

    public ScreenshakeToClient setEasing(Easing easing) {
        this.intensityCurveStartEasing = easing;
        this.intensityCurveEndEasing = easing;
        return this;
    }

    public ScreenshakeToClient setEasing(Easing intensityCurveStartEasing, Easing intensityCurveEndEasing) {
        this.intensityCurveStartEasing = intensityCurveStartEasing;
        this.intensityCurveEndEasing = intensityCurveEndEasing;
        return this;
    }

    public static void encoder(ScreenshakeToClient message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.duration);
        buffer.writeFloat(message.intensity1);
        buffer.writeFloat(message.intensity2);
        buffer.writeFloat(message.intensity3);
        buffer.writeUtf(message.intensityCurveStartEasing.name);
        buffer.writeUtf(message.intensityCurveEndEasing.name);
    }

    public static ScreenshakeToClient decoder(FriendlyByteBuf buffer) {
        return (new ScreenshakeToClient(buffer.readInt())).setIntensity(buffer.readFloat(), buffer.readFloat(), buffer.readFloat()).setEasing(Easing.valueOf(buffer.readUtf()), Easing.valueOf(buffer.readUtf()));
    }

    public static void messageConsumer(ScreenshakeToClient message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context context = ctxSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            ScreenshakeHandler.addScreenshake((new ScreenshakeInstance(message.duration)).setIntensity(message.intensity1, message.intensity2, message.intensity3).setEasing(message.intensityCurveStartEasing, message.intensityCurveEndEasing));
            return true;
        }));
        context.setPacketHandled(true);
    }
}
