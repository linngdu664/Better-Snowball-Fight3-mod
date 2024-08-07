package com.linngdu664.bsf.entity.snowball;

import com.linngdu664.bsf.entity.Absorbable;
import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.entity.snowball.util.LaunchFrom;
import com.linngdu664.bsf.item.tool.GloveItem;
import com.linngdu664.bsf.registry.ParticleRegister;
import com.linngdu664.bsf.util.BSFConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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

public abstract class AbstractBSFSnowballEntity extends ThrowableItemProjectile implements Absorbable {
    protected float particleGenerationStepSize =0.5F;
    protected float particleGeneratePointOffset;
    protected Vec3 previousTickPosition;
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
        this.previousTickPosition=this.getPosition(0);
    }

    public AbstractBSFSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
        this.previousTickPosition=this.getPosition(0);
    }

    public AbstractBSFSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(pEntityType, pShooter, pLevel);
        this.launchAdjustment = launchAdjustment;
        this.previousTickPosition=this.getPosition(0);
    }

    public AbstractBSFSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(pEntityType, pShooter, pLevel);
        this.previousTickPosition=this.getPosition(0);
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
            if (getOwner() instanceof LivingEntity owner) {
                owner.setLastHurtMob(entity);
            }
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
        callTrackParticles();
    }
    protected void callTrackParticles(){
        float v = (float)this.getDeltaMovement().length();
        int n = (int) (v/ particleGenerationStepSize);
        int num=0;
        for (int i = 0; i <= n && particleGeneratePointOffset+i* particleGenerationStepSize <v; i++) {
            generateParticles(this.getPreviousPosition((particleGeneratePointOffset+i* particleGenerationStepSize)/v,previousTickPosition));
            num++;
        }
        particleGeneratePointOffset=num* particleGenerationStepSize +particleGeneratePointOffset-v;
        previousTickPosition=this.getPosition(0);
    }
    protected void callTrackParticlesEnd(Vec3 pos){
        float v = (float)this.getPosition(1).distanceTo(pos);
        Vec3 vec3d = this.getPosition(1).add(this.getDeltaMovement().normalize().scale(v));
        int n = (int) (v/ particleGenerationStepSize);
        for (int i = 0; i <= n && particleGeneratePointOffset+i* particleGenerationStepSize <v; i++) {
            generateParticles(this.getCurrentlyPosition((particleGeneratePointOffset+i* particleGenerationStepSize)/v,vec3d));
        }
    }
    protected void generateParticles(Vec3 vec3) {
        // Spawn trace particles
        Level level = level();
        if (level.isClientSide) {
            level.addParticle(ParticleRegister.SHORT_TIME_SNOWFLAKE.get(), vec3.x, vec3.y+0.1, vec3.z, 0, 0, 0);
        }
    }
    public final Vec3 getPreviousPosition(float pPartialTicks,Vec3 previousTickPosition) {
        double d0 = Mth.lerp(pPartialTicks, previousTickPosition.x, this.xo);
        double d1 = Mth.lerp(pPartialTicks, previousTickPosition.y, this.yo);
        double d2 = Mth.lerp(pPartialTicks, previousTickPosition.z, this.zo);
        return new Vec3(d0, d1, d2);
    }
    public final Vec3 getCurrentlyPosition(float pPartialTicks,Vec3 position) {
        double d0 = Mth.lerp(pPartialTicks, this.xo, position.x);
        double d1 = Mth.lerp(pPartialTicks, this.yo, position.y);
        double d2 = Mth.lerp(pPartialTicks, this.zo, position.z);
        return new Vec3(d0, d1, d2);
    }

    /**
     * @param entity The player who is using the glove.
     * @return If the glove catches return true.
     */
    private boolean catchOnGlove(LivingEntity entity) {
        Level level = level();
        if (entity instanceof Player player) {
            ItemStack mainHand = player.getMainHandItem();
            ItemStack offHand = player.getOffhandItem();
            if ((offHand.getItem() instanceof GloveItem && player.getUsedItemHand() == InteractionHand.OFF_HAND ||
                    mainHand.getItem() instanceof GloveItem && player.getUsedItemHand() == InteractionHand.MAIN_HAND) &&
                    player.isUsingItem() && isHeadingToSnowball(player) && canBeCaught()) {
                player.getInventory().placeItemBackInInventory(new ItemStack(getDefaultItem()));
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
                return true;
            }
        }
        return false;
    }

    // Check whether the player can catch the snowball
    private boolean isHeadingToSnowball(Player player) {
        Vec3 speedVec = this.getDeltaMovement().normalize();
        Vec3 cameraVec = Vec3.directionFromRotation(player.getXRot(), player.getYRot());
        return Math.abs(cameraVec.dot(speedVec) + 1.0) < 0.2;
    }

    protected void handleExplosion(float radius,Vec3 location) {
        Level level = level();
        if (!level.isClientSide) {
            if (level.getGameRules().getBoolean((GameRules.RULE_MOBGRIEFING)) && BSFConfig.explosiveDestroy) {
                level.explode(null, location.x, location.y, location.z, radius, Level.ExplosionInteraction.TNT);
            } else {
                level.explode(null, location.x, location.y, location.z, radius, Level.ExplosionInteraction.NONE);
            }
        }

    }

    protected void spawnBasicParticles(Level level) {
        if (!level.isClientSide) {
            ((ServerLevel) level).sendParticles(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY(), this.getZ(), 8, 0, 0, 0, 0);
            ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 8, 0, 0, 0, 0.04);
        }
    }

    public boolean canBeCaught() {
        return true;
    }

    public float getBasicDamage() {
        return Float.MIN_NORMAL;
    }

    public float getBasicBlazeDamage() {
        return 3;
    }

    public int getBasicWeaknessTicks() {
        return 0;
    }

    public int getBasicFrozenTicks() {
        return 0;
    }

    public double getBasicPunch() {
        return 0;
    }

    @Override
    public float getSubspacePower() {
        return 1;
    }

    @Override
    public ItemStack getSnowballItem() {
        return getItem();
    }
}
