package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.client.screenshake.Easing;
import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.network.ScreenshakeToClient;
import com.linngdu664.bsf.registry.*;
import com.linngdu664.bsf.util.BSFCommonUtil;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ImpulseSnowballEntity extends AbstractBSFSnowballEntity {
    public ImpulseSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, new BSFSnowballEntityProperties());
    }

    public ImpulseSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.IMPULSE_SNOWBALL.get(), pShooter, pLevel, new BSFSnowballEntityProperties().applyAdjustment(launchAdjustment));
    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        Level level = level();
        if (!level.isClientSide) {
            if (!isCaught) {
                Vec3 location = BSFCommonUtil.getRealHitPosOnMoveVecWithHitResult(this, pResult);
                List<Entity> list = level.getEntitiesOfClass(Entity.class, new AABB(location,location).inflate(4), EntitySelector.NO_SPECTATORS);
                impulseForceEffect(list,location);
                ((ServerLevel) level).sendParticles(ParticleRegister.IMPULSE.get(), location.x, location.y, location.z, 1, 0, 0, 0, 0);
                level.playSound(null, location.x, location.y, location.z, SoundRegister.MEME[1].get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
            discard();
        }
    }

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        if (!level.isClientSide) {
            List<Projectile> list = level.getEntitiesOfClass(Projectile.class, getBoundingBox().inflate(2), p -> !this.equals(p));
            if (!list.isEmpty()) {
                List<Entity> list1 = level.getEntitiesOfClass(Entity.class, getBoundingBox().inflate(4), EntitySelector.NO_SPECTATORS);
                impulseForceEffect(list1,getPosition(1));
                discard();
                ((ServerLevel) level).sendParticles(ParticleRegister.IMPULSE.get(), this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
                level.playSound(null, getX(), getY(), getZ(), SoundRegister.MEME[1].get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
        }
    }

    private void impulseForceEffect(List<? extends Entity> list,Vec3 pos) {
        for (Entity entity : list) {
            Vec3 rVec = new Vec3(entity.getX(), (entity.getY() + entity.getEyeY()) * 0.5, entity.getZ()).add(pos.reverse());
            if (rVec.length() < 4) {
                Vec3 norm = rVec.normalize();
                Vec3 aVec = norm.scale(2).add(rVec.scale(-0.2));
                entity.push(aVec.x, aVec.y, aVec.z);
                if (entity instanceof ServerPlayer player) {
                    player.connection.send(new ClientboundSetEntityMotionPacket(entity));
                    NetworkRegister.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> player), new ScreenshakeToClient(5).setEasing(Easing.EXPO_IN_OUT).setIntensity(0.8F));
                }
            }
        }
    }

    @Override
    public float getSubspacePower() {
        return 1.5F;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.IMPULSE_SNOWBALL.get();
    }
}
