package com.linngdu664.bsf.mixin;

import com.linngdu664.bsf.item.tool.GloveItem;
import com.linngdu664.bsf.registry.ParticleRegister;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Snowball.class)
public abstract class SnowballMixin extends ThrowableItemProjectile {
    public SnowballMixin(EntityType<? extends ThrowableItemProjectile> p_37442_, Level p_37443_) {
        super(p_37442_, p_37443_);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return Items.SNOWBALL;
    }

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        if (!level.isClientSide) {
            ((ServerLevel) level).sendParticles(ParticleRegister.SHORT_TIME_SNOWFLAKE.get(), this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult p_37258_) {
        super.onHitBlock(p_37258_);
        bsf$spawnBasicParticles(level());
    }

    // Cancel "broadcastEntityEvent" and discard directly.
    @Inject(method = "onHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;broadcastEntityEvent(Lnet/minecraft/world/entity/Entity;B)V"), cancellable = true)
    private void injectedOnHit(HitResult pResult, CallbackInfo ci) {
        this.discard();
        ci.cancel();
    }

    @Inject(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void injectedBeforeInvokeHurtOnHitEntity(EntityHitResult pResult, CallbackInfo ci, Entity entity) {
        Level level = level();
        if (entity instanceof Player player) {
            ItemStack mainHand = player.getMainHandItem();
            ItemStack offHand = player.getOffhandItem();
            if ((offHand.getItem() instanceof GloveItem && player.getUsedItemHand() == InteractionHand.OFF_HAND ||
                    mainHand.getItem() instanceof GloveItem && player.getUsedItemHand() == InteractionHand.MAIN_HAND) &&
                    player.isUsingItem() && bsf$isHeadingToSnowball(player)) {
                player.getInventory().placeItemBackInInventory(Items.SNOWBALL.getDefaultInstance(), true);
                if (mainHand.getItem() instanceof GloveItem glove) {
                    mainHand.hurtAndBreak(1, player, (e) -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                    glove.releaseUsing(mainHand, level, player, 1);
                } else if (offHand.getItem() instanceof GloveItem glove) {
                    offHand.hurtAndBreak(1, player, (e) -> e.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                    glove.releaseUsing(offHand, level, player, 1);
                }
                if (!level.isClientSide) {
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOW_BREAK, SoundSource.NEUTRAL, 3F, 0.4F / level.getRandom().nextFloat() * 0.4F + 0.8F);
                    ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 3, 0, 0, 0, 0.04);
                }
            } else {
                player.hurt(this.damageSources().thrown(this, this.getOwner()), Float.MIN_NORMAL);
                bsf$spawnBasicParticles(level());
            }
            ci.cancel();
        } else {
            bsf$spawnBasicParticles(level());
        }
    }

    @Unique
    private void bsf$spawnBasicParticles(Level level) {
        if (!level.isClientSide) {
            ((ServerLevel) level).sendParticles(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY(), this.getZ(), 8, 0, 0, 0, 0);
            ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 8, 0, 0, 0, 0.04);
        }
    }

    @Unique
    private boolean bsf$isHeadingToSnowball(Player player) {
        Vec3 speedVec = this.getDeltaMovement().normalize();
        Vec3 cameraVec = Vec3.directionFromRotation(player.getXRot(), player.getYRot());
        return Mth.abs((float) (cameraVec.dot(speedVec) + 1.0F)) < 0.2F;
    }
}
