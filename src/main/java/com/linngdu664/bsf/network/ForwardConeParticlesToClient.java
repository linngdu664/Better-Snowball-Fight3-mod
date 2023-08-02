package com.linngdu664.bsf.network;

import com.linngdu664.bsf.util.ParticleUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ForwardConeParticlesToClient {
    private final double eX, eY, eZ, loweredVision, sX, sY, sZ;
    private final float aStep, rStep, r;

    /**
     * Constructor of the ForwardConeParticlesToClient packet.<p>
     * The client will use the parametric equation of circles, in which the distance from plane to origin is 8m and the
     * normal vector is sightVec, to spray cone-shaped particles forward, and the maximum radius needs to be specified.
     *
     * @param eyePos        The eye position of the entity.
     * @param sightVec      The sight vector of the entity.
     * @param r             The max radius.
     * @param aStep         The angle step.
     * @param rStep         The radius step.
     * @param loweredVision The offset of eyePos in the negative direction of the y-axis.
     */
    public ForwardConeParticlesToClient(Vec3 eyePos, Vec3 sightVec, float r, float aStep, float rStep, double loweredVision) {
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
        return new ForwardConeParticlesToClient(new Vec3(eX, eY, eZ), new Vec3(sX, sY, sZ), r, aStep, rStep, loweredVision);
    }

    public static void messageConsumer(ForwardConeParticlesToClient message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context context = ctxSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> handlePacket(message.eX, message.eY, message.eZ, message.sX, message.sY, message.sZ, message.r, message.aStep, message.rStep, message.loweredVision)));
        context.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean handlePacket(double x, double y, double z, double vecX, double vecY, double vecZ, float r, float aStep, float rStep, double loweredVision) {
        ParticleUtil.spawnForwardConeParticles(Minecraft.getInstance().player.level(), x, y, z, new Vec3(vecX, vecY, vecZ), ParticleTypes.SNOWFLAKE, r, aStep, rStep, loweredVision);
        return true;
    }
}
