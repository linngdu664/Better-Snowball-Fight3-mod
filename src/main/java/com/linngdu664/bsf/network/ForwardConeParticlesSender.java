package com.linngdu664.bsf.network;

import com.linngdu664.bsf.particle.ParticleUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForwardConeParticlesSender {
    final double x, y, z, loweredVision, vecX, vecY, vecZ;
    final float aStep, rStep, r;

    public ForwardConeParticlesSender(LivingEntity entity, Vec3 sightVec, float r, float aStep, float rStep, double loweredVision) {
        this.x = entity.getX();
        this.y = entity.getEyeY();
        this.z = entity.getZ();
        this.vecX = sightVec.x;
        this.vecY = sightVec.y;
        this.vecZ = sightVec.z;
        this.r = r;
        this.aStep = aStep;
        this.rStep = rStep;
        this.loweredVision = loweredVision;
    }

    public ForwardConeParticlesSender(FriendlyByteBuf buffer) {
        this.x = buffer.readDouble();
        this.y = buffer.readDouble();
        this.z = buffer.readDouble();
        this.vecX = buffer.readDouble();
        this.vecY = buffer.readDouble();
        this.vecZ = buffer.readDouble();
        this.r = buffer.readFloat();
        this.aStep = buffer.readFloat();
        this.rStep = buffer.readFloat();
        this.loweredVision = buffer.readDouble();
    }

    public static void buffer(ForwardConeParticlesSender message, FriendlyByteBuf buffer) {
        buffer.writeDouble(message.x);
        buffer.writeDouble(message.y);
        buffer.writeDouble(message.z);
        buffer.writeDouble(message.vecX);
        buffer.writeDouble(message.vecY);
        buffer.writeDouble(message.vecZ);
        buffer.writeFloat(message.r);
        buffer.writeFloat(message.aStep);
        buffer.writeFloat(message.rStep);
        buffer.writeDouble(message.loweredVision);
    }

    public static void handler(ForwardConeParticlesSender message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> handlePacket(message.x, message.y, message.z, message.vecX, message.vecY, message.vecZ, message.r, message.aStep, message.rStep, message.loweredVision)));
        contextSupplier.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean handlePacket(double x, double y, double z, double vecX, double vecY, double vecZ, float r, float aStep, float rStep, double loweredVision) {
        ParticleUtil.spawnForwardConeParticles(Minecraft.getInstance().player.level(), x, y, z, new Vec3(vecX, vecY, vecZ), ParticleTypes.SNOWFLAKE, r, aStep, rStep, loweredVision);
        return true;
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        Network.addNetworkMessage(ForwardConeParticlesSender.class, ForwardConeParticlesSender::buffer, ForwardConeParticlesSender::new, ForwardConeParticlesSender::handler);
    }
}
