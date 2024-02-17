package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.registry.EntityRegister;
import com.linngdu664.bsf.registry.ItemRegister;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class EnderSnowballEntity extends AbstractBSFSnowballEntity {
    public EnderSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public EnderSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.ENDER_SNOWBALL.get(), pShooter, pLevel, launchAdjustment);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (!isCaught && !level.isClientSide) {
            Entity entity = pResult.getEntity();
            if (getOwner() != null && (entity instanceof Player || entity instanceof Mob)) {
                Entity owner = getOwner();
                generateTpParticles(entity, level);
                generateTpParticles(owner, level);
                Vec3 ownerPos = owner.position();
                Vec3 v1 = owner.getDeltaMovement();
                Vec3 v2 = entity.getDeltaMovement();
                float xRot1 = owner.getXRot();
                float yRot1 = owner.getYRot();
                float xRot2 = entity.getXRot();
                float yRot2 = entity.getYRot();
                owner.absMoveTo(entity.getX(), entity.getY(), entity.getZ(), yRot2, xRot2);
                owner.push(v2.x - v1.x, v2.y - v1.y, v2.z - v1.z);
                if (owner instanceof ServerPlayer serverPlayer) {
                    serverPlayer.connection.send(new ClientboundPlayerPositionPacket(entity.getX(), entity.getY(), entity.getZ(), yRot2, xRot2, new HashSet<>(), owner.getId(), true));
                    serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(owner));
                }
                entity.absMoveTo(ownerPos.x, ownerPos.y, ownerPos.z, yRot1, xRot1);
                entity.push(v1.x - v2.x, v1.y - v2.y, v2.z - v1.z);
                if (entity instanceof ServerPlayer serverPlayer) {
                    serverPlayer.connection.send(new ClientboundPlayerPositionPacket(ownerPos.x, ownerPos.y, ownerPos.z, yRot1, xRot1, new HashSet<>(), entity.getId(), true));
                    serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(entity));
                }
                level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.NEUTRAL, 1.0F, 1.0F);
                level.playSound(null, owner.getX(), owner.getY(), owner.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
        }
        this.discard();
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult p_37258_) {
        super.onHitBlock(p_37258_);
        this.discard();
    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide) {
            ((ServerLevel) level).sendParticles(ParticleTypes.PORTAL, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
        }
    }

    @Override
    public float getSubspacePower() {
        return 1.6f;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.ENDER_SNOWBALL.get();
    }

    private void generateTpParticles(Entity entity, Level level) {
        AABB aabb = entity.getBoundingBox();
        Vec3 center = aabb.getCenter();
        double x = 0.5 * (aabb.maxX - aabb.minX);
        double y = 0.5 * (aabb.maxY - aabb.minY);
        ((ServerLevel) level).sendParticles(ParticleTypes.PORTAL, center.x, center.y, center.z, (int) (1000 * x * x * y), x, y, x, 0);
    }
}