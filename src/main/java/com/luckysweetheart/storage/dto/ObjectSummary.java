package com.luckysweetheart.storage.dto;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yangxin on 2017/10/9.
 */
public class ObjectSummary implements Serializable {

    /**
     * The name of the bucket in which this object is stored
     */
    private String bucketName;

    private String storeId;

    private long size;

    private Date lastModified;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
