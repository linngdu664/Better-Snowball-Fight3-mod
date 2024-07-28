package com.linngdu664.bsf.util;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class SphereAxisRotationHelper {
    private final Vec3 circle1;
    private final Vec3 circle2;
    float angle = 0;


    public SphereAxisRotationHelper(Vec3 offset, double axisYaw, double axisPitch) {
        this(offset,BSFCommonUtil.radRotationToVector(1, axisYaw, axisPitch));
    }
    public SphereAxisRotationHelper(Vec3 offset, Vec3 axis) {
        Vec3 vec33 = axis.scale(offset.length() * BSFCommonUtil.vec3AngleCos(offset, axis));
        circle1 = offset.subtract(vec33);
        circle2 = circle1.cross(axis);     // axis为单位向量且垂直于vec31，不需要调整结果长度
    }

    public Vec3 getDeltaMovement(float speed) {
        float angle1 = angle + speed;
        Vec3 diff = circle1.scale(Mth.cos(angle1) - Mth.cos(angle)).add(circle2.scale(Mth.sin(angle1) - Mth.sin(angle)));
        angle = angle1;
        return diff;
    }
}
