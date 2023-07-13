package com.linngdu664.bsf.entity;

public interface ILaunchAdjustment {
    double adjustPunch(double punch);
    int adjustWeaknessTicks(int weaknessTicks);
    int adjustFrozenTicks(int frozenTicks);
    float adjustDamage(float damage);
    float adjustBlazeDamage(float blazeDamage);

    ILaunchAdjustment DEFAULT = new ILaunchAdjustment() {
        @Override
        public double adjustPunch(double punch) {
            return punch;
        }

        @Override
        public int adjustWeaknessTicks(int weaknessTicks) {
            return weaknessTicks;
        }

        @Override
        public int adjustFrozenTicks(int frozenTicks) {
            return frozenTicks;
        }

        @Override
        public float adjustDamage(float damage) {
            return damage;
        }

        @Override
        public float adjustBlazeDamage(float blazeDamage) {
            return blazeDamage;
        }
    };
}
