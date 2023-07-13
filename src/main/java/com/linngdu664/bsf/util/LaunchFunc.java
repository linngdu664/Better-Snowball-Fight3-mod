package com.linngdu664.bsf.util;

import com.linngdu664.bsf.entity.BSFSnowballEntity;

@Deprecated
public interface LaunchFunc {
    LaunchFrom getLaunchFrom();

    void launchProperties(BSFSnowballEntity bsfSnowballEntity);
}
