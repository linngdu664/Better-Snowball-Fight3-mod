package com.linngdu664.bsf.util;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;

/**
 * Polar coordinate system.
 * The positive x-axis is the polar axis.
 */
public class PolarPos2d {
    private final Vec2 pole;
    private final float radius;
    private float angle;
    private Vec2 p;

    public PolarPos2d(Vec2 pole, float radius, float angle) {
        this.pole = pole;
        this.radius = radius;
        this.angle = angle;
        this.p = new Vec2(radius * Mth.cos(angle),radius * Mth.sin(angle));
    }

    public PolarPos2d(Vec2 pole, Vec2 p) {
        this.pole = pole;
        this.radius = Mth.sqrt(pole.distanceToSqr(p));
        this.angle = (float) Mth.atan2(p.y-pole.y, p.x-pole.x);
        this.p = p.add(pole.negated());
    }

    public Vec2 rotate(float angle) {
        Vec2 p0 = new Vec2(-p.x, -p.y);
        this.angle += angle;
        this.p = new Vec2(radius * Mth.cos(this.angle),radius * Mth.sin(this.angle));
        return p.add(p0);
    }

    public Vec2 rotateLength(float length) {
        return rotate(length / radius);
    }

    public Vec2d getPReal() {
        return new Vec2d(p.x + pole.x,p.y + pole.y);
    }

    public Vec2 getP() {
        return p;
    }

    public Vec2 getPole() {
        return pole;
    }

    public float getAngle() {
        return angle;
    }

    public float getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "PolarPos2d{" +
                "pole=" + pole +
                ", radius=" + radius +
                ", angle=" + angle +
                ", angleD=" + angle* Mth.RAD_TO_DEG +
                '}';
    }
}
