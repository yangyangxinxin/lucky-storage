package com.luckysweetheart.storage.image;

import com.luckysweetheart.storage.image.base.PictureProcess;
import com.luckysweetheart.storage.util.Cons;
import org.apache.commons.codec.binary.Base64;

/**
 * 图片格式转换
 * Created by yangxin on 2017/10/27.
 */
public class ConvertFormatProcess implements PictureProcess {

    /**
     * 需要转换的格式，如png
     */
    private String format;

    public ConvertFormatProcess(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String process() {
        return Cons.PREFIX + "format," + format;
    }

}
