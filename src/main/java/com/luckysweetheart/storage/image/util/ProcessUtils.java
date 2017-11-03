package com.luckysweetheart.storage.image.util;

/**
 * Created by yangxin on 2017/11/3.
 */
public class ProcessUtils {

    public static String dealResult(String process) {
        if (process.lastIndexOf(",") == process.length() - 1) {
            process = process.substring(0, process.lastIndexOf(","));
        }
        return process;
    }
}
