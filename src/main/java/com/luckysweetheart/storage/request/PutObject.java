package com.luckysweetheart.storage.request;

import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.luckysweetheart.storage.util.Cons;
import com.luckysweetheart.storage.util.IdWorker;
import com.luckysweetheart.storage.util.SpringUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by yangxin on 2017/9/22.
 */
public class PutObject {

    private byte[] bytes;

    private String groupName;

    private String storeId;

    private String extName;

    private String fileName;

    private long length;

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

    public PutObjectRequest build() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        IdWorker idWorker = SpringUtil.getBean(IdWorker.class);

        if (StringUtils.isBlank(this.storeId)) {
            this.storeId = this.getGroupName() + "/" + idWorker.nextId() + this.extName;
        }

        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(this.length);
        Map<String, String> userMetadata = new HashMap<>();
        userMetadata.put(Cons.KEY_FILENAME, this.fileName);
        userMetadata.put(Cons.KEY_EXTNAME, this.extName);

        objectMeta.setUserMetadata(userMetadata);

        return new PutObjectRequest(this.groupName, this.storeId, inputStream, objectMeta);
    }

}
