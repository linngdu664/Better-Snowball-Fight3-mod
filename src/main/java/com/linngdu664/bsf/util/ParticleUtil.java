package com.linngdu664.bsf.util;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ParticleUtil {
    /**
     * This method uses the parametric equation of circles, in which the distance from plane to origin is 8m and the
     * normal vector is sightVec, to spray cone-shaped particles forward, and the maximum radius needs to be specified.
     * It can only work in the client side. Please use "ForwardConeParticlesSender" to send particles in the server side.
     *
     * @param pLevel          Entity's Level.
     * @param entity          Entity.
     * @param sightVec        The entity's sight vector.
     * @param particleOptions The type of the particle.
     * @param r               The max radius.
     * @param aStep           Angle step length in degree.
     * @param rStep           Radius step length.
     * @param loweredVision   The negative y offset of the spawning point.
     */
    public static void spawnForwardConeParticles(Level pLevel, Entity entity, Vec3 sightVec, ParticleOptions particleOptions, float r, float aStep, float rStep, double loweredVision) {
        //particleOptions=ParticleTypes.SNOWFLAKE r=4.5 deg=30 d=0.5f
        spawnForwardConeParticles(pLevel, entity.getX(), entity.getEyeY(), entity.getZ(), sightVec, particleOptions, r, aStep, rStep, loweredVision);
    }

    public static void spawnForwardConeParticles(Level pLevel, double eX, double eY, double eZ, Vec3 sightVec, ParticleOptions particleOptions, float r, float aStep, float rStep, double loweredVision) {
        //particleOptions=ParticleTypes.SNOWFLAKE r=4.5 deg=30 d=0.5f
        Vec3 vecA = sightVec.cross(new Vec3(0, 1, 0)).normalize();
        if (vecA == Vec3.ZERO) {
            vecA = sightVec.cross(new Vec3(1, 0, 0)).normalize();
        }
        Vec3 vecB = sightVec.cross(vecA).normalize();
        for (float ri = 0.5F; ri <= r; ri += rStep) {
            float rand = pLevel.getRandom().nextFloat() * Mth.DEG_TO_RAD * aStep;
            for (float theta = rand; theta < Mth.TWO_PI + rand; theta += Mth.DEG_TO_RAD * aStep) {
                double x = 8.0F * sightVec.x + ri * (Mth.cos(theta) * vecA.x + Mth.sin(theta) * vecB.x);
                double y = 8.0F * sightVec.y + ri * (Mth.cos(theta) * vecA.y + Mth.sin(theta) * vecB.y);
                double z = 8.0F * sightVec.z + ri * (Mth.cos(theta) * vecA.z + Mth.sin(theta) * vecB.z);
                double inverseL = Mth.invSqrt(BSFMthUtil.modSqr(x, y, z));
                double rand1 = Math.sqrt(pLevel.getRandom().nextDouble() * 0.9 + 0.1);
                pLevel.addParticle(particleOptions, eX, eY - loweredVision, eZ, x * inverseL * rand1, y * inverseL * rand1, z * inverseL * rand1);
            }
        }
    }
}
