package com.linngdu664.bsf.entity.ai.goal;

import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.linngdu664.bsf.util.BSFMthUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class BSFGolemJumpHighGoal extends Goal {
    private static final float[][] ANGLE_TBL = {
            {1.484F, 1.396F, 1.307F, 1.216F, 1.121F, 1.020F, 0.912F, 0.791F},
            {1.484F, 1.396F, 1.307F, 1.215F, 1.120F, 1.019F, 0.910F, 0.788F},
            {1.484F, 1.396F, 1.307F, 1.215F, 1.119F, 1.018F, 0.909F, 0.785F},
            {1.484F, 1.396F, 1.306F, 1.214F, 1.118F, 1.017F, 0.907F, 0.782F},
            {1.484F, 1.396F, 1.306F, 1.214F, 1.117F, 1.015F, 0.904F, 0.777F},
            {0.000F, 1.395F, 1.305F, 1.213F, 1.116F, 1.014F, 0.901F, 0.772F},
            {0.000F, 0.000F, 1.305F, 1.212F, 1.115F, 1.012F, 0.898F, 0.765F},
            {0.000F, 0.000F, 0.000F, 1.211F, 1.114F, 1.009F, 0.894F, 0.756F},
            {0.000F, 0.000F, 0.000F, 1.210F, 1.112F, 1.006F, 0.888F, 0.744F},
            {0.000F, 0.000F, 0.000F, 1.209F, 1.110F, 1.003F, 0.881F, 0.724F},
            {0.000F, 0.000F, 1.302F, 1.207F, 1.107F, 0.998F, 0.872F, 0.682F},
            {0.000F, 1.392F, 1.300F, 1.205F, 1.104F, 0.992F, 0.857F, 0.000F},
            {1.482F, 1.391F, 1.299F, 1.202F, 1.099F, 0.983F, 0.827F, 0.000F},
            {1.481F, 1.390F, 1.297F, 1.199F, 1.092F, 0.968F, 0.000F, 0.000F},
            {1.480F, 1.389F, 1.294F, 1.194F, 1.082F, 0.922F, 0.000F, 0.000F},
            {1.479F, 1.386F, 1.290F, 1.185F, 1.058F, 0.000F, 0.000F, 0.000F},
            {1.478F, 1.383F, 1.282F, 1.165F, 0.000F, 0.000F, 0.000F, 0.000F}};

    private final BSFSnowGolemEntity golem;

    public BSFGolemJumpHighGoal(BSFSnowGolemEntity golem) {
        this.golem = golem;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return golem.getTarget() != null && golem.distanceToSqr(golem.getTarget()) < 4 && (golem.getStatus() == 2 || golem.getStatus() == 3);
    }

    @Override
    public void start() {
        Level level = golem.level();
        Vec3 pos1 = golem.getTarget().getPosition(0);
        Vec3 pos2 = golem.getPosition(0);
        Vec3 vec31 = pos2.subtract(pos1);
        float len = Mth.sqrt((float) BSFMthUtil.modSqr(vec31.x, vec31.z));
        float initX = (float) (vec31.x / len);
        float initZ = (float) (vec31.z / len);
        for (int h = 0; h <= 8; h++) {
            Vec3 pushVec = getPushVec(level, h, pos2, initX, initZ);
            if (pushVec != null) {
                System.out.println("ok. vec3 is: " + pushVec);
                golem.push(pushVec.x, pushVec.y, pushVec.z);
                return;
            }
        }
        for (int h = -1; h >= -8; h--) {
            Vec3 pushVec = getPushVec(level, h, pos2, initX, initZ);
            if (pushVec != null) {
                System.out.println("ok. vec3 is: " + pushVec);
                golem.push(pushVec.x, pushVec.y, pushVec.z);
                return;
            }
        }
        System.out.println("failed.");
    }

    private Vec3 getPushVec(Level level, int h, Vec3 golemPos, float initX, float initZ) {
        for (int r = 8; r >= 1; r--) {
            float theta = ANGLE_TBL[h + 8][r - 1];
            System.out.printf("h=%d, r=%d\n", h, r);
            if (theta != 0) {
                System.out.printf("theta=%f\n", theta);
                for (int j = 0; j <= r / 2; j++) {
                    float ang = (float) j / r;
                    float p1 = initX * Mth.cos(ang);
                    float p2 = initZ * Mth.sin(ang);
                    float p3 = -initX * Mth.sin(ang);
                    float p4 = initZ * Mth.cos(ang);
                    float x1 = p1 + p2;
                    float z1 = p3 + p4;
                    BlockPos blockPos = new BlockPos(BSFMthUtil.vec3ToI(golemPos.add(r * x1, h, r * z1)));
                    System.out.println("try blockPos: " + blockPos);
                    System.out.println("blocks motion: " + level.getBlockState(blockPos.below()).blocksMotion());
                    if (level.getBlockState(blockPos.below()).blocksMotion() && isNotBlocked(level, r, theta, x1, z1, golemPos)) {
                        return new Vec3(x1 * Mth.cos(theta), Mth.sin(theta), z1 * Mth.cos(theta)).scale(2);
                    }
                    x1 = p1 - p2;
                    z1 = -p3 + p4;
                    blockPos = new BlockPos(BSFMthUtil.vec3ToI(golemPos.add(r * x1, h, r * z1)));
                    System.out.println("try blockPos: " + blockPos);
                    System.out.println("blocks motion: " + level.getBlockState(blockPos.below()).blocksMotion());
                    if (level.getBlockState(blockPos.below()).blocksMotion() && isNotBlocked(level, r, theta, x1, z1, golemPos)) {
                        return new Vec3(x1 * Mth.cos(theta), Mth.sin(theta), z1 * Mth.cos(theta)).scale(2);
                    }
                }
            }
        }
        System.out.println("invalid h or r.");
        return null;
    }

    private boolean isNotBlocked(Level level, int r, float theta, float initX, float initZ, Vec3 golemPos1) {
        float maxT = (float) -Math.log(1 - 3.438 * r / (40F * Mth.cos(theta))) / 3.438F;
        Vec3 golemPos0 = golemPos1.add(initZ * 0.5, 0, -initX * 0.5);
        Vec3 golemPos2 = golemPos1.add(-initZ * 0.5, 0, initX * 0.5);
        for (int i = 1; i <= 50; i++) {
            float t = i / maxT;
            float d = 0.2909F * 40F * Mth.cos(theta) * (1 - (float) Math.exp(-3.438F * t));
            float h = 1.263F * 40F * Mth.sin(theta) * (1 - (float) Math.exp(-0.792 * t)) - 78.4F * t;
            BlockPos blockPos0 = new BlockPos(BSFMthUtil.vec3ToI(golemPos0.add(d * initX, h, d * initZ)));
            BlockPos blockPos1 = new BlockPos(BSFMthUtil.vec3ToI(golemPos1.add(d * initX, h, d * initZ)));
            BlockPos blockPos2 = new BlockPos(BSFMthUtil.vec3ToI(golemPos2.add(d * initX, h, d * initZ)));
            BlockPos blockPos3 = blockPos0.above();
            BlockPos blockPos4 = blockPos1.above();
            BlockPos blockPos5 = blockPos2.above();
            BlockPos blockPos6 = blockPos3.above();
            BlockPos blockPos7 = blockPos4.above();
            BlockPos blockPos8 = blockPos5.above();
            if (!level.getBlockState(blockPos0).isAir() || !level.getBlockState(blockPos1).isAir() || !level.getBlockState(blockPos2).isAir() ||
                    !level.getBlockState(blockPos3).isAir() || !level.getBlockState(blockPos4).isAir() || !level.getBlockState(blockPos5).isAir() ||
                    !level.getBlockState(blockPos6).isAir() || !level.getBlockState(blockPos7).isAir() || !level.getBlockState(blockPos8).isAir()) {
                System.out.println("ok. not blocked.");
                return false;
            }
        }
        System.out.println("blocked.");
        return true;
    }

    @Override
    public void tick() {
        System.out.println(golem.getDeltaMovement());
    }

    @Override
    public boolean canContinueToUse() {
        return !golem.onGround();
    }
}
