package com.luckysweetheart.storage.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yangxin on 2017/10/28.
 */
@Service
public class EnvironmentService {

    @Resource
    private Environment environment;

    public static final String DEV = "dev";

    public static final String PRODUCT = "product";

    public static final String SANDBOX = "sandbox";

    /**
     * 获取当前环境
     *
     * @return
     */
    public String getProfile() {
        String[] strings = environment.getActiveProfiles();
        String profile = null;
        if (strings != null && strings.length > 0) {
            profile = strings[0];
        }
        return profile;
    }

    public boolean dev() {
        return is(DEV);
    }

    public boolean product() {
        return is(PRODUCT);
    }

    public boolean sandbox() {
        return is(SANDBOX);
    }

    public boolean is(String str) {
        return StringUtils.equalsIgnoreCase(getProfile(), str);
    }

}
