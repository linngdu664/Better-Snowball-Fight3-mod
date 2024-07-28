package com.linngdu664.bsf.network;

import com.linngdu664.bsf.registry.ParticleRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SubspaceSnowballReleaseTraceParticlesToClient {
    private final double px, py, pz,dx,dy,dz;

    public SubspaceSnowballReleaseTraceParticlesToClient(double px, double py, double pz, double dx, double dy, double dz) {
        this.px = px;
        this.py = py;
        this.pz = pz;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }
    public static void encoder(SubspaceSnowballReleaseTraceParticlesToClient message, FriendlyByteBuf buffer) {
        buffer.writeDouble(message.px);
        buffer.writeDouble(message.py);
        buffer.writeDouble(message.pz);
        buffer.writeDouble(message.dx);
        buffer.writeDouble(message.dy);
        buffer.writeDouble(message.dz);
    }

    public static SubspaceSnowballReleaseTraceParticlesToClient decoder(FriendlyByteBuf buffer) {
        double px = buffer.readDouble();
        double py = buffer.readDouble();
        double pz = buffer.readDouble();
        double dx = buffer.readDouble();
        double dy = buffer.readDouble();
        double dz = buffer.readDouble();
        return new SubspaceSnowballReleaseTraceParticlesToClient(px, py, pz, dx,dy,dz);
    }

    public static void messageConsumer(SubspaceSnowballReleaseTraceParticlesToClient message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context context = ctxSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            assert Minecraft.getInstance().level != null;
            ClientLevel level = Minecraft.getInstance().level;
            for (int i = 0; i < 10; i++) {
                level.addParticle(ParticleRegister.SUBSPACE_SNOWBALL_RELEASE_TRACE.get(), message.px, message.py, message.pz, message.dx, message.dy, message.dz);
            }
            return true;
        }));
        context.setPacketHandled(true);
    }
}
