package com.linngdu664.bsf.network;

import com.linngdu664.bsf.particle.BSFParticleType;
import com.linngdu664.bsf.util.ParticleUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ForwardRaysParticlesToClient {
    private final double p1x, p1y, p1z, p2x, p2y, p2z, vx, vy, vz, vMin, vMax;
    private final int num,type;

    public ForwardRaysParticlesToClient(Vec3 pos1, Vec3 pos2, Vec3 vec, double vMin, double vMax, int num, int type) {
        this.p1x = pos1.x;
        this.p1y = pos1.y;
        this.p1z = pos1.z;
        this.p2x = pos2.x;
        this.p2y = pos2.y;
        this.p2z = pos2.z;
        this.vx = vec.x;
        this.vy = vec.y;
        this.vz = vec.z;
        this.vMin = vMin;
        this.vMax = vMax;
        this.num = num;
        this.type = type;
    }

    public static void encoder(ForwardRaysParticlesToClient message, FriendlyByteBuf buffer) {
        buffer.writeDouble(message.p1x);
        buffer.writeDouble(message.p1y);
        buffer.writeDouble(message.p1z);
        buffer.writeDouble(message.p2x);
        buffer.writeDouble(message.p2y);
        buffer.writeDouble(message.p2z);
        buffer.writeDouble(message.vx);
        buffer.writeDouble(message.vy);
        buffer.writeDouble(message.vz);
        buffer.writeDouble(message.vMin);
        buffer.writeDouble(message.vMax);
        buffer.writeInt(message.num);
        buffer.writeInt(message.type);
    }

    public static ForwardRaysParticlesToClient decoder(FriendlyByteBuf buffer) {
        double p1x = buffer.readDouble();
        double p1y = buffer.readDouble();
        double p1z = buffer.readDouble();
        double p2x = buffer.readDouble();
        double p2y = buffer.readDouble();
        double p2z = buffer.readDouble();
        double vx = buffer.readDouble();
        double vy = buffer.readDouble();
        double vz = buffer.readDouble();
        double vMin = buffer.readDouble();
        double vMax = buffer.readDouble();
        int num = buffer.readInt();
        int type = buffer.readInt();
        return new ForwardRaysParticlesToClient(new Vec3(p1x, p1y, p1z), new Vec3(p2x, p2y, p2z), new Vec3(vx, vy, vz), vMin, vMax, num, type);
    }

    public static void messageConsumer(ForwardRaysParticlesToClient message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context context = ctxSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            ParticleUtil.spawnForwardRaysParticles(
                    Minecraft.getInstance().level, BSFParticleType.values()[message.type].get(),
                    new Vec3(message.p1x, message.p1y, message.p1z),
                    new Vec3(message.p2x, message.p2y, message.p2z),
                    new Vec3(message.vx, message.vy, message.vz),
                    message.vMin, message.vMax, message.num
            );
            return true;
        }));
        context.setPacketHandled(true);
    }
}
