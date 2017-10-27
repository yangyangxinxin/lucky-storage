package com.luckysweetheart.storage;

import com.luckysweetheart.storage.dto.Group;
import com.luckysweetheart.storage.service.OSSStoreService;
import com.luckysweetheart.storage.util.Common;
import com.luckysweetheart.storage.util.SpringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * bucketName管理
 * Created by yangxin on 2017/5/26.
 */
@Service
public strictfp class StorageGroupService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //@Value("${oos.defaultGroupName}")
    private String ossDefaultGroupName;

    //@Value("${oos.userGroupName}")
    private String ossUserGroupName;

    //@Value("${oos.photoGroupName}")
    private String ossPhotoGroupName;

    @Resource
    private StorageApi storageApi;

    @Resource
    private Environment environment;

    /**
     * 测试的bucketName
     *
     * @return
     */
    public String getDefaultGroupName() {
        if (StringUtils.isBlank(ossDefaultGroupName)) {
            init();
        }
        return ossDefaultGroupName;
    }

    /**
     * 用于存储用户的头像信息
     *
     * @return
     */
    public String getUserGroupName() {
        if (StringUtils.isBlank(ossUserGroupName)) {
            init();
        }
        return ossUserGroupName;
    }

    /**
     * 用于用户相片存储
     *
     * @return
     */
    public String getPhotoGroupName() {
        if (StringUtils.isBlank(ossPhotoGroupName)) {
            init();
        }
        return ossPhotoGroupName;
    }

    public void init() {
        try {
            String[] strings = environment.getActiveProfiles();
            String profile = "dev";
            if (strings != null && strings.length > 0) {
                System.out.println("active profiles 为：" + Arrays.toString(strings));
                profile = strings[0];
            }
            List<Group> groups = storageApi.groupList(profile);
            for (Group group : groups) {
                String name = group.getName();
                if (name.contains("user")) {
                    this.ossUserGroupName = name;
                }
                if (name.contains("default")) {
                    this.ossDefaultGroupName = name;
                }
                if (name.contains("photo")) {
                    this.ossPhotoGroupName = name;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public String getGroupName(String storeId) {
        return Common.getGroupName(storeId);
    }

}
