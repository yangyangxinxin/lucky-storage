package com.luckysweetheart.storage.image;

import com.luckysweetheart.storage.image.base.PictureProcess;
import com.luckysweetheart.storage.util.Cons;
import org.apache.commons.codec.binary.Base64;

/**
 * 图片水印处理
 * https://help.aliyun.com/document_detail/44957.html?spm=5176.doc47505.2.12.qICXt3
 * Created by yangxin on 2017/10/27.
 */
public class WatermarkProcess implements PictureProcess {

    /**
     * 水印文字
     */
    private String text;

    public WatermarkProcess(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String process() {
        return Cons.PREFIX + "watermark,text_" + Base64.encodeBase64String(text.getBytes());
    }
}
