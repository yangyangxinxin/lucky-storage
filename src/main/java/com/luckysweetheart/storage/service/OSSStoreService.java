package com.luckysweetheart.storage.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.luckysweetheart.storage.StorageApi;
import com.luckysweetheart.storage.StorageGroupService;
import com.luckysweetheart.storage.dto.FileMetaInfo;
import com.luckysweetheart.storage.dto.Group;
import com.luckysweetheart.storage.dto.ObjectSummary;
import com.luckysweetheart.storage.exception.StorageException;
import com.luckysweetheart.storage.image.ConvertFormatProcess;
import com.luckysweetheart.storage.image.base.PictureProcess;
import com.luckysweetheart.storage.image.request.ProcessRequest;
import com.luckysweetheart.storage.image.response.ProcessResponse;
import com.luckysweetheart.storage.request.CopyObject;
import com.luckysweetheart.storage.request.PutObject;
import com.luckysweetheart.storage.util.Cons;
import com.luckysweetheart.storage.util.DateUtil;
import com.luckysweetheart.storage.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * Created by yangxin on 2017/8/10.
 */
@Service("storageApi")
public class OSSStoreService implements StorageApi {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final int BUFFER_SIZE = 4096;

    @Resource
    private OSSClient ossClient;

    @Resource
    private StorageGroupService storageGroupService;

    @Resource
    private EnvironmentService environmentService;

    @Override
    public List<Group> groupList() throws StorageException {
        return groupList(environmentService.getProfile());
    }

    @Override
    public List<Group> groupList(String prefix) throws StorageException {
        logger.info("调用OSS存储获取Bucket列表开始 at {}", DateUtil.formatNow());
        BucketList bucketsList = ossClient.listBuckets(prefix, null, null);
        List<Bucket> buckets = bucketsList.getBucketList();
        List<Group> groups = new ArrayList<>();
        try {
            if (buckets != null && buckets.size() > 0) {
                for (Bucket bucket : buckets) {
                    Group group = new Group();
                    group.setName(bucket.getName());
                    group.setCreateTime(bucket.getCreationDate());
                    group.setLocation(bucket.getLocation());
                    group.setExtranetEndpoint(bucket.getExtranetEndpoint());
                    group.setIntranetEndpoint(bucket.getIntranetEndpoint());
                    groups.add(group);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new StorageException(e.getMessage());
        }
        logger.info("调用OSS存储获取Bucket列表结束 at {}", DateUtil.formatNow());
        return groups;
    }

    @Override
    public Group getGroupInfo(String groupName) throws StorageException {
        Assert.isTrue(StringUtils.isNotBlank(groupName), "group name can not be null");
        try {
            BucketInfo bucketInfo = ossClient.getBucketInfo(groupName);
            Bucket bucket = bucketInfo.getBucket();
            Group group = new Group();
            group.setName(bucket.getName());
            group.setCreateTime(bucket.getCreationDate());
            group.setLocation(bucket.getLocation());
            group.setExtranetEndpoint(bucket.getExtranetEndpoint());
            group.setIntranetEndpoint(bucket.getIntranetEndpoint());
            return group;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new StorageException(e.getMessage());
        }
    }

    @Override
    public List<ObjectSummary> objectList(String groupName) throws StorageException {
        logger.info("调用OSS存储查询 {} 组下的文件列表开始 at {}", groupName, DateUtil.formatNow());
        try {
            Assert.isTrue(StringUtils.isNotBlank(groupName), "组名不能为空");
            ListObjectsRequest request = new ListObjectsRequest();
            request.setMaxKeys(1000);
            request.setBucketName(groupName);
            List<ObjectSummary> list = new ArrayList<>();
            ObjectListing objectListing = ossClient.listObjects(request);

            if (objectListing != null) {
                List<OSSObjectSummary> objectSummaries = objectListing.getObjectSummaries();
                if (objectSummaries != null && objectSummaries.size() > 0) {

                    for (OSSObjectSummary ossObjectSummary : objectSummaries) {
                        ObjectSummary summary = new ObjectSummary();
                        summary.setBucketName(ossObjectSummary.getBucketName());
                        summary.setLastModified(ossObjectSummary.getLastModified());
                        summary.setSize(ossObjectSummary.getSize());
                        summary.setStoreId(ossObjectSummary.getKey());
                        list.add(summary);
                    }
                }
            }
            logger.info("调用OSS存储查询 {} 组下的文件列表结束 at {}", groupName, DateUtil.formatNow());
            return list;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new StorageException(e.getMessage());
        }

    }

    @Override
    public long groupFileSize(String groupName) throws StorageException {
        long size = 0L;
        try {
            logger.info("调用OSS存储查询 {} 组文件占用大小开始 at {}", groupName, DateUtil.formatNow());
            Assert.isTrue(StringUtils.isNotBlank(groupName), "组名不能为空");
            List<ObjectSummary> list = this.objectList(groupName);
            if (list != null && list.size() > 0) {
                for (ObjectSummary objectSummary : list) {
                    size += objectSummary.getSize();
                }
            }
            logger.info("调用OSS存储查询 {} 组文件占用大小结束 at {}", groupName, DateUtil.formatNow());
            return size;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new StorageException(e.getMessage());
        }
    }

    @Override
    public String putObject(PutObject putObject) throws StorageException {
        try {
            PutObjectRequest request = putObject.build();
            logger.info("调用OSS存储存储文件开始，要存储的文件的组名为：{} ， storeId 为 {} at {}", request.getBucketName(), request.getKey(), DateUtil.formatNow());
            ossClient.putObject(request);
            logger.info("调用OSS存储存储文件结束， storeId 为 {} at {}", request.getKey(), DateUtil.formatNow());
            return putObject.getStoreId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new StorageException(e.getMessage());
        }
    }

    @Override
    public String copyObject(CopyObject copyObject) throws StorageException {
        try {
            Assert.notNull(copyObject, "copyObject can not be null");
            ossClient.copyObject(copyObject.getSourceGroupName(), copyObject.getSourceStoreId(), copyObject.getTargetGroupName(), copyObject.getTargetStoreId());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new StorageException(e.getMessage());
        }
        return copyObject.getTargetStoreId();
    }

    @Override
    public byte[] getObject(String storeId) throws StorageException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            logger.info("调用OSS存储下载文件开始，要下载的文件为{} at {}", storeId, DateUtil.formatNow());
            OSSObject ossObject = ossClient.getObject(storageGroupService.getGroupName(storeId), storeId);
            InputStream inputStream = ossObject.getObjectContent();
            byte[] data = new byte[BUFFER_SIZE];
            int count;
            while ((count = inputStream.read(data, 0, BUFFER_SIZE)) != -1) {
                outStream.write(data, 0, count);
            }
            inputStream.close();
            outStream.close();
            logger.info("调用OSS存储下载文件结束，下载的文件为{} at {}", storeId, DateUtil.formatNow());
            return outStream.toByteArray();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new StorageException(e.getMessage());
        }
    }

    @Override
    public boolean deleteObject(String storeId) throws StorageException {
        try {
            logger.info("开始调用OSS存储删除文件，要删除的文件为{} at {]", storeId, DateUtil.formatNow());
            ossClient.deleteObject(storageGroupService.getGroupName(storeId), storeId);
            logger.info("结束调用OSS存储删除文件，要删除的文件为{} at {]", storeId, DateUtil.formatNow());
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new StorageException(e.getMessage());
        }
    }

    @Override
    public String getHttpUrl(String storeId) throws StorageException {
        try {
            Date now = new Date();
            Date date = DateUtils.addYears(now, 100);
            return getHttpUrl(storeId, date.getTime());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new StorageException(e.getMessage());
        }
    }

    @Override
    public String getHttpUrl(String storeId, Long expire) throws StorageException {
        try {
            URL url = ossClient.generatePresignedUrl(storageGroupService.getGroupName(storeId), storeId, new Date(expire));
            return url.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new StorageException(e.getMessage());
        }
    }

    @Override
    public boolean doesObjectExist(String storeId) throws StorageException {
        try {
            String group = storageGroupService.getGroupName(storeId);
            HeadObjectRequest headObjectRequest = new HeadObjectRequest(group, storeId);
            return ossClient.doesObjectExist(headObjectRequest);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new StorageException(e.getMessage());
        }
    }

    @Override
    public FileMetaInfo getFileMetaInfo(String storeId) throws StorageException {
        try {
            ObjectMetadata objectMetadata = ossClient.getObjectMetadata(storageGroupService.getGroupName(storeId), storeId);
            if (objectMetadata == null) {
                throw new StorageException("获取文件元信息失败");
            }
            FileMetaInfo fileMetaInfo = new FileMetaInfo();
            Date time = objectMetadata.getLastModified();
            if (time != null) {
                fileMetaInfo.setLastModified(time.getTime());
            }
            Map<String, String> map = objectMetadata.getUserMetadata();
            if (map != null && map.size() > 0) {
                if (map.containsKey(Cons.KEY_FILENAME)) {
                    fileMetaInfo.setFileName(map.get(Cons.KEY_FILENAME));
                }
                if (map.containsKey(Cons.KEY_EXTNAME)) {
                    fileMetaInfo.setExtName(map.get(Cons.KEY_EXTNAME));
                }
            }
            fileMetaInfo.setLength(objectMetadata.getContentLength());

            return fileMetaInfo;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new StorageException(e.getMessage());
        }
    }

    @Override
    public ProcessResponse pictureProcess(ProcessRequest request) throws StorageException {
        try {
            String storeId = request.getStoreId();
            PictureProcess pictureProcess = request.getPictureProcess();

            String process = pictureProcess.process();
            logger.info(process);

            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(request.getGroup(), request.getStoreId());
            generatePresignedUrlRequest.setProcess(process);

            Date expire = request.getExpireTime();
            if (request.getExpireTime() == null) {
                expire = DateUtils.addYears(new Date(), 100);
            }

            generatePresignedUrlRequest.setExpiration(expire);
            URL url = ossClient.generatePresignedUrl(generatePresignedUrlRequest);

            String result = url.toString();

            ProcessResponse response = new ProcessResponse();
            response.setUrl(result);

            if (request.isNeedByte()) {
                GetObjectRequest getObjectRequest = new GetObjectRequest(request.getGroup(), storeId);
                getObjectRequest.setProcess(process);
                String extention = ".jpg";
                if (pictureProcess instanceof ConvertFormatProcess) {
                    extention = ((ConvertFormatProcess) pictureProcess).getFormat();
                }
                File file = new File(FileUtil.getFilePath() + System.currentTimeMillis() + extention);
                ossClient.getObject(getObjectRequest, file);
                try {
                    logger.info(file.getPath());
                    byte[] bytes = FileUtils.readFileToByteArray(file);
                    response.setBytes(bytes);
                } catch (IOException e) {
                    logger.error(e.getMessage());
                } finally {
                    if (environmentService.product()) {
                        if (file.exists()) {
                            boolean delete = file.delete();
                            logger.info("delete {} {}", file.getPath(), delete ? "success" : "fail");
                        }
                    }
                }
            }
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new StorageException(e.getMessage());
        }

    }
}
