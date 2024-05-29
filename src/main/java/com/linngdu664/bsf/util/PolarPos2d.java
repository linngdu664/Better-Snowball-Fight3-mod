package com.linngdu664.bsf.util;

import net.minecraft.util.Mth;

/**
 * Polar coordinate system.
 * The positive x-axis is the polar axis.
 */
public class PolarPos2d {
    private Vec2d pole;
    private double radius;
    private double angle;
    public PolarPos2d(Vec2d pole, double radius, double angle) {
        this.pole=pole;
        this.radius = radius;
        this.angle = angle;
    }

    public PolarPos2d(Vec2d pole, Vec2d p) {
        this.pole=pole;
        this.radius = Math.sqrt(pole.distanceToSqr(p));
        this.angle = Math.atan2(p.y-pole.y, p.x-pole.x);
    }
    public void rotate(double angle) {
        this.angle += angle;
    }
    public void rotateLength(double length){
        this.rotate(length/radius);
    }
    public Vec2d getP() {
        return new Vec2d(Math.cos(angle),Math.sin(angle)).scale(radius).add(pole);
    }
    public Vec2d getPole() {
        return pole;
    }
    public double getAngle() {
        return angle;
    }
    public double getRadius() {
        return radius;
    }

    public void setPole(Vec2d pole) {
        this.pole = pole;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setAngle(double angle) {
        this.angle = angle;
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
