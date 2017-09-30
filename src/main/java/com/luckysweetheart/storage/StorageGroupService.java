package com.luckysweetheart.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * bucketName管理
 * Created by yangxin on 2017/5/26.
 */
@Service
public class StorageGroupService {

    @Value("${oos.defaultGroupName}")
    private String oosDefaultGroupName;

    @Value("${oos.userGroupName}")
    private String oosUserGroupName;

    @Value("${oos.photoGroupName}")
    private String oosPhotoGroupName;


    /**
     * 测试的bucketName
     *
     * @return
     */
    public String getDefaultGroupName() {
        return oosDefaultGroupName;
    }

    /**
     * 用于存储用户的头像信息
     *
     * @return
     */
    public String getUserGroupName() {
        return oosUserGroupName;
    }

    /**
     * 用于用户相片存储
     *
     * @return
     */
    public String getPhotoGroupName() {
        return oosPhotoGroupName;
    }

    public String getGroupName(String storeId) {
        Assert.notNull(storeId, "storeId不能为空");
        String[] keys = storeId.split("/");
        if (keys.length > 0) {
            return keys[0];
        }
        return null;
    }

}
