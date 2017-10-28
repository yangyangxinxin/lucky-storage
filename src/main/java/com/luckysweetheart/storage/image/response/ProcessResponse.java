package com.luckysweetheart.storage.image.response;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by yangxin on 2017/10/28.
 */
public class ProcessResponse implements Serializable {

    /**
     * 处理过的文件二进制数组
     */
    private byte[] bytes;

    /**
     * 图片的网络访问路径，默认有效期100年
     */
    private String url;

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
