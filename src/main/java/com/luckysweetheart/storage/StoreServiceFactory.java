package com.luckysweetheart.storage;

import com.luckysweetheart.storage.service.OSSStoreService;
import com.luckysweetheart.storage.util.SpringUtil;

/**
 * 存储服务工厂
 * Created by yangxin on 2017/8/10.
 */
public class StoreServiceFactory {

    public static final String OSS = "oss";

    public static final String COS = "cos";

    /**
     * 获取存储服务
     *
     * @param storeName
     * @return
     */
    public static StorageApi createStorageService(String storeName) {
        if (storeName.equalsIgnoreCase(StoreServiceFactory.OSS)) {
            return SpringUtil.getBean(OSSStoreService.class);
        }
        return null;
    }

}
