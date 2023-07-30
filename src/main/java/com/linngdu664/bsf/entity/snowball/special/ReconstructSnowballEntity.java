package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.registry.EntityRegister;
import com.linngdu664.bsf.registry.ItemRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class ReconstructSnowballEntity extends AbstractSnowStorageSnowballEntity {
    private static final int GENERATING_DISTANCE = 1;
    private static final int POS_NUM = 10 + GENERATING_DISTANCE;
    private static final int GROWTH_CONSTRAINT = 3;
    private final BlockPos[] passingPosArr = new BlockPos[POS_NUM];
    private int counter = 0;

    public ReconstructSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        setNoGravity(true);
    }

    public ReconstructSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment, int snowStock) {
        super(EntityRegister.RECONSTRUCT_SNOWBALL.get(), pShooter, pLevel, launchAdjustment, snowStock);
        setNoGravity(true);
        this.destroyStepSize = Math.max(snowStock / 40, 1);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("counter", counter);
        List<Integer> tmpList = new ArrayList<>();
        for (int i = 0; i < POS_NUM && passingPosArr[i] != null; i++) {
            tmpList.add(passingPosArr[i].getX());
            tmpList.add(passingPosArr[i].getY());
            tmpList.add(passingPosArr[i].getZ());
        }
        pCompound.putIntArray("passingPosArr", tmpList);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        counter = pCompound.getInt("counter");
        int[] tmpArr = pCompound.getIntArray("passingPosArr");
        if (tmpArr.length % 3 == 0) {
            for (int i = 0; i < tmpArr.length; i += 3) {
                allBlock.push(new BlockPos(tmpArr[i], tmpArr[i + 1], tmpArr[i + 2]));
            }
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        Level level = level();
        if (!level.isClientSide && !(posIsLooseSnow(level, result.getBlockPos()))) {
            if (!inBlockDuration) {
                startTimingOfDiscard(new Vec3(this.getX(), this.getY(), this.getZ()));
            }
        }
        super.onHitBlock(result);
    }

    @Override
    public void tick() {
        Level level = level();
        if (snowStock <= 0) {
            if (!inBlockDuration) {
                startTimingOfDiscard(new Vec3(this.getX(), this.getY(), this.getZ()));
            }
        }
        if (counter % GROWTH_CONSTRAINT == 0) {
            handleSetBlock(level);
        }
        if (!level.isClientSide) {
            posArrMove(new BlockPos(Mth.floor(this.getX()), Mth.floor(this.getY()), Mth.floor(this.getZ())));
        }
        counter++;

        super.tick();
    }

    /**
     * Handle wall building tasks
     */
    private void handleSetBlock(Level level) {
        if (snowStock <= 0) return;
        for (int i = GENERATING_DISTANCE; i < POS_NUM; i++) {
            if (passingPosArr[i] != null) {
                tryPlaceLooseSnowBlock(level, passingPosArr[i].offset(0, (i - GENERATING_DISTANCE) / GROWTH_CONSTRAINT, 0));
                tryPlaceLooseSnowBlock(level, passingPosArr[i].offset(0, -(i - GENERATING_DISTANCE) / GROWTH_CONSTRAINT, 0));
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
                                    tryPlaceLooseSnowBlock(level, passingPosArr[i].offset(j, (i - GENERATING_DISTANCE) / GROWTH_CONSTRAINT, h));
                                    tryPlaceLooseSnowBlock(level, passingPosArr[i].offset(j, -(i - GENERATING_DISTANCE) / GROWTH_CONSTRAINT, h));
                                }
                            } else {
                                for (int j = 1; j < adx; j++) {
                                    if (k > 0 && k * (x2 + j) + b > z2 + h) {
                                        h++;
                                    } else if (k <= 0 && k * (x2 + j) + b < z2 + h) {
                                        h--;
                                    }
                                    tryPlaceLooseSnowBlock(level, passingPosArr[i + 1].offset(j, (i - GENERATING_DISTANCE) / GROWTH_CONSTRAINT, h));
                                    tryPlaceLooseSnowBlock(level, passingPosArr[i + 1].offset(j, -(i - GENERATING_DISTANCE) / GROWTH_CONSTRAINT, h));
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
                                    tryPlaceLooseSnowBlock(level, passingPosArr[i].offset(h, (i - GENERATING_DISTANCE) / GROWTH_CONSTRAINT, j));
                                    tryPlaceLooseSnowBlock(level, passingPosArr[i].offset(h, -(i - GENERATING_DISTANCE) / GROWTH_CONSTRAINT, j));
                                }
                            } else {
                                for (int j = 1; j < adz; j++) {
                                    if (k > 0 && k * (z2 + j) + b > x2 + h) {
                                        h++;
                                    } else if (k <= 0 && k * (z2 + j) + b < x2 + h) {
                                        h--;
                                    }
                                    tryPlaceLooseSnowBlock(level, passingPosArr[i + 1].offset(h, (i - GENERATING_DISTANCE) / GROWTH_CONSTRAINT, j));
                                    tryPlaceLooseSnowBlock(level, passingPosArr[i + 1].offset(h, -(i - GENERATING_DISTANCE) / GROWTH_CONSTRAINT, j));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void posArrMove(BlockPos newPos) {
        for (int i = POS_NUM - 1; i > 0; i--) {
            passingPosArr[i] = passingPosArr[i - 1];
        }
        passingPosArr[0] = newPos;
    }

    protected void tryPlaceLooseSnowBlock(Level level, BlockPos blockPos) {
        if (snowStock > 0) {
            if (!level.isClientSide) {
                if (posIsLooseSnow(level, blockPos) || level.getBlockState(blockPos).canBeReplaced()) {
                    placeAndRecordBlock(level, blockPos);
                    level.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.SNOW_PLACE, SoundSource.NEUTRAL, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);

                }

            }
            snowStock--;
        } else {
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
        return ItemRegister.RECONSTRUCT_SNOWBALL.get();
    }
}
