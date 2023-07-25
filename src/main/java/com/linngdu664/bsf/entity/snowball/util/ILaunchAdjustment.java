package com.linngdu664.bsf.entity.snowball.util;

public interface ILaunchAdjustment {
    double adjustPunch(double punch);

    int adjustWeaknessTicks(int weaknessTicks);

    int adjustFrozenTicks(int frozenTicks);

    float adjustDamage(float damage);

    float adjustBlazeDamage(float blazeDamage);

    LaunchFrom getLaunchFrom();
}
