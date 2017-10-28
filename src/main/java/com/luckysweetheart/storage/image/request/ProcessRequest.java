package com.luckysweetheart.storage.image.request;

import com.luckysweetheart.storage.image.base.PictureProcess;
import com.luckysweetheart.storage.util.Common;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yangxin on 2017/10/27.
 */
public class ProcessRequest implements Serializable {

    /**
     * 文件的key
     */
    private String storeId;

    /**
     * 处理方式
     */
    private PictureProcess pictureProcess;

    /**
     * 过期时间，例如设置为2018年1月1日过期。不指定默认为100年。
     */
    private Date expireTime;

    public ProcessRequest(String storeId, PictureProcess pictureProcess) {
        this.storeId = storeId;
        this.pictureProcess = pictureProcess;
    }

    public ProcessRequest() {
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public PictureProcess getPictureProcess() {
        return pictureProcess;
    }

    public void setPictureProcess(PictureProcess pictureProcess) {
        this.pictureProcess = pictureProcess;
    }

    public String getGroup() {
        return Common.getGroupName(this.storeId);
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
