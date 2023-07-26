package com.linngdu664.bsf.entity.ai.goal;

import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class BSFGolemJumpHighGoal extends Goal {
    private final BSFSnowGolemEntity golem;

    public BSFGolemJumpHighGoal(BSFSnowGolemEntity golem) {
        this.golem = golem;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return false;
    }

    @Override
    public void start() {

    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }
}
