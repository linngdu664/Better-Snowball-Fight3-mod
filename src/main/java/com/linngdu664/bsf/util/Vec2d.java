package com.linngdu664.bsf.util;


public class Vec2d {
    public static final Vec2d ZERO = new Vec2d(0, 0);
    public final double x;
    public final double y;
    public Vec2d(Vec2d vec2d) {
        this.x = vec2d.x;
        this.y = vec2d.y;
    }

    public Vec2d(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Vec2d scale(double pFactor) {
        return new Vec2d(this.x * pFactor, this.y * pFactor);
    }

    public double dot(Vec2d pOther) {
        return this.x * pOther.x + this.y * pOther.y;
    }

    public Vec2d add(Vec2d pOther) {
        return new Vec2d(this.x + pOther.x, this.y + pOther.y);
    }

    public Vec2d add(float pValue) {
        return new Vec2d(this.x + pValue, this.y + pValue);
    }

    public Vec2d sub(Vec2d pOther) {
        return new Vec2d(this.x - pOther.x, this.y - pOther.y);
    }

    public Vec2d sub(float pValue) {
        return new Vec2d(this.x - pValue, this.y - pValue);
    }

    public boolean equals(Vec2d pOther) {
        return this.x == pOther.x && this.y == pOther.y;
    }

    public Vec2d normalized() {
        double f = Math.sqrt(this.x * this.x + this.y * this.y);
        return f < 1.0E-4F ? ZERO : new Vec2d(this.x / f, this.y / f);
    }

    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public double lengthSquared() {
        return this.x * this.x + this.y * this.y;
    }

    public double distanceToSqr(Vec2d pOther) {
        double f = pOther.x - this.x;
        double f1 = pOther.y - this.y;
        return f * f + f1 * f1;
    }

    public Vec2d negated() {
        return new Vec2d(-this.x, -this.y);
    }

    @Override
    public String toString() {
        return "Vec2d{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
