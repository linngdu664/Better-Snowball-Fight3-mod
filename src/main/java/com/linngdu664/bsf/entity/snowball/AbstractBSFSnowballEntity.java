package com.linngdu664.bsf.entity.snowball;

import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.entity.snowball.util.LaunchFrom;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.item.tool.GloveItem;
import com.linngdu664.bsf.particle.ParticleRegister;
import com.linngdu664.bsf.util.TargetGetter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class AbstractBSFSnowballEntity extends ThrowableItemProjectile {
    protected boolean isCaught = false;
    protected ILaunchAdjustment launchAdjustment = new ILaunchAdjustment() {
        @Override
        public double adjustPunch(double punch) {
            return punch;
        }

        @Override
        public int adjustWeaknessTicks(int weaknessTicks) {
            return weaknessTicks;
        }

        @Override
        public int adjustFrozenTicks(int frozenTicks) {
            return frozenTicks;
        }

        @Override
        public float adjustDamage(float damage) {
            return damage;
        }

        @Override
        public float adjustBlazeDamage(float blazeDamage) {
            return blazeDamage;
        }

        @Override
        public LaunchFrom getLaunchFrom() {
            return LaunchFrom.HAND;
        }
    };

    public AbstractBSFSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AbstractBSFSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
    }

    public AbstractBSFSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(pEntityType, pShooter, pLevel);
    }


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

            float blazeDamage = launchAdjustment.adjustBlazeDamage(getBasicBlazeDamage());
            float damage = launchAdjustment.adjustDamage(getBasicDamage());
            int frozenTicks = launchAdjustment.adjustFrozenTicks(getBasicFrozenTicks());
            int weaknessTicks = launchAdjustment.adjustWeaknessTicks(getBasicWeaknessTicks());
            double punch = launchAdjustment.adjustPunch(getBasicPunch());

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
        spawnBasicParticles(level);
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
        super.tick();
        // Spawn trace particles
        Level level = level();
        if (!level.isClientSide) {
            ((ServerLevel) level).sendParticles(ParticleRegister.SHORT_TIME_SNOWFLAKE.get(), this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
        }
    }

    /**
     * @param entity The player who is using the glove.
     * @return If the glove catches return true.
     */
    private boolean catchOnGlove(LivingEntity entity) {
        if (entity instanceof Player player && canBeCaught() && (player.getOffhandItem().is(ItemRegister.GLOVE.get()) &&
                player.getUsedItemHand() == InteractionHand.OFF_HAND || player.getMainHandItem().is(ItemRegister.GLOVE.get()) &&
                player.getUsedItemHand() == InteractionHand.MAIN_HAND) && player.isUsingItem() && isHeadingToSnowball(player)) {
            player.getInventory().placeItemBackInInventory(new ItemStack(getDefaultItem()));
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
    private boolean isHeadingToSnowball(Player player) {
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

    public void forceEffect(Class<? extends Entity> targetClass, double range, double boundaryR2, double GM) {
        List<? extends Entity> list = TargetGetter.getTargetList(this, targetClass, range);
        for (Entity entity : list) {
            Vec3 rVec = new Vec3(getX() - entity.getX(), getY() - entity.getEyeY(), getZ() - entity.getZ());
            double r2 = rVec.lengthSqr();
            double ir2 = Mth.invSqrt(r2);
            double a;
            if (r2 > boundaryR2) {
                a = GM / r2;
            } else if (r2 > 0.25) {
                a = GM / boundaryR2;
            } else {
                a = 0;
            }
            entity.push(a * rVec.x * ir2, a * rVec.y * ir2, a * rVec.z * ir2);
            //Tell client that player should move because client handles player's movement.
            if (entity instanceof ServerPlayer player && !player.getAbilities().instabuild && !player.getAbilities().invulnerable) {
                player.connection.send(new ClientboundSetEntityMotionPacket(entity));
            }
        }
    }

    private void spawnBasicParticles(Level level) {
        if (!level.isClientSide) {
            ((ServerLevel) level).sendParticles(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY(), this.getZ(), 8, 0, 0, 0, 0);
            ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 8, 0, 0, 0, 0.04);
        }
    }

    abstract public boolean canBeCaught();
    abstract public float getBasicDamage();
    abstract public float getBasicBlazeDamage();
    abstract public int getBasicWeaknessTicks();
    abstract public int getBasicFrozenTicks();
    abstract public double getBasicPunch();
    abstract public float getSubspacePower();
}