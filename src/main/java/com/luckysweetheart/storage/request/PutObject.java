package com.luckysweetheart.storage.request;

import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.luckysweetheart.storage.StorageGroupService;
import com.luckysweetheart.storage.dto.FileMetaInfo;
import com.luckysweetheart.storage.util.Cons;
import com.luckysweetheart.storage.util.IdWorker;
import com.luckysweetheart.storage.util.SpringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * Created by yangxin on 2017/9/22.
 */
public class PutObject {

    /**
     * 文件二进制数组
     */
    private byte[] bytes;

    /**
     * 存储组名
     */
    private String groupName;

    /**
     * 文件存储ID
     */
    private String storeId;

    /**
     * 文件后缀 例如.png等
     */
    private String extName;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件大小
     */
    private long length;

    private FileMetaInfo fileMetaInfo;

    public FileMetaInfo getFileMetaInfo() {
        return fileMetaInfo;
    }

    public void setFileMetaInfo(FileMetaInfo fileMetaInfo) {
        this.fileMetaInfo = fileMetaInfo;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getExtName() {
        return extName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * 构建OSS的PutObjectRequest对象
     *
     * @return
     */
    public PutObjectRequest build() {
        Assert.notNull(bytes, "文件不能为空");
        StorageGroupService storageGroupService = SpringUtil.getBean(StorageGroupService.class);
        groupName = StringUtils.isBlank(groupName) ? storageGroupService.getDefaultGroupName() : groupName;
        Assert.isTrue(StringUtils.isNotBlank(groupName), "存储组名不能为空");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

        if (StringUtils.isBlank(this.storeId)) {
            String key = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
            this.storeId = this.getGroupName() + "/" + key;
            if (StringUtils.isNotBlank(this.extName)) {
                this.storeId += this.extName;
            }
        }
        ObjectMetadata objectMetadata = new ObjectMetadata();
        if (fileMetaInfo == null) {
            objectMetadata.setContentLength(this.length);

            Map<String, String> userMetadata = new HashMap<>();
            userMetadata.put(Cons.KEY_FILENAME, this.fileName);
            userMetadata.put(Cons.KEY_EXTNAME, this.extName);

            objectMetadata.setUserMetadata(userMetadata);

            fileMetaInfo = new FileMetaInfo(fileName, extName, null, length, new Date().getTime());
        } else {
            objectMetadata.setContentLength(fileMetaInfo.getLength());

            Map<String, String> userMetadata = new HashMap<>();
            userMetadata.put(Cons.KEY_FILENAME, fileMetaInfo.getFileName());
            userMetadata.put(Cons.KEY_EXTNAME, fileMetaInfo.getExtName());

            objectMetadata.setUserMetadata(userMetadata);
        }
        return new PutObjectRequest(this.groupName, this.storeId, inputStream, objectMetadata);
    }

}
