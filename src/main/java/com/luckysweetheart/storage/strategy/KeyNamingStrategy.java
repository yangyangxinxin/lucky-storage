package com.luckysweetheart.storage.strategy;

/**
 * 考虑封装storeID生成策略
 * Created by yangxin on 2017/10/30.
 */
public interface KeyNamingStrategy {

    String generate();

    String groupName(String storeId);

}
