package com.luckysweetheart.storage.request;

import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.luckysweetheart.storage.util.Cons;
import com.luckysweetheart.storage.util.IdWorker;
import com.luckysweetheart.storage.util.SpringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
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

    /**
     * 文件元数组
     */
    private ObjectMetadata objectMetadata;

    public ObjectMetadata getObjectMetadata() {
        return objectMetadata;
    }

    public void setObjectMetadata(ObjectMetadata objectMetadata) {
        this.objectMetadata = objectMetadata;
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
        Assert.notNull(groupName, "存储组名不能为空");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

        if (StringUtils.isBlank(this.storeId)) {
            String key = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
            this.storeId = this.getGroupName() + "/" + key;
            if (StringUtils.isNotBlank(this.extName)) {
                this.storeId += this.extName;
            }
        }
        if (this.objectMetadata == null) {
            this.objectMetadata = new ObjectMetadata();
            this.objectMetadata.setContentLength(this.length);

            Map<String, String> userMetadata = new HashMap<>();
            userMetadata.put(Cons.KEY_FILENAME, this.fileName);
            userMetadata.put(Cons.KEY_EXTNAME, this.extName);

            objectMetadata.setUserMetadata(userMetadata);
        }
        return new PutObjectRequest(this.groupName, this.storeId, inputStream, objectMetadata);
    }

}
