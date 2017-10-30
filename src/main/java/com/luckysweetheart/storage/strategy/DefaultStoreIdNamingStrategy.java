package com.luckysweetheart.storage.strategy;

import org.springframework.util.Assert;

import java.util.UUID;

/**
 * Created by yangxin on 2017/10/30.
 */
public class DefaultStoreIdNamingStrategy implements KeyNamingStrategy {

    private String groupName;

    private String separator;

    private String extName;

    public DefaultStoreIdNamingStrategy(String groupName, String separator, String extName) {
        this.groupName = groupName;
        this.separator = separator;
        this.extName = extName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getExtName() {
        return extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    @Override
    public String generate() {
        String key = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        return groupName + separator + key + extName;
    }

    @Override
    public String groupName(String storeId) {
        Assert.notNull(storeId, "storeId不能为空");
        String[] keys = storeId.split(separator);
        if (keys.length > 0) {
            return keys[0];
        }
        return null;
    }
}
