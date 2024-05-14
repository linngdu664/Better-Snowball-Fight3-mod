package com.linngdu664.bsf.network;

import com.linngdu664.bsf.particle.util.BSFParticleType;
import com.linngdu664.bsf.particle.util.ParticleUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ForwardConeParticlesToClient {
    private final double eX, eY, eZ, loweredVision, sX, sY, sZ;
    private final float aStep, rStep, r;
    private final int type;

    /**
     * Constructor of the ForwardConeParticlesToClient packet.<p>
     * The client will use the parametric equation of circles, in which the distance from plane to origin is 8m and the
     * normal vector is sightVec, to spray cone-shaped particles forward, and the maximum radius needs to be specified.
     *
     * @param eyePos        The eye position of the entity.
     * @param sightVec      The sight vector of the entity.
     * @param r             The max radius.
     * @param aStep         The angle step in degree.
     * @param rStep         The radius step.
     * @param loweredVision The offset of eyePos in the negative direction of the y-axis.
     * @param type          The particle type from BSFParticleType
     */
    public ForwardConeParticlesToClient(Vec3 eyePos, Vec3 sightVec, float r, float aStep, float rStep, double loweredVision, int type) {
        this.eX = eyePos.x;
        this.eY = eyePos.y;
        this.eZ = eyePos.z;
        this.sX = sightVec.x;
        this.sY = sightVec.y;
        this.sZ = sightVec.z;
        this.r = r;
        this.aStep = aStep;
        this.rStep = rStep;
        this.loweredVision = loweredVision;
        this.type = type;
    }

    public static void encoder(ForwardConeParticlesToClient message, FriendlyByteBuf buffer) {
        buffer.writeDouble(message.eX);
        buffer.writeDouble(message.eY);
        buffer.writeDouble(message.eZ);
        buffer.writeDouble(message.sX);
        buffer.writeDouble(message.sY);
        buffer.writeDouble(message.sZ);
        buffer.writeFloat(message.r);
        buffer.writeFloat(message.aStep);
        buffer.writeFloat(message.rStep);
        buffer.writeDouble(message.loweredVision);
        buffer.writeInt(message.type);
    }

    public static ForwardConeParticlesToClient decoder(FriendlyByteBuf buffer) {
        double eX = buffer.readDouble();
        double eY = buffer.readDouble();
        double eZ = buffer.readDouble();
        double sX = buffer.readDouble();
        double sY = buffer.readDouble();
        double sZ = buffer.readDouble();
        float r = buffer.readFloat();
        float aStep = buffer.readFloat();
        float rStep = buffer.readFloat();
        double loweredVision = buffer.readDouble();
        int type = buffer.readInt();
        return new ForwardConeParticlesToClient(new Vec3(eX, eY, eZ), new Vec3(sX, sY, sZ), r, aStep, rStep, loweredVision, type);
    }

    public static void messageConsumer(ForwardConeParticlesToClient message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context context = ctxSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            ParticleUtil.spawnForwardConeParticles(
                    Minecraft.getInstance().level, BSFParticleType.values()[message.type].get(),
                    message.eX, message.eY, message.eZ,
                    new Vec3(message.sX, message.sY, message.sZ),
                    message.r, message.aStep, message.rStep, message.loweredVision
            );
            return true;
        }));
        context.setPacketHandled(true);
    }
}
