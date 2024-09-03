package com.linngdu664.bsf.entity.snowball;

import com.linngdu664.bsf.config.ServerConfig;
import com.linngdu664.bsf.entity.Absorbable;
import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.entity.snowball.util.LaunchFrom;
import com.linngdu664.bsf.item.tool.GloveItem;
import com.linngdu664.bsf.registry.ParticleRegister;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
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
    protected float particleGenerationStepSize = 0.5F;
    protected float particleGeneratePointOffset;
    protected Vec3 previousTickPosition = getPosition(0);
    protected boolean isCaught = false;
    private final BSFSnowballEntityProperties properties;

    public AbstractBSFSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel, BSFSnowballEntityProperties pProperties) {
        super(pEntityType, pLevel);
        this.properties = pProperties;
    }

    public AbstractBSFSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, double pX, double pY, double pZ, Level pLevel, BSFSnowballEntityProperties pProperties) {
        super(pEntityType, pX, pY, pZ, pLevel);
        this.properties = pProperties;
    }

    public AbstractBSFSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel, BSFSnowballEntityProperties pProperties) {
        super(pEntityType, pShooter, pLevel);
        this.properties = pProperties;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putFloat("Damage", properties.damage);
        pCompound.putFloat("BlazeDamage", properties.blazeDamage);
        pCompound.putInt("WeaknessTicks", properties.weaknessTicks);
        pCompound.putInt("FrozenTicks", properties.frozenTicks);
        pCompound.putDouble("Punch", properties.punch);
        pCompound.putBoolean("CanBeCaught", properties.canBeCaught);
        pCompound.putInt("LaunchFrom", properties.launchFrom.ordinal());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        properties.damage = pCompound.getFloat("Damage");
        properties.blazeDamage = pCompound.getFloat("BlazeDamage");
        properties.weaknessTicks = pCompound.getInt("WeaknessTicks");
        properties.frozenTicks = pCompound.getInt("FrozenTicks");
        properties.punch = pCompound.getDouble("Punch");
        properties.canBeCaught = pCompound.getBoolean("CanBeCaught");
        properties.launchFrom = LaunchFrom.values()[pCompound.getInt("LaunchFrom")];
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

            // Damage entity
            float hurt = entity instanceof Blaze ? properties.blazeDamage : properties.damage;
            entity.hurt(level.damageSources().thrown(this, this.getOwner()), hurt);

            // Handle frozen and weakness effects
            if (properties.frozenTicks > 0 && !(entity instanceof BSFSnowGolemEntity) && !(entity instanceof SnowGolem)) {
                if (entity.getTicksFrozen() < properties.frozenTicks) {
                    entity.setTicksFrozen(properties.frozenTicks);
                }
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1));
            }
            if (properties.weaknessTicks > 0) {
                entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, properties.weaknessTicks, 1));
            }

            // Push entity
            Vec3 vec3d = this.getDeltaMovement().multiply(0.1 * properties.punch, 0.0, 0.1 * properties.punch);
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

    protected void handleExplosion(float radius, Vec3 location) {
        Level level = level();
        if (!level.isClientSide) {
            if (level.getGameRules().getBoolean((GameRules.RULE_MOBGRIEFING)) && ServerConfig.EXPLOSIVE_DESTROY.getConfigValue()) {
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

    public final boolean canBeCaught() {
        return properties.canBeCaught;
    }

    public final float getDamage() {
        return properties.damage;
    }

    public final float getBlazeDamage() {
        return properties.blazeDamage;
    }

    public final int getWeaknessTicks() {
        return properties.weaknessTicks;
    }

    public final int getFrozenTicks() {
        return properties.frozenTicks;
    }

    public final double getPunch() {
        return properties.punch;
    }

    public final LaunchFrom getLaunchFrom() {
        return properties.launchFrom;
    }

    public void setDamage(float damage) {
        properties.damage = damage;
    }

    public void setBlazeDamage(float damage) {
        properties.blazeDamage = damage;
    }

    @Override
    public float getSubspacePower() {
        return 1;
    }

    @Override
    public ItemStack getSnowballItem() {
        return getItem();
    }

    public static class BSFSnowballEntityProperties {
        float damage;
        float blazeDamage;
        int weaknessTicks;
        int frozenTicks;
        double punch;
        boolean canBeCaught;
        LaunchFrom launchFrom;

        public BSFSnowballEntityProperties() {
            damage = Float.MIN_VALUE;
            blazeDamage = 3;
            weaknessTicks = 0;
            frozenTicks = 0;
            punch = 0;
            canBeCaught = true;
            launchFrom = LaunchFrom.HAND;
        }

        public BSFSnowballEntityProperties basicDamage(float damage) {
            this.damage = damage;
            return this;
        }

        public BSFSnowballEntityProperties basicBlazeDamage(float damage) {
            this.blazeDamage = damage;
            return this;
        }

        public BSFSnowballEntityProperties basicWeaknessTicks(int ticks) {
            this.weaknessTicks = ticks;
            return this;
        }

        public BSFSnowballEntityProperties basicFrozenTicks(int ticks) {
            this.frozenTicks = ticks;
            return this;
        }

        public BSFSnowballEntityProperties basicPunch(double punch) {
            this.punch = punch;
            return this;
        }

        public BSFSnowballEntityProperties canBeCaught(boolean canBeCaught) {
            this.canBeCaught = canBeCaught;
            return this;
        }

        public BSFSnowballEntityProperties applyAdjustment(ILaunchAdjustment adjustment) {
            blazeDamage = adjustment.adjustBlazeDamage(blazeDamage);
            damage = adjustment.adjustDamage(damage);
            frozenTicks = adjustment.adjustFrozenTicks(frozenTicks);
            weaknessTicks = adjustment.adjustWeaknessTicks(weaknessTicks);
            punch = adjustment.adjustPunch(punch);
            launchFrom = adjustment.getLaunchFrom();
            return this;
        }
    }
}
