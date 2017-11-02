package com.luckysweetheart.storage.request;

import com.luckysweetheart.storage.strategy.DefaultStoreIdNamingStrategy;
import com.luckysweetheart.storage.strategy.KeyNamingStrategy;
import com.luckysweetheart.storage.util.Common;
import com.luckysweetheart.storage.util.Cons;

/**
 * Created by yangxin on 2017/11/2.
 */
public class CopyObject {

    private String sourceStoreId;

    private String targetGroupName;

    private KeyNamingStrategy keyNamingStrategy;

    private String targetStoreId;

    public CopyObject(String sourceStoreId) {
        this(sourceStoreId, Common.getGroupName(sourceStoreId));
    }

    public CopyObject(String sourceStoreId, String targetGroupName) {
        this(sourceStoreId, targetGroupName, null);
    }

    public CopyObject(String sourceStoreId, String targetGroupName, KeyNamingStrategy keyNamingStrategy) {
        this.sourceStoreId = sourceStoreId;
        this.targetGroupName = targetGroupName;
        if (keyNamingStrategy == null) {
            keyNamingStrategy = new DefaultStoreIdNamingStrategy(targetGroupName, Cons.SEPARATOR, getExetion());
        }
        this.targetStoreId = keyNamingStrategy.generate();
        this.keyNamingStrategy = keyNamingStrategy;
    }

    public String getTargetStoreId() {
        return targetStoreId;
    }

    public String getSourceGroupName() {
        return Common.getGroupName(sourceStoreId);
    }

    private String getExetion() {
        return sourceStoreId.substring(sourceStoreId.lastIndexOf("."), sourceStoreId.length());
    }

    public String getSourceStoreId() {
        return sourceStoreId;
    }

    public void setSourceStoreId(String sourceStoreId) {
        this.sourceStoreId = sourceStoreId;
    }

    public String getTargetGroupName() {
        return targetGroupName;
    }

    public void setTargetGroupName(String targetGroupName) {
        this.targetGroupName = targetGroupName;
    }

    public KeyNamingStrategy getKeyNamingStrategy() {
        return keyNamingStrategy;
    }

    public void setKeyNamingStrategy(KeyNamingStrategy keyNamingStrategy) {
        this.keyNamingStrategy = keyNamingStrategy;
    }
}
