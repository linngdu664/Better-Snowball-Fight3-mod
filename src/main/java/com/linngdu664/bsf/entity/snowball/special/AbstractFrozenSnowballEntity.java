package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.block.entity.CriticalSnowEntity;
import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.entity.snowball.util.LaunchFrom;
import com.linngdu664.bsf.util.BSFMthUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public abstract class AbstractFrozenSnowballEntity extends AbstractBSFSnowballEntity {
    public AbstractFrozenSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AbstractFrozenSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
    }

    public AbstractFrozenSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(pEntityType, pShooter, pLevel, launchAdjustment);
    }

    public void setEffects(Level level, HitResult hitResult, BlockState newBlock) {
        if (!level.isClientSide) {
            if (!isCaught) {
                float frozenRange;
                if (launchAdjustment.getLaunchFrom() == LaunchFrom.FREEZING_CANNON) {
                    frozenRange = 3.5F;
                } else {
                    frozenRange = 2.5F;
                }
                Vec3 location = hitResult.getLocation();
                BlockPos blockPos = new BlockPos(Mth.floor(location.x), Mth.floor(location.y), Mth.floor(location.z));
                BlockState ice = Blocks.ICE.defaultBlockState();
                BlockState basalt = Blocks.BASALT.defaultBlockState();
                BlockState air = Blocks.AIR.defaultBlockState();
                for (int i = (int) (blockPos.getX() - frozenRange); i <= (int) (blockPos.getX() + frozenRange); i++) {
                    for (int j = (int) (blockPos.getY() - frozenRange); j <= (int) (blockPos.getY() + frozenRange); j++) {
                        for (int k = (int) (blockPos.getZ() - frozenRange); k <= (int) (blockPos.getZ() + frozenRange); k++) {
                            if (BSFMthUtil.modSqr(i - blockPos.getX(), j - blockPos.getY(), k - blockPos.getZ()) <= frozenRange * frozenRange) {
                                BlockPos blockPos1 = new BlockPos(i, j, k);
                                BlockState blockState = level.getBlockState(blockPos1);
                                if (blockState.getBlock() == Blocks.WATER && blockState.getValue(LiquidBlock.LEVEL) == 0) {
                                    level.setBlockAndUpdate(blockPos1, ice);
                                } else if (blockState.getBlock() == Blocks.LAVA && blockState.getValue(LiquidBlock.LEVEL) == 0) {
                                    level.setBlockAndUpdate(blockPos1, basalt);
                                } else if (blockState.getBlock() == Blocks.FIRE) {
                                    level.setBlockAndUpdate(blockPos1, air);
                                } else if (level.getBlockEntity(blockPos1) instanceof CriticalSnowEntity blockEntity){
                                    blockEntity.setAge(0);
                                    blockEntity.setChanged();
                                } else if (blockState.canBeReplaced() && newBlock.canSurvive(level, blockPos1)) {
                                    level.setBlockAndUpdate(blockPos1, newBlock);
                                }
                            }
                        }
                    }
                }
                List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(4), p -> !(p instanceof BSFSnowGolemEntity) && !(p instanceof SnowGolem) && !(p instanceof Player player && player.isSpectator()) && distanceToSqr(p) < frozenRange * frozenRange);
                for (LivingEntity entity : list) {
                    int frozenTicks = launchAdjustment.adjustFrozenTicks(getBasicFrozenTicks());
                    if (frozenTicks > 0) {
                        if (entity.getTicksFrozen() < 200) {
                            entity.setTicksFrozen(entity.getTicksFrozen()+frozenTicks);
                        }
                        entity.hurt(level.damageSources().thrown(this, this.getOwner()), Float.MIN_NORMAL);
                        if (launchAdjustment.getLaunchFrom() == LaunchFrom.FREEZING_CANNON) {
                            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 4));
                        }
                        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 2));
                    }
                }
                if (launchAdjustment.getLaunchFrom() == LaunchFrom.FREEZING_CANNON) {
                    ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 400, 0, 0, 0, 0.32);
                } else {
                    ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 200, 0, 0, 0, 0.32);
                }
                level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_HURT_FREEZE, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            }
            this.discard();
        }
    }

    @Override
    public boolean canBeCaught() {
        return true;
    }

    @Override
    public float getBasicDamage() {
        return 3;
    }

    @Override
    public float getBasicBlazeDamage() {
        return 8;
    }

    @Override
    public int getBasicWeaknessTicks() {
        return 0;
    }

    @Override
    public int getBasicFrozenTicks() {
        return 60;
    }

    @Override
    public double getBasicPunch() {
        return 0;
    }

    @Override
    public float getSubspacePower() {
        return 1.6f;
    }
}
