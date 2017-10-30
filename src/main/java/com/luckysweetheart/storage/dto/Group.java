package com.luckysweetheart.storage.dto;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;

/**
 * Bucket
 * Created by yangxin on 2017/9/21.
 */
public class Group implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 地区
     */
    private String location;

    /**
     * 内部地址
     */
    private String extranetEndpoint;

    /**
     * 外部地址
     */
    private String intranetEndpoint;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getExtranetEndpoint() {
        return extranetEndpoint;
    }

    public void setExtranetEndpoint(String extranetEndpoint) {
        this.extranetEndpoint = extranetEndpoint;
    }

    public String getIntranetEndpoint() {
        return intranetEndpoint;
    }

    public void setIntranetEndpoint(String intranetEndpoint) {
        this.intranetEndpoint = intranetEndpoint;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
