package com.linngdu664.bsf.entity;

import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.item.tool.GloveItem;
import com.linngdu664.bsf.particle.ParticleRegister;
import com.linngdu664.bsf.util.LaunchFrom;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class BSFSnowballEntity extends ThrowableItemProjectile {
    protected boolean isCaught = false;
    protected double punch = 0.0;
    protected float damage = Float.MIN_NORMAL;
    protected float blazeDamage = 3.0F;
    protected LaunchFrom launchFrom;
    protected int frozenTicks = 0;
    protected int weaknessTicks = 0;
    // You need to distinguish between LaunchFrom and LaunchFunc
    // LaunchFrom is an Enum, LaunchFunc is an Interface

    public BSFSnowballEntity(EntityType<? extends BSFSnowballEntity> p_37473_, Level p_37474_) {
        super(p_37473_, p_37474_);
    }

    public BSFSnowballEntity(LivingEntity livingEntity, Level level) {
        super(EntityRegister.BSF_SNOWBALL.get(), livingEntity, level);
    }

    public BSFSnowballEntity(Level level, double x, double y, double z) {
        super(EntityRegister.BSF_SNOWBALL.get(), x, y, z, level);
    }

    /**
     * Triggered when an entity hits an entity
     *
     * @param pResult EntityHitResult
     */
    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        Level level = level();
        if (pResult.getEntity() instanceof LivingEntity entity) {
            // Handling the catch
            if (catchOnGlove(entity)) {
                if (!level.isClientSide) {
                    ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 3, 0, 0, 0, 0.04);
                }
                isCaught = true;
                return;
            }

            // Damage entity
            float hurt = entity instanceof Blaze ? blazeDamage : damage;
            entity.hurt(level.damageSources().thrown(this, this.getOwner()), hurt);

            // Handle frozen and weakness effects
            if (frozenTicks > 0 && !(entity instanceof BSFSnowGolemEntity) && !(entity instanceof SnowGolem)) {
                if (entity.getTicksFrozen() < frozenTicks) {
                    entity.setTicksFrozen(frozenTicks);
                }
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1));
            }
            if (weaknessTicks > 0) {
                entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, weaknessTicks, 1));
            }

            // Push entity
            Vec3 vec3d = this.getDeltaMovement().multiply(0.1 * punch, 0.0, 0.1 * punch);
            entity.push(vec3d.x, 0.0, vec3d.z);
        }
        spawnBasicParticles(level());
    }

    /**
     * Triggered when an entity hits a block.
     *
     * @param p_37258_ blockHitResult
     */
    @Override
    protected void onHitBlock(@NotNull BlockHitResult p_37258_) {
        super.onHitBlock(p_37258_);
        spawnBasicParticles(level());
    }

    /**
     * This method will be called every tick.
     */
    @Override
    public void tick() {
        // Spawn trace particles
        Level level = level();
        if (!level.isClientSide) {
            ((ServerLevel) level).sendParticles(ParticleRegister.SHORT_TIME_SNOWFLAKE.get(), this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
        }
        super.tick();
    }

    /**
     * Do not touch/override this magical fucking method, or the texture of the snowball will become egg!
     *
     * @return Who knows.
     */
    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.SMOOTH_SNOWBALL.get();
    }

    /**
     * You should override this fucking method if you want to catch the snowball!
     *
     * @return The corresponding item.
     */
    public Item getCorrespondingItem() {
        return null;
    }

    /**
     * @param entity The player who is using the glove.
     * @return If the glove catches return true.
     */
    protected boolean catchOnGlove(LivingEntity entity) {
        if (entity instanceof Player player && (player.getOffhandItem().is(ItemRegister.GLOVE.get()) &&
                player.getUsedItemHand() == InteractionHand.OFF_HAND || player.getMainHandItem().is(ItemRegister.GLOVE.get()) &&
                player.getUsedItemHand() == InteractionHand.MAIN_HAND) && player.isUsingItem() && isHeadingToSnowball(player)) {
            player.getInventory().placeItemBackInInventory(new ItemStack(getCorrespondingItem()));
            if (player.getMainHandItem().getItem() instanceof GloveItem glove) {
                player.getMainHandItem().hurtAndBreak(1, player, (e) -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                glove.releaseUsing(player.getMainHandItem(), player.level(), player, 1);
            } else if (player.getOffhandItem().getItem() instanceof GloveItem glove) {
                player.getOffhandItem().hurtAndBreak(1, player, (e) -> e.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                glove.releaseUsing(player.getOffhandItem(), player.level(), player, 1);
            }
            Level level = level();
            if (!level.isClientSide) {
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOW_BREAK, SoundSource.NEUTRAL, 3F, 0.4F / level.getRandom().nextFloat() * 0.4F + 0.8F);
                ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 3, 0, 0, 0, 0.04);
            }
            return true;
        }
        return false;
    }

    // Check whether the player can catch the snowball
    protected boolean isHeadingToSnowball(Player player) {
        Vec3 speedVec = this.getDeltaMovement().normalize();
        Vec3 cameraVec = Vec3.directionFromRotation(player.getXRot(), player.getYRot());
        return Math.abs(cameraVec.dot(speedVec) + 1.0) < 0.2;
    }

    protected void handleExplosion(float radius) {
        Level level = level();
        if (level.getGameRules().getBoolean((GameRules.RULE_MOBGRIEFING))) {
            level.explode(null, this.getX(), this.getY(), this.getZ(), radius, Level.ExplosionInteraction.TNT);
        } else {
            level.explode(null, this.getX(), this.getY(), this.getZ(), radius, Level.ExplosionInteraction.NONE);
        }
    }

    protected void spawnBasicParticles(Level level) {
        if (!level.isClientSide) {
            ((ServerLevel) level).sendParticles(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY(), this.getZ(), 8, 0, 0, 0, 0);
            ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 8, 0, 0, 0, 0.04);
        }
    }

    // Setters with builder style
    // Like this:  snowballEntity.setPunch(2.0).setDamage(2.5f)
    // If necessary, add setWeaknessTime.
    public BSFSnowballEntity setPunch(double punch) {
        this.punch = punch;
        return this;
    }

    public BSFSnowballEntity setLaunchFrom(LaunchFrom launchFrom) {
        this.launchFrom = launchFrom;
        return this;
    }

    public BSFSnowballEntity setFrozenTicks(int frozenTicks) {
        this.frozenTicks = frozenTicks;
        return this;
    }

    public BSFSnowballEntity setWeaknessTicks(int weaknessTicks) {
        this.weaknessTicks = weaknessTicks;
        return this;
    }

    public float getBlazeDamage() {
        return blazeDamage;
    }

    public BSFSnowballEntity setBlazeDamage(float blazeDamage) {
        this.blazeDamage = blazeDamage;
        return this;
    }

    public float getDamage() {
        return damage;
    }

    public BSFSnowballEntity setDamage(float damage) {
        this.damage = damage;
        return this;
    }

    public float getPower() {
        return 1;
    }
}
