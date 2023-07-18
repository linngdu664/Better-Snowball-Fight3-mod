package com.linngdu664.bsf.entity.ai.goal;

import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.linngdu664.bsf.item.weapon.SnowballShotgunItem;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class BSFGolemRangedAttackGoal extends Goal {
    private final BSFSnowGolemEntity golem;
    private final double speedModifier;
    private final int attackInterval;
    private final float attackRadius;
    private final float attackRadiusSqr;
    private int attackTime;
    private int seeTime;
    private int strafingTime;
    private boolean strafingClockwise;
    private boolean strafingBackwards;

    public BSFGolemRangedAttackGoal(BSFSnowGolemEntity golem, double pSpeedModifier, int pAttackInterval, float pAttackRadius) {
        this.golem = golem;
        this.speedModifier = pSpeedModifier;
        this.attackInterval = pAttackInterval;
        this.attackRadius = pAttackRadius;
        this.attackRadiusSqr = pAttackRadius * pAttackRadius;
        golem.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity livingEntity = golem.getTarget();
        return livingEntity != null && livingEntity.isAlive() && golem.getStatus() != 1;
    }

    @Override
    public boolean canContinueToUse() {
        return canUse() || !golem.getNavigation().isDone();
    }

    @Override
    public void stop() {
        seeTime = 0;
        attackTime = 0;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    private boolean isBlockedByOthers(double d2, LivingEntity target) {
        int d = (int) Math.sqrt(d2);
        Vec3 sightVec = new Vec3(target.getX() - golem.getX(), target.getEyeY() - golem.getEyeY(), target.getZ() - golem.getZ()).normalize();
        double x = golem.getX();
        double y = golem.getEyeY();
        double z = golem.getZ();
        for (int i = 0; i <= d; i++) {
            AABB aabb = new AABB(Mth.floor(x), Mth.floor(y), Mth.floor(z), Mth.ceil(x), Mth.ceil(y), Mth.ceil(z));
            List<LivingEntity> list = golem.level().getEntitiesOfClass(LivingEntity.class, aabb);
            list.remove(target);
            list.remove(golem);
            if (!list.isEmpty()) {
                if (!golem.isUseLocator()) {
                    golem.setTarget(null);
                }
                attackTime = 1;
                return true;
            }
            x += sightVec.x;
            y += sightVec.y;
            z += sightVec.z;
        }
        return false;
    }

    public void tick() {
        LivingEntity target = golem.getTarget();
        float attackRadiusSqr = this.attackRadiusSqr;
        float attackRadius = this.attackRadius;
        if (golem.getWeapon().getItem() instanceof SnowballShotgunItem) {
            attackRadius *= 0.2;
            attackRadiusSqr *= 0.04;
        }
        if (target != null) {
            double d0 = golem.distanceToSqr(target.getX(), target.getY(), target.getZ());
            boolean flag = golem.getSensing().hasLineOfSight(target);
            boolean flag1 = seeTime > 0;
            if (flag != flag1) {
                seeTime = 0;
            }
            if (flag) {
                ++seeTime;
            } else {
                --seeTime;
            }
            if (d0 <= attackRadiusSqr && seeTime >= 20 || golem.getStatus() == 4) {
                golem.getNavigation().stop();
                ++strafingTime;
            } else {
                golem.getNavigation().moveTo(target, speedModifier);
                strafingTime = -1;
            }
            if (strafingTime >= 20) {
                if (golem.getRandom().nextFloat() < 0.3F) {
                    strafingClockwise = !strafingClockwise;
                }
                if (golem.getRandom().nextFloat() < 0.3F) {
                    strafingBackwards = !strafingBackwards;
                }
                strafingTime = 0;
            }
            if (strafingTime > -1 && golem.getStatus() != 4) {
                if (d0 > attackRadiusSqr * 0.64F) {
                    strafingBackwards = false;
                } else if (d0 < attackRadiusSqr * 0.09F) {
                    strafingBackwards = true;
                }
                golem.getMoveControl().strafe(strafingBackwards ? -0.5F : 0.5F, strafingClockwise ? 0.5F : -0.5F);
                golem.lookAt(target, 30.0F, 30.0F);
            } else {
                golem.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }
            if (--attackTime <= 0) {
                if (attackTime == 0) {
                    if (!flag || isBlockedByOthers(d0, target)) {
                        return;
                    }
                    float f = (float) Math.sqrt(d0) / attackRadius;
                    float f1 = Mth.clamp(f, 0.1F, 1.0F);
                    golem.performRangedAttack(target, f1);
                }
                attackTime = attackInterval;
            }
        }
    }
}
