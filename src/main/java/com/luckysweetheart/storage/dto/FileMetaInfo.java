package com.luckysweetheart.storage.dto;

import com.alibaba.fastjson.JSON;
import com.luckysweetheart.storage.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by yangxin on 2017/09/30.
 */
public class FileMetaInfo implements Serializable {
    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件扩展名
     */
    private String extName;

    /**
     * MIME类型
     */
    private String mimeType;

    /**
     * 文件大小
     */
    private Long length;

    /**
     * 最后更新时间
     */
    private Long lastModified;

    public FileMetaInfo() {
    }

    public FileMetaInfo(String fileName, String extName, String mimeType, long length, long lastModified) {
        this.fileName = fileName;
        this.extName = extName;
        this.mimeType = mimeType;
        this.length = length;
        this.lastModified = lastModified;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtName() {
        return extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

    public static FileMetaInfo getFileMetaInfo(File file) throws IOException {
        String fileName = file.getName();
        String name = FileUtil.getWithoutExtension(fileName);
        String extName = FileUtil.getExtension(fileName);
        String mimeType = FileUtil.getMimeType(fileName);
        long length = file.length();
        long lastModified = file.lastModified();
        return new FileMetaInfo(name, extName, mimeType, length, lastModified);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
