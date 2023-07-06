package com.linngdu664.bsf.entity.ai.goal;

import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

public class BSFGolemRandomStrollGoal extends RandomStrollGoal {
    private final float probability;
    private final BSFSnowGolemEntity golem;

    public BSFGolemRandomStrollGoal(BSFSnowGolemEntity golem, double pSpeedModifier, float pProbability) {
        super(golem, pSpeedModifier);
        this.probability = pProbability;
        this.golem = golem;
    }

    @Override
    public boolean canUse() {
        if (golem.getStatus() == 3) {
            return super.canUse();
        }
        return false;
    }

    protected Vec3 getPosition() {
        if (mob.isInWaterOrBubble()) {
            Vec3 vec3 = LandRandomPos.getPos(mob, 15, 7);
            return vec3 == null ? super.getPosition() : vec3;
        } else {
            return mob.getRandom().nextFloat() >= probability ? LandRandomPos.getPos(mob, 10, 7) : super.getPosition();
        }
    }
}
