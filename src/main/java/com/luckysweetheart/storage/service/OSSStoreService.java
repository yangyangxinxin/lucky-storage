package com.luckysweetheart.storage.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.luckysweetheart.storage.StorageApi;
import com.luckysweetheart.storage.dto.Group;
import com.luckysweetheart.storage.exception.StorageException;
import com.luckysweetheart.storage.request.PutObject;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yangxin on 2017/8/10.
 */
@Service("storageApi")
public class OSSStoreService implements StorageApi {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final int BUFFER_SIZE = 4096;

    @Resource
    private OSSClient ossClient;

    public List<Group> groupList() throws StorageException {
        List<Bucket> buckets = ossClient.listBuckets();
        List<Group> groups = new ArrayList<>();
        try {
            if (buckets != null && buckets.size() > 0) {
                for (Bucket bucket : buckets) {
                    Group group = new Group();
                    group.setName(bucket.getName());
                    group.setCreateTime(bucket.getCreationDate());
                    groups.add(group);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new StorageException(e.getMessage());
        }
        return groups;
    }

    public String putObject(PutObject putObject) throws StorageException {
        try {
            ossClient.putObject(putObject.build());
            return putObject.getStoreId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new StorageException(e.getMessage());
        }
    }

    public byte[] getObject(String storeId) throws StorageException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            String[] keys = storeId.split("/");
            OSSObject ossObject = ossClient.getObject(keys[0], storeId);
            InputStream inputStream = ossObject.getObjectContent();
            byte[] data = new byte[BUFFER_SIZE];
            int count = -1;
            while ((count = inputStream.read(data, 0, BUFFER_SIZE)) != -1) {
                outStream.write(data, 0, count);
            }
            inputStream.close();
            outStream.close();
            return outStream.toByteArray();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new StorageException(e.getMessage());
        }
    }

    public boolean deleteObject(String storeId) throws StorageException {
        try {
            String[] keys = storeId.split("/");
            ossClient.deleteObject(keys[0], storeId);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new StorageException(e.getMessage());
        }
    }

    public String getHttpUrl(String storeId) throws StorageException {
        try {
            Date date = DateUtils.addYears(new Date(), 100);
            return getHttpUrl(storeId,date.getTime());
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new StorageException(e.getMessage());
        }
    }

    @Override
    public String getHttpUrl(String storeId, Long expire) throws StorageException {
        try {
            URL url = ossClient.generatePresignedUrl(getGroupName(storeId), storeId, new Date(expire));
            return url.toString();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new StorageException(e.getMessage());
        }
    }

    private String getGroupName(String storeId){
        String[] keys = storeId.split("/");
        return keys[0];
    }
}
