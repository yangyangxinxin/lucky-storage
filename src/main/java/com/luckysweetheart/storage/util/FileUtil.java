package com.luckysweetheart.storage.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by wlinguo on 14-5-7.
 */
public class FileUtil {
    public static final String DOT = ".";

    /**
     * 获取没有扩展名的文件名
     *
     * @param fileName
     * @return
     */
    public static String getWithoutExtension(String fileName) {
        String name = StringUtils.substring(fileName, 0,
                StringUtils.lastIndexOf(fileName, DOT));
        return StringUtils.trimToEmpty(name);
    }

    /**
     * 获取扩展名
     *
     * @param fileName
     * @return
     */
    public static String getExtension(String fileName) {
        if (StringUtils.INDEX_NOT_FOUND == StringUtils.indexOf(fileName, DOT)) {
            return StringUtils.EMPTY;
        }
        String ext = StringUtils.substring(fileName,
                StringUtils.lastIndexOf(fileName, DOT));
        return StringUtils.trimToEmpty(ext);
    }

    /**
     * 判断是否存在扩展名
     *
     * @param fileName
     * @return
     */
    public static boolean hasExtension(String fileName) {
        return !isExtension(fileName, StringUtils.EMPTY);
    }

    /**
     * 判断是否同为扩展名
     *
     * @param fileName
     * @param ext
     * @return
     */
    public static boolean isExtension(String fileName, String ext) {
        return StringUtils.equalsIgnoreCase(getExtension(fileName), ext);
    }

    /**
     * 获取文件mime类型
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String getMimeType(String fileName) throws IOException {
        Path source = Paths.get(fileName);
        return Files.probeContentType(source);
    }

    public static void main(String[] args) {
        while (true) {
            String random = RandomStringUtils.random(4, "abcdefghigklmnopqrstuvwxyz1234567890");
            String[] splits = random.split("");
            for (String s : splits) {

            }
        }
    }

}
