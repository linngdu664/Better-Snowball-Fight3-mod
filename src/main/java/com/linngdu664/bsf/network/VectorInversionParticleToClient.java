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

public class VectorInversionParticleToClient {
    private final double px, py, pz, range, v;
    private final int num;

    public VectorInversionParticleToClient(double px, double py, double pz, double range, double v, int num) {
        this.px = px;
        this.py = py;
        this.pz = pz;
        this.range = range;
        this.v = v;
        this.num = num;
    }

    public static void encoder(VectorInversionParticleToClient message, FriendlyByteBuf buffer) {
        buffer.writeDouble(message.px);
        buffer.writeDouble(message.py);
        buffer.writeDouble(message.pz);
        buffer.writeDouble(message.range);
        buffer.writeDouble(message.v);
        buffer.writeInt(message.num);
    }

    public static VectorInversionParticleToClient decoder(FriendlyByteBuf buffer) {
        double px = buffer.readDouble();
        double py = buffer.readDouble();
        double pz = buffer.readDouble();
        double range = buffer.readDouble();
        double v = buffer.readDouble();
        int num = buffer.readInt();
        return new VectorInversionParticleToClient(px, py, pz, range, v, num);
    }

    public static void messageConsumer(VectorInversionParticleToClient message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context context = ctxSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            ParticleUtil.spawnVectorInversionParticles(
                    Minecraft.getInstance().level, ParticleRegister.VECTOR_INVERSION_PURPLE.get(),
                    new Vec3(message.px, message.py, message.pz), message.range, message.num, message.v
            );
            ParticleUtil.spawnVectorInversionParticles(
                    Minecraft.getInstance().level, ParticleRegister.VECTOR_INVERSION_RED.get(),
                    new Vec3(message.px, message.py, message.pz), message.range, message.num, -message.v
            );
            return true;
        }));
        context.setPacketHandled(true);
    }
}
