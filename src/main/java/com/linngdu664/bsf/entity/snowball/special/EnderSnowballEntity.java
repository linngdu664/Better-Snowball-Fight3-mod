package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.entity.EntityRegister;
import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.item.ItemRegister;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class EnderSnowballEntity extends AbstractBSFSnowballEntity {

//    public EnderSnowballEntity(LivingEntity livingEntity, Level level, LaunchFunc launchFunc) {
//        super(livingEntity, level);
//        this.setLaunchFrom(launchFunc.getLaunchFrom());
//        launchFunc.launchProperties(this);
//        this.setItem(new ItemStack(ItemRegister.ENDER_SNOWBALL.get()));
//    }
    public EnderSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public EnderSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.ENDER_SNOWBALL.get(), pShooter, pLevel);
        this.launchAdjustment = launchAdjustment;
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Level level = level();
        if (!isCaught) {
            Entity entity = pResult.getEntity();
            if (entity instanceof Player || entity instanceof Mob) {
                Entity owner = getOwner();
                ((ServerLevel) level).sendParticles(ParticleTypes.PORTAL, entity.getX(), entity.getEyeY(), entity.getZ(), 32, 1, 1, 1, 0.1);
                ((ServerLevel) level).sendParticles(ParticleTypes.PORTAL, owner.getX(), owner.getEyeY(), owner.getZ(), 32, 1, 1, 1, 0.1);
                this.discard();
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
                    serverPlayer.connection.send(new ClientboundPlayerPositionPacket(entity.getX(), entity.getY(), entity.getZ(), yRot2, xRot2, new HashSet<>(), owner.getId()));
                    serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(owner));
                }
                entity.absMoveTo(ownerPos.x, ownerPos.y, ownerPos.z, yRot1, xRot1);
                entity.push(v1.x - v2.x, v1.y - v2.y, v2.z - v1.z);
                if (entity instanceof ServerPlayer serverPlayer) {
                    serverPlayer.connection.send(new ClientboundPlayerPositionPacket(ownerPos.x, ownerPos.y, ownerPos.z, yRot1, xRot1, new HashSet<>(), entity.getId()));
                    serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(entity));
                }

            }
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult p_37258_) {
        super.onHitBlock(p_37258_);
        this.discard();
    }




    @Override
    public void tick() {
        super.tick();
        Level level = level();
        if (!level.isClientSide) {
            ((ServerLevel) level).sendParticles(ParticleTypes.PORTAL, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
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
        return 1.6f;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.ENDER_SNOWBALL.get();
    }
}