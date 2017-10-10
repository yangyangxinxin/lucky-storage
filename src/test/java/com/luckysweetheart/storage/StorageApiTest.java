package com.luckysweetheart.storage;

import com.luckysweetheart.storage.dto.FileMetaInfo;
import com.luckysweetheart.storage.dto.Group;
import com.luckysweetheart.storage.dto.ObjectSummary;
import com.luckysweetheart.storage.request.PutObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

/**
 * Created by yangxin on 2017/10/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/spring/spring-storage.xml"})
@ActiveProfiles("prod")
public class StorageApiTest {

    @Resource
    private StorageApi storageApi;

    @Resource
    private StorageGroupService storageGroupService;

    @Test
    public void groupList() throws Exception {
        List<Group> groups = storageApi.groupList();
        System.out.println(groups);
    }

    @Test
    public void groupList1() throws Exception {
    }

    @Test
    public void objectList() throws Exception {
        List<ObjectSummary> list = storageApi.objectList(storageGroupService.getDefaultGroupName());
        System.out.println(list);
    }

    @Test
    public void groupFileSize() throws Exception {
        long size = storageApi.groupFileSize(storageGroupService.getDefaultGroupName());
        System.out.println(size);
    }

    @Test
    public void putObject() throws Exception {
        PutObject putObject = new PutObject();
        String str = "this is test oss upload";
        putObject.setBytes(str.getBytes());
        putObject.setLength(putObject.getBytes().length);
        putObject.setFileName("test.txt");
        putObject.setExtName(".txt");
        putObject.setGroupName(storageGroupService.getDefaultGroupName());
        String storeId = storageApi.putObject(putObject);
        System.out.println(storeId); // prod-default/E7C5DD85A318411EA36C21DF5B6FB8E3.txt
    }

    @Test
    public void getObject() throws Exception {
        List<ObjectSummary> list = storageApi.objectList(storageGroupService.getDefaultGroupName());
        if (list != null && list.size() > 0) {
            for (ObjectSummary objectSummary : list) {
                byte[] bytes = storageApi.getObject(objectSummary.getStoreId());
                System.out.println(new String(bytes));
            }
        }
    }

    @Test
    public void deleteObject() throws Exception {
    }

    @Test
    public void getHttpUrl() throws Exception {
    }

    @Test
    public void getHttpUrl1() throws Exception {
    }

    @Test
    public void doesObjectExist() throws Exception {
    }

    @Test
    public void getObjectMetadata() throws Exception {
        List<ObjectSummary> list = storageApi.objectList(storageGroupService.getDefaultGroupName());
        if (list != null && list.size() > 0) {
            for (ObjectSummary objectSummary : list) {
                FileMetaInfo objectMetadata = storageApi.getFileMetaInfo(objectSummary.getStoreId());
                System.out.println(objectMetadata);
            }
        }
    }

}