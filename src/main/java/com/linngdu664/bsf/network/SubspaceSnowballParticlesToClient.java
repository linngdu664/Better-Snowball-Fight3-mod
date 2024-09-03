package com.linngdu664.bsf.network;

import com.linngdu664.bsf.particle.util.ParticleUtil;
import com.linngdu664.bsf.registry.ParticleRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SubspaceSnowballParticlesToClient {
    private final double px, py, pz, range;
    private final int num;

    public SubspaceSnowballParticlesToClient(double px, double py, double pz, double range, int num) {
        this.px = px;
        this.py = py;
        this.pz = pz;
        this.range = range;
        this.num = num;
    }
    public static void encoder(SubspaceSnowballParticlesToClient message, FriendlyByteBuf buffer) {
        buffer.writeDouble(message.px);
        buffer.writeDouble(message.py);
        buffer.writeDouble(message.pz);
        buffer.writeDouble(message.range);
        buffer.writeInt(message.num);
    }

    public static SubspaceSnowballParticlesToClient decoder(FriendlyByteBuf buffer) {
        double px = buffer.readDouble();
        double py = buffer.readDouble();
        double pz = buffer.readDouble();
        double range = buffer.readDouble();
        int num = buffer.readInt();
        return new SubspaceSnowballParticlesToClient(px, py, pz, range, num);
    }

    public static void messageConsumer(SubspaceSnowballParticlesToClient message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context context = ctxSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            ParticleUtil.spawnSubspaceSnowballParticles(
                    Minecraft.getInstance().level, ParticleRegister.SUBSPACE_SNOWBALL_HIT_PARTICLE.get(),
                    new Vec3(message.px, message.py, message.pz), message.range, message.num
            );
            return true;
        }));
        context.setPacketHandled(true);
    }
}
