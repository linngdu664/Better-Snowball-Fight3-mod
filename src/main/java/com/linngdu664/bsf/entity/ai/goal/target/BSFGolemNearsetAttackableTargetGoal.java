package com.linngdu664.bsf.entity.ai.goal.target;

import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.linngdu664.bsf.util.BSFTeamSavedData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class BSFGolemNearsetAttackableTargetGoal extends TargetGoal {
    private static final int DEFAULT_RANDOM_INTERVAL = 10;
    private static final int SEARCH_DISTANCE = 50;
    protected final int randomInterval;
    @Nullable
    protected LivingEntity target;
    private BSFSnowGolemEntity snowGolem;


    public BSFGolemNearsetAttackableTargetGoal(BSFSnowGolemEntity snowGolem) {
        super(snowGolem, true, false);
        this.randomInterval = reducedTickDelay(DEFAULT_RANDOM_INTERVAL);
        this.snowGolem = snowGolem;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    public boolean canUse() {
        if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
            return false;
        } else {
            this.findTarget();
            return target != null;
        }
    }

    protected AABB getTargetSearchArea() {
        return this.mob.getBoundingBox().inflate(SEARCH_DISTANCE, SEARCH_DISTANCE, SEARCH_DISTANCE);
    }

    protected void findTarget() {
        if (snowGolem.getLocator() == 1) {
            target = null;
            return;
        }
        TargetingConditions targetConditions = TargetingConditions.forCombat().range(SEARCH_DISTANCE);
        if (snowGolem.getLocator() == 0) {
            targetConditions.selector(p -> p instanceof Enemy);
            target = snowGolem.level().getNearestEntity(snowGolem.level().getEntitiesOfClass(LivingEntity.class, getTargetSearchArea(), p_148152_ -> true), targetConditions, snowGolem, snowGolem.getX(), snowGolem.getEyeY(), snowGolem.getZ());
        } else if (snowGolem.getLocator() == 2) {
            BSFTeamSavedData savedData = snowGolem.getServer().overworld().getDataStorage().computeIfAbsent(BSFTeamSavedData::new, BSFTeamSavedData::new, "bsf_team");
            targetConditions.selector(p -> !savedData.isSameTeam(snowGolem.getOwner(), p));
            target = snowGolem.level().getNearestPlayer(targetConditions, snowGolem, snowGolem.getX(), snowGolem.getEyeY(), snowGolem.getZ());
        } else {
            if (snowGolem.getOwner() != null) {
                targetConditions.selector(p -> !(p instanceof Player) && snowGolem.wantsToAttack(p, snowGolem.getOwner()) || p instanceof Player player && !player.isCreative() && !player.isSpectator() && !player.equals(snowGolem.getOwner()));
                target = snowGolem.level().getNearestEntity(snowGolem.level().getEntitiesOfClass(LivingEntity.class, getTargetSearchArea(), p_148152_ -> true), targetConditions, snowGolem, snowGolem.getX(), snowGolem.getEyeY(), snowGolem.getZ());
            } else {
                targetConditions.selector(p -> p instanceof Player player && !player.isCreative() && !player.isSpectator());
                target = snowGolem.level().getNearestPlayer(targetConditions, snowGolem, snowGolem.getX(), snowGolem.getEyeY(), snowGolem.getZ());
            }
        }
    }

    public void start() {
        this.mob.setTarget(this.target);
        super.start();
    }

    public void setTarget(@Nullable LivingEntity pTarget) {
        this.target = pTarget;
    }
}
