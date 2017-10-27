package com.luckysweetheart.storage.image.request;

import com.luckysweetheart.storage.image.base.PictureProcess;
import com.luckysweetheart.storage.util.Common;

import java.io.Serializable;

/**
 * Created by yangxin on 2017/10/27.
 */
public class ProcessRequest implements Serializable {

    private String storeId;

    private PictureProcess pictureProcess;

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
}
