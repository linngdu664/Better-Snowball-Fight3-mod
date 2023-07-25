package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.block.entity.LooseSnowBlockEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.registry.EntityRegister;
import com.linngdu664.bsf.registry.ItemRegister;
import com.linngdu664.bsf.util.BSFMthUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class IcicleSnowballEntity extends AbstractSnowStorageSnowballEntity {
    private static final int TRY_SUMMON_ICICLE_MAX_TIMES = 20;
    private static final int ICICLE_MAX_NUM = 15;
    private static final int TRY_SUMMON_ICICLE_DETECTION_RADIUS = 3;
    private final Icicle[] icicles = new Icicle[ICICLE_MAX_NUM];
    private boolean isBuildingIcicle = false;
    private int iciclesNum = 0;
    private BlockPos impactPoint;
    private class Icicle {
        private Vec3 icicleVec;
        private final double icicleStepSize;
        private final double icicleStepRadius;
        private final ArrayList<Icicle.IciclePoint> path = new ArrayList<>();

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
                    placeLooseSnowBlock(level, new BlockPos(BSFMthUtil.vec3ToI(point.add(c))));
                }
                radius += icicleStepRadius;
            }
        }

        public Icicle(Vec3 icicleVec, double icicleStepSize, double icicleStepRadius) {
            this.icicleVec = icicleVec;
            this.icicleStepSize = icicleStepSize;
            this.icicleStepRadius = icicleStepRadius;
            path.add(new Icicle.IciclePoint(impactPoint.getCenter().add(icicleVec)));
        }

        public void generate(Level level) {
            placeLooseSnowBlock(level, new BlockPos(BSFMthUtil.vec3ToI(impactPoint.getCenter().add(icicleVec))));
            icicleVec = icicleVec.add(icicleVec.normalize().scale(icicleStepSize));
            path.add(new Icicle.IciclePoint(impactPoint.getCenter().add(icicleVec)));
            for (Icicle.IciclePoint iciclePoint : path) {
                iciclePoint.pointGenerate(level);
            }
        }
    }

    public IcicleSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public IcicleSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment, int snowStock) {
        super(EntityRegister.ICICLE_SNOWBALL.get(),pShooter, pLevel, launchAdjustment,snowStock);
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
            if (!(level.getBlockEntity(result.getBlockPos()) instanceof LooseSnowBlockEntity)) {
                impactPoint = result.getBlockPos();
                icicleInit(level);
            }
        }
        super.onHitBlock(result);
    }

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        if (isBuildingIcicle) {
            stopTheSnowball();
            if (!level.isClientSide) {
                handleBuildIcicle(level);
            }
        }
    }

    private void icicleInit(Level level) {
        stopTheSnowball();
        this.setNoGravity(true);
        //Determine the direction of the icicle
        //init icicle
        for (int i = 0; i < TRY_SUMMON_ICICLE_MAX_TIMES; i++) {
            double theta = BSFMthUtil.randDouble(0, 2 * Mth.PI);
            double phi = Math.acos(BSFMthUtil.randDouble(-1, 1));
            Vec3 direction = BSFMthUtil.rotationToVector(TRY_SUMMON_ICICLE_DETECTION_RADIUS, theta, phi);
            BlockPos blockPos1 = impactPoint.offset(Mth.floor(direction.x), Mth.floor(direction.y), Mth.floor(direction.z));
            if ((level.getBlockState(blockPos1).canBeReplaced() || level.getBlockEntity(blockPos1) instanceof LooseSnowBlockEntity) && iciclesNum < ICICLE_MAX_NUM) {
                icicles[iciclesNum++] = new Icicle(direction.normalize(), BSFMthUtil.randDouble(0.3, 1), BSFMthUtil.randDouble(0.1, 0.2));
            }
        }
        if (iciclesNum == 0 || snowStock <= 0) {
            this.discard();
        }
        isBuildingIcicle = true;
    }

    private void stopTheSnowball() {
        Vec3 vec3 = this.getDeltaMovement();
        this.push(-vec3.x, -vec3.y, -vec3.z);
    }

    @Override
    public boolean canBeCaught() {
        return false;
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
        return 4F;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.ICICLE_SNOWBALL.get();
    }
}
