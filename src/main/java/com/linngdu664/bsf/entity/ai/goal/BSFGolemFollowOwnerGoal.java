package com.linngdu664.bsf.entity.ai.goal;

import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

import java.util.EnumSet;

public class BSFGolemFollowOwnerGoal extends Goal {
    private final BSFSnowGolemEntity golem;
    private final LevelReader level;
    private final double speedModifier;
    private final PathNavigation navigation;
    private final float stopDistance;
    private final float startDistance;
    private LivingEntity owner;
    private int timeToRecalcPath;

    public BSFGolemFollowOwnerGoal(BSFSnowGolemEntity golem, double pSpeedModifier, float pStartDistance, float pStopDistance) {
        this.golem = golem;
        this.level = golem.level();
        this.speedModifier = pSpeedModifier;
        this.navigation = golem.getNavigation();
        this.startDistance = pStartDistance;
        this.stopDistance = pStopDistance;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity livingentity = golem.getOwner();
        if (livingentity == null || livingentity.isSpectator() || golem.isOrderedToSit() ||
                golem.distanceToSqr(livingentity) < (double) (startDistance * startDistance) ||
                golem.getStatus() == 3 || golem.getStatus() == 4) {
            return false;
        } else {
            owner = livingentity;
            return true;
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (navigation.isDone() || golem.isOrderedToSit()) {
            return false;
        } else {
            return !(golem.distanceToSqr(owner) <= (double) (stopDistance * stopDistance));
        }
    }

    @Override
    public void start() {
        timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        owner = null;
        navigation.stop();
    }

    @Override
    public void tick() {
        golem.getLookControl().setLookAt(owner, 10.0F, (float) golem.getMaxHeadXRot());
        if (--timeToRecalcPath <= 0) {
            timeToRecalcPath = adjustedTickDelay(10);
            if (!golem.isLeashed() && !golem.isPassenger()) {
                if (golem.distanceToSqr(owner) >= 1024.0D) {
                    teleportToOwner();
                } else {
                    navigation.moveTo(owner, speedModifier);
                }
            }
        }
    }

    private void teleportToOwner() {
        BlockPos blockpos = owner.blockPosition();
        for (int i = 0; i < 10; ++i) {
            int j = randomIntInclusive(-3, 3);
            int k = randomIntInclusive(-1, 1);
            int l = randomIntInclusive(-3, 3);
            boolean flag = maybeTeleportTo(blockpos.getX() + j, blockpos.getY() + k, blockpos.getZ() + l);
            if (flag) {
                return;
            }
        }
    }

    private boolean maybeTeleportTo(int pX, int pY, int pZ) {
        if (Math.abs((double) pX - owner.getX()) < 2.0D && Math.abs((double) pZ - owner.getZ()) < 2.0D) {
            return false;
        } else if (!canTeleportTo(new BlockPos(pX, pY, pZ))) {
            return false;
        } else {
            golem.moveTo(pX + 0.5D, pY, pZ + 0.5D, golem.getYRot(), golem.getXRot());
            navigation.stop();
            return true;
        }
    }

    private boolean canTeleportTo(BlockPos pPos) {
        BlockPathTypes blockpathtypes = WalkNodeEvaluator.getBlockPathTypeStatic(level, pPos.mutable());
        if (blockpathtypes != BlockPathTypes.WALKABLE) {
            return false;
        } else {
            BlockState blockstate = level.getBlockState(pPos.below());
            if (blockstate.getBlock() instanceof LeavesBlock) {
                return false;
            } else {
                BlockPos blockpos = pPos.subtract(golem.blockPosition());
                return level.noCollision(golem, golem.getBoundingBox().move(blockpos));
            }
        }
    }

    private int randomIntInclusive(int pMin, int pMax) {
        return golem.getRandom().nextInt(pMax - pMin + 1) + pMin;
    }
}
