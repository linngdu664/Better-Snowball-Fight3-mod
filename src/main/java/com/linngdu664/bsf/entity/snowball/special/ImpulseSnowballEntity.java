package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.registry.EntityRegister;
import com.linngdu664.bsf.registry.ItemRegister;
import com.linngdu664.bsf.registry.ParticleRegister;
import com.linngdu664.bsf.registry.SoundRegister;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ImpulseSnowballEntity extends AbstractBSFSnowballEntity {
    public ImpulseSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ImpulseSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.IMPULSE_SNOWBALL.get(), pShooter, pLevel, launchAdjustment);
    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        Level level = level();
        if (!level.isClientSide) {
            if (!isCaught) {
                List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(4), p -> !(p instanceof Player player && player.isSpectator()));
                impulseForceEffect(list);
                ((ServerLevel) level).sendParticles(ParticleRegister.IMPULSE.get(), this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
                level.playSound(null, getX(), getY(), getZ(), SoundRegister.MEME[0].get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
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
                List<Projectile> list1 = level.getEntitiesOfClass(Projectile.class, getBoundingBox().inflate(4), p -> !this.equals(p));
                impulseForceEffect(list1);
                discard();
                ((ServerLevel) level).sendParticles(ParticleRegister.IMPULSE.get(), this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
                level.playSound(null, getX(), getY(), getZ(), SoundRegister.MEME[0].get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
        }
    }

    private void impulseForceEffect(List<? extends Entity> list) {
        Vec3 pos = new Vec3(getX(), getY(), getZ());
        for (Entity entity : list) {
            Vec3 rVec = new Vec3(entity.getX(), (entity.getY() + entity.getEyeY()) * 0.5, entity.getZ()).add(pos.reverse());
            if (rVec.length() < 4) {
                Vec3 norm = rVec.normalize();
                Vec3 aVec = norm.scale(2).add(rVec.scale(-0.2));
                entity.push(aVec.x, aVec.y, aVec.z);
                if (entity instanceof ServerPlayer player) {
                    player.connection.send(new ClientboundSetEntityMotionPacket(entity));
                }
            }
        }
    }

    @Override
    public boolean canBeCaught() {
        return true;
    }

    @Override
    public float getBasicDamage() {
        return Float.MIN_NORMAL;
    }

    @Override
    public float getBasicBlazeDamage() {
        return 3;
    }

    @Override
    public int getBasicWeaknessTicks() {
        return 0;
    }

    @Override
    public int getBasicFrozenTicks() {
        return 0;
    }

    @Override
    public double getBasicPunch() {
        return 0;
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
