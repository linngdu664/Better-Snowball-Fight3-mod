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
    private Vec2d p;
    public PolarPos2d(Vec2d pole, double radius, double angle) {
        this.pole=pole;
        this.radius = radius;
        this.angle = angle;
        this.p=new Vec2d(radius*Math.cos(angle),radius*Math.sin(angle));
    }

    public PolarPos2d(Vec2d pole, Vec2d p) {
        this.pole=pole;
        this.radius = Math.sqrt(pole.distanceToSqr(p));
        this.angle = Math.atan2(p.y-pole.y, p.x-pole.x);
        this.p=p.sub(pole);
    }
    public Vec2d rotate(double angle) {
        Vec2d p0 = new Vec2d(p);
        this.angle += angle;
        updateP();
        return p.sub(p0);
    }
    public Vec2d rotateLength(double length){
        return rotate(length/radius);
    }
    private void updateP() {
        this.p=new Vec2d(radius*Math.cos(angle),radius*Math.sin(angle));
    }
    public Vec2d getPReal() {
        return new Vec2d(p.x+pole.x,p.y+pole.y);
    }
    public Vec2d getP() {
        return p;
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
