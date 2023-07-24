package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.block.BlockRegister;
import com.linngdu664.bsf.block.entity.LooseSnowBlockEntity;
import com.linngdu664.bsf.entity.EntityRegister;
import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.item.ItemRegister;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ReconstructSnowballEntity extends AbstractBSFSnowballEntity {
    private static final int GENERATING_DISTANCE = 1;
    private static final int POS_NUM = 10 + GENERATING_DISTANCE;
    private static final int GROWTH_CONSTRAINT = 3;

    private static final int TRY_SUMMON_ICICLE_MAX_TIMES = 20;
    private static final int ICICLE_MAX_NUM = 15;
    private static final int TRY_SUMMON_ICICLE_DETECTION_RADIUS = 3;

    private int snowStock = 0;
    private boolean buildWall = true;
    private int counter = 0;

    private boolean isBuildingIcicle = false;
    private int iciclesNum = 0;

    private final BlockPos[] passingPosArr = new BlockPos[POS_NUM];
    private final Icicle[] icicles = new Icicle[ICICLE_MAX_NUM];
    private BlockPos impactPoint;

    private class Icicle {
        private Vec3 icicleVec;
        private final double icicleStepSize;
        private final double icicleStepRadius;
        private final ArrayList<IciclePoint> path = new ArrayList<>();

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
            path.add(new IciclePoint(impactPoint.getCenter().add(icicleVec)));
        }

        public void generate(Level level) {
            placeLooseSnowBlock(level, new BlockPos(BSFMthUtil.vec3ToI(impactPoint.getCenter().add(icicleVec))));
            icicleVec = icicleVec.add(icicleVec.normalize().scale(icicleStepSize));
            path.add(new IciclePoint(impactPoint.getCenter().add(icicleVec)));
            for (IciclePoint iciclePoint : path) {
                iciclePoint.pointGenerate(level);
            }
        }
    }

    public ReconstructSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ReconstructSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment, int snowStock, boolean buildWall) {
        super(EntityRegister.RECONSTRUCT_SNOWBALL.get(), pShooter, pLevel, launchAdjustment);
        this.snowStock = snowStock;
        this.buildWall = buildWall;
    }

    private void icicleInit(Level level) {
        stopTheSnowball();
        this.setNoGravity(true);
        //Determine the direction of the icicle
        //init icicle
        for (int i = 0; i < TRY_SUMMON_ICICLE_MAX_TIMES; i++) {
            double theta = BSFMthUtil.randDouble(0, 2 * Mth.PI);
            double phi = Math.acos(BSFMthUtil.randDouble(-1, 1));
            System.out.println(theta + " " + phi);
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

    private void handleBuildIcicle(Level level) {
        if (snowStock <= 0) return;
        for (int i = 0; i < iciclesNum; i++) {
            icicles[i].generate(level);
        }
    }

    @Override
    public void tick() {
        super.tick();
//        if (stockSnow<=0){
//            this.discard();
//            return;
//        }
        Level level = level();
        if (isBuildingIcicle) {
            stopTheSnowball();
            if (!level.isClientSide) {
                handleBuildIcicle(level);
            }
        } else {
            if (!level.isClientSide && buildWall) {
                if (counter % GROWTH_CONSTRAINT == 0) {
                    handleSetBlock(level);
                }
                posArrMove(new BlockPos(Mth.floor(this.getX()), Mth.floor(this.getY()), Mth.floor(this.getZ())));
                counter++;
            }
//            this.push(0,0.01,0);
        }
    }

    /**
     * Handle wall building tasks
     */
    private void handleSetBlock(Level level) {
        if (snowStock <= 0) return;
        for (int i = GENERATING_DISTANCE; i < POS_NUM; i++) {
            if (passingPosArr[i] != null) {
                placeLooseSnowBlock(level, passingPosArr[i].offset(0, (i - GENERATING_DISTANCE) / GROWTH_CONSTRAINT, 0));
                placeLooseSnowBlock(level, passingPosArr[i].offset(0, -(i - GENERATING_DISTANCE) / GROWTH_CONSTRAINT, 0));
                //Fill a gap in the wall
                if (i + 1 < POS_NUM && passingPosArr[i + 1] != null) {
                    int dx = passingPosArr[i].getX() - passingPosArr[i + 1].getX();
                    int dz = passingPosArr[i].getZ() - passingPosArr[i + 1].getZ();
                    int adx = Mth.abs(dx);
                    int adz = Mth.abs(dz);
                    if (adx > 1 || adz > 1) {
                        int x1 = passingPosArr[i].getX();
                        int x2 = passingPosArr[i + 1].getX();
                        int z1 = passingPosArr[i].getZ();
                        int z2 = passingPosArr[i + 1].getZ();
                        int h = 0;
                        if (adx > adz) {
                            float k = (float) ((z2 - z1) / (x2 - x1));
                            float b = z1 - k * x1;
                            if (dx < 0) {
                                for (int j = 1; j < adx; j++) {
                                    if (k > 0 && k * (x1 + j) + b > z1 + h) {
                                        h++;
                                    } else if (k <= 0 && k * (x1 + j) + b < z1 + h) {
                                        h--;
                                    }
                                    placeLooseSnowBlock(level, passingPosArr[i].offset(j, (i - GENERATING_DISTANCE) / GROWTH_CONSTRAINT, h));
                                    placeLooseSnowBlock(level, passingPosArr[i].offset(j, -(i - GENERATING_DISTANCE) / GROWTH_CONSTRAINT, h));
                                }
                            } else {
                                for (int j = 1; j < adx; j++) {
                                    if (k > 0 && k * (x2 + j) + b > z2 + h) {
                                        h++;
                                    } else if (k <= 0 && k * (x2 + j) + b < z2 + h) {
                                        h--;
                                    }
                                    placeLooseSnowBlock(level, passingPosArr[i + 1].offset(j, (i - GENERATING_DISTANCE) / GROWTH_CONSTRAINT, h));
                                    placeLooseSnowBlock(level, passingPosArr[i + 1].offset(j, -(i - GENERATING_DISTANCE) / GROWTH_CONSTRAINT, h));
                                }
                            }
                        } else {
                            float k = (float) ((x2 - x1) / (z2 - z1));
                            float b = x1 - k * z1;
                            if (dz < 0) {
                                for (int j = 1; j < adz; j++) {
                                    if (k > 0 && k * (z1 + j) + b > x1 + h) {
                                        h++;
                                    } else if (h <= 0 && k * (z1 + j) + b < x1 + h) {
                                        h--;
                                    }
                                    placeLooseSnowBlock(level, passingPosArr[i].offset(h, (i - GENERATING_DISTANCE) / GROWTH_CONSTRAINT, j));
                                    placeLooseSnowBlock(level, passingPosArr[i].offset(h, -(i - GENERATING_DISTANCE) / GROWTH_CONSTRAINT, j));
                                }
                            } else {
                                for (int j = 1; j < adz; j++) {
                                    if (k > 0 && k * (z2 + j) + b > x2 + h) {
                                        h++;
                                    } else if (k <= 0 && k * (z2 + j) + b < x2 + h) {
                                        h--;
                                    }
                                    placeLooseSnowBlock(level, passingPosArr[i + 1].offset(h, (i - GENERATING_DISTANCE) / GROWTH_CONSTRAINT, j));
                                    placeLooseSnowBlock(level, passingPosArr[i + 1].offset(h, -(i - GENERATING_DISTANCE) / GROWTH_CONSTRAINT, j));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void stopTheSnowball() {
        Vec3 vec3 = this.getDeltaMovement();
        this.push(-vec3.x, -vec3.y, -vec3.z);
    }

    private void placeLooseSnowBlock(Level level, BlockPos blockPos) {
        if (snowStock > 0) {
            if (level.getBlockState(blockPos).canBeReplaced()) {
                level.setBlock(blockPos, BlockRegister.LOOSE_SNOW_BLOCK.get().defaultBlockState(), 3);
                level.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.SNOW_PLACE, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                snowStock--;
            } else if (level.getBlockEntity(blockPos) instanceof LooseSnowBlockEntity blockEntity) {
                blockEntity.setAge(0);
                blockEntity.setChanged();
            }
        } else {
            this.discard();
        }
    }

    private void posArrMove(BlockPos newPos) {
//        System.arraycopy(passingPosArr, 0, passingPosArr, 1, POS_NUM - 1);
        for (int i = POS_NUM - 1; i > 0; i--) {
            passingPosArr[i] = passingPosArr[i - 1];
        }
        passingPosArr[0] = newPos;
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
        return ItemRegister.RECONSTRUCT_SNOWBALL.get();
    }
}
