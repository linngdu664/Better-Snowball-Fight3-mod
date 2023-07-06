package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.linngdu664.bsf.entity.BSFSnowballEntity;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.util.BSFMthUtil;
import com.linngdu664.bsf.util.LaunchFrom;
import com.linngdu664.bsf.util.LaunchFunc;
import com.linngdu664.bsf.util.TargetGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FrozenSnowballEntity extends BSFSnowballEntity {
    private float frozenRange = 2.5F;

    public FrozenSnowballEntity(LivingEntity livingEntity, Level level, LaunchFunc launchFunc) {
        super(livingEntity, level);
        this.setFrozenTicks(60).setLaunchFrom(launchFunc.getLaunchFrom()).setDamage(3).setBlazeDamage(8);
        launchFunc.launchProperties(this);
        this.setItem(new ItemStack(ItemRegister.FROZEN_SNOWBALL.get()));
        if (launchFrom == LaunchFrom.FREEZING_CANNON) {
            frozenRange = 3.5f;
        }
    }

    //This is only used for dispenser
    public FrozenSnowballEntity(Level level, double x, double y, double z) {
        super(level, x, y, z);
        this.setDamage(3).setBlazeDamage(8).setFrozenTicks(60);
        this.setItem(new ItemStack(ItemRegister.FROZEN_SNOWBALL.get()));
    }

    @Override
    public Item getCorrespondingItem() {
        return ItemRegister.FROZEN_SNOWBALL.get();
    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        Level level = level();
        if (!level.isClientSide) {
            if (!isCaught) {
                Vec3 location = pResult.getLocation();
                BlockPos blockPos = new BlockPos(Mth.floor(location.x), Mth.floor(location.y), Mth.floor(location.z));
                BlockState ice = Blocks.ICE.defaultBlockState();
                BlockState basalt = Blocks.BASALT.defaultBlockState();
                BlockState air = Blocks.AIR.defaultBlockState();
                BlockState snow = Blocks.SNOW.defaultBlockState();
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
                                } else if (blockState.getBlock() == Blocks.AIR && snow.canSurvive(level, blockPos1)) {
                                    level.setBlockAndUpdate(blockPos1, snow);
                                }
                            }
                        }
                    }
                }
                List<LivingEntity> list = TargetGetter.getTargetList(this, LivingEntity.class, 2.5F);
                for (LivingEntity entity : list) {
                    if (distanceToSqr(entity) < frozenRange * frozenRange && !(entity instanceof BSFSnowGolemEntity) && !(entity instanceof SnowGolem)) {
                        if (frozenTicks > 0) {
                            if (entity.getTicksFrozen() < frozenTicks) {
                                entity.setTicksFrozen(frozenTicks);
                            }
                            entity.hurt(level.damageSources().thrown(this, this.getOwner()), Float.MIN_NORMAL);
                            if (launchFrom == LaunchFrom.FREEZING_CANNON) {
                                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 4));
                            }
                            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 2));
                        }
                    }
                }
                if (launchFrom == LaunchFrom.FREEZING_CANNON) {
                    ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 400, 0, 0, 0, 0.32);
                } else {
                    ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 200, 0, 0, 0, 0.32);
                }
                level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_HURT_FREEZE, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            }
            this.discard();
        }
    }

    public float getPower() {
        return 1.6f;
    }
}
