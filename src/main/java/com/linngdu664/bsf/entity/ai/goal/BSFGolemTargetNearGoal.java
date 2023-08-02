package com.linngdu664.bsf.entity.ai.goal;

import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.linngdu664.bsf.registry.ItemRegister;
import com.linngdu664.bsf.util.BSFMthUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class BSFGolemTargetNearGoal extends Goal {
    private final BSFSnowGolemEntity golem;
    private Vec3 vec3;
    private Item core;

    public BSFGolemTargetNearGoal(BSFSnowGolemEntity golem) {
        this.golem = golem;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        Entity target = golem.getTarget();
        if (target != null && golem.distanceToSqr(target) < 16 && (golem.getStatus() == 2 || golem.getStatus() == 3) && golem.getCoreCoolDown() == 0) {
            core = golem.getCore().getItem();
            if (core.equals(ItemRegister.THRUST_GOLEM_CORE.get())) {
                double dx = -target.getX() + golem.getX();
                double dz = -target.getZ() + golem.getZ();
                float initTheta = (float) Mth.atan2(dz, dx);
                float bias = (float) BSFMthUtil.randNormalDouble(0, 0.52);
                int maxJ = 12;
                int maxI = 0;
                for (int i = 0; i < 6; i++) {
                    float theta = initTheta + bias + Mth.PI / 6 * i;
                    for (int j = 0; j < maxJ; j++) {
                        float phi = (1 + j / 12F) * Mth.PI / 4;
                        if (isNotBlocked(theta, phi)) {
                            maxJ = j;
                            maxI = i;
                        }
                    }
                }
                if (maxJ != 12) {
                    float theta = initTheta + bias + Mth.PI / 6 * maxI;
                    float phi = (1 + maxJ / 12F) * Mth.PI / 4;
                    vec3 = new Vec3(Mth.cos(theta) * Mth.cos(phi), Mth.sin(phi), Mth.sin(theta) * Mth.cos(phi)).scale(1.75);
                    return true;
                }
            } else if (core.equals(ItemRegister.NEAR_TELEPORTATION_GOLEM_CORE.get())) {
                vec3 = golem.getRandomTeleportPos();
                return vec3 != null;
            }
        }
        return false;
    }

    @Override
    public void start() {
        if (core.equals(ItemRegister.THRUST_GOLEM_CORE.get())) {
            golem.setDeltaMovement(vec3);
            golem.resetCoreCoolDown();
        } else if (core.equals(ItemRegister.NEAR_TELEPORTATION_GOLEM_CORE.get())) {
            golem.tpWithParticlesAndResetCD(vec3);
        }
    }

    private boolean isNotBlocked(float theta, float phi) {
        float x = Mth.cos(theta);
        float z = Mth.sin(theta);
        Vec3 vec3 = new Vec3(x * Mth.cos(phi), Mth.sin(phi), z * Mth.cos(phi)).scale(0.25);
        Vec3 golemPos1 = golem.getPosition(0);
        Level level = golem.level();
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 3; j++) {
                BlockPos blockPos1 = new BlockPos(BSFMthUtil.vec3ToI(golemPos1)).above(j);
                if (!level.getBlockState(blockPos1).getCollisionShape(level, blockPos1).isEmpty()) {
                    return false;
                }
            }
            golemPos1 = golemPos1.add(vec3);
        }
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }
}
