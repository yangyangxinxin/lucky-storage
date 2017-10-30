package com.luckysweetheart.storage.strategy;

/**
 * Created by yangxin on 2017/10/30.
 */
public interface StoreIdNamingStrategy {

    String generate();

    String groupName();

    String getSeparator();

}
