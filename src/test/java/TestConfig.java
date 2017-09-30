import com.aliyun.oss.OSSClient;
import com.luckysweetheart.storage.StorageApi;
import com.luckysweetheart.storage.StorageGroupService;
import com.luckysweetheart.storage.dto.Group;
import com.luckysweetheart.storage.exception.StorageException;
import com.luckysweetheart.storage.request.PutObject;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TestConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OSSClient ossClient;

    @Autowired
    private StorageGroupService storageGroupService;

    @Resource
    private StorageApi storageApi;


    @Test
    public void test1(){
        System.out.println(ossClient);
    }

    @Test
    public void test2() throws StorageException {
        List<Group> groups = storageApi.groupList();
        for (Group group : groups) {
            String name = group.getName();
            System.out.println(name);
            /*try {
                ossClient.deleteBucket(name);
            }catch (Exception e){
                logger.error(e.getMessage());
            }*/
        }
    }

    @Test
    public void test3(){
        PutObject putObject = new PutObject();
        byte[] bytes = "测试上传接口2222".getBytes();
        putObject.setBytes(bytes);
        putObject.setGroupName(storageGroupService.getUserGroupName());
        try {
            String result = storageApi.putObject(putObject); // lucky-user-dev/8847358877965225984
            System.out.println(result);
        } catch (StorageException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void test4(){
        try {
            byte[] bytes = storageApi.getObject("lucky-user-dev/8847358877965225984");
            System.out.println(new String(bytes));
        } catch (StorageException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void test5(){
        try {
            byte[] bytes = FileUtils.readFileToByteArray(new File("C:\\Users\\dp\\Desktop\\图片\\070.jpg"));
            PutObject putObject = new PutObject();
            putObject.setBytes(bytes);
            putObject.setGroupName(storageGroupService.getUserGroupName());
            putObject.setExtName(".jpg");
            putObject.setFileName("070");
            putObject.setLength(bytes.length);
            String result = storageApi.putObject(putObject);
            System.out.println(result); // lucky-user-dev/8847372361260077056.jpg
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }

    @Test
    public void test6(){
        try {
            String httpUrl = storageApi.getHttpUrl("lucky-user-dev/8847372361260077056.jpg");
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
}
