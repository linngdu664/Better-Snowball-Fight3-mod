package com.linngdu664.bsf.util;

import com.linngdu664.bsf.entity.BSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.LaunchFrom;

@Deprecated
public interface LaunchFunc {
    LaunchFrom getLaunchFrom();

    void launchProperties(BSFSnowballEntity bsfSnowballEntity);
}
