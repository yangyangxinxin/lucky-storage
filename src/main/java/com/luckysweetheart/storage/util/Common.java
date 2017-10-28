package com.luckysweetheart.storage.util;

import org.springframework.util.Assert;

/**
 * Created by yangxin on 2017/10/27.
 */
public class Common {

    public static String getGroupName(String storeId) {
        Assert.notNull(storeId, "storeId不能为空");
        String[] keys = storeId.split(Cons.SEPARATOR);
        if (keys.length > 0) {
            return keys[0];
        }
        return null;
    }

}
