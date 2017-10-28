package com.luckysweetheart.storage.util;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by wlinguo on 14-7-31.
 */
public interface Cons {
    /**
     * 文件存储元信息的键值，文件名
     */
    String KEY_FILENAME = "filename";

    /**
     * 文件存储元信息的键值，扩展名
     */
    String KEY_EXTNAME = "extname";

    String SEPARATOR = "_" + Base64.encodeBase64String("lucky".getBytes()) + "_";
}
