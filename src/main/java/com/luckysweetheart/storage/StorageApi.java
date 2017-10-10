package com.luckysweetheart.storage;

import com.luckysweetheart.storage.dto.FileMetaInfo;
import com.luckysweetheart.storage.dto.Group;
import com.luckysweetheart.storage.dto.ObjectSummary;
import com.luckysweetheart.storage.exception.StorageException;
import com.luckysweetheart.storage.request.PutObject;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 存储服务API
 * Created by yangxin on 2017/6/12.
 */
@Component
public interface StorageApi {

    /**
     * 列出所有的Bucket，如果在运行环境中指定了环境，那么根据环境配置来取Bucket
     *
     * @return
     * @throws StorageException
     */
    List<Group> groupList() throws StorageException;

    /**
     * 列出指定前缀的Bucket
     *
     * @param prefix 前缀，为null查询全部
     * @return
     * @throws StorageException
     */
    List<Group> groupList(String prefix) throws StorageException;

    /**
     * 获取某个组下的文件，最多能取1000个
     *
     * @param groupName 组名
     * @return
     * @throws StorageException
     */
    List<ObjectSummary> objectList(String groupName) throws StorageException;

    /**
     * 计算某个组所占用的空间大小
     *
     * @param groupName
     * @return
     * @throws StorageException
     */
    long groupFileSize(String groupName) throws StorageException;

    /**
     * 上传文件
     *
     * @param putObject
     * @return
     * @throws StorageException
     */
    String putObject(PutObject putObject) throws StorageException;

    /**
     * 下载文件
     *
     * @param storeId
     * @return
     * @throws StorageException
     */
    byte[] getObject(String storeId) throws StorageException;

    /**
     * 删除文件
     *
     * @param storeId
     * @return
     * @throws StorageException
     */
    boolean deleteObject(String storeId) throws StorageException;

    /**
     * 获取图片的网络访问地址，有效期为100年
     *
     * @param storeId
     * @return
     * @throws StorageException
     */
    String getHttpUrl(String storeId) throws StorageException;

    /**
     * 获取图片的网络访问地址，并指定有效期
     *
     * @param storeId
     * @param expire  过期时间
     * @return
     * @throws StorageException
     */
    String getHttpUrl(String storeId, Long expire) throws StorageException;

    /**
     * 判断某个文件是否存在
     *
     * @param storeId
     * @return
     * @throws StorageException
     */
    boolean doesObjectExist(String storeId) throws StorageException;

    /**
     * 获取文件的元信息
     *
     * @param storeId
     * @return
     * @throws StorageException
     */
    FileMetaInfo getFileMetaInfo(String storeId) throws StorageException;

}
