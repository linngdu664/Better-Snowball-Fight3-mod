package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.block.LooseSnowBlock;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.registry.BlockRegister;
import com.linngdu664.bsf.registry.EntityRegister;
import com.linngdu664.bsf.registry.ItemRegister;
import com.linngdu664.bsf.registry.SoundRegister;
import com.linngdu664.bsf.util.BSFMthUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class IcicleSnowballEntity extends AbstractSnowStorageSnowballEntity {
    private static final int TRY_SUMMON_ICICLE_MAX_TIMES = 20;
    private static final int ICICLE_MAX_NUM = 15;
    private static final int TRY_SUMMON_ICICLE_DETECTION_RADIUS = 3;
    private static final double FREEZE_PERCENTAGE = BSFMthUtil.randDouble(0.6, 0.9);
    private static final int FREEZE_TIME = BSFMthUtil.randInt(25, 50);
    private static final float FREEZE_PROPAGATION_RATE = 0.1f;
    private final Icicle[] icicles = new Icicle[ICICLE_MAX_NUM];
    private final Queue<BlockPos> tmpFreezingBlocks = new LinkedList<>();
    private boolean isBuildingIcicle = false;
    private boolean isFreezing = false;
    private int iciclesNum = 0;
    private int initSnowStock = 0;
    private int freezingCount = 0;
    private BlockPos impactPoint;

    public IcicleSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public IcicleSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment, int snowStock) {
        super(EntityRegister.ICICLE_SNOWBALL.get(), pShooter, pLevel, launchAdjustment, snowStock);
        this.initSnowStock = snowStock;
        this.destroyStepSize = Math.max(snowStock / 60, 1);
    }

    private void handleBuildIcicle(Level level) {
        if (snowStock <= 0) return;
        for (int i = 0; i < iciclesNum; i++) {
            icicles[i].generate(level);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        Level level = level();
        if (!level.isClientSide && !isBuildingIcicle) {
            impactPoint = new BlockPos(BSFMthUtil.vec3ToI(pResult.getLocation()));
            icicleInit(level);
        }
        super.onHitEntity(pResult);
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        Level level = level();
        if (!level.isClientSide && !isBuildingIcicle) {
            if (!posIsLooseSnow(level, result.getBlockPos())) {
                impactPoint = result.getBlockPos();
                icicleInit(level);
            }
        }
        super.onHitBlock(result);
    }

    @Override
    public void tick() {

        Level level = level();
        if (isBuildingIcicle) {
            stopTheSnowball(impactPoint.getCenter());
            if (isFreezing) {
                hendleFrozenSpread(level);
            } else {
                handleBuildIcicle(level);
            }
        }
        super.tick();
    }

    private void hendleFrozenSpread(Level level) {
        int freezingSpeed = initSnowStock / FREEZE_TIME;
        if (!level.isClientSide) {
            for (int t = 0; t < freezingSpeed && freezingCount < initSnowStock * FREEZE_PERCENTAGE && !tmpFreezingBlocks.isEmpty(); t++) {
                BlockPos blockPos = tmpFreezingBlocks.poll();
                int x = blockPos.getX();
                int y = blockPos.getY();
                int z = blockPos.getZ();
                tryAddBlockState(level, x + 1, y, z);
                tryAddBlockState(level, x - 1, y, z);
                tryAddBlockState(level, x, y + 1, z);
                tryAddBlockState(level, x, y - 1, z);
                tryAddBlockState(level, x, y, z + 1);
                tryAddBlockState(level, x, y, z - 1);
                for (int i = -1; i < 2; i += 1) {
                    for (int j = -1; j < 2; j += 1) {
                        for (int k = -1; k < 2; k += 1) {
                            tryAddBlockState(level, x + i, y + j, z + k);
                        }
                    }
                }
            }
        }
    }

    private void tryAddBlockState(Level level, int x, int y, int z) {
        BlockPos blockPos = new BlockPos(x, y, z);
        BlockState blockState = level.getBlockState(blockPos);
        if (posIsLooseSnow(level, blockPos) && blockState.getValue(LooseSnowBlock.FROZEN) == 0 && BSFMthUtil.randDouble(0, 1) < FREEZE_PROPAGATION_RATE && freezingCount < initSnowStock * FREEZE_PERCENTAGE) {
            tmpFreezingBlocks.offer(blockPos);
            level.setBlockAndUpdate(blockPos, blockState.setValue(LooseSnowBlock.FROZEN, 1));
            level.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundRegister.FREEZING.get(), SoundSource.NEUTRAL, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            freezingCount++;
        }
    }

    private void icicleInit(Level level) {
        stopTheSnowball(impactPoint.getCenter());
        this.setNoGravity(true);
        //Determine the direction of the icicle
        //init icicle
        for (int i = 0; i < TRY_SUMMON_ICICLE_MAX_TIMES; i++) {
            double theta = BSFMthUtil.randDouble(0, 2 * Mth.PI);
            double phi = Math.acos(BSFMthUtil.randDouble(-1, 1));
            Vec3 direction = BSFMthUtil.rotationToVector(TRY_SUMMON_ICICLE_DETECTION_RADIUS, theta, phi);
            BlockPos blockPos1 = impactPoint.offset(Mth.floor(direction.x), Mth.floor(direction.y), Mth.floor(direction.z));
            if ((level.getBlockState(blockPos1).canBeReplaced() || posIsLooseSnow(level, blockPos1)) && iciclesNum < ICICLE_MAX_NUM) {
                icicles[iciclesNum++] = new Icicle(direction.normalize(), BSFMthUtil.randDouble(0.3, 1), BSFMthUtil.randDouble(0.1, 0.2));
            }
        }
        if (iciclesNum == 0 || snowStock <= 0) {
            this.discard();
        }
        isBuildingIcicle = true;
    }


    protected void tryPlaceLooseSnowBlock(Level level, BlockPos blockPos) {
        if (snowStock > 0) {
            if (level.getBlockState(blockPos).canBeReplaced()) {
                if (!level.isClientSide) {
                    placeAndRecordBlock(level, blockPos);
                    level.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.SNOW_PLACE, SoundSource.NEUTRAL, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                }
                snowStock--;
            }

        } else {
            if (!level.isClientSide) {
                BlockState blockState = level.getBlockState(blockPos);
                if (blockState.getBlock().getName().getString().equals(BlockRegister.LOOSE_SNOW_BLOCK.get().getName().getString()) && blockState.getValue(LooseSnowBlock.FROZEN) != 1) {
                    placeAndRecordBlock(level, blockPos);
                }
                if (!isFreezing) {
                    for (int i = 0; i < 4; i++) {
                        tmpFreezingBlocks.offer(impactPoint);
                    }
                    isFreezing = true;
                }
            }
            if (!inBlockDuration) {
                startTimingOfDiscard(new Vec3(this.getX(), this.getY(), this.getZ()));
            }

        }
    }

    @Override
    public boolean canBeCaught() {
        return false;
    }

    @Override
    public float getSubspacePower() {
        return 4F;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.ICICLE_SNOWBALL.get();
    }

    private class Icicle {
        private final double icicleStepSize;
        private final double icicleStepRadius;
        private final ArrayList<Icicle.IciclePoint> path = new ArrayList<>();
        private Vec3 icicleVec;

        public Icicle(Vec3 icicleVec, double icicleStepSize, double icicleStepRadius) {
            this.icicleVec = icicleVec;
            this.icicleStepSize = icicleStepSize;
            this.icicleStepRadius = icicleStepRadius;
            path.add(new Icicle.IciclePoint(impactPoint.getCenter().add(icicleVec)));
        }

        public void generate(Level level) {
            tryPlaceLooseSnowBlock(level, new BlockPos(BSFMthUtil.vec3ToI(impactPoint.getCenter().add(icicleVec))));
            icicleVec = icicleVec.add(icicleVec.normalize().scale(icicleStepSize));
            path.add(new Icicle.IciclePoint(impactPoint.getCenter().add(icicleVec)));
            for (Icicle.IciclePoint iciclePoint : path) {
                iciclePoint.pointGenerate(level);
            }
        }

        private class IciclePoint {
            private final Vec3 point;
            private double radius = 0;

            public IciclePoint(Vec3 point) {
                this.point = point;
            }

            public void pointGenerate(Level level) {
                for (int i = 0; i < Mth.ceil(radius); i++) {
                    Vec3 a = icicleVec.cross(new Vec3(0, 1, 0)).normalize();
                    if (a.lengthSqr() == 0) {
                        a = icicleVec.cross(new Vec3(1, 0, 0)).normalize();
                    }
                    Vec3 b = a.cross(icicleVec).normalize();
                    float x = (float) BSFMthUtil.randDouble(0, 2 * Mth.PI);
                    Vec3 c = a.scale(Mth.cos(x)).add(b.scale(Mth.sin(x))).scale(radius);
                    tryPlaceLooseSnowBlock(level, new BlockPos(BSFMthUtil.vec3ToI(point.add(c))));
                }
                radius += icicleStepRadius;
            }
        }
    }
}
