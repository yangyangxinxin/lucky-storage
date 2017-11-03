import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.luckysweetheart.storage.StorageApi;
import com.luckysweetheart.storage.StorageGroupService;
import com.luckysweetheart.storage.dto.Group;
import com.luckysweetheart.storage.exception.StorageException;
import com.luckysweetheart.storage.image.*;
import com.luckysweetheart.storage.image.base.PictureProcess;
import com.luckysweetheart.storage.image.process.resize.ResizeProcess;
import com.luckysweetheart.storage.image.process.rotate.RotateProcess;
import com.luckysweetheart.storage.image.request.ProcessRequest;
import com.luckysweetheart.storage.image.response.ProcessResponse;
import com.luckysweetheart.storage.request.PutObject;
import com.luckysweetheart.storage.util.Common;
import com.luckysweetheart.storage.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;

/**
 * Created by yangxin on 2017/9/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/spring/spring-storage.xml"})
@ActiveProfiles("dev")
public class TestConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OSSClient ossClient;

    @Autowired
    private StorageGroupService storageGroupService;

    @Resource
    private StorageApi storageApi;

    @Test
    public void testCreate() {
        Bucket bucket = ossClient.createBucket("prod-default");
        System.out.println(bucket);
        bucket = ossClient.createBucket("prod-photo");
        System.out.println(bucket);
        bucket = ossClient.createBucket("prod-user");
        System.out.println(bucket);
    }

    @Test
    public void test1() {
        System.out.println(storageGroupService.getDefaultGroupName());
        System.out.println(storageGroupService.getPhotoGroupName());
        System.out.println(storageGroupService.getUserGroupName());
    }

    @Test
    public void test2() throws StorageException {
        List<Group> groups = storageApi.groupList();
        for (Group group : groups) {
            String name = group.getName();
            System.out.println(name);
        }
    }

    @Test
    public void testFilter() throws StorageException {
        List<Group> groups = storageApi.groupList("prod");
        for (Group group : groups) {
            String name = group.getName();
            System.out.println(name);
        }
    }

    @Test
    public void test3() {
        PutObject putObject = new PutObject();
        String str = "heello";
        byte[] bytes = str.getBytes();
        putObject.setBytes(bytes);
        putObject.setGroupName(storageGroupService.getUserGroupName());
        try {
            String result = storageApi.putObject(putObject); // lucky-user-dev/350F9C92127C40128BEA12B1B5C88C6E
            System.out.println(result);
        } catch (StorageException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void test4() {
        try {
            byte[] bytes = storageApi.getObject("lucky-user-dev/350F9C92127C40128BEA12B1B5C88C6E");
            System.out.println(new String(bytes));
        } catch (StorageException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void test5() {
        try {
            byte[] bytes = FileUtils.readFileToByteArray(new File("C:\\Users\\dp\\Desktop\\图片\\070.jpg"));
            PutObject putObject = new PutObject();
            putObject.setBytes(bytes);
            putObject.setGroupName(storageGroupService.getDefaultGroupName());
            putObject.setExtName(".jpg");
            putObject.setFileName("6963");
            putObject.setLength(bytes.length);
            String result = storageApi.putObject(putObject);
            System.out.println(result); // prod-default/CF4C95C644E3485DA6ACA8549113F4DE.jpg
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void test6() {
        try {
            String httpUrl = storageApi.getHttpUrl("lucky-bubu-dev/FDC5930D777944839413C3FCFC31D75A.jpg");
            System.out.println(httpUrl);
        } catch (StorageException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void test7() throws StorageException {
        boolean deleteObject = storageApi.deleteObject("lucky-user-dev/8847372361260077056.jpg");
        System.out.println(deleteObject);
    }

    @Test
    public void test8() throws StorageException {
        System.out.println(storageApi.doesObjectExist("lucky-bubu-dev/FDC5930D777944839413C3FCFC31D75A.jpg"));
    }

    @Test
    public void test9() throws StorageException {
        Long size = storageApi.groupFileSize(storageGroupService.getDefaultGroupName());
        System.out.println(size);
    }

    @Test
    public void test10() throws StorageException {
        PutBucketImageRequest putBucketImageRequest = new PutBucketImageRequest(storageGroupService.getDefaultGroupName());
        PictureProcess process = new WatermarkProcess("yangxin");
        process = new RotateProcess(90);
        //process = new ResizeProcess(200, 300);
        process = new BlurProcess(50, 50);
        ProcessRequest request = new ProcessRequest("prod-default_bHVja3k=_FE5C7E57D6644A8EA42427C3F9A3D122.jpg", process);
        ProcessResponse response = storageApi.pictureProcess(request);
        System.out.println(response.getUrl());
    }

    @Test
    public void test11() throws StorageException {
        Group group = storageApi.getGroupInfo(storageGroupService.getDefaultGroupName());
        System.out.println(group);
    }

    @Test
    public void test12() {
        UserQos userQos = ossClient.getBucketStorageCapacity(storageGroupService.getDefaultGroupName());
        System.out.println(JSON.toJSONString(userQos));
    }

    @Test
    public void test13() throws IOException {
        String key = "prod-default_bHVja3k=_FE5C7E57D6644A8EA42427C3F9A3D122.jpg";
        PictureProcess process = new PictureProcess() {
            @Override
            public String process() {
                return "image/info";
            }
        };

        GetObjectRequest getObjectRequest = new GetObjectRequest(Common.getGroupName(key), key);
        getObjectRequest.setProcess(process.process());

        File file = new File(FileUtil.getFilePath() + System.currentTimeMillis() + ".txt");

        ossClient.getObject(getObjectRequest, file);

        byte[] bytes = FileUtils.readFileToByteArray(file);

        String result = new String(bytes);

        JSONObject jsonObject = JSON.parseObject(result);
        System.out.println(jsonObject);
    }

    @Test
    public void test14() throws StorageException {
        BlurProcess blurProcess = new BlurProcess(30, 20);

        ResizeProcess resizeProcess = new ResizeProcess(200, 150);

        WatermarkProcess watermarkProcess = new WatermarkProcess("yangxin");

        SharpenProcess sharpenProcess = new SharpenProcess(0);

        CompositeProcess compositeProcess = new CompositeProcess(sharpenProcess, blurProcess, watermarkProcess, resizeProcess);

        ProcessRequest request = new ProcessRequest();
        request.setPictureProcess(compositeProcess);
        request.setStoreId("prod-default_bHVja3k=_FE5C7E57D6644A8EA42427C3F9A3D122.jpg");

        ProcessResponse response = storageApi.pictureProcess(request);
        System.out.println(response.getUrl());
    }
}
