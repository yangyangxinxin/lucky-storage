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

    /**
     * 是否需要返回byte数组，如果不需要则只返回图片的URL地址。
     * 返回byte数组需要把文件下载下来，再取byte数组，一般在网页上直接显示处理后的图片是不需要的。
     * 默认不需要
     */
    private boolean needByte = false;

    public ProcessRequest(String storeId, PictureProcess pictureProcess) {
        this.storeId = storeId;
        this.pictureProcess = pictureProcess;
        this.needByte = false;
    }

    public ProcessRequest(String storeId, PictureProcess pictureProcess, boolean needByte) {
        this.storeId = storeId;
        this.pictureProcess = pictureProcess;
        this.needByte = needByte;
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

    public boolean isNeedByte() {
        return needByte;
    }

    public void setNeedByte(boolean needByte) {
        this.needByte = needByte;
    }

}
